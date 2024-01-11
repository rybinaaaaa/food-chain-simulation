package Core.Operation;

import Core.Channel.ChannelType;

public class Growing extends Operation{
    private Double temperature;


    public Growing(Double price, ChannelType channelType, String name, Float duration, Double temperature) {
        super(price, channelType, name, duration);
        this.temperature = temperature;
    }
}
