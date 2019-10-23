package app;
//how we will send data to weather API to get hourly updates

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;

import org.json.JSONArray;
import org.json.JSONObject;

/* TODO
* Consider finding a website with an API that allows you to enter in the 
* city and state and returns the latitude and longitude coordinates.
*       - You can take those coordinates and plug them into the openweather's
*            API URL.
*/

public class weatherData {
    private String country, city, state, zip; // How we narrow down your location
    // URL urlZip = new
    // URL("pro.openweathermap.org/data/2.5/forecast/hourly?zip=94040,us");

    /*------------------ Setters ------------------*/
    // Sets the user's country
    public void setCountry() {
        country = System.console().readLine().toString();
        System.out.println("The Country is " + country);
    }

    // sets the user's city
    public void setCity() {
        city = System.console().readLine().toString();
        System.out.println("The city is " + city);
    }

    // set the user's zip
    public void setZip() {
        zip = System.console().readLine().toString();
        System.out.println("The zip code is " + zip);
    }

    // set the user's state
    public void setState() {
        state = System.console().readLine().toString();
        System.out.println("The state is " + state);
    }
    /*------------------ Setters ------------------*/

    /*------------------ Getters ------------------*/
    // Returns the Country the user inputted
    public String getCountry() {
        return country;
    }

    // Returns the City the user inputted
    public String getCity() {
        return city;
    }

    // Reutrns the zip code the user inputted
    public String getZip() {
        return zip;
    }

    // Reutrns the state the user inputted
    public String getState() {
        return state;
    }
    /*------------------ Getters ------------------*/

    /* How the fuck are we going to deliver this data to the open weather api? */

    // send data to openWeather.com API in JSON formant to get a resopnse
    public void sendData(String city, String state) {
        web(city, state);
    }

    // send data to openWeather.com API in JSON formant to get a resopnse
    public void sendData(String zip) {
        web(zip);

    }

    /*--- Start of Parsing ---*/

    public void web(String zip) {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + zip
                    + ",us&appid=2fde14716377aa681d3c55151b6dcc59"); // url we want to talk to
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // opens connection and casts it to type
                                                                               // HTTPURLConnection (makes it more
                                                                               // secure?)
            conn.setConnectTimeout(10000); // gives connection 10 seconds to timeout
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF8"); // identifies that the data being
                                                                                       // sent is JSON
            conn.setDoOutput(true); // allows us to send data
            conn.setDoInput(true); // allows us to read a response
            conn.setRequestMethod("POST");
            System.out.println("Here1");

            /*--- Write Output to the server---*/
            OutputStream out = conn.getOutputStream(); // the vehicle we use to send data to API
            String json = " ";
            out.write(json.getBytes("UTF-8")); // encodes JSON request in UTF-8 characters
            out.flush(); // Always flush after doing your business, no one likes smelling or looking at
                         // it. (All data is sent to the server)
            out.close(); // close the portal, it helps keep things neat
            System.out.println("Here2");
            /*--- End of writing output to the server ---*/

