package core.operation;

import core.channel.ChannelType;

public class Selling extends Operation{
    public Selling(Double price, ChannelType channelType, String name) {
        super(price, channelType, name);
    }
}
