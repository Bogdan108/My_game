
public class Playground {
    Playground() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                ground[i][j] = GroundColor.E;
            }
        }
        ground[3][3] = GroundColor.W;
        ground[4][3] = GroundColor.B;
        ground[4][4] = GroundColor.W;
        ground[3][4] = GroundColor.B;
    }

    private void reverseCage(int i, int j, int lineX, int lineY, GroundColor id) {
        int tempX = i;
        int tempY = j;
        int coutOpposite = 0;
        while (tempX >= 0 && tempX <= 7 && tempY >= 0 && tempY <= 7) {
            if (lineX == 0) {
                i = -10;
            }
            if (lineY == 0) {
                j = -10;
            }
            tempX += lineX;
            tempY += lineY;
            if (tempX < 0 || tempX > 7 || tempY < 0 || tempY > 7) {
                break;
            }
            if (ground[tempX][tempY] == id) {
                if (((-coutOpposite) * (lineX % 2) + tempX + (-1) * (lineX % 2) == i || ((-coutOpposite) * (lineY % 2) + tempY + (-1) * (lineY % 2) == j)) && coutOpposite != 0) {
                    for (int k = 1; k <= coutOpposite; ++k) {
                        ground[tempX - lineX][tempY - lineY] = id;
                        tempX -= lineX;
                        tempY -= lineY;
                    }
                    return;
                }
            } else {
                if (ground[tempX][tempY] != GroundColor.E && ground[tempX][tempY] != id) {
                    ++coutOpposite;
                }
            }
        }
    }

    private void findCage(int i, int j, int lineX, int lineY, Boolean printer, GroundColor Color) {
        int tempX = i;
        int tempY = j;
        int coutOpposite = 0;
        while (tempX >= 0 && tempX <= 7 && tempY >= 0 && tempY <= 7) {
            if (lineX == 0) {
                i = -10;
            }
            if (lineY == 0) {
                j = -10;
            }
            tempX += lineX;
            tempY += lineY;
            if (tempX < 0 || tempX > 7 || tempY < 0 || tempY > 7) {
                break;
            }
            if (groundCanGo[tempX][tempY] == GroundColor.E || groundCanGo[tempX][tempY] == GroundColor.Y) {
                if (((-coutOpposite) * (lineX % 2) + tempX + (-1) * (lineX % 2) == i || ((-coutOpposite) * (lineY % 2) + tempY + (-1) * (lineY % 2) == j)) && coutOpposite != 0) {
                    groundCanGo[tempX][tempY] = GroundColor.Y;
                    if (printer) {
                        System.out.println("Вы можете походить на точку x = " + (tempX + 1) + " y = " + (tempY + 1));
                    }
                    return;
                }
            } else {
                if (groundCanGo[tempX][tempY] != Color && groundCanGo[tempX][tempY] != GroundColor.Y && groundCanGo[tempX][tempY] != GroundColor.E) {
                    ++coutOpposite;
                }
            }
        }
    }

    public void changeCage(GroundColor id, int i, int j) {
        --i;
        --j;
        ground[i][j] = id;
        reverseCage(i, j, Direction.Left.number, 0, id);
        reverseCage(i, j, Direction.Right.number, 0, id);
        reverseCage(i, j, 0, Direction.Up.number, id);
        reverseCage(i, j, 0, Direction.Down.number, id);
        reverseCage(i, j, Direction.Left.number, Direction.Up.number, id);
        reverseCage(i, j, Direction.Left.number, Direction.Down.number, id);
        reverseCage(i, j, Direction.Right.number, Direction.Up.number, id);
        reverseCage(i, j, Direction.Right.number, Direction.Down.number, id);
    }

    public void canGo(Boolean printer, GroundColor Color) {
        // копирую исходный массив для отображения следующих ходов
        for (int i = 0; i < 8; ++i) {
            System.arraycopy(ground[i], 0, groundCanGo[i], 0, 8);
        }
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (groundCanGo[i][j] == Color) {
                    findCage(i, j, Direction.Up.number, 0, printer, Color);
                    findCage(i, j, Direction.Down.number, 0, printer, Color);
                    findCage(i, j, 0, Direction.Left.number, printer, Color);
                    findCage(i, j, 0, Direction.Right.number, printer, Color);
                    findCage(i, j, Direction.Up.number, Direction.Right.number, printer, Color);
                    findCage(i, j, Direction.Up.number, Direction.Left.number, printer, Color);
                    findCage(i, j, Direction.Down.number, Direction.Right.number, printer, Color);
                    findCage(i, j, Direction.Down.number, Direction.Left.number, printer, Color);
                }
            }
        }
    }

    int scoringPoints(GroundColor id) {
        int counter = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (ground[i][j] == id) {
                    ++counter;
                }
            }
        }
        return counter;
    }

    boolean checkPlayground() {
        int counter = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (ground[i][j] == GroundColor.E) {
                    ++counter;
                }
            }
        }
        return counter != 0;
    }
    boolean checkPlaygroundCanGo() {
        int counter = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (groundCanGo[i][j] == GroundColor.Y) {
                    ++counter;
                }
            }
        }
        return counter != 0;
    }

    void savePlayground() {
        for (int i = 0; i < 8; ++i) {
            System.arraycopy(ground[i], 0, groundBack[i], 0, 8);
        }
    }

    void backPlayground() {
        for (int i = 0; i < 8; ++i) {
            System.arraycopy(groundBack[i], 0, ground[i], 0, 8);
        }
    }

    void printGroundActual() {
        printGround(ground);
    }

    void printGroundCanGo() {
        System.out.println("Вы можете походить такими способами:");
        printGround(groundCanGo);
    }

    private void printGround(GroundColor[][] temp) {
        System.out.print("  ");
        for (int k = 1; k < 9; ++k) {
            System.out.print(k);
            System.out.print(' ');
        }
        System.out.println();
        for (int i = 0; i < 8; ++i) {
            System.out.print(i + 1);
            System.out.print("|");
            for (int j = 0; j < 8; ++j) {
                System.out.print(temp[i][j].symbol);
                System.out.print("|");
            }
            System.out.println();
        }
    }

    final GroundColor[][] ground = new GroundColor[8][8];
    final GroundColor[][] groundCanGo = new GroundColor[8][8];
    private final GroundColor[][] groundBack = new GroundColor[8][8];
}
