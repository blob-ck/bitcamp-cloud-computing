자바를 우선 설치해야 자바를 사용하는 Eclipse, gradle 설치시 편하다


1. OpenJDK 10 GE
'''
	환경변수 설정시 변수값을 맨 위(윈10) 또는 맨 앞으로(윈8이하) 입력해줘야 오라클 JDK보다 우선 OpenJDK를 읽어들일 수 있다.
	powershell 에서 환경변수 확인하려면 echo $env:JAVA_HOME 을 입력한다(유닉스에서는 대소문자를 구분하므로 습관을 들이자)
'''

2. Eclipse photon
'''
    워크스페이스 설정
    문자집합을 utf-8 로 설정
    Window/Preferences/General/Worksapce/Text file encoding 에서 utf-8 로 설정
'''

3. Visual studio code
'''
'''

4. Scoop - 패키지 매니저(mac은 brew, linux는 sdk)
'''
	scoop 설치 후 shell에서 명령어로 설치 - 이 과정에서 실행규칙변경이 필요할 수도 있다)
	scoop 설치 후 scoop install gradle 입력 (패키지명 만 적으면 최신버전을 설치 - 버전을 지정하려면 gradle@3 이런식으로 골뱅이 뒤에 버전 입력)
'''

5. Gradle 설치
'''

'''

6. git 클라이언트(일단은 명령어에 익숙해지도록 GUI버전이 아닌 일반버전을 설치)
'''
    직접 인스톨 파일을 다운받아 설치하는 방법과
	패키지 관리자를 통해 명령어 입력으로 설치할 수도 있다 - scoop install git

	gitHub.com/java106 의 repo를 clone 할 것 
	https://github.com/eomjinyoung/java106
'''



7. MariaDB
'''
shell 관리자권한으로 실행 후
	mysqld --install "mariadb"
	제어판 - 관리도구 - 서비스 - mariadb 우클릭 - 시작
		마리아 디비가 안되는 자리가 있어 mysql로 변경했다 (어차피 돌아가는건 같다)
'''

8. Mysql
'''
	(mariadb가 설치시 오류나서 대체했다)
	mysql -uroot -p; 입력 후 Enter password:에서 그냥 엔터 친다(최초는 암호설정이 되어있지 않으므로)
	show databases;
	use mysql;
	update user set authentication_string=password('1111') where user='root';
	flush privileges;
	
	다시 접속
	mysql -uroot -p;
	Enter password> 1111;
	
	create user 'study'@'localhost' identified by '1111';
	다른 컴으로 접속하고싶다면(원격으로) localhost에 % 를 붙인다 
 	
	create database studydb character set utf8 collate utf8_general_ci;
	
	studydb의 사용권한을 study(사용자)에게 부여하기
	
'''	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
페이스북 반드시 관리할 것

	
	shell 명령어

mkdir 폴더명	:	새 폴더 생성
cd..		:	상위폴더로 이동
cd 하위폴더명	:	하위폴더로 이동
pwd			:	현재 디렉토리 출력 (present work directory)


줄바꿈 line delimeter