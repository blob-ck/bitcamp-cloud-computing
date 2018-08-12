// 주제 : HTTP 서버 만들기 - 서버 실행하기

// 1) 모듈 로딩
const http = require('http')

// 2) HTTP 서버 객체 생성
// => 파라미터는 요청이 들어왔을 때 호출될 함수
const server = http.createServer((req, res) => {
    console.log('요청 받았음');
});

                // 웹 브라우저의 요청을 받았지만 응답을 하지 않았기 때문에
                // 웹 브라우저는 계속 응답을 기다리는 상태에 있을 것이다.

// 3) HTTP 서버 실행
// => listen(포트번호, [서버가 시작된 후 호출될 함수])
// 이벤트에 대해서 리스너를 등록하는 것.
// This function is asynchronous.
// When the server starts listening, the 'listening' event will be emitted. 
// The last parameter callback will be added as a listener for the 'listening' event.
server.listen(8000,
              ()=>{console.log('리슨~ 서버 시작됨~')}
);

