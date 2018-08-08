// 주제 : HTTP 서버 만들기 - 클라이언트에게 출력하기

const http = require('http')

const server = http.createServer((req, res) => {
    console.log('요청 받았음');
    res.end('Hi~');
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});

