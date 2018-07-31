"use strict"

var data = null;

var {id, page, size} = $.parseQuery(location.href);

if(id == undefined){
    $('.viewform').css('display', 'none');
    $(eId).removeAttr('readonly');
    
} else {
    $('.newform').css('display', 'none');
    
    $.getJSON(serverApiAddr + `/json/member/view/${id}`,
            function(result){
                data = result;
                console.log('id = ' ,data.member.id);
                console.log('id = ' ,data.member.id);
                if (data.member == null) {
                    alert('아이디가 무효합니다.');
                    location.href = 'list.html';
                    return;
                }
                $(eId).val(data.member.id);
                $(eEmail).val(data.member.email);
                console.log($(eId).val());
                console.log($(eEmail).val());
            });
}


$(eListBtn).click(function() {
    
    if(page) {
        location.href = `list.html?page=${page}&size=${size}`;
    }else {
        location.href = 'list.html';
    }
});

$(eAddBtn).click(function() {
    
    $.post(serverApiAddr + '/json/member/add',
            {
                id: $(eId).val(),
                email: $(eEmail).val(),
                password: $(ePassword).val()
            },
            function(data) {
                if(data.status == 'success') {
                    location.href = 'list.html';
                }else {
                    alert('이미 존재하는 아이디입니다.');
                    console.log(data.error);
                }
            },
            'json');
});




$(eUpdateBtn).click(function() {
    $.post(serverApiAddr + '/json/member/update',
            {
                id: $(eId).val(),
                email: $(eEmail).val(),
                password: $(ePassword).val()
            },
            function(data) {
                console.log(data);
                if(data.status == 'success') {
                    location.href = `list.html?page=${page}&size=${size}`;
                }else {
                    alert('변경 오류입니다.');
                 console.log(data.error);
                }
            },
            'json');
});



$(eDeleteBtn).click(function() {
    
    $.getJSON(serverApiAddr + `/json/member/delete?id=${eId.value}`,
            function(data) {
                if(data.status == 'success') {
                    location.href = 'list.html';
                }else {
                    alert('삭제 오류입니다.');
                    console.log(data.error);
                }
            });
});
