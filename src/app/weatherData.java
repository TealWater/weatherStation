package app;
//how we will send data to weather API to get hourly updates

import java.net.URL;

public class weatherData {
    private String state, city, zip; //How we narrow down your location
   // URL urlZip = new URL("pro.openweathermap.org/data/2.5/forecast/hourly?zip=94040,us");

/*------------------ Setters ------------------*/
//Sets the user's state
public void setState(){
    state = System.console().readLine().toString();
    System.out.println("The state is " + state);
 }

//sets the user's city
 public void setCity(){
     city = System.console().readLine().toString();
     System.out.println("The city is " + city);
 }

 //set the user's zip
 public void setZip(){
     zip = System.console().readLine().toString();
     System.out.println("The zip code is " + zip);
 }
/*------------------ Setters ------------------*/


/*------------------ Getters ------------------*/
//Returns the State the user inputted
public String getState() {
    return state;
}

//Returns the City the user inputted
public String getCity(){
    return city;
}

//Reutrns the zip code the user inputted
public String getZip(){
    return zip;
}
/*------------------ Getters ------------------*/

/* How the fuck are we going to deliver this data to the open weather api? */
 
 //send data to openWeather.com API in JSON formant to get a resopnse 
 public void sendData(String state, String city){

 }
 
 //send data to openWeather.com API in JSON formant to get a resopnse 
 public void sendData(String zip){

 }

}