package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.AllPeoplePage;
import guru.qa.niffler.pages.FriendsPage;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.SignInPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.*;

@WebTest
@ExtendWith(UserQueueExtension.class)
public class UsersQueueTest extends BaseTest {

    private final SignInPage signInPage = new SignInPage();
    private final MainPage mainPage = new MainPage();
    private final AllPeoplePage allPeoplePage = new AllPeoplePage();
    private final FriendsPage friendsPage = new FriendsPage();

    @Test
    @DisplayName("Проверка наличия пользователей в таблице All People")
    void userNameInAllPeopleTableTest(
            @User(INVITATION_RECEIVED) UserJson user1,
            @User(INVITATION_SEND) UserJson user2
    ) {
        signInPage.signIn(user2.username(), user2.testData().password());
        mainPage.clickAllPeopleButton();
        allPeoplePage.checkUserNameInAllPeopleTable(user1.username());
    }

    @Test
    @DisplayName("Проверка входящего запроса в друзья")
    void incomingFriendRequestTest(
            @User(INVITATION_RECEIVED) UserJson user
    ) {
        signInPage.signIn(user.username(), user.testData().password());
        mainPage.clickFriendsButton();
        friendsPage.checkFriendRequest(user.testData().friendName());
    }

    @Test
    @DisplayName("Проверка исходящего запроса в друзья")
    void sentFriendRequestTest(
            @User(INVITATION_SEND) UserJson user
    ) {
        signInPage.signIn(user.username(), user.testData().password());
        mainPage.clickAllPeopleButton();
        allPeoplePage.checkSentFriendRequest("Pending invitation");
    }

    @Test
    @DisplayName("Проверка статусов у пользователя в таблице All People")
    void friendsStatusInAllPeopleTest(
            @User(WITH_FRIENDS) UserJson user1,
            @User(INVITATION_SEND) UserJson user2
    ) {
        signInPage.signIn(user1.username(), user1.testData().password());
        mainPage.clickAllPeopleButton();
        allPeoplePage.checkFriendsNameAndStatus(
                user1.testData().friendName(),
                "You are friends");
        allPeoplePage.checkFriendsNameAndStatus(
                user2.testData().friendName(),
                "Pending invitation");
    }

    @Test
    @DisplayName("Проверка наличия друга в таблице Friends")
    void friendsNameAndStatusTest(
            @User(WITH_FRIENDS) UserJson user
    ) {
        signInPage.signIn(user.username(), user.testData().password());
        mainPage.clickFriendsButton();
        friendsPage.checkNameAndStatusInFriendsTable(
                user.testData().friendName(),
                "You are friends"
        );

    }

}
