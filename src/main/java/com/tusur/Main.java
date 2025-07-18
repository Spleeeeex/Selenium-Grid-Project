package com.tusur;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите длины сторон треугольника (1-100):");
        System.out.print("A = ");
        int a = scanner.nextInt();
        System.out.print("B = ");
        int b = scanner.nextInt();
        System.out.print("C = ");
        int c = scanner.nextInt();

        if (isValidTriangle(a, b, c)) {
            String triangleType = determineTriangleType(a, b, c);
            System.out.println("Тип треугольника: " + triangleType);
        } else {
            System.out.println("Это не треугольник!");
        }
    }

    private static boolean isValidTriangle(int a, int b, int c) {
        return a > 0 && b > 0 && c > 0 &&
                a + b > c && a + c > b && b + c > a &&
                a <= 100 && b <= 100 && c <= 100;
    }

    private static String determineTriangleType(int a, int b, int c) {
        if (a == b && b == c) {
            return "Равносторонний";
        } else if (a == b || a == c || b == c) {
            return "Равнобедренный";
        } else if (isRightAngled(a, b, c)) {
            return "Прямоугольный";
        } else {
            return "Разносторонний";
        }
    }

    private static boolean isRightAngled(int a, int b, int c) {
        int max = Math.max(a, Math.max(b, c));
        if (max == a) {
            return b*b + c*c == a*a;
        } else if (max == b) {
            return a*a + c*c == b*b;
        } else {
            return a*a + b*b == c*c;
        }
    }
}