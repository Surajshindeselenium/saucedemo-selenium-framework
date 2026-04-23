package utilities;

import base.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.File;

public class ScreenshotUtils {

    public static String takeScreenshot(String name) {
        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        File src = ts.getScreenshotAs(OutputType.FILE);
        String path = "reports/screenshots/" + name + ".png";
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}