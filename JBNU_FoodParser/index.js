/***
 * 
 * 2023/03/20 (월)
 * 작성자 : LeeJungHwan
 * 
 */


import  FoodListUpdateStart  from './Module/FoodModule/UpdateFoodListModule.js';
import  EditLog  from '../JBNU_FoodParser/Module/LoggerModule/Logger.js';

try{
    setInterval(FoodListUpdateStart,1000,null);
    EditLog('Update Start');

    // 24 시간 86400000 -> 상용 단계에서 권장
    // 12 시간 43200000 -> 안정화 단계에서 권장
    // 8  시간 28800000 -> 안정화 단계에서 권장
    // 4  시간 14400000 -> Test 단계에서 권장
    // 2  시간 7200000  -> Test 단계에서 권장

}catch(e){
  console.log('Parser Interval function Error : ' + e);
}