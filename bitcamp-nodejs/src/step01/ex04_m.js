// module 정의 2
// 존재하는 module.exports 객체를 사용하는 대신
// => 새 객체를 만들어 리턴할 수 있다.

module.exports = {
        // {}에서 함수 정의하는 방법 1
        plus: function(a, b) { return a + b;},
        minus: function(a, b) { return a - b;},
        
        // {}에서 함수 정의하는 방법 2
        multiple(a, b) { return a * b;},
        divide(a, b) { return a / b;}
}
// ! 결론 !
// => module에서 뭔가를 리턴하고 싶다면 exports에 담아라!