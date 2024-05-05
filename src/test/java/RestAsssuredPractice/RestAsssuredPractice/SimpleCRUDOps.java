package RestAsssuredPractice.RestAsssuredPractice;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;
public class SimpleCRUDOps {
	
	// given() - whatever it given for the API -> query parameter, header, body
	// When() - defines type of action or https method, resource(path paramter) -> GET, Put, Post, Delete, Patch and Resource(Path Parameters) as well.
	// Then() - validates the response.
	
	
	public static void main(String[] args)
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Post Example
		String responseBodyAsString=given().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Rahul New house\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"English-IN\"\r\n"
				+ "}").when().log().all().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.header("Content-Length", equalTo("194"))
				.body("status", equalTo("OK"))
				.body("scope", containsString("PP"))
				.extract().response().asString();
		
		JsonPath js=new JsonPath(responseBodyAsString);
		String PlaceID=js.getString("place_id");
		
		// Get the Place 
		
		given().queryParam("key", "qaclick123").queryParam("place_id", PlaceID).when().log().all().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200)
		.body("location.size()", greaterThan(0))
		.body("location.longitude",startsWith("3"));
		
		
		//  delete a resource
		given().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "    \"place_id\":\""+PlaceID+"\"\r\n"
				+ "}\r\n"
				+ "").when().log().all().delete("maps/api/place/delete/json").then().log().all().assertThat().statusCode(200);
		
		// Get the Place which is already deleted
		
				given().queryParam("key", "qaclick123").queryParam("place_id", PlaceID).when().log().all().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(404);
				
	}
	
	

	
	
}
