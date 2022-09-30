package com.train.restAsssured.utility;

public class PayLoad {

    public static String getBook(String isbn, String aisle){
        return "{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java124\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foe\"\n" +
                "}\n";
    }
}
