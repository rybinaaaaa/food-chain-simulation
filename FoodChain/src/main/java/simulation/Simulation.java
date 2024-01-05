package simulation;


import core.party.Party;
import core.product.Carrot;
import core.report.FoodChainReport;
import core.report.ReportProxy;
import core.report.ReportType;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

public class Simulation {

    ConfigLoader configLoader = new ConfigLoader();

    //successful configuration
    String path1 = "/config/config1.json";

    // configuration simulating double spending error
    String path2 = "/config/config2.json";


    //configuration simulating retroactive change attempt(pokus o zpětnou změnu)
    String path3 = "/config/config3.json";



    private List<Party> parties;

    private Carrot carrot;


    public void start() throws ParserConfigurationException, IllegalAccessException {
        configLoader.load(path1);
        this.parties = configLoader.getParties();
        this.carrot = configLoader.getProduct();
        Carrot ecoCarrot = (Carrot) carrot.clone();


        parties.get(0).processProduct(carrot);
        ReportProxy reportProxy = new ReportProxy(carrot, ReportType.PARTIES);
        reportProxy.downloadReport();
        FoodChainReport report1 = new FoodChainReport(carrot);
        report1.downloadReport();
        //other reports...

    }


}
