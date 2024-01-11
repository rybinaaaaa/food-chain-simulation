package Core.Report;

import Core.Product.Product;
import Core.Transaction.Transaction;
import Core.Transaction.TransactionResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

public class PartiesReport extends Report {
    public PartiesReport(Product product) throws ParserConfigurationException, IllegalAccessException {
        super(product);
        this.fileName = "partiesReport" + this.id.toString();
    }

    @Override
    protected Document buildReport(Product product) throws ParserConfigurationException {
        List<Transaction> transactions = product.getHistory();
        Double price = product.getPrice();
        Double margin = 0.0;

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document newDoc = builder.newDocument();

        Element rootElement = newDoc.createElement("PartiesReport");
        newDoc.appendChild(rootElement);

        for (Transaction t : transactions) {
            if (t.getTransactionResult() != TransactionResult.SUCCESS) {
                continue;
            }
            margin += t.getOperation().getPrice();
            Element detailElement = newDoc.createElement("reportDetail");
            rootElement.appendChild(detailElement);

            detailElement.setAttribute("partyName", t.getParty().getFullName());
            detailElement.setAttribute("ProductId", Long.toString(product.getId()));
            detailElement.setAttribute("ProductName", product.getName());
            detailElement.setAttribute("OperationType", t.getOperation().getName());
            detailElement.setAttribute("Duration", Float.toString(t.getOperation().getDuration()));
        }

        Element marginElement = newDoc.createElement("Margin");
        marginElement.setAttribute("margin", String.valueOf(margin - price));

        return newDoc;
    }
}
