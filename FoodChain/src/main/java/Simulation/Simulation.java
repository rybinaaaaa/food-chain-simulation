package Simulation;


import Core.Party.Party;
import Core.Product.Carrot;
import Core.Product.Product;
import Core.Report.FoodChainReport;
import Core.Report.ReportProxy;
import Core.Report.ReportType;

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

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void start() throws ParserConfigurationException, IllegalAccessException {
        configLoader.load(path1);
        this.parties = configLoader.getParties();
        this.product = configLoader.getProduct();
        Carrot ecoCarrot = (Carrot) product.clone();


        parties.get(0).processProduct(product);
        ReportProxy reportProxy = new ReportProxy(this, ReportType.PARTIES);
        reportProxy.downloadReport();
        FoodChainReport report1 = new FoodChainReport(product);
        report1.downloadReport();
        //other reports...
    }
}
