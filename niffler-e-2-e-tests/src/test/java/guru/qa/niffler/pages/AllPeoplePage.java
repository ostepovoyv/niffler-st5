package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AllPeoplePage {

    private final SelenideElement
            allPeopleTable = $(".abstract-table tbody");

    @Step("Проверка текста статуса в отправленном запросе")
    public AllPeoplePage checkSentFriendRequest(String text) {
        allPeopleTable.shouldHave(text(text));
        return this;
    }

    @Step("Проверка наличия имени пользователя в таблице All People")
    public AllPeoplePage checkUserNameInAllPeopleTable(String name) {
        allPeopleTable.$$("tr").find(text(name)).shouldBe(visible);
        return this;
    }

    @Step("Проверка наличия имени: {user} и статуса: {status} в таблице AllPeople")
    public AllPeoplePage checkFriendsStatus(String user, String status) {
        allPeopleTable.$$("tr").find(text(user)).shouldHave(text(status));
        return this;
    }

}
