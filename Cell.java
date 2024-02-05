public class Cell {
    private int number;
    private String color;
    private int moves;

    public Cell(int number, String color, int moves) {
        this.number = number;
        this.color = color;
        this.moves = moves;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
}
