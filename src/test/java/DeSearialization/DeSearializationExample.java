package DeSearialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import DeSearializationPoJo.GetCourses;
import DeSearializationPoJo.webAutomation;
import io.restassured.path.json.JsonPath;


public class DeSearializationExample {
	// We Will convert response payload into java object
	@Test
	public void DeSerialization()
	{
		// Get the token from Authorization Server
		String response=given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope", "trust")
		.when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().extract().response().asString();
		
		JsonPath js=new JsonPath(response);
		String Token=js.getString("access_token");
		
		
		// Get Course details from OAuth API
		
		GetCourses courseDetails=given().queryParam("access_token", Token).when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourses.class);
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
