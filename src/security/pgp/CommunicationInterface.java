package security.pgp;

public interface CommunicationInterface {
    int AUTH=0;
    int CONF=1;
    public void send(int aim,String message,CommunicationInterface destinationTo);
    public void onReceive(int aim,String message,CommunicationInterface from);
}
