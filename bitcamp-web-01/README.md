# bitcamp-web-01 : 웹 프로젝트 폴더 준비


## 프로젝트 폴더 생성

'''
> mkdir bitcamp-web-01
> cd bitcamp-web-01
'''


## Maven 기본 디렉토리 구조를 생성
자바 애플리케이션 프로젝트를 위한 기본 폴더 및 예제파일을 생성
'''
> gradle init --type java-application

'''

## 웹 관련 폴더 추가
'''
- src/main 폴더에 resources 폴더 추가
- src/main 폴더에 webapp 폴더 추가
'''

## Eclipse 관련 설정파일 추가
- build.gradle 설정파일에 eclipse 플러그인 추가
'''
plugins {
    id 'java'
    id 'eclipse-wtp'
    id 'war'
}
'''

이클립스 설정파일 만들기
'''
> gradle eclipse
'''

## 4) 이클립스에서 프로젝트를 import

## 5) 웹 애플리케이션 배포 및 테스트