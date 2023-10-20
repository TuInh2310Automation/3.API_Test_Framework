package api.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTest {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String UserID, String UserName, String	FirstName, String	LastName, String Email, String Password, String Phone) throws JsonProcessingException {
		User userpayload = new User();
		userpayload.setId(Integer.parseInt(UserID));
		userpayload.setUsername(UserName);
		userpayload.setFirstName(FirstName);
		userpayload.setLastName(LastName);
		userpayload.setEmail(Email);
		userpayload.setPassword(Password);
		userpayload.setPhone(Phone);
		Response response= UserEndPoints.createUser(userpayload);
		assertEquals(response.getStatusCode(), 200);
		System.out.println("user name " + UserID +UserName + FirstName + LastName+ Email+Password+Phone);
	}
	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeletUserByName(String username){
		System.out.println("user name " + username);
		Response response= UserEndPoints.deleteUser(username);
		assertEquals(response.getStatusCode(), 200);
	}
}
