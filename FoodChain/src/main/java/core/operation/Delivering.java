package core.operation;

import core.channel.ChannelType;

public class Delivering extends Operation{
    private Float duration;

    public Delivering(Double price, ChannelType channelType, String description, Float duration) {
        super(price, channelType, description);
        this.duration = duration;
    }
}
