package com.maryor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Challenge9 {
    public static void main(String[] args) {
        final NewTutor tutor = new NewTutor();
        final NewStudent student = new NewStudent(tutor);
        tutor.setStudent(student);

        Thread tutorThread = new Thread(tutor::studyTime);

        Thread studentThread = new Thread(student::handInAssignment);

        tutorThread.start();
        studentThread.start();
    }
}

class NewTutor {
    private NewStudent student;
    private final Lock lock = new ReentrantLock();

    public void setStudent(NewStudent student) {
        this.student = student;
    }

    public void studyTime() {

        if (lock.tryLock()) {
            try {
                System.out.println("Tutor has arrived");
                synchronized (this) {
                    try {
                        // wait for student to arrive
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    student.startStudy();
                    System.out.println("Tutor is studying with student");
                }
            } finally {
                lock.unlock();
            }

        }
    }

    public void getProgressReport() {
        // get progress report
        System.out.println("Tutor gave progress report");
    }
}

class NewStudent {

    private final NewTutor tutor;
    private final Lock lock = new ReentrantLock();

    NewStudent(NewTutor tutor) {
        this.tutor = tutor;
    }

    public void startStudy() {
        // study
        System.out.println("Student is studying");
    }

    public void handInAssignment() {
        if (lock.tryLock()) {
            try {
                tutor.getProgressReport();
                synchronized (tutor) {
                    System.out.println("Student handed in assignment");
                    tutor.notifyAll();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
