package Core.Operation;

import Core.Channel.ChannelType;

public class Selling extends Operation{
    public Selling(Double price, ChannelType channelType, String name, Float duration) {
        super(price, channelType, name, duration);
    }
}
