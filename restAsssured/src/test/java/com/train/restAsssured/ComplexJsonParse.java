package com.train.restAsssured;

import com.train.restAsssured.utility.Utils;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {
    public static void main(String[] args) {
        String payload="{\n" +
                "\n" +
                "        \"dashboard\": {\n" +
                "\n" +
                "        \"purchaseAmount\": 910,\n" +
                "\n" +
                "                \"website\": \"rahulshettyacademy.com\"\n" +
                "\n" +
                "    },\n" +
                "\n" +
                "        \"courses\": [\n" +
                "\n" +
                "        {\n" +
                "\n" +
                "            \"title\": \"Selenium Python\",\n" +
                "\n" +
                "                \"price\": 50,\n" +
                "\n" +
                "                \"copies\": 6\n" +
                "\n" +
                "        },\n" +
                "\n" +
                "        {\n" +
                "\n" +
                "            \"title\": \"Cypress\",\n" +
                "\n" +
                "                \"price\": 40,\n" +
                "\n" +
                "                \"copies\": 4\n" +
                "\n" +
                "        },\n" +
                "\n" +
                "        {\n" +
                "\n" +
                "            \"title\": \"RPA\",\n" +
                "\n" +
                "                \"price\": 45,\n" +
                "\n" +
                "                \"copies\": 10\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "]\n" +
                "\n" +
                "    }";
        JsonPath json= Utils.rawToJSON(payload);

        //1. Print No of courses returned by API
        int count=json.getInt("courses.size()");
        System.out.println(count);

//
//2.Print Purchase Amount
        int amount = json.getInt("dashboard.purchaseAmount");
        System.out.println(amount);
//3. Print Title of the first course
        String title = json.getString("courses[0].title");
        System.out.println(title);
//4. Print All course titles and their respective Pricestit

        for (int i=0;i<count;i++){
            String price = json.getString("courses[" + i + "].price");
            String result = (json.getString("courses[" + i + "].title")).concat("--").concat(price);
            System.out.println(result);
        }
//5. Print no of copies sold by RPA Course
        int totalRPACopies=0;
        for (int i=0;i<count;i++){
            String title1 = json.getString("courses[" + i + "].title");
            if("RPA".equals(title1)){
                int copies=json.getInt("courses[" + i + "].copies");
                totalRPACopies=totalRPACopies+copies;
            }
        }
        System.out.println(totalRPACopies);


//6. Verify if Sum of all Course prices matches with Purchase Amount
        int totalSum=0;
        for (int i=0;i<count;i++){
            int totalprice=json.getInt("courses[" + i + "].copies") * json.getInt("courses[" + i + "].price");
            totalSum=totalSum+totalprice;
        }

        Assert.assertEquals(totalSum,amount);

    }


}
