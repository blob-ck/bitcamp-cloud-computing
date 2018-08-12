// 주제 : HTTP 서버 만들기 - 요청 정보 다루기
const http = require('http')

const server = http.createServer((req, res) => {
    console.log('요청 받았음');
    
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});

    //클라이언트가 요청한 url 보기
    // => http://localhost:8080/aa?name=orineoguri&age=11
    res.write(req.url); // => /aa?name=orineoguri&age=11 출력

    res.end();
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});

