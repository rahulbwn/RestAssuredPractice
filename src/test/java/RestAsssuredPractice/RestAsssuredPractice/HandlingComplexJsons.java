/*
 * 
 * {

"dashboard": {   //parent child nodes

"purchaseAmount": 910,

"website": "rahulshettyacademy.com"

},

"courses": [   // [ represents array

{

"title": "Selenium Python",

"price": 50,

"copies": 6

},

{

"title": "Cypress",

"price": 40,

"copies": 4

},

{

"title": "RPA",

"price": 45,

"copies": 10

}

]

}



1. Print No of courses returned by API

2.Print Purchase Amount

3. Print Title of the first course

4. Print All course titles and their respective Prices

5. Print no of copies sold by RPA Course

6. Verify if Sum of all Course prices matches with Purchase Amount
 */


package RestAsssuredPractice.RestAsssuredPractice;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class HandlingComplexJsons {
	 //Parsing Complex Json in String, integers
	
	public static void main(String[] args) {
		JsonPath js =new JsonPath(Payloads.getCoursesJson());
		//1. Print No of courses returned by API
		int coursesCount=js.getInt("courses.size()");
		System.out.println(coursesCount);
		
		//2.Print Purchase Amount
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//3. Print Title of the first course
		System.out.println(js.getString("courses[0].title"));
		
		//4. Print All course titles and their respective Prices
		for(int i=0;i<coursesCount;i++)
		{
			String courseTitles=js.getString("courses["+i+"].title");
			System.out.println(courseTitles);
		}
		
		//5. Print no of copies sold by RPA Course
		for(int i=0;i<coursesCount;i++)
		{
			String actiualTitle=js.getString("courses["+i+"].title");
			if(actiualTitle.equalsIgnoreCase("rpa"))
			{
				System.out.println(js.getInt("courses["+i+"].copies"));
				break;
			}
		}
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int SumofAllCoursesPrice=0;
		int totalPricePerCourse=0;
		for(int i=0;i<coursesCount;i++)
		{
			totalPricePerCourse=(js.getInt("courses["+i+"].price")) * (js.getInt("courses["+i+"].copies"));
			SumofAllCoursesPrice+=totalPricePerCourse;
		}
		
		Assert.assertEquals(SumofAllCoursesPrice, purchaseAmount);
	}
	
	
	
	
	

}
