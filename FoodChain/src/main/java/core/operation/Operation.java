package core.operation;

import core.channel.ChannelType;

public abstract class Operation {
    private final Double price;
    private final ChannelType channelType;
    private final String name;
    private final Float duration;


    public Operation(Double price, ChannelType channelType, String name, Float duration) {
        this.price = price;
        this.channelType = channelType;
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
