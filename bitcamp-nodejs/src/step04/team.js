// '/team/*' 요청을 처리할 라우터 만들기
const express = require('express');
const router = express.Router();

//어느 라우터가 실행중인지 확인용
router.use((req, res, next) => {
    console.log('team.js 실행');
    next();
});

// team/list 요청이 들어왔을때 호출될 메서드
router.get('/list', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write('팀 목록입니다');
    res.end();
});

// team/view 요청이 들어왔을때 호출될 메서드
router.get('/view', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
    res.write('팀 상세정보입니다');
    res.end();
});

// 주의!!!!!!
// 라우터 쓸 때는 module 을 생략하면 못찾는다@!!!!
module.exports = router;