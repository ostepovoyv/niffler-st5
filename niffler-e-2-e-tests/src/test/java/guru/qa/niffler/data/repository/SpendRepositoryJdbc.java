package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import guru.qa.niffler.model.CurrencyValues;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SpendRepositoryJdbc implements SpendRepository {

    private static final DataSource spendDataSource = DataSourceProvider
            .dataSource(DataBase.SPEND);

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO category (category, username) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, category.getCategory());
            ps.setString(2, category.getUsername());
            ps.executeUpdate();

            UUID generatedId = null;
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can`t access to id");
                }
            }
            category.setId(generatedId);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE category " +
                             "SET category = ?, username = ?" +
                             " WHERE id = ?")) {
            ps.setString(1, category.getCategory());
            ps.setString(2, category.getUsername());
            ps.setObject(3, category.getId());
            ps.executeUpdate();
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCategory(CategoryEntity category) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM category WHERE id = ?"
             )) {
            ps.setObject(1, category.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn
                     .prepareStatement(
                             "INSERT INTO spend (username, spend_date, currency, amount, description, category_id)" +
                                     "VALUES (?, ?, ?, ?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {
            ps.setString(1, spend.getUsername());
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setString(3, spend.getCurrency().toString());
            ps.setDouble(4, spend.getAmount());
            ps.setString(5, spend.getDescription());
            ps.setObject(6, findCategoryIdByCategoryName(spend.getCategory()));
            ps.executeUpdate();

            UUID generatedId = null;
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalArgumentException("Can`t access to id");
                }
            }
            spend.setId(generatedId);
            return spend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity editSpend(SpendEntity spend) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE spend " +
                             "SET currency = ?, spend_date = ?, amount = ?, description = ? " +
                             " WHERE id = ?")) {
            ps.setString(1, String.valueOf(spend.getCurrency()));
            ps.setDate(2, (Date) spend.getSpendDate());
            ps.setDouble(3, spend.getAmount());
            ps.setString(4, spend.getDescription());
            ps.setObject(5, spend.getId());
            ps.executeUpdate();
            return spend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeSpend(SpendEntity spend) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM spend WHERE id = ?"
             )) {
            ps.setObject(1, spend.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SpendEntity> findAllByUsername(String username) {
        List<SpendEntity> spends = new ArrayList<>();
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT * FROM spend WHERE username = ?")) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                SpendEntity spend = new SpendEntity();
                spend.setId(UUID.fromString(resultSet.getString("id")));
                spend.setUsername(resultSet.getString("username"));
                spend.setSpendDate(resultSet.getDate("spend_date"));
                spend.setCurrency(CurrencyValues.valueOf(resultSet.getString("currency")));
                spend.setAmount(resultSet.getDouble("amount"));
                spend.setDescription(resultSet.getString("description"));
                spend.setCategory(String.valueOf(UUID.fromString(resultSet.getString("category_id"))));
                spends.add(spend);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return spends;
    }

    public UUID findCategoryIdByCategoryName(String category) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM category WHERE category = ?")) {
            ps.setString(1, category);
            ps.execute();

            try (ResultSet resultSet = ps.getResultSet()) {
                if (resultSet.next()) {
                    return UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can`t access to id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID findSpendIdByCategoryId(UUID categoryId) {
        try (Connection conn = spendDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM spend WHERE category_id = ?")) {
            ps.setObject(1, categoryId);
            ps.execute();

            try (ResultSet resultSet = ps.getResultSet()) {
                if (resultSet.next()) {
                    return UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can`t access to id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
