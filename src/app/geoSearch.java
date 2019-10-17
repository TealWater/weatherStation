package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

// The purpose of this file is to open a URL to recieve JSON response for corrdinates

public class geoSearch {
    public static void main(String[] args) {
        
    

        try {
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyCPAYQsQaQwH4rD55sBNBpJtSP1r6jJSqk"); //url we want to talk to
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // opens connection and casts it to type HTTPURLConnection (makes it more secure?)
        conn.setConnectTimeout(10000); //gives connection 10 seconds to timeout
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF8"); //identifies that the data being sent is JSON
        conn.setDoOutput(true); //allows us to send data
        conn.setDoInput(true); //allows us to read a response
        conn.setRequestMethod("POST");
        System.out.println("Here1");


        /*--- Write Output to the server---*/
        OutputStream out = conn.getOutputStream(); //the vehicle we use to send data to API
            String json = " ";
            out.write(json.getBytes("UTF-8")); // encodes JSON request in UTF-8 characters
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
       // System.out.println(obj + " ");

        JSONArray location = obj.getJSONArray("results");
        
        String lat = location.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
        String lng = location.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString(); 

        System.out.println("Latitiude is: "+ lat + "\nLongitude is: " + lng);
        /*--- End of JSON API Response Conversion ---*/

        }//end of try
        catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }

    }//end of main

}//end of geoSearch class