package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.extension.CategoryExtension;
import guru.qa.niffler.jupiter.extension.SpendExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.SignInPage;
import guru.qa.niffler.pages.WelcomePage;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@ExtendWith({CategoryExtension.class, SpendExtension.class})
public class SpendingTest {

    private final WelcomePage welcomePage = new WelcomePage();
    private final SignInPage signInPage = new SignInPage();
    private final MainPage mainPage = new MainPage();

    static {
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        Configuration.browserCapabilities = chromeOptions;
    }

    @BeforeEach
    void doLogin() {
        Selenide.open("http://127.0.0.1:3000/main");
        welcomePage.clickLoginButton("Login");
        signInPage.signIn("oleg", "12345");
    }

    @AfterEach
    void doScreenshot() {
        Allure.addAttachment(
                "Screen on test end",
                new ByteArrayInputStream(
                        Objects.requireNonNull(
                                Selenide.screenshot(OutputType.BYTES)
                        )
                )
        );
    }

    @GenerateCategory(
            category = "Обучение",
            username = "oleg"
    )
    @GenerateSpend(
            username = "oleg",
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB,
            category = "Обучение"
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        SelenideElement rows = mainPage.findSpendingRowByDescription(spendJson.description());
        mainPage.chooseSpendingFromTable(rows)
                .deleteSpending("Delete selected")
                .checkCountOfSpendings(0);
    }
    
}
