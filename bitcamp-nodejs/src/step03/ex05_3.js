// 주제 : 여러 개의 요청 처리하기 - 조건문을 없애고 url과 함수를 자동 연결하기
// [실행 url]
// => http://localhost:8000/member/delete?id=user1000
// [출력결과]
// 삭제 성공입니다.


const http = require('http');
const url =require('url');
const mysql = require('mysql');

var pool = mysql.createPool({
    connectionLimit: 10,
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});

const server = http.createServer((req, res) => {
    
    var urlInfo = url.parse(req.url, true);
    if (urlInfo.pathname === '/favicon.ico') {
        res.end();
        return;
    }
    if (urlInfo.pathname !== '/member/list') {
        res.end('해당 url을 지원하지 않습니다.');
        return;
    }

    var pageNo = 1;
    var pageSize = 3;
    if (urlInfo.query.pageNo) {
        pageNo = parseInt(urlInfo.query.pageNo);
    }
    if (urlInfo.query.pageSize) {
        pageSize = parseInt(urlInfo.query.pageSize);
    }
    
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});

    console.log(urlInfo.query);

    var startIndex = (pageNo - 1) * pageSize;
    
    pool.query(
        'select * from pms2_member limit ?, ?',
        [startIndex, pageSize],
        function(err, results){
            if (err) {
                res.end('DB 조회 중 예외 발생!');
                return;
            } 
            
            res.write(`<body style="background-color: black; font-size: 50px; color: red">`);
            for (var row of results) {
                    res.write(`<div>${row.mid} ${row.email}</div>\n`);
            }
            res.write(`</body>`);

        res.end();
    });
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});
