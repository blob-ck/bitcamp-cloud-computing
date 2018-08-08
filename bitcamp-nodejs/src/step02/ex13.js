// 주제: 데이터베이스 프로그래밍 - INSERT 실행 후 auto_increment 값 알아내기

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

var title = '제목입니다';
var content = '내용입니다';

con.query(
        `insert into pms2_board(titl, cont, cdt)
         values(?, ?, now())`,
         [title, content],
         function(err, result){
    if (err) throw err;
    console.log("삽입 성공!!");
    console.log(result);
    
    //INSERT 실행 후 auto_increment 값 알아내기
    console.log(result.insertId);
});

con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});


console.log('select 실행!');
