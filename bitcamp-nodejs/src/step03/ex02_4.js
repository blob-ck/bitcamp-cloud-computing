// 주제 : HTTP 서버 만들기 - 클라이언트에게 출력하기 - 멀티라인 처리

const http = require('http')

const server = http.createServer((req, res) => {
    console.log('요청 받았음');
    
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});

    
    //줄 끝에 \ 를 붙이면 멀티라인으로 쉽게 사용할 수 있다.
    // 다만 공백도 문자열로 취급하므로 주의!
    res.write(
'<html>\n\
<head>\n\
    <title>Node.js</title>\n\
</head>\n\
<body>\n\
    <h1>안녕!</h1>\n\
</body>\n\
</html>')
    res.end();
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});

