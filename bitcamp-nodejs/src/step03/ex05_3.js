// 주제 : 여러 개의 요청 처리하기 - 조건문을 없애고 url과 함수를 자동 연결하기
// => 요청 핸들러(요청이 들어왔을때 호출되는 함수)를 좀 더 관리학 쉽게 분리한다.

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


const express = {
    reqMap: {},
    add(url, handler) {
        this.reqMap[url] = handler;
    },
    getHandler(url) {
        return this.reqMap[url];
    }
}



const server = http.createServer((req, res) => {
    
    var urlInfo = url.parse(req.url, true);
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});
    
    if (urlInfo.pathname === '/favicon.ico') {
        res.end();
        return;
    }
    
    var handler = express.getHandler(urlInfo.pathname);
    
    if (handler) {
        try{
            handler(urlInfo, req, res);
        } catch(err) {
            res.end('실행 중 오류 발생!');
        }
    } else {
        res.end('해당 URL을 찾을 수 없습니다.');
        return;
    }
});

server.listen(8000,() => {
    console.log('리슨~ 서버 시작됨~')
});


// 객체를 express에 저장할 때 url을 key로 저장하고 있다.
express.add('/member/list', (urlInfo, req, res) => {
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
});

express.add('/member/add', (urlInfo, req, res) => {
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
});

express.add('/member/update', (urlInfo, req, res) => {
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
});

express.add('/member/delete', (urlInfo, req, res) => {
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
});


//이렇게 조건문 없이도 쉽게 기능을 추가할 수 있다.
express.add('/member/hello', (urlInfo, req, res) => {
    res.write(`${urlInfo.query.name}님 안녕하세요`);
    res.end();
});