            /*--- Read Output from the server---*/
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); // read incoming bytes
            StringBuffer response = new StringBuffer(); // helps with conversion from Buffered Reader
            String result;
            System.out.println("Response Code: " + conn.getResponseCode() + " "); // Displays the response code from
                                                                                  // server
            System.out.println("Here3");

            while ((result = in.readLine()) != null) {
                response.append(result);
            }
            in.close(); // close the inputstream reader
            conn.disconnect();
            /*--- End of Read output from the server---*/

            /*--- JSON API Response Conversion ---*/
            JSONObject obj = new JSONObject(response.toString());
            // System.out.println(obj + " ");
            /*--- End of JSON API Response Conversion ---*/

            /*--- Start: Pasrse the JSON Response ---*/

            // Stores the Temperature Summary Array Object
            JSONObject tempSum = obj.getJSONObject("main");

            // Stores weather condition summary
            JSONArray weather = obj.getJSONArray("weather");
            // JSONObject condition = weather.getJSONObject("description");

            // This provides the atmospheric temp summary.
            // System.out.println("Summary: "+ tempSum );

            // one of the key: value pairs of the array object
            // System.out.println(tempSum.get("temp") + " Kelvin\n");

            /*--- Temperature report ---*/
            // Temp High
            System.out.print("Temp High: ");
            tempConversion('f', tempSum.get("temp_max").toString());

            // Current temp
            System.out.print("Temp: ");
            tempConversion('f', tempSum.get("temp").toString());

            // Temp Low
            System.out.print("Temp Low: ");
            tempConversion('f', tempSum.get("temp_min").toString());
            /*--- End of temperature report ---*/

            /*--- Atmospheric Conditions ---*/
            // Pressure
            System.out.print("The current pressure is: " + tempSum.get("pressure") + " hpa\n");

            // Humidity
            System.out.print("The current pressure is: " + tempSum.get("humidity") + "%\n");
            // Condition
            System.out.print("The current condition is: " + weather.getJSONObject(0).get("main") + "\n"); // try looking
                                                                                                          // at the 1st
                                                                                                          // item
            // in the array and then parse for "condition"

            /*--- End of atmospheric conditions ---*/

            /*--- End: Pasrse the JSON Response ---*/

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }// end of web()
    //uses the lat and longitiude coordinates to give an accurate forecast
    public void web(String city, String state) {
        //System.out.println("getting the data");
        try {
            String send = geoEncoder(city, state);
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?"+ send +"&appid=2fde14716377aa681d3c55151b6dcc59"); // url we want to talk to
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // opens connection and casts it to type
                                                                               // HTTPURLConnection (makes it more
                                                                               // secure?)
            conn.setConnectTimeout(10000); // gives connection 10 seconds to timeout
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF8"); // identifies that the data being
                                                                                       // sent is JSON
            conn.setDoOutput(true); // allows us to send data
            conn.setDoInput(true); // allows us to read a response
            conn.setRequestMethod("POST");
            System.out.println("Here1");

            /*--- Write Output to the server---*/
            OutputStream out = conn.getOutputStream(); // the vehicle we use to send data to API
            String json = "";
            out.write(json.getBytes("UTF-8")); // encodes JSON request in UTF-8 characters
            out.flush(); // Always flush after doing your business, no one likes smelling or looking at
                         // it. (All data is sent to the server)
            out.close(); // close the portal, it helps keep things neat
            System.out.println("Here2");
            /*--- End of writing output to the server ---*/

            /*--- Read Output from the server---*/
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); // read incoming bytes
            StringBuffer response = new StringBuffer(); // helps with conversion from Buffered Reader
            String result;
            System.out.println("Response Code: " + conn.getResponseCode() + " "); // Displays the response code from
                                                                                  // server
            System.out.println("Here3");

            while ((result = in.readLine()) != null) {
                response.append(result);
            }
            in.close(); // close the inputstream reader
            conn.disconnect();
            /*--- End of Read output from the server---*/

            /*--- JSON API Response Conversion ---*/
            JSONObject obj = new JSONObject(response.toString());
            // System.out.println(obj + " ");
            /*--- End of JSON API Response Conversion ---*/

            /*--- Start: Pasrse the JSON Response ---*/

            // Stores the Temperature Summary Array Object
            JSONObject tempSum = obj.getJSONObject("main");

            // Stores weather condition summary
            JSONArray weather = obj.getJSONArray("weather");
            // JSONObject condition = weather.getJSONObject("description");

            // This provides the atmospheric temp summary.
            // System.out.println("Summary: "+ tempSum );

            // one of the key: value pairs of the array object
            // System.out.println(tempSum.get("temp") + " Kelvin\n");

            /*--- Temperature report ---*/
            // Temp High
            System.out.print("Temp High: ");
            tempConversion('f', tempSum.get("temp_max").toString());

            // Current temp
            System.out.print("Temp: ");
            tempConversion('f', tempSum.get("temp").toString());

            // Temp Low
            System.out.print("Temp Low: ");
            tempConversion('f', tempSum.get("temp_min").toString());
            /*--- End of temperature report ---*/

            /*--- Atmospheric Conditions ---*/
            // Pressure
            System.out.print("The current pressure is: " + tempSum.get("pressure") + " hpa\n");

            // Humidity
            System.out.print("The current pressure is: " + tempSum.get("humidity") + "%\n");
            // Condition
            System.out.print("The current condition is: " + weather.getJSONObject(0).get("main") + "\n"); // try looking
                                                                                                          // at the 1st
                                                                                                          // item
            // in the array and then parse for "condition"

            /*--- End of atmospheric conditions ---*/

            /*--- End: Pasrse the JSON Response ---*/

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }// end of web()

    /**
     * 
     * @param L   the char that allows for the temperature to be in Fahrenheight or
     *            Celsius
     * @param num The kelvin number that you want to convert
     */
    public void tempConversion(char L, String num) throws Exception {
        Double temp = Double.valueOf(num);
        Double result = 0.0;
        if (L == 'f' || L == 'F') {
            // provide fahrenheight
            result = (((temp - 273) * 9 / 5) + 32);
            System.out.println("*F: " + (int) Math.round(result));
        } else if (L == 'c' || L == 'C') {
            // provide celsius
            result = temp - 273.15;
            System.out.println("*C: " + (int) Math.round(result));
        } else {
            throw new InvalidParameterException("the char entered is not a valid parameter."
                    + " Please enter in 'F' or 'f' for Fahrenheight. For Celsius please enter 'C' or 'c'.");
        }

    }// end of tempConversion()

    public String urlEncoder(String city, String state) {
        //System.out.println("encoding url!");
        city += ", ";
        try {
            return (URLEncoder.encode(city, StandardCharsets.UTF_8.toString())
                    + URLEncoder.encode(state, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }// end of urlEncoder()

    public String geoEncoder(String city, String state) {
        //System.out.println("getting coordinates!");
        String query = urlEncoder(city, state);
        //System.out.println("Query: " + query);
        String send = "";
        try {
            URL url = new URL(
                    "https://maps.googleapis.com/maps/api/geocode/json?address="+query+"&key=AIzaSyAWVtgPG_3JJXKPQWq7o5MdSp05q70YKdY"); // url
                                                                                                                                                                           // we
                                                                                                                                                                           // want
                                                                                                                                                                           // to
                                                                                                                                                                           // talk
                                                                                                                                                                           // to
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // opens connection and casts it to type
                                                                               // HTTPURLConnection (makes it more
                                                                               // secure?)
            conn.setConnectTimeout(10000); // gives connection 10 seconds to timeout
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF8"); // identifies that the data being
                                                                                       // sent is JSON
            conn.setDoOutput(true); // allows us to send data
            conn.setDoInput(true); // allows us to read a response
            conn.setRequestMethod("POST");
            //System.out.println("Here1");

            /*--- Write Output to the server---*/
            OutputStream out = conn.getOutputStream(); // the vehicle we use to send data to API
            String json = " ";
            out.write(json.getBytes("UTF-8")); // encodes JSON request in UTF-8 characters
            out.flush(); // Always flush after doing your business, no one likes smelling or looking at
                         // it. (All data is sent to the server)
            out.close(); // close the portal, it helps keep things neat
           // System.out.println("Here2");
            /*--- End of writing output to the server ---*/

            /*--- Read Output from the server---*/
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); // read incoming bytes
            StringBuffer response = new StringBuffer(); // helps with conversion from Buffered Reader
            String result;
            System.out.println("Response Code: " + conn.getResponseCode() + " "); // Displays the response code from
                                                                                  // server
            //System.out.println("Here3");

            while ((result = in.readLine()) != null) {
                response.append(result);
            }
            in.close(); // close the inputstream reader
            conn.disconnect();
            /*--- End of Read output from the server---*/

            /*--- JSON API Response Conversion ---*/
            JSONObject obj = new JSONObject(response.toString());
           // System.out.println("Hi");
            // System.out.println(obj + " ");

            JSONArray location = obj.getJSONArray("results");

            String lat = location.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat")
                    .toString();
            String lng = location.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng")
                    .toString();

            send = "lat=" + lat + "&lon=" + lng+ "";//this the the lat and long format for the open weather API

            System.out.println("Latitiude is: " + lat + "\nLongitude is: " + lng);
            /*--- End of JSON API Response Conversion ---*/
           // System.out.println("Done!");
            return send;

        } // end of try
        catch (Exception e) {
            //System.out.println("fail!");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return send;

    }// end of geoEncoder()

}// end of class