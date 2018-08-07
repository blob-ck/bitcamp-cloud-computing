// 주제: 데이터베이스 프로그래밍 - DELETE 실행

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

// SQL문을 만들 때 변수의 값을 직접 넣어서 만드는 경우
// 해커의 공격에 노출될 수 있다.
// 외부 사용자가 아래처럼 입력하면
// 조건이 무조건 참이 되어 데이터 전부가 삭제되어 버린다.
// var mid = "user002' or 1=1 or ''='"; 
// 그래서 SQL문에 변수의 값을 직접 삽입하는 방법을 사용해선 안된다.
// ====> 해결책
// ====> in-parameter 방법을 사용한다.
con.query(
        `delete from pms2_member
         where mid='${mid}'`
         //where mid='user002' or 1=1 or ''=''` //이렇게 들어가면 모든 데이터 날림
        , function(err, results){
    if (err) throw err;
    console.log("삭제 성공!");
});

con.end(function(err) {
    if (err) throw err;
    console.log('연결 끊었음!~');
});


console.log('select 실행!');
