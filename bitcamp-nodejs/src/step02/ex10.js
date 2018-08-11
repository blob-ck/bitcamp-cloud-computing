// 주제: 데이터베이스 프로그래밍 - in-parameter 사용하기

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

var mid = 'user002';

// ====> 해결책
// ====> in-parameter 방법을 사용한다.
// in-parameter 방식에서는 사용자가 입력한 값으로 SQL을 만드는 것이 아니기 때문에
// SQL 조작이 불가능하다.
con.query(
        `delete from pms2_member
         where mid=?`, // ? 를 in-parameter 라 부른다
         [mid],        // in-parameter 의 개수만큼 배열에 값을 담으면 된다.
        function(err, results){
    if (err) throw err;
    console.log("삭제 성공!");
});

con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});


console.log('select 실행!');
