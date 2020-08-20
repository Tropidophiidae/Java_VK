package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import static webDriver.DriverFactory.getBrowser;
import static webDriver.DriverFactory.getDriver;

public class DownloadImage {
    public static String getFileNameByPath(String filePath) {
        return filePath.replaceAll("src/test/resources/files/original", "");
    }

    public static String getFileFormatByName(String fileName) {
        return fileName.replaceAll("^([^.]*).", "");
    }

    public static void checkForExistedFile(String filePath) {
        String fileName = getFileNameByPath(filePath);
        filePath = getBrowser().getBrowserDownloadDirectory().concat(fileName);
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void downloadImage(String filePath) {
        String fileName = getFileNameByPath(filePath);
        filePath = getBrowser().getBrowserDownloadDirectory().concat(fileName);
        String format = getFileFormatByName(fileName);
        String url = getDriver().getCurrentUrl();
        try {
            URL image = new URL(url);
            BufferedImage saveImage = ImageIO.read(image);
            ImageIO.write(saveImage, format, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
