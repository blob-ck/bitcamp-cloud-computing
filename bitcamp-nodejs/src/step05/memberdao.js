// DAO 모듈 만들기

var pool;

exports.setConnectionPool = (connectionPool) => {
    pool = connectionPool;
};

exports.list = (pageNo = 1, pageSize = 3, handler) => {

    var startIndex = (pageNo - 1) * pageSize;
    //this를 사용하여 이 객체의 pool이란걸 명시해야한다.
    //지금은 클로저 pool을 사용하므로 this를 뺐다.
    pool.query(
        'select * from pms2_member limit ?, ?',
        [startIndex, pageSize],
        function(err, results){
            handler(err, results);
    });
};

exports.add = (data, handler) => {
    pool.query(
        `insert into pms2_member(mid, email, pwd)
        values(?, ?, password(?))`,
        [data.id, data.email, data.password],
        function(err, result){
            handler(err, result);
    });
};

exports.update = (data, handler) => {
    pool.query(
        `update pms2_member set email=?, pwd=password(?)
        where mid=?`,
        [data.email, data.password, data.id],
        function(err, result){
            handler(err, result);
    });
};

exports.remove = (data, handler) => {
    pool.query(
        `delete from pms2_member
        where mid=?`,
        [data.id],
        function(err, result){
            handler(err, result);
    });
};

exports.hello = (data, handler) => {
    handler(data);
};