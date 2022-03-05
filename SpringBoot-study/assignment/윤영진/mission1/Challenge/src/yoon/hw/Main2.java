package yoon.hw;

import java.util.*;

public class Main2 {

    // ArrayList, LinkedList, Vector, HashSet 모두 Collection을 상속 받은 클래스
    // 따라서, 모두 다른 클래스이지만 함수의 매개변수로 Collection을 사용하면 4가지 클래스를 모두 받을 수 있다.

    public static void main(String[] args) {

        printItemByIdx(new ArrayList<>(Arrays.asList("a", 10000000, true, 3.14f)));
        printItemByIdx(new LinkedList<>(Arrays.asList("b", 100, false)));
        printItemByIdx(new Vector<>());
        printItemByIdx(new HashSet<>(Arrays.asList(-100, 100, "c", true, 3.14f)));
    }

    public static <E> void printItemByIdx(Collection<E> collection) {
        if (collection.isEmpty()) {
            System.out.println("No Elements\n");
            return;
        }
        System.out.printf("%-5s\t%s%n", "idx", "item");
        int i = 0;
        for (E e : collection) {
            System.out.printf("%-5s\t%s%n", i++, e);
        }
        System.out.println();
    }
}

