/*
 * Homework 4_2 TicTacToe with OOP by Sergey Anisimov
 */


import java.util.*;

public class TicTacToeOOP {
	
	Scanner scanner = new Scanner(System.in);
	Field field = new Field();
	Player player = new Player();
	AIplayer ai = new AIplayer();
	final String PLAYER_WON = "You won!";
    final String AI_WON = "AI won!";
    final String DRAW_MSG = "Draw";
    
    
    
	public static void main(String[] args) {
		new TicTacToeOOP().play();
	}
	
	void play(){
		field.print();
		while(true){
			player.move();
            field.print();
            if (field.isWin(player.getDot())) {
                System.out.println(PLAYER_WON);
                break;
            }
            if (field.isFull()) {
                System.out.println(DRAW_MSG);
                break;
            }
            ai.turn();
            field.print();
            if (field.isWin(ai.getDot())) {
                System.out.println(AI_WON);
                break;
            }
            if (field.isFull()) {
                System.out.println(DRAW_MSG);
                break;
            }
		}
	}

	class Field {
		final int FIELD_SIZE = 3;
		final char EMPTY_DOT = '.';
		char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
		Field() {
			for (int i = 0; i < FIELD_SIZE; i++)
				for (int j = 0; j < FIELD_SIZE; j++)
					field[i][j] = EMPTY_DOT;
		}

		int getSize() { return FIELD_SIZE; }

		void setDot(int x, int y, char ch) { field[x][y] = ch; }

		boolean isCellEmpty(int x, int y) {
			if (x < 0 || y < 0 || x > FIELD_SIZE - 1 || y > FIELD_SIZE - 1) return false;
			if (field[x][y] == EMPTY_DOT) return true;
			return false;
		}

		boolean isFull() {
			for (int i = 0; i < FIELD_SIZE; i++)
				for (int j = 0; j < FIELD_SIZE; j++)
					if (field[i][j] == EMPTY_DOT) return false;
			return true;
		}

		boolean isWin(char ch) {
			for (int i = 0; i < FIELD_SIZE; i++) {
				if (field[i][0] == ch && field[i][1] == ch && field[i][2] == ch) return true;
				if (field[0][i] == ch && field[1][i] == ch && field[2][i] == ch) return true;
			}
			if(field[0][0] == ch && field[1][1] == ch && field[2][2] == ch) return true;
			if(field[2][0] == ch && field[1][1] == ch && field[0][2] == ch) return true;
			return false;
		}

		void print() {
			for (int y = 0; y < FIELD_SIZE; y++) {
				for (int x = 0; x < FIELD_SIZE; x++) 
					System.out.print(field[x][y]);
				System.out.println();
			}
		}
	}

	
	class Player{
		private final char DOT = 'X';
		char getDot() { return DOT; }
		void move() {
			int x, y;
			do {
				System.out.println("Enter coordinates X Y (1-"+field.getSize()+"):");
				x = scanner.nextInt() - 1;
				y = scanner.nextInt() - 1;
			} while (!field.isCellEmpty(x, y));
			field.setDot(x, y, DOT);
		}
	}

	
	class AIplayer{
		Random random = new Random();
		final char DOT = 'O';
		char getDot() { return DOT; }
		void turn() {
			System.out.println("AI made its turn");
			int x, y;
			do {
				x = random.nextInt(field.getSize());
				y = random.nextInt(field.getSize());
			} while (!field.isCellEmpty(x, y));
		field.setDot(x, y, DOT);
		}
	}
}