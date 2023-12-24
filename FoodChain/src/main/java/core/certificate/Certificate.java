package core.certificate;

import core.operation.Operation;
import core.party.Party;

public class Certificate {
    private Long id;

    private String code;

    private Class<? extends Operation> operationClass;

    private Party party;

    private Long productId;

    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Class<? extends Operation> getOperationClass() {
        return operationClass;
    }

    public void setOperationClass(Class<Operation> operationClass) {
        this.operationClass = operationClass;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Certificate(Long id, String code, Class<? extends Operation> operationClass, Party party, Long productId, boolean isActive) {
        this.id = id;
        this.code = code;
        this.operationClass = operationClass;
        this.party = party;
        this.productId = productId;
        this.isActive = isActive;
    }
}
