import java.lang.reflect.Type;

public enum Direction {
    Up(-1),
    Down(1),
    Left(-1),
    Right(1);
    int number;

    Direction(int number) {
        this.number = number;
    }
}

