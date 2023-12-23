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
import core.operation.Processing;
import core.operation.Selling;
import core.operation.Storing;
import core.transaction.Account;
import core.model.party.UserKey;


public class Application {
    public static void main(String[] args) {
        //create products
        Carrot carrot = new Carrot(1L, 0.5, "Norwegian carrot");
        //create the first party
        Party farmer = PartyFactory.createParty(PartyFactory.PartyType.FARMER, new UserKey(), "Dan", "Samon");
        farmer.setAccount(new Account(111L, 2000000D));
        //create the second party
        Party processor = PartyFactory.createParty(PartyFactory.PartyType.PROCESSOR,new UserKey(), "Manuele", "Thaleman");
        processor.setAccount(new Account(222L, 2000000D));
        //create the third party
        Party warehouse = PartyFactory.createParty(PartyFactory.PartyType.WAREHOUSE, new UserKey(), "John", "Holland");
        warehouse.setAccount(new Account(333L, 2000000D));
        //create the forth party
        Party retailer = PartyFactory.createParty(PartyFactory.PartyType.RETAILER, new UserKey(), "Rachel", "Johnson");
        retailer.setAccount(new Account(444L, 2000000D));
        //create the fifth party
        Party customer = PartyFactory.createParty(PartyFactory.PartyType.CUSTOMER, new UserKey(), "Monica", "Roberts");
        customer.setAccount(new Account(555L, 2000000D));
        //create the sixth part
        Party deliver = PartyFactory.createParty(PartyFactory.PartyType.DELIVER, new UserKey(), "Tom", "Swan");
        deliver.setAccount(new Account(666L, 2000000D));

        //create channels
        Channel prepareVeggiChannel = new Channel(ChannelType.VEGETABLES);
        Channel sellVeggiChannel = new Channel(ChannelType.EXCHANGE);


        //create operations
        Growing growing = new Growing(75000D, ChannelType.VEGETABLES, "Grow carrots", 30F, 15D);
        Processing processing = new Processing(50000D, ChannelType.VEGETABLES, "Process carrots", 15D, 2F);
        Storing storing = new Storing(25000D, ChannelType.VEGETABLES, "Store carrots", 5D, 5F, 96D);
        Storing delivering = new Storing(25000D, ChannelType.VEGETABLES, "Deliver carrots", 5D, 5F, 96D);
        Selling selling = new Selling(10000D, ChannelType.VEGETABLES, "Sell carrots");

        //set operations
        farmer.setOperation(growing);
        processor.setOperation(processing);
        warehouse.setOperation(storing);
        deliver.setOperation(delivering);
        retailer.setOperation(selling);

        //add channels to parties
        farmer.addChannel(prepareVeggiChannel);
        processor.addChannel(prepareVeggiChannel);
        warehouse.addChannel(prepareVeggiChannel);
        deliver.addChannel(sellVeggiChannel);
        retailer.addChannel(sellVeggiChannel);

        //add channel subscribers
        prepareVeggiChannel.subscribe(processor, growing);
        prepareVeggiChannel.subscribe(warehouse, processing);
        prepareVeggiChannel.subscribe(deliver, storing);
        sellVeggiChannel.subscribe(retailer, delivering);
        sellVeggiChannel.subscribe(customer, selling);


        //run the process of exchange
        farmer.processProduct(carrot);




    }
}
