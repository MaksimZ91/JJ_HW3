package org.example;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Student st = new Student("Иван", 21, 4.2);
        Serialize.writeToBIN("Student.bin", st);
        System.out.println("Объект " + st +  " сериализован в .bin");
        Serialize.writeToJSON("Student.json", st);
        System.out.println("Объект " + st +  " сериализован в .json");
        Serialize.writeToXML("Student.xml", st);
        System.out.println("Объект " + st +  " сериализован в .xml");
        System.out.println("=====================================");
        Student stBin = (Student) Serialize.loadFile("Student.bin", Student.class);
        System.out.println("Объект " + stBin +  " десериализован из .bin");
        Student stJSON = (Student) Serialize.loadFile("Student.json", Student.class);
        System.out.println("Объект " + stJSON +  " десериализован из .json");
        Student stXML = (Student) Serialize.loadFile("Student.xml", Student.class);
        System.out.println("Объект " + stXML +  " десериализован из .xml");


    }
}