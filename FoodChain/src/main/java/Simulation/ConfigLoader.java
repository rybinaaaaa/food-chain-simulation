package Simulation;
import Core.Certificate.Certificate;
import Core.Channel.Channel;
import Core.Channel.ChannelType;
import Core.Operation.*;
import Core.Party.Party;
import Core.Party.PartyFactory;
import Core.Party.UserKey;
import Core.Product.AmountUnit;
import Core.Product.Carrot;
import Core.Transaction.Account;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {
    private JSONObject jsonObject;

    private List<Party> parties;

    private List<Channel> channels;

    private List<Operation> operations;

    private Carrot carrot;

    /**
     * Loads project configuration from a json file.
     * @param path path to the configuration file
     */
    public void load(String path) {
        File file = new File(path);
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            assert inputStream != null;
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            jsonObject = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //product
        JSONArray loadedProduct = null;
        JSONObject product = null;
        this.carrot = null;
        try {
            loadedProduct = jsonObject.getJSONArray("product");
        } catch (Exception ignored) {
        }
        if (loadedProduct != null) {
            product = loadedProduct.getJSONObject(0);
            String name = product.getString("name");
            Double weight = product.getDouble("weight");
            Double price = product.getDouble("price");
            int amount = product.getInt("amount");
            AmountUnit unit = AmountUnit.valueOf(product.getString("unit"));
            carrot = new Carrot(1L, weight, name, price, amount, unit);
        }

        // parties
        JSONArray loadedParties = null;
        this.parties = new ArrayList<>();
        try {
            loadedParties = jsonObject.getJSONArray("parties");
        } catch (Exception ignored) {
        }
        Long id = 1L;
        if (loadedParties != null) {
            for (Object p : loadedParties) {
                JSONObject party = (JSONObject) p;
                String name = party.getString("name");
                String surname = party.getString("surname");
                PartyFactory.PartyType type = PartyFactory.PartyType.valueOf(party.getString("type"));
                boolean isRetroactiveChange = party.getBoolean("isRetroactiveChange");
                Party current = PartyFactory.createParty(type, new UserKey(), name, surname);
                if(isRetroactiveChange) current.setRetroactiveChange(true);
                current.setAccount(new Account(id, 2000000D));
                parties.add(current);
                id++;
            }
        }

        //channels
        JSONArray loadedChannels = null;
        this.channels = new ArrayList<>();
        try {
            loadedChannels = jsonObject.getJSONArray("channels");
        } catch (Exception ignored) {
        }
        if (loadedChannels != null) {
            for (Object ch : loadedChannels) {
                JSONObject channel = (JSONObject) ch;
                ChannelType type = ChannelType.valueOf(channel.getString("type"));
                channels.add(new Channel(type));
            }
        }

        //operations
        JSONArray loadedOperations = null;
        this.operations = new ArrayList<>();
        try {
            loadedOperations = jsonObject.getJSONArray("operations");
        } catch (Exception ignored) {
        }
        if (loadedOperations != null) {
            for (Object o : loadedOperations) {
                JSONObject operation = (JSONObject) o;
                String operationType = operation.getString("type");
                Double price = operation.getDouble("price");
                String name = operation.getString("name");
                ChannelType ct = ChannelType.valueOf(operation.getString("channelType"));
                Float duration = operation.getFloat("duration");
                switch(operationType){
                    case "Growing":
                        Double temp = operation.getDouble("temperature");
                        operations.add(new Growing(price, ct, name, duration, temp));
                        continue;

                    case "Processing":
                        Double temp1 = operation.getDouble("temperature");
                        operations.add(new Processing(price, ct, name, temp1, duration));
                        continue;

                    case "Storing":
                        Double temp2 = operation.getDouble("temperature");
                        Double humidity = operation.getDouble("humidity");
                        operations.add(new Storing(price, ct, name, temp2, duration, humidity));
                        continue;

                    case "Delivering":
                        operations.add(new Delivering(price, ct, name, duration));
                        continue;

                    case "Selling":
                        operations.add(new Selling(price, ct, name, duration));

                }
            }
        }

        //set operations and channels to parties
        JSONArray partyOperationsAndChannels = null;
        try {
            partyOperationsAndChannels = jsonObject.getJSONArray("partyOperationsAndChannels");
        } catch (Exception ignored) {
        }
        if (partyOperationsAndChannels != null) {
            for (Object po : partyOperationsAndChannels) {
                JSONObject party = (JSONObject) po;
                String name = party.getString("name");
                String surname = party.getString("surname");
                String operationType = party.getString("operation");
                ChannelType channelType = ChannelType.valueOf(party.getString("channel"));
                Channel currentChannel = channels.stream().filter(c -> c.getType().equals(channelType)).findAny().get();
                Party currentParty = parties.stream().filter(p -> p.getFirstName().equals(name) && p.getLastName().equals(surname)).findAny().get();
                Operation currentOperation = operations.stream().filter(o -> o.getName().equals(operationType)).findAny().get();
                currentParty.setOperation(currentOperation);
                currentParty.addChannel(currentChannel);
            }
        }

        //channel subscribers
        JSONArray channelSubscribers = null;
        try {
            channelSubscribers = jsonObject.getJSONArray("channelSubscribers");
        } catch (Exception ignored) {
        }
        if (channelSubscribers != null) {
            for (Object cs : channelSubscribers) {
                JSONObject party = (JSONObject) cs;
                String name = party.getString("name");
                String surname = party.getString("surname");
                String operationType = party.getString("operation");
                ChannelType channelType = ChannelType.valueOf(party.getString("channelType"));
                Party currentParty = parties.stream().filter(p -> p.getFirstName().equals(name) && p.getLastName().equals(surname)).findAny().get();
                Operation currentOperation = operations.stream().filter(o -> o.getName().equals(operationType)).findAny().get();
                Channel currentChannel = channels.stream().filter(c -> c.getType().equals(channelType)).findAny().get();
                currentChannel.subscribe(currentParty, currentOperation);
            }
        }

        //add certificates
        JSONArray loadedCertificates = null;
        try {
            loadedCertificates = jsonObject.getJSONArray("certificates");
        } catch (Exception ignored) {
        }
        if (loadedCertificates != null) {
            for (Object c : loadedCertificates) {
                JSONObject certificate = (JSONObject) c;
                String partyName = certificate.getString("name");
                String partySurname = certificate.getString("surname");
                Long idCert = certificate.getLong("idCert");
                String code = certificate.getString("code");
                Party currentParty = parties.stream().filter(p -> p.getFirstName().equals(partyName) && p.getLastName().equals(partySurname)).findAny().get();

                Certificate cert = new Certificate(idCert, code,currentParty.getOperation().getClass(),currentParty, carrot.getId(),true);
                currentParty.addCertificates(cert);
            }
        }

    }

    public List<Party> getParties() {
        return parties;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public Carrot getProduct() {
        return carrot;
    }
}
