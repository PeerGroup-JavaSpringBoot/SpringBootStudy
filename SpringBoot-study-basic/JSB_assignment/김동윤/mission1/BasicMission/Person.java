public interface Person {
	void speak();
}

abstract class AbstractPerson implements Person{
	String name;
	int age;
	AbstractPerson(){}
	AbstractPerson(int age,String name){
		this.age=age;this.name=name;
	}
}

class Student extends AbstractPerson{
	String Studentinfo;
	
	Student(){};
	Student(int age,String name, String info){
		super(age, name);
		this.Studentinfo= info;
	}
	@Override
	public void speak() {
		System.out.println("내 이름은 " + name + "이고 나의 신분은 "+ Studentinfo + "학생입니다");
	}
}

class Lecturer extends AbstractPerson{
	String Lecturerinfo;
	Lecturer(){};
	Lecturer(int age,String name, String info){
		super(age, name);
		this.Lecturerinfo= info;
	}
	@Override
	public void speak() {
		System.out.println("내 이름은 " + name + "이고 나의 신분은 "+ Lecturerinfo + "강사입니다");
	}
}

