// 주제 : HTTP 서버 만들기 - 클라이언트에게 출력하기 - 컨텐트 타입 설정

const http = require('http')

const server = http.createServer((req, res) => {
    console.log('요청 받았음');
    
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});

    //개발도중 소스보기 하면 한줄로 나와서 가독성이 떨어지므로 줄바꿈 문자를 추가해줬다.
    res.write('<html>\n')
    res.write('<head>\n')
    res.write('<title>Node.js</title>\n')
    res.write('</head>\n')
    res.write('<body>\n')
    res.write('<h1>안녕!</h1>\n')
    res.write('</body>\n')
    res.write('</html>')
    res.end();
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});

