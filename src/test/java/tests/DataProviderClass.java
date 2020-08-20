package tests;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
    @DataProvider
    public static Object[][] testCase001() {
        return new Object[][]{
                {"login",
                        "password",
                        "access_token",
                        "src/test/resources/files/original/test.jpg"}
        };
    }

    @DataProvider
    public static Object[][] testCase002() {
        return new Object[][]{
                {"login",
                        "password",
                        "access_token"}
        };
    }

    @DataProvider
    public static Object[][] testCase003() {
        return new Object[][]{
                {"login",
                        "password",
                        "access_token"}
        };
    }

    @DataProvider
    public static Object[][] testCase004() {
        return new Object[][]{
                {"login",
                        "password",
                        "access_token",
                        "src/test/resources/files/original/testDoc.txt",
                        "testDoc.txt"}
        };
    }

    @DataProvider
    public static Object[][] testCase005() {
        return new Object[][]{
                {"login",
                        "password",
                        "access_token",
                        "src/test/resources/files/original/test.jpg"},
                {"login",
                        "password",
                        "access_token",
                        "src/test/resources/files/original/test.png"}
        };
    }
}
