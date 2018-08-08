// 주제 : HTTP 서버 만들기 - QUIZ! 계산기 만들기
// => http://localhost:8000/calc?a=100&b=200&op=%2b
// => http://localhost:8000/calc?a=100&b=200&op=-
// => http://localhost:8000/calc?a=100&b=200&op=*
// => http://localhost:8000/calc?a=100&b=200&op=/

// => 출력결과 100 + 200 = 300

const http = require('http');

// URL 분석에 사용할 모듈 로딩
const url =require('url');

const server = http.createServer((req, res) => {
    
    
    var urlInfo = url.parse(req.url, true);
    
    if (urlInfo.pathname === '/favicon.ico') {
        res.end();
        return;
    }
    
    console.log('요청 받았음');
    
    if (urlInfo.pathname !== '/calc') {
        res.end('해당 url을 지원하지 않습니다.');
        return;
    }
    
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});

    console.log(urlInfo.query);
    var result;
    var a = parseInt(urlInfo.query.a);
    var b = parseInt(urlInfo.query.b);
    var op = urlInfo.query.op;
    switch (op) {
        case '+':
            result = a + b;
            break;
        case '-':
            result = a - b;
            break;
        case '*':
            result = a * b;
            op = '×';
            break;
        case '/':
            result = a / b;
            op = '÷';
            break;
        default:
            console.log('뭐야 뭐 입력한거야');
            break;
    }

    console.log(result);
    res.write(`<body style="background-color: black;">`);
    res.write(`<div style="font-size: 200px; color: red">${a}\n ${op} ${b}\n <hr>= ${result}</div>\n`);
    res.write(`</body>`);
    res.end();
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});
