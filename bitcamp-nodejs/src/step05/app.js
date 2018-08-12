// 과제 : 회원관리, 팀관리, 게시판, 강좌관리 웹애플리케이션 만들기

//주제 : express 사용하기 - 요청 핸들러를 모듈로 분리하기
const express = require('express');
const app = express();

//POST 요청 데이터 처리
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: false}));

//정적 HTML 파일 처리
// => 현재 node 를 실행한 폴더 기준으로 폴더를 찾는다. 
// step04 폴더에서 node실행하면 step04 를 루트로 폴더를 찾는다.
// public 폴더의 HTML파일을 긁어온다
app.use(express.static('public'));

//통합 템플릿 엔진 관리자 - 템플릿 엔진이 아니라 템플릿 엔진을 중간에서 관리해 줌.
//다른 템플릿으로 바꿀 때 등등
const consolidate = require('consolidate');

//Express에 템플릿 엔진을 등록한다.
// => consolidate(템플릿 관리자)에 대해 template 을 지정하면 찾아내서 리턴한다.
//    여기선 handlebars
//Express에 여러개의 템플릿 엔진을 등록할 수 있다.
//        (key,    engine)   => html로 템플릿을 이름지었다. 꺼내쓸때 html로 꺼내씀
app.engine('html', consolidate.handlebars);

// => 등록된 템플릿 엔진 중에 사용할 엔진을 지정한다.
app.set('view engine', 'html');

//템플릿 파일이 있는 디렉토리 경로를 지정한다.
//스프링의 viewResolver 처럼...
const path = require('path');
app.set('views', path.join(__dirname, 'template'));

//=> 라우터를 Express의 웹서버에 등록한다.
app.use('/member', require('./member.js'));// OK
app.use('/board', require('./board.js'));// ing
// app.use('/class', require('./member.js'));// Not YET
// app.use('/team', require('./member.js'));// Not YET
// app.use('/teammember', require('./member.js'));// Not YET
// app.use('/task', require('./member.js'));// Not YET

app.get('/hello', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write(`Hello Node!!`);
    res.end();
});

app.listen(8000, () => {
    console.log('서버 실행 중......');
});