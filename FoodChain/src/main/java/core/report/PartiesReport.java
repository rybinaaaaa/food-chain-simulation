package core.report;

import core.product.Product;
import core.transaction.Transaction;
import core.transaction.TransactionResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

public class PartiesReport extends Report {
    private Double margin;

    @Override
    public void buildReport(Product product) throws ParserConfigurationException {
        List<Transaction> transactions = product.getHistory();
        Double price = product.getPrice();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document newDoc = builder.newDocument();

        Element rootElement = newDoc.createElement("PartiesReport");
        newDoc.appendChild(rootElement);

        for (Transaction t : transactions) {
            if (t.getTransactionResult() != TransactionResult.SUCCESS) {
                continue;
            }
            Element detailElement = newDoc.createElement("reportDetail");
            rootElement.appendChild(detailElement);

            detailElement.setAttribute("partyName", t.getParty().getFullName());
            detailElement.setAttribute("ProductId", Long.toString(product.getId()));
            detailElement.setAttribute("ProductName", product.getName());
            detailElement.setAttribute("OperationType", t.getOperation().getName());
            detailElement.setAttribute("Duration", Float.toString(t.getOperation().getDuration()));
        }

        downloadReport(newDoc, "partiesReport");
    }
}
