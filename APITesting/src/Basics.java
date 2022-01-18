import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;
public class Basics {

	public static void main(String[] args) {

		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String Response = given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body(payload.AddPlace()).
		when().post("maps/api/place/add/json").
		
		then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)")
		.extract().response().asString();
		
		
		//JSONPath is a class which takes string as input and convert that into json format as output.
		JsonPath js = new JsonPath(Response); //parse JSON
		String placeID = js.getString("place_id");
		
		System.out.println("placeId is :" + placeID);
		
		
		//Update Place using the place ID extracted above
		
		String newAddress = "70 winter walk, USA";
		
		given().log().all()
		.queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//GET Place API 
		
	String getPlaceresponse = given().log().all()
		.queryParam("key", "qaclick123")
		.queryParam("place_id",placeID)
		
		.when().get("maps/api/place/get/json")
		
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
	JsonPath js1 = new JsonPath(getPlaceresponse); //parse JSON
	String getplaceID = js.getString("address");
		
		
		
		
		
		
		
		
		
	}

}
