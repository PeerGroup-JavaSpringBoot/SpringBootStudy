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
		System.out.println("�� �̸��� " + name + "�̰� ���� �ź��� "+ Studentinfo + "�л��Դϴ�");
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
		System.out.println("�� �̸��� " + name + "�̰� ���� �ź��� "+ Lecturerinfo + "�����Դϴ�");
	}
}

