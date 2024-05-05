package RestAsssuredPractice.RestAsssuredPractice;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

public class DynamicPayloadsHandlingWithParameterization {
	
	@Test(dataProvider="BooksData")
	public void DynamicPayloadsHandling(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		
		String responsePayload=given().header("Content-Type","application/json").body(Payloads.getBook(isbn,aisle))
		.when().log().all().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=ReusablesMethods.rawToJson(responsePayload);
		String actualMessage=js.get("Msg");
		Assert.assertEquals(actualMessage, "successfully added");
		
		// Deleting the data created
		String BookID=js.get("ID");
		
		
		String responseDelPayload=given().header("Content-Type","application/json").body(Payloads.deleteBook(BookID))
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2=ReusablesMethods.rawToJson(responseDelPayload);
		String actualDelSuccessMessage=js2.get("msg");
		
		Assert.assertEquals(actualDelSuccessMessage, "book is successfully deleted");
	}
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		// return new Object[][]{array1,array2,array3}
		
		return new Object[][] {
			{"ghuds","34567"},
			{"ifnbdh","82466"},
			{"dhjshs","82691"}
			};
	}
	
}
