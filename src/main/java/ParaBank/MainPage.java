package ParaBank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends BasePage{

    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, null);
    }


    /** Login */
    private By loginUsername = By.xpath("//input[@name='username']");
    private By loginPass = By.xpath("//input[@name='password']");
    private By loginBtn = By.xpath("//input[@value='Log In']");

    public void LoginUsername(String name) throws InterruptedException {
        this.sendText(name, loginUsername);
    }

    public void LoginPass(String pass) throws InterruptedException {
        this.sendText(pass, loginPass);
    }

    public void clickLoginBtn() throws InterruptedException {
        this.click(loginBtn);
    }

    /** Registro */
    private By btnRegister = By.xpath("//a[normalize-space()='Register']");


    private By firstName = By.id("customer.firstName");
    private By lastName = By.id("customer.lastName");

    private By address = By.id("customer.address.street");


    private By city = By.id("customer.address.city");
    private By state = By.id("customer.address.state");

    private By zipCode = By.id("customer.address.zipCode");

    private By phoneNumber = By.id("customer.phoneNumber");
    private By ssn = By.id("customer.ssn");
    private By username = By.id("customer.username");
    private By password = By.id("customer.password");
    private By repassword = By.id("repeatedPassword");

    private By btnContinue = By.xpath("//input[@value='Register']");

    private By successMessage = By.xpath("//p[contains(text(),'Your account was created successfully. You are now')]");

    public void clickRegister() throws InterruptedException {
        this.click(btnRegister);
    }

    public void typeFirstName(String name) throws InterruptedException {
        this.sendText(name, this.firstName);
    }


    public void typeLastName(String lastname) throws InterruptedException {
        this.sendText(lastname, lastName);
    }

    public void typeAdress(String address) throws InterruptedException {
        this.sendText(address, this.address);
    }


    public void typeCity(String City) throws InterruptedException {
        this.sendText(City, city);
    }

    public void typeState(String State) throws InterruptedException {
        this.sendText(State, state);
    }

    public void typeZipCode(String zip) throws InterruptedException {
        this.sendText(zip, zipCode);
    }

    public void typePhoneNumber(String phone) throws InterruptedException {
        this.sendText(phone, phoneNumber);
    }


    public void typeSsn(String Ssn) throws InterruptedException {
        this.sendText(Ssn, ssn);
    }
    public void typeUsername(String usernam) throws InterruptedException {
        this.sendText(usernam, username);
    }

    public void typePassword(String pass) throws InterruptedException {
        this.sendText(pass, password);
    }

    public void typeRePassword(String pass) throws InterruptedException {
        this.sendText(pass, repassword);
    }


    public void clickConfirmRegister() throws InterruptedException {
        this.click(btnContinue);
    }



    public String userAccountCreatedConfirmation() throws InterruptedException {
        System.out.println("User account creation confirmation message: " + this.getText(successMessage));
        return this.getText(successMessage);
    }



    /** Nueva Cuenta */
    private By newAccount = By.xpath("//a[normalize-space()='Open New Account']");


    private By openNewAccount = By.cssSelector("input[value='Open New Account']");
    private By accountConfirmed = By.xpath("//p[normalize-space()='Congratulations, your account is now open.']");

    public void clickNewAccount() throws InterruptedException {
        this.click(newAccount);
    }
    public void accountType() throws InterruptedException {
        By accountTypeLocator = By.xpath("//select[@id='type']");
        int savingsOptionValue = 1;

        // Llamar al método de BasePage para seleccionar la opción "SAVINGS"
        selectOptionFromDropdown(accountTypeLocator, savingsOptionValue);

    }
    public void setAccountConfirmed() throws InterruptedException {
        this.click(openNewAccount);
        Thread.sleep(1000);

    }
    public String bankAccountConfirmation() throws InterruptedException {
        System.out.println("Account creation confirmation message: " + this.getText(accountConfirmed));
        return this.getText(accountConfirmed);
    }
    /** Resumen de cuenta */
    private By clickBalance = By.xpath("//a[normalize-space()='Accounts Overview']");
    private By balanceConfirmation = By.xpath("//td[contains(text(),'*Balance includes deposits that may be subject to ')]");
    public void clickBalanc() throws InterruptedException {
        this.click(clickBalance);
    }

    public String accountBalanceConfirmation() throws InterruptedException {
        System.out.println("Account balance confirmation message: " + this.getText(balanceConfirmation));
        return this.getText(balanceConfirmation);
    }

    /** Transferir fondos */
    private By transferBtn = By.xpath("//a[normalize-space()='Transfer Funds']");
    private By transferText = By.xpath("//h1[normalize-space()='Transfer Funds']");
    private By ammount = By.xpath("//input[@id='amount']");
    private By transferConfirmation = By.xpath("//input[@value='Transfer']");
    private By transferComplete = By.xpath("//h1[normalize-space()='Transfer Complete!']");


    public void clickTransferBtn() throws InterruptedException {
        this.click(transferBtn);
    }

    public String transferText() throws InterruptedException {
        System.out.println("Transfer alert message: " + this.getText(transferText));
        return this.getText(transferText);
    }
    public void transferAmmount(String amount) throws InterruptedException {
        this.sendText(amount, ammount);
    }

    public void clickTransferConfirmation() throws InterruptedException {
        this.click(transferConfirmation);
    }

    public String transferComplete() throws InterruptedException {
        System.out.println("Completed transfer message: " + this.getText(transferComplete));
        return this.getText(transferComplete);
    }

    /** Actividad por mes */

    private By monthlyActivity = By.xpath("/html[1]/body[1]/div[1]/div[3]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/a[1]");
    private By accountDetails = By.xpath("//h1[normalize-space()='Account Details']");
    private By accountGo = By.xpath("//input[@value='Go']");

    public void monthActivity() throws InterruptedException {
        this.click(monthlyActivity);
    }

    public String accountDetails() throws InterruptedException {
        System.out.println("Account Details" + this.getText(accountDetails));
        return this.getText(accountDetails);
    }

    public void activityPeriod() throws InterruptedException {
        By accountTypeLocator = By.id("month");
        int savingsOptionValue = 0;

        selectOptionFromDropdown(accountTypeLocator, savingsOptionValue);
    }

    public void activityType() throws InterruptedException {
        By accountTypeLocator = By.id("transactionType");
        int savingsOptionValue = 0;

        selectOptionFromDropdown(accountTypeLocator, savingsOptionValue);
    }

    public void accountGo() throws InterruptedException {
        this.click(accountGo);
    }
}