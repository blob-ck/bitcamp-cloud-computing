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
// query(sql, 실행  후 호출될 함수);
// => SQL을 바로 실행하는 것이 아니라 실행을 예약한다.
// => DBMS와 연결되면 이렇게 예약한 SQL문을 실행할 것이다.
// => 문제는 연결 오류에 상관없이 무조건 예약한다는 것이다.
//    connect()에 등록한 함수에서 예외를 던지지 않으면 ==> if (err) throw err;
//    이 SQL문을 실행한다.
con.query('select * from pms2_member', function(err, results){
    if (err) throw err;
    console.log("결과를 가져왔다!");
    
    //results 파라미터에 결과가 들어있다.
    console.log(typeof results);
    for (var row of results) {
        console.log(row.mid, '    ', row.email, '    ', row.pwd);
    }
});

//예약 3
con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});

console.log('select 실행!');
