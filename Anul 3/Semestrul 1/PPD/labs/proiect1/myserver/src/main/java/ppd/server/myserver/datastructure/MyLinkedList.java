package ppd.server.myserver.datastructure;

import ppd.server.myserver.entity.Concurent;
import ppd.server.myserver.util.Node;

import java.util.ArrayList;
import java.util.List;

public class MyLinkedList extends Node {
    public final Node head = new Node(null, null, null);
    public final Node tail = new Node(null, null, null);

    public MyLinkedList() {
        head.next = tail;
        tail.previous = head;
    }

    public void add(Concurent concurent) {
        head.lock();
        head.next.lock();

        Node right = head.next;

        Node node = new Node(concurent, null, null);
        node.lock();

        head.next = node;
        node.previous = head;
        node.next = right;
        right.previous = node;

        right.unlock();
        node.unlock();
        head.unlock();
    }

    public Node update(Concurent concurent) {
        Node actual = head.next;
        if (head.next != tail) {
            while (actual.isNotLastNode()) {
                actual.lock();
                if (actual.getData().getId().equals(concurent.getId())) {
                    actual.getData().setScore(actual.getData().getScore() + concurent.getScore());
                    actual.unlock();
                    return actual;
                }
                actual.unlock();
                actual = actual.next;
            }
        }
        return null;
    }

    public void delete(Concurent concurent) {
        head.lock();
        head.next.lock();
        if (head.next == tail) {
            head.unlock();
            head.next.unlock();
            return;
        }
        Node actual = head.next;

        while (actual.isNotLastNode()) {
            actual.next.lock();
            if (actual.getData().getId().equals(concurent.getId())) {
                Node left = actual.previous;
                Node right = actual.next;

                left.next = right;
                right.previous = left;
                left.unlock();
                actual.unlock();
                right.unlock();
                return;
            }
            actual.previous.unlock();
            actual = actual.next;
        }

        actual.previous.unlock();
        actual.unlock();
    }

    public List<Concurent> getItemsAsList() {
        List<Concurent> list = new ArrayList<>();
        Node actual = head.next;
        while (actual.isNotLastNode()) {
            list.add(actual.getData());
            actual = actual.next;
        }
        return list;
    }

    public void deleteAll() {
        head.next = tail;
        tail.previous = head;
    }

    public void sortList() {
        boolean sorted;
        do {
            sorted = true;

            Node actual = head.next;

            while (actual.next != tail) {
                if (actual.getData().getScore() < actual.next.getData().getScore()) {
                    Concurent temporaryData = actual.getData();
                    actual.setData(actual.next.getData());
                    actual.next.setData(temporaryData);
                    sorted = false;
                }
                actual = actual.next;
            }

        } while (!sorted);
    }
}
