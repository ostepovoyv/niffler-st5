package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositorySpringJdbc;
import guru.qa.niffler.model.RandomUserJson;

public class DbCreateUserExtension extends AbstractCreateUserExtension {

    UserRepository userRepository = new UserRepositorySpringJdbc();

    @Override
    protected RandomUserJson createUser(RandomUserJson user) {
        userRepository.createUserInAuth(UserAuthEntity.fromJson(user));
        userRepository.createUserInUserdata(UserEntity.fromJson(user));
        return user;
    }
}
