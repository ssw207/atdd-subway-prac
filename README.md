# 넥스트 스텝 ATDD 재구현

## 기능목록

- 지하철 노선 생성
- 지하철 노선 목록 조회
- 지하철 노선 조회
- 지하철 노선 수정
- 지하철 노선 삭제
- 지하철 구간 추가
- 지하철 구간 제거
- 경로 조회
    - 경로조회 타입
- 깃헙 로그인
- 즐겨찾기
- 문서화
- 요금조회
- 요금정책 추가
    - 기본운임 + 거리비례 추가운임
        - 10km 초과 5km당 100원
        - 50km 초과시 8km 당 100원
    - 요금정책은 추가될수 있다.
        - 요금정책마다 입력값은 달라질수 있다.
    - Fare에서 FarePolicy를 주입받아 요금을 계산한다
        - 입력값
            - 총 이동거리
            - 구간당 요금
            - 기본요금
        - 출력값
            - 요금
        - 계산로직
            - 거리에 따른 요금을 계산
            - 요금이 계산된 거리는 다음 계산시 제외 해야함
    - 도메인
        - Fare : 요금을 계산한다. N개의 요금정책이 있다면 N개의 요금정책 합을 int로 반환한다.
        - FarePolicy : 장책별 요금을 계산한다. N개의 정책이 있을수 있다.
        - FareRequestDto : 요금정책을 계산하는데 필요한 정보를 가지고 있다. 정책별로 필요한 값이 다를수 있으므로 객체로 포장한다
        - FareDistancePolicy : 거리당 요금을 계산하는 정책
        - FareDistance : 거리당 요금
            - 기본 : 1 ~ 10
            - 1구간 : 11 ~ 50 / 5km당 100원
            - 2구간 : 51 ~ / 8km당 100원
- 노선별 요금정책 추가
    - 사용하는 line의 추가 요금에 따라 요금이 가산
    - 경로탐색시 경유하는 section에서 line을 조회해 추가요금을 확인한다.
    - FareLinePolicy 도메인 클래스 추가
        - line의 추가요금을 파라미터로 받는다
- 연령별 요금 정책 추가
    - 로그인한 경우만 적용
    - 무료 : ~ 5
    - 어린이 : 6 ~ 12 -> 요금: (운임 - 350) 0.5
    - 청소년 : 13 ~ 18 -> 요금: (운임 - 350) 0.8
    - 성인 : 19 ~
    - 연령별 요금 께산은 상태를 가질
    - FareAgePolicy 도메인 클래스 추가
        - 나이를 파라미터로 받는다
- 요금 정책 로직 변경필요
    - 기존 : List로 정책 객체를 순회하면서 요금을 계산하고 그 합을 반환
    - 변경 : 연령별 요금정책은 가장 마지막에 적용되야하며 기존 정책들의 요금합과 나이를 전달 받아 요금을 계산함
    - 요금타입
        - 추가요금 : 정액 요금을 추가함
        - 비율요금 : 현재까지 계산한 총요금에서 계산함
    - 요금계산
        - 요금정책 일급 컬랙션을 추가후 추가요금, 비율요금 정책을 따로 추가
        - 요금정책 일급컬랙션은 내부에 List로 추가요금과 비율요금을 가지며 각각 다른 메서드로 정책이 추가됨
            - 비율요금 정책은 순서가 있음을 메서드 네이밍에 명시해야됨
        - 요금계산시 먼저 추가요금 합을 계산후 비율요금을 계산

## 요구사항

- 인수테스트 격리

## 코딩규칙

- Dto는 컨트롤러에서 Entity로 변환해서 Service로 넘긴다
- 커맨드와 쿼리는 치명적이 성능이슈가 없다면 분리한다.
- 파라미터가 3개 이상이면 모든 파라미터를 줄바꿈한다.
- 의미가 있는 생성자는 정적 팩토리 메서드로 변경한다.
- 특별한 이유가 없다면 기본적으로 원시타입을 사용한다.
- TDD 진행시 로직구현전 클래스생성이 필요하다면 메서드 내부에서 `throw new UnsupportedOperationException();` 호출후 실제 구현시 제거한다
- 리팩토링 사이클에서도 단계별로 커밋한다
- 기능 개발전 주석으로 기능을 쓰고 주석 내용을 바탕으로 메서드명을 짓는다. 주석을 쓸때 메서드가 두개이상의 일을 하면 메서드를 분리한다
- Entity 연관관계는 조회가 필요한 경우만 맺는다
    - 즐겨찾기 저장시 회원pk가 필요하지만 즐겨찾기에서 회원을 탐색하지 않으므로 연관관계를 설정하지 않고 회원pk만 즐겨찾기의 필드로 추가한다
- 상위 패키지 (인증등)은 하위 패키지의 의존성을 최대한 가지지 않는다.
    - 필요하다면 중간클래스를 만들어 의존성을 끊는다
- dto에서 기본적으로 래퍼클래스를 사용하지 않는다. 단 entity의 id등 원본 클래스의 타입이 래퍼클래스라면 래퍼클래스를 사용한다
- test작성시 클래스, 메서드가 추가 되는 경우 커밋시 test와 feat을 가능한 나눈다.

