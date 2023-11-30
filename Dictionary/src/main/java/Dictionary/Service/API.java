package Dictionary.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

interface API {

  public static URL createURL(String linkURL) {
    try {
      return new URL(linkURL);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static HttpURLConnection openConnection(URL url) {
    try {
      return (HttpURLConnection) url.openConnection();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
