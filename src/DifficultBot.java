public class DifficultBot implements Player {
    public DifficultBot(GroundColor temp) {
        id = temp;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GroundColor getId() {
        return id;
    }

    @Override
    public void changeCagePlayer(Playground play) {
        double specialSum;
        double localSum = 0;
        double maxDiff = 0;
        double maxSum;
        int maxX = 0;
        int maxY = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                specialSum = 0;
                if (play.groundCanGo[i][j] == GroundColor.Y) {
                    if ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 0) || (i == 7 && j == 7)) {
                        specialSum = 0.8;
                    } else {
                        if (i == 0 || j == 0 || i == 7 || j == 7) {
                            specialSum = 0.4;
                        }
                    }
                    specialSum = findSumCage(i, j, Direction.Left.number, 0, id, play);
                    specialSum += findSumCage(i, j, Direction.Right.number, 0, id, play);
                    specialSum += findSumCage(i, j, 0, Direction.Up.number, id, play);
                    specialSum += findSumCage(i, j, 0, Direction.Down.number, id, play);
                    specialSum += findSumCage(i, j, Direction.Left.number, Direction.Up.number, id, play);
                    specialSum += findSumCage(i, j, Direction.Left.number, Direction.Down.number, id, play);
                    specialSum += findSumCage(i, j, Direction.Right.number, Direction.Up.number, id, play);
                    specialSum += findSumCage(i, j, Direction.Right.number, Direction.Down.number, id, play);

                    play.savePlayground();
                    play.changeCage(id, i + 1, j + 1);
                    play.canGo(false, GroundColor.W);
                    maxSum = 0;
                    for (int n = 0; n < 8; ++n) {
                        for (int p = 0; p < 8; ++p) {
                            localSum = 0;
                            if (play.groundCanGo[n][p] == GroundColor.Y) {
                                if ((n == 0 && p == 0) || (n == 0 && p == 7) || (n == 7 && p == 0) || (n == 7 && p == 7)) {
                                    localSum = 0.8;
                                } else {
                                    if (n == 0 || p == 0 || n == 7 || p == 7) {
                                        localSum = 0.4;
                                    }
                                }
                                localSum += findSumCage(n, p, Direction.Left.number, 0, GroundColor.W, play);
                                localSum += findSumCage(n, p, Direction.Right.number, 0, GroundColor.W, play);
                                localSum += findSumCage(n, p, 0, Direction.Up.number, GroundColor.W, play);
                                localSum += findSumCage(n, p, 0, Direction.Down.number, GroundColor.W, play);
                                localSum += findSumCage(n, p, Direction.Left.number, Direction.Up.number, GroundColor.W, play);
                                localSum += findSumCage(n, p, Direction.Left.number, Direction.Down.number, GroundColor.W, play);
                                localSum += findSumCage(n, p, Direction.Right.number, Direction.Up.number, GroundColor.W, play);
                                localSum += findSumCage(n, p, Direction.Right.number, Direction.Down.number, GroundColor.W, play);
                            }
                            if (localSum > maxSum) {
                                maxSum = localSum;
                            }
                        }
                    }
                    play.backPlayground();
                    if (specialSum - localSum > maxDiff) {
                        maxDiff = specialSum - localSum;
                        maxX = i;
                        maxY = j;
                    }
                }
            }
        }
        System.out.println("Бот походил на клетку x = " + ++maxX + " y = " + ++maxY);
        play.changeCage(id, maxX, maxY);
    }

    private double findSumCage(int i, int j, int lineX, int lineY, GroundColor color, Playground play) {
        int tempX = i;
        int tempY = j;
        double sum = 0;
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
            if (play.ground[tempX][tempY] == color) {
                if (((-coutOpposite) * (lineX % 2) + tempX + (-1) * (lineX % 2) == i || ((-coutOpposite) * (lineY % 2) + tempY + (-1) * (lineY % 2) == j)) && coutOpposite != 0) {
                    for (int k = 1; k <= coutOpposite; ++k) {
                        tempX -= lineX;
                        tempY -= lineY;
                        if (tempX == 0 || tempY == 0 || tempX == 7 || tempY == 7) {
                            sum += 2;
                        } else {
                            ++sum;
                        }
                    }
                }
                return sum;
            } else {
                if (play.ground[tempX][tempY] != GroundColor.E && play.ground[tempX][tempY] != color) {
                    ++coutOpposite;
                }
            }
        }
        return sum;
    }

    GroundColor id;
    static final String name = "Сложный Бот";

}
