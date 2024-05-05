package Serialization;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import SerializationPojo.AddPlace;
import SerializationPojo.LocationClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SerializarionExample {
	
	//Google API to add a place
	
	@Test
	public void SerializatingGoogleLocationPayload()
	{
		//Conversion of java object into json payload
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		AddPlace place=new AddPlace();
		
		LocationClass loc=new LocationClass();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		
		place.setLocation(loc);
		place.setAccuracy(70);
		place.setName("Test Place");
		place.setPhone_number("(+91) 983 893 3937");
		place.setAddress("29, side layout, cohen 09");
		
		/*List<String> listOfTypes=new ArrayList<String>();
		listOfTypes.add("shoe park");
		listOfTypes.add("shop"); */
		//OR
		List<String> listOfTypes=Arrays.asList("shoe park","shop");
		place.setTypes(listOfTypes);
		place.setWebsite("http://google.com");
		place.setLanguage("English");
		
		
		Response response=given().queryParam("key", "qaclick123").body(place)
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response();
		
		System.out.println(response.asString());
	}

}
