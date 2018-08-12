// 요청 핸들러를 관리 + 호출 하는 모듈

const http = require('http');
const url =require('url');

module.exports = function() {
    return { // 이렇게 함수자체를 먼저 리턴하고, 각 서버객체를 만들 때 함수를 실행하면 된다
        requestHandlerMap: {},
        add(url, handler) {
            this.requestHandlerMap[url] = handler;
        },
        getHandler(url) {
            return this.requestHandlerMap[url];
        },
        listen(port, callback) {
            var mapper = this;
            const server = http.createServer((req, res) => {
                
                var urlInfo = url.parse(req.url, true);
                res.writeHead(200, {'Content-Type': 'text/html;charset=UTF-8'});
                
                if (urlInfo.pathname === '/favicon.ico') {
                    res.end();
                    return;
                }
                
                //애로우 함수 내부에서 this를 쓰게되면 
                //여기선 global객체, 브라우저에선 window 객체를 가리킨다
                //그러므로 애로우함수를 쓰기 전에 this를 임시변수에 담아 쓰도록 한다.
                var handler = mapper.getHandler(urlInfo.pathname);
                
                if (handler) {
                    try{
                        handler(urlInfo, req, res);
                    } catch(err) {
                        res.end('실행 중 오류 발생!');
                    }
                } else {
                    res.end('해당 URL을 찾을 수 없습니다.');
                    return;
                }
            });
            
            server.listen(port,callback);
        }
    }
}



