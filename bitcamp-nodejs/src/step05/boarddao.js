// DAO 모듈 만들기

var pool;

exports.setConnectionPool = (connectionPool) => {
    pool = connectionPool;
};

exports.list = (pageNo = 1, pageSize = 3, handler) => { //OK

    var startIndex = (pageNo - 1) * pageSize;
    pool.query(
        `select bno, titl, cdt 
        from pms2_board
        order by bno desc limit ?, ?`,
        [startIndex, pageSize],
        function(err, results){
            handler(err, results);
    });
};

exports.view = (data, handler) => { // OK
    pool.query(
        'select bno, titl, cont, cdt from pms2_board where bno=?',
        [data.bno],
        function(err, results){
            handler(err, results);
    });
};

exports.add = (data, handler) => { // OK
    pool.query(
        `insert into pms2_board(titl,cont,cdt) 
        values(?, ?, now())`,
        [data.title, data.content],
        function(err, result){
            handler(err, result);
    });
};

exports.update = (data, handler) => { //OK
    pool.query(
        `update pms2_board set 
            titl=?, 
            cont=?, 
            cdt=now()
        where bno=?`,
        [data.title, data.content, data.bno],
        function(err, result){
            handler(err, result);
    });
};

exports.remove = (data, handler) => { //OK
    pool.query(
        `delete from pms2_board
        where bno=?`,
        [data.bno],
        function(err, result){
            handler(err, result);
    });
};
