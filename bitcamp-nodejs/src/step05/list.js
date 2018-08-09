"use strict"

var trTemplateSrc = $('#tr-template-src').text();
var trTemplateFn = Handlebars.compile(trTemplateSrc);

var {page, size} = $.parseQuery(location.href);
let data = null;
let tbody = $("#eListTable > tbody");

if(page != undefined && size != undefined) {
    loadList(page, size);
}else {
    loadList(1, 3);
}

$(ePrevBtn).click(function() {
    loadList(data.page - 1, data.size);
});

$(eNextBtn).click(function() {
    loadList(data.page + 1, data.size);
});

function loadList(page, size) {
    
    $.getJSON(
            serverApiAddr + '/json/member/list',
            {
                page: page,
                size: size
            })
            .done(
            function(result) {
                data = result;
                
                var trListHTML = trTemplateFn({list: data.list});
                tbody.html(trListHTML);
                
                /*for (var item of data.list) {
                    
                    var trHTML = trTemplateFn(item);
                    $(trHTML).appendTo(tbody);
                }
                */
                $(ePageNo).html(data.page);
                if (data.page <= 1)
                    $(ePrevBtn).attr('disabled', '');
                else 
                    $(ePrevBtn).removeAttr('disabled');
                
                if (data.page >= data.totalPage)
                    $(eNextBtn).attr('disabled', '');
                else
                    $(eNextBtn).removeAttr('disabled');
            });
    
}

tbody.on('click', 'a.viewLink', function(){
    event.preventDefault();
    var id = $(event.target).attr('data-id');
    location.href = `view.html?id=${id}&page=${data.page}&size=${data.size}`
});

function clickViewLink(event, id) {
    event.preventDefault();
    var id = $(event.currentTarget).attr('data-id');
    location.href = `view.html?id=${id}&page=${data.page}&size=${data.size}`
    console.log(id);
}
