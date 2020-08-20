package tests;

import bars.Navigation;
import forms.LogInForm;
import org.testng.annotations.Test;
import utils.VkApi;

import static asserts.Asserts.isPostUpdatedWithImage;
import static logger.log4j2.log;
import static utils.Randomizer.getRandomText;

public class TestCase005 extends BaseTest{

    @Test(dataProvider = "testCase005", dataProviderClass = DataProviderClass.class)
    public void testCase5(String loginUser, String passUser, String token, String imagePath) {
        log().info("Test Case --- " + this.getClass().getSimpleName());
        LogInForm logIn = new LogInForm();
        Navigation navigation = new Navigation();
        VkApi vkApi = new VkApi();
        final int TEXT_SIZE = 10;

        log().info("Step 1 --- Go to VK website");
        goToStartPage();

        log().info("Step 2 --- Log In");
        logIn.LogIn(loginUser, passUser);

        log().info("Step 3 --- Go to 'My Profile' page");
        navigation.goToMyProfile();
        String userId = vkApi.getCurrentUserId(token);

        log().info("Step 4 --- Create a wall post using API with random text and and image (from test data). Get Post ID from API response");
        String randomText = getRandomText(TEXT_SIZE);
        String attachment = vkApi.getPhotoAttachment(token, userId, imagePath);
        String postId = vkApi.wallPost(userId, randomText, token, attachment).path("response.post_id").toString();

        log().info("Step 5 --- Check the wall post is displayed");
        isPostUpdatedWithImage(userId, postId, randomText, imagePath);

        log().info("Post Condition --- Delete created wall post");
        vkApi.wallDelete(userId, postId, token);

        log().info("Post Condition --- Delete uploaded photo");
        vkApi.photosDelete(userId, attachment, token);

    }
}
