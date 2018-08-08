// 주제 : HTTP 서버 만들기 - URL 분석하기
const http = require('http');

// URL 분석에 사용할 모듈 로딩
const url =require('url');

const server = http.createServer((req, res) => {
    console.log('요청 받았음');
    
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});

    //클라이언트가 요청한 url 보기
    // => http://localhost:8080/aa?name=orineoguri&age=11
    res.write(`url = ${req.url}\n`); // => /aa?name=orineoguri&age=11 출력

    // URL 분석
    var urlInfo = url.parse(req.url);

    res.write(`href= ${urlInfo.href}\n
pathname= ${urlInfo.pathname}\n
search= ${urlInfo.search}\n
query= ${urlInfo.query}\n`);

    res.write('테슽흐');
    res.end();
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});

//브라우저에 출력됨

// url = /aa?name=orineoguri&age=11
// href= /aa?name=orineoguri&age=11

// pathname= /aa

// search= ?name=orineoguri&age=11

// query= name=orineoguri&age=11
// 테슽흐
