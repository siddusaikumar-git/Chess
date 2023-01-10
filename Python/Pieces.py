"""
br1, br2, wr1, wr2 : rook(elephant)
bk1, bk2, wk1, wk2: knight(horse)
bb1, bb2, wb1, wb2: bishop(camel)
bq, wq: queen
bk, wk: king
bp0 ... bp7, wp0...wp7: pawn(soldier)
"""


def queen_switch(x_pos, y_pos, new_pos, direction):
    if direction == "right":
        return x_pos, y_pos + new_pos
    elif direction == "left":
        return x_pos, y_pos - new_pos
    elif direction == "top":
        return x_pos - new_pos, y_pos
    elif direction == "bottom":
        return x_pos + new_pos, y_pos
    elif direction == "right-top":
        return x_pos - new_pos, y_pos + new_pos
    elif direction == "left-top":
        return x_pos - new_pos, y_pos - new_pos
    elif direction == "right-bottom":
        return x_pos + new_pos, y_pos + new_pos
    elif direction == "left-bottom":
        return x_pos + new_pos, y_pos - new_pos


"""
   left-one-up-two == up-two-left-one  
   left-two-up-one == up-one-left-two
   left-one-down-two == down-two-left-one
   left-two-down-one == down-one-left-two
   right-one-down-two == down-two-right-one
   right-two-down-one == down-one-right-two
   right-one-up-two == up-two-right-one
   right-two-up-one == up-one-right-two
"""


def knight_switch(x_pos, y_pos, direction):
    if direction == "left-one-up-two":
        return x_pos - 1, y_pos - 2
    elif direction == "left-two-up-one":
        return x_pos - 2, y_pos - 1
    elif direction == "left-one-down-two":
        return x_pos - 1, y_pos + 2
    elif direction == "left-two-down-one":
        return x_pos - 2, y_pos + 1
    elif direction == "right-one-down-two":
        return x_pos + 1, y_pos - 2
    elif direction == "right-two-down-one":
        return x_pos + 2, y_pos - 1
    elif direction == "right-one-up-two":
        return x_pos + 1, y_pos - 2
    elif direction == "right-two-up-one":
        return x_pos + 2, y_pos - 1


"""
    input: x, y positions of the king, opposite team color
    output: boolean whether two step of pawn possible or not.
"""


def pawn_two_step_check(board, x_pos, y_pos, opposite_team):
    if ((opposite_team == "b" and board[x_pos - 1][y_pos] == "." and board[x_pos - 2][y_pos] == ".") or
            (opposite_team == "w" and board[x_pos + 1][y_pos] == "." and board[x_pos + 2][y_pos] == ".")):
        return True
    return False


chessboard = [
    ["br1", "bk1", "bb1", "bq", "bk", "bb2", "bk1", "br2"],
    ["bp0", "bp1", "bp2", "bp3", "bp4", "bp5", "bp6", "bp7"],
    [".", ".", ".", ".", ".", ".", ".", "."],
    [".", ".", ".", ".", ".", ".", ".", "."],
    [".", ".", ".", ".", ".", ".", ".", "."],
    [".", ".", ".", ".", ".", ".", ".", "."],
    ["wp0", "wp1", "wp2", "wp3", "wp4", "wp5", "wp6", "wp7"],
    ["wr1", "wk1", "wb1", "wq", "wk", "wb2", "wk1", "wr2"]]


