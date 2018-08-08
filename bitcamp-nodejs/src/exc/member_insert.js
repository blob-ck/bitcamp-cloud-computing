// 주제: 데이터베이스 프로그래밍 - INSERT 실행

// https://www.w3schools.com/nodejs/nodejs_mysql.asp

const mysql = require('mysql');

var con = mysql.createConnection({
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});

con.connect(function(err) {
    if (err) throw err;
    console.log("연결했음!~!!");
});

var email = 'user002@test.com';
var mid = 'user002';
var pwd = '1111';

con.query(
        `insert into pms2_member(email, mid, pwd)
         values(?, ?, password(?))`,
         [email, mid, pwd],
         function(err, results){
    if (err) throw err;
    console.log("삽입 성공!!");
});

con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});


console.log('select 실행!');
