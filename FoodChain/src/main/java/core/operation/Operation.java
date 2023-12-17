package core.operation;

import core.channel.ChannelType;

public abstract class Operation {
    private Double price;
    private ChannelType channelType;
    private String name;


    public Operation(Double price, ChannelType channelType, String name) {
        this.price = price;
        this.channelType = channelType;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
