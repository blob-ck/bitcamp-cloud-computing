# bitcamp-web-06 : 서블릿
pms2_member 테이블에 대한 CRUD 서블릿을 만들기


#06
Front Controller (Dispatcher Controller)
Servlet -> POJO


#05
Persistence FrameWork - Mybatis 적용
SqlSessionFactory 사용
설정파일 1개, SQL파일 1개, 도메인 매핑, 동적 쿼리 작성

#04
서블릿에서 뷰를 JSP로 분리
 - JSTL 을 mvnrepository.com 에서 가져와 build.gradle dependencies에 추가한다(gradle eclipse 는 당연히)
 - jsp에서 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 추가

Filter에서 request의 encoding을 UTF-8으로 설정했다. - 매번 servlet에서 쓰인 중복소스 제거
 - request.setCharacterEncoding("UTF-8");


#03
DAO로 method를 따로 분류했고, 
JDBC드라이버는 static, 
DAO 생성은 Listener를 사용했다. (bitcamp.pms.listener.ContextLoaderListener)

테이블간 제약조건으로 Member delete는 미구현됬다.
어떤방향으로 할지는 나중에...
처음부터 만든게 아니므로 분석 및 목표에 따라 훈련을 우선하기로 한다.



## 패키지 생성
bitcamp.pms.servlet 패키지 생성한다.

## 회원 관리 서블릿 만들기
- servlet-api 의존 라이브러리 추가하기
  - mvnrepository.com에서 sevlet-api 라이브러리 검색
  - build.gradle에 라이브러리 등록
  - 'gradle eclipse' 실행하여 .classpath 파일 갱신
  - 이클립스 프로젝트 refresh
- mysql jdbc driver 의존 라이브러리 추가하기  
- bitcamp.pms.servlet.member 패키지 생성
- MemberListServlet, MemberViewServlet, MemberAddServlet, MemberUpdateServlet, MemberDeleteServlet 클래스 생성
