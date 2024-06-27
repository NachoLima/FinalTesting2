package ParaBank;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import reports.ReportFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.ByteArrayInputStream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Back {

    static ExtentSparkReporter info = new ExtentSparkReporter("reports/BackTests.html");
    static ExtentReports extent;

    private static String username = "DannyPhantom";
    private static String password = "icghosts123";
    private static int customerId = 12434;
    private static int fromAccountNumber = 14898;
    private static int toAccountnumber = 15009;

    @BeforeAll
    public static void setup() {
        System.out.println("<<< BACKEND TESTING BEGINS >>>");
        extent = ReportFactory.getInstance();
        extent.attachReporter(info);
    }

    @Test
    @Order(1)
    @Tag("GET")
    public void SignUp() {
        ExtentTest test = extent.createTest("GET - SignUp");
        test.log(Status.INFO, "SignUp URL test");

        try {
            Response responseGet = RestAssured.get("https://parabank.parasoft.com/parabank/register.htm");

            System.out.println(responseGet.getBody().asString());
            System.out.println(responseGet.statusCode());
            System.out.println(responseGet.getHeader("Date"));
            System.out.println(responseGet.getTime());

            assertNotNull(responseGet.getBody().asString(), "Response body should not be null");
            assertEquals(200, responseGet.statusCode(), "The status code should be 200");

            test.log(Status.PASS, "GET - SignUp test completed");
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @Tag("POST")
    public void OpenAccount() {
        System.out.println("Starting Open Account POST test");
        ExtentTest test = extent.createTest("POST - Open Account");
        test.log(Status.INFO, "Starting test to open a new account");

        int newAccountType = 1;

        JsonObject request = new JsonObject();
        request.addProperty("customerId", customerId);
        request.addProperty("type", "SAVINGS");

        try {
            Response response = given()
                    .auth().basic(username, password)
                    .contentType("application/json")
                    .body(request.toString())
                    .post("https://parabank.parasoft.com/parabank/services_proxy/bank/createAccount?customerId=" + customerId + "&newAccountType=" + newAccountType + "&fromAccountId=" + fromAccountNumber);

            test.log(Status.INFO, "POST request to create account");

            String responseBody = response.getBody().asString();
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + responseBody);

            // Parse XML response
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(responseBody.getBytes()));

            // Extract values from XML
            NodeList idNode = doc.getElementsByTagName("id");
            NodeList customerIdNode = doc.getElementsByTagName("customerId");

            assertNotNull(idNode.item(0).getTextContent(), "Response should contain account ID");
            assertEquals(200, response.getStatusCode(), "The status code should be 200");
            assertEquals(String.valueOf(customerId), customerIdNode.item(0).getTextContent(), "The customerId should match");
            assertTrue(response.getTime() < 5000, "The response time should be less than 5000ms");

            test.log(Status.PASS, "POST - Open Account test completed");
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @Tag("POST")
    public void TransferFunds() {
        System.out.println("Starting Transfer Funds POST test");
        ExtentTest test = extent.createTest("POST - Transfer Funds");
        test.log(Status.INFO, "Starting test to transfer funds");

        int amount = 10;

        try {
            Response response = given()
                    .auth().basic(username, password)
                    .post("https://parabank.parasoft.com/parabank/services_proxy/bank/transfer?fromAccountId=" + fromAccountNumber + "&toAccountId=" + toAccountnumber + "&amount=" + amount);

            test.log(Status.INFO, "POST request to transfer funds");

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());

            assertEquals(200, response.getStatusCode(), "The status code should be 200");
            assertNotNull(response.getBody().asString(), "Response body should not be null");
            assertEquals(amount, response.getBody().jsonPath().getInt("amount"), "The transferred amount should match");
            assertTrue(response.getTime() < 5000, "The response time should be less than 5000ms");

            test.log(Status.PASS, "POST - Transfer Funds test completed");
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    @Tag("GET")
    public void AccountActivity() {
        System.out.println("Starting Account Activity GET test");
        ExtentTest test = extent.createTest("GET - Account Activity");
        test.log(Status.INFO, "Starting test to get account activity");

        String url = "https://parabank.parasoft.com/parabank/services_proxy/bank/accounts/" + fromAccountNumber + "/transactions/month/All/type/All";
        test.log(Status.INFO, "GET request to " + url);

        try {
            Response response = given()
                    .auth().basic(username, password)
                    .get(url);

            System.out.println("Request URL: " + url);
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());

            assertNotNull(response.getBody().asString(), "Response body should not be null");
            assertEquals(200, response.getStatusCode(), "The status code should be 200");
            assertEquals("application/json", response.getHeader("Content-Type"), "The Content-Type should be application/json");
            assertTrue(response.getTime() < 5000, "The response time should be less than 5000ms");

            test.log(Status.PASS, "GET - Account Activity test completed");
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @AfterAll
    public static void saveReport() {
        extent.flush();
        System.out.println("<<< BACKEND TESTS END >>>");
    }
}
