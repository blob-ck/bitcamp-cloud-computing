// 주제 : module을 실행하는 과정 알아보기

// require() 를 여러번 호출해도 각 module은 한번만 실행된다.
// => 그래서 return 값은 같다.

// require() 를 여러번 호출할 때 리턴 값은?
var obj1 = require('./ex09_m');
obj1.test = 100;
console.log(obj1); // 100

var obj2 = require('./ex09_m');
obj2.test = 200;
console.log(obj1); // 200
console.log(obj2); // 200

var obj3 = require('./ex09_m');
obj3.test = 999;
console.log(obj1); // 999
console.log(obj2); // 999
console.log(obj3); // 999

// 결론~!
// => module 은 오직 한 번만 실행된다.(물론 다른 모듈이라면 각각 한 번씩 실행된다)
// => module 변수는 오직 한개만 존재한다 -> 싱글톤? 보다는 스프링의 프로토타입
// require() 에 같은 모듈을 여러번 호출해도 같은 객체를 리턴한다.