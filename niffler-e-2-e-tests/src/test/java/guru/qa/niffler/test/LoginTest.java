package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.model.RandomUserJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.ProfilePage;
import guru.qa.niffler.pages.SignInPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(DbCreateUserExtension.class)
public class LoginTest extends BaseTest {

    private final SignInPage signInPage = new SignInPage();
    private final MainPage mainPage = new MainPage();
    private final ProfilePage profilePage = new ProfilePage();

    @Test
    @TestUser
    void loginTest(RandomUserJson userJson) {
        signInPage.signIn(userJson.username(), userJson.testData().password());
        mainPage.clickProfileButton();
        profilePage.verifyProfileInfo(
                userJson.username(),
                userJson.firstname(),
                userJson.surname()
        );
    }
}
