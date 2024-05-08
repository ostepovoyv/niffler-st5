package guru.qa.niffler.test;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.extension.CategoryExtension;
import guru.qa.niffler.jupiter.extension.SpendExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.SignInPage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({CategoryExtension.class, SpendExtension.class})
public class SpendingTest extends BaseTest{

    private final SignInPage signInPage = new SignInPage();
    private final MainPage mainPage = new MainPage();

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
        signInPage.signIn("oleg", "12345");
        SelenideElement rows = mainPage.findSpendingRowByDescription(spendJson.description());
        mainPage.chooseSpendingFromTable(rows)
                .deleteSpending("Delete selected")
                .checkCountOfSpendings(0);
    }
    
}
