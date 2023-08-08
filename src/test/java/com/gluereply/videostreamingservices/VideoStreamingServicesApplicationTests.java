package com.gluereply.videostreamingservices;

import com.gluereply.videostreamingservices.data.PaymentDao;
import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.data.UserDao;
import com.gluereply.videostreamingservices.services.PaymentService;
import com.gluereply.videostreamingservices.services.RegestrationService;
import com.gluereply.videostreamingservices.services.validation.RejectionReason;
import com.gluereply.videostreamingservices.services.validation.ValidationResult;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.imageio.spi.RegisterableService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VideoStreamingServicesApplicationTests {

	@Autowired
	RegestrationService regestrationService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	PaymentService paymentService;

	@LocalServerPort
	private int serverPort;

	@BeforeEach
	void setup(){
		RestAssured.port = serverPort;
		RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig());
	}

	@AfterEach
	void tearDown(){
		paymentDao.clear();
	}

	@Nested
	@DisplayName("Registration Api")
	class RegistrationApi {

		@Test
		@DisplayName("should return http status of 201(CREATED) for successful registration")
		public void registration_success() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("username", "james1");
			jsonAsMap.put("password", "Validpass1");
			jsonAsMap.put("email", "james@yahoo.com");
			jsonAsMap.put("dob", "2000-12-31");
			jsonAsMap.put("creditCard", "1234567898765432");

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/users/register")
					.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(0);
			User user = userDao.getUser("james1");
			userDao.deleteUser(user);
		}

		@Test
		@DisplayName("should return http status of 403(FORBIDDEN) if the date of birth computes to age under 18")
		public void registration_fails_on_age_less_than_18() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("username", "james2");
			jsonAsMap.put("password", "Validpass1");
			jsonAsMap.put("email", "james@yahoo.com");
			jsonAsMap.put("dob", "2005-12-31");
			jsonAsMap.put("creditCard", "1234567898765432");

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/users/register")
					.then()
					.statusCode(HttpStatus.SC_FORBIDDEN)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("UNDER_AGE");
		}

		@Test
		@DisplayName("should return http status of 409(CONFLICT) if the username to be registered already exists")
		public void registration_fails_on_existing_username() throws JSONException {
			User user = new User("james3", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"));
			ValidationResult result = regestrationService.register(user);

			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("username", "james3");
			jsonAsMap.put("password", "Validpass1");
			jsonAsMap.put("email", "james@yahoo.com");
			jsonAsMap.put("dob", "2000-12-31");
			jsonAsMap.put("creditCard", "1234567898765432");

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/users/register")
					.then()
					.statusCode(HttpStatus.SC_CONFLICT)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("USERNAME_EXISTS");
		}

		@Test
		@DisplayName("should return http status of 400(BAD_REQUEST) if no password is included in the request")
		public void registration_fails_if_no_password_included() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("username", "james4");
			jsonAsMap.put("email", "james@yahoo.com");
			jsonAsMap.put("dob", "2000-12-31");
			jsonAsMap.put("creditCard", "1234567898765432");

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/users/register")
					.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("EMPTY_FIELDS");
		}

		@Test
		@DisplayName("should return http status of 400(BAD_REQUEST) if no username is included in the request")
		public void registration_fails_if_no_username_included() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("password", "Validpass1");
			jsonAsMap.put("email", "james@yahoo.com");
			jsonAsMap.put("dob", "2000-12-31");
			jsonAsMap.put("creditCard", "1234567898765432");

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/users/register")
					.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("EMPTY_FIELDS");
		}

		@Test
		@DisplayName("should return http status of 400(BAD_REQUEST) if no email is included in the request")
		public void registration_fails_if_no_email_included() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("username", "james5");
			jsonAsMap.put("password", "Validpass1");
			jsonAsMap.put("dob", "2000-12-31");
			jsonAsMap.put("creditCard", "1234567898765432");

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/users/register")
					.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("EMPTY_FIELDS");
		}

		@Test
		@DisplayName("should return http status of 400(BAD_REQUEST) if no date of birth is included in the request")
		public void registration_fails_if_no_dob_included() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("username", "james6");
			jsonAsMap.put("password", "Validpass1");
			jsonAsMap.put("email", "james@yahoo.com");
			jsonAsMap.put("creditCard", "1234567898765432");

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/users/register")
					.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("EMPTY_FIELDS");
		}

		@Test
		@DisplayName("should return all register customers with credit card")
		public void getUsers_with_credit_card(){
			String filter = "yes";
			JsonPath jsonPath = given()
					.queryParam("filter", filter)
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.get("/users/get")
					.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("users")).hasSize(3);
			assertThat(jsonPath.getString("username[0]")).isEqualTo("james");
		}


		@Test
		@DisplayName("should return all register customers without  credit card")
		public void getUsers_without_credit_card(){
			String filter = "no";
			JsonPath jsonPath = given()
					.queryParam("filter", filter)
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.get("/users/get")
					.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("users")).hasSize(2);
			assertThat(jsonPath.getString("username[0]")).isEqualTo("david");
		}

		@Test
		@DisplayName("should return all register customers with and without credit card")
		public void getUsers_with_and_without_credit_card(){
			String filter = null;
			JsonPath jsonPath = given()
					.queryParam("filter", filter)
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.get("/users/get")
					.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("users")).hasSize(5);
			assertThat(jsonPath.getString("username[4]")).isEqualTo("jane");
		}
	}

	@Nested
	@DisplayName("Payment Api")
	class PaymentApi {
		@Test
		@DisplayName("should return http status of 201(CREATED) for successful payment")
		public void payment_success() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("id", 1l);
			jsonAsMap.put("username", "james");
			jsonAsMap.put("creditCard", "1236458712364568");
			jsonAsMap.put("amount", 123);

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/payments/pay")
					.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(0);
		}

		@Test
		@DisplayName("should return http status of 404(NOT_FOUND) if the credit card is not registered against a registered user")
		public void payment_fails_for_unregistered_credit_card() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("id", 1l);
			jsonAsMap.put("username", "james");
			jsonAsMap.put("creditCard", "1567858712364568");
			jsonAsMap.put("amount", 123);

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/payments/pay")
					.then()
					.statusCode(HttpStatus.SC_NOT_FOUND)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("UNREGISTERED_CREDIT_CARD");
		}


		@Test
		@DisplayName("should return http status of 400(BAD_REQUEST) if the credit card is invalid(15 digit)")
		public void payment_fails_for_invalid_credit_card() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("id", 1l);
			jsonAsMap.put("username", "james");
			jsonAsMap.put("creditCard", "123645871236456");
			jsonAsMap.put("amount", 123);

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/payments/pay")
					.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("INVALID_CREDIT_CARD");
		}

		@Test
		@DisplayName("should return http status of 400(BAD_REQUEST) if the payment contains invalid amount")
		public void payment_fails_for_invalid_amount() throws JSONException {
			Map<String, Object> jsonAsMap = new HashMap<>();
			jsonAsMap.put("id", 1l);
			jsonAsMap.put("username", "james");
			jsonAsMap.put("creditCard", "1236458712364568");
			jsonAsMap.put("amount", 12);

			JsonPath jsonPath = given()
					.when()
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(jsonAsMap)
					.post("/payments/pay")
					.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.jsonPath();

			assertThat(jsonPath.getList("rejectionReasons")).hasSize(1);
			assertThat(jsonPath.getString("rejectionReasons[0]")).isEqualTo("INVALID_AMOUNT");
		}

	}
	@Test
	void contextLoads() {
	}



}
