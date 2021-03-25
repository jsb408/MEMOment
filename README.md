# Memoment

순간을 기록하는 간단 일기장

- 진행기간 : 2020. 09. 04 ~ 2020. 09. 28
- 사용기술 : Android Studio, Kotlin, Realm, Material Calendar View

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111783906-1298b300-88fe-11eb-9131-db5784215c38.jpg" width="30%"/></p>

## 서비스 소개

- MEMOment(메모먼트)는 순간순간을 기록할 수 있는 한 줄 일기입니다.
- 글쓰기 버튼을 통해 선택한 날짜에 순간을 기록할 수 있습니다.
- 수정 기능은 제공되지 않으며, 삭제만 가능합니다.

## 상세 기능 소개

### 1. 모먼트 조회

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111784030-3fe56100-88fe-11eb-97a7-7730e5075cb7.jpg" width="30%"/> <img src="https://user-images.githubusercontent.com/55052074/111784039-4247bb00-88fe-11eb-8349-ef633a254d62.jpg" width="30%"/></p>

- 날짜를 선택하면 해당 날짜의 모먼트들이 시간순으로 정렬되어 표시됩니다.
- 달력에서 모먼트가 있는 날짜는 해당 숫자 밑에 분홍색 점이 표시됩니다.
- 달력 하단의 모먼트 목록을 위로 스와이프하면 전체 모먼트를 볼 수 있습니다.
- 더 많은 모먼트는 아래로 스크롤하여 조회할 수 있습니다.

### 2. 모먼트 작성

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111784248-8b980a80-88fe-11eb-8249-dfc7284a20d9.jpg" width="30%"/></p>

- 원하는 내용으로 모먼트를 작성하고 저장할 수 있습니다.
- 작성한 모먼트는 Realm을 통해 로컬에 저장됩니다.
- 줄 바꿈 사용이 가능합니다.

### 2-1. 시간 변경

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111784275-96eb3600-88fe-11eb-9e68-168412e28d3f.jpg" width="30%"/> <img src="https://user-images.githubusercontent.com/55052074/111784286-994d9000-88fe-11eb-8bdc-3ac3eb245e51.jpg" width="30%"/></p>

- 작성 페이지의 상단 날짜를 터치하면 작성 대상 날짜를 변경할 수 있습니다.
- 상단의 시각 부분을 터치하면 작성 대상 시각을 변경할 수 있습니다.
- 시각은 24시간제를 사용합니다.

### 2-2. 글 작성

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111784383-b3876e00-88fe-11eb-8f06-96753fccd6f1.jpg" width="30%"/> <img src="https://user-images.githubusercontent.com/55052074/111784389-b5513180-88fe-11eb-95cb-7b9d5165514c.jpg" width="30%"/></p>

- 글을 입력하고 작성 버튼을 누르면 Realm에 저장되고 리스트에 반영됩니다.
- 리스트는 작성 시각에 상관 없이 게시글에 설정된 시각 순서로 정렬됩니다.

### 3. 모먼트 삭제

<p align="center"><img src="https://user-images.githubusercontent.com/55052074/111784501-d0bc3c80-88fe-11eb-8a4e-015dbd4f2486.jpg" width="30%"/></p>

- 모먼트 목록에서 원하는 모먼트를 길게 누르면 삭제 다이얼로그가 표시됩니다.
- 확인 버튼을 눌러 모먼트를 삭제할 수 있습니다.

## 기타 사항

- 매 순간을 기록한다는 기획 의도에 맞게 수정 기능은 구현되어있으나 주석처리 되어있습니다.
- Bottom Sheet를 스와이프해 전체 모먼트 리스트를 표시할 때 아이템 간 간격이 넓어지며 시간 사이 빨간 줄이 표시됩니다.

<img src="https://user-images.githubusercontent.com/55052074/111784608-f0536500-88fe-11eb-9488-462a6cf8d74b.gif" width="30%"/>
