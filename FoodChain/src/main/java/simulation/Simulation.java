package simulation;

// creates all necessary objects

public class Simulation {

    //simple draft to try to run the project
    private void init(){

    }
    private void start() {
        for (int time = 0; time < 1000; time++) {


            // wait for a while
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
