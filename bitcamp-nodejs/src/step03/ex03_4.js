// 주제 : HTTP 서버 만들기 - URL 에 따라 작업을 분리하기
const http = require('http');

// URL 분석에 사용할 모듈 로딩
const url =require('url');

const server = http.createServer((req, res) => {
    
    var urlInfo = url.parse(req.url, true);
    console.log(urlInfo);

    if (urlInfo.pathname === '/favicon.ico') {
        res.end();
        return;
    }
    
    console.log('요청 받았음');

    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});

    if (urlInfo.pathname === '/board/list/') {
        res.write('게시물 목록\n');
    } else if (urlInfo.pathname === '/board/add/') {
        res.write('게시물 등록\n');
    } else {
        res.write('해당 URL을 지원하지 않습니다.\n');
    }
    res.write(`name=${urlInfo.query.name}\n`);
    res.write(`age=${urlInfo.query.age}\n`);
    res.end();
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});
