// 주제 : module을 정의하고 사용하기 1

// require('module의 경로' 또는 '모듈명')
// => module의 경로는 현재 파일의 위치의 상대위치다.
var obj = require('./ex03_m.js');

console.log(obj.plus(10, 20));
console.log(obj.minus(10, 20));
console.log(obj.multiple(10, 20));
console.log(obj.divide(10, 20));
