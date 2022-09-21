


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.scene.image.ImageView;


public class Server{
    javafx.scene.image.Image zero ;
    ImageView zero_view;
    javafx.scene.image.Image one ;
    ImageView one_view ;
    javafx.scene.image.Image two ;
    ImageView two_view ;
    javafx.scene.image.Image three ;
    ImageView three_view ;
    javafx.scene.image.Image four ;
    ImageView four_view ;
    javafx.scene.image.Image five ;
    ImageView five_view;

    int count = 0;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheGameOfMorra gmm;
    TheServer server;
    private Consumer<Serializable> callback;


    MorraInfo info ;
    Server()
    {

    }

    Server(Consumer<Serializable> call){

        callback = call;
        server = new TheServer();
        info =  new MorraInfo();
        server.start();
    }

    public class TheServer extends Thread{

        public void run() {

            try(ServerSocket mysocket = new ServerSocket(5555)){
                System.out.println("Server is waiting for a client!");

                while(true) {




                    ClientThread c = new ClientThread(mysocket.accept(), count );

                    clients.add(c);

                    c.start();
                    System.out.println("Client size" + clients.size());
                    clients.get(count).count1 = count;
                    //   }



                    if(clients.size() ==2)
                    {
                        info.have_2_Players = true;
                        callback.accept("There are : " + clients.size() + " Client(s)!!! Get ready for the game" );

                    }
                    else
                    {

                        if(clients.size()<2) {
                            info.have_2_Players = false;
                            callback.accept("There are : " + clients.size() + " Client(s)!!! Not enough Players wait for another client");
                        }

                    }


                    count++;
                    callback.accept("Hello Client " + count);

                }

            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    public int game_Decider(int totalguess , int player_1 , int player_2 )
    {
        if (player_1 ==  totalguess) {
            /*info.player1_points += info.player1_points +2; //increment the points
            callback.accept("player 1 won");*/

            return 1;
        }
        else if(player_1 == totalguess && player_2 == totalguess)
        {
            /*            callback.accept("its a tie ");*/
            return 0;
        }

        else if(player_1 != totalguess && player_2 != totalguess)
        {
            /*            callback.accept("its a tie ");*/
            return 0;
        }
        else {
            if (player_2 ==  totalguess) {
/*
                callback.accept("player 2 won");
                info.player2_points += info.player2_points +2;
*/
                return 2;

            }
        }
        return 0;
    }


    public int check_guess(int p1 , int p2)
    {
        if(p1 >= 0 && p1<= 10 && p2 >= 0 && p2<= 10 ) {
            return 1;
        }
        else
        {
            return -1;
        }
    }

            ///for the player to display
    public ImageView img_return(int plays)
    {


        //////////////////////IMAGES FOR THE GAME //////////
        if(plays ==0) {
            zero = new javafx.scene.image.Image("zero.png");
            zero_view = new ImageView();
            zero_view.setImage(zero);
            zero_view.setFitHeight(60);
            zero_view.setFitWidth(60);
            return zero_view;
        }
        else if(plays==1) {
            one = new javafx.scene.image.Image("one.jpg");
            one_view = new ImageView();
            one_view.setImage(one);

            one_view.setFitHeight(60);
            one_view.setFitWidth(60);
            return one_view;
        }

        else if(plays==2) {
            two = new javafx.scene.image.Image("two.jpg");
            two_view = new ImageView();
            two_view.setImage(two);
            two_view.setFitHeight(60);
            two_view.setFitWidth(60);
            return two_view;
        }

        else if(plays==3) {
            three = new javafx.scene.image.Image("three.jpg");
            three_view = new ImageView();
            three_view.setImage(three);
            three_view.setFitHeight(60);
            three_view.setFitWidth(60);
            return three_view;
        }

        else if(plays==4) {
            four = new javafx.scene.image.Image("four.jpg");
            four_view = new ImageView();
            four_view.setImage(four);

            four_view.setFitHeight(60);
            four_view.setFitWidth(60);
            return four_view;
        }
        else {
            five = new javafx.scene.image.Image("five.jpg");
            five_view = new ImageView();
            five_view.setImage(five);
            five_view.setFitHeight(60);
            five_view.setFitWidth(60);
            return five_view;
        }

    }
    class ClientThread extends Thread{


        Socket connection;
        int count1= 0;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count1 = count;

        }


        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);

            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            //          updateClients("new client on server: client #"+count);

            while(true) {
                try {

                    //  String data = in.readObject().toString();
                    // THIS IS WORKING FINE
                    MorraInfo info1 = (MorraInfo) in.readObject();
                    info.client_size = count;
                    if(info1.specified_winning!=(info.player1_points + info.player2_points)) {

                        callback.accept("WINNING POINTS ARE : "+ info1.specified_winning);
                        if (clients.size() < 0) {
                            clients.get(this.count1).out.writeObject("Wait for the opponent");

                            info.player1_points = 0;
                            info.player2_points = 0;
                        }

                            if (clients.size() == 2) {
                                //////if client size is two and player wants to playagain

                                if (info1.player1_again == true) {
                                    info.player1_points = 0;
                                    info.player2_points = 0;
                                }

                                if (this.count1 == 0) {
                                    info.play1_guess = info1.Plays1;
                                    info.Plays1 = info1.play1_guess;
                                    info.player1_points = info.player1_points;

                                    if (check_guess(info.Plays1, 0) == 1) {

                                        callback.accept("Player # " + this.count1 + 1 + " Guess " + (info.play1_guess));
                                        callback.accept("Player # " + this.count1 + 1 + " Play " + (info.Plays1));

                                        clients.get(0).out.writeObject("Player 1 Points: " + info.player1_points);     //updating the client
                                        clients.get(0).out.writeObject(info);

                                    } else {
                                        clients.get(0).out.writeObject("Guess Values must be between 0-10");     //updating the client

                                    }

                                }
                                if (this.count1 == 1) {

                                    if (info1.player1_again == true) {
                                        info.player1_points = 0;
                                        info.player2_points = 0;

                                    }

                                    info.play12_guess = info1.Plays1;
                                    info.Plays2 = info1.play1_guess;
                                    info.player2_points = info.player2_points;


                                    if (check_guess(info.Plays1, info.Plays2) == 1) {

                                        callback.accept("Player # " + this.count1 + 1 + " Guess " + (info.play12_guess));
                                        callback.accept("Player # " + this.count1 + 1 + " Play " + (info.Plays2));
                                        clients.get(1).out.writeObject("Player 2 Points: " + info.player2_points);     //updating the client
                                        clients.get(1).out.writeObject(info);
                                    } else {
                                        clients.get(1).out.writeObject("Guess Values must be between 0-10");     //updating the client

                                    }

                                }

                                if (info.Plays1 != 0 && info.Plays2 != 0 && info.play12_guess != 0 && info.play1_guess != 0) {
                                    int total = (info.Plays1 + info.Plays2);

                                    if (game_Decider(total, info.play1_guess, info.play12_guess) == 1) {
                                        info.player1_points = info.player1_points + 1;
                                        callback.accept("Player 1 Won : Player 1 Points are : " + info.player1_points);

                                        info.Plays1 = 0;
                                        info.Plays2 = 0;

                                    } else if (game_Decider(total, info.play1_guess, info.play12_guess) == 2) {

                                        info.player2_points = info.player2_points + 1;
                                        callback.accept("Player 2 Won : Player 2 Points are : " + info.player2_points);
                                        info.Plays1 = 0;
                                        info.Plays2 = 0;
                                    } else {

                                        callback.accept("TIE");
                                        callback.accept("TIE");

                                        info.Plays1 = 0;
                                        info.Plays2 = 0;
                                    }
                                }

                                out.reset();
                            } else {
                                callback.accept("Not have enough players");
                                clients.get(this.count1).out.writeObject("Not  have enough players");
                            }
                        //end if specify condition
                    }
                    else
                    {
                        if(info.player1_points > info.player2_points) {
                            callback.accept("Player 1 Won the Game");
                            info.player1_points = 0;
                            info.player2_points = 0;

                        }
                        if(info.player1_points < info.player2_points) {
                            callback.accept("Player 2 Won the Game");
                            info.player1_points = 0;
                            info.player2_points = 0;
                        }
                        clients.get(this.count1).out.writeObject("You can't play more!! You reached the winning points");

                        info1.specified_winning = -1;
                    }
                } catch (Exception e) {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    if(this.count1 == 0)
                    {
                        clients.remove(this);
                        count = 0;
                    }
                    else
                    {
                        if(this.count1 == 1)
                        {
                            clients.remove(this);
                            count = 1;
                        }
                    }
                    break;
                }


            }

        }//end of run


    }//end of client thread
}







