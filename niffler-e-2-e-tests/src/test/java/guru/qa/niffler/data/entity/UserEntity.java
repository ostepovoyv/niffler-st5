package guru.qa.niffler.data.entity;

import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.RandomUserJson;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class UserEntity implements Serializable {
    private UUID id;
    private String username;
    private CurrencyValues currency;
    private String firstname;
    private String surname;
    private byte[] photo;
    private byte[] photoSmall;

    public static UserEntity fromJson(RandomUserJson userJson) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userJson.username());
        userEntity.setCurrency(CurrencyValues.valueOf(userJson.currency().name()));
        userEntity.setFirstname(userJson.firstname());
        userEntity.setSurname(userJson.surname());
        return userEntity;
    }
}
