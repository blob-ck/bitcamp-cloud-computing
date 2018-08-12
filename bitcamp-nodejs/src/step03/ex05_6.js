// 주제 : express 사용하기
// urlInfo 는 express 에서 req에 담아놓았다.
// => 요청 핸들러를 호출하는 코드 분리


const mysql = require('mysql');
const express = require('express');
const app = express();
var pool = mysql.createPool({
    connectionLimit: 10,
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});



//GET 요청 처리하기
app.get('/member/list', (req, res) => {
    var pageNo = 1;
    var pageSize = 3;
    if (req.query.pageNo) {
        pageNo = parseInt(req.query.pageNo);
    }
    if (req.query.pageSize) {
        pageSize = parseInt(req.query.pageSize);
    }
    var startIndex = (pageNo - 1) * pageSize;
    res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});
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

app.get('/member/add', (req, res) => {
    // res.write(`id=${req.query.id}\n`);
    // res.write(`email=${req.query.email}\n`);
    // res.write(`password=${req.query.password}\n`);
    // res.end();
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    pool.query(
        `insert into pms2_member(mid, email, pwd)
        values(?, ?, password(?))`,
        [req.query.id, req.query.email, req.query.password],
        function(err){
            if (err) {
                res.end('DB insert 중 예외 발생!');
                return;
            }
            res.end('정상 입력되었습니다!');
    });
});
    
app.get('/member/update', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    pool.query(
        `update pms2_member set email=?, pwd=password(?)
        where mid=?`,
        [req.query.email, req.query.password, req.query.id],
        function(err){
            if (err) {
                res.end('DB update 중 예외 발생!');
                return;
            } 
             res.end('정상 변경되었습니다!');
    });
});

app.get('/member/delete', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    pool.query(
        `delete from pms2_member
        where mid=?`,
        [req.query.id],
        function(err){
            if (err) {
                res.end('DB 삭제 중 예외 발생!');
                return;
            } 
            res.end('정상 삭제하였습니다!');
    });
});


//이렇게 조건문 없이도 쉽게 기능을 추가할 수 있다.
app.get('/member/hello', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write(`${req.query.name}님 안녕하세요`);
    res.end();
});



app.listen(8000, () => {
    console.log('서버 실행 중!');
});