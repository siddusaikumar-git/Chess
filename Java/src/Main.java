public class Main {
    public static void main(String[] args) {

    BoardLayout boardLayout = new BoardLayout();
    String[][] board = boardLayout.board;

//    Main.display(board);
    boardLayout.move(board, "a2", "a4");
    Main.display(board);

    }

    public static void display(String[][] board){
        for(String[] row: board){
            for(String val: row){
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}