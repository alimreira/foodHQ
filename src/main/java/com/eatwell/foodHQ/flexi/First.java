package com.eatwell.foodHQ.flexi;

import java.util.Scanner;

public class First {


    public static void main(String[] args) {
        //write a program that reads a positive integer N from the user, and indicates if N is a prime
        Scanner can = new Scanner(System.in);
        System.out.println("Enter an integer");
        int num = can.nextInt();
        int sum = 0;

//            if(num <=0){
//                System.out.println(num + " is an invalid number");
//        }else {
        int i =2;
        while (i <= num/2){
            if(num%i==0){
                sum+= i;
            }
            ++i;
        }
        String st = sum>1? num+ " is not a prime number ": num+" is a prime number";
        System.out.println(st);
    }

}


