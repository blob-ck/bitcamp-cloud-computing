// 주제: 데이터베이스 프로그래밍 - SELECT 실행

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


// query(sql, 실행 후 호출될 함수);
con.query('select * from pms2_member', function(err){
    if (err) throw err;
    console.log("결과를 가져왔다!");
});

con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});

console.log('select 실행!');
