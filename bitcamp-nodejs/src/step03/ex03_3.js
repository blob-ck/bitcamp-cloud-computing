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

    // query 객체에서 파라미터 명을 사용하여 갑을 꺼내고 싶다면,
    // 두 번째 파라미터의 값을 true 로 설정하라!!!!
    // => parse(req.url, true) 함수가 파라미터 값을 꺼내기 쉽도록
    //    query객체에 프로퍼니 형태로 담아둔다.
    var urlInfo = url.parse(req.url, true);

    //파라미터값을 꺼낼 때는 그냥 "query.파라미터명" 으로 쓴다.
    res.write(`name=${urlInfo.query.name}\n`);
    res.write(`age=${urlInfo.query.age}\n`);
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
