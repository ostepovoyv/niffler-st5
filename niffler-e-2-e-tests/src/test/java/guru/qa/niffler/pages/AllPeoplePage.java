package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
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
        allPeopleTable.shouldHave(text(name));
        return this;
    }

    @Step("Проверка наличия имени: {name} и статуса: {status} в табилце AllPeople")
    public AllPeoplePage checkFriendsNameAndStatus(String user, String status) {
        allPeopleTable.$$("tr").find(text(user));
        allPeopleTable.shouldHave(text(status));
        return this;
    }

}
