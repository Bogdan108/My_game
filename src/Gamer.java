import java.util.InputMismatchException;
import java.util.Scanner;

public
class Gamer implements Player {
    Gamer(GroundColor temp, String fio) {
        id = temp;
        name = fio;
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
        Scanner in = new Scanner(System.in);
        System.out.print("Введите кооринаты: ");
        int i, j;
        while (true) {
            try {
                i = in.nextInt();
                j = in.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Некорекктное значение \nВведите координаты: ");
                in.next();
            }
        }
        if (i > 8 || j > 8 || i < 1 || j < 1) {
            System.out.print("Некорекктное значение \n");
            changeCagePlayer(play);
            return;
        }
        if (play.groundCanGo[i - 1][j - 1] == GroundColor.Y) {
            play.changeCage(id, i, j);
        } else {
            System.out.print("Некорекктное значение \n");
            changeCagePlayer(play);
        }

    }


    String name;
    GroundColor id;

}
