package guru.qa.niffler.data.sjdbc;

import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.model.CurrencyValues;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserEntityRowMapper implements RowMapper<UserEntity> {

    public static final UserEntityRowMapper instance = new UserEntityRowMapper();

    private UserEntityRowMapper() {
    }

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId((UUID) rs.getObject("id"));
        userEntity.setUsername(rs.getString("username"));
        userEntity.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
        userEntity.setFirstname(rs.getString("firstname"));
        userEntity.setSurname(rs.getString("surname"));
        userEntity.setPhoto(rs.getBytes("photo"));
        userEntity.setPhotoSmall(rs.getBytes("photo_small"));
        return userEntity;
    }
}
