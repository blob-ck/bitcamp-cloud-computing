'use strict'
var currentPlayer = 'x';
var winner = '';
var gameOver = false; // 게임 종료 여부
var isDraw = false; // 비김 여부
var countCheck = 0; //boxSquare -1; // 이 숫자에 도달할 때까지 승패 가르지 못하면 비김
var boxSize = prompt('한 변의 길이를 정하시오'); // 3*3, 4*4, ... = gameOver 조건 = 이중반복문 중 바깥 length
$('#eTable').css('width',boxSize*200);
makeBox(boxSize);
//var boxSquare = boxSize * boxSize; //총 입력 칸 수
var boxCheckArr = new Array();
for (var i = 0; i < boxSize; i++) {
	boxCheckArr[i] = new Array();
	for (var j = 0; j < boxSize; j++) {
		boxCheckArr[i][j] = '';
	}
}

$('#new-game').click(function(){
	location.reload();
});

$('#eTable').on('click', 'div.row > div.box', function(){
	if(currentPlayer == 'x' && !gameOver && !isDraw) {
		fillBox(this);
	}
});

/* $('#eTable').on('click', 'tr > td.box', function(){
	if(currentPlayer == 'x' && !gameOver && !isDraw) {
		fillBox(this);
	}
}); */

//게임 승패 여부 체크
function checkOver(cp) {
    var checkRow = 0;
	var checkCol = 0;
	var checkCrossLR = 0;
	var checkCrossRL = 0;
    for (var i = 0; i < boxSize; i++) {
        for (var j = 0; j < boxSize; j++) {
            if (cp == boxCheckArr[i][j]) {//가로체크
                checkRow++;
				if (i == j) { //좌상우하 대각선 체크
					checkCrossLR++;
				}
			}
			if (cp == boxCheckArr[j][i]) {//세로체크
                checkCol++;
				if ((i + j) == (boxSize - 1)) { //우상좌하 대각선 체크
					checkCrossRL++;
				}
            }
			if (checkRow == boxSize || checkCol == boxSize || checkCrossLR == boxSize || checkCrossRL == boxSize) {
				$('#winner').attr('style', 'display:show').text('승자는 '+cp);
				gameOver = true;
				return cp;
			}
        }
		console.log('checkRow = ',checkRow);
		console.log('checkCol = ',checkCol);
		console.log('checkCrossLR = ',checkCrossLR);
        checkRow = 0;
        checkCol = 0;
	}
    
	countCheck++;
	console.log('현재 체크된 박스 수 = ', countCheck);
	if (countCheck == (boxSize * boxSize)) {
		$('#winner').attr('style', 'display:show').text('비겼네');
		gameOver = true;
		isDraw = true;
	}
}


//박스체크
function fillBox(box) {
	if($(box).text()) {
		console.log('이미 체크한 박스다.');
		return;
	}
	if(currentPlayer == 'x') {
		$(box).text(currentPlayer);
		$(box).addClass('cell-'+currentPlayer);
		var tdIndex = $(box).index(); // td sibling 중 현재 td의 인덱스를 반환한다. 0,1,2 중 하나.
		var rowIndex = $(box).parent().index();// td의 부모인 row의 sibling 중 현재 td의 부모row를 반환한다. 0,1,2 중 하나.
		boxCheckArr[rowIndex][tdIndex] = currentPlayer;
		console.log(boxCheckArr);
		checkOver(currentPlayer);
		if (gameOver) {
			return;
		}
		$(box).removeClass('open');
		currentPlayer = 'o';
		
		setTimeout(function() {
			var $open = $('.open'); //모든 open클래스 수 = 9개 = boxSquare
			var random = Math.floor(Math.random() * $open.length);
			if ($open.length <=0) {
				console.log('모든 칸이 찍혔음');
				return;
			}
			//var tdIndex = $($open[random]).text(currentPlayer).removeClass('open').index();
			var tdIndex = $($open[random]).text(currentPlayer).addClass('cell-'+currentPlayer).removeClass('open').index();
			var rowIndex = $($open[random]).parent().index();
			boxCheckArr[rowIndex][tdIndex] = currentPlayer;
			checkOver(currentPlayer);
			if (gameOver) {
				return;
			}
			currentPlayer = 'x';
		}, 1000);
	}
}

function makeBox(size) {
	for (var j = 0; j < size; j++) {
		var $mTr = $('<div class="row"/>');
		$('#eTable').append($mTr);	
		for (var i = 0; i < size; i++) {
		    var $mTd = $('<div class="box open"/>');
    	    $mTr.append($mTd);	
		}
	}
}

/* function makeBox(size) {
	for (var j = 0; j < size; j++) {
		//var $mTr = $('<tr/>').attr('class', 'row');
		var $mTr = $('<tr class="row"/>');
		$('#eTable').append($mTr);	
		for (var i = 0; i < size; i++) {
		    var $mTd = $('<td class="box open"/>');
    	    $mTr.append($mTd);	
		}
	}
} */