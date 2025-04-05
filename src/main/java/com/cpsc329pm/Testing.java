package com.cpsc329pm;

public class Testing {
    public static void main(String[] args) {
        DataStorage dataStorage = new DataStorage("data.json");

        dataStorage.addData("test", "test", "test");
        System.out.println("Added test data 1");
        dataStorage.addData("test1", "test1", "test1");
        System.out.println("Added test data 2");
        dataStorage.addData("test2", "test2", "test2");
        System.out.println("Added test data 3");
    }
}
