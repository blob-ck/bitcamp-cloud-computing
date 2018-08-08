// 주제: 데이터베이스 프로그래밍 - UPDATE 실행

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

var dscrt = '';
var max_qty = '';
var sdt = '';
var edt = '';
var name = '';

con.query(
        `update pms2_team set 
            dscrt=?, 
            max_qty=?, 
            sdt=?, 
            edt=? 
        where name=?`,
         [dscrt, max_qty, sdt, edt, name], 
         function(err, results){
    if (err) throw err;
    console.log("변경 성공!");
});

con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});


console.log('select 실행!');
