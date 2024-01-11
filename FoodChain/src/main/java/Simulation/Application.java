package Simulation;

//main method, application run

import javax.xml.parsers.ParserConfigurationException;


public class Application {
    public static void main(String[] args) throws ParserConfigurationException, IllegalAccessException {

        Simulation simulation = new Simulation();
        simulation.start();
    }
}
