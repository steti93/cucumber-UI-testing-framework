package com.steti.stepDef;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.steti.core.dataKeys.PageKeys;
import com.steti.core.utils.ItemList;
import com.steti.core.utils.ScenarioContext;
import com.steti.pages.abstractPage.AbstractPage;
import com.steti.pages.abstractPage.AbstractPageObject;
import com.steti.pages.abstractPage.PageNames;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.steti.core.assertions.STAssertions.assertFalse;
import static com.steti.core.assertions.STAssertions.assertTrue;
import static com.steti.hooks.GlobalStepHooks.SCREENSHOT_PATH;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class GlobalStepDef {

    private static Logger logger = LoggerFactory.getLogger(GlobalStepDef.class);

    @Autowired
    private ScenarioContext scenarioContext;

    @Value("${application.base.url}")
    private String baseUrl;

    @Value("${application.base.adminurl}")
    private String adminBaseUrl;

    @Value("${application.base.create}")
    private String userCreateUrl;

    @Value("${application.default.timeout}")
    private Long DEFAULT_TIMEOUT;

    @Value("${application.webelement.timeout}")
    private Long WEBELEMENT_TIMEOUT;

    @Value("${application.version}")
    private String applicationVersion;

    private static List<String> valuesToSave = new ArrayList<>();

    private static int screenNumber;

    public WebElement getWebElementByObjectAndName(Object object, String fieldName) {
        WebElement webElement = null;
        Class<?> validationClass = object.getClass();
        Field[] fields = validationClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == WebElement.class) {
                field.setAccessible(true);
                if (field.getName().equals(fieldName)) {
                    try {
                        webElement = (WebElement) field.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
        return webElement;
    }

    public AbstractPageObject getPageObjectByPageName(PageNames pageNames, WebDriver driver) throws Exception {
        Class<?> c = Class.forName("com.steti.pages.pageObjects.".concat(applicationVersion).concat(".") + pageNames.name());
        Constructor<?> cons = c.getConstructor(WebDriver.class);
        return (AbstractPageObject) cons.newInstance(driver);
    }

    private static String getTextOfInvisibleElement(WebDriver driver, WebElement element) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return jQuery(arguments[0]).text();", element);
    }

    private static boolean resultOfMakeScreenShot() {
        boolean makescreenshot = false;
        try {
            makescreenshot = Boolean.valueOf(System.getProperty("screenshot"));
        } catch (Exception e) {
            logger.warn("System variable screenshot is not defined");
        }
        return makescreenshot;
    }

    private static void makeScreenshot(String scenarioName, WebDriver driver) {
        if (resultOfMakeScreenShot()) {
            String imageName = scenarioName.concat(String.valueOf(screenNumber++));
            Shutterbug.shootPage(driver)
                    .withName(imageName)
                    .save(SCREENSHOT_PATH);
        }
    }

    public static void highLighterMethod(WebDriver driver, WebElement element) {
        if (resultOfMakeScreenShot()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        }
    }


    @When("^user clicks on '(.*)' from current page$")
    public void userClicksOnSimpleFileDownloadFromCurrentPage(String webElement) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement foundElement = getWebElementByObjectAndName(currentPage, webElement);

        await().atMost(DEFAULT_TIMEOUT, SECONDS).until(() -> foundElement.isEnabled());
        foundElement.click();
        makeScreenshot((String) scenarioContext.getData(PageKeys.SCENARIO_NAME), (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER));
        scenarioContext.save(PageKeys.LAST_CLICKED, foundElement);
    }

    @When("^element '(.*)' is not displayed on current page$")
    public void elementIsNotDisplayedOnCurrentPage(String webElement) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement foundElement = getWebElementByObjectAndName(currentPage, webElement);

        await().atMost(DEFAULT_TIMEOUT, SECONDS).until(() -> !foundElement.isDisplayed());
    }

    @Then("^page '(.*)' is displayed$")
    public void pageIsDisplayed(PageNames pageNames) throws Exception {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        AbstractPageObject object = getPageObjectByPageName(pageNames, driver);
        await().atMost(DEFAULT_TIMEOUT, SECONDS).until(() -> object.isInitialized());
        assertTrue("Page object " + pageNames.name() + " is displayed", object.isInitialized());
        scenarioContext.save(PageKeys.CURRENT_PAGE, object);
    }

    @When("^dialog '(.*)' with following message is displayed$")
    public void dialogWithFollowingMessageIsDisplayed(String elementName, DataTable dataTable) {
        List<String> dialogMessage = dataTable.raw().get(0);
        AbstractPage currentPage = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement addToCartModal = getWebElementByObjectAndName(currentPage, elementName);
        await().atMost(DEFAULT_TIMEOUT, SECONDS).until(() -> addToCartModal.isDisplayed());
        for (String item : dialogMessage) {
            assertTrue("Message is not displayed in dialog", addToCartModal.getText().contains(item));
        }

    }


    @When("^alert '(.*)' with following message is displayed$")
    public void alertWithFollowingMessageIsDisplayed(String expectedMessage) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        String actualMessage = driver.switchTo().alert().getText();
        assertTrue("Message is wrong", actualMessage.contains(expectedMessage));

    }

    @When("^user is waiting for '(\\d+)' seconds$")
    public void userIsWaitingForSeconds(int seconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(seconds);
    }

    @When("^user inserts following data in current page$")
    public void userInsertsFollowingDataInCurrentPage(List<ItemList> dataTableValues) {
        WebDriver webDriver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        AbstractPage currentPage = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        for (ItemList itemList : dataTableValues) {
            WebElement webElement = getWebElementByObjectAndName(currentPage, itemList.getFieldName());
            await().atMost(WEBELEMENT_TIMEOUT, SECONDS).until(() -> webElement.isEnabled());
            webElement.clear();
            if (itemList.getFieldValue().contains("%s")) {
                String userTestEmail = String.format(itemList.getFieldValue(), LocalDateTime.now().toLocalTime().toSecondOfDay());
                scenarioContext.save(PageKeys.TEST_EMAIL, userTestEmail);
                webElement.sendKeys(userTestEmail);
                highLighterMethod(webDriver, webElement);
                makeScreenshot((String) scenarioContext.getData(PageKeys.SCENARIO_NAME), webDriver);
            } else {
                webElement.sendKeys(itemList.getFieldValue());
                highLighterMethod(webDriver, webElement);
                makeScreenshot((String) scenarioContext.getData(PageKeys.SCENARIO_NAME), webDriver);
            }
        }
    }

    @When("^following element is displayed on current page$")
    public void followingElementIsDisplayed(DataTable dataTable) {
        List<String> elementsNames = dataTable.raw().get(0);
        AbstractPage currentPage = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        for (String elementName : elementsNames) {
            WebElement webElement = getWebElementByObjectAndName(currentPage, elementName);
            await().atMost(DEFAULT_TIMEOUT, SECONDS).until(() -> webElement.isDisplayed());
            assertTrue(String.format("Element with name: %s is not displayed", elementName), webElement.isDisplayed());
        }
    }

    @Then("^page contains following text and URL$")
    public void pageContainsTextAndUrl(DataTable dataTable) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        for (List<String> row : dataTable.asLists(String.class)) {
            await().atMost(DEFAULT_TIMEOUT, SECONDS).until(() -> driver.getPageSource().contains(row.get(0)));
            assertTrue("Following page contains text", driver.getPageSource().contains(row.get(0)));
            assertTrue("Url is same", driver.getCurrentUrl().contains(row.get(1)));
        }
    }

    @When("^user selects following data from select element$")
    public void userSelectsFollowingDataFromSelectElement(List<ItemList> itemLists) {
        AbstractPage currentPage = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        for (ItemList itemList : itemLists) {
            WebElement element = getWebElementByObjectAndName(currentPage, itemList.getFieldName());
            List<WebElement> options = element.findElements(By.tagName("option"));
            options.stream().filter(r -> r.getText().contains(itemList.getFieldValue())).findFirst().get().click();

        }
    }

    @When("^user inserts an existing email$")
    public void userInsertsAnExistingEmail() {
        String usedEmail = (String) scenarioContext.getData(PageKeys.TEST_EMAIL);
        AbstractPage currentPage = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement element = getWebElementByObjectAndName(currentPage, "emailAddress");
        element.sendKeys(usedEmail);
    }

    @Then("^following element '(.*)' contains text$")
    public void followingElementErrorMessageContainsText(String elementName, DataTable dataTable) {
        List<String> messages = dataTable.raw().get(0);
        AbstractPage currentPage = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement webElement = getWebElementByObjectAndName(currentPage, elementName);
        for (String item : messages)
            assertTrue("Following element don't contains text:".concat(item), webElement.getText().contains(item));
    }

    @Then("^following element '(.*)' is not empty$")
    public void followingElementIsNotEmpty(String elementName) {
        AbstractPage currentPage = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement webElement = getWebElementByObjectAndName(currentPage, elementName);
        assertTrue(webElement.toString().concat(" is empty"), !webElement.getText().isEmpty());
    }

    @When("^user focus on '(.*)'$")
    public void focusOnElement(String xpath) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        WebElement foundElement = driver.findElement(By.xpath(xpath));
        new Actions(driver).moveToElement(foundElement).perform();
    }

    @When("^user navigates to '(.*)' link$")
    public void userIsNavigatesTo(String linkName) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        driver.navigate().to(baseUrl.concat("/").concat(linkName));
        logger.info("User navigates to: " + baseUrl.concat("/").concat(linkName));
    }

    @When("^user clicks on '(.*)'$")
    public void userClicksOnElementPath(String elementPath) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        WebElement element = driver.findElement(By.xpath(elementPath));
        element.click();
        makeScreenshot((String) scenarioContext.getData(PageKeys.SCENARIO_NAME), driver);
    }

    @Then("^page (|do not )contains following text$")
    public void pageContainsFollowingText(String condition, DataTable dataTable) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        for (List<String> elements : dataTable.raw()) {
            if (condition.isEmpty())
                assertTrue("Following page contains text [".concat(elements.get(0)).concat("]"), driver.getPageSource().contains(elements.get(0)));
            else
                assertFalse("Following page contains text [".concat(elements.get(0)).concat("]"), driver.getPageSource().contains(elements.get(0)));
        }

    }

    @And("^user select '(.*)' from dropdown$")
    public void userSelectFromDropdown(String webElement) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement foundElement = getWebElementByObjectAndName(currentPage, webElement);
        foundElement.click();
        scenarioContext.save(PageKeys.LAST_CLICKED, foundElement);

    }


    @Then("^user opens page '(.*)'$")
    public void userOpensPage(PageNames pageName) throws Exception {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        AbstractPageObject abstractPage = getPageObjectByPageName(pageName, driver);
        String currentPageLink = abstractPage.getPageLink();
        driver.navigate().to(baseUrl.concat("/").concat(currentPageLink));
    }

    @When("^user see the '(.*)' web element$")
    public void userSeeTheModal(String modal) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        assertTrue("Following page contains text", driver.getPageSource().contains(modal));

    }

    @Then("^user clicks on '(.*)' close the modal$")
    public void userClicksOnCloseModalCloseTheModal(String webElement) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement foundElement = getWebElementByObjectAndName(currentPage, webElement);
        foundElement.click();
    }

    @Then("^page contains following email for specified elements$")
    public void pageContainsFollowingEmailForSpecifiedElements(String webElement) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement foundElement = getWebElementByObjectAndName(currentPage, webElement);
        assertTrue("Content of found element don't match", PageKeys.TEST_EMAIL.toString().contains(foundElement.getText()));
    }

    @Then("^page contains following text for specified elements$")
    public void pageContainsFollowingTextForSpecifiedElements(List<ItemList> dataTable) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        for (ItemList itemList : dataTable) {
            WebElement foundElement = getWebElementByObjectAndName(currentPage, itemList.getFieldName());
            assertTrue("Content of found element don't match", itemList.getFieldValue().contains(foundElement.getText()));
        }

    }

    @When("^user tap '(.*)' button$")
    public void userTapOnButton(Keys keyName) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        Actions actions = new Actions(driver);
        actions.sendKeys(keyName).perform();
    }

    @Then("^type for element '(.*)' is '(.*)'$")
    public void typeForElementIs(String elementName, String typeName) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement element = getWebElementByObjectAndName(currentPage, elementName);
        String attributeValue = element.getAttribute("type");
        assertTrue("Type for element is not equals", attributeValue.equals(typeName));
    }

    @When("^new page is opened in new tab$")
    public void switchToChildWindow() {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        Set<String> st = driver.getWindowHandles();
        Iterator<String> it = st.iterator();
        String parent = it.next();
        String child = it.next();
        driver.switchTo().window(child);
    }

    @Then("^user refresh the page$")
    public void userRefreshThePage() {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        driver.navigate().refresh();
    }


    @When("^input with name '(.*)' is cleared$")
    public void inputWithNameCollegeInputIsCleared(String elementName) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement element = getWebElementByObjectAndName(currentPage, elementName);
        element.clear();
    }

    @When("^user select random value for select with name '(.*)'$")
    public void userSelectRandomValueForSelectWithNameDegreePath(String elementName) {
        AbstractPageObject currentPage = (AbstractPageObject) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement element = getWebElementByObjectAndName(currentPage, elementName);
        List<WebElement> options = element.findElements(By.tagName("option"));
        Random random = new Random();
        int randomInt = random.nextInt(options.size());
        String randomValue = options.get(randomInt).getText();
        options.stream().filter(r -> r.getText().contains(randomValue)).findFirst().get().click();
        valuesToSave.add(randomValue);
        scenarioContext.save(PageKeys.LIST_RANDOM_VALUES, valuesToSave);
    }

    @Then("^where is an error in console$")
    public void whereIsAnErrorInConsole() {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        assertTrue("Where are no logs", driver.manage().logs().get("browser").getAll().size() > 0);
    }

    @Then("^web element '(.*)' display following message$")
    public void webElementTxtLoginDisplayFollowingMessage(String elementName, DataTable dataTable) {
        String expectedMessage = dataTable.raw().get(0).get(0);
        AbstractPage page = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        WebElement element = getWebElementByObjectAndName(page, elementName);
        String actualMessage = element.getAttribute("validationMessage");
        assertTrue("Message is wrong", expectedMessage.equals(actualMessage));
    }

    @Given("^cookies are deleted$")
    public void cookiesAreDeleted() {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        driver.manage().deleteAllCookies();
    }

    @Given("user navigates to following '(.*)' link")
    public void userNavigatesToFollowingFacebookComLink(String link) {
        WebDriver driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        driver.navigate().to(link);
    }

    @Then("^displayed text for elements is$")
    public void displayedTextForElementsIs(List<ItemList> itemLists) {
        AbstractPage currentPage = (AbstractPage) scenarioContext.getData(PageKeys.CURRENT_PAGE);
        for (ItemList itemList : itemLists) {
            WebElement webElement = getWebElementByObjectAndName(currentPage, itemList.getFieldName());
            await().atMost(WEBELEMENT_TIMEOUT, SECONDS).until(() -> webElement.isDisplayed());
            assertTrue("Text for web element do not corresponds", webElement.getText().equals(itemList.getFieldValue()));
        }
    }
}
