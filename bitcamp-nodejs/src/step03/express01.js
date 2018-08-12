// 요청 핸들러를 관리하는 모듈

module.exports = {
    reqMap: {},
    add(url, handler) {
        this.reqMap[url] = handler;
    },
    getHandler(url) {
        return this.reqMap[url];
    }
}
