package sample.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 18:05
 * Description: No Description
 */
public class PropertiesUtil {

    public static String getPro(String fileName,String pro) throws IOException {
        InputStream in = ApiUrlManager.class.getClassLoader().getResource(fileName).openStream();
        Properties prop = new Properties();
        prop.load(in);
        String property = prop.getProperty(pro);
        in.close();
        return property;
    }

}
