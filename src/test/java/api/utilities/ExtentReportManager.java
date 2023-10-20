package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.compress.harmony.pack200.NewAttribute;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test Report" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\ExtentReport\\" + repName);// specify location
		sparkReporter.config().setDocumentTitle("RestAssured Automation Project");
		sparkReporter.config().setReportName("Pet Store User API");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", " Pet Store User API");
		extent.setSystemInfo("Operation System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA ");
		extent.setSystemInfo("User", "Van anh ");

	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS, "Test Passed");
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.FAIL, "Test Failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.SKIP, "Test Skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
	}
}
