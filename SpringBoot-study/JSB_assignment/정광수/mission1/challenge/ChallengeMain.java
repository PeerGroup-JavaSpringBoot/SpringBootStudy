package com.company.challenge;
import com.company.basic.Lecturer;
import com.company.basic.Person;
import com.company.basic.Student;

import java.util.*;

public class ChallengeMain {
    public static void main(String[] args) {
        //Collection Interface 구현체들

        ArrayList arrayList; // -> Collection 클래스의 상속, List 인터페이스의 구현체
        LinkedList linkedList; // -> Collection 클래스의 상속, List 인터페이스의 구현체
        Vector vector; // -> Collection 클래스의 상속, List 인터페이스의 구현체
        HashSet hashSet; // -> Collection 클래스의 상속, Set 인터페이스의 구현체

        //Set과 List의 차이
        //Set은 순서 보장 X, 중복 X
        //List는 순서 보장 O, 중복 O

        Person student1 = new Student("Kim",44);
        Person student2 = new Student("Lee",29);
        Person student3 = new Student("Park",30);

        Person lecturer = new Lecturer("Teacher",66);
        List<Person> everyone = new ArrayList<>();
        everyone.add(student1);
        everyone.add(student2);
        everyone.add(student3);
        everyone.add(lecturer);

        printItems(everyone);
    }

    public static <T> void printItems(Iterable<T> iterable){
        Iterator<T> iterator = iterable.iterator();
        if(!iterator.hasNext()){
            System.out.println("No Elements");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("idx\t\titem\n");
        for(int i = 1; iterator.hasNext() ; i++){
            T item = iterator.next();
            sb.append(String.format("%d\t\t%s\n", i, item.toString()));
        }

        System.out.println(sb);
    }
}