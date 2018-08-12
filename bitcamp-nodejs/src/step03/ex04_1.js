// 주제 : DBMS 다루기 - 회원 조회하기
// [실행 url]
// => http://localhost:8000/member/list?pageNo=1&pageSize=3
// [출력결과]
// 아이디, 이메일

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
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});

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

        // end() 시점을 잘 알아야 비동기 사용시 원하는 대로 작동한다.
        //pool 바깥에서 end() 하게 되면 비동기로 요청해 놓은 결과물을 가져오지 못하고 응답해버리므로 주의!!
        res.end();
    });
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});
