package core.operation;

import core.channel.ChannelType;

public class Processing extends Operation{
    private Double temperature;

    private Float duration;

    public Processing(Double price, ChannelType channelType, String name, Double temp, Float duration) {
        super(price, channelType, name);
        this.temperature = temp;
        this.duration = duration;
    }
}
