import fs from 'fs';
const logPath = '../JBNU_FoodParser/Logs/logs.txt';

var now = new Date();

const EditLog = ((info)=>{

    try{
        fs.appendFile(logPath,now+' => '+info,'utf-8',()=>null);
    }catch(e){
        console.log(e);
    }

});

/**
 * 
 * 해당 함수는 로그를 작성하는 함수입니다.
 * 기본적으로 Logs 폴더에 logs.txt 파일에 기록합니다.
 * 
 * 만약, logs.txt 파일이 없으면 오류가 출력됩니다.
 * 즉, 반드시 Logs폴더에 logs.txt 파일을 만들어주세요.
 * 
 */


export default EditLog;