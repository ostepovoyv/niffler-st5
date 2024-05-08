package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final SelenideElement
            deleteButton = $(".spendings__bulk-actions button"),
            friendsButton = $("a[href*='friends']"),
            allPeopleButton = $("a[href*='people']");

    private final ElementsCollection
            spendingsTableRows = $(".spendings-table tbody").$$("tr");

    @Step("Поиск расходов по описанию")
    public SelenideElement findSpendingRowByDescription(String description) {
        return spendingsTableRows.find(text(description));
    }

    @Step("Выбор найденных расходов")
    public MainPage chooseSpendingFromTable(SelenideElement rows) {
        rows.$("td").scrollTo().click();
        return this;
    }

    @Step("Удаление найденных расходов из таблицы")
    public MainPage deleteSpending(String buttonName) {
        deleteButton.shouldHave(text(buttonName)).click();
        return this;
    }

    @Step("Проверка размера таблицы после удаления")
    public MainPage checkCountOfSpendings(int expectedSize) {
        spendingsTableRows.shouldHave(size(expectedSize));
        return this;
    }

    @Step("Переход на страницу Friends")
    public MainPage clickFriendsButton() {
        friendsButton.click();
        return this;
    }

    @Step("Переход на страницу All People")
    public MainPage clickAllPeopleButton() {
        allPeopleButton.click();
        return this;
    }

}
