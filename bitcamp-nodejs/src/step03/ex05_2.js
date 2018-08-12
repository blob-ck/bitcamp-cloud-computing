// 주제 : 여러 개의 요청 처리하기 - 각 요청을 함수로 분리하기
// => 각 요청을 처리하는 코드를 별도의 함수로 분리하면 관리가 편하다.

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

function list(urlInfo, req, res) {
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
};

function add(urlInfo, req, res) {
    pool.query(
        `insert into pms2_member(mid, email, pwd)
        values(?, ?, password(?))`,
        [urlInfo.query.id, urlInfo.query.email, urlInfo.query.password],
        function(err, results){
            if (err) {
                res.end('DB insert 중 예외 발생!');
                return;
            }
        res.end('정상 입력되었습니다!');
    });
};

function update(urlInfo, req, res) {
    pool.query(
        `update pms2_member set email=?, pwd=password(?)
        where mid=?`,
        [urlInfo.query.email, urlInfo.query.password, urlInfo.query.id],
        function(err, results){
            if (err) {
                res.end('DB update 중 예외 발생!');
                return;
            } 
        res.end('정상 변경되었습니다!');
    });
};

function remove(urlInfo, req, res) {
    pool.query(
        `delete from pms2_member
        where mid=?`,
        [urlInfo.query.id],
        function(err, results){
            if (err) {
                res.end('DB 삭제 중 예외 발생!');
                return;
            } 
        res.end('정상 삭제하였습니다!');
    });
};



const server = http.createServer((req, res) => {
    
    var urlInfo = url.parse(req.url, true);
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});
    
    if (urlInfo.pathname === '/favicon.ico') {
        res.end();
        return;
    }
    
    switch(urlInfo.pathname) {
        case '/member/list':
            list(urlInfo, req, res);
            break;
            
        case '/member/add':
            add(urlInfo, req, res);
            break;
            
        case '/member/update':
            update(urlInfo, req, res);
            break;
            
        case '/member/delete':
            remove(urlInfo, req, res);
            break;

        default:
            res.end('해당 url 이 존재하지 않습니다.');
            break;
    }
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});
