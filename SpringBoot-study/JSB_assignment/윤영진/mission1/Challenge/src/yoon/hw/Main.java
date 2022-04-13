package yoon.hw;

import java.util.*;

public class Main {

    // ArrayList, LinkedList, Vector, HashSet 모두 Collection을 상속 받은 클래스
    // 따라서, 모두 다른 클래스이지만 함수의 매개변수로 Collection을 사용하면 4가지 클래스를 모두 받을 수 있다.

    public static void main(String[] args) {

        // ArrayList
        List<Item> arrlist = new ArrayList<>();
        arrlist.add(new Item(0, "Item 1"));
        arrlist.add(new Item(1, "Item 2"));
        arrlist.add(new Item(2, "Item 3"));
        arrlist.add(new Item(3, "Item 4"));
        System.out.println("#ArrayList");
        print(arrlist);

        //LinkedList
        List<Item> linkedlist = new LinkedList<>();
        linkedlist.add(new Item(0, "Item 1"));
        linkedlist.add(new Item(1, "Item 2"));
        linkedlist.add(new Item(2, "Item 3"));
        linkedlist.add(new Item(3, "Item 4"));
        System.out.println("#LinkedList");
        print(linkedlist);

        //Vector
        List<Item> vector = new Vector<>();
//        vector.add(new Item(0,"Item 1"));
//        vector.add(new Item(1,"Item 2"));
//        vector.add(new Item(2,"Item 3"));
//        vector.add(new Item(3,"Item 4"));
        System.out.println("#Vector");
        print(vector);

        // HashSet
        Set<Item> hashSet = new HashSet<>();
        hashSet.add(new Item(0, "Item 1"));
        hashSet.add(new Item(1, "Item 2"));
        hashSet.add(new Item(2, "Item 3"));
        hashSet.add(new Item(3, "Item 4"));
        System.out.println("#HashSet");
        print(hashSet);



    }

    private static void print(Collection<Item> collection) {
        if (collection.size() == 0) {
            System.out.println("No Elements");
        } else {
            System.out.println("idx\titem");
            collection.stream().forEach(System.out::println);
        }
        System.out.println();
    }


}
