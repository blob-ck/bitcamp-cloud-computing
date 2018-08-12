// 주제 : Template 엔진 사용하기 - HTML 출력하기 (동기방식)
const express = require('express');
const app = express();
const handlebars = require('handlebars');


//외부 파일의 경로를 다룰 때 사용할 모듈을 로딩한다.
const path = require('path');
console.log(__dirname); // 현재 파일의 경로를 출력한다.
//C:\Users\bit-user\git\bitcamp-cloud-computing\bitcamp-nodejs\src\step03

//외부 템플릿 파일의 경로 설정하기
var templatePath = path.join(__dirname, 'ex06_4_template.html');
console.log(templatePath);
//C:\Users\bit-user\git\bitcamp-cloud-computing\bitcamp-nodejs\src\step03\ex06_4_template.html

//템플릿 파일의 내용을 읽어들일 모듈 로딩
const fs = require('fs'); // File System

//동기방식으로 템플릿 파일의 내용을 읽어들인다.
//동기방식이므로 파일을 전부 읽은 후 리턴한다.
//리턴값은 읽은 파일의 데이터 -> 버퍼 이므로, 문자열로 변환하는 작업이 필요하다.
var templateSrc = fs.readFileSync(templatePath).toString();
console.log(templateSrc);

// 템플릿 소스에 데이터를 삽입하여 최종 결과를 만들어낼 함수를 준비한다.
var templateFn = handlebars.compile(templateSrc);


app.get('/hello', (req, res) => {

    // 템플릿 함수를 호출하여 소스로부터 결과를 얻는다.
    // => 소스에 삽입될 데이터를 파라미터로 넘긴다
    var resultStr = templateFn({name: '홈깃똥'});
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});
    res.write(resultStr);
    res.end();
});

app.listen(8000, () => {
    console.log('서버 실행 중!');
});