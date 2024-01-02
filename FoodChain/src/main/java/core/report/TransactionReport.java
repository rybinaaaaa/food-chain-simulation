package core.report;

import core.product.Product;
import core.transaction.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.Field;
import java.util.List;

public class TransactionReport extends Report{

    public TransactionReport(Product product) throws ParserConfigurationException, IllegalAccessException {
        super(product);
        this.fileName = "transactionsReport" + this.id.toString();
    }

    @Override
    protected Document buildReport(Product product) throws ParserConfigurationException, IllegalAccessException {
        List<Transaction> transactions = product.getHistory();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document newDoc = builder.newDocument();

        Element rootElement = newDoc.createElement("TransactionReport");
        newDoc.appendChild(rootElement);

        for (Transaction t : transactions) {
            Element detailElement = newDoc.createElement("reportDetail");
            rootElement.appendChild(detailElement);

            for (Field field : Transaction.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(t);
                if (value != null && (field.getType() == String.class || field.getType() == Float.class || field.getType() == Double.class || field.getType() == Integer.class)) {
                    detailElement.setAttribute(field.getName(), value.toString());
                }
            }
        }

        return newDoc;
    }
}
