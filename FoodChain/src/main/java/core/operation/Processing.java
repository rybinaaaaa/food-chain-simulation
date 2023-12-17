package core.operation;

import core.channel.ChannelType;

public class Processing extends Operation{
    private Double temperature;

    private Float duration;

    public Processing(Double price, ChannelType channelType, String name) {
        super(price, channelType, name);
    }
}
