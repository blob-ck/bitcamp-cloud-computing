'use strict'

$('#loginBtn').click(() => {
    
    //console.log($('#fSaveEmail').is(':checked'));
    //console.log($('#fSaveEmail').prop('checked'));
    $.post(
            `${serverApiAddr}/json/auth/signIn`, 
            {
                email: $('#fEmail').val(),
                saveEmail: $('#fSaveEmail').prop('checked'),
                password: $('#fPassword').val()
            }, 
            (result) => {
                if (result.status === 'success') {
                    location.href = 'main.html?';
                }
            }, 
            'json')
            .fail((e) => {
                alert('로그인 중 오류 발생!');
                console.log(e);
            });
});