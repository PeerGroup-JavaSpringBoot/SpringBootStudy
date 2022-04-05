# Chapter9 강의 정리

## Spring Cloud

### Spring Cloud Project
- 웹 개발을 공부하다 보면 배포 단계에서 항상 등장하는 서비스들
- AWS, GCP, Azure

![image](https://user-images.githubusercontent.com/83503188/161539713-ab5b564c-8e78-437c-96c9-c2acb7a61f3f.png)

- 게시판에 게시글을 남긴다 ?
    - 서비스 제공을 위해 필요로 하는 자원
      1. 사용자가 원할 때
      2. 즉시 제공을 하 수 있도록

- AWS에서 제공하는 서비스의 일부
1. RDS – 관계형 데이터베이스 제공에 특화
2. Lambda – 어플리케이션 대신 함수를 제공하여 기능 구현
3. S3 – RESTful하게 데이터를 다루는 솔루션


### Spring Cloud
- 분산된 시스템을 만드는데 있어서 필요로 하는 특수한 기능들을 만드는 것을 지원하는 걸 목표로 한다.
- Backend 개발자로의 성장을 목표한다면, 결국 분산 시스템 설계 및 개발에 대하여 공부하는 단계가 올 것이다.
- 그 시점이 왔을 때 Spring Cloud Project를 다시 찾아볼 것을 권장

### 프로젝트 살펴보기

- 일상적인 개발에선 명확한 주소를 제공

![image](https://user-images.githubusercontent.com/83503188/161749649-0f9d7dc6-6fed-4756-8ea6-8d8c4f6f1ec4.png)

- 클라우드 컴퓨팅 단계로 넘어오면 정확히 어디에 어떤 어플리케이션이 실행되는지 알 수 없다.

![image](https://user-images.githubusercontent.com/83503188/161539729-128de219-fdaf-41c6-8bed-56663078f2bf.png)




- Client Side Service Discovery
  - Client가 Service Registry를 통해 어느 서비스로 향할지를 물어보고 해당 서비스로 가는 구조 -> Service Registry == 안내 데스크
    ![image](https://user-images.githubusercontent.com/83503188/161539733-36d6c59a-6e06-4684-8c74-f65b5d8026bf.png)

- Service-Side Service Discovery
  - 클라이언트와 서비스 사이의 Load Balancer
  - 클라이언트는 부하 분산기인 Load Balancer로 요청을 보냄
  - Load Balancer가 Service Registry를 통해 실제로 보내고자 하는 서비스의 위치를 파악한후 해당 서비스로 요청을 분산시켜주는 구조

![image](https://user-images.githubusercontent.com/83503188/161750618-536b7ce7-6121-4a25-8556-178659945109.png)

- 메인에 들어가면 한번에 나오는 컨텐츠

  - 분류가 다름(엔터, 스포츠, 자동차, …)
  - 해당 분류에 대한 데이터는 다름

![image](https://user-images.githubusercontent.com/83503188/161539760-edc1118d-1d76-4db6-81af-dd866ac78344.png)

- 클라이언트 브라우저가 나눠져 있는 서비스에 따로 따로 연락을 보내고 정리를 해서 페이지를 제공하는 경우에 관리적인 측면에서 힘든 부분이 있다.
  - 데이터 관리, 위치 관리, …

![image](https://user-images.githubusercontent.com/83503188/161750707-dcc0b554-bdd1-40ef-969b-c1c3e6db55d2.png)


- **API Gateway**
  - 3개의 서비스(엔터, 자동차, 스포츠)에 대해서 먼저 어떤 데이터가 필요한지 구분을 하고 요청을 보낼 준비를 함
  - 클라이언트는 API Gateway에만 요청을 보냄
  - 필요한 정보를 한 번에 받아오고 정확한 위치를 알 필요 없고 하나의 엔드 포인트만 알아도 된다.

![image](https://user-images.githubusercontent.com/83503188/161539763-abd5d9e5-575a-46e0-86bc-e3beaf31d17a.png)

- 설정 파일
  - 서비스 실행 마다 설정파일 제공, 갱신이 어렵다. 
  - 클라우드 환경에서는 문제가 있다.

![image](https://user-images.githubusercontent.com/83503188/161539770-efbe8c55-ebbb-49e2-8f15-f46682487003.png)


- **Cloud Config**
  - 여러 대의 컴퓨터를 하나의 큰 클라우드라고 부르고 그 중 어딘가에서 SpringApp이 실행
  - 실행할 때 마다 설정 파일이 변경(DB 위치, 요청을 하고자 하는 application, …)되어야 한다면 문제가 발생
  - 스프링 클라우드 프로젝트의 하위 프로젝트 중 하나 인 설정 관리자라는 서버를 둠
  - 다양한 설정 파일을 관리하는 서버 역할
  - 클라우드 환경에서 새로운 app이 실행되면 설정 관리자에서 해당 app이 어떤 설정을 사용해야 하는지에 대한 질의를 통해서 설정을 받아오는 과정을 진행함

![image](https://user-images.githubusercontent.com/83503188/161539772-82695064-ef0e-4682-96d1-a75e65c1b421.png)




## MSA(Microservice Architecture)

### MSA 개요
- 새로운 기능 추가(로그인 기능, 위치 기반, 리뷰 게시글,…)
- 전통적인 개발은 하나의 큰 서비스를 만드는 방향으로 진행 (큰 틀을 만들어 놓고 틀에 벗어나지 않도록 기능을 추가)

  1. 오롯이 개발에 집중 – 난이도 하락
  2. 하나만 개발(하나의 jar파일) – 테스트 편리
  3. 산출물이 한가지(하나의 jar파일) – 배포 편리
  4. 확장성 – 사용자의 증가에 따른 사용량 허용치에 대한 증가, 산출물 jar파일 하나를 또 다른 서버에 똑같이 옮겨서 몇몇 요청은 그 서버로 옮김
- 위에 해당하는 아키텍쳐를 모놀리스(Monolithic) 아키텍처라고 한다.


#### 모놀리스 아키텍처 단점
- 어플리케이션의 구성요소가 서로에게 영향을 미치기 쉬움
- 한 기능의 문제가 전체 어플리케이션을 위태롭게 만듦
- 작은 기능 갱신을 위해 전체를 배포
- 서로 다른 기술 스택을 활용하기 힘듦
- 빠른 요구사항 대응과 신기술 적용에 불리함


### Microservice Architecture


![image](https://user-images.githubusercontent.com/83503188/161539781-f007f198-8432-462a-9aa1-5c96022df4cf.png)

![image](https://user-images.githubusercontent.com/83503188/161539785-10fff18d-2fc1-4fce-b3ed-0530c9e11eb5.png)

-	각각의 하나의 서비스(기능)을 가지고 있고 서로 상호 작용을 함으로써 하나의 큰 서비스를 제공하는 구조

#### 하지 말아야 할 이유
1. 서로 분리된 서비스이기 때문에 네트워크의 영향을 받음
   1. 통신 단계의 딜레이, 통신의 허용량(한번에 전송될 수 있는 데이터량), 통신 두절에 대한 대처가 필요


![image](https://user-images.githubusercontent.com/83503188/161539793-8674079c-660e-4edb-9e7a-92f16cc2b912.png)

2. 서로 다른 서비스의 기능을 요구할 때, 기능 구현 및 테스트가 어려움
   1. MSA로 분리(버스 노선을 제공하는 서비스, 지하철 노선을 제공하는 서비스, …)
   2. 길 찾기 서비스는 버스 노선 제공 서비스, 지하철 노선 제공 서비스가 정상적으로 작동을 하고 있는 상태에서만 제대로 작동하는지 확인할 수 있다.

![image](https://user-images.githubusercontent.com/83503188/161539800-0238f938-8fbf-41f0-8f78-1f615453d96d.png)


3. 자신이 필요로 하는 서비스의 상태를 확인하기 어려움
   1. 서로 다른 컴퓨터(네트워크)에 올라가 있는 서비스들이기 때문에

![image](https://user-images.githubusercontent.com/83503188/161539808-dce2d07a-ce7a-402e-ab3f-0935777fceff.png)
4. 배포 과정이 더 복잡함

![image](https://user-images.githubusercontent.com/83503188/161539812-18cd471c-2f03-49cb-af1c-00332ff906af.png)



#### MSA를 하는 이유
1. 상황에 맞는 기술 스택을 선택할 수 있음 
   1. 모놀리스의 가장 큰 문제로 하나의 기술 스택을 선정하면 새로운 기술이 선정한 스택과 어울리지 않는 경우 문제가 발생하는 단점을 해결

![image](https://user-images.githubusercontent.com/83503188/161539820-5c5efa27-3829-4655-9a12-10a13f480a90.png)

2. 기능이 개별적으로 발전, 개별 배포 용이 
   1. 서로에게 영향을 끼치지 않는다.

![image](https://user-images.githubusercontent.com/83503188/161539826-c2a82bf6-2a41-4716-9e86-7420076f2086.png)
3. 개별 서비스의 복잡성이 적어짐 
   1. 큰 서비스의 경우 부품이 많이 들어감	   
   2. MSA의 경우 하나의 부품만 관리하면 됨 (다른 서비스는 정상적으로 동작한다는 신뢰성이 생김)



![image](https://user-images.githubusercontent.com/83503188/161539834-fcf7df55-c3fd-4caa-b55b-ec0bc972db3b.png)

5. 서비스의 규모에 따라 MSA가 필요 없을 수도 있다. 
   1. 모놀리스로 먼저 구성을 하고 MSA로 넘어가는 단계가 적합할 수도 있다.


![image](https://user-images.githubusercontent.com/83503188/161539839-a4746fa8-86b3-43ee-a02a-514e30707149.png)


## Spring Cloud Gateway

### Gateway 기능
- 스포츠와 연관된 모든 컨텐츠를 가져오는 서비스?
- 신문기사를 제공하는 서비스, 방송사에서 영상을 받아오는 서비스, 블로그 글을 가져오는 서비스, …


![image](https://user-images.githubusercontent.com/83503188/161539856-fc5934ff-659a-4d3b-8826-14ec03e754e9.png)

![image](https://user-images.githubusercontent.com/83503188/161539853-e9fc4076-2849-4843-8fb6-b79a118f25f6.png)

- 다양한 주소에서 가져오는 다양한 서비스이지만 **API Gateway**를 통해 하나의 End Point로 관리할 수 있다.
- 같은 서버일 경우에는 port정보, 다른 서버일 경우에는 url 정보, … API Gateway 대신 처리


![image](https://user-images.githubusercontent.com/83503188/161539861-07a7fbc7-6e6a-43de-82e0-86d55fa9d607.png)

- 연관된 서비스를 MSA로 나누게 되면 원래는 하나의 서버에서 이용되던 엔드 포인트들이 서비스의 개수에 맞춰서 나눠진다.
- 추가적인 서비스가 늘어나면 엔드포인트가 증가
- **루팅(routing)**: API Gateway가 클라이언트의 요청을 다른 하위 MSA로 전달하는 것


### 횡단 관심사

![image](https://user-images.githubusercontent.com/83503188/161539877-560791ff-a781-493a-96ba-63b496fc8dc2.png)
![image](https://user-images.githubusercontent.com/83503188/161539883-bd914c6a-5fc9-4db7-b1f8-dd1e1124f2b2.png)

- 기존에 하나의 서버에서 유지되던 횡단 관심사가 MSA로 넘어오면서 자기들만의 횡단관심사를 가지지 않고 각 서비스들이 공유
- 각각 서비스가 나눠져 있으므로 자바 소스코드과 같은 간단한 방법으로 관심사를 공통으로 처리하기 쉽지 않음
- 따라서, 횡단 관심사를 각 서비스에서 처리하지 않고 API Gateway에서 처리
- 서비스들 간의 횡단 관심사를 처리하기 위해서 Spring Cloud API Gateway에서 filter로 처리

![image](https://user-images.githubusercontent.com/83503188/161539890-e2936078-53d8-4f8c-9957-98fe336ea8dd.png)



## Spring Cloud Config
- 설정 파일을 외부, 중앙에서 관리
- 실행할 때 중앙에서 설정파일을 모아 놓고 클라이언트가 필요한 설정 파일을 가져갈 수 있도록 하는 것

![image](https://user-images.githubusercontent.com/83503188/161539897-7de00279-ce4a-4bed-887f-316087e34ec8.png)

### 설정 파일 보관 방법
1. 서버 안에 저장
![image](https://user-images.githubusercontent.com/83503188/161539911-dc84b691-1ef6-446c-b8b5-30c8c2de610d.png)

2. Git 원격 저장소에 저장

![image](https://user-images.githubusercontent.com/83503188/161539919-e6639e83-c82d-47ba-aad2-7ac14625b61c.png)


### Config 기능
- Config 서버와 Actuator 도움을 통해 실시간으로 앱을 실시간으로 설정파일을 적용할 수 있다.

![image](https://user-images.githubusercontent.com/83503188/161539929-7e422172-1b16-4128-9628-59b7b223a5aa.png)

- gateway가 새로운 설정 파일에 대한 요청(POST / actuator/refresh)을 보낼 수 있다.
- gateway가 config-server에 새로운 설정 파일이 존재하는지에 대한 질의를 한다.
- 새로운 설정파일이 있는 경우 gateway에 해당 파일을 응답
- Config-Server가 파일이 변경됨을 아는 시점 ?
- 변경을 감지하는 것이 아님
- 누군가가 새로운 정보에 대한 요청을 보냈을 때


### 완전 자동화 가능

![image](https://user-images.githubusercontent.com/83503188/161539935-a34df05c-10c6-4370-9f12-a4cf415dd486.png)

#### Config Server
![image](https://user-images.githubusercontent.com/83503188/161539944-42b2f0b9-b07f-48e4-9b0c-77f17570511d.png)

#### Config Client

![image](https://user-images.githubusercontent.com/83503188/161539953-b1860d15-af21-43af-a505-2b50c4c5cd26.png)


