package config;

import java.io.*;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop;

    static {
        try {
            prop = new Properties();
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                prop.load(input);
            } else {
                throw new RuntimeException("config.properties not found in classpath");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}