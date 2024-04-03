package ui;

import model.Event;
import model.EventLog;

public class Main {
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Program is exiting. Printing event log:");
            for (Event event : EventLog.getInstance()) {
                System.out.println(event);
            }
        }));


        //This is used for phase 0/1/2
//        new WardrobeApp();

        // The Wardrobe App with GUI
        new WardrobeGui();


    }
}
