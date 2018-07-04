# EC2 AWS Linux AMI 에 LAMP 설치하기
- Linux, Apache, MySQL, PHP

## 흐름
- AWS 가입 - 신용/체크카드 필요
- aws.amazon.com 로그인
- AWS Management Console
- 서비스 > 컴퓨팅 > EC2
- 인스턴스 시작
- Amazon Linux AMI 2018.03.0 (HVM), SSD Volume Type - ami-ebc47185 선택
- 검토 및 시작 * 5
- 시작 - 새 키 페어 생성 / 키페어 이름: bitcamp 
- 키페어 다운로드
- 인스턴스 보기 > 인스턴스 수명주기 > 연결 > PuTTY 사용하여 연결
- 자습서 내용대로 따라한다
(ppk 인증서는 개인 이메일이나 usb등에 백업할 것)

- 브라우저에서 ip주소 입력 후 불러오는지 테스트 -> 접근권한 없어서 안됨
- 인스턴스 보기 > 스크롤 우측으로 > 보안 그룹 링크 클릭
- 인바운드 탭 > 편집 > HTTP , 위치무관
- 브라우저에서 ip주소 입력 후 불러오는지 테스트 -> 됨
