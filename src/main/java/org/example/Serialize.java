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
