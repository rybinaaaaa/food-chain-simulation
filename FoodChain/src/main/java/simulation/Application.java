package simulation;

//main method, application run

import core.certificate.Certificate;
import core.channel.Channel;
import core.channel.ChannelType;
import core.operation.*;
import core.party.Farmer;
import core.party.Party;
import core.party.PartyFactory;
import core.party.UserKey;
import core.product.Carrot;
import core.report.*;
import core.transaction.Account;

import javax.xml.parsers.ParserConfigurationException;


public class Application {
    public static void main(String[] args) throws ParserConfigurationException, IllegalAccessException {
        //create products
        Carrot carrot = new Carrot(1L, 0.5, "Norwegian carrot");
        Carrot ecoCarrot = (Carrot) carrot.clone();
        //create the first party
        Party farmer = PartyFactory.createParty(PartyFactory.PartyType.FARMER, new UserKey(), "Dan", "Samon");
        farmer.setAccount(new Account(111L, 2000000D));
        //create the second parties
        Party processor1 = PartyFactory.createParty(PartyFactory.PartyType.PROCESSOR, new UserKey(), "Manuele", "Thaleman");
        processor1.setAccount(new Account(222L, 2000000D));

        Party processor2 = PartyFactory.createParty(PartyFactory.PartyType.PROCESSOR, new UserKey(), "Honza", "Honzen");
        processor2.setAccount(new Account(2323L, 2000000D));
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
        Selling selling = new Selling(10000D, ChannelType.VEGETABLES, "Sell carrots", 10F);

        //set operations
        farmer.setOperation(growing);
        processor1.setOperation(processing);
        processor2.setOperation(processing);
        warehouse.setOperation(storing);
        deliver.setOperation(delivering);
        retailer.setOperation(selling);

        //add channels to parties
        farmer.addChannel(prepareVeggiChannel);
        processor1.addChannel(prepareVeggiChannel);
        processor2.addChannel(prepareVeggiChannel);
        warehouse.addChannel(prepareVeggiChannel);
        deliver.addChannel(sellVeggiChannel);
        retailer.addChannel(sellVeggiChannel);

        //add channel subscribers
        prepareVeggiChannel.subscribe(processor1, growing);
        prepareVeggiChannel.subscribe(processor2, growing);

        //add certificate for the second party
        processor1.addCertificates(new Certificate(334L, "ffsjldf", Processing.class, processor1, carrot.getId(), true));

        prepareVeggiChannel.subscribe(warehouse, processing);
        prepareVeggiChannel.subscribe(deliver, storing);
        sellVeggiChannel.subscribe(retailer, delivering);
        sellVeggiChannel.subscribe(customer, selling);


        //run the process of exchange
//        farmer.processProduct(carrot);
        farmer.addCertificates(new Certificate(2L, "203EhjfdS", Growing.class, farmer, carrot.getId(), true));
        warehouse.addCertificates(new Certificate(324L, "ff99ldf", Storing.class, warehouse, carrot.getId(), true));
        deliver.addCertificates(new Certificate(327L, "f00lldf", Delivering.class, deliver, carrot.getId(), true));
        retailer.addCertificates(new Certificate(328L, "f11ll8f", Selling.class, retailer, carrot.getId(), true));

        processor1.setRetroactiveChange(true);
        farmer.processProduct(carrot);

        PartiesReport report = new PartiesReport(carrot);
        report.downloadReport();

        ReportProxy reportProxy = new ReportProxy(carrot, ReportType.PARTIES);
        reportProxy.downloadReport();
//        FoodChainReport report1 = new FoodChainReport();
//        report1.buildReport(carrot);
    }
}
