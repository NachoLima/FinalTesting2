package reports;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReportFactory {
    public static ExtentReports getInstance() {
        ExtentReports extent = new ExtentReports();
        extent.setSystemInfo("OS", "Windows");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Environment", "Final Testing Para Bank");
        extent.setSystemInfo("Selenium Version", "4.22.0");
        return extent;
    }

    public static void captureScreenshot(ExtentTest test, String testName, WebDriver driver) {
        try {
            String screenshotPath = "reports/screenshots/" + testName + ".png";
            File existingScreenshot = new File(screenshotPath);
            if (existingScreenshot.exists()) {
                existingScreenshot.delete();
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotDir = "reports/screenshots/";
            Files.createDirectories(Paths.get(screenshotDir));
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
            test.addScreenCaptureFromPath("screenshots/" + testName + ".png", testName);
        } catch (IOException e) {
            test.log(Status.WARNING, "No se pudo hacer la captura de pantalla: " + e.getMessage());
        }
    }
}