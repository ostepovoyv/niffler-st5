package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.AllPeoplePage;
import guru.qa.niffler.pages.FriendsPage;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.SignInPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.*;

@WebTest
@ExtendWith(UserQueueExtension.class)
public class UsersQueueFriendRequestTest extends BaseTest {

    private final SignInPage signInPage = new SignInPage();
    private final MainPage mainPage = new MainPage();
    private final AllPeoplePage allPeoplePage = new AllPeoplePage();
    private final FriendsPage friendsPage = new FriendsPage();

    @BeforeEach
    void doLogin(@User(INVITATION_RECEIVED) UserJson user) {
        signInPage.signIn(user.username(), user.testData().password());
    }

    @Test
    @DisplayName("Проверка исходящего запроса в друзья")
    void sentFriendRequestTest() {
        mainPage.clickAllPeopleButton();
        allPeoplePage.checkSentFriendRequest("Pending invitation");
    }

    @Test
    @DisplayName("Проверка входящего запроса в друзья")
    void incomingFriendRequestTest(
            @User(INVITATION_RECEIVED) UserJson user
    ) {
        mainPage.clickFriendsButton();
        friendsPage.checkFriendRequest(user.testData().friendName());
    }
}
