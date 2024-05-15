package guru.qa.niffler.test;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.CategoryHttpExtension;
import guru.qa.niffler.jupiter.extension.SpendHttpExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.SignInPage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@WebTest
@ExtendWith({CategoryHttpExtension.class, SpendHttpExtension.class})
public class SpendingHttpTest extends BaseTest{

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
