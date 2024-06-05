package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    UserAuthEntity createUserInAuth(UserAuthEntity user);

    UserEntity createUserInUserdata(UserEntity user);

    UserAuthEntity updateUserInAuth(UserAuthEntity user);

    UserEntity updateUserInUserdata(UserEntity user);

    Optional<UserEntity> findUserInUserdataById(UUID id);

}
