# Урок 3. Сериализация
1. Разработайте класс Student с полями String name, int age, transient double GPA (средний балл). Обеспечьте поддержку сериализации для этого класса.  
Создайте объект класса Student и инициализируйте его данными. Сериализуйте этот объект в файл.  
Десериализуйте объект обратно в программу из файла. Выведите все поля объекта, включая GPA, и обсудите, почему значение GPA не было сохранено/восстановлено.  
3. Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).

![3](https://github.com/MaksimZ91/JJ_HW3/assets/72209139/29cc1a90-1d8b-406b-8c91-7987ab2ab014)

## Class Student
   ```java
   package org.example;

import java.io.*;

public class Student implements Serializable, Externalizable {


    private String name;
    private  int age;
    private transient double GPA;

    public Student(String name, int age, double GPA) {
        this.name = name;
        this.age = age;
        this.GPA = GPA;
    }

    public  Student(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", GPA=" + GPA +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = in.readInt();
    }
}
```
## Class Serialize
```java
package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;

public class Serialize {
    private static final String END_FILE_JSON = "json";
    private  static final String END_FILE_BIN = "bin";
    private  static final String END_FILE_XML = "xml";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static Object loadFile(String fileName, Class clazz) throws IOException, ClassNotFoundException {
        return switch (fileName.split("\\.")[1]) {
            case END_FILE_BIN -> readBINFile(fileName);
            case END_FILE_JSON -> readJSONFile(fileName, clazz);
            case END_FILE_XML -> readXMLFile(fileName, clazz);
            default -> null;
        };
    }

    public static void writeToBIN(String fileName, Object ob) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(ob);
        }
    }
    public static void writeToJSON(String fileName, Object ob) {
        try {
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(fileName), ob);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToXML(String fileName, Object ob) {
        try {
            xmlMapper.writeValue(new File(fileName), ob);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  static Object readJSONFile(String fileName, Class clazz)  {
        try {
            return objectMapper.readValue(new File(fileName), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object readXMLFile(String fileName, Class clazz)  {
        try {
            return xmlMapper.readValue(new File(fileName), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static  Object readBINFile(String fileName) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return objectInputStream.readObject();
        }
    }

}
```
### Class Main
```java
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
```

