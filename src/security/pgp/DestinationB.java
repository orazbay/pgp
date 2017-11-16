package security.pgp;

import security.DES;
import security.RSA;
import security.Utils;
import security.pgp.CommunicationInterface;

import java.security.PrivateKey;
import java.util.ArrayList;

public class DestinationB implements CommunicationInterface {
    private PrivateKey privateKey;
    private ArrayList<CommunicationInterface> allowedUsers;
    public DestinationB(PrivateKey privateKey){
        this.privateKey=privateKey;
        allowedUsers=new ArrayList<>();
    }
    @Override
    public void send(int aim,String message,CommunicationInterface to) {

    }

    @Override
    public void onReceive(int aim,String message,CommunicationInterface from) {
//        System.out.println("security.pgp.DestinationB:onRecieve;"+message);
        switch (aim){
            case CommunicationInterface.AUTH:
                try {
                    String decompressed=decompress(message);
                    String encryptedHash=decompressed.substring(0,256);
                    String msg=decompressed.substring(256);
                    String decryptedHash=decryptHash(encryptedHash);
                    String hashOfMsg=Utils.md5Custom(msg);
                    if (decryptedHash.equals(hashOfMsg)){
                        System.out.println("DestinationB:Success auth");
                        allowedUsers.add(from);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CommunicationInterface.CONF:
                if (isDestinationAuthed(from)){//checs whether destination allowed
                    try {
                        String encryptedKey=message.substring(0,256);
                        String encryptedAndCompressedMessage=message.substring(256);
                        String key=Utils.getStringFrom(RSA.decrypt(privateKey,Utils.getBytesFrom(encryptedKey)));//get session(des) key by decrypting with rsa
                        String compressedMessage= DES.decrypt(key,encryptedAndCompressedMessage);//decrypt with des otained above des key
                        String msg= Utils.decompress(Utils.getBytesFrom(compressedMessage));
                        System.out.println("DestinationB received message: '"+msg+"'");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    private boolean isDestinationAuthed(CommunicationInterface destination){
        return allowedUsers.contains(destination);
    }
    private String decompress(String compressedString){
        return Utils.decompress(Utils.getBytesFrom(compressedString));
    }
    private String decryptHash(String encryptedHash) throws Exception {
        return Utils.getStringFrom(RSA.decrypt(privateKey,Utils.getBytesFrom(encryptedHash)));
    }

//    private
}
