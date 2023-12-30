package core.party;

import core.channel.Channel;
import exception.NoCustomerFoundException;

public class Warehouse extends  Party{
    public Warehouse(UserKey key, String firstName, String lastName) {
        super(key, firstName, lastName);
    }


}
