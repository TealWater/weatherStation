package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

//import org.apache.poi.util.IOUtils;



public class test {
    //public static void main(String[] args) {
       
    //}

    public void web() {
        String json = "zip=10603, us";
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?zip=10603,us&appid=5c5cd74d13a8f68d2cab041ee826b8e6"); //url we want to talk to
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // opens connection and casts it to type HTTPURLConnection (makes it more secure?)
            conn.setConnectTimeout(10000); //gives connection 10 seconds to timeout
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF8"); //identifies that the data being sent is JSON
            conn.setDoOutput(true); //allows us to send data
            conn.setDoInput(true); //allows us to read a response
            conn.setRequestMethod("POST");
            System.out.println("Here1");


            /*--- Write Output to the server---*/
            OutputStream out = conn.getOutputStream(); //the vehicle we use to send data to API
            out.write(json.getBytes("UTF-8") ); //encodes JSON request in UTF-8 characters
            out.flush(); //Always flush after doing your business, no one likes smelling or looking at it. (All data is sent to the server)
            out.close(); // close the portal, it helps keep things neat
            System.out.println("Here2");
           /*--- End of writing output to the server ---*/

            /*--- Read Output from the server---*/
            /*InputStream in = new BufferedInputStream( conn.getInputStream() ); //Reads incoming bytes
            System.out.println("Response Code: " +conn.getResponseCode() + " "); //Displays the response code from server 
            //String result = IOUtils.toString();*/
            

            BufferedReader in = new BufferedReader(new InputStreamReader( conn.getInputStream() ) );  //read incoming bytes
            StringBuffer response = new StringBuffer();  //helps with conversion from Buffered Reader
            String result;
            System.out.println("Response Code: " +conn.getResponseCode() + " "); //Displays the response code from server 
            System.out.println("Here3");
            
            while( (result = in.readLine() ) !=null ){
                response.append(result);
            }
            
            System.out.println(result);
            in.close();
            //conn.disconnect();
            /*--- End of Read output from the server---*/

            /*--- JSON API Response Conversion ---*/
            JSONObject obj = new JSONObject(response.toString());
            System.out.println(obj + " ");
            /*--- End of JSON API Response Conversion ---*/
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



}


}