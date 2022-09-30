package com.train.bookAPI;

import com.train.restAsssured.utility.PayLoad;
import com.train.restAsssured.utility.Utils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJSON {
    @Test(dataProvider = "bookData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI ="http://216.10.245.166";
//        String isbn="bnhg";
//        String aisle="dffg4546";
        String response = given().header("Content-Type", "application/json").body(PayLoad.getBook(isbn,aisle))
                .when().post("Library/Addbook.php")
                .then().log().all().extract().asString();
        JsonPath js= Utils.rawToJSON(response);
        Assert.assertEquals(js.getString("Msg"),"successfully added");

    }

    @DataProvider(name="bookData")
    public Object[][] getBooks(){
        return new Object[][]{{"asbf","4325435"},{"sfsf","454656"},{"dsfd","454665"}};
    }
}
