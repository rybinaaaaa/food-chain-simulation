package core.report;

import core.product.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Report {
    protected Long id;

    private static final Logger logger = LogManager.getLogger(Report.class);

    protected static final AtomicLong counter = new AtomicLong();

    protected Document document;

    protected String fileName;

    public Report(Product product) throws ParserConfigurationException, IllegalAccessException {
        this.id = counter.getAndIncrement();
        this.document = buildReport(product);
    }

    protected Report() {
    }

    public void downloadReport() throws ParserConfigurationException, IllegalAccessException {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(this.document);
            StreamResult result = new StreamResult(new File(this.fileName + ".xml"));

            transformer.transform(source, result);
            logger.info("The report has been successfully stored in  " + this.fileName + ".xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract Document buildReport(Product product) throws ParserConfigurationException, IllegalAccessException;
}
