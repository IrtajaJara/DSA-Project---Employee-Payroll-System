import java.util.ArrayList;

public class StackAction {
    ArrayList<Action> list = new ArrayList<>();

    void push(Action a) {
        list.add(a);
    }

    Action pop() {
        if (list.isEmpty()) return null;
        return list.remove(list.size() - 1);
    }

    boolean isEmpty() {
        return list.isEmpty();
    }
}