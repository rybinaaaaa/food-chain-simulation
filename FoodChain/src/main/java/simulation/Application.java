package simulation;

//main method, application run

import core.channel.Channel;
import core.channel.ChannelType;
import core.model.party.Farmer;
import core.model.party.Processor;
import core.model.product.Carrot;
import core.model.product.ProductPrototype;

import java.util.ArrayList;

public class Application {
    public static void main(String[] args)  {
//        Simulation simulation = new Simulation();
        Carrot carrot = new Carrot(1L, 0.5, "Norwegian carrot");
        Farmer farmer = new Farmer(1L, "Dan", "Samon");
        farmer.setProduct(carrot);
        Processor processor1 = new Processor(2L, "Manuele", "Thaleman");
        Processor processor2 = new Processor(3L, "Nick", "Fisherman");
        Channel meatChannel = new Channel(ChannelType.MEAT);
        meatChannel.subscribe(processor1);
        meatChannel.subscribe(processor2);



    }
}
