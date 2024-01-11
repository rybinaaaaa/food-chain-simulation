package Core.Product.State;

import Core.Channel.Channel;
import Core.Product.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class State {
    protected final Product context;

    protected int timeToProcess;

    protected static final Logger logger = LogManager.getLogger(Channel.class);

    public State(Product product) {
        this.context = product;
    }

    public void process(){}
    public void setNextState(){}





}
