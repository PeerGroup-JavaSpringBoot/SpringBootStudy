# 1. 보드 테스트

## psot 생성 시 이것이 속할 board_id 지정

- id 0 , id 1 생성 시 board id 와 password 지정
- 
![image](https://user-images.githubusercontent.com/76711238/155147853-2c023afa-a42f-428e-b811-e4b6ad52e362.png)

![image](https://user-images.githubusercontent.com/76711238/155148307-a81391ae-652e-4661-b53b-0d8006982a7a.png)

![image](https://user-images.githubusercontent.com/76711238/155148171-e62f30b0-66d7-49e8-bb39-8422932eb841.png)

## post 불러올 때 post가 속한 board_id와 url의 board_id가 같지 않으면 에러

![image](https://user-images.githubusercontent.com/76711238/155148443-dd976e9e-c88e-40d6-b4c5-1319d097cc5a.png)

## 같아야만 잘 반환 

![image](https://user-images.githubusercontent.com/76711238/155148509-6ec98b5a-6631-4cd2-8f28-d0374f956483.png)
_________________________________________________________________
<br><br><br>

# 2. 비번 테스트

## 현 상황 (get - all)

![image](https://user-images.githubusercontent.com/76711238/155151596-c2fe815c-4c99-4fc4-9ecd-dacd430237f6.png)

## 비번 확인 여부 처리 코드 (`PostService` 클래스)

![image](https://user-images.githubusercontent.com/76711238/155153997-7462e115-2d86-4f08-806c-a40411459bf2.png)

- 유저가 입력한 패스워드와 post의 비번을 equal을 통해서 비교
- 비번이 일치하면 로그에 success 메시지 뜨게, 불일치하면 error 뜨게 해놓음

## 비밀번호 같을 때

- id 0 의 아이디 값 post의 비번 (1234) & 유저가 작성한 pw 값 (1234) 적어서 넘기면
- 
![image](https://user-images.githubusercontent.com/76711238/155152331-1c26e867-e72f-4c02-b50d-ddac64caa70d.png)

- 로그창에 아래와 같이 찍힘
- 
![image](https://user-images.githubusercontent.com/76711238/155153136-94b05db0-364a-417c-9739-aeb80ea49f96.png)


## 비번 다를 때 
(id 0 이 사라지자 기존 id1이었던 아이가 id 0이 됨)
- id 0을 지우고자 시도할 때 비번이 12345 인데 비번을 1234로 해보겠음
- 
![image](https://user-images.githubusercontent.com/76711238/155154265-93e64478-59bf-4542-81f0-9ac905137f8c.png)

![image](https://user-images.githubusercontent.com/76711238/155154185-4031d5b0-ab87-495d-b688-a642911ad384.png)


## 에러는 성공의 어머니 ..^^

![image](https://user-images.githubusercontent.com/76711238/155150447-96665a98-3879-4e6e-b33b-3913a4ccc51f.png)

위와 같이 인자로 받은 pw와 id의 pw가 동일한데도 같지 않다고 인식함 -> log를 찍어봄

![image](https://user-images.githubusercontent.com/76711238/155150888-5e57541a-ddc7-4dce-a82e-8541dd04a73f.png)

-> 인자 pw와 dto의 pw 둘다 string으로 선언했었음 => 같음 여부를 등호가 아닌 equals로 했어야 한다 ^^!! JAVA 복습하장..
