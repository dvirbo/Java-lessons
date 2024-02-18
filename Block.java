/*
 * class block represent a block in the matrix
 * each bloch has number, color and cost
 */
class Block {

    int num;
    String color; // red or white
    int cost;
    int moves; // how much moves the block can made
    int[] coordinates = new int[2];

    Block(int num) {
        this.num = num;
        this.color = "Red"; // the default color
        this.cost = 30;

    }

    // constructor for the Red cells
    Block(int num, String color) {
        this.num = num;
        this.color = color;
        this.cost = 30;

    }

    // constructor for the white cells
    Block(int num, String color, int moves) {
        this.num = num;
        this.color = color;
        this.moves = moves;

    }

    public boolean equals(Block[] other) {
        for (int i = 0; i < other.length; i++) {
            if (num != other[i].num) {
                return false;
            }
        }
        return true;
    }

    public static boolean CompareBlocks(Block[] current, Block[] goal) {
        for (int i = 0; i < goal.length; i++) {
            if (current[i].num != goal[i].num) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "num: " + num + " color: " + color;
    }
}


