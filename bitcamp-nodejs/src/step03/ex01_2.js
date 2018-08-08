// 주제 : HTTP 서버 만들기 - 서버 실행하기

const http = require('http')

const server = http.createServer((req, res) => {
    console.log('요청 받았음');
    
    //HTTP  응답을 완료해서 웹 브라우저의 기다림이 멈춘다.
    res.end();
});

server.listen(8000,()=>{
    console.log('리슨~ 서버 시작됨~')
});

