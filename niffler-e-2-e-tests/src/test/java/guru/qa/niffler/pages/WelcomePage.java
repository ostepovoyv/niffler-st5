package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class WelcomePage {

    private final SelenideElement
            loginButton = $("a[href*='redirect']");

    @Step("Click login button")
    public WelcomePage clickLoginButton(String buttonName) {
        loginButton.should(visible).shouldHave(text(buttonName)).click();
        return this;
    }

}
