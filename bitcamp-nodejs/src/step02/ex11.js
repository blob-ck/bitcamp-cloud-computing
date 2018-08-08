// 주제: 데이터베이스 프로그래밍 - 커넥션 풀 사용

// https://www.npmjs.com/package/mysql#pooling-connections

const mysql = require('mysql');

var pool = mysql.createPool({
    connectionLimit: 10,
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});

pool.getConnection(function(err, con) {
    if (err) throw err;
    console.log("Connection 객체 얻음!!");
    
    //연결에 성공했을 때만 SQL을 실행하라고 예약할  수 있다.
    con.query('select * from pms2_member', function(err, results){
        if (err) throw err;
        console.log("결과를 가져왔다!");
        
        //results 파라미터에 결과가 들어있다.
        for (var row of results) {
            console.log(row.mid, row.email, row.pwd);
        }

        // 커넥션풀에 연결객체를 반납한다. 책 대여했다가 반납했다고 생각하면 됨
        con.release();

        //프로그램을  종료하고 싶다면 작업이 끝난 후 커넥션풀의 모든 커넥션을 닫아야 한다.
        //보통은 서버로서 실행하다가 종료할 때 커넥션 풀을 닫는다.
        //커넥션 풀을 닫지 않으면 Node.js의 메인 쓰레드가 종료되지 않는다.
        //물론 서버로 실행할 때는 당연히 그래야 하지만 
        //이 예제에서처럼 간단히 사용하려면 다음과 같이 커넥션풀을 닫는다.
        pool.end(console.log('예제 종료!'));
            //pool.end() 는 종료 예약이 아니라 종료를 해버리므로, 
            //바깥에 선언해버리면 비동기로 실행한 쿼리가 정상실행되지 못한다.
    });
});


console.log('select 실행!');
