import java.util.Scanner;

public class Process {
    private void mainMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("Выберите режим игры : \nлегкий - команда /e \nпродвинутый - команда /d  \nигрок против игрока - команда /pvp");
        String command = in.nextLine();
        boolean indicator = false;
        while (!indicator) {
            if (command.equals("/e")) {
                System.out.println("Введите имя первого игрока:");
                String nameFirst = in.nextLine();
                playerFirst = new Gamer(GroundColor.W, nameFirst);
                playerSecond = new EasyBot(GroundColor.B);
                indicator = true;
            } else {
                if (command.equals("/d")) {
                    System.out.println("Введите имя первого игрока:");
                    String nameFirst = in.nextLine();
                    playerFirst = new Gamer(GroundColor.W, nameFirst);
                    playerSecond = new DifficultBot(GroundColor.B);
                    indicator = true;
                } else {
                    if (command.equals("/pvp")) {
                        System.out.println("Введите имя первого игрока:");
                        String nameFirst = in.nextLine();
                        playerFirst = new Gamer(GroundColor.W, nameFirst);
                        System.out.println("Введите имя второго игрока:");
                        String nameSecond = in.nextLine();
                        playerSecond = new Gamer(GroundColor.B, nameSecond);
                        indicator = true;
                    } else {
                        System.out.println("Неверная команда! \nПопробуйте снова!");
                        command = in.nextLine();
                    }
                }
            }
        }
    }

    private boolean stepPlayer(Player player, Playground Pc) {
        boolean specialInd = true;
        System.out.println("Актуальная доска: ");
        Pc.printGroundActual();
        if (player.getId() == GroundColor.W) {
            System.out.println("(Белые) Ходит игрок: ");
        } else {
            System.out.println("(Чёрные) Ходит игрок: ");
        }
        System.out.println(player.getName());
        Pc.savePlayground();
        if (player.getId() == GroundColor.W) {
            Pc.canGo(true, GroundColor.W);
        } else {
            Pc.canGo(true, GroundColor.B);
        }
        Pc.printGroundCanGo();
        if (Pc.checkPlaygroundCanGo()) {
            player.changeCagePlayer(Pc);
        } else {
            System.out.println("Пропуск хода!");
            specialInd = false;
        }
        System.out.println("Актуальная доска: ");
        Pc.printGroundActual();
        return specialInd;
    }


    private void askCommand(Playground Pc, Player player) {
        Scanner in = new Scanner(System.in);
        System.out.println("Что вы хотите сделать ?\nпродолжить игру - команда /c\ncделать шаг назад - команда /b ?");
        String command = in.nextLine();
        boolean indicator = false;
        while (!indicator) {
            if (command.equals("/c")) {
                indicator = true;
            } else {
                if (command.equals("/b")) {
                    indicator = true;
                    Pc.backPlayground();
                    stepPlayer(player, Pc);
                } else {
                    System.out.println("Неверная команда! \nПопробуйте снова!");
                    command = in.nextLine();
                }
            }
        }
    }

    private void endGame(Player playerFirst, Player playerSecond, Playground Pc) {
        Pc.printGroundActual();
        int firstResult = Pc.scoringPoints(GroundColor.W);
        int secondResult = Pc.scoringPoints(GroundColor.B);
        System.out.println("Игрок " + playerFirst.getName() + " набрал очков: " + firstResult);
        System.out.println("Игрок " + playerSecond.getName() + " набрал очков: " + secondResult);
        if (secondResult > firstResult) {
            if (secondResult > sessionResult) {
                sessionResult = secondResult;
                sessionName = playerSecond.getName();
            } else {
                sessionResult = firstResult;
                sessionName = playerFirst.getName();
            }
        }
        System.out.println("Игрок " + sessionName + " набрал больше всего очков: " + sessionResult);
    }

    public void startGame() {
        boolean sessionInd = true;
        while (sessionInd) {
            int stopInd = 0;
            Playground Pc = new Playground();
            mainMenu();
            while (Pc.checkPlayground() && stopInd != 2) {
                stopInd = 0;
                if(!stepPlayer(playerFirst, Pc)){
                    ++stopInd;
                }
                askCommand(Pc, playerFirst);
                if (!Pc.checkPlayground()) {
                    break;
                }
                if(!stepPlayer(playerSecond, Pc)){
                    ++stopInd;
                }
                if (!(playerSecond.getName().equals("Легкий Бот") || playerSecond.getName().equals("Сложный Бот"))) {
                    askCommand(Pc, playerSecond);
                }
            }
            endGame(playerFirst, playerSecond, Pc);

            System.out.println("Желаете ли вы продолжить игру ?\n/y or /n");
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();

            boolean indicator = false;
            while (!indicator) {
                if (!command.equals("/y")) {
                    if (command.equals("/n")) {
                        indicator = true;
                        sessionInd = false;
                    } else {
                        System.out.println("Неверная команда! \nПопробуйте снова!");
                        command = in.nextLine();
                    }
                } else {
                    indicator = true;
                }
            }
        }
    }

    Player playerFirst = null;
    Player playerSecond = null;
    int sessionResult = 0;
    String sessionName;
}
