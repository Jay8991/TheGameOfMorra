import java.io.Serializable;

public class MorraInfo implements Serializable {

    private static final long serialVersionUID=1L;

    int client_size=0;
    int player1_points=0;
    int player2_points=0;

    int Plays1=0;
    int Plays2=0;
    int play1_guess=0;
    int play12_guess=0;

    Boolean player1_again=false;
    Boolean player2_again= false;

    int specified_winning=0 ;
    Boolean have_2_Players;
}
