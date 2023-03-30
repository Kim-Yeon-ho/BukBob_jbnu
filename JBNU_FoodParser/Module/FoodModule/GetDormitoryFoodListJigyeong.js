/***
 * 2023/03/27 (월)
 * 작성자 : LeeJungHwan
 * 
 * 주의! 매번 홈페이지의 주요 태그가 변경되어 수시로 업데이트 해줘야 할 수도 있습니다.
 * '전북대학교기숙사 주간식단표'사이트를 기준으로 파싱해 사용한 코드입니다.
 * 
 * 추가적으로 여기서 말하는 문서란 html을 말하는 것이며, html을 담은 변수를 뜻합니다.
 */

import axios from 'axios';
import cheerio from 'cheerio';
import EditLog from '../LoggerModule/Logger.js';

const url = 'https://likehome.jbnu.ac.kr/home/main/inner.php?sMenu=B7100';
// 소비자 생활협동조합 주소

var allFoodList = [];
var filterAllFoodList = [];
// allFoodList에는 진수원,의대,후생관의 식단 정보가 들어가있습니다.
// filterAllFoodList에는 진수원,의대,후생관이 DB에 올라가기 좋게 가공되어 있습니다.

var isUpdate = false;
// 음식 리스트 업데이트 유무를 확인하기 위한 변수입니다.

const CheckFoodUpdate = async ()=>{

  const WaitResponse = (timeToDelay) => new Promise((resolve) => setTimeout(resolve,timeToDelay));
  // setTimeout 함수에 Promise를 적용하여 비동기처리했습니다.
  // 위 과정을 적용하지 않고 setTimeout 함수를 사용하면 Promise가 반환되지 않아 await을 사용해도 무시하고 함수가 바로 종료됩니다.
  // 즉 올바른 리스트 결과를 UpdateFoodListModule에게 전달 할 수 없습니다.

  GetFoodHTML();

  await WaitResponse(1000);
  // 테스트는 10sec로 진행했습니다.
  // 안정적인 작업을 위해 실제 서비스 할 경우 60sec로 적용하여 진행해주세요.
  // +- 2sec의 오차범위가 존재합니다.

  if(isUpdate){
    return [filterAllFoodList];
  }else{
    return 1;
  }
  // 60초 동안 각 호관 음식 리스트 업데이트를 대기하고 결과를 리턴합니다.
  // 배열 리턴은 이상없음 / 1은 리스폰 타임아웃입니다.
}


const GetFoodHTML = async () => {
  
    var foodListHTML = [];
    //axios()를 통해 불러온 HTML문서를 cheerio를 사용해 파싱한 데이터를 담을 배열입니다.
  
    const res = await axios.get(url);
    const $ = cheerio.load(res.data);
    
    /***
     * 
     * axios를 사용해 res변수에 html 문서를 저장한다. 
     * $ 변수에 cheerio를 사용해 html 문서에서 파싱한 데이터를 저장한다.
     * 
     */
  
    try{
      for(const filteredHTML of $('td')){
        foodListHTML.push($(filteredHTML).html())
      }
    }catch(e){
      EditLog('Parser Push Error : ' + e);
    }
  
    return foodListHTML;
    //foodListHTML에는 결과적으로 td 태그를 바탕으로한 html 태그가 담겨있습니다.
    //text로 저장할 경우 메뉴가 구분없이 한줄로 쭉 삽입되어 차후 split이 불가능합니다.
  
  };
  
  /**
   * 
   * 위 함수는 UpdateFoodList 함수가 호출되면 실행되는 함수입니다.
   * 해당 함수를 기반으로 HTML 문서가 받아와지고 아래 then을 사용하여 필터작업을 진행했습니다.
   * 
   * UpdateFoodList 함수에서는 파이어베이스에 정보에 전송만 됩니다.
   * 
   */

  try{
    GetFoodHTML().then(html => {
      allFoodList = html.toString().replace(/(<([^>]+)>)/ig,'<br>').split('<td>').toString().replace(/\n/igm,'').replace(/\t/igm,'').replace(/\s/igm,'').split(',');
    }).then(()=>{
      var FoodList = new Map()
      var foodItem
      for(var i = 0; i < allFoodList.length; i++){
        foodItem = allFoodList[i].split('<br>');
        FoodList = {
          List : foodItem
        }
        if(foodItem != ''){
          filterAllFoodList.push(FoodList);
        }
      }
    }).then(()=>{
        isUpdate = true;
      })
  }catch(e){
    EditLog('Parser Array Error : ' + e);
  }


export default CheckFoodUpdate;