class SelectPieces:
    kingRange = ((0, 1), (0, -1), (1, 0), (-1, 0), (1, 1), (-1, -1), (1, -1), (-1, 1))
    queenRange = 8
    boardBoundary = (0, 7)
    queen_directions = ("right", "left", "top", "bottom", "right-top", "left-top", "right-bottom", "left-bottom")

    def __init__(self):

        self.kingRange = ((0, 1), (0, -1), (1, 0), (-1, 0), (1, 1), (-1, -1), (1, -1), (-1, 1))
        self.queenRange = 8
        self.boardBoundary = (0, 7)
        self.queen_directions = (
            "right", "left", "top", "bottom", "right-top", "left-top", "right-bottom", "left-bottom")

    """
        input: x, y positions of the king, opposite team color
        output: boolean result whether next position is valid or not 
    """

    def king_pos_check(self, board, new_x, new_y, opposite_team):

        if (self.boardBoundary[0] <= new_x <= self.boardBoundary[1] and
                self.boardBoundary[0] <= new_y <= self.boardBoundary[1]):
            if board[new_x][new_y] == "." or board[new_x][new_y][0] == opposite_team:
                return True
        return False

    """
        input: x, y positions of the king, opposite team color
        output: list of possible move positions of the king
    """

    def king_select(self, board, x_pos, y_pos, opposite_team):

        if len(board[x_pos][y_pos]) != 2 or board[x_pos][y_pos][1] != "k":
            return [None]
        # opposite_team = "b" if self.board[x_pos][y_pos][0] == "w" else "w"

        possible_king_positions = []

        for p_x, p_y in self.kingRange:
            new_x = p_x + x_pos
            new_y = p_y + y_pos

            if self.king_pos_check(board, new_x, new_y, opposite_team):
                possible_king_positions.append((new_x, new_y))

        return possible_king_positions

    def pos_check(self, board, x_pos, y_pos, opposite_team, direction):
        possible_queen_pos = []
        for new_pos in range(1, self.queenRange):

            new_x, new_y = queen_switch(x_pos, y_pos, new_pos, direction)
            # print(new_x, new_y, direction, new_pos, x_pos, y_pos)
            if (self.boardBoundary[0] <= new_x <= self.boardBoundary[1] and
                    self.boardBoundary[0] <= new_y <= self.boardBoundary[1]):
                if board[new_x][new_y] == ".":
                    possible_queen_pos.append((new_x, new_y))
                elif board[new_x][new_y][0] == opposite_team:
                    possible_queen_pos.append((new_x, new_y))
                    break
                else:
                    break
            else:
                break
        return possible_queen_pos

    """
        input: x, y positions of the king, opposite team color
        output: list of possible move positions of the queen 
    """

    def queen_select(self, board, x_pos, y_pos, opposite_team):

        if len(board[x_pos][y_pos]) != 2 or board[x_pos][y_pos][1] != "q":
            return [None]

        possible_queen_positions = []

        # queen_directions = ("right", "left", "top", "bottom", "right-top", "left-top", "right-bottom", "left-bottom")

        for direction in self.queen_directions:
            possible_queen_positions.extend(self.pos_check(board, x_pos, y_pos, opposite_team, direction))

        return possible_queen_positions

    """
        input: x, y positions of the king, opposite team color
        output: list of possible move positions of the bishop 
    """

    def bishop_select(self, board, x_pos, y_pos, opposite_team):

        if len(board[x_pos][y_pos]) != 3 or board[x_pos][y_pos][1] != "b":
            return [None]

        possible_bishop_positions = []

        bishop_directions = self.queen_directions[4:]

        for direction in bishop_directions:
            possible_bishop_positions.extend(self.pos_check(board, x_pos, y_pos, opposite_team, direction))

        return possible_bishop_positions

    """
        input: x, y positions of the king, opposite team color
        output: list of possible move positions of the rook 
    """

    def rook_select(self, board, x_pos, y_pos, opposite_team):

        if len(board[x_pos][y_pos]) != 3 or board[x_pos][y_pos][1] != "r":
            return [None]

        possible_rook_positions = []

        rook_directions = self.queen_directions[:4]

        for direction in rook_directions:
            possible_rook_positions.extend(self.pos_check(board, x_pos, y_pos, opposite_team, direction))

        return possible_rook_positions

    """
        input: x, y positions of the king, opposite team color
        output: list of possible move positions of the pawn in next step
    """

    def pawn_pos_check(self, board, x_pos, y_pos, opposite_team):

        possible_pawn_pos = []
        directions = []
        if opposite_team == "b":
            directions = ("top", "left-top", "right-top")
        elif opposite_team == "w":
            directions = ("bottom", "left-bottom", "right-bottom")

        for direction in directions:
            new_x, new_y = queen_switch(x_pos, y_pos, 1, direction)
            if (self.boardBoundary[0] <= new_x <= self.boardBoundary[1] and
                    self.boardBoundary[0] <= new_y <= self.boardBoundary[1]):
                if direction in {"top", "bottom"}:
                    if board[new_x][new_y] == ".":
                        possible_pawn_pos.append((new_x, new_y))
                else:
                    if board[new_x][new_y][0] == opposite_team:
                        possible_pawn_pos.append((new_x, new_y))

        return possible_pawn_pos

    """
        input: x, y positions of the king, opposite team color
        output: list of possible move positions of the pawn 
    """

    def pawn_select(self, board, x_pos, y_pos, opposite_team):

        if len(board[x_pos][y_pos]) != 3 or board[x_pos][y_pos][1] != "p":
            return [None]

        possible_pawn_positions = []

        if x_pos == 6 and pawn_two_step_check(board, x_pos, y_pos, opposite_team):
            possible_pawn_positions.append((x_pos - 2, y_pos))

        if x_pos == 1 and pawn_two_step_check(board, x_pos, y_pos, opposite_team):
            possible_pawn_positions.append((x_pos + 2, y_pos))

        possible_pawn_positions.extend(self.pawn_pos_check(board, x_pos, y_pos, opposite_team))

        return possible_pawn_positions

    """
       input: x, y positions of the king, opposite team color
       output: list of possible move positions of the pawn 
    """

    def knight_select(self, board, x_pos, y_pos, opposite_team):

        if len(board[x_pos][y_pos]) != 3 or board[x_pos][y_pos][1] != "k":
            return [None]

        possible_bishop_positions = []

        directions = ["left-one-up-two", "left-two-up-one", "left-one-down-two", "left-two-down-one",
                      "right-one-down-two", "right-two-down-one", "right-one-up-two", "right-two-up-one"]

        for direction in directions:
            new_x, new_y = knight_switch(x_pos, y_pos, direction)
            if (self.boardBoundary[0] <= new_x <= self.boardBoundary[1] and
                    self.boardBoundary[0] <= new_y <= self.boardBoundary[1]):
                if board[new_x][new_y] == "." or board[new_x][new_y][0] == opposite_team:
                    possible_bishop_positions.append((new_x, new_y))

        return possible_bishop_positions

