package simulation;

//main method, application run

import core.channel.Channel;
import core.channel.ChannelType;
import core.model.party.Farmer;
import core.model.party.Party;
import core.model.party.PartyFactory;
import core.model.party.Processor;
import core.model.product.Carrot;
import core.operation.Growing;
import core.transaction.Account;

public class Application {
    public static void main(String[] args) {
//        Simulation simulation = new Simulation();
        Carrot carrot = new Carrot(1L, 0.5, "Norwegian carrot");
        Party farmer = PartyFactory.createParty(PartyFactory.PartyType.FARMER, 1L, "Dan", "Samon");
        farmer.setProduct(carrot);
        farmer.setAccount(new Account(111L, 2000000D));

        Party processor1 = PartyFactory.createParty(PartyFactory.PartyType.PROCESSOR,2L, "Manuele", "Thaleman");
        Party processor2 = PartyFactory.createParty(PartyFactory.PartyType.PROCESSOR, 3L, "Nick", "Fisherman");

        processor1.setAccount(new Account(222L, 2000000D));
        processor2.setAccount(new Account(333L, 2000000D));

        Channel meatChannel = new Channel(ChannelType.MEAT);
        Growing growing = new Growing(75000D, ChannelType.MEAT, "Grow carrots", 30F, 15D);
        farmer.setOperation(growing);
        farmer.addChannel(meatChannel);
        meatChannel.subscribe(processor1, growing);
        meatChannel.subscribe(processor2, growing);

        farmer.publishEvent(farmer);
    }
}
