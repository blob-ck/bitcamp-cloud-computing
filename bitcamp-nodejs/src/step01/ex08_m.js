// module 정의 6
// module 을 변수로 사용하기

var result = 0;

module.exports = {
        plus(value) { result += value},
        minus(value) { result -= value},
        multiple(value) { result *= value},
        divide(value) { result /= value},
        getResult() {return result}
}
