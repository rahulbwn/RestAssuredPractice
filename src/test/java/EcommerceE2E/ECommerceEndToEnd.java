package EcommerceE2E;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import ECommercePojo.LoginRequest;
import ECommercePojo.LoginResponse;
import ECommercePojo.OrderDetails;
import ECommercePojo.PlaceOrder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class ECommerceEndToEnd {
	
	@Test
	public void EcommerceExample()
	{
		//login to Application and generate the token
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest login=new LoginRequest();
		login.setUserEmail("testraman@gmail.com");
		login.setUserPassword("Insan@777");
		
		RequestSpecification reqLogin=given().log().all().spec(req).body(login);
		
		
		LoginResponse loginResponse=reqLogin.when().log().all().post("/api/ecom/auth/login").as(LoginResponse.class);
		String token=loginResponse.getToken();
		String userID=loginResponse.getUserId();
		System.out.println(token);
		
		
		//Add Product
		RequestSpecification req2=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.MULTIPART).addHeader("Authorization", token).build();
		
		RequestSpecification reqCreateProduct=given().spec(req2).formParam("productName", "qwerty")
		.formParam("productAddedBy", userID)
		.formParam("productCategory", "fashion")
		.formParam("productSubCategory", "shirts")
		.formParam("productPrice", "1500")
		.param("productDescription", "Addias Originals")
		.param("productFor", "men")
		.multiPart("productImage",new File("D:\\Workspace\\Eclipse_WorkSpace\\RestAsssuredPractice2023\\target\\test_pic.jpg"));
		
		String createProdRes=reqCreateProduct.when().log().all().post("/api/ecom/product/add-product").then().log().all().assertThat().statusCode(201).extract().response().asString();
		System.out.println(createProdRes);
		
		JsonPath js=new JsonPath(createProdRes);
		String productID=js.getString("productId");
		System.out.println(productID);
		
		//PlaceOrder
		RequestSpecification req3=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).addHeader("Authorization", token).build();
		
		OrderDetails orderDetail=new OrderDetails();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productID);
		
		List<OrderDetails> orderDetailsList=new ArrayList<OrderDetails>();
		orderDetailsList.add(orderDetail);
		
		PlaceOrder order=new PlaceOrder();
		order.setOrders(orderDetailsList);
		RequestSpecification reqPlaceOrder=given().spec(req3).body(order);
		
		String responsePlacedOrder=reqPlaceOrder.when().post("/api/ecom/order/create-order").asString();
		System.out.println(responsePlacedOrder);
		
		JsonPath js2=new JsonPath(responsePlacedOrder);
		String orderID=js2.getString("orders[0]");
		
		System.out.println(orderID);
		
		//Delete Order
		RequestSpecification req4=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
		
		String deleteOrderResponse=given().spec(req4).pathParam("orderNo", orderID).delete("/api/ecom/order/get-orders-for-customer/{orderNo}").asString();
		
		System.out.println(deleteOrderResponse);
		
		
		
		
	}


}