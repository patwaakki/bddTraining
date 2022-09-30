package com.train.restAsssured;

import com.train.restAsssured.utility.Utils;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {

    public static void main(String[] args) {
        RestAssured.baseURI = "http://localhost:8080/";

        //call login api
        SessionFilter sessionFilter = new SessionFilter();
        String loginRes = given().log().all().header("Content-Type", "application/json").body("{ \"username\": \"akshaypatwa999\", \"password\": \"satyam10#\" }")
                .filter(sessionFilter)
                .when().post("rest/auth/1/session")
                .then().log().all().extract().response().asString();
        JsonPath js = Utils.rawToJSON(loginRes);
        String jsession = js.getString("session.name");
        String cookie = js.getString("session.value");

        //add comment
//header("cookie", jsession.concat("=").concat(cookie))
        given().log().all().pathParam("id", "10002").header("Content-Type", "application/json").body("{\n" +
                        "    \"body\": \"adding new comments \",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .filter(sessionFilter)
                .when().post("rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201);

        //add attachment

        given().log().all().pathParam("id", "10002").header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data")
                .filter(sessionFilter)
                .multiPart("file",new File("C:\\Users\\AkshayKumar\\Documents\\bddTrainig\\project\\bddTraining1\\restAsssured\\src\\test\\resources\\dataFiles\\test.txt"))
                .when().post("rest/api/2/issue/{id}/attachments")
                .then().log().all().assertThat().statusCode(200);
    }



}
