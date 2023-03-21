/***
 * 
 * 2023/03/20 (월)
 * 작성자 : LeeJungHwan
 * 
 */

import { initializeApp } from 'firebase/app';
import { getFirestore,doc, setDoc } from 'firebase/firestore';
import  firebaseConfig  from '../SecretKeyModule/SecuritKey.js';
import  GetFood  from './GetFoodListModule.js';
import  EditLog from '../LoggerModule/Logger.js';

const apiKey = initializeApp(firebaseConfig);
const fireDB = getFirestore(apiKey);

const weeks = ['월','화','수','목','금'];
const foodWeekIndex = [[20,25,30,35,40,45],[21,26,31,36,41,46],[22,27,32,37,42,47],[23,28,33,38,43,48],[24,29,34,39,44,49]];
//각 요일별 후생관 한식 ~ 샐러드 까지의 인덱스 번호입니다.
const omuriceIndex = [52,53,54,55,56];
// 돈까스와 오므라이스 공통 영역을 제외한 각 요일별 오므라이스 인덱스 번호입니다.
var weeksCounter = 0;
// for문을 사용할때 요일을 카운트 하기 위한 변수입니다.


/**
 * 후생관
 * 20,25,30,35,40,45 월요일 샐러드까지 5 단위
 * 21,26,31,36,41,46 화요일 샐러드까지 5 단위 
 * 22,27,32,37,42,47 수요일 샐러드까지 5 단위
 * 23,28,33,38,43,48 목요일 샐러드까지 5 단위
 * 24,29,34,39,44,49 금요일 샐러드까지 5 단위
 * 
 * 돈까스 공통 50
 * 오므라이스 공통 51
 * 오므라이스 월화수목금 52 ~ 56
 * 김밥라면우동 공통 -> 57 ~ 59
 * 
 */

const DbFoodListUpdateStart = ((foodArr) => {

  try {
    SetDbJinswoMedical(foodArr, 0, 5, 'Jinswo', '진수원', '중식');
    SetDbJinswoMedical(foodArr, 5, 10, 'Jinswo-night', '진수원', '석식');
    SetDbJinswoMedical(foodArr, 10, 15, 'Medical', '의대', '중식');
    SetDbJinswoMedical(foodArr, 15, 20, 'Medical-night', '의대', '석식');
    SetDbHusaeng(foodArr);

  } catch (e) {
    EditLog('DB Update Error : ' + e);
  }

})

/**
 * 위 함수는 Firestore에 각 진수당, 의대, 후생관의 식단을
 * 업로드하는 함수입니다.
 */

const SetDbJinswoMedical = ((foodArr, start, end, docName, title, state)=>{

  weeksCounter = 0;

  for(var i = start; i < end; i++){
    setDoc(doc(fireDB,docName,weeks[weeksCounter]),{
      Weeks : weeks[weeksCounter],
      state : state,
      Title : title,
      List : foodArr[0][i].List
    });
    weeksCounter++;
  }

})

/***
 * 진수원과 의대 식단을 업로드하는 함수입니다.
 * 각 매개 인자값으로 (파싱한 음식 리스트, 시작 인덱스 번호, 끝 인덱스 번호, 데이터 베이스 doc 이름, 식당 이름, 식당 중식 or 석식 상태정보입니다.)
 */


const SetDbHusaeng = ((foodArr)=>{

  weeksCounter = 0;
  try{
    foodWeekIndex.forEach(list =>{
      var foodItemList = ""
        list.forEach(index=>{
          foodItemList += foodArr[0][index].List + "</br>";
        });

        foodItemList += foodArr[0][50].List + "</br>";
        foodItemList += foodArr[0][51].List + "</br>";
        foodItemList += foodArr[0][omuriceIndex[weeksCounter]].List + "</br>";
        /***
         * 50 ~ 51은 양식 -> 돈까스류 오므라이스류 요일별 공통 메뉴 입니다.
         * 52 ~ 56은 양식 -> 요일별 오므라이스류 메뉴 입니다.
         */

        foodItemList += foodArr[0][57].List + "</br>";
        foodItemList += foodArr[0][58].List + "</br>";
        foodItemList += foodArr[0][59].List + "</br>";
        /***
         * 57 ~ 58은 후생관의 분식 항목에서 김밥,라면,우동을 나타내는 Index입니다.
         */

        setDoc(doc(fireDB,'Husaeng',weeks[weeksCounter]),{
          Weeks : weeks[weeksCounter],
          state : '중식',
          Title : '후생관',
          List : foodItemList
        });
        weeksCounter++;
    })
  }catch(e){
    console.log(e);
  }

})


const UpdateFoodList = (()=>{
  GetFood().then((foodArr)=>{
    if(Array.isArray(foodArr) === false){
      EditLog('NetWork Error : Response TimeOut');
      //Response TimeOut이 발생할 경우 Array가 리턴되지 않기 때문에 해당 코드로 오류 처리가 가능합니다.
    }else {
      DbFoodListUpdateStart(foodArr);
      EditLog('Update Complete');
    }

  });
  //각 식당의 식단 리스트를 받아와 DB에 저장하는 함수에 넘겨주는 함수입니다.
})


export default UpdateFoodList;