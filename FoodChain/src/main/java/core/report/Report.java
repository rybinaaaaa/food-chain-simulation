package core.report;

import core.channel.Channel;
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

public abstract class Report {
    private Long id;
    private static final Logger logger = LogManager.getLogger(Report.class);

    public void downloadReport(Document newDoc, String fileName) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(newDoc);
            StreamResult result = new StreamResult(new File(fileName + ".xml"));

            transformer.transform(source, result);
            logger.info("The report has been successfully stored in  " + fileName + ".xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract void buildReport(Product product) throws ParserConfigurationException, IllegalAccessException;
}
