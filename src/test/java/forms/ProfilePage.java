package forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DownloadImage;
import utils.ImageComparator;

import java.util.ArrayList;

import static webDriver.DriverFactory.getDriver;


public class ProfilePage {

    private final String vkUrl = "https://vk.com/id%s";
    private final String xpathPostUser = ".//div[contains(@id,'post%s_%s')]//a[@class='author']";
    private final String xpathPostText = ".//div[@id='wpt%s_%s']//div[contains(@class, 'wall_post_text')]";
    private final String xpathWallPosts = "//div[@id='page_wall_posts']";
    private final String xpathAttachment = "//div[@id='wpt%s_%s']//a";
    private final String xpathMoreLink = "//a[@class='pv_actions_more']";
    private final String xpathOpenOriginal = "//a[@id='pv_more_act_download']";
    private final By xpathCloseImage = By.className("pv_close_btn");
    private final String xpathCommentLink = ".//a[@href='/wall%s_%s']";
    private final String xpathPost = "//*[@id='post%s_%s']";
    private final By xpathCloseCommentPopup = By.className("wk_close_inner");
    private final String xpathLikeIcon = "//div[contains(@class, '_like_wall%s_%s')]//a[contains(@class, 'like_btn') and contains(@class,'_like')]";
    private final int deleteWallPostTimeout = 10;
    private final String xpathDocument = "//div[contains(@id,'post%s_%s')]//a[@class='page_doc_title']";

    public boolean isWallPostDisplayed(String userId, String postText, String postUserId, String postId) {
        String vkPostUserId = String.format(vkUrl, postUserId);
        String user = String.format(xpathPostUser, userId, postId);
        String text = String.format(xpathPostText, userId, postId);
        WebElement wallPosts = getDriver().findElement(By.xpath(xpathWallPosts));
        WebElement wallPostUser = wallPosts.findElement(By.xpath(user));
        WebElement wallPostText = wallPosts.findElement(By.xpath(text));

        boolean isAuthorCorrect = wallPostUser.getAttribute("href").equals(vkPostUserId);
        boolean isTextCorrect = wallPostText.getText().equals(postText);
        return isAuthorCorrect && isTextCorrect;
    }

    public boolean isPostUpdated(String userId, String postId, String expectedText, String filePath) {
        String text = String.format(xpathPostText, userId, postId);
        WebElement wallPosts = getDriver().findElement(By.xpath(xpathWallPosts));
        WebElement wallPostText = wallPosts.findElement(By.xpath(text));

        String actualText = wallPostText.getText();
        boolean textMatch = expectedText.equals(actualText);
        boolean attachmentMatch = isFileUploaded(userId, postId, filePath);
        return textMatch && attachmentMatch;
    }

    public boolean isCommentCreated(String userId, String commentId, String postId, String expectedText, String expectedUserId) {
        WebElement wallPosts = getDriver().findElement(By.xpath(xpathWallPosts));
        wallPosts.findElement(By.xpath(String.format(xpathCommentLink, userId, postId))).click();
        String comment = String.format(xpathPost, userId, commentId);
        WebElement actualComment = getDriver().findElement(By.xpath(comment));
        boolean isUserCorrect = actualComment.findElements(By.xpath(String.format("//*[@*='%s']", expectedUserId))).size() > 0;
        boolean isTextCorrect = actualComment.findElements(By.xpath(String.format("//*[text()='%s']", expectedText))).size() > 0;
        getDriver().findElement(xpathCloseCommentPopup).click();
        return isUserCorrect && isTextCorrect;
    }

    public void clickLikeIcon(String userId, String postId) {
        WebElement wallPosts = getDriver().findElement(By.xpath(xpathWallPosts));
        wallPosts.findElement(By.xpath(String.format(xpathLikeIcon, userId, postId))).click();
    }

    public boolean isPostDeleted(String userId, String postId) {
        WebDriverWait wait = new WebDriverWait(getDriver(),deleteWallPostTimeout);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath((String.format(xpathPost, userId, postId)))));
        return !getDriver().findElement(By.xpath((String.format(xpathPost, userId, postId)))).isDisplayed();
    }

    public String getUserLink(){
        return getDriver().getCurrentUrl();
    }

    public String getIdFromProfileLink(String url) {
        return url.replaceAll("^(.*?)id", "");
    }

    public String getDocName(String userId, String postId){
        return getDriver().findElement(By.xpath(String.format(xpathDocument, userId, postId))).getText();
    }

    private boolean isFileUploaded(String userId, String postId, String filePath) {
        boolean result;
        DownloadImage.checkForExistedFile(filePath);
        openImage(userId, postId);

        ArrayList tabs = new ArrayList(getDriver().getWindowHandles());
        getDriver().switchTo().window((String) tabs.get(1));

        DownloadImage.downloadImage(filePath);
        result = ImageComparator.CompareImage(filePath, filePath.replace("original", "downloads"));

        getDriver().close();
        getDriver().switchTo().window((String) tabs.get(0));
        getDriver().findElement(xpathCloseImage).click();

        return result;
    }

    private void openImage(String userId, String postId) {
        getDriver().findElement(By.xpath(String.format(xpathAttachment, userId, postId))).click();
        getDriver().findElement(By.xpath(xpathMoreLink)).click();
        getDriver().findElement(By.xpath(xpathOpenOriginal)).click();
    }
}
