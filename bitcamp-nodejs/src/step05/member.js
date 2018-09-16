// 주제 : DAO 로 분리


const mysql = require('mysql');
const express = require('express');
const router = express.Router();
const memberdao = require('./memberdao');

var pool = mysql.createPool({
    connectionLimit: 10,
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});

memberdao.setConnectionPool(pool);

//GET 요청 처리하기
router.get('/list', (req, res) => {
    var pageNo = 1;
    var pageSize = 3;
    if (req.query.pageNo) {
        pageNo = parseInt(req.query.pageNo);
    }
    if (req.query.pageSize) {
        pageSize = parseInt(req.query.pageSize);
    }
    
    //비동기이므로, 결과를 받아왔을때 실행할 함수를 함께 등록해줘야한다.
    //바로  memberdao에서 리턴하면 결과값을 비동기로 가져오기도 전에 리턴해버리므로 주의!
    memberdao.list(pageNo, pageSize, (err, results) => {
        if (err) {
            res.end('DB 조회 중 예외 발생!');
            return;
        } 
        // for (var row of results) {
        //         res.write(`${row.mid} ${row.email}\n`);
        // }
        // res.end();
        console.log(results);
        res.render('list', {list : results});
    });
});

router.get('/add', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    memberdao.add(req.query, (err, results) => {
        if (err) {
            res.end('DB insert 중 예외 발생!');
            return;
        }
        res.end('정상 입력되었습니다!');
    });
});
    
router.get('/update', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    memberdao.update(req.query, (err, result) => {
        if (err) {
            res.end('DB update 중 예외 발생!');
            return;
        } 
        res.end('정상 변경되었습니다!');
    });
});

router.get('/delete', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    memberdao.remove(req.query, (err, result) => {
        if (err) {
            res.end('DB 삭제 중 예외 발생!');
            return;
        } 
        res.end('정상 삭제하였습니다!');
    });
});

module.exports = router;