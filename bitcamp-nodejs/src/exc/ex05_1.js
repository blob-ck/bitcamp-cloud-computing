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
    
var express = {
        requestMap: {},
        add(urlpath, handler) {
            this.requestMap[urlpath] = handler;
        },
        getHandler(urlpath) {
            return this.requestMap[urlpath];
        },
        
        listen(port, callback) {
            var mapper = this;
            const server = http.createServer((req, res) => {
                
                var urlInfo = url.parse(req.url, true);
                res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});
                
                if (urlInfo.pathname === '/favicon.ico') {
                    res.end();
                    return;
                }
                
                var handler = mapper.getHandler(urlInfo.pathname);
                
                if (handler) {
                    try{
                        handler(urlInfo, req, res);
                    }catch(err) {
                        res.end('실행 중 오류 발생!');
                    }
                } else {
                    res.end('해당 URL을 찾을 수 없습니다.');
                    return;
                }
            });
            
            server.listen(8000, callback);
        }
};




express.add('/member/list' ,(urlInfo, req, res) => {
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
express.add('/member/add' ,(urlInfo, req, res) => {
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
express.add('/member/update' ,(urlInfo, req, res) => {
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
express.add('/member/remove' ,(urlInfo, req, res) => {
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
