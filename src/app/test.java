package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

import org.json.JSONArray;
import org.json.JSONObject;

/* TODO
* Create fields for [Temp High, Temp Low, Current Temp], *done
* Pressure, Conditions(Cloudy, partly cloudy, overcast, rain, snow).
* 
* Append zip code, state, town to URL.
*/


public class test {

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
            BufferedReader in = new BufferedReader(new InputStreamReader( conn.getInputStream() ) );  //read incoming bytes
            StringBuffer response = new StringBuffer();  //helps with conversion from Buffered Reader
            String result;
            System.out.println("Response Code: " +conn.getResponseCode() + " "); //Displays the response code from server 
            System.out.println("Here3");
            
            while( (result = in.readLine() ) !=null ){
                response.append(result);
            }
            in.close(); //close the inputstream reader
            conn.disconnect();
            /*--- End of Read output from the server---*/

            /*--- JSON API Response Conversion ---*/
            JSONObject obj = new JSONObject(response.toString());
            //System.out.println(obj + " ");
            /*--- End of JSON API Response Conversion ---*/

            /*--- Start: Pasrse the JSON Response ---*/

            //Stores the Temperature Summary Array Object
            JSONObject tempSum = obj.getJSONObject("main");

            //Stores weather condition summary
            JSONArray weather = obj.getJSONArray("weather");
            //JSONObject condition = weather.getJSONObject("description");

            //This provides the atmospheric temp summary.
            //System.out.println("Summary: "+ tempSum );
            
            //one of the key: value pairs of the array object
            //System.out.println(tempSum.get("temp")  + " Kelvin\n");
            
            /*--- Temperature report ---*/
            //Temp High
            System.out.print("Temp High: " );
            tempConversion('f', tempSum.get("temp_max").toString() );

            //Current temp
            System.out.print("Temp: ");
            tempConversion('f', tempSum.get("temp").toString() );
          
            //Temp Low
            System.out.print("Temp Low: ");
            tempConversion('f', tempSum.get("temp_min").toString() );
           /*--- End of temperature report ---*/

            /*--- Atmospheric Conditions ---*/
            //Pressure
            System.out.print("The current pressure is: " +
            tempSum.get("pressure") + " hpa\n" );
            
            //Humidity
            System.out.print("The current pressure is: " +
            tempSum.get("humidity") + "%\n" );
            //Condition
            System.out.print("The current pressure is: " +
            weather.length().toString()+ "\n" ); //try looking at the 1st item 
                                                 //in the array and then parse for "condition"

            /*--- End of atmospheric conditions ---*/

            /*--- End: Pasrse the JSON Response ---*/
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }
    /**
    * 
    * @param L the char that allows for the temperature to be in Fahrenheight or Celsius
    * @param num The kelvin number that you want to convert
    */
    public void tempConversion(char L, String num) throws Exception{
        Double temp = Double.valueOf(num);
        Double result = 0.0;
        if (L == 'f' || L == 'F'){
            //provide fahrenheight
            result = (((temp - 273) * 9/5) + 32);
            System.out.println("*F: " + (int)Math.round(result));
        }
        else if(L == 'c' || L == 'C'){
            //provide celsius
            result = temp - 273.15;
            System.out.println("*C: " + (int)Math.round(result));
        }else{
            throw new InvalidParameterException("the char entered is not a valid parameter."+ 
                " Please enter in 'F' or 'f' for Fahrenheight. For Celsius please enter 'C' or 'c'.");
        }
    
    }

    
}

