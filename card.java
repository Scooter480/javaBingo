import java.util.ArrayList;

//bingo card
public class card{
	// attributes
	private String name;
	private int[][] numbers;
	private boolean[][] marks;

	// constructor
	public card(String name) {
		this.name = name;
		this.numbers = generateCard();
		this.marks = new boolean[5][5];
		for (int rows = 0; rows < 5; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				this.marks[rows][columns] = false;
			}
		}
		this.marks[2][2] = true;
	}

	// set the card to a newly generated card
	public void newCard() {
		this.numbers = generateCard();
	}

	// return the name of the player
	public String getName() {
		return this.name;
	}

	// print the bingo card with numbers
	public void printCard() {
		// System.out.println(this.name + ":");
		char[] bingo = { 'B', 'I', 'N', 'G', 'O' };
		System.out.print("|");
		for (int i = 0; i < 5; i++)
			System.out.print("\t" + bingo[i] + "\t|");
		System.out.println();
		for (int rows = 0; rows < 5; rows++) {
			System.out.print("|");
			for (int columns = 0; columns < 5; columns++) {
				System.out.print("\t" + numbers[rows][columns] + "\t|");
			}
			System.out.println();
		}
	}

	// print the bingo card with marks
	public void printMarks() {
		// System.out.println(this.name + ":");
		char[] bingo = { 'B', 'I', 'N', 'G', 'O' };
		System.out.print("|");
		for (int i = 0; i < 5; i++)
			System.out.print("\t" + bingo[i] + "\t|");
		System.out.println();
		for (int rows = 0; rows < 5; rows++) {
			System.out.print("|");
			for (int columns = 0; columns < 5; columns++) {
				if (marks[rows][columns] == true) {
					System.out.print("\t" + "X" + "\t|");
				} else {
					System.out.print("\t" + " " + "\t|");
				}

			}
			System.out.println();
		}
	}

	// mark the bingo card if the number called is on the card
	public void markCard(int called) {
		for (int rows = 0; rows < 5; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				if (numbers[rows][columns] == called) {
					marks[rows][columns] = true;
				}
			}
		}
	}

	// return the number of marks on a card
	public int numOfMarks() {
		int count = 0;
		for (int rows = 0; rows < 5; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				if (marks[rows][columns] == true) {
					count++;
				}
			}
		}
		return count;
	}

	public boolean winnerFound() {
		ArrayList<Boolean> trueing = new ArrayList<>();
		// check for win accross rows
		for (int rows = 0; rows < 5; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				if (marks[rows][columns] == true)
					trueing.add(true);
				else
					trueing.add(false);
			}
			if (!(trueing.contains(false))) {
				return true;
			}
			trueing.clear();
		}
		trueing.clear();
		// check for win accross columns
		for (int columns = 0; columns < 5; columns++) {
			for (int rows = 0; rows < 5; rows++) {
				if (marks[rows][columns] == true)
					trueing.add(true);
				else
					trueing.add(false);
			}
			if (!(trueing.contains(false))) {
				return true;
			}
			trueing.clear();
		}

		trueing.clear();
		// check for win accross LTR diagonal
		for (int diagonal = 0; diagonal < 5; diagonal++) {
			if (marks[diagonal][diagonal] == true)
				trueing.add(true);
			else
				trueing.add(false);
		}
		if (!(trueing.contains(false))) {
			return true;
		}
		trueing.clear();
		// check for win RTL diagonal
		for (int diagonal = 0; diagonal < 5; diagonal++) {
			if (marks[4 - diagonal][diagonal] == true)
				trueing.add(true);
			else
				trueing.add(false);
		}
		if (!(trueing.contains(false))) {
			return true;
		}

		trueing.clear();
		// return false if all win checks fail
		return false;

	}

	// generate a new card
	private int[][] generateCard() {
		int[][] numbers = new int[5][5];
		for (int rows = 0; rows < 5; rows++) {
			for (int columns = 0; columns < 5; columns++) {
				numbers[rows][columns] = (int) (Math.random() * 15) + 15 * columns;
				// reroll the random number if it exists on the card to avoid duplicates
				for (int i = 0; i < rows; i++) {
					if (numbers[rows][columns] == numbers[i][columns]) {
						numbers[rows][columns] = (int) (Math.random() * 15) + 15 * columns;
						i = -1;
					}
				}
			}
		}
		return numbers;
	}

}
