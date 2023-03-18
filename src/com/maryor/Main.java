package com.maryor;


public class Main {
    public static void main(String[] args) {
        final BankAccount account = new BankAccount("12345-678", 1000.00);

//        Thread trThread1 = new Thread() {
//            public void run() {
//                account.deposit(300.00);
//                account.withdraw(50.00);
//            }
//        };
//
//        Thread trThread2 = new Thread() {
//            public void run() {
//                account.deposit(203.75);
//                account.withdraw(100.00);
//            }
//        };

        Thread trTread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(300.00);
                account.withdraw(50.00);
            }
        });

        Thread trTread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(203.75);
                account.deposit(100.00);
            }
        });

        trTread1.start();
        trTread2.start();
    }

}