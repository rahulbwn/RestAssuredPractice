package RestAsssuredPractice.RestAsssuredPractice;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class HandlingStaticjsonFiles {
	// Json File Content covert to String in two stages -> 1. Convert Json to Bytes by reading the Bytes and 2. Bytes to String
	@Test
	public void HandleStaticJsonFiles() throws IOException
	{
		RestAssured.baseURI="http://216.10.245.166";
		//new String(Files.readAllBytes(Paths.get("D:\\Tutorials Docs\\API Testing\\Practice\\LibraryAPI.json")))
		given().header("Content-Type","application/json").body(Files.readString(Paths.get("D:\\\\Tutorials Docs\\\\API Testing\\\\Practice\\\\LibraryAPI.json")))
		.when().log().all().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200);
	}

}
