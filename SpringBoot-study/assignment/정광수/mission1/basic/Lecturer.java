package com.company.basic;

public class Lecturer extends AbstractPerson{
    //추상 클래스 AbstractPerson의
    public Lecturer(String name,int age){
        //Lecturer 객체는 super() 메소드를 활용하여 상속받은 추상 클래스 AbstractPerson의 생성자로 생성한다.
        super(name,age);
    }
    //Lecturer클래스가 AbstractPesron를 상속받았고
    //AbstractPerson 클래스는 Person 인터페이스를 구현한 클래스이다.
    //따라서, Lecturer 클래스도 Person 인터페이스의 speak() 메소드를 오버라이딩해서 구현해주어야한다.
    @Override
    public void speak() {
        System.out.println(String.format("제 이름은 %s, 이고 강사입니다.",getName()));
        System.out.println(String.format("나이는 %d살 입니다",getAge()));
    }
}
