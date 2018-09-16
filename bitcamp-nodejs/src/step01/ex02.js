// 주제 : Node.js 에서 라이브러리 사용하는 방법
// => 노드에서는 library라는 말 보다는 module 이라는 말을 사용한다.

//HTTP 통신을 도와주는 모듈을 로딩한다.
// => HTML에서 <script>의 역할과 같은 것이 require 다.
const http = require('http');
var a = 0;
//HTTP서버를 설정한다.
// => 클라이언트에서 요청이 들어왔을 때 호출될 함수를 등록한다.
const server = http.createServer((req, res) => {
    console.log('클라이언트가 요청하였음!@')
    a++;
    console.log(a + '번 호출했다~');
  res.end();
});

//HTTP서버를 시작한다.
// => 서버가 시작되었을 때 호출될 메서드를 등록한다.
server.listen(8000, () => {
    console.log('서버 실행중');
});