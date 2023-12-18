package core.model.party;

public class PartyFactory {
    public static enum PartyType {
        CUSTOMER, FARMER, PROCESSOR, RETAILER, WAREHOUSE
    }

    public static Party createParty (PartyType partyType, Long id, String firstName, String lastName) {
        switch (partyType) {
            case FARMER:
                return new Farmer(id, firstName, lastName);
            case CUSTOMER:
                return new Customer(id, firstName, lastName);
            case RETAILER:
                return new Retailer(id, firstName, lastName);
            case PROCESSOR:
                return new Processor(id, firstName, lastName);
            case WAREHOUSE:
                return new Warehouse(id, firstName, lastName);
            default:
                throw new IllegalArgumentException("Unknown PartyType: " + partyType);
        }
    }
}
