package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class FriendsPage {

    private final SelenideElement
            friendsTableName = $(".abstract-table tbody");

    @Step("Поиск кнопки Submit invitation для пользователя с именем: {name}")
    public FriendsPage checkFriendRequest(String name) {
        friendsTableName
                .$$("tr")
                .find(text(name))
                .$(".button-icon_type_submit")
                .shouldBe(visible);
        return this;
    }

    @Step("Поиск друга с именем: {name} и статусом: {state}")
    public FriendsPage checkNameAndStatusInFriendsTable(String name, String state) {
        friendsTableName.$$("tr").find(text(name)).shouldHave(text(state));
        return this;
    }

}
