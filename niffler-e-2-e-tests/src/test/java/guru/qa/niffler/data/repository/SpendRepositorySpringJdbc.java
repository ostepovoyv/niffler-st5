package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import guru.qa.niffler.data.sjdbc.SpendEntityRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class SpendRepositorySpringJdbc implements SpendRepository {

    private final static JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceProvider.dataSource(DataBase.SPEND));

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO category (category, username) VAlUES (?, ?)",
                            PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, category.getCategory());
                    preparedStatement.setString(2, category.getUsername());
                    return preparedStatement;
                }, keyHolder
        );
        category.setCategory((String) keyHolder.getKeys().get("id"));

        return category;
    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {
        jdbcTemplate.update("UPDATE category SET category = ?, username = ? WHERE id = ?",
                category.getCategory(),
                category.getUsername(),
                category.getId());

        return category;
    }

    @Override
    public void removeCategory(CategoryEntity category) {
        jdbcTemplate.update("DELETE FROM category where id = ?", category.getId());
    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO public.spend(username, spend_date, currency, amount, description, category_id)" +
                                    " VALUES (?, ?, ?, ?, ?, ?))",
                            PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, spend.getUsername());
                    preparedStatement.setDate(2, (Date) spend.getSpendDate());
                    preparedStatement.setString(3, spend.getCurrency().toString());
                    preparedStatement.setDouble(4, spend.getAmount());
                    preparedStatement.setString(5, spend.getDescription());
                    preparedStatement.setObject(6, spend.getCategory());
                    return preparedStatement;
                }, keyHolder
        );
        spend.setId(UUID.fromString((String) keyHolder.getKeys().get("id")));

        return spend;
    }

    @Override
    public SpendEntity editSpend(SpendEntity spend) {
        jdbcTemplate.update("UPDATE spend SET username = ?, spend_date = ?, currency = ?, amount = ?, description = ?, category_id = ? WHERE id = ?",
                spend.getUsername(),
                (Date) spend.getSpendDate(),
                spend.getCurrency().toString(),
                spend.getAmount(),
                spend.getDescription(),
                spend.getCategory(),
                spend.getId());
        return spend;
    }

    @Override
    public void removeSpend(SpendEntity spend) {
        jdbcTemplate.update("DELETE FROM public.spend where id = ?", spend.getId());
    }

    @Override
    public List<SpendEntity> findAllByUsername(String username) {
        return jdbcTemplate.query(
                "SELECT * FROM spend WHERE username = ?",
                SpendEntityRowMapper.instance, username
        );
    }
}
