package core.report;

import core.product.Product;
import org.w3c.dom.Document;
import simulation.Simulation;

import javax.xml.parsers.ParserConfigurationException;

public class ReportProxy extends Report {
    private Report report;
    private Product product;
    private ReportType reportType;

    public ReportProxy(Simulation simulation, ReportType reportType) {
        this.product = simulation.getProduct();
        this.reportType = reportType;
    }

    @Override
    protected Document buildReport(Product product) throws ParserConfigurationException, IllegalAccessException {
        if (report == null) {
            switch (reportType) {
                case PARTIES -> this.report = new PartiesReport(product);
                case SECURITY -> this.report = new SecurityReport(product);
                case FOOD_CHAIN -> this.report = new FoodChainReport(product);
                case TRANSACTION -> this.report = new TransactionReport(product);
            }
        }
        return report.buildReport(product);
    }

    public void downloadReport() throws ParserConfigurationException, IllegalAccessException {
        this.document = this.buildReport(this.product);
        report.downloadReport();
    }
}