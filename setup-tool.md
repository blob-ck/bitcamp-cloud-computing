# 프로그래밍 준비
## 개발도구 설치
- openjdk 10.0.1
- eclipse photon
- visual studio code
- git client
- scoop (package manager)
- scoop install gradle
- scoop install mariaDB

	mysql -uroot -p; 입력 후 Enter password:에서 그냥 엔터 친다(최초는 암호설정이 되어있지 않으므로)
	show databases;
	use mysql;  //database changed
	update user set authentication_string=password('1111') where user='root';
	flush privileges;
	
	--다시 접속
    quit;
	mysql -uroot -p;
	Enter password> 1111;
	
    --'study'로 사용자 생성, 비밀번호 '1111'
	create user 'study'@'localhost' identified by '1111';
	---다른 컴으로 접속하고싶다면(원격으로) localhost에 % 를 붙인다 
 	
    --'studydb' 데이터베이스 생성
	create database studydb character set utf8 collate utf8_general_ci;
	
	--studydb의 사용권한을 study(사용자)에게 부여하기
    grant all privileges on studydb.* to 'study'@'localhost';

    --사용자 'study'로 접속
    quit;
    mysql -ustudy -p;
    1111; //암호
    use studydb;
    //git에서 clone한 ddl문을 복사해서 실행한다. (-- 게시판 부터 끝까지)



## Eclipse 설정
'''
워크스페이스 설정
1. 문자집합을 utf-8 로 설정
    Window/Preferences/General/Worksapce/Text file encoding
    utf-8 로 설정

2. Editor 기본설정
    Window/Preferences/General/Editors/Text Editors 
    tab 크기를 2 또는 4 로 설정 
    Insert spaces for tabs 체크! (요즘 추세가 tab 대신 space)
    Show print margin 체크! + Allow editors to override the margin column
    Show whitespace charaters(cpnfigure visibility) - 맨 우측아래 두개 체크해제, 숫자 30으로

3. 자바 설정
    Window/Preference/Java
    Instalssed JRE : JDK 위치 지정하기
    Code Style/Formatter : 자바 에디터 탭 정보 설정- new 로 새로 만든다
    Compiler : 기본 컴파일러 버전 설정

4. 웹 환경설정
    Window/Preference/Web
    HTML, CSS, JSP 에서 문자집합을 UTF-8로 설정

5. WAS 서버 환경설정
    Window/Preference/Server
    Runtime Environments - 톰캣 서버 위치 설정 (apache 상위폴더 - 여기선 apps 폴더로 지정)

6. 애플리케이션 배포하고 테스트 할 톰캣 실행 환경설정
    이클립스 하단 Servers view 에서 톰캣 서버 생성
'''





## Tomcat 설치
'''
1. 톰캣 다운로드
- tomcat.apache.ort 에서 다운
2. 톰캣 설치
- c:\apps 폴더에 압축해제


'''


## 웹 프로젝트 폴더 준비
'''
1. 기존의 예제 프로젝트 복사
java106-java-project 를 
bitcamp-cloud-computing 폴더로 복사한다.
복사 후 복사한 폴더명을 pms-projcet 로 개명

2. 프로젝트 폴더를 이클립스 프로젝트로 만든다.
- powershell - build.gradle 파일이 존재하는 디렉토리에서 'gradle eclipse'를 실행하여



'''











