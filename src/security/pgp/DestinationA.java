package security.pgp;

import security.DES;
import security.RSA;
import security.Utils;
import security.pgp.CommunicationInterface;

import java.security.PublicKey;

public class DestinationA implements CommunicationInterface {
    private PublicKey publicKey;
    public DestinationA(PublicKey publicKey) throws Exception {
        this.publicKey=publicKey;

    }
    @Override
    public void send(int aim,String message,CommunicationInterface to) {
        to.onReceive(aim,message,this);
    }

    @Override
    public void onReceive(int aim,String message,CommunicationInterface from) {
    }
    public void auth(String message,CommunicationInterface to) throws Exception {
        String hash= Utils.md5Custom(message);//first of all hash message
        String encryptedHash=encryptWithPublicKey(hash);//encrypt hash
        String concatenatedHASHandMESSAGE=concat(encryptedHash,message);//concat hash and message
        String comressed=compress(concatenatedHASHandMESSAGE);
        send(CommunicationInterface.AUTH,comressed,to);
    }
    public void conf(String key,String message,CommunicationInterface to) throws Exception {
        String compressed=compress(message);//compress message
        String encrypted= DES.encrypt(key,compressed);//encrypt compressedMessage with des
        String encryptedKey=Utils.getStringFrom(RSA.encrypt(publicKey,key));//encypt session(private key) with public key(rsa)
        String result=encryptedKey+encrypted;//concatenate
        send(CONF,result,to);
    }
    public String encryptWithPublicKey(String hash) throws Exception {
        byte [] encrypted = RSA.encrypt(publicKey, hash);
        return Utils.getStringFrom(encrypted);
    }
    public String concat(String encryptedHashOfMessage, String message){
        return encryptedHashOfMessage+message;
    }
    public String compress(String concatenatedString){
        String result=Utils.getStringFrom(Utils.compress(concatenatedString));
        return result;
    }

}
