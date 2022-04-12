import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

//driver class
public class Main {

	private static void help() {
		System.out.println("This software is licenced under the GNU GPL v3 and comes with absolutely NO WARRANTY");
		System.out.println(
				"javaBingo simulates bingo games with randomly generated boards and numbers. The names of the players are stored in names.txt");
	}

	// check which player has the highest number of marks on their board
	private static int getMostMarks(ArrayList<bingoBoard> players) {
		int most = 0;
		int mostHigh = -1;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).numOfMarks() > most) {
				most = players.get(i).numOfMarks();
				mostHigh = i;
			}
		}
		return mostHigh;
	}

	public static void main(String[] args) {
		if (args.length > 0 && (args[0].equals("-h") || args[0].equals("--help"))) {
			help();
		} else {
			// create players from file of names
			File nameFile = new File("names.txt");
			ArrayList<bingoBoard> players = new ArrayList<bingoBoard>();
			try {
				Scanner scanNames = new Scanner(nameFile);
				while (scanNames.hasNextLine()) {
					players.add(new bingoBoard(scanNames.nextLine()));
				}
				scanNames.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < 5; i++) {
				players.get(i).newCard();
			}
			for (int i = 0; i < 5; i++) {
				players.get(i).printCard();
				System.out.println();
			}
			// create an arraylist with numbers from 1 to 75
			ArrayList<Integer> hopper = new ArrayList<Integer>();
			for (int i = 0; i < 75; i++) {
				hopper.add(i);
			}

			// call each number in a random order
			int calledI, called;
			String prefix = "";
			boolean winner = false;
			for (int i = 0; i < 75; i++) {
				calledI = (int) (Math.random() * hopper.size() - 1);
				called = hopper.get(calledI);
				// add the bingo letters
				if (called < 16)
					prefix = "B";
				else if (called < 33)
					prefix = "I";
				else if (called < 46)
					prefix = "N";
				else if (called < 61)
					prefix = "G";
				else
					prefix = "O";
				// mark the cards and print them out
				System.out.println(prefix + called);
				for (int j = 0; j < players.size() - 1; j++) {
					players.get(j).markCard(called);
					if (players.get(j).winnerFound()) {
						System.out.println("WINNER: " + players.get(j).getName());
						players.get(j).printCard();
						System.out.println();
						players.get(j).printMarks();
						winner = true;
						break;
					}
				}
				// remove the called number from the hopper
				if (winner)
					break;
				hopper.remove(calledI);
				// print the leader's name
				System.out.println("Leader: " + players.get(getMostMarks(players)).getName());
				players.get(getMostMarks(players)).printCard();
				System.out.println();
				players.get(getMostMarks(players)).printMarks();

			}

		}
	}
}
