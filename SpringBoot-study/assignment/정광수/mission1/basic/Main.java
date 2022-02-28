package com.company.basic;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person student1 = new Student("Kim",44);
        Person student2 = new Student("Lee",29);
        Person student3 = new Student("Park",30);
        //Person 인터페이스를 통해 구현한 클래스 Student
        //Person 인터페이스 변수 student#n 에 Student의 객체 저장
        //인터페이스 변수는 참조타입이기 때문에 구현 객체가 대입될 경우 구현 객체의 주소가 저장된다.

        Person lecturer = new Lecturer("Teacher",66);
        List<Person> everyone = new ArrayList<>();
        everyone.add(student1);
        everyone.add(student2);
        everyone.add(student3);
        everyone.add(lecturer);
        for(Person p : everyone){
            p.speak();
        }
    }
}
