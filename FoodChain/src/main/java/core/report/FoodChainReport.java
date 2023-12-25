package core.report;

import core.channel.Channel;
import core.operation.Operation;
import core.party.Party;
import core.product.Product;
import core.transaction.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class FoodChainReport extends Report {

    @Override
    public void buildReport(Product product) throws ParserConfigurationException, IllegalAccessException {
        List<Transaction> transactions = product.getHistory();
        Double price = product.getPrice();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document newDoc = builder.newDocument();

        Element rootElement = newDoc.createElement("PartiesReport");
        newDoc.appendChild(rootElement);

        for (Transaction t : transactions) {
            Element detailElement = newDoc.createElement("reportDetail");

            Element operationDetailElement = newDoc.createElement("operationDetail");
            Element partyDetailElement = newDoc.createElement("partyDetail");
            Element productDetailElement = newDoc.createElement("productDetail");


            rootElement.appendChild(detailElement);
            detailElement.appendChild(operationDetailElement);
            detailElement.appendChild(partyDetailElement);
            detailElement.appendChild(productDetailElement);


            Operation o = t.getOperation();

            operationDetailElement.setAttribute("OperationType", o.getName());
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                operationDetailElement.setAttribute(field.getName(), field.get(o).toString());
            }

            Party p = t.getParty();
            partyDetailElement.setAttribute("partyType", p.getClass().getName());
            for (Field field : Party.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(p);
                if (value != null && (field.getType() == String.class || field.getType() == Float.class || field.getType() == Double.class || field.getType() == Integer.class)) {
                    partyDetailElement.setAttribute(field.getName(), value.toString());
                }
            }

            for (Field field : Product.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(product);
                if (value != null && (field.getType() == String.class || field.getType() == Float.class || field.getType() == Double.class || field.getType() == Integer.class)) {
                    productDetailElement.setAttribute(field.getName(), value.toString());
                }
            }
        }

        downloadReport(newDoc, "foodChainReport");
    }
}