# 고민내용

- 구간 삭제시 상행, 하행, 중간역 삭제 로직이 다른데 어떻게 분리할까?
    - 클래스로 분리
        - 코드베이스가 늘어난다
        - 상행,하행,중간 로직이 클래스단위로 분리되 가독성이 좋음.
        - 중복로직을 상속으로 다른케이스와 공유가능함
    - enum으로 분리
        - 내부에 상태값을 가질수 없다.
        - 코드베이스가 크게 늘어나지 않음
        - 만약 구간추가 로직과 공통 로직이 있더라도 상속으로 공통로직을 추출할수 없음
        - 상행, 중간, 하행 관련 로직이 하나의 클래스에 섞여있음
    - if문 사용
        - 상행, 중간, 삭제 로직이 Sections 클래스에 모여있어 각각의 케이스에 어떤로직을 사용하는지 가독성이 좋지 않다.
- 구간 삭제시 클래스/enum 분리할때 Sections 클래스를 그대로 넘기는게 좋을까?
    - 그대로 넘기는 경우
        - Sections에 구간 조회관련 로직을 추가 할수 있음.
        - 데이터를 Sections에 가지고 있으므로 데이터와 관련된 로직이 모여있어 응집도가 높다.
        - Sections의 값을 삭제하는 메서드를 외부에 노출해야함.
    - values를 넘기는겨우
        - 값이 외부에 노출되지 않음
        - Seciton 값이 Sections가 아닌 다른 클래스에서 제어됨.
        - 만약 이 클래스의 메서드를 다른 클래스에서 사용하는 경우가 생기면 중복로직이 생김
        - remove(Section seciton) 메서드가 노출되지 않음
- Sections 클래스의 SectionActionFactory 클래스는 외부에서 주입받아야 할까? -> 필요 할 떄 변경할것
    - SectionActionFactory클래스는 Secitons외 다른 클래스에서 사용하지 않음 -> 만약 주입받아야한다고하면 수정 포인트가 한곳이다.
    - SectionActionFactory를 생성자로 주입받도록 변경하면 기존 테스트가 깨짐
    - 현시점에 SectionActionFactory는 다형성을 이용하지 않으므로 외부에 주입받는 이점이 없음
- JwtProvider 클래스는 어디에 두는게 적절할까?
- 깃허브, jwt인증에 따라 유저정보를 가져오는 로직은 어떻게 구성할까?
    - 요구사항
        - 입력값
            - 인증헤더 : prefix + accessToken 형태
        - 기능
            - 파싱기능 (같음)
                - 헤더를 파싱해 토큰을 생성 : " " 기준으로 스플릿 한뒤 뒤의 값을 가져온다
            - 검증기능
                - 헤더검증 (다름)
                    - 헤더값이 유효한지 체크
                    - prefix값이 깃허브면 Authentication, jwt는 Barere 이여야함. 추가로 방식이 늘어날수 있다.
                - 토큰검증 (같음)
                    - 모두 jwtProvider validation로 검증
            - 유저정보 조회기능 (다름)
                - jwt : 이메일로 회원 DB에서 조회
                - github : github에요청
                - 조회 후 인증유저타입으로 리턴
        - 출력값
            - 인증 유저 정보
    - 구현
        - 헤더값을 그대로 서비스로 넘겨서 서비스에서 헤더정보를 확인후 분기처리하는 방식
            - 인증방식이 늘어나면 클라이언트 코드가 늘어남
            - 유저정보 조회로직만 분기처리하면됨
        - 헤더값으로 인증타입 객체를 생성하고 토큰을 필드로 가진다. 객체가 토큰서비스를 파라미터로 받아 유저조회 메서드를 실행한다. (커맨드패턴)
            - 객체가 유저정보 조회로직을 각각 가져야한다.
            - 이러면 도메인객체에 스프링의존성이 생기는데 맞을까?
- 노선 요금정책에서 요금계산은 어디서 해야할까?
    - 지금은 Sections에서 총 노선요금을 계산해 dto를 통해 요금정책으로 넘겨주고 있다.
    - 구간의 노선정보는 Sections가 알고 있으므로 그 속성인 노선요금은 Sections가 계산하는게 맞을까?
    - 아니면 요금계산의 책임은 요금정책에 있으므로 Sections는 List<Line>을 요금정책으로 넘겨주는게 좋을까?
- 경로 조회에서 너무 많은 검증을 하는걸까?

# 문서화

- documentation 패키지 하위에 문서화 관련 테스트가 있으며, 이 테스트가 성공하면 `build/generated-snippets/path` 경로에 스니펫이 생긴다
- gradle task중 documentation > ascildoctor를 실행하면 `src/docs/asciidoc/index.adoc` 템플릿 파일을
  참고해 `build/docs/asciidoc/index.html` 문서파일이 생성된다
- `asciildoc` 플러그인을 설치하면  `src/docs/asciidoc/index.adoc` 파일을 미리보기로 볼수 있다.