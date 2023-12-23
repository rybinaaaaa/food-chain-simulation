package core.operation;

import core.channel.ChannelType;

public class Delivering extends Operation{

    public Delivering(Double price, ChannelType channelType, String name, Float duration) {
        super(price, channelType, name, duration);
    }
}
