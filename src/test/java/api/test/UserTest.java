package api.test;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;
import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	Faker faker;
	User userPlayload;
	public Logger logger;
	@BeforeClass
	public void setupData()
	{
		faker= new Faker();
		userPlayload= new User();
		String userNameString= faker.name().username();
		System.out.println("userNameString " + userNameString);
		userPlayload.setId(faker.idNumber().hashCode());
		userPlayload.setUsername(userNameString);
		userPlayload.setFirstName(faker.name().firstName());
		userPlayload.setLastName(faker.name().lastName());
		userPlayload.setEmail(faker.internet().safeEmailAddress());
		userPlayload.setPassword(faker.internet().password(5,10));
		userPlayload.setPhone(faker.phoneNumber().cellPhone());
		userPlayload.setUserStatus(0);
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testPostUser() throws JsonProcessingException
	{
		logger.info("--------------Create User info---------------------");
		Response response=UserEndPoints.createUser(userPlayload);
		response.then().log().all();
		assertEquals(response.getStatusCode(), 200);
		logger.info("--------------User is created---------------------");
	}
	
	@Test(priority = 2)
	public void testGetUserByName()
	{
		logger.info("--------------Get User info---------------------");
		Response response=UserEndPoints.readUser(userPlayload.getUsername());
		response.then().log().all();
		// parsing json response to POJO class
		User a= response.getBody().as(User.class);
		System.out.print("user name............"+ a.getUsername());
		assertEquals(response.getStatusCode(), 200);
		logger.info("--------------Get User info finished---------------------");
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName()
	{
		logger.info("--------------Update User info---------------------");
		userPlayload.setFirstName(faker.name().firstName());
		userPlayload.setLastName(faker.name().lastName());
		userPlayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response=UserEndPoints.updateUser(userPlayload.getUsername(), userPlayload);
		response.then().log().all();
		assertEquals(response.getStatusCode(), 200);
		logger.info("--------------update User info finish---------------------");
		
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName()
	{
		logger.info("--------------delte User ---------------------");
		Response response=UserEndPoints.deleteUser(userPlayload.getUsername());
		response.then().log().all();
		assertEquals(response.getStatusCode(), 200);
		logger.info("--------------delte User info finished ---------------------");
	}
}
