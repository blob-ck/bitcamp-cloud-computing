// 주제 : module을 실행하는 과정 알아보기

// module 에서 exports객체에 아무것도 담지 않으면 기본으로 빈 객체를 리턴한다.
var obj = require('./ex09_m');

console.log(obj);