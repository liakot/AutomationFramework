package com.tests.ui.web.autodesk.accounts;

import com.ui.pageobjects.web.autodesk.accounts.LaunchApplication;
import com.ui.pageobjects.web.autodesk.accounts.LoginPage;
import com.ui.pageobjects.web.autodesk.accounts.ProfilePage;
import com.framework.Global;
import com.framework.base.BaseSeleniumWebTest;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

/**
 * Created by gadrea on 7/6/2015.
 */
public class TestsForGridAccountsLogin extends BaseSeleniumWebTest{

    @DataProvider(name = "browsers", parallel = true)
    public static Object[][] getData() {
        return Global.getBrowsersForGrid();
    }

    @Factory(dataProvider = "browsers")
    //Created with values from @DataProvider in @Factory
    public TestsForGridAccountsLogin(String browser, String browserVersion, String OS) {
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.OS = OS;
    }

    @BeforeClass
    public void beforeClass() throws IOException {
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        super.beforeClass();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setBrowserName(browser);
        dc.setVersion(browserVersion);
        dc.setPlatform(asapDriver.getPlatform(OS));

        super.beforeMethod(method);

        try{
            setRemoteWebDriver("http://localhost:4444/wd/hub",dc);
        }
        catch(MalformedURLException e){
            System.out.println("Exception " + e);
        }
    }

    @Test
    public void testValidLogin(){
        ProfilePage profilePage = new LaunchApplication(driver, dictionary, environment,Reporter)
                .launchIdentityApplication()
                .enterLoginCredentials("aniket@autodesk","Jaguar21")
                .clickSignIn(ProfilePage.class);

        Assert.assertTrue(profilePage.shouldHaveMyProfileTab(),"Assert Profile page has Profile Tab");
    }

    @Test
    public void testInvalidLoginError(){
        LoginPage loginPage = new LaunchApplication(driver, dictionary, environment,Reporter)
                .launchIdentityApplication()
                .enterLoginCredentials("aniket@autodesk","Jaguar22")
                .clickSignIn(LoginPage.class);

        Assert.assertTrue(loginPage.shouldDisplayError("Email address / username and password do not match."));
    }

    @Test
    public void testBlankCredentialsError(){
        LoginPage loginPage =  new LaunchApplication(driver, dictionary, environment,Reporter)
                .launchIdentityApplication()
                .enterLoginCredentials("","")
                .clickSignIn(LoginPage.class);

        Assert.assertTrue(loginPage.shouldDisplayError("Please enter an email address or username."));
        Assert.assertTrue(loginPage.shouldDisplayError("Please enter your password."));
    }

    @AfterMethod
    public void afterMethod(Method method){
        super.afterMethod(method);
    }

    @AfterClass
    public void afterClass(){
        super.afterClass();
    }
}
