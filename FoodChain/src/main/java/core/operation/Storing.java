package core.operation;

import core.channel.ChannelType;

public class Storing extends Operation{
    private Double temperature;

    private Float duration;

    private Double humidity;

    public Storing(Double price, ChannelType channelType, String name, Double temp, Float duration, Double humidity) {
        super(price, channelType, name);
        this.temperature = temp;
        this.duration = duration;
        this.humidity = humidity;
    }
}
