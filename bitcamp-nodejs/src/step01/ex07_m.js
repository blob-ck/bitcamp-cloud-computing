// module 정의 5
// - 함수 응용 : private 변수를 갖는 객체 만들기
// => 클로저 사용하기

module.exports = function() {
    
    var result = 0;
    
    return {
        plus(value) { result += value},
        minus(value) { result -= value},
        multiple(value) { result *= value},
        divide(value) { result /= value},
        getResult() {return result}
    }
}
