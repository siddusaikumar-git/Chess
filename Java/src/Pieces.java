import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pieces {

    public int[] queen_switch(int x_pos, int y_pos, int new_pos, String direction){
        int[] array = new int[2];

        switch (direction){
            case "right":
                array[0] = x_pos;
                array[1]= y_pos + new_pos;
                break;
            case "left":
                array[0] = x_pos;
                array[1]= y_pos - new_pos;
                break;
            case "top":
                array[0] = x_pos - new_pos;
                array[1]= y_pos;
                break;
            case "bottom":
                array[0] = x_pos + new_pos;
                array[1]= y_pos;
                break;
            case "right-top":
                array[0] = x_pos - new_pos;
                array[1]= y_pos + new_pos;
                break;
            case "left-top":
                array[0] = x_pos - new_pos;
                array[1]= y_pos - new_pos;
                break;
            case "right-bottom":
                array[0] = x_pos + new_pos;
                array[1]= y_pos + new_pos;
                break;
            case "left-bottom":
                array[0] = x_pos + new_pos;
                array[1]= y_pos - new_pos;
                break;
            default:
                array[0] = -1;
                array[1]= -1;
                break;
        }
        return array;
    }

    public int[] knight_switch(int x_pos, int y_pos, String direction){
        int[] array = new int[2];

        switch (direction) {
            case "left-one-up-two":
                array[0] = x_pos - 2;
                array[1] = y_pos - 1;
                break;
            case "left-two-up-one":
                array[0] = x_pos - 1;
                array[1] = y_pos - 2;
                break;
            case "left-one-down-two":
                array[0] = x_pos + 2;
                array[1] = y_pos - 1;
                break;
            case "left-two-down-one":
                array[0] = x_pos + 1;
                array[1] = y_pos - 2;
                break;
            case "right-one-down-two":
                array[0] = x_pos + 2;
                array[1] = y_pos + 1;
                break;
            case "right-two-down-one":
                array[0] = x_pos + 1;
                array[1] = y_pos + 2;
                break;
            case "right-one-up-two":
                array[0] = x_pos - 2;
                array[1] = y_pos + 1;
                break;
            case "right-two-up-one":
                array[0] = x_pos - 1;
                array[1] = y_pos + 2;
                break;
            default:
                array[0] = -1;
                array[1] = -1;
                break;
        }
        return array;
    }

    public boolean pawn_two_step_check(String[][] board, int x_pos, int y_pos, String opposite_team){
        return (opposite_team.equals("b") && board[x_pos - 1][y_pos].equals(".") && board[x_pos - 2][y_pos].equals(".")) ||
                (opposite_team.equals("w") && board[x_pos + 1][y_pos].equals(".") && board[x_pos + 2][y_pos].equals("."));
    }

    private final int[][] kingRange = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    private final int[] boardBoundary = {0, 7};
    private final String[] queen_directions = {"right", "left", "top", "bottom", "right-top", "left-top", "right-bottom", "left-bottom"};
    private final String[] knight_directions = {"left-one-up-two", "left-two-up-one", "left-one-down-two", "left-two-down-one",
                                                "right-one-down-two", "right-two-down-one", "right-one-up-two", "right-two-up-one"};

    public boolean king_pos_check(String[][] board, int new_x, int new_y, String opposite_team){

        if(((boardBoundary[0] <= new_x)  && (new_x <= boardBoundary[1])) && ((boardBoundary[0] <= new_y) && (new_y <= boardBoundary[1]))){
            return (board[new_x][new_y].equals(".")) || (board[new_x][new_y].substring(0, 1).equals(opposite_team));
        }
        return false;
    }

    public List<List<Integer>> king_select(String[][] board, int x_pos, int y_pos, String opposite_team){
        if((board[x_pos][y_pos].length() != 2) || (board[x_pos][y_pos].charAt(1) != 'k')){
            return null;
        }

        List<List<Integer>> possible_king_positions = new ArrayList<>();

        for(int[] position:kingRange){
            int new_x = position[0] + x_pos;
            int new_y = position[1] + y_pos;
            if(king_pos_check(board, new_x, new_y,opposite_team)){
                possible_king_positions.add(Arrays.asList(new_x, new_y));
            }
        }
        return possible_king_positions;
    }

    public List<List<Integer>> pos_check(String[][] board, int x_pos, int y_pos, String opposite_team, String direction) {
        List<List<Integer>> possible_queen_pos = new ArrayList<>();

        int queenRange = 8;
        for (int new_pos = 1; new_pos < queenRange; new_pos++) {
            int new_x, new_y;
            int[] array = queen_switch(x_pos, y_pos, new_pos, direction);
            new_x = array[0];
            new_y = array[1];

            if (((boardBoundary[0] <= new_x) && (new_x <= boardBoundary[1])) && ((boardBoundary[0] <= new_y) && (new_y <= boardBoundary[1]))) {
                if (board[new_x][new_y].equals(".")) {
                    possible_queen_pos.add(Arrays.asList(new_x, new_y));
                } else if (board[new_x][new_y].substring(0, 1).equals(opposite_team)) {
                    possible_queen_pos.add(Arrays.asList(new_x, new_y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return possible_queen_pos;
    }

    public List<List<Integer>> queen_select(String[][] board, int x_pos, int y_pos, String opposite_team){

        if((board[x_pos][y_pos].length() != 3) || (board[x_pos][y_pos].charAt(1) != 'q')){
            return null;
        }

        List<List<Integer>> possible_queen_positions = new ArrayList<>();

        for(String direction: queen_directions){
            List<List<Integer>> positions = pos_check(board, x_pos, y_pos, opposite_team, direction);
            possible_queen_positions.addAll(positions);
        }

        return possible_queen_positions;
    }

    public List<List<Integer>> bishop_select(String[][] board, int x_pos, int y_pos, String opposite_team){
        if((board[x_pos][y_pos].length() != 3) || (board[x_pos][y_pos].charAt(1) != 'b')){
            return null;
        }

        List<List<Integer>> possible_bishop_positions = new ArrayList<>();

        for(int index = 4; index < queen_directions.length; index++){
            String direction = queen_directions[index];
            possible_bishop_positions.addAll(pos_check(board, x_pos, y_pos, opposite_team, direction));
        }

        return possible_bishop_positions;
    }

    public List<List<Integer>> rook_select(String[][] board, int x_pos, int y_pos, String opposite_team){
        if((board[x_pos][y_pos].length() != 3) || (board[x_pos][y_pos].charAt(1) != 'r')){
            return null;
        }
        List<List<Integer>> possible_rook_positions = new ArrayList<>();

        for(int index = 0; index < 4; index++){
            String direction = queen_directions[index];
            possible_rook_positions.addAll(pos_check(board, x_pos, y_pos, opposite_team, direction));
        }

        return possible_rook_positions;
    }

    public List<List<Integer>> pawn_pos_check(String[][] board, int x_pos, int y_pos, String opposite_team){
        List<List<Integer>> possible_pawn_pos = new ArrayList<>();
        List<String> directions = new ArrayList<>();

        if(opposite_team.equals("b")){
            directions.addAll(Arrays.asList("top", "left-top", "right-top"));
        }else if(opposite_team.equals("w")){
            directions.addAll(Arrays.asList("bottom", "left-bottom", "right-bottom"));
        }

        for(String direction: directions){
            int[] array = queen_switch(x_pos, y_pos, 1, direction);
            int new_x = array[0];
            int new_y = array[1];

            if(((boardBoundary[0] <= new_x)  && (new_x <= boardBoundary[1])) && ((boardBoundary[0] <= new_y) && (new_y <= boardBoundary[1]))){

                if(direction.equals("top") || direction.equals("bottom")){
                    if(board[x_pos][y_pos].charAt(0) == '.'){
                        possible_pawn_pos.add(Arrays.asList(new_x, new_y));
                    }
                }else{
                    if(board[x_pos][y_pos].substring(0, 1).equals(opposite_team)){
                        possible_pawn_pos.add(Arrays.asList(new_x, new_y));
                    }
                }
            }
        }
        return possible_pawn_pos;
    }

    public List<List<Integer>> pawn_select(String[][] board, int x_pos, int y_pos, String opposite_team){
        if((board[x_pos][y_pos].length() != 3) || (board[x_pos][y_pos].charAt(1) != 'p')){
            return null;
        }

        List<List<Integer>> possible_pawn_positions = new ArrayList<>();

        if(x_pos == 6 && pawn_two_step_check(board, x_pos, y_pos, opposite_team)){
            possible_pawn_positions.add(Arrays.asList(x_pos - 2, y_pos));
        }
        else if(x_pos == 1 && pawn_two_step_check(board, x_pos, y_pos, opposite_team)){
            possible_pawn_positions.add(Arrays.asList(x_pos + 2, y_pos));
        }

        possible_pawn_positions.addAll(pawn_pos_check(board, x_pos, y_pos, opposite_team));

        return possible_pawn_positions;
    }

    public List<List<Integer>> knight_select(String[][] board, int x_pos, int y_pos, String opposite_team){
        if((board[x_pos][y_pos].length() != 3) || (board[x_pos][y_pos].charAt(1) != 'r')){
            return null;
        }

        List<List<Integer>> possible_knight_positions = new ArrayList<>();

        for(String direction: knight_directions){
            int[] array = knight_switch(x_pos, y_pos, direction);
            int new_x = array[0];
            int new_y = array[1];

            if(((boardBoundary[0] <= new_x)  && (new_x <= boardBoundary[1])) && ((boardBoundary[0] <= new_y) && (new_y <= boardBoundary[1]))){

                if ((board[new_x][new_y].equals(".")) || (board[x_pos][y_pos].substring(0, 1).equals(opposite_team))){
                    possible_knight_positions.add(Arrays.asList(new_x, new_y));
                }
            }
        }
        return possible_knight_positions;
    }
}
