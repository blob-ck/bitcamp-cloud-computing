
//다른 라이브러리를 가져다 쓸 경우 이름이 같아 충돌할 경우가 있다.
// 자바 패키지처럼 이름이 같아도 패키지가 달라서 충돌이 일어나지 않는 것을 모방하여
// 자바스크립트도 글로벌로 함수를 선언하지 말고 객체 별로 선언해서 가져다 쓰면 좋다.
// => 자주 사용할 함수를 라이브러리 화 시킨다.

'use strict'

//value 에는 인자로 문자열 또는 오리지널 태그객체가 올 수 있다.
//자바스크립트 오리지널 객체
//제이쿼리 객체 = 오리지널 객체 + 제이쿼리 프로퍼티
let bit = function(value) {
    
    //복수의 태그들 -> 배열로 저장한다.
    var el = [];

    //오리지널 태그 객체도 jQuery를 사용할 수 있게 첨가해준다.
    //제이쿼리 객체 = 오리지널 객체 + 제이쿼리 프로퍼티ㄴ
    if (value instanceof HTMLElement) {
        el[0] = value;
        
    //태그 객체가 아니라면 문자열로 정보가 넘어올 것이므로 판단,
    //"<"으로 간편하게 태그 만드려면 요로케 문자열 잘라서 태그 생성
    } else if (value.startsWith('<')) {
        el[0]  = document.createElement(value.substr(1, value.length - 2));
        
    // "<태그명>"으로 인자가 넘어오지 않았다면 쿼리셀렉터를 사용해 기존 방식으로 태그 생성
    } else {
        var list = document.querySelectorAll(value);
        //selector로 찾은 태그들을 빈 배열로 옮긴다.
        for (var i = 0; i < list.length; i++) {
            el[i] = list[i];
        }
    }
    
    
    //ajax로 태그를 추가했을 때 뷰에서 타겟잡으려면 못잡는다.
    //if (el.length == 0) return null;
    
    
    
    
    // 개발자가 쓰기 좋은 함수를 추가해서 리턴하자!
    el.html = function(value) {
        if(arguments.length == 0) {
            return el[0].innerHTML;
        }
        
        for (var e of el) {
            e.innerHTML = value;
        }
        return el;
    };
    
    el.append = function(child) {
        for (var e of el) {
            //여러 부모가 있다면, 한 부모의 자식이어야 하므로 맨 마지막 부모의 자식으로 붙는다.
            //이미지 로테이션 연습에서 존재하는 태그를 잘라내서 뒤로 덭분인걸 보면 알 수 있다.
            e.appendChild(child);
        }
        return el;
    };
    
    //위에서 태그 타겟잡을 때 태그들 배열을 불러오므로, parent도 배열이다.
    //어차피 반복문 돌면서 마지막 부모에 자식태그들을 붙일 것이므로,
    //부모태그배열의 마지막 값을 지정해서 자식태그들을 반복문으로 append한다.
    el.appendTo = function(parent) {
        for (var e of el) {
            parent[parent.length - 1].appendChild(e);
        }
        return el;
    };
    
    el.attr = function(name, value) {
        if(arguments.length < 2) {
            return el[0].getAttribute(name);
        }
        
        for (var e of el) {
            e.setAttribute(name, value)
        }
        return el;
    };
    
    el.removeAttr = function(name) {
        for (var e of el) {
            e.removeAttribute(name);
        }
        return el;
    };
    
    //event listener(=handler)를 추가하는 함수
    el.on = function(name, p2, p3) {
        
        var selector = null;
        var handler = null;
        
        if (arguments.length == 2) handler = p2;
        if (arguments.length == 3) {
            selector = p2;
            handler = p3;
        }
        
        for (var e of el) {
            if (!selector) {
                //selector 가 지정되어있지 않으면 호출
                e.addEventListener(name, handler);
            } else {
                // selector가 지정되어 있으면,
                // 핸들러를 호출하기 전에 selector에 해당하는 것인지 검사하는
                // 함수가 먼저 호출되게 한다.
                e.addEventListener(name, function(event) {
                    // 현재 태그의 자신 태그 중에서 
                    // selector 조건에 해당되는 자식 태그들을 찾는다. 
                    var selectorTargets = e.querySelectorAll(selector);
                    
                    // 그 자식 태그들 중에 이 이벤트가 발생된 태그를 찾는다.
                    for (var target of selectorTargets) {
                        // 만약 이벤트가 발생된 태그와 일치하는 자식 태그가 있다면,
                        // 그때서야 핸들러를 호출해준다.
                        if (event.target == target) {
                            handler(event);
                            break;
                        }
                    }
                });
            }
        }
        return el;
    };
    
    //위의 on 에서 
    //자주 쓰는걸 좀더 간소화 했다.
    el.click = function(handler) {
        return el.on('click', handler);
    };
    
    
    
    //CSS
    el.css = function(name, value) {
        if (arguments.length == 1) {
            return el[0].style[name];
        }
        for (var e of el) {
            e.style[name] = value;
        }
        return el;
    }


    
    //el.value
    el.val = function(value) {
        if (arguments.length == 0) {
            return el[0].value;
        }
        for (var e of el) {
            e.value = value;
        }
        return el;
    }
    
    
    //chaining을 위한 자신 return
    return el;
};





