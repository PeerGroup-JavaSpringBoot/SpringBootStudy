package com.company.basic;

public class Student extends AbstractPerson{
    public Student(String name,int age){
        super(name,age);
    }
    @Override
    public void speak() {
        /*String.format() 메소드 -> 오버로드 Format(String, Object)
        문자열에 있는 하나 이상의 형식 항목을 지정된 개체의 문자열로 표현
        */
        System.out.println(String.format("제 이름은 %s, 이고 학생입니다.",getName()));
        System.out.println(String.format("나이는 %d살 입니다",getAge()));
    }
}
