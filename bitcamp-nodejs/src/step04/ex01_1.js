//주제 : express 사용하기 - 서버 실행
// => express 모듈 로딩
const express = require('express');

//=> 웹서버 객체 준비
const app = express();

//=> url에 대해 함수 연결하기
app.get('/test01', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write('test01 OK!');
    res.end();
});

//=> 서버 실행
app.listen(8000, () => {
    console.log('서버 실행 중......');
});