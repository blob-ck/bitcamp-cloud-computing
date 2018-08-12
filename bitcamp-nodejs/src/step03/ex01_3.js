// 주제 : HTTP 서버 만들기 - Single Thread
// => Node.js 는 Single Thread로 동작한다.
// => 즉, 한 클라이언트의 요청을 처리할 때까지 다른 클라이언트는 기다려야 한다.
// => 그래서 Node.js의 HTTP서버는 시간이 오래 걸리는 동시 요청을 처리하기에는 부적합하다.
//    Node.js 의 용도는 작업시간이 짧은 단타성 요청을 처리하는데 적합하다.

const http = require('http')

const server = http.createServer((req, res) => {
    console.log('요청 받았음');
    
    //클라이언트에서 요청이 오면 10초 후에 응답해보기
    // => 테스트 방법 : 웹브라우저에 탭을 두개 띄우고
    //              콘솔창에 뜨는 순서를 확인한다.
    setTimeout(() => {
        res.end();
    }, 10000);
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});

