// 주제 : module을 정의하고 사용하기 4

// exports에 저장된 함수 받기

var fn = require('./ex06_m');
var obj = fn();

console.log(obj.plus(10,20));
console.log(obj.minus(10,20));
console.log(obj.multiple(10,20));
console.log(obj.divide(10,20));
