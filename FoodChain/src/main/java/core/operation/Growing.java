package core.operation;

import core.channel.ChannelType;

public class Growing extends Operation{
    private Double temperature;


    public Growing(Double price, ChannelType channelType, String description, Float duration, Double temperature) {
        super(price, channelType, description, duration);
        this.temperature = temperature;
    }
}
