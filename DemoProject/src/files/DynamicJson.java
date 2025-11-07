package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {

        RestAssured.baseURI = "http://216.10.245.166";

        String response = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
//                .body(payload.Addbook())   //hard coded sent data from payload.addbook
//                .body(payload.Addbook( "abc",  "123"))  //hard coded send data but through parameter from here
                .body(payload.Addbook( isbn,  aisle))  //pass data through data provider which can pass array of elements with any num of data
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);


//        RestAssured.given().when().then();


    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        // 3 sets of data
        return new Object[][] {
                {"abcd", "1234"},
                {"efgh", "5678"},
                {"ijkl", "9101"}
        };
    }
}
