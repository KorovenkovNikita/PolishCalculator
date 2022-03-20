package com.company;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String DIVIDE = "/";
    private static final String MULTIPLY = "*";
    private static final String OPEN = "(";
    private static final String CLOSE = ")";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String string = "(10+22/2)*(5-3)*2*((10-5)+6/3/2)";
        List<String> arrayListString = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9\\.]+|[+\\-*/]|[()]");
        Matcher matcher = pattern.matcher(string);
        int count0 = 0;
        while (matcher.find()) {
            arrayListString.add(count0, matcher.group());
            count0++;
        }
        System.out.println(arrayListString);
        System.out.println(getPolishNotation(arrayListString));
        System.out.println(evalAnswer(getPolishNotation(arrayListString)));
    }

    private static double evalAnswer(Stack<String> polishNotation) {
        Stack<Double> stack = new Stack<>();
        double val;
        for (String sign : polishNotation) {
            if (!isDigit(sign)) {
                if (sign.equals("+")) {
                    stack.push(stack.pop() + stack.pop());
                } else if (sign.equals("-")) {
                    val = stack.pop();
                    stack.push(stack.pop() - val);
                } else if (sign.equals("*")) {
                    stack.push(stack.pop() * stack.pop());
                } else if (sign.equals("/")) {
                    val = stack.pop();
                    stack.push(stack.pop() / val);
                }
            } else {
                stack.push(Double.parseDouble(sign));
            }
        }
        return stack.pop();
    }

    public static Stack<String> getPolishNotation(List<String> arrayListString) {
        Stack<String> polishNotation = new Stack<>();
        Stack<String> signs = new Stack<>();
        for (String string : arrayListString) {
            if (isDigit(string)) {
                polishNotation.push(string);
            } else {
                if (string.equals(OPEN)) {
                    signs.push(string);
                    continue;
                }
                if (string.equals(CLOSE)) {
                    while (!signs.peek().equals(OPEN)) {
                        polishNotation.push(signs.pop());
                    }
                    signs.pop();
                } else {
                    if (!signs.isEmpty() && getPriority(signs.peek()) >= getPriority(string)) {
                        polishNotation.push(signs.pop());
                    }
                    signs.push(string);
                }
            }
        }
        while (signs.size() != 0) {
            polishNotation.push(signs.pop());
        }
        return polishNotation;
    }

    private static boolean isDigit(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private static int getPriority(String string) {
        if (string.contains(DIVIDE) || string.contains(MULTIPLY)) {
            return 3;
        } else if (string.contains(MINUS) || string.contains(PLUS)) {
            return 2;
        }
        return 0;
    }
}

