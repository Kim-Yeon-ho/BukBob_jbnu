/**
 * 작성일 : 2023 / 03 / 08
 * 
 * 주의! 매번 홈페이지의 주요 태그가 변경되어 수시로 업데이트 해줘야 할 수도 있습니다.
 * 
 * '전북대학교생활협동조합 주간메뉴'사이트를 기준으로 파싱해 사용한 코드입니다.
 * 
 * 각 진수원 / 의대 / 후생관의 파싱은 인덱스 기반으로 진행했습니다.
 * 
 * 진수원 0 ~ 40번 [ 점심 / 저녁 ] 
 * 의대  41 ~ 104번 [ 점심 / 저녁 ] -> 의대는 기존 allFoodList에서 추가적인 필터를 사용하여 작성했습니다.
 * 후생관 50 ~ allFoodList의 마지막 인덱스를 그대로 사용했습니다. 즉, allFoodList를 재활용했습니다.
 * 
 * 즉, 모든 필터 문서의 기반은 allFoodList를 기반으로 재활용 했습니다.
 * 추가적으로 여기서 말하는 문서란 html을 말하는 것이며, html을 담은 변수를 뜻합니다.
 */

import axios from 'axios';
import cheerio from 'cheerio';
import EditLog from '../LoggerModule/Logger.js';

const url = 'https://sobi.jbnu.ac.kr/menu/week_menu.php';
// 소비자 생활협동조합 주소

var allFoodList = [];
var jinswoFoodList = [];
var medicalFoodList = [];
var husaengFoodList = [];
// 각 배열은 진수원, 의대, 후생관 리스트입니다.

var isUpdate = false;
// 음식 리스트 업데이트 유무를 확인하기 위한 변수입니다.

const CheckFoodUpdate = async ()=>{

  const WaitResponse = (timeToDelay) => new Promise((resolve) => setTimeout(resolve,timeToDelay));
  // setTimeout 함수에 Promise를 적용하여 비동기처리했습니다.
  // 위 과정을 적용하지 않고 setTimeout 함수를 사용하면 Promise가 반환되지 않아 await을 사용해도 무시하고 함수가 바로 종료됩니다.
  // 즉 올바른 리스트 결과를 UpdateFoodListModule에게 전달 할 수 없습니다.

  GetFoodHTML();

  await WaitResponse(10000);
  // 테스트는 10sec로 진행했습니다.
  // 안정적인 작업을 위해 실제 서비스 할 경우 60sec로 적용하여 진행해주세요.
  // +- 2sec의 오차범위가 존재합니다.

  if(isUpdate){
    return [jinswoFoodList,medicalFoodList,husaengFoodList];
  }else{
    return 1;
  }
  // 60초 동안 각 호관 음식 리스트 업데이트를 대기하고 결과를 리턴합니다.
  // 0은 이상 없음 / 1은 리스폰 타임아웃입니다.
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
      allFoodList = html.toString().split('<br>').toString().split(',');
      //진수당 중식,석식 Parser
    
    }).then(()=>{
      for(var i = 0; i < 40; i++){
      jinswoFoodList.push(allFoodList[i]);
      }
    }).then(() => {
      const list = allFoodList.toString()
                    .split('</span>').toString()
                    .split('<span style="font-size: 14px; letter-spacing: -1px;">').toString()
                    .split('<br style="font-size: 14px; letter-spacing: -1px;">').toString()
                    .split(',');
    
      for(var i = 41; i < 104; i++){
        if(list[i] !== ''){
          medicalFoodList.push(list[i]);
        }
      }
    
      //의대 중식,석식 Parser
      //해당 부분은 for문으로 순회하여 공백칸을 제거하기위해 for문이 사용됐습니다.
    
    }).then(()=>{
      //후생관 Parser
      //차후에 이곳을 파이어베이스 업데이트 구간으로 사용할 것
      //해당 부분은 for문을 사용하여 allFoodList의 50번 구간부터 husaengFoodList에 메뉴를 추가했습니다.
    
        for(var i = 50; i < allFoodList.length; i++){
          husaengFoodList.push(allFoodList[i]);
        }
    
      }).then(()=>{
        isUpdate = true;
      })
  }catch(e){
    EditLog('Parser Array Error : ' + e);
  }


export default CheckFoodUpdate;