package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {



    /*
     *configReader class is responsible for reading values from the config.properties file
     *
     * Design approach:--
     * -- static block ensures file loads only once (performance optimized)
     * --centralized configuration management
     * --Avoid hardcoding frameworks
     *
     *
     */
    private static Properties properties;

    //    static block executes only once when class is loaded
    static{



        try{

            properties = new Properties();
            String filePath = System.getProperty("user.dir")+"/src/main/resources/config.properties";
            File file = new File(filePath);

            FileInputStream fis = new FileInputStream(file);

            properties.load(fis);


        }catch (IOException e)
        {
            throw  new RuntimeException("Failed to load the properties file ", e);
        }


    }


    /*
     *Fetch value based on key from properties file
     *@param key property key
     * @return value of the key
     * */
    public static String get(String key)
    {
       return properties.getProperty(key);
    }



}
