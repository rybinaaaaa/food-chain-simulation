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
import core.product.AmountUnit;
import core.product.Carrot;
import core.report.*;
import core.transaction.Account;

import javax.xml.parsers.ParserConfigurationException;


public class Application {
    public static void main(String[] args) throws ParserConfigurationException, IllegalAccessException {

        Simulation simulation = new Simulation();
        simulation.start();
    }
}
