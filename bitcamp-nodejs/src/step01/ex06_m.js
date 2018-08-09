// module 정의 4

module.exports = function() {
    
    // 객체를 만들어 리턴
    return {
    
        // {}에서 함수 정의하는 방법 1
        plus: function(a, b) { return a + b;},
        minus: function(a, b) { return a - b;},
        
        // {}에서 함수 정의하는 방법 2
        multiple(a, b) { return a * b;},
        divide(a, b) { return a / b;}
    }
}
