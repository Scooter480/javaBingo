import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//driver class
public class Main {

	private static void help() {
		System.out.println("This software is licenced under the GNU GPL v3 and comes with absolutely NO WARRANTY");
		System.out.println(
				"javaBingo simulates bingo games with randomly generated boards and numbers. The names of the players are stored in names.txt");
		System.out.println("ARGUMENTS:");
		System.out.println("-h: display this help screen");
		System.out.println("-s: skip the pause between each round");
	}

	// check which player has the highest number of marks on their board
	private static int getMostMarks(ArrayList<card> players) {
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

	private static int callNumber(ArrayList<Integer> hopper) {
		// call each number in a random order
		int calledI, called;
		String prefix = "";
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

		System.out.println(prefix + called);
		return calledI;
	}

	public static void main(String[] args) {
		if (args.length > 0 && (args[0].equals("-h") || args[0].equals("--help"))) {
			help();
		} else {
			// create players from file of names
			File nameFile = new File("names.txt");
			ArrayList<card> players = new ArrayList<card>();
			try {
				Scanner scanNames = new Scanner(nameFile);
				while (scanNames.hasNextLine()) {
					players.add(new card(scanNames.nextLine()));
				}
				scanNames.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// create an arraylist with numbers from 1 to 75
			ArrayList<Integer> hopper = new ArrayList<Integer>();
			for (int i = 0; i < 75; i++) {
				hopper.add(i);
			}
			boolean winner = false;
			int calledI, called;
			for (int i = 0; i < 75; i++) {
				calledI = callNumber(hopper);
				called = hopper.get(calledI);
				// mark the cards and print them out
				for (int j = 0; j < players.size() - 1; j++) {
					players.get(j).markCard(called);
					if (players.get(j).winnerFound()) {
						System.out.println("WINNER: " + players.get(j).getName());
						players.get(j).printCard();
						winner = true;
						break;
					}
				}
				// remove the called number from the hopper
				hopper.remove(calledI);
				if (winner)
					break;
				// print the leader's name
				System.out.println("Leader: " + players.get(getMostMarks(players)).getName());
				players.get(getMostMarks(players)).printCard();
				if (args.length <= 0 || !(args[0].equals("-s"))) {
					try {
						System.out.println("Press enter to continue");
						System.in.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}
}
