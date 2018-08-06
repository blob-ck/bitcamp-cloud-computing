// 주제 : module을 정의하고 사용하기 3

// destructuring 변수를 바꾸기 (alias)
// {원래property명 : 변수명, 원래property명 : 변수명, ...}
var {plus:p, minus:m} = require('./ex03_m.js');

console.log(p(10, 20));
console.log(m(10, 20));

