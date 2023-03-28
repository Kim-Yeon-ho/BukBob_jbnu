/***
 * 
 * 2023/03/27 (월)
 * 작성자 : LeeJungHwan
 * 
 */

import { initializeApp } from 'firebase/app';
import { getFirestore,doc, setDoc } from 'firebase/firestore';
import  firebaseConfig  from '../SecretKeyModule/SecuritKey.js';
import  GetFood  from './GetDormitoryFoodListCham.js';
import  EditLog from '../LoggerModule/Logger.js';

const apiKey = initializeApp(firebaseConfig);
const fireDB = getFirestore(apiKey);

const weeks = ['일','월','화','수','목','금','토'];
const dayState = ['-breakfast','-lunch','-night']
var weeksCounter = 0;

/**
 * weeks -> 요일을 담고있는 리스트입니다.
 * dayState -> 아침,점심,저녁 docName을 담고있는 리스트입니다.
 * weeksCounter -> 요일 정보를 변경시킬때 사용되는 변수입니다.
 */

const DbFoodListUpdateStart = ((foodArr) => {

  try {
    SetDbChame(foodArr, 0, 6, 'Chame'+dayState[0], '참빛관', '조식');
    SetDbChame(foodArr, 7, 13, 'Chame'+dayState[1], '참빛관', '중식');
    SetDbChame(foodArr, 14, 20, 'Chame'+dayState[2], '참빛관', '석식');

  } catch (e) {
    EditLog('DB Update Error : ' + e);
  }

})

/**
 * 위 함수는 Firestore에 직영관 아침,점심,저녁을 업데이트 함수를 콜합니다.
 * 업로드하는 함수입니다.
 */

const SetDbChame = ((foodArr, start, end, docName, title, state)=>{

  weeksCounter = 0;

  for(var i = start; i <= end; i++){
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
 * 직영관 식단 업로드하는 함수입니다.
 * 각 매개 인자값으로 (파싱한 음식 리스트, 시작 인덱스 번호, 끝 인덱스 번호, 데이터 베이스 doc 이름, 식당 이름, 식당 조식 or 중식 or 석식 상태정보입니다.)
 */


const UpdateFoodList = (()=>{
  GetFood().then((foodArr)=>{
    if(Array.isArray(foodArr) === false){
      EditLog('NetWork Error : Response TimeOut');
      //Response TimeOut이 발생할 경우 Array가 리턴되지 않기 때문에 해당 코드로 오류 처리가 가능합니다.
    }else {
      DbFoodListUpdateStart(foodArr);
      EditLog('Update Complete Cham');
    }
  });
  //식당의 식단 리스트를 받아와 DB에 저장하는 함수에 넘겨주는 함수입니다.
})


export default UpdateFoodList;