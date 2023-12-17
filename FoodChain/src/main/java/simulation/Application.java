package simulation;

//main method, application run

import core.channel.Channel;
import core.channel.ChannelType;
import core.model.party.Farmer;
import core.model.party.Processor;
import core.model.product.Carrot;
import core.operation.Growing;

public class Application {
    public static void main(String[] args) {
//        Simulation simulation = new Simulation();
        Carrot carrot = new Carrot(1L, 0.5, "Norwegian carrot");
        Farmer farmer = new Farmer(1L, "Dan", "Samon");
        farmer.setProduct(carrot);

        Processor processor1 = new Processor(2L, "Manuele", "Thaleman");
        Processor processor2 = new Processor(3L, "Nick", "Fisherman");

        Channel meatChannel = new Channel(ChannelType.MEAT);
        Growing growing = new Growing(12000D, ChannelType.MEAT, "Grow carrots", 30F, 15D);
        farmer.setOperation(growing);
        farmer.addChannel(meatChannel);
        meatChannel.subscribe(processor1, growing);
        meatChannel.subscribe(processor2, growing);

        farmer.publishEvent(farmer);


    }
}
