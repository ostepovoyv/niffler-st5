package guru.qa.niffler.utils;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.model.CurrencyValues;

import java.sql.Date;
import java.util.UUID;

public class UpdateCategoryAndSpend {

    private final CategoryEntity categoryEntity = new CategoryEntity();
    private final SpendEntity spendEntity = new SpendEntity();
    private final SpendRepositoryJdbc spendRepositoryJdbc = new SpendRepositoryJdbc();

    public CategoryEntity updateCategory(String name) {
        categoryEntity.setId(spendRepositoryJdbc.findCategoryIdByCategoryName(name));
        categoryEntity.setUsername("OLEG EDITED");
        categoryEntity.setCategory("Обучение1 EDITED");
        spendRepositoryJdbc.editCategory(categoryEntity);
        return categoryEntity;
    }

    public SpendEntity updateSpend(UUID categoryId) {
        spendEntity.setId(spendRepositoryJdbc.findSpendIdByCategoryId(categoryId));
        spendEntity.setSpendDate(new Date(System.currentTimeMillis()));
        spendEntity.setCategory("Обучение1 EDITED");
        spendEntity.setCurrency(CurrencyValues.RUB);
        spendEntity.setAmount(500.00);
        spendEntity.setDescription("QA.GURU Advanced 5 EDITED");
        spendRepositoryJdbc.editSpend(spendEntity);
        return spendEntity;
    }
}
