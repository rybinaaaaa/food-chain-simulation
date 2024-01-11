package Core.Operation;

import Core.Channel.ChannelType;

public class Storing extends Operation{
    private Double temperature;

    private Double humidity;

    public Storing(Double price, ChannelType channelType, String name, Double temp, Float duration, Double humidity) {
        super(price, channelType, name, duration);
        this.temperature = temp;
        this.humidity = humidity;
    }
}
