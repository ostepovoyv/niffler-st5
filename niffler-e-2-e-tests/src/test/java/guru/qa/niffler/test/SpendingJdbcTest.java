package guru.qa.niffler.test;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.CategoryJdbcExtension;
import guru.qa.niffler.jupiter.extension.SpendJdbcExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.SignInPage;
import guru.qa.niffler.utils.UpdateCategoryAndSpend;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@WebTest
@ExtendWith({CategoryJdbcExtension.class, SpendJdbcExtension.class})
public class SpendingJdbcTest extends BaseTest{

    private final SignInPage signInPage = new SignInPage();
    private final MainPage mainPage = new MainPage();

    @GenerateCategory(
            category = "Обучение1",
            username = "oleg1"
    )
    @GenerateSpend(
            username = "oleg1",
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB,
            category = "Обучение1"
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        signInPage.signIn("oleg1", "12345");
        SelenideElement rows = mainPage.findSpendingRowByDescription(spendJson.description());
        mainPage.chooseSpendingFromTable(rows)
                .deleteSpending("Delete selected")
                .checkCountOfSpendings(0);
    }

    @GenerateCategory(
            category = "Обучение2",
            username = "oleg2"
    )
    @GenerateSpend(
            username = "oleg2",
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB,
            category = "Обучение2"
    )
    @Test
    void spendingShouldBeDeletedAfterUpdateCategoryAndSpend(SpendJson spendJson) {
        UpdateCategoryAndSpend update = new UpdateCategoryAndSpend();
        signInPage.signIn("oleg2", "12345");
        CategoryEntity categoryEntity = update.updateCategory(spendJson.category());
        SpendEntity spendEntity = update.updateSpend(categoryEntity.getId());
        SelenideElement rows = mainPage.findSpendingRowByDescription(spendEntity.getDescription());
        mainPage.chooseSpendingFromTable(rows)
                .deleteSpending("Delete selected")
                .checkCountOfSpendings(0);
    }
    
}
