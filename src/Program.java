import game.GameWindow;

import java.util.*;
import java.math.*;
/**
 * Created by sonng on 7/4/2017.
 */
public class Program {
//    public static void main(String[] args) {
////        System.out.println("Hello World");
//
////        int i = 0;
////        char c = 'c';
////        String myName = "SS";
////        float f = 5f;
////        double d = 5.0d;
////        boolean check = true;
////        System.out.println(String.format("i = %s c = %s myName = %s", i, c , myName));
//
////        float a = 0;
////        float b = 1;
////        float c = -1;
////
////        float delta = b * b - 4 * a * c;
////        a = 2*a; //Thay doi a
////        if (delta == 0) {
////            System.out.println(String.format("X = ", -b/(a)));
////        }
////        else if (delta < 0) {
////            System.out.println("Vo nghiem");
////        }
////        else {
////            float canDelta = (float)Math.sqrt(delta);
////            System.out.println(String.format("X1 = %s", (-b + canDelta)/a));
////            System.out.println(String.format("X1 = %s", (-b - canDelta)/a));
////        }
////        int[] arr = {1, 2, 3};
////        for (int x: arr) {
////            System.out.println(x);
////        }
//
//        List<Integer> numbers = new ArrayList<>();
//        List<Integer> newNumbers = new ArrayList<>();
//        numbers.add(10);
//        numbers.add(11);
//        numbers.add(2);
//        numbers.add(1);
//        numbers.add(0);
//        numbers.add(10);
//
////        for (Integer number : numbers) {
////            if (number == 10) {
////                newNumbers.add(9);
////            }
////        }
////        numbers.addAll(newNumbers);
////        newNumbers.clear();
////
//        ListIterator<Integer> iterator = numbers.listIterator();
////        Iterator<Integer> iterator = numbers.iterator();
////        while (iterator.hasNext()) {
////            Integer number = iterator.next();
////            if (number % 2 == 0) {
////                iterator.remove();
////            }
////        }
//
//        System.out.println(numbers);
//    }
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.run();
    }
}

