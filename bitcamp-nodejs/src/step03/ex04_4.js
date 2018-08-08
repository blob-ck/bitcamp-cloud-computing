// 주제 : DBMS 다루기 - 회원 삭제하기
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
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});

    if (urlInfo.pathname === '/favicon.ico') {
        res.end();
        return;
    }
    if (urlInfo.pathname !== '/member/delete') {
        res.end('해당 url을 지원하지 않습니다.');
        return;
    }

    console.log(urlInfo.query);
    var id = urlInfo.query.id;
    pool.query(
        `delete from pms2_member
         where mid=?`,
         [id],
        function(err, results){
            if (err) {
                res.end('DB 삭제 중 예외 발생!');
                return;
            } 
        res.end('정상 삭제하였습니다!');
    });
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});