bit.parseQuery = function(url) {
    var paramMap = {};

    var qs = url.split('?');
    if (qs.length > 1) {
        var params = qs[1].split('&');
        for (var param of params) {
            var kv = param.split('=')
            paramMap[kv[0]] = kv[1];
        }
    }
    return paramMap;
};





bit.ajax = function(url, settings) {
    if (settings == undefined) {
        settings = {};
    }
    
    if (settings.dataType == undefined) {
        settings.dataType = 'text';
    }
    
    if (settings.method == undefined) {
        settings.method = 'GET';
    }
    
    var xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = function() {
        if (xhr.readyState < 4) return;
        if (xhr.status !== 200) {
            if (settings.error) {
                error();
            }
            return;
        }
        
        let data = xhr.responseText;
        if (settings.success) {
            if (settings.dataType == 'json') {
                data = JSON.parse(xhr.responseText);
            }
            
            settings.success(data);
            
        }
        // done() 에 의해 두번째 success함수가 등록되어 있다면 호출한다.
        if (done) {
            done(data);
        }
        
    };

    //settings 에 서버로 보낼 data가 들어있다면 url에 포함해야 한다.
    var qs = '';
    if (settings.data) {
        for (var propName in settings.data) {
            qs += `&${propName}=${settings.data[propName]}`;
        }
    }
    
    //GET
    if(settings.method == 'GET') {
        if (url.indexOf('?') == -1) {
            url += '?';
        }
        url += qs;
        xhr.open(settings.method, url, true);
        xhr.send();
    //POST
    } else {
        xhr.open(settings.method, url, true);
        xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        xhr.send(qs);
    }
    
    
    //XMLHttpRequest 객체를 리턴하기 전에 함수를 추가한다.
    let done;
    xhr.done = function(func) {
        done = func;
    };
    
    return xhr;
};


//bit.getJSON = function(url, data, s) {
bit.getJSON = function(url, p2, p3) {
    let data = {};
    var success = null;

    if (arguments.length > 1) {
        if (typeof p2 == 'function') success = p2;
        else data = p2;
        
        if (typeof p3 == 'function') success = p3;
    }
    
    return bit.ajax(url, {
       dataType: 'json',
       data: data,
       success: success
    });
};




//==
/*$.ajax({
type: "POST",
url: url,
data: data,
success: success,
dataType: dataType
});*/
// == jQuery.post( url [, data ] [, success ] [, dataType ] )
bit.post = function(url, p2, p3, p4) {
    let data = {};
    let success = null;
    let dataType = 'text';

    if (arguments.length == 2) {
        if (typeof p3 == 'function') {
            data = p2;
            success = p3;
        } else if (typeof p2 == 'function') {
            success = p2;
            dataType = p3;
        } else {
            data = p2;
            dataType = p3;
        }
    } else if (arguments.length > 2) {
        data = p2;
        success = p3;
        dataType = p4;
    }
    
    return bit.ajax(url, {
        method: 'POST',
        dataType: dataType,
        data: data,
        success: success
    });
};





let $ = bit;








