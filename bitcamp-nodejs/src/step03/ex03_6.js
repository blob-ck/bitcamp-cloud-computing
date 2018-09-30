//2018-08-09 목 강1
// 주제 : 요청정보 다루기 - POST 요청 다루기
const http = require('http');
const url =require('url');
const querystring = require('querystring');

const server = http.createServer((req, res) => {
    var urlInfo = url.parse(req.url, true);

    if (urlInfo.pathname !== "/") {
        res.end();
        return;
    }
    
    res.writeHead(200, {'Content-Type': 'text/plain;charset=UTF-8'});
        //console.log(req);
    if (req.method !== 'POST') {
        res.end('GET 요청을 지원하지 않습니다');
        return;
    }
    
    //https://nodejs.org/dist/latest-v10.x/docs/api/stream.html#stream_class_stream_readable
    //POST 요청 데이터 처리
    // 1) data 이벤트 처리
    // => 클라이언트가 보낸 데이터를 읽을 때마다 발생하는 이벤트다.
    //(주의!!!) 
    // !! 클라이언트가 보낸 데이터를 모두 읽었을 때 발생하는 이벤트가 아니다!!!
    // !! 즉, 클라이언트가 보낸 데이터를 한 번에 모두 읽는게 아니라
    // !! 일부 읽을 때 마다 알려주는 방식으로 동작한다.
    // => 따라서 데이터 일부를 읽었으면 'data' 이벤트가 발생하고, 
    //    그 이벤트에 등록된 함수가 있다면 호출해준다.
    // => 리스너(이벤트 핸들러)에 넘어오는 파라미터는 클라이언트가 보낸
    //    데이터의 일부이다.

    //Node.js 는 monolitic 서비스보다 소규모의 작업을 처리하기 위한 목적이 크다.
    
    var data = '';
    req.on('data', (chunk) => {
        data += chunk; // 이렇게 해야 데이터를 계속해서 덧붙여 전체 데이터를 받아간다.
        // console.log(`Received ${chunk.length} bytes of data.`);
        // console.log(data);
    });

    //데이터를 모두 읽었을 때 응답을 완료해야 한다.
    //위의 'data' event 조각모음과 한쌍이다. 데이터를 완전하게 받았을 때 실행하는 콜백이다.
    //The 'end' event is emitted when there is no more data to be consumed from the stream.
    //The 'end' event will not be emitted unless the data is completely consumed.
    req.on('end', () => {
        //읽은 데이터를 사용하기 좋게 key / value 로 분리한다.
        var params = querystring.parse(data);
        res.write(`name=${params.name}\n`);
        res.write(`age=${params.age}`);
        res.end(); // 반드시 데이터를 전부 읽어오면 응답을 완료해야한다.
        //console.log('There will be no more data.');
    });

    // 데이터를 읽을때 호출될 메서드를 동록한 후
    // 다음과 같이 바로 응답을 완료하면 안된다.
    // res.end();
});

server.listen(8000, () => {
    console.log('서버 시작됨~')
});