package RestAsssuredPractice.RestAsssuredPractice;

import io.restassured.path.json.JsonPath;

public class ReusablesMethods {
	
	public static JsonPath rawToJson(String rawJsonString)
	{
		JsonPath js =new JsonPath(rawJsonString);
		return js;
	}

}
