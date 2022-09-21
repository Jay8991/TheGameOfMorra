import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{


    Socket socketClient;
    MorraInfo mi;
    ObjectOutputStream out;
    ObjectInputStream in;
TheGameOfMorra gm = new TheGameOfMorra();
    String portNumber;
    MorraInfo data1;
    int Port;
    String d;
    Client ()
    {

    }
    void check_port_ip(int Port, String portnumber)
    {
        this.portNumber = portnumber;
        this.Port = Port;

    }


    int get_port()
    {
        return this.Port;
    }
    String get_ip()
    {
        return this.portNumber;
    }
    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call){

        callback = call;
    }

    public void run() {

        try {
            socketClient= new Socket("127.0.0.1",5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {}

        while(true) {

            try {

               // String data = in.readObject().toString();
               /*Serializable data1 = (Serializable)in.readObject();*/
                 data1 = (MorraInfo) in.readObject();
                callback.accept(" Message From Server: " +
                        "\n Player 1: " + data1.Plays1 +
                        "\n Player 2: " + data1.Plays2  +
                        "\n Player 1 Guess: " + data1.play1_guess +
                        "\n Player 2 Guess: " + data1.play12_guess
//                        "\n Player 1 points:" + data1.player1_points +
  //                      "\n Player 2 points:" + data1.player2_points+ "\n" ) ;
                );



            }
            catch(Exception e) {


            }
        }

    }


    public void send_morra(int data,int data1, boolean data2, int data3)
    {
        mi = new MorraInfo();
        try {
            mi.play1_guess = data;
            mi.Plays1 = data1;
            mi.player1_again = data2;
            mi.specified_winning= data3;
            out.writeObject(mi);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void send(String data) {

        mi = new MorraInfo();
        try {
            //mi.Plays1 = data;
            out.writeObject(mi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
