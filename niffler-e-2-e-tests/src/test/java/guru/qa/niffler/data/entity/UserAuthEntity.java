package guru.qa.niffler.data.entity;

import guru.qa.niffler.model.RandomUserJson;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Getter
@Setter
public class UserAuthEntity implements Serializable {
    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private List<AuthorityEntity> authorities = new ArrayList<>();

    public static UserAuthEntity fromJson(RandomUserJson userJson) {
        AuthorityEntity read = new AuthorityEntity();
        read.setAuthority(Authority.read);
        AuthorityEntity write = new AuthorityEntity();
        write.setAuthority(Authority.write);
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setUsername(userJson.username());
        userAuthEntity.setPassword(userJson.testData().password());
        userAuthEntity.setEnabled(true);
        userAuthEntity.setAccountNonExpired(true);
        userAuthEntity.setAccountNonLocked(true);
        userAuthEntity.setCredentialsNonExpired(true);
        userAuthEntity.setAuthorities(List.of(read, write));
        return userAuthEntity;
    }
}