// 주제 : 코드를 모듈로 분리한다. 2
// => 요청 핸들러를 호출하는 코드 분리


const mysql = require('mysql');
const express = require('./express02.js');
const app = express(); // 각각의 객체를 만들기 위해서 함수를 리턴받은 뒤 객체생성시 함수실행

var pool = mysql.createPool({
    connectionLimit: 10,
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});



// 객체를 express에 저장할 때 url을 key로 저장하고 있다.
app.add('/member/list', (urlInfo, req, res) => {
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

app.add('/member/add', (urlInfo, req, res) => {
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

app.add('/member/update', (urlInfo, req, res) => {
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

app.add('/member/delete', (urlInfo, req, res) => {
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
app.add('/member/hello', (urlInfo, req, res) => {
    res.write(`${urlInfo.query.name}님 안녕하세요`);
    res.end();
});



app.listen(8000, () => {
    console.log('서버 실행 중!');
});