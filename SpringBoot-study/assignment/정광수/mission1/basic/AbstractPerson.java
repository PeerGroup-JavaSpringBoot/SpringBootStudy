package com.company.basic;

//상속 클래스는 부모에서 선언/정의를 모두하여 자식은 메소드 변수를 그대로 이용할 수 있다.
public abstract class AbstractPerson implements Person{ //Person Interface를 구현하는 Method
    protected String name;
    protected int age;
    
    //추상 클래스의 생성자
    public AbstractPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //Person Interface를 구현한 추상 클래스이기 때문에
    //메소드 speak()를 오버라이딩 해야한다.
    @Override
    public void speak() {
        System.out.println(String.format("Hi my name is %s",this.name));
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    @Override
    public String toString() {
        return "AbstractPerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
