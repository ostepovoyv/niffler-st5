package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class SignInPage {

    private final SelenideElement
            userNameInput = $("input[name='username']"),
            passwordInput = $("input[name='password']"),
            signInButton = $("button[type='submit']");

    @Step("Fill username and password, Click signIn button")
    public SignInPage signIn(String userName, String password){
        userNameInput.setValue(userName);
        passwordInput.setValue(password);
        signInButton.click();
        return this;
    }

}
