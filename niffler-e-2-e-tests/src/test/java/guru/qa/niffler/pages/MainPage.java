package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final SelenideElement
            deleteButton = $(".spendings__bulk-actions button");

    private final ElementsCollection
            spendingsTableRows = $(".spendings-table tbody").$$("tr");

    @Step("Search spending by description")
    public SelenideElement findSpendingRowByDescription(String description) {
        return spendingsTableRows.find(text(description));
    }

    @Step("Select found spending")
    public MainPage chooseSpendingFromTable(SelenideElement rows) {
        rows.$("td").scrollTo().click();
        return this;
    }

    @Step("Delete found spending from the table")
    public MainPage deleteSpending(String buttonName) {
        deleteButton.shouldHave(text(buttonName)).click();
        return this;
    }

    @Step("Check table size after delete")
    public MainPage checkCountOfSpendings(int expectedSize) {
        spendingsTableRows.shouldHave(size(expectedSize));
        return this;
    }

}
