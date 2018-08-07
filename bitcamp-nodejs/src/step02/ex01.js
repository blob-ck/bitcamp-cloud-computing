// 주제: 데이터베이스 프로그래밍

//    DBMS와 연동하여 데이터를 처리하려면 
//    먼저 DBMS와 연결할 수 있는 모듈을 가져와야 한다.

// 0) package.json 파일 생성
//    > npm init

// 1) 모듈 설치
//    > npm install 모듈명
//    > npm install mysql

// 2) 모듈을 사용하여 프로그래밍
//  const mysql = require('mysql');

const mysql = require('mysql');

console.log(mysql);
/*
{ createConnection: [Function: createConnection],
    createPool: [Function: createPool],
    createPoolCluster: [Function: createPoolCluster],
    createQuery: [Function: createQuery],
    escape: [Function: escape],
    escapeId: [Function: escapeId],
    format: [Function: format],
    raw: [Function: raw] }
*/