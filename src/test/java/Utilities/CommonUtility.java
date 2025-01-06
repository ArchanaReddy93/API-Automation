package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonUtility {

public static Logger Log=LogManager.getLogger(CommonUtility.class);

    public static String getBaseURI() throws IOException {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\LENOVO\\IdeaProjects\\BBC_RMS_APIAutomation\\src\\main\\resources\\config.properties");
            prop.load(fis);
            String BaseURI = prop.getProperty("baseURL");
            Log.info("Base URI: {}{}", BaseURI);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);

        }
        return prop.getProperty("baseURL");
    }
    }

