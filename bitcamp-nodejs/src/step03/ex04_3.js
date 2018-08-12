// 주제 : DBMS 다루기 - 회원 변경하기
// [실행 url]
// => http://localhost:8000/member/update?id=user1000?email=user1000@test.com&password=2222
// [출력결과]
// 변경 성공입니다.

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
    if (urlInfo.pathname !== '/member/update') {
        res.end('해당 url을 지원하지 않습니다.');
        return;
    }
    
    console.log(urlInfo.query);
    var id = urlInfo.query.id;
    var email = urlInfo.query.email;
    var password = urlInfo.query.password;

    pool.query(
        `update pms2_member set email=?, pwd=password(?)
        where mid=?`,
        [email, password, id],
        function(err, results){
            if (err) {
                res.end('DB update 중 예외 발생!');
                return;
            } 
        res.end('정상 변경되었습니다!');
    });
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});
