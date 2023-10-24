import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Приветствую! Введите арифметическое выражение (например, '2 + 3' или 'IV * V'):");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("До свидания!");
                break;
            }

            String result = calc(input);
            System.out.println("Результат: " + result);
        }
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            return "Неверный формат ввода. Используйте пример: '2 + 3' или 'IV * V'.";
        }

        String firstOperand = tokens[0];
        String operator = tokens[1];
        String secondOperand = tokens[2];

        try {
            int a = convertToArabic(firstOperand);
            int b = convertToArabic(secondOperand);
            int result = performOperation(a, b, operator);
            return convertToRoman(result);
        } catch (NumberFormatException e) {
            return "Ошибка: Неправильный формат чисел или оператора.";
        }
    }

    private static int convertToArabic(String roman) {
        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanToArabicMap.get(roman.charAt(i));
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            prevValue = currentValue;
        }
        return result;
    }

    private static String convertToRoman(int arabic) {
        if (arabic <= 0 || arabic > 3999) {
            return "Римские числа поддерживаются в диапазоне от 1 до 3999.";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arabicToRomanMap.length; i++) {
            while (arabic >= arabicToRomanMap[i]) {
                arabic -= arabicToRomanMap[i];
                result.append(romanNumerals[i]);
            }
        }
        return result.toString();
    }

    private static int performOperation(int a, int b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                throw new IllegalArgumentException("Недопустимый оператор: " + operator);
        }
    }

    private static final Map<Character, Integer> romanToArabicMap = new HashMap<>();
    private static final int[] arabicToRomanMap = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    static {
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);
    }
}
