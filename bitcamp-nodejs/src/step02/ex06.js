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
    
    //연결에 성공했을 때만 SQL을 실행하라고 예약할  수 있다.
    con.query('select * from pms2_member', function(err, results){
        if (err) throw err;
        console.log("결과를 가져왔다!");
        
        //results 파라미터에 결과가 들어있다.
        for (var row of results) {
            console.log(row.email, row.mid, row.pwd);
        }
    });

    // end실행 예약을 SQL 실행 후 하도록 순서를 준다.
    con.end(function(err) {
        if (err) throw err;
        console.log('연결 끊었음!~');
    });
});


console.log('select 실행!');
