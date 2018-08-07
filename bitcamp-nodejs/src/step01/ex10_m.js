// module 정의 8
// - module 변수와 글로벌 변수

// module 변수는 이 파일 내에서만 사용할 수 있다.
var v1 = 100;
console.log("ex10_m", v1); //100

// 글로벌 변수는 모든 모듈이 공유한다.
console.log("global", global.v1); //200
console.log(v1 == global.v1); // false

// module에서 글로벌 변수 변경하기
global.v1 = 300;