from tabulate import tabulate
from Chess import Chess


def display_chess(chessObj, source, destination):
    chessObj.move(source, destination)
    headers = ["a", "b", "c", "d", "e", "f", "g", "h"]
    rowIDs = [8, 7, 6, 5, 4, 3, 2, 1]
    print(tabulate(chessObj.start(), headers=headers, tablefmt="fancy_grid", showindex=rowIDs))


chess = Chess()
print(tabulate(chess.start(), headers=["a", "b", "c", "d", "e", "f", "g", "h"],
               tablefmt="fancy_grid", showindex=[8, 7, 6, 5, 4, 3, 2, 1]))
display_chess(chess, "e2", "e4")
display_chess(chess, "d2", "d4")
display_chess(chess, "d7", "d5")
display_chess(chess, "d5", "e4")

