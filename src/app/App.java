package app;

public class App {
    public static void main(String[] args) throws Exception {
        weatherData location1 = new weatherData();
        String temp; //temporary variable
        test trial = new test();

        System.out.println("Welcome to the weather terminal. \n To help my deliver the forcast for your area, I need a few things from you.");

        //Ask for city and state
        System.out.println("May I have your state and city? | Answer 'yes'/ 'y' or 'no' / 'n' ");
        temp = System.console().readLine().toString(); // Store the result of the answer from the user
        if(temp.equals("yes") || temp.equals("y") ){
            //get info
            System.out.println("Hello1");
            System.out.println("State: " );
            location1.setState();
            System.out.println("City: ");
            location1.setCity();
            trial.web();
        }else{
            //ask for zip
            System.out.println("Please enter your zip code: ");
            location1.setZip();
            trial.web();
        }
    }
}