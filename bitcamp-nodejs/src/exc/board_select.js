// 주제: 데이터베이스 프로그래밍 - SELECT 실행

// https://www.w3schools.com/nodejs/nodejs_mysql.asp

const mysql = require('mysql');

var con = mysql.createConnection({
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});

//예약 1
con.connect(function(err) {
    if (err) throw err;
    console.log("연결했음!~!!");
});


//예약 2
// query(sql, 실행 후 호출될 함수);
// => SQL을 바로 실행하는 것이 아니라 실행을 예약한다.
// => 어차피 비동기라서 연결함수 안에 쿼리를 쓰지 않아도 되지만, 
// => 문제는 연결 오류 여부와 상관없이 무조건 함수를 예약실행한다.
var bno = '';

con.query(
        `select bno, titl, cont, cdt 
         from pms2_board`,
         [bno],
         function(err, results){
    if (err) throw err;
    console.log("결과를 가져왔다!");
    
    //results 파라미터에 결과가 들어있다.
    for (var row of results) {
        console.log(row.bno,"   ", row.titl,"   ", row.cont, "  ",row.cdt);
    }
});

//예약 3
con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});

console.log('select 실행!');
