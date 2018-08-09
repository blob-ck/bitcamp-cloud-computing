// 주제 : DAO 로 분리


const mysql = require('mysql');
const express = require('express');
const router = express.Router();
const boarddao = require('./boarddao');

var pool = mysql.createPool({
    connectionLimit: 10,
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});

boarddao.setConnectionPool(pool);

//GET 요청 처리하기
router.get('/list', (req, res) => { //OK
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    var pageNo = 1;
    var pageSize = 3;
    if (req.query.pageNo) {
        pageNo = parseInt(req.query.pageNo);
    }
    if (req.query.pageSize) {
        pageSize = parseInt(req.query.pageSize);
    }
    
    boarddao.list(pageNo, pageSize, (err, results) => {
        if (err) {
            res.end('DB 조회 중 예외 발생!');
            return;
        } 
        console.log(results);
        for (var row of results) {
                res.write(`${row.bno} : ${row.titl} : ${row.cdt}\n`);
        }
        res.end();
    });
});

router.get('/view', (req, res) => { //OK
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    // 파라미터 : bno
    boarddao.view(req.query, (err, results) => {
        if (err) {
            res.end('DB 조회 중 예외 발생!');
            return;
        } 
        // bno,titl,cont,cdt 
        res.write(`${results[0].bno} : ${results[0].titl}\n`);
        res.end();
    });
});


router.get('/add', (req, res) => { //OK
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    boarddao.add(req.query, (err, results) => {
        if (err) {
            res.end('DB insert 중 예외 발생!');
            return;
        }
        res.end('정상 입력되었습니다!');
    });
});
    
router.get('/update', (req, res) => { //OK
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    boarddao.update(req.query, (err, result) => {
        if (err) {
            res.end('DB update 중 예외 발생!');
            return;
        } 
        res.end('정상 변경되었습니다!');
    });
});

router.get('/delete', (req, res) => { //OK
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    boarddao.remove(req.query, (err, result) => {
        if (err) {
            res.end('DB 삭제 중 예외 발생!');
            return;
        } 
        res.end('정상 삭제하였습니다!');
    });
});

module.exports = router;