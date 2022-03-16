package helpers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Routes {
    /**
     * @param viewName name of the view file
     * @return URL path to view file
     * @throws MalformedURLException throws if view file is not found
     */
    public static URL viewsRoute(String viewName) throws MalformedURLException {
        if (viewName == null || viewName.trim().length() == 0) {
            throw new NullPointerException(
                    "Cannot find viewName with empty string");
        }
        File file = new File("src\\main\\java\\views\\" + viewName);
        return file.toURI().toURL();
    }
}