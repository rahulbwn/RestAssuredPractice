package RestAsssuredPractice.RestAsssuredPractice;

import static io.restassured.RestAssured.given;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class OAuthAuthorizationCodeGrantType {
	
	@Test
	public void AuthorizationCode()
	{
		// Get the Code
		//String url="https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
		
		
		/*ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=C:/Users/rahul/AppData/Local/Google/Chrome/User Data");
		//options.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.get(url);
		*/
		
		String code="4%2F0AfJohXlBITMoIGj1z0XznhKIwPT0s01f-xe0zsZlLU-CSMoGg3OjcmI0-28xL1uL_91JVA";
		
		
		//Get the Access Token
		String responseString=given().urlEncodingEnabled(false).queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("grant_type", "authorization_code")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js=new JsonPath(responseString);
		String access_token=js.getString("access_token");
		
		//Get the list of courses
		String listOfCourses=given().queryParam("access_token", access_token).when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(listOfCourses);
	}

}
