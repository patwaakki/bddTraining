package com.train.restAsssured;

import com.train.restAsssured.utility.Utils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Basics {
    public static void main(String[] args) throws IOException {

        //given --all input details,
        // when --submit the api,--resource,http method
        // then --validate the response
        Basics bb= new Basics();
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\AkshayKumar\\Documents\\bddTrainig\\project\\bddTraining1\\restAsssured\\src\\test\\resources\\dataFiles\\bookData.json"))))

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
