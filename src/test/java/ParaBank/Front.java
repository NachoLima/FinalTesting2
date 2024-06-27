package ParaBank;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reports.ReportFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Front {

    static ExtentSparkReporter info = new ExtentSparkReporter("reports/FrontTests.html");
    static ExtentReports extent;

    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "DannyP";
    private String password = "icghosts123";


    @BeforeAll
    public static void createReport() {
        extent = ReportFactory.getInstance();
        extent.attachReporter(info);
        System.out.println("<<< FRONTEND TESTING BEGINS >>>");
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        driver = new ChromeDriver(options);        wait = new WebDriverWait(driver, Duration.ofMillis(5000));
        MainPage mainPage = new MainPage(driver, wait);

        mainPage.setup();
        mainPage.getUrl("https://parabank.parasoft.com/parabank/index.htm");

    }

    public void Login() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);

        mainPage.LoginUsername(username);
        mainPage.LoginPass(password);
        mainPage.clickLoginBtn();
    }
    @Test
    @Order(1)
    @Tag("SMOKE")
    @Tag("SIGNUP")

    public void signUp() throws InterruptedException {
        ExtentTest test = extent.createTest("SignUp");

        test.log(Status.INFO, "Test begins");
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.clickRegister();
        test.log(Status.INFO, "Access SignUp page");


        mainPage.typeFirstName("Danny");
        mainPage.typeLastName("Phantom");
        mainPage.typeAdress("183 1st Street");
        mainPage.typeCity("New York");
        mainPage.typeState("New York");
        mainPage.typeZipCode("10008");
        mainPage.typePhoneNumber("5342826451");
        mainPage.typeSsn("123456789");

        mainPage.typeUsername(username);
        mainPage.typePassword(password);
        mainPage.typeRePassword(password);
        test.log(Status.INFO, "Form information completed");

        mainPage.clickConfirmRegister();
        test.log(Status.INFO, "SignUp process finished");

        String result = mainPage.userAccountCreatedConfirmation();

        assertEquals("Your account was created successfully. You are now logged in.", result);
        test.log(Status.PASS, "SignUp successful");

    }

    @Test
    @Order(2)
    @Tag("SMOKE")
    @Tag("ACCOUNT")

    public void openAccount() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        ExtentTest test = extent.createTest("Open Account");

        Login();
        test.log(Status.INFO, "Login Successful");

        mainPage.clickNewAccount();
        mainPage.accountType();
        test.log(Status.INFO, "New account");

        mainPage.setAccountConfirmed();

        mainPage.setAccountConfirmed();
        test.log(Status.INFO, "Confirmation");

        String result = mainPage.bankAccountConfirmation();

        assertEquals("Congratulations, your account is now open.", result);
        test.log(Status.PASS, "Account successfully created");

    }@Test
    @Order(3)
    @Tag("SMOKE")
    @Tag("ACCOUNT")
    @Tag("BALANCE")
    public void resumeAccount() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        ExtentTest test = extent.createTest("Account Balance");

        Login();
        test.log(Status.INFO, "Login Successful");

        mainPage.clickBalanc();
        test.log(Status.INFO, "Check Balance");


        String result = mainPage.accountBalanceConfirmation();

        assertEquals("*Balance includes deposits that may be subject to holds", result);
        test.log(Status.PASS, "Success");

    }

    @Test
    @Order(4)
    @Tag("SMOKE")
    @Tag("TRANSFER")
    public void transferFunds() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        ExtentTest test = extent.createTest("Transfer");

        Login();
        test.log(Status.INFO, "Login Successful");

        mainPage.clickTransferBtn();

        test.log(Status.INFO, "Begin transfer");


        mainPage.transferAmmount("500");
        test.log(Status.INFO, "Select amount");


        mainPage.clickTransferConfirmation();
        test.log(Status.INFO, "Confirm transfer");


        String result = mainPage.transferComplete();

        assertEquals("Transfer Complete!", result);

        test.log(Status.PASS, "Transfer Successful");

    }

    @Test
    @Order(5)
    @Tag("SMOKE")
    @Tag("ACCOUNT")
    @Tag("ACTIVITY")
    public void accountActivityPerMonth() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        ExtentTest test = extent.createTest("Monthly activity");

        Login();
        test.log(Status.INFO, "Login Successful");

        mainPage.clickBalanc();
        test.log(Status.INFO, "Access balance");

        mainPage.monthActivity();
        test.log(Status.INFO, "Access monthly activity");

        String result = mainPage.accountDetails();

        assertEquals("Account Details", result);

        mainPage.activityPeriod();

        test.log(Status.INFO, "Access activity period");

        mainPage.activityType();
        test.log(Status.INFO, "Check activity type");


        mainPage.accountGo();
        test.log(Status.PASS, "Monthly activity successful");

    }

    @AfterEach
    public void closeBrowser() {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.close();
    }

    @AfterAll
    public static void saveReport() {
        extent.flush();
        System.out.println("<<< FRONTEND TESTS END >>>");
    }
}