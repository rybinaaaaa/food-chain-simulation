package Core.Operation;

import Core.Channel.ChannelType;

public class Processing extends Operation{
    private Double temperature;

    public Processing(Double price, ChannelType channelType, String name, Double temp, Float duration) {
        super(price, channelType, name, duration);
        this.temperature = temp;
    }
}
