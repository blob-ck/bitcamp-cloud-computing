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

});

// 이 예제에서는 연결 완료 후 END를 먼저 실행하라고 예약했기 때문에
// connect()에 등록한 함수가 호출되어 SQL을 예약하더라도
// end보다 이후에 실행되기 때문에
// SQL 실행 오류가 발생한다.
// => 해결책은 예약순서를 정하는거다.
con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});

console.log('select 실행!');
