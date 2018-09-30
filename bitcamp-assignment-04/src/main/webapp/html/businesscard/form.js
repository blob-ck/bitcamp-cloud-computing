'use strict'

var selectedCardNo = 0;
var formState = 'view';

$('.edit-ctrl').css('display', 'none');



// list.js의 trigger이벤트에서 넘어온 파라미터 활용하여 명함 내용 출력하기
/*$('#name-list').on('click', 'li', (e) => {
    console.log($(e.target).attr('data-no'), $(e.target).text());
    var no = $(e.target).attr('data-no');
    
    $(document.body).trigger('show.detail', [no]);
});*/
//각 화면이 index.html 에서 모이므로, body에 이벤트를 걸면 공유하게 된다.
//list.js에서 body에 show.detail이란 이름으로 이벤트를 발생시켰다.
// => show.detail 이벤트가 실행됬을 때 실행할 함수 등록
$(document.body).on('show.detail', (e, no) => {
    $('.form-control').addClass('form-control-plaintext')
        .removeClass('form-control');
    $('.edit-ctrl').css('display', 'none');
    $('.view-ctrl').css('display', '');

    formState = 'view';

    $.getJSON(`${serverApiAddr}/json/businesscard/${no}`, (result) => {
        if (result.status !== 'success') {
            selectedCardNo = 0;
            return;
        }
        
        selectedCardNo = no;
        
        $('#f-name').val(result.data.name);
        $('#f-mobile-tel').val(result.data.mobileTel);
        $('#f-tel').val(result.data.tel);
        $('#f-fax').val(result.data.fax);
        $('#f-email').val(result.data.email);
        $('#f-memo').val(result.data.memo);
    })
})

$('#add-btn').click(() => {
    
    formState = 'add';
    //reset()은 비동기 이므로, 변수로 상태를 주어 조건식으로 뭔가 할 때 주의할 것!!
    $('#reset-btn').trigger('click', ['add']);
    
    $('.form-control-plaintext')
        .addClass('form-control')
        .removeClass('form-control-plaintext');
    
    $('.edit-ctrl').css('display', '');
    $('.view-ctrl').css('display', 'none');
});

$('#update-btn').click(() => {
    
    formState = 'update';
    
    $('.form-control-plaintext')
        .addClass('form-control')
        .removeClass('form-control-plaintext');

    $('.edit-ctrl').css('display', '');
    $('.view-ctrl').css('display', 'none');
});

$('#delete-btn').click(() => {
    $.getJSON(`${serverApiAddr}/json/businesscard/delete`, {
        'no': selectedCardNo
    }, (result) => {
        //list.js에 있는 이벤트 리스너를 실행시켜 loadList를 실행한다.
        //비동기로 명함목록을 가져오므로, 
        //명함 추가나 삭제 작업시 새로고침하여 DB정렬순대로 다시 출력할 필요가 있다.
        //하나하나 타겟잡아 화면에서 삭제하여 보여주는건 너무 비효율적이다.
        //새로고침 한방이면 끝날걸...
        $(document.body).trigger('refresh.list');
    })
});

$('#reset-btn').click((e, action) => {
    
    $('.form-control')
        .addClass('form-control-plaintext')
        .removeClass('form-control');
    $('.view-ctrl').css('display', '');
    $('.edit-ctrl').css('display', 'none');
    
    // '추가' 버튼을 클릭한 후 리셋 할 때는 
    // 기존의 명함 정보를 로딩해서는 안된다. 그냥 빈 입력폼을 출력해야 한다.
    // 만약 사용자가 취소 버튼을 눌렀다면,
    // 그때는 이전 명함 정보를 로딩해야 한다.
    if (selectedCardNo > 0 && action !== 'add') {
        $(document.body).trigger('show.detail', [selectedCardNo]);
        formState = 'view';
    }
});

$('#ok-btn').click(() => {
    if (formState === 'add') {
        $.post(
                `${serverApiAddr}/json/businesscard/add`, 
                {
                    name: $('#f-name').val(),
                    mobileTel: $('#f-mobile-tel').val(),
                    tel: $('#f-tel').val(),
                    fax: $('#f-fax').val(),
                    email: $('#f-email').val(),
                    memo: $('#f-memo').val()
                }, 
                (result) => {
                    if (result.status !== 'success') {
                        return;
                    }
                    $(document.body).trigger('refresh.list');
                }, 
                'json')
                .fail((e) => {
                    alert('서버 요청 중 오류 발생!');
                    console.log(e);
                });
    } else if (formState === 'update') {
        $.post(`${serverApiAddr}/json/businesscard/update`, {
            'no': selectedCardNo,
            'name': $('#f-name').val(),
            'mobileTel': $('#f-mobile-tel').val(),
            'tel': $('#f-tel').val(),
            'fax': $('#f-fax').val(),
            'email': $('#f-email').val(),
            'memo': $('#f-memo').val()
        }, (result) => {

            if (result.status !== 'success') return;
            
            $(document.body).trigger('refresh.list');
            
        }, 'json')
        .fail(() => {
            alert('서버 요청 중 오류 발생!')
        });
    }
});
