package IDLChain;

import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {

        public String transactionId;    // This is also the hash of the transaction.
        public PublicKey sender;        // Sender's address/public key.
        public PublicKey reciepient;     // reciepient's address/public key.
        public float value;
        public byte[] signature;        // This prevents anyone else from spending funds in your wallet.

        public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
        public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

        private static int sequence = 0;    // Approximate count of how many transactions have been generated.

        // Constructor:
        public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
                this.sender = from;
                this.reciepient = to;
                this.value = value;
                this.inputs = inputs;
        }

    // This calculates the transaction hash (which will be used as its Id).
    private String calculateHash() {
            sequence++; // Increase the sequence to avoid 2 identical transactions having the same hash.
            return StringUtil.applySha256(
                        StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(reciepient) +
                        Float.toString(value) + sequence
            );
    }

    // Signs all the data we don't want tampered with.
    public void generateSignature(PrivateKey privateKey) {
                String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
                signature = StringUtil.applyECDSASig(privateKey,data);
    }
    // Verifies the data we signed hasn't been tampered with.
    public boolean verifySignature() {
            String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
            return StringUtil.verifyECDSASig(sender, data, signature);
    }
}
/* Signatures perform two important tasks on the blockchain. They allow only the owner to spend their coins, and they
* prevent others from tampering with their submitted transaction before a new block is mined (at the point of entry).*/

