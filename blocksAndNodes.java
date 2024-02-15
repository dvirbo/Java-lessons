/*
 * class block represent a cell in the matrix
 * each bloch has number, color and cost
 */


class Block {

    int num;
    String color; // red or white
    int cost;
    int moves; // how much moves the block can made
    int[] mat = new int[2];

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

    
    public boolean equals(Block [] other) {
        for (int i = 0; i < other.length; i++) {
            if (num != other[i].num) {
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

/*
 * class Node represent a level in the game tree.
 */
class Node {
    Block[] blocks = new Block[Ex1.col * Ex1.row];
    int cost; // cost of al the moves
    char father = 'X'; // the move of the "father" (L/R/U/D)
    int f;
    String mark; //
    String path = null;
    public static int col = Ex1.col;
    public static int row = Ex1.row;

    public Node() {

    }


    public Node(Block[] arr) {

        this.blocks = arr;
        this.path = null;
        this.cost = 0;

    }

    public Node(Node other) {
        this.blocks = other.blocks;
        this.cost = other.cost;
        this.f = other.f;
        this.mark = other.mark;
        this.father = other.father;
        this.path = other.path;
    }

    /*
     * Each block in the array has a variable that holds
     * the position of that block in the matrix.
     * Help us find distances in the heuristic function
     */
    public static void initMat(Node e) {
        int k = 0;
        for (int i = 0; i < Ex1.row; i++) {
            for (int j = 0; j < Ex1.col; j++) {

                e.blocks[k].mat[0] = i;
                e.blocks[k].mat[1] = j;
                k++;
            }
        }
    }



    @Override
    public String toString() {
        String ans = "[";
        for (int i = 0; i < this.blocks.length; i++) {
            ans += this.blocks[i].num + " ";
        }
        ans += "]\n";
        return ans;
    }

}
