package com.eatwell.foodHQ.flexi;

import java.util.Scanner;

public class Second {

    public static void main(String[] args) {
        //write a program that reads a sequence of positive integers, and stops when the
        //user fills a negative value and shows the maximum and the minimum of these values.
        //boolean expression determines how many times loop runs and when the loop ends. When the boolean
        //expression is negated or reversed, the loop ends.
        Scanner can = new Scanner(System.in);
        System.out.println("Enter an integer");
        int num = can.nextInt();
        int min = num;
        int max = num;
        while (num >=0){
            min = min < num ? num: min;
            max = max > num ? num : max;
            System.out.println("Enter another integer ");
            num = can.nextInt();

        }
        System.out.println(min + " min " + max + " max");

    }
}

