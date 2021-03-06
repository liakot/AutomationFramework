package com.ui.pageobjects.web.autodesk.accounts;

import com.framework.base.BasePage;
import com.framework.core.Reporting;
import com.framework.core.Wrappers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Created by gadrea on 4/2/2015.
 */
public class LoginPage extends BasePage{

    private Reporting Reporter;
    private WebDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private Wrappers objWrapper;

    public static final String pageTitle = "Autodesk - Sign In";

    //Page UI Objects
    public final String edtUserName = "id:=userName";
    public final String edtPassword = "id:=password";
    public final String btnSubmit = "id:=btnSubmit";
    public final String txtErrors = "xpath:=//span[contains(@class,'field-validation-error')]/span";

    //Define the constructor
    public LoginPage(WebDriver GDriver, HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment, Reporting GReporter)
    {
        Reporter = GReporter;
        driver = GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        objWrapper = new Wrappers(driver, Reporter);
    }

    public LoginPage enterLoginCredentials(String username, String password)
    {
        objWrapper.enterText(edtUserName, username);
        objWrapper.enterText(edtPassword, password);
        Reporter.writeToTestLevelReport("Enter Login Credentials", "Login details should be Entered", "Login Details entered Successfully", "Pass");
        return this;
    }

    public <T extends BasePage> T clickSignIn(Class<T> clazz) {
        objWrapper.click(btnSubmit);
        Reporter.writeToTestLevelReport("Enter Login Credentials", "Login details should be Entered", "Login Details entered Successfully", "Pass");
        return getNewInstanceOfClass(clazz, driver, Dictionary, Environment, Reporter);
    }

    private List<String> getErrorMessages()
    {
        List<String> lstErrors = new ArrayList<String>();
        List<WebElement> elementErrors = objWrapper.getElements(txtErrors);
        for(WebElement elemError : elementErrors){
            lstErrors.add(elemError.getText());
        }

        return lstErrors;
    }

    public boolean shouldDisplayError(String errorMessage)
    {
        List<String> lstErrorMessages = getErrorMessages();
        for(String strErrorMessage : lstErrorMessages){
            if(strErrorMessage.equals(errorMessage)){
                Reporter.writeToTestLevelReport("Check Error message","Should display error " + errorMessage,"Error displayed","Pass");
                return true;
            }
        }
        Reporter.writeToTestLevelReport("Check Error message","Should display error " + errorMessage,"Error not displayed","Fail");
        return false;
    }
}
