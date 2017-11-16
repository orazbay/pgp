import security.RSA;
import security.pgp.DestinationA;
import security.pgp.DestinationB;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello World!I am gonna fuck all of u");
//        Random random=new Random();
//
//        int[] m = new int[1000];
//        int aiFirstArray[] = new int[6];
//
//        for (int i=m.length-1;i>=0;i--){
//            m[i]=random.nextInt(1000);
////            m[m.length-1-i]=i;
//        }
//
//        System.out.println(Arrays.toString(m));
//        System.out.println(Arrays.toString(Sorter.insertionSort(m)));
//        System.out.println(Arrays.toString(m));
        try {
            securityPGP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void printByte(byte b){
        System.out.println(String.format("%8s",Integer.toBinaryString(b&0xFF)).replace(' ','0'));
    }
    private static void securityPGP() throws Exception {
        KeyPair keyPair = RSA.buildKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        DestinationA destinationA=new DestinationA(pubKey);
        DestinationB destinationB=new DestinationB(privateKey);

        String message="Hello World from Sdu rwew rwe rwew wrew r we we ew gdsf sdgsdghgdfkjdskjdsdkjfgdjdhgfjkghjkdhjkghgjkdfghjdkjghdhjgdfshjgldghjdfhgjdfhgjdlfjghdfjghdsjlghdjghdsfgjhldhgjldfshgjdflhjgfdhjdjgdsjhgjhsdgjhsdfgjksdgjkdfgjfdsgjdsfgjsdgjsdkfgjdfgjdfjgdf rw we r rd asdsasd das da  da sad asd asd asd as das  sad ad a da d sad sad sa das sd asd asd a daa da  asdsa da sdas d sad assdas ew rew rew  dasdsad ad asd a dsa dsa sd";
        String desKey="avangard";//key's length must be 8

        System.out.println("destinationA:tries to auth destinationB");
        destinationA.auth(message,destinationB);

        String confMessage="Anuarbek is the greatest chmo in the world";
        System.out.println("destinationA:tries to send  to destinationB message:'"+confMessage+"'");
        destinationA.conf(desKey,confMessage,destinationB);
    }

}
