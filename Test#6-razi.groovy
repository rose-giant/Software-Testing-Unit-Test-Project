import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.SelectorMethod

import com.thoughtworks.selenium.Selenium
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.WebDriver
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium
import static org.junit.Assert.*
import java.util.regex.Pattern
import static org.apache.commons.lang3.StringUtils.join
import org.testng.asserts.SoftAssert

SoftAssert softAssertion = new SoftAssert();
WebUI.openBrowser('https://www.google.com/')
def driver = DriverFactory.getWebDriver()
String baseUrl = "https://www.google.com/"
selenium = new WebDriverBackedSelenium(driver, baseUrl)
selenium.open("http://localhost:8080/swagger-ui/index.html#/")
selenium.click("xpath=//div[@id='operations-authentication-controller-signup']/div/button")
selenium.click("xpath=//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div/div[2]/button")
selenium.click("xpath=//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")
selenium.type("xpath=//div[@id='operations-authentication-controller-signup']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea", ("{\n  \"username\":\"razi\",\n  \"password\":\"123\"\n}").toString())
selenium.click("xpath=//div[@id='operations-authentication-controller-signup']/div[2]/div/div[2]/button")
selenium.click("xpath=//div[@id='operations-authentication-controller-signup']/div/button")
selenium.click("xpath=//div[@id='operations-commodities-controller-getCommodities']/div/button/span[2]/a/span")
selenium.click("xpath=//div[@id='operations-commodities-controller-getCommodities']/div[2]/div/div/div/div[2]/button")
selenium.click("xpath=//div[@id='operations-commodities-controller-getCommodities']/div[2]/div/div[2]/button")
selenium.click("css=#operations-commodities-controller-getCommodities > div.opblock-summary.opblock-summary-get > button.opblock-summary-control > svg.arrow")
selenium.click("xpath=//div[@id='operations-user-controller-addCredit']/div/button/span[2]/a/span")
selenium.click("xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div/div[2]/button")
selenium.click("xpath=//input[@value='']")
selenium.type("xpath=//input[@value='razi']", "razi")
selenium.click("xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")
selenium.type("xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea", ("{\n  \"username\":\"razi\",\n  \"credit\":2000\n}").toString())
selenium.click("xpath=//div[@id='operations-user-controller-addCredit']/div[2]/div/div[2]/button")
selenium.click("css=button.opblock-summary-control > svg.arrow")
selenium.click("xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div/button")
selenium.click("xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div/div[2]/button")
selenium.click("xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")
selenium.type("xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea", ("{\n  \"username\":\"razi\",\n  \"id\":\"1\"\n}").toString())
selenium.click("xpath=//div[@id='operations-buy-list-controller-addToBuyList']/div[2]/div/div[2]/button")
selenium.click("css=#operations-buy-list-controller-addToBuyList > div.opblock-summary.opblock-summary-post > button.opblock-summary-control > svg.arrow")
selenium.click("xpath=//div[@id='operations-buy-list-controller-purchaseBuyList']/div/button")
selenium.click("xpath=//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div/div[2]/button")
selenium.click("xpath=//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")
selenium.type("xpath=//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea", ("{\n  \"username\":\"razi\",\n  \"id\":\"1\"\n}").toString())
selenium.click("xpath=//div[@id='operations-buy-list-controller-purchaseBuyList']/div[2]/div/div[2]/button")
selenium.click("css=#operations-buy-list-controller-purchaseBuyList > div.opblock-summary.opblock-summary-post > button.opblock-summary-control > svg.arrow")
selenium.click("xpath=//div[@id='operations-buy-list-controller-getPurchasedList']/div/button/span[2]/a/span")
selenium.click("xpath=//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div/div[2]/button")
selenium.click("xpath=//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea")
selenium.type("xpath=//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div/div[3]/div[2]/div/div/div/textarea", ("{\n  \"username\":\"razi\"\n}").toString())
selenium.click("xpath=//div[@id='operations-buy-list-controller-getPurchasedList']/div[2]/div/div[2]/button")
selenium.click("css=#operations-buy-list-controller-getPurchasedList > div.opblock-summary.opblock-summary-post > button.opblock-summary-control > svg.arrow")
