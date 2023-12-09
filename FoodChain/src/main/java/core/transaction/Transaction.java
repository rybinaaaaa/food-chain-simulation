package core.transaction;

import core.entity.party.Party;
import core.operation.Operation;

public class Transaction {
    private Long id;

    private Party party;

    private Operation operation;

    private String hash;

    private Transaction previousTransaction;

}
