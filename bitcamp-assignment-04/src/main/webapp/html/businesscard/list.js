'use strict'
var liTemplateSrc = $('#li-template').text();
var template = Handlebars.compile(liTemplateSrc);

loadList();


//동적으로 태그를 추가할 때(데이터를 불러와서 화면에 붙여넣을 때)
//$('#name-list').html(html);
//$('#name-list li').click();
// 이렇게 만든다음 이벤트 등록해도 되지만, 아래 방식을 추천한다.
$('#name-list').on('click', 'li', (e) => {
    
    //현재 이벤트 발생한 곳에 강조효과 주기
    //먼저 모든 강조효과 제거 후 다시 부여
    $('#name-list li.list-group-item-secondary').removeClass('list-group-item-secondary');
    $(e.target).addClass('list-group-item-secondary');
    
    //Arrow Func에서는 this는 window를 가리킨다.
    //console.log($(e.target).attr('data-no'), $(e.target).text());
    var no = $(e.target).attr('data-no');
    
    //각 화면당 각 js가 담당하는데, 리스트 이름을 누르면 인덱스화면에 상세내용이 떠야한다.
    //이때 필요한 파라미터를 리스트.js -> 인덱스.js 로 전달해야하는데 
    //커스텀 이벤트를 발생시킨다. trigger!
    $(document.body).trigger('show.detail', [no]);
});

$(document.body).on('refresh.list', () => loadList());

function loadList() {
    //url 에 상대경로를 넣게 되면, 폰갭같은 모바일로 넘어갔을때 찾지 못하므로 절대경로를 사용한다.
    $.getJSON(
            `${serverApiAddr}/json/businesscard/list`, 
            (result) => {
                console.log(result);
                
                var html = template(result);
                $('#name-list').html(html);
                $('#name-list li:first-child')
                    .click()
                    .addClass('list-group-item-secondary');
    });
}