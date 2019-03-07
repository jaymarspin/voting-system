
package votingsystem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class server {
    
    
    
    public HttpURLConnection con(String url) throws MalformedURLException, IOException{
        URL stash = new URL(url);
        HttpURLConnection http = (HttpURLConnection) stash.openConnection();
        
        
         http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
           
        
        return http;
        
    }
}
