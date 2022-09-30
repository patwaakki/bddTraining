package com.train.restAsssured;

import com.train.restAsssured.utility.Utils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Basics {
    public static void main(String[] args) {

        //given --all input details,
        // when --submit the api,--resource,http method
        // then --validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Frontline house\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"29, side layout, cohen 09\",\n" +
                        "  \"types\": [\n" +
                        "    \"shoe park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"http://google.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}\n")
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();


        System.out.println("response" + response);
        JsonPath path = new JsonPath(response);
        String place_id = path.getString("place_id");
        System.out.println(place_id);
        //add place -->update place -->get place to validate if new address present in response

        //put api
        String address = "70 Summer walk, USA";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + place_id + "\",\n" +
                        "\"address\":\"" + address + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("/maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));


        //get api

        String getRes = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
                .when().get("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).body("address", equalTo(address)).extract().response().asString();

        JsonPath getJSon = Utils.rawToJSON(getRes);
        String actualAddress = getJSon.getString("address");

        Assert.assertEquals(actualAddress, address);


    }


}
