// 주제 : module을 정의하고 사용하기 2

// require()가 리턴한 exports 객체를 분해해서 받을 수 있다.
// => 리턴값 destructuring
var {plus, minus} = require('./ex03_m.js');

console.log(plus(10, 20));
console.log(minus(10, 20));

