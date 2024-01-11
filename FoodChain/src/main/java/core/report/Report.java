package core.report;

import core.product.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import simulation.Simulation;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Abstract class representing a general report structure.
 */
public abstract class Report {
    protected Long id;

    private static final Logger logger = LogManager.getLogger(Report.class);

    protected static final AtomicLong counter = new AtomicLong();

    protected Document document;

    protected String fileName;

    /**
     * Constructs a Report instance for a given simulation.
     *
     * @param simulation The simulation to create a report for.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws IllegalAccessException if the access to the class is illegal.
     */
    public Report(Simulation simulation) throws ParserConfigurationException, IllegalAccessException {
        this.id = counter.getAndIncrement();
        this.document = buildReport(simulation.getProduct());
    }

    /**
     * Constructs a Report instance for a given product.
     *
     * @param product The product to create a report for.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws IllegalAccessException if the access to the class is illegal.
     */
    public Report(Product product) throws ParserConfigurationException, IllegalAccessException {
        this.id = counter.getAndIncrement();
        this.document = buildReport(product);
    }

    protected Report() {
        // Protected constructor for use in subclasses
    }

    /**
     * Saves the report to an XML file.
     *
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws IllegalAccessException if the access to the class is illegal.
     */
    public void downloadReport() throws ParserConfigurationException, IllegalAccessException {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(this.document);
            StreamResult result = new StreamResult(new File(this.fileName + ".xml"));

            transformer.transform(source, result);
            logger.info("The report has been successfully stored in " + this.fileName + ".xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abstract method to build a report based on the given product.
     *
     * @param product The product to build the report for.
     * @return A Document representing the report.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws IllegalAccessException if the access to the class is illegal.
     */
    abstract Document buildReport(Product product) throws ParserConfigurationException, IllegalAccessException;
}
