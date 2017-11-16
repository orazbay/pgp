package security;

public class HASHER {
    private static String key = "hellohellohellohello";//fixed length of key 160 bit(20 characters)
    public static String getHASH(String input){
        return getHash(fillWithZero(256,addInputLength(getBinary(input))),keyFrom160To80(getBinary(key)));
    }
    static String getBinary(String input) {
        String result = "";
        for (byte b : input.getBytes()) {
            result=result+String.format("%8s",Integer.toBinaryString(b&0xFF)).replace(' ','0');
        }
        return result;
    }
    private static String addInputLength(String result){
        return result + Integer.toBinaryString(result.length());//added key length in binary
    }

    private static String fillWithZero(int upTo, String input) {
        int size = input.length();
        while (size != upTo) {
            input = input + "0";
            size++;
        }
        return input;
    }

    private static String xor(String a, String b) {
        int result = Integer.parseInt(a) + Integer.parseInt(b);
        if (result == 2) {
            result = 0;
        }
        return String.valueOf(result);
    }
    private static String keyFrom160To80(String keyOld){
        String keyNew="";
        for(int i=0;i<80;i++){
            keyNew=keyNew+(xor(String.valueOf(keyOld.charAt(i)), String.valueOf(keyOld.charAt(159-i))));
        }
        return keyNew;
    }
    private static String getHash(String inputBytes,String keyBytes){
        String result="";
        keyBytes=keyBytes+keyBytes.substring(0,48);//set key size 128
        for(int i=0;i<128;i++){
            int condition=Integer.parseInt(String.valueOf(keyBytes.charAt(i)));
            String bitOfFirstHalf=String.valueOf(inputBytes.charAt(i));
            String bitOfSecondHalf=String.valueOf(inputBytes.charAt(255-i));
            switch (condition){
                case 0:
                    result=result+bitOfFirstHalf+bitOfSecondHalf;
                    break;
                case 1:
                    result=result+bitOfSecondHalf+bitOfFirstHalf;
                    break;

            }
        }
        //now we have 256 bits 32 groups by 8 bit

        String result160bit="";
        for (int i=1;i<33;i++){
            result160bit=result160bit+(result.substring((i-1)*8,8*i)).substring(3);
        }
        System.out.println(result160bit.length());
        return result160bit;
    }
    private static String fromBinaryToHexadecimal(String binary){
        int decimal = Integer.parseInt(binary,2);
        return Integer.toString(decimal,16);
    }
    public static String fromBinaryToDecimal(String binary){
        int decimal = Integer.parseInt(binary,2);
        return Integer.toString(decimal,10);
    }
    private static String fromBinaryToHexStream(String binaryStream){
        String hexStream="";
        for (int i=1;i<binaryStream.length()/8+1;i++){
            hexStream=hexStream+fromBinaryToHexadecimal(binaryStream.substring((i-1)*8,8*i));
        }
        return hexStream;
    }

    static String fromBinaryToDecimalStream(String binaryStream){
        String hexStream="";
        for (int i=1;i<binaryStream.length()/8+1;i++){
            hexStream=hexStream+fromBinaryToDecimal(binaryStream.substring((i-1)*8,8*i))+" ";
        }
        return hexStream;
    }
}
