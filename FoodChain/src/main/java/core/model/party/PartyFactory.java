package core.model.party;

public class PartyFactory {
    public static enum PartyType {
        CUSTOMER, FARMER, PROCESSOR, RETAILER, WAREHOUSE, DELIVER
    }

    public static Party createParty (PartyType partyType, UserKey key, String firstName, String lastName) {
        switch (partyType) {
            case FARMER:
                return new Farmer(key, firstName, lastName);
            case CUSTOMER:
                return new Customer(key, firstName, lastName);
            case RETAILER:
                return new Retailer(key, firstName, lastName);
            case PROCESSOR:
                return new Processor(key, firstName, lastName);
            case WAREHOUSE:
                return new Warehouse(key, firstName, lastName);
            case DELIVER:
                return new Warehouse(key, firstName, lastName);
            default:
                throw new IllegalArgumentException("Unknown PartyType: " + partyType);
        }
    }
}
