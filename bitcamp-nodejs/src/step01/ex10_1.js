// 주제 : module을 실행하는 과정 알아보기

// 글로벌 변수
// => 노드에서 기본으로 정의된 객체인 global에 저장된 변수이다.
// => 웹브라우저에서 실행할 때는 window 객체이다.

global.v1 = 200;
require('./ex10_m.js');

console.log(global.v1); //300
