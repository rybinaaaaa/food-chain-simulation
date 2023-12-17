package core.operation;

import core.channel.ChannelType;

public class Storing extends Operation{
    private Double temperature;

    private Float duration;

    private Double humidity;

    public Storing(Double price, ChannelType channelType, String name) {
        super(price, channelType, name);
    }
}
