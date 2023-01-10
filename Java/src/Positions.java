import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class Positions extends Selection{

    private final int[] boardBoundary = {0, 7};
    public int black_pawn_to_queen = 2;
    public int white_pawn_to_queen = 2;
    public int black_pawn_to_knight = 3;
    public int white_pawn_to_knight = 3;
    public int black_pawn_to_bishop = 3;
    public int white_pawn_to_bishop = 3;
    public int black_pawn_to_rook = 3;
    public int white_pawn_to_rook = 3;

    public int[] get_position_to_indices(String position){
        return new int[]{8 - (position.charAt(1) - '0'), position.charAt(0) - 'a'};
    }

    public String opposite_team_color(String current_team){
        String opposite_team;

        if(current_team.equals("w")){
            opposite_team = "b";
        }else{
            opposite_team = "w";
        }
        return opposite_team;
    }

    public String piece_name(String[][] board, int x_pos, int y_pos){
        String piece = board[x_pos][y_pos];
        int length = piece.length();
        char pIdentifier = piece.charAt(1);
        String pname = "";
        String switchInput = "" + length + pIdentifier;
        System.out.println("Inside piece name..." + switchInput);
        switch (switchInput){
            case "3r":
                pname = "rook";
                break;
            case "3k":
                pname = "knight";
                break;
            case "3b":
                pname = "bishop";
                break;
            case "3q":
                pname = "queen";
                break;
            case "2k":
                pname = "king";
                break;
            case "3p":
                pname = "pawn";
                break;
            default:
                break;
        }
        return pname;
    }

    public List<List<Integer>> possible_next_positions(String[][] board, String position){
        List<List<Integer>> next_moves = new ArrayList<>();

        int[] array = get_position_to_indices(position);
        int x_pos = array[0];
        int y_pos = array[1];

        if(board[x_pos][y_pos].length() < 2 || board[x_pos][y_pos].length() > 3){
            return null;
        }

        String opposite_team = opposite_team_color(board[x_pos][y_pos].substring(0, 1));
        String name = piece_name(board, x_pos, y_pos);
        System.out.println("I am here...." + name);
        switch (name){
            case "rook":
                next_moves.addAll(rook_select(board, x_pos, y_pos, opposite_team));
                break;
            case "knight":
                next_moves.addAll(knight_select(board, x_pos, y_pos, opposite_team));
                break;
            case "bishop":
                next_moves.addAll(bishop_select(board, x_pos, y_pos, opposite_team));
                break;
            case "queen":
                next_moves.addAll(queen_select(board, x_pos, y_pos, opposite_team));
                break;
            case "king":
                next_moves.addAll(king_select(board, x_pos, y_pos, opposite_team));
                break;
            case "pawn":

                List<List<Integer>> output = pawn_select(board, x_pos, y_pos, opposite_team);
                System.out.println(output.toString());
                next_moves.addAll(output);
//                next_moves.addAll(pawn_select(board, x_pos, y_pos, opposite_team));

                break;
            default:
                break;
        }
        return next_moves;
    }

    public boolean boundary_check(int x_pos, int y_pos){

        return ((boardBoundary[0] <= x_pos) && (x_pos <= boardBoundary[1])) && ((boardBoundary[0] <= y_pos) && (y_pos <= boardBoundary[1]));
    }

    public boolean pawn_to_power_check(String[][] board, int src_x, int src_y, int dst_x){
        return (board[src_x][src_y].startsWith("wp") && src_x == 1 && dst_x == 0) ||
                (board[src_x][src_y].startsWith("bp") && src_x == 6 && dst_x == 7);
    }

    public String pawn_to_power(String current_team){
        String output = "";
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the number for power among these 4 -> 1.Queen, 2.Knight, 3.Bishop, 4.Rook");
        int power = reader.nextInt();

        switch (power){
            case 1:
                if(current_team.equals("b")){
                    output = valueOf("bq" +  (char)black_pawn_to_queen);
                    black_pawn_to_queen += 1;
                }else{
                    output = valueOf("wq" +  (char)white_pawn_to_queen);
                    white_pawn_to_queen += 1;
                }
                break;
            case 2:
                if(current_team.equals("b")){
                    output = valueOf("bk" +  (char)black_pawn_to_knight);
                    black_pawn_to_knight += 1;
                }else{
                    output = valueOf("wk" +  (char)white_pawn_to_knight);
                    white_pawn_to_knight += 1;
                }
                break;
            case 3:
                if(current_team.equals("b")){
                    output = valueOf("bb" +  (char)black_pawn_to_bishop);
                    black_pawn_to_bishop += 1;
                }else{
                    output = valueOf("wb" +  (char)white_pawn_to_bishop);
                    white_pawn_to_bishop += 1;
                }
                break;
            case 4:
                if(current_team.equals("b")){
                    output = valueOf("br" +  (char)black_pawn_to_rook);
                    black_pawn_to_rook += 1;
                }else{
                    output = valueOf("wr" +  (char)white_pawn_to_rook);
                    white_pawn_to_rook += 1;
                }
                break;
            default:
                System.out.println("please select the appropriate input ...");
                output = pawn_to_power(current_team);
                break;
        }

        return output;
    }

    public void move(String[][] board, String source, String destination){

        int[] sourceArray = get_position_to_indices(source);
        int[] destinationArray = get_position_to_indices(destination);
        int src_x = sourceArray[0];
        int src_y = sourceArray[1];
        int dst_x = destinationArray[0];
        int dst_y = destinationArray[1];
        System.out.println(src_x + " " + src_y +" " + dst_x +" " + dst_y);

        if(boundary_check(src_x, src_y) && boundary_check(dst_x, dst_y)){
            if(possible_next_positions(board, source).contains(Arrays.asList(dst_x, dst_y))){
                if(pawn_to_power_check(board, src_x, src_y, dst_x)){
                    board[dst_x][dst_y] = pawn_to_power(board[src_x][src_y].substring(0, 1));
                }else{
                    board[dst_x][dst_y] = board[src_x][src_y];
                }
                board[src_x][src_y] = ".";
            }else{
                System.out.println("unable to move as position is out of range");
            }
        }else{
            System.out.println("provided position is out of the board range");
        }
    }
}
