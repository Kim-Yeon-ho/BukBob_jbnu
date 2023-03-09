import { initializeApp } from 'firebase/app';
import { getFirestore,doc, setDoc } from 'firebase/firestore';
import  firebaseConfig  from '../SecretKeyModule/SecuritKey.js';
import  GetFood  from './GetFoodListModule.js';
import  EditLog from '../LoggerModule/Logger.js';

const apiKey = initializeApp(firebaseConfig);
const fireDB = getFirestore(apiKey);

const DbFoodListUpdateStart = await ((foodArr)=>{

  try{
    setDoc(doc(fireDB,'Food','Jinswo'),{
      FoodList : foodArr[0]
    });

    setDoc(doc(fireDB,'Food','Medical'),{
      FoodList : foodArr[1]
    });

    setDoc(doc(fireDB,'Food','Husaeng'),{
      FoodList : foodArr[2]
    });
  }catch(e){
    EditLog('DB Update Error : '+ e);
  }

})

/**
 * 
 * 위 함수는 Firestore에 각 진수당, 의대, 후생관의 식단을
 * 업로드하는 함수입니다.
 * 
 * foodArr에는 각 음식 리스트의 배열이 담겨 있습니다.
 * ex.) [[진수당 식단 리스트], [의대 식단 리스트], [후생관 식단 리스트]]
 * 
 */


const UpdateFoodList = (()=>{
  GetFood().then((foodArr)=>{

    if(Array.isArray(foodArr) === false){
      EditLog('NetWork Error : Response TimeOut');
      //Response TimeOut이 발생할 경우 Array가 리턴되지 않기 때문에 해당 코드로 오류 처리가 가능합니다.
    }else {
      DbFoodListUpdateStart([foodArr[0],foodArr[1],foodArr[2]]);
      EditLog('Update Complete');
    }

  });
  //각 식당의 식단 리스트를 받아와 DB에 저장하는 함수에 넘겨주는 함수입니다.
})


export default UpdateFoodList;
