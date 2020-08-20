package asserts;

import forms.LogInForm;
import forms.ProfilePage;
import org.testng.Assert;
import utils.VkApi;

import static logger.log4j2.log;

public final class Asserts {
    private static final ProfilePage profilePage = new ProfilePage();
    private static final VkApi vkApi = new VkApi();
    private static final LogInForm logInForm = new LogInForm();

    public static void isWallPostDisplayed(String userId, String randomText, String postUserId, String postId) {
        try {
            Assert.assertTrue(profilePage.isWallPostDisplayed(userId, randomText, postUserId, postId),
                    "Wall Post from is not displayed" +
                            "\nUserID = " + userId +
                            "\nPost Text = " + randomText +
                            "\nPostID = " + postId +
                            "\nPost User Id = " + postUserId + "\n");
        } catch (Throwable e) {
            log().error("isWallPostDisplayed is failed", e);
            Assert.fail();
        }
    }

    public static void isPostUpdatedWithImage(String userId, String postId, String randomText, String imagePath) {
        try {
            Assert.assertTrue(profilePage.isPostUpdated(userId, postId, randomText, imagePath),
                    "Wall Post from is not displayed" +
                            "\nUserID = " + userId +
                            "\nPost Text = " + randomText +
                            "\nPostID = " + postId +
                            "\nImage Path = " + imagePath + "\n");
        } catch (Throwable e) {
            log().error("isPostUpdatedWithImage is failed", e);
            Assert.fail();
        }
    }

    public static void isPostUpdatedWithDocument(String userId, String randomText, String postUserId, String postId, String docName) {
        try {
            Assert.assertTrue(profilePage.isWallPostDisplayed(userId, randomText, postUserId, postId),
                    "Wall Post from is not displayed" +
                            "\nUserID = " + userId +
                            "\nPost Text = " + randomText +
                            "\nPostID = " + postId + "\n");
            Assert.assertEquals(docName, profilePage.getDocName(userId, postId),
                    "The document name is not correct" +
                            "\nDocument Name = " + docName + "\n");
        } catch (Throwable e) {
            log().error("isPostUpdatedWithDocument is failed", e);
            Assert.fail();
        }
    }

    public static void isCommentCreated(String userId, String commentId, String postId, String expectedText, String expectedUserId) {
        try {
            Assert.assertTrue(profilePage.isCommentCreated(userId, commentId, postId, expectedText, expectedUserId),
                    "Text or User is not correct" +
                            "\nUserID = " + userId +
                            "\nComment Text = " + expectedText +
                            "\nPostID = " + postId +
                            "\nCommentID = " + commentId +
                            "\nComment User ID = " + expectedUserId + "\n");
        } catch (Throwable e) {
            log().error("isCommentCreated is failed", e);
            Assert.fail();
        }
    }

    public static void isPostLiked(String userId, String postId, String ownerId, String token) {
        try {
            Assert.assertEquals(1, vkApi.isLiked(userId, "post", postId, ownerId, token),
                    "Like is not added to the post" +
                            "\nUserID = " + userId +
                            "\nPostID = " + postId +
                            "\nOwnerID = " + ownerId + "\n");
        } catch (Throwable e) {
            log().error("isPostLiked", e);
            Assert.fail();
        }
    }

    public static void isPostUnliked(String userId, String postId, String ownerId, String token) {
        try {
            Assert.assertEquals(0, vkApi.isLiked(userId, "post", postId, ownerId, token),
                    "Like is not added to the post" +
                            "\nUserID = " + userId +
                            "\nPostID = " + postId +
                            "\nOwnerID = " + ownerId + "\n");
        } catch (Throwable e) {
            log().error("isPostLiked", e);
            Assert.fail();
        }
    }

    public static void isPostDeleted(String userId, String postId) {
        try {
            Assert.assertTrue(profilePage.isPostDeleted(userId, postId),
                    "The post is not deleted" +
                            "\nUserID = " + userId +
                            "\nPostID = " + postId + "\n");
        } catch (Throwable e) {
            log().error("isPostDeleted", e);
            Assert.fail();
        }
    }

    public static void isQuickLogInFormDisplayed() {
        try {
            Assert.assertTrue(logInForm.isQuickLogInFormDisplayed(),
                    "Quick Log In form is not displayed");
        } catch (Throwable e) {
            log().error("isQuickLogInFormDisplayed", e);
            Assert.fail();
        }
    }
}
