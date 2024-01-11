package core.report;

import core.product.Product;
import org.w3c.dom.Document;
import simulation.Simulation;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Proxy class for generating different types of reports.
 */
public class ReportProxy extends Report {
    private Report report;
    private final Product product;
    private final ReportType reportType;

    /**
     * Constructs a ReportProxy for a specific simulation and report type.
     *
     * @param simulation The simulation data to generate the report from.
     * @param reportType The type of the report to be generated.
     */
    public ReportProxy(Simulation simulation, ReportType reportType) {
        this.product = simulation.getProduct();
        this.reportType = reportType;
    }

    /**
     * Builds the report based on the product.
     *
     * @param product The product to generate the report for.
     * @return A Document object representing the generated report.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be created.
     * @throws IllegalAccessException If the access to the class is illegal.
     */
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

    /**
     * Downloads the generated report.
     *
     * @throws ParserConfigurationException If a DocumentBuilder cannot be created.
     * @throws IllegalAccessException If the access to the class is illegal.
     */
    public void downloadReport() throws ParserConfigurationException, IllegalAccessException {
        this.document = this.buildReport(this.product);
        report.downloadReport();
    }
}