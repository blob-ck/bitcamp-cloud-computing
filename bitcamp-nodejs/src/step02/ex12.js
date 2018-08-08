// 주제: 데이터베이스 프로그래밍 - 커넥션 풀 사용 - 커넥션 객체를 통하지 않고 직접 쿼리 날리기

// https://www.npmjs.com/package/mysql#pooling-connections

const mysql = require('mysql');

var pool = mysql.createPool({
    connectionLimit: 10,
    host: "13.209.48.23",
    database: 'studydb',
    user: "study",
    password: "1111"
});


// This is a shortcut for the 

//커넥션 객체를 사용하지 않고 pool 내장객체로 직접 쿼리
pool.query('select * from pms2_member', function(err, results){
    if (err) throw err;
    console.log("결과를 가져왔다!");
    
    for (var row of results) {
        console.log(row.mid, row.email, row.pwd);
    }
    
    //프로그램을  종료하고 싶다면 작업이 끝난 후 커넥션풀의 모든 커넥션을 닫아야 한다.
    //보통은 서버로서 실행하다가 종료할 때 커넥션 풀을 닫는다.
    //커넥션 풀을 닫지 않으면 Node.js의 메인 쓰레드가 종료되지 않는다.
    //물론 서버로 실행할 때는 당연히 그래야 하지만 
    //이 예제에서처럼 간단히 사용하려면 다음과 같이 커넥션풀을 닫는다.
    pool.end(console.log('예제 종료!'));
    //pool.end() 는 종료 예약이 아니라 종료를 해버리므로, 
    //바깥에 선언해버리면 비동기로 실행한 쿼리가 정상실행되지 못한다.
});

console.log('select 실행!');
// pool.getConnection() -> connection.query() -> connection.release() code flow. 
// Using pool.getConnection() is useful to share connection state for subsequent queries. 
// This is because two calls to pool.query() may use two different connections and run in parallel.

//ex11.js 와 ex12.js 의 비교
// => ex12.js 는 connectionPool 에 대해 바로 쿼리를 호출할 수 있어 코드가 간결하다
// => ex12.js 의 단점은 SQL문 하나를 호출할 때마다 새 커넥션을 만들어(꺼내) 사용하기 때문에
//            여러 작업을 수행할 경우 질의마다 커넥션을 만들어(꺼내)야 한다.

// => ex11.js 는 ex12.js 보다 커넥션을 얻는 절차를 명시하고 있어 코드가 늘어나지만,
// =>         일단 커넥션을 풀에서 꺼내오면 여러 번 사용할 수 있다.
// =>         여러 테이블에 조인해서 결과를 가져올 경우 11이 더 편리함.
// =>         트랜젝션 관리도 11버전이 더 편리함?


// 단타성 질의는 12로, 여러 작업이 필요한 경우는 11로