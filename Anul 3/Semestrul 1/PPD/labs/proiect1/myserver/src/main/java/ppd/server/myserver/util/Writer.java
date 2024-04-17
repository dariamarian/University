package ppd.server.myserver.util;

import ppd.server.myserver.datastructure.MyList;
import ppd.server.myserver.datastructure.MyLinkedList;
import ppd.server.myserver.datastructure.MyQueue;
import ppd.server.myserver.entity.Concurent;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Writer extends Thread {
    private final MyLinkedList resultList;
    private final MyList myList;
    private final MyQueue queue;
    private final AtomicInteger readersLeft;
    private final Map<String, ReentrantLock> access;

    public Writer(MyLinkedList resultList, MyList myList, MyQueue queue,
                  AtomicInteger readersLeft, Map<String, ReentrantLock> access) {
        this.resultList = resultList;
        this.myList = myList;
        this.queue = queue;
        this.readersLeft = readersLeft;
        this.access = access;
    }

    @Override
    public void run() {
        while (readersLeft.get() != 0 || !queue.isEmpty()) {
            Concurent concurent = null;
            try {
                concurent = queue.pop();
            } catch (InterruptedException ignored) {
            }

            if (concurent == null) {
                queue.finish();
                continue;
            }

            access.get(concurent.getId()).lock();

            if (!myList.contains(new Pair(concurent.getId(), concurent.getCountry()))) {
                if (concurent.getScore() == -1) {
                    resultList.delete(concurent);
                    myList.add(new Pair(concurent.getId(), concurent.getCountry()));
                } else {
                    Node actual = resultList.update(concurent);

                    if (actual == null) {
                        resultList.add(concurent);
                    }
                }
            }

            access.get(concurent.getId()).unlock();
        }
    }
}