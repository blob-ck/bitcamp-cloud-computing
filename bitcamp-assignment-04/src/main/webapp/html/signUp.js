'use strict'

$('#addBtn').click(() => {
    $.post(
            `${serverApiAddr}/json/member/signUp`, 
            {
                email: $('#fEmail').val(),
                name: $('#fName').val(),
                password: $('#fPassword').val()
            }, 
            (result) => {
                if (result.status === 'success') {
                    location.href = 'signIn.html';
                } else {
                    alert('회원 가입 실패!');
                    console.log(result.message);
                }
            }, 
            'json')
            .fail((e) => {
                alert('회원 가입 중 오류 발생!');
                console.log(e);
            });
});