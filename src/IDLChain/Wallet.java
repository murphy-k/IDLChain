package IDLChain;

import java.security.*;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
}

/* The public key will act as the address for this 'wallet.' The private key is used to *sign* transactions, so that
* nobody other than the owner of the private key can "spend" the IDLChain blocks. It's wise to keep this secret! We will
* also send our public key along with the transation and it can be used to verify that our signature is valid and the
* data has not been tampered with. */