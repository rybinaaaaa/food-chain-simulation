package core.report;

import core.party.Party;
import core.product.Product;
import core.transaction.Transaction;
import core.transaction.TransactionResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.Field;
import java.util.List;

public class SecurityReport extends Report{

    public SecurityReport(Product product) throws ParserConfigurationException, IllegalAccessException {
        super(product);
        this.fileName = "securityReport" + this.id.toString();
    }

    @Override
    public Document buildReport(Product product) throws ParserConfigurationException, IllegalAccessException {
        List<Transaction> transactions = product.getHistory();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document newDoc = builder.newDocument();

        Element rootElement = newDoc.createElement("TransactionReport");
        newDoc.appendChild(rootElement);

        for (Transaction t : transactions) {
            if (t.getTransactionResult() == TransactionResult.SUCCESS) continue;
            Element detailElement = newDoc.createElement("reportDetail");
            rootElement.appendChild(detailElement);

            Element transactionElement = newDoc.createElement("transactionDetail");

            for (Field field : Transaction.class.getDeclaredFields()) {

                field.setAccessible(true);
                Object value = field.get(t);
                if (value != null && (field.getType() == String.class || field.getType() == Float.class || field.getType() == Double.class || field.getType() == Integer.class)) {
                    transactionElement.setAttribute(field.getName(), value.toString());
                }
            }
            detailElement.appendChild(transactionElement);

            Element partyElement = newDoc.createElement("partyDetail");

            for (Field field : Party.class.getDeclaredFields()) {

                field.setAccessible(true);
                Object value = field.get(t.getParty());
                if (value != null && (field.getType() == String.class || field.getType() == Float.class || field.getType() == Double.class || field.getType() == Integer.class)) {
                    transactionElement.setAttribute(field.getName(), value.toString());
                }
            }
            detailElement.appendChild(partyElement);
        }

        return newDoc;
    }
}
