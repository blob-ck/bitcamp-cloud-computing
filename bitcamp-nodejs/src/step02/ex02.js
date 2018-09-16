// 주제: 데이터베이스 프로그래밍 - DB 연결하기

// https://www.w3schools.com/nodejs/nodejs_mysql.asp

const mysql = require('mysql');

console.log(mysql);
/*
{   createConnection: [Function: createConnection],
    createPool: [Function: createPool],
    createPoolCluster: [Function: createPoolCluster],
    createQuery: [Function: createQuery],
    escape: [Function: escape],
    escapeId: [Function: escapeId],
    format: [Function: format],
    raw: [Function: raw] }
*/


// 1) DBMS와 연결을 수행할 connection 객체 생성
var con = mysql.createConnection({
    host: "13.209.48.23", // host가 localhost면 생략 가능
    //port: "3306", // port가 3306이면 생략 가능
    database: 'studydb',
    user: "study",
    password: "1111"
});

// 2) 연결 객체를 통해 DBMS와 연결한다.
// => connect(연결완료후 호출될 함수); ==> 비동기
con.connect(function(err) {
    
    //만약 연결에 실패하면 err파라미터에 값이 존재할 것이다.
    if (err) throw err;
    console.log("연결했음!~!!");
});

// 3) DBMS와의 연결 해제를 예약한다.
// => 다음 소스를 실행하는 것은 connect()를 통해 등록한 함수 호출이 끝나면
//    예약 작업이 실행될 것이다.
//    즉, 연결 해제하라고 예약한 작업이 실행될 것이다.
// => 비동기
con.end(function(err) {
    if (err) throw err; // 만약 연결을 끊다가 에러 발생시 예외를 던진다
    console.log('연결 끊었음!~');
});
// 지금 당장 연결을 끊으라는게 아니다.
// => 비동기 함수임을 알 수 있다.


console.log('연결 테스트~');

// 연결 테스트~
// 연결했음!~!!
// 연결 끊었음!~
// => 순서로 출력될 것이다. 비동기!