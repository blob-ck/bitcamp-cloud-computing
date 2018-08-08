// 주제 : 여러 개의 요청 처리하기 - 회원 CRUD 모아넣기 (add, select, update, delete)
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
    

    //url.pathname 으로 불러올 함수 구분하고,
    //필요한 파라미터는 함수 종속
function selectList(pool, query, params){
    pool.query(
        query,
        params,
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
}
    




const server = http.createServer((req, res) => {
    
    var urlInfo = url.parse(req.url, true);
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});
    
    if (urlInfo.pathname === '/favicon.ico') {
        res.end();
        return;
    }
    
    var path = urlInfo.pathname;

    switch(path) {
        case '/member/list':

            var pageNo = 1;
            var pageSize = 3;
            if (urlInfo.query.pageNo) {
                pageNo = parseInt(urlInfo.query.pageNo);
            }
            if (urlInfo.query.pageSize) {
                pageSize = parseInt(urlInfo.query.pageSize);
            }
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
            break;

        case '/member/add':

            var id = urlInfo.query.id;
            var email = urlInfo.query.email;
            var password = urlInfo.query.password;
            pool.query(
                `insert into pms2_member(mid, email, pwd)
                values(?, ?, password(?))`,
                [id, email, password],
                function(err, results){
                    if (err) {
                        res.end('DB insert 중 예외 발생!');
                        return;
                    }
                res.end('정상 입력되었습니다!');
            });
            break;

        case '/member/update':

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
            break;

        case '/member/delete':

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
            break;

        default:

            res.end('해당 url 이 존재하지 않습니다.');
            break;
    }
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});
