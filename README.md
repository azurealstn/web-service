# 게시판 웹 서비스
게시판 프로젝트입니다.

## 사용 기술

- Java 11 (openjdk: https://jdk.java.net/archive/)
- Spring Boot '2.4.5.RELEASE'
- Spring Security
- Spring Data JPA
- Mustache (화면 구성)
- Ajax (CRUD 동작)
- MySQL
- 부트스트랩

<br/>
<br/>
<br/>

## 기능

- REST API 통신
- C(Create): http://localhost:8080/api/v1/posts
- R(Read): http://localhost:8080/ (메인 화면)
- U(Update): http://localhost:8080/api/v1/posts/{id}
- D(Delete): http://localhost:8080/api/v1/posts/{id}
- 소셜로그인 (구글, 네이버)
    - **USER** 권한을 가진 사람만 글 등록 허용 (Default: **GUEST**)
        - **USER 권한 주기**: update user set 'USER';
- JPA Auditing으로 생성시간/수정시간 자동화
- 검색 (JPA 활용)

<br/>
<br/>
<br/>

## 화면

![1](https://user-images.githubusercontent.com/55525868/117652278-ef87d280-b1cd-11eb-8ee1-b912fbf8293e.PNG)

## DB

- MySQL version: 8.0.22

#### DB Table Relationship

![1](https://user-images.githubusercontent.com/55525868/117635388-b7c35f80-b1ba-11eb-8eec-f0ace1eb8605.PNG)

- 게시글 : 사용자 -> N : 1 관계

#### User Table

![1](https://user-images.githubusercontent.com/55525868/117634749-32d84600-b1ba-11eb-8a33-ab045420d491.PNG)

#### Posts Table

![1](https://user-images.githubusercontent.com/55525868/117635236-98c4cd80-b1ba-11eb-9482-4eb65faae764.PNG)

### 세션 저장소

- 기본적으로 세션은 **WAS의 메모리에서 저장하고 호출**되기에 Spring Boot 같은 내장 톰캣이 있는 경우에는 애플리케이션 실행시 항상 초기화가 됨.
- 해결법으로 **MySQL DB를 세션 저장소로 사용** (로그인이 덜 필요한 백오피스에서 주로 사용)

```
application.properties
# 세션 저장소
spring.session.store-type=jdbc
spring.session.jdbc.table-name= SPRING_SESSION
spring.session.jdbc.initialize-schema= always
```

## 에러 해결

```
Failed to convert from type [java.lang.Object] to type [byte[]] for value 'com.azurealstn.webservice.config.auth.dto.SessionUser@5e872f2e';
```

- **세션에 저장할 SessionUser 클래스에 직렬화를 구현하지 않았다**는 에러
- `Serializable` 인터페이스를 상속받으면 해결

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

## References

- 스프링 부트와 AWS로 혼자 구현하는 웹 서비스
- https://gofnrk.tistory.com/45
- https://jojoldu.tistory.com/307
- https://andamiro25.tistory.com/132
- https://gonyda.tistory.com/15?category=803530