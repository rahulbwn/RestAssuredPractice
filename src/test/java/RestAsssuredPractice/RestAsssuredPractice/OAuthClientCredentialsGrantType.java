package RestAsssuredPractice.RestAsssuredPractice;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class OAuthClientCredentialsGrantType {
	
	@Test
	public void ClientCredentialsOAuth()
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
		
		String courseDetails=given().queryParam("access_token", Token).when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		System.out.println(courseDetails);
	}

}
