package tests;

import bars.Navigation;
import bars.Header;
import forms.LogInForm;
import forms.ProfilePage;
import org.testng.annotations.Test;
import utils.VkApi;

import static asserts.Asserts.*;
import static logger.log4j2.log;
import static utils.Randomizer.getRandomText;
import static webDriver.DriverFactory.getDriver;

public class TestCase002 extends BaseTest{

    @Test(dataProvider = "testCase002", dataProviderClass = DataProviderClass.class)
    public void testCase002(String loginUser, String passUser, String token){
        log().info("Test Case --- " + this.getClass().getSimpleName());
        LogInForm logIn = new LogInForm();
        Navigation navigation = new Navigation();
        VkApi vkApi = new VkApi();
        ProfilePage profilePage = new ProfilePage();
        Header header = new Header();
        final int TEXT_SIZE = 10;

        log().info("Step 1 --- Go to VK website");
        goToStartPage();

        log().info("Step 2 --- Log In");
        logIn.LogIn(loginUser, passUser);

        log().info("Step 3 --- Go to 'My Profile' page");
        navigation.goToMyProfile();

        log().info("Step 4 --- Get user URL");
        String userLink = profilePage.getUserLink();

        log().info("Step 5 --- Get user ID");
        String userId = profilePage.getIdFromProfileLink(userLink);

        log().info("Step 6 --- Create a wall post using API with random text and get Post ID from API response");
        String randomText = getRandomText(TEXT_SIZE);
        String postId = vkApi.wallPost(userId, randomText, token).path("response.post_id").toString();

        log().info("Step 7 --- Check the wall post is displayed");
        isWallPostDisplayed(userId, randomText, userId, postId);

        log().info("Step 8 --- Like the wall post");
        profilePage.clickLikeIcon(userId, postId);

        log().info("Step 9 --- Check the wall post is liked using API");
        isPostLiked(userId, postId, userId, token);

        log().info("Step 10 --- Log out");
        header.logOut();

        log().info("Step 11 --- Go to VK website");
        goToStartPage();

        log().info("Step 12 --- Log In");
        logIn.LogIn(loginUser, passUser);

        log().info("Step 13 --- Go to user page via URL (Step 4)");
        getDriver().get(userLink);

        log().info("Step 14 --- Check the wall post is displayed");
        isWallPostDisplayed(userId, randomText, userId, postId);

        log().info("Step 15 --- Unlike the wall post.");
        profilePage.clickLikeIcon(userId, postId);

        log().info("Step 16 --- Check the wall post isn't liked using API");
        isPostUnliked(userId, postId, userId, token);

        log().info("Post Condition --- Delete created wall post");
        vkApi.wallDelete(userId, postId, token);
    }
}
