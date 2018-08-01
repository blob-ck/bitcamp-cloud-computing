// 다른 라이브러리를 가져다 쓸 경우 이름이 같아 충돌할 경우가 있다.
// 자바 패키지처럼 일므이 같아도 패키지가 달럿 충돌이 일어나지 않는 것을 모방하여
// 자바스크립트도 글로벌로 함수를 선언하지 말고 객체 별로 선언해서 가져다 쓰면 충돌을 방지할 수 있다.
// => 자주 사용할 함수를 라이브러리화 시킨다.

//1
'use strict'
let bit  = function(value) {
        
        //3
        var el = [];
        
        //5
        // value = js객체
        // value = document.querySelector("태그")
        if(value instanceof HTMLElement) {
            el[0] = value;
        // value = "<태그명>"
        } else if (value.startsWith('<')) {
            el[0] = document.createElement(value.substr(1, value.length-2));
        // value = "태그명"
        } else {
            var list = document.querySelectorAll(value);
            for (var i = 0; i < list.length; i++) {
                el[i] = list[i];
            }
        }
        
        //6 each function test
        // test ok
        el.html = function(value) {
            if(arguments.length == 0) {
                return el[0].innerHTML;
            }
            for (var e of el) {
                e.innerHTML = value;
            }
            return el;
        };
        
        // test ok
        el.append = function(child) {
            for (var e of el) {
                e.appendChild(child);
            }
            return el;
        };
        // test ok
        el.appendTo = function(parent) {
            for (var e of el) {
                parent[parent.length - 1].appendChild(e);
            }
            return el;
        };
        
        // test ok
        el.attr = function(name, value) {
            if(arguments.length < 2) {
                return el[0].getAttribute(name);
            } 
            for (var e of el) {
              e.setAttribute(name, value);  
            }
            return el;
        };
        
        // test ok
        el.removeAttr = function(name) {
            for (var e of el) {
                e.removeAttribute(name);
            }
            return el;
        }
        // test ok
        el.on = function(name, p2, p3) {
            
            var selector = null;
            var handler = null;
            
            if (arguments.length == 2) {
                handler = p2;
            }
            if (arguments.length == 3) {
                selector = p2;
                handler = p3;
            }
            
            for (var e of el) {
                if(!selector) {
                    e.addEventListener(name, handler);
                } else {
                    e.addEventListener(name, function(event) {
                        var selectorTargets = e.querySelectorAll(selector);
                        for (var target of selectorTargets) {
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
        
        //test ok
        el.click = function(handler) {
            return el.on('click', handler);
        };
        
        // test not yet
        el.css = function(name, value) {
            if(arguments.length == 1) {
                return e.style[name];
            }
            for (var e of el) {
                e.style[name] = value;
            }
            return el;
        };
        
        //test not yet
        el.val = function(value) {
            if(arguments.length == 0){
                return e.value;
            }
            for (var e of el) {
                e.value = value;
            }
            return el;
        }
        
        
        //4
        //이렇게 리턴해줘야 추가한 jquery 프로퍼티를 쓸 수 있다.
        //메소드 체인
        //method chaining
        return el;
}

//bit.parseQuery
// test ok
bit.parseQuery = function(url) {
    
    var paramMap = {};
    var qs = url.split('?');
    if (qs.length > 1) {
        var params = qs[1].split('&');
        for(var param of params) {
            var kv = param.split('=');
            paramMap[kv[0]] = kv[1];
        }
    }
    return paramMap;
};

//bit.ajax
// test
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
        if (xhr.readyState < 4) {
            return;
        }
        if (xhr.status !== 200) {
            if(settings.error) {
                error();
            }
            return;
        }
        
        let data = xhr.responseText;
        if (settings.success) {
            if (settings.dataType == 'json') {
                data = JSON.parse(data);
            }
            settings.success(data);
        }
        if (done) {
            done(data);
        }
    };
    
    var qs = '';
    if (settings.data) {
        for (var propName in settings.data) {
            qs += `&${propName}=${settings.data[propName]}`;
        }
    }
    
    if (settings.method == 'GET') {
        if (url.indexOf('?') == -1) {
            url += '?';
        }
        url += qs;
        xhr.open(settings.method, url, true);
        xhr.send();
    } else {
        xhr.open(settings.method, url, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send(qs);
    }
    
    let done;
    xhr.done = function(fnc) {
        done = fnc;
    }
    
    return xhr;
}

//bit.getJSON
//bit.post



//2
let $ = bit;




















