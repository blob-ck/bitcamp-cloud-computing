// 주제 : module을 정의하고 사용하기 2

// require('module의 경로' 또는 '모듈명')
// => module의 경로는 현재 파일의 위치의 상대위치다.

// module 명에서 .js 확장자를 생략해도 된다.
// 물론 커맨드창에서도 생략 가능하다.
// 단! 같은 폴더에 있더라도 ./로 경로를 명시하지 않으면  module명으로 인식한다.
// module명으로 찾을 때는 npm이 설치한 module 경로에서 찾기 때문에
// 소스 경로에서 찾을 수 없다.
var obj = require('./ex04_m');

console.log(obj.plus(10, 20));
console.log(obj.minus(10, 20));
console.log(obj.multiple(10, 20));
console.log(obj.divide(10, 20));
