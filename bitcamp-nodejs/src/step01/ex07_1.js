// 주제 : module을 정의하고 사용하기 5

// exports에 저장된 함수 받기

var fn = require('./ex07_m');

var obj = fn();

obj.plus(100);
console.log(obj.getResult());
obj.minus(7);
console.log(obj.getResult());
obj.multiple(6);
console.log(obj.getResult());
obj.divide(8);

console.log(obj.getResult());
//result 변수는 리턴된 객체에 들어있지 않다. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// => 클로저가 공유하는 메모리에 있을 뿐이므로,
// => 외부에서 직접적으로 접근할 수 없다.
// => 자바 문법으로 따지면 일종의 private 변수가 된 것이다.