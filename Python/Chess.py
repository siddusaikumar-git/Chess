from Pieces import SelectPieces


def get_indices_to_position(x_pos, y_pos):
    return chr(y_pos), 8 - x_pos


def get_position_to_indices(char1, char2):
    return 8 - int(char2),  ord(char1) - 97


def opposite_team_color(current_team):
    if current_team == "w":
        return "b"
    else:
        return "w"


class Chess:

    def __init__(self):
        self.board = [
            ["br1", "bk1", "bb1", "bq", "bk", "bb2", "bk1", "br2"],
            ["bp0", "bp1", "bp2", "bp3", "bp4", "bp5", "bp6", "bp7"],
            [".", ".", ".", ".", ".", ".", ".", "."],
            [".", ".", ".", ".", ".", ".", ".", "."],
            [".", ".", ".", ".", ".", ".", ".", "."],
            [".", ".", ".", ".", ".", ".", ".", "."],
            ["wp0", "wp1", "wp2", "wp3", "wp4", "wp5", "wp6", "wp7"],
            ["wr1", "wk1", "wb1", "wq", "wk", "wb2", "wk1", "wr2"]]
        self.boardBoundary = (0, 7)

    def start(self):
        return self.board

    def piece_name(self, x_pos, y_pos):
        piece = self.board[x_pos][y_pos]
        length = len(piece)
        pname = piece[1]
        piece_mapper = {(3, "r"): "rook", (3, "k"): "knight", (3, "b"): "bishop",
                        (2, "q"): "queen", (2, "k"): "king", (3, "p"): "pawn"}
        if piece == ".":
            return None
        else:
            return piece_mapper[(length, pname)]

    def possible_next_positions(self, position):
        next_moves = []
        x_pos, y_pos = get_position_to_indices(position[0], position[1])
        if len(self.board[x_pos][y_pos]) < 2 or len(self.board[x_pos][y_pos]) > 3:
            return []
        opposite_team = opposite_team_color(self.board[x_pos][y_pos][0])
        name = self.piece_name(x_pos, y_pos)
        sp = SelectPieces()

        if name == "rook":
            next_moves.extend(sp.rook_select(board=self.board, x_pos=x_pos, y_pos=y_pos, opposite_team=opposite_team))
        elif name == "knight":
            next_moves.extend(sp.knight_select(board=self.board, x_pos=x_pos, y_pos=y_pos, opposite_team=opposite_team))
        elif name == "bishop":
            next_moves.extend(sp.bishop_select(board=self.board, x_pos=x_pos, y_pos=y_pos, opposite_team=opposite_team))
        elif name == "queen":
            next_moves.extend(sp.queen_select(board=self.board, x_pos=x_pos, y_pos=y_pos, opposite_team=opposite_team))
        elif name == "king":
            next_moves.extend(sp.king_select(board=self.board, x_pos=x_pos, y_pos=y_pos, opposite_team=opposite_team))
        elif name == "pawn":
            next_moves.extend(sp.pawn_select(board=self.board, x_pos=x_pos, y_pos=y_pos, opposite_team=opposite_team))
        return next_moves

    def boundary_check(self, x_pos, y_pos):
        if (self.boardBoundary[0] <= x_pos <= self.boardBoundary[1] and
                self.boardBoundary[0] <= y_pos <= self.boardBoundary[1]):
            return True
        return False

    def move(self, source, destination):

        src_x, src_y = get_position_to_indices(source[0], source[1])
        dst_x, dst_y = get_position_to_indices(destination[0], destination[1])
        print(src_x, src_y, dst_x, dst_y)
        if self.boundary_check(src_x, src_y) and self.boundary_check(dst_x, dst_y):
            if (dst_x, dst_y) in self.possible_next_positions(source):

                self.board[dst_x][dst_y] = self.board[src_x][src_y]
                self.board[src_x][src_y] = "."
            else:
                print("unable to move as position is out of range")
        else:
            print("provided position is out of the board range")

