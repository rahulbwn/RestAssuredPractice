package SpecBuilders;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import DeSearializationPoJo.GetCourses;
import DeSearializationPoJo.webAutomation;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class SpecBuilderExample {
	// We Will convert response payload into java object
	@Test
	public void DeSerialization()
	{
		//Request Specification for Token
		RequestSpecification tokenReqSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addFormParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.addFormParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.addFormParam("grant_type","client_credentials")
		.addFormParam("scope", "trust")
		.build();
		
		//Response Specification for Token
		ResponseSpecification tokenResSpec=new ResponseSpecBuilder().log(LogDetail.ALL).build();
		
		
		// Get the token from Authorization Server
		Response response=given().spec(tokenReqSpec)
		.when().log().all().post("oauthapi/oauth2/resourceOwner/token")
		.then().spec(tokenResSpec).extract().response();
		
		JsonPath js=new JsonPath(response.toString());
		String Token=js.getString("access_token");
		
		// Get Course details from OAuth API
		
		GetCourses courseDetails=given().queryParam("access_token", Token).when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(DeSearializationPoJo.GetCourses.class);
		System.out.println(courseDetails.getInstructor());
		System.out.println(courseDetails.getLinkedIn());
		
		//getting Course title of  web automation course using index, (not a good approach because index can varry in json payload)
		String webAutomationCourseTitle=courseDetails.getCourses().getWebAutomation().get(0).getCourseTitle();
		System.out.println(webAutomationCourseTitle);
		
		//Better Approach -> using loop
		List<webAutomation> listOfWebAuto=courseDetails.getCourses().getWebAutomation();
		int sizeOfCourseList=listOfWebAuto.size();
		String expectedTitle="Selenium Webdriver Java";
		for(int i=0;i<sizeOfCourseList;i++)
		{
			if(listOfWebAuto.get(i).getCourseTitle().equals(expectedTitle))
			{
				System.out.println(listOfWebAuto.get(i).getPrice());
				break;
			}
		}
		
		//Total amount of all courses for webAutomation
		int total=0;
		for(int i=0;i<sizeOfCourseList;i++)
		{
			total+=Integer.parseInt(listOfWebAuto.get(i).getPrice());		
			System.out.println(listOfWebAuto.get(i).getCourseTitle());
		}
		System.out.println(total);	
		
		//In Practical, we assert the all courses titles with expected values
		
		String[] expectedCourses= {"Selenium Webdriver Java","Protractor","Cypress"}; //Expected Array
		List<String> actualCourses=new ArrayList<String>(); // Preferred ArrayList because of dynamic size
		
		for(int i=0;i<sizeOfCourseList;i++)
		{
			actualCourses.add(listOfWebAuto.get(i).getCourseTitle());
		}
		
		//expectedCourses is a Array while actualCourses is ArrayList
		//Need to Array to ArrayList  Arrays.asList(pass array)
		
		List<String> expectedCourseslist=Arrays.asList(expectedCourses);
		
		//we can directly do asseration on collection like list it will do one to one comparision without loop.
		
		Assert.assertEquals(expectedCourseslist, actualCourses);
		
	}

}
