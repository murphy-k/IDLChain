package IDLChain;

import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    private String data;        // data is a message.
    private long timeStamp;    // the number of milliseconds since the UNIX epoch.
    private int nonce;

    // Block Constructor.
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); // Doing this only after we have set up the other values.
    }

    // Calculate new hash based on blocks contents
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
        return calculatedhash;
    }
    /* Use our 'applySha256 helper' in a new method in the 'Block' class. This is how we calculate our hash, we must
    * calculate the hash from all parts of the block we don't want to be tampered. We include 'previousHash' ' data' and
    * 'timeStamp. */
    // Increase 'nonce' value until hash target is reached.
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined successfully. : " + hash);
    }
    /* This mineBlock() method takes an int called difficulty, this is the number of 0's that must be solved for. Low
    * difficulty like 1-2 can be solved nearly instantaneously on most computers. Something like 4-6 for testing is
    * appropriate. As of Wednesday August 7th 2019 @ 1:49pm LiteCoin's is difficulty is 9,814,639. */
}