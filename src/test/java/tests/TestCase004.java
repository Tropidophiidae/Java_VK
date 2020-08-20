package tests;

import bars.Navigation;
import forms.LogInForm;
import forms.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.VkApi;

import static asserts.Asserts.*;
import static logger.log4j2.log;
import static utils.Randomizer.getRandomText;

public class TestCase004 extends BaseTest {

    @Test(dataProvider = "testCase004", dataProviderClass = DataProviderClass.class)
    public void testCase004(String loginUser, String passUser, String token, String docPath, String docName) {
        log().info("Test Case --- " + this.getClass().getSimpleName());
        LogInForm logIn = new LogInForm();
        Navigation navigation = new Navigation();
        VkApi vkApi = new VkApi();
        ProfilePage profilePage = new ProfilePage();
        String userId = vkApi.getCurrentUserId(token);
        final int TEXT_SIZE = 10;

        log().info("Step 1 --- Go to VK website");
        goToStartPage();

        log().info("Step 2 --- Log In");
        logIn.LogIn(loginUser, passUser);

        log().info("Step 3 --- Go to 'My Profile' page");
        navigation.goToMyProfile();

        log().info("Step 4 --- Create a wall post using API with random text and get Post ID from API response");
        String randomText = getRandomText(TEXT_SIZE);
        String postId = vkApi.wallPost(userId, randomText, token).path("response.post_id").toString();

        log().info("Step 5 --- Check the wall post is displayed");
        isWallPostDisplayed(userId, randomText, userId, postId);

        log().info("Step 6 --- Edit the wall post using API - edit post text and add text file");
        randomText = getRandomText(TEXT_SIZE);
        String attachment = vkApi.getDocAttachment(token, userId, docPath);
        vkApi.wallEdit(userId, postId, randomText, token, attachment);

        log().info("Step 7 --- Check the wall post is edited");
        isPostUpdatedWithDocument(userId, randomText, userId, postId, docName);

        log().info("Step 8 --- Add a comment to the wall post using API");
        randomText = getRandomText(TEXT_SIZE);
        String commentId = vkApi.createComment(userId, postId, randomText, token).
                path("response.comment_id").toString();

        log().info("Step 9 --- Check the comment is added");
        isCommentCreated(userId, commentId, postId, randomText, userId);

        log().info("Step 10 --- Like the wall post");
        profilePage.clickLikeIcon(userId, postId);

        log().info("Step 11 --- Check the wall post is liked using API");
        isPostLiked(userId, postId, userId, token);

        log().info("Step 12 --- Delete the wall post using API");
        vkApi.wallDelete(userId, postId, token);

        log().info("Step 13 --- Check the wall post is deleted");
        Assert.assertTrue(profilePage.isPostDeleted(userId, postId), "The post is not deleted");

        log().info("Post Condition --- Delete uploaded text file");
        vkApi.docsDelete(userId, attachment, token);
    }
}
