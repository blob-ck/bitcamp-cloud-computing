//주제 : express 사용하기 - 템플릿 엔진 지정하기
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

// 어떤 템플릿 엔진은 아래처럼 호출 등록하는 반면, 아닌것도 있기에 템플릿관리자 consolidate를 사용한다.
    // Express와 호환되는 템플리트 엔진(예: Pug)은 __express(filePath, options, callback)라는 이름의 함수를 내보내며, 
    // 이 함수는 res.render() 함수에 의해 호출되어 템플리트 코드를 렌더링합니다.
    // 일부 템플리트 엔진은 이러한 방식을 따르지 않습니다. 
    // Consolidate.js 라이브러리는 널리 이용되고 있는 모든 Node.js 템플리트 엔진을 
    // 맵핑함으로써 이러한 방식을 따르므로 Express 내에서 완벽하게 작동합니다.




// => 등록된 템플릿 엔진 중에 사용할 엔진을 지정한다.
// 또한 이 이름으로 템플릿의 기본 확장자로 사용한다.
// 아래 template01.html 파일을 확장자를 생략하여 사용하면 설정한 확장자명인 html로 붙는다.
app.set('view engine', 'html');

//템플릿 파일이 있는 디렉토리 경로를 지정한다.
//스프링의 viewResolver 처럼...
const path = require('path');
app.set('views', path.join(__dirname, 'public'));

app.get('/test01', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write(`name = ${req.query.name}\n`);
    res.write(`age = ${req.query.age}\n`);
    res.end();
});

app.post('/test02', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write(`name = ${req.body.name}\n`);
    res.write(`age = ${req.body.age}\n`);
    res.end();
});

app.get('/test03', (req, res) => {
    //확장자는 위에서 설정한대로 html
    //여기선 템플릿이 template01.html 이다. 
    res.render('template01', req.query);

    // res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    // res.write(`name = ${req.body.name}\n`);
    // res.write(`age = ${req.body.age}\n`);
    // res.end();
});

app.listen(8000, () => {
    console.log('서버 실행 중......');
});