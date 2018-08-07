// 주제 : module을 정의하고 사용하기 6

// exports에 저장된 함수 받기
// module 바깥에 선언된 함수가 있다면 전역변수로 사용한다.

var obj1 = require('./ex08_m');
var obj2 = require('./ex08_m');

obj1.plus(100);
obj1.minus(7);
console.log(obj1.getResult());

obj2.plus(100);
obj2.multiple(7);
console.log(obj2.getResult());
console.log(obj1.getResult());

// 결론!
// => require() 가 리턴하는 객체의 함수는 같은 module 변수를 사용한다.
