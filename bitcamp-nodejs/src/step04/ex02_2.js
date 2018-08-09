//주제 : express 사용하기 - 정적 HTML 파일을 서비스하기
const express = require('express');
const app = express();

//POST 요청 데이터 처리
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: false}));

//정적 HTML 파일 처리
// => 현재 node 를 실행한 폴더 기준으로 폴더를 찾는다. 
// step04 폴더에서 node실행하면 step04 를 루트로 폴더를 찾는다.
// public 폴더의 HTML파일을 긁어온다
app.use(express.static('public'));

app.get('/test01', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write(`name = ${req.query.name}\n`);
    res.write(`age = ${req.query.age}\n`);
    res.end();
});

app.post('/test02', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write(`name = ${req.body.name}\n`);
    res.write(`age = ${req.body.age}\n`);
    res.end();
});

app.listen(8000, () => {
    console.log('서버 실행 중......');
});