/*
 * TicTacToe Game by Anisimov Sergey
 * AI v2 with simple blocking Horizontal & vertical lines
 * checkWin with for(){}
 * 
 */
import java.util.*;

class TicTacToe {

    final static char PLAYER_DOT = 'x';
    final static char AI_DOT = 'o';
    final char EMPTY_DOT = '.';
    final static int FIELD_SIZE = 3;
    static char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    public static void main(String[] args) {
        new TicTacToe().go();
    }

    void go() {
        initField();
        printField();
        while (true) {
            turnPlayer();
            printField();
            if (checkWin(PLAYER_DOT)) {
                System.out.println("You won!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("Sorry draw...");
                break;
            }
            turnAI();
            printField();
            if (checkWin(AI_DOT)) {
                System.out.println("AI won!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("Sorry draw...");
                break;
            }
        }
    }

    void turnPlayer() {
        int x, y;
        do {
            System.out.println("Enter coordinates X Y (1-"+FIELD_SIZE+"):");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellEmpty(x, y));
        field[x][y] = PLAYER_DOT;
    }

    void turnAI() {
        int x, y;
        if(!blockingPlayer()){
        	do {
        		x = rand.nextInt(FIELD_SIZE);
        		y = rand.nextInt(FIELD_SIZE);
        	} while (!isCellEmpty(x, y));
        field[x][y] = AI_DOT;
        }
    }

    boolean isCellEmpty(int x, int y) {
        if (x < 0 || y < 0 || x > FIELD_SIZE - 1 || y > FIELD_SIZE - 1) return false;
        if (field[x][y] == EMPTY_DOT) return true;
        return false;
    }

    boolean isFieldFull() {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++)
                if (field[i][j] == EMPTY_DOT) return false;
        return true;
    }

    boolean checkWin(char ch) {
        for (int i=0; i<3; i++){
        	// check horizontals
        	if (field[i][0] == ch && field[i][1] == ch && field[i][2] == ch) return true;
        	// check verticals
        	if (field[0][i] == ch && field[1][i] == ch && field[2][i] == ch) return true;
        }
        // check diag
        if (field[0][0] == ch && field[1][1] == ch && field[2][2] == ch) return true;
        if (field[2][0] == ch && field[1][1] == ch && field[0][2] == ch) return true;
        return false;
    }

    void initField() {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++)
                field[i][j] = EMPTY_DOT;
    }

    void printField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) 
                System.out.print(field[i][j]);
            System.out.println();
        }
    }
    
    boolean blockingPlayer(){
    	for(int i=0; i<2; i++){
    		if (field[i][0] == PLAYER_DOT && field[i][1] == PLAYER_DOT && isCellEmpty(i,2)) {field[i][2] = AI_DOT; return true;}
    		if (field[i][1] == PLAYER_DOT && field[i][2] == PLAYER_DOT && isCellEmpty(i,0)) {field[i][0] = AI_DOT; return true;}
    		if (field[i][0] == PLAYER_DOT && field[i][2] == PLAYER_DOT && isCellEmpty(i,1)) {field[i][1] = AI_DOT; return true;}
    		if (field[0][i] == PLAYER_DOT && field[1][i] == PLAYER_DOT && isCellEmpty(2,i)) {field[2][i] = AI_DOT; return true;}
    		if (field[1][i] == PLAYER_DOT && field[2][i] == PLAYER_DOT && isCellEmpty(0,i)) {field[0][i] = AI_DOT; return true;}
    		if (field[0][i] == PLAYER_DOT && field[2][i] == PLAYER_DOT && isCellEmpty(1,i)) {field[1][i] = AI_DOT; return true;}
    	}
    	return false;
    }
}