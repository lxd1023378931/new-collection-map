import com.uzak.list.List;
import com.uzak.list.NewLinkedList;

public class Main {

    public static void main(String[] args) {
        List.Queue a = (new NewLinkedList<>()).new LinkedQueue();
        a.enqueue(1);
        a.enqueue(2);
        a.enqueue(3);
        a.enqueue(4);
        System.out.println(a.toString());
        while (a.next()) {
            System.out.println(a.dequeue());
        }
    }
}
