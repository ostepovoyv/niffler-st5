package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {

    private final SelenideElement
            avatarContainer = $(".avatar-container"),
            inputName = $(byName("firstname")),
            inputSurname = $(byName("surname"));

    @Step("Проверка данных пользователя на странице Profile")
    public ProfilePage verifyProfileInfo(String username, String firstName, String surname) {
        avatarContainer.shouldHave(text(username));
        inputName.shouldHave(value(firstName));
        inputSurname.shouldHave(value(surname));
        return this;
    }

}
