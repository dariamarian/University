import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        IOHandler.cleanFile();
        AtomicInteger totalProducers = new AtomicInteger(5);
        AtomicInteger totalConsumers = new AtomicInteger(6);
        AtomicInteger messagesSent = new AtomicInteger(0);
        MyQueue<Mesaj> queue1 = new MyQueue<>(totalProducers);
        MyQueue<Mesaj> queue2 = new MyQueue<>(totalProducers);
        MyQueue<Mesaj> queue3 = new MyQueue<>(totalProducers);
        MyMap map = new MyMap();
        List<Thread> producers = new ArrayList<>();
        List<Thread> consumers = new ArrayList<>();
        Thread supervisor;

        for (int i = 0; i < totalProducers.get(); i++) {
            Producer producer = new Producer(queue1, queue2, queue3, totalProducers, messagesSent);
            producers.add(producer);
            producer.start();
        }
        for (int i = 0; i < totalConsumers.get(); i++) {
            int j = i % 3 + 1;
            Consumer consumer;
            if (j == 1) {
                consumer = new Consumer(queue1, totalConsumers, map);
                consumers.add(consumer);
                consumer.start();
            } else if (j == 2) {
                consumer = new Consumer(queue2, totalConsumers, map);
                consumers.add(consumer);
                consumer.start();
            } else {
                consumer = new Consumer(queue3, totalConsumers, map);
                consumers.add(consumer);
                consumer.start();
            }
        }
        supervisor = new Supervisor(queue1, queue2, queue3, map);
        supervisor.start();

        for (Thread producer : producers) {
            try {
                producer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Thread consumer : consumers) {
            try {
                consumer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            supervisor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Producer extends Thread {
        final MyQueue<Mesaj> queue1;
        final MyQueue<Mesaj> queue2;
        final MyQueue<Mesaj> queue3;
        AtomicInteger totalProducers;
        AtomicInteger messagesSent;
        Random random = new Random();

        public Producer(MyQueue<Mesaj> queue1, MyQueue<Mesaj> queue2, MyQueue<Mesaj> queue3, AtomicInteger totalProducers, AtomicInteger messagesSent) {
            this.queue1 = queue1;
            this.queue2 = queue2;
            this.queue3 = queue3;
            this.totalProducers = totalProducers;
            this.messagesSent = messagesSent;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    int id_agent = (int) this.getId();
                    int id_apel = random.nextInt(10);
                    int dificultate = random.nextInt(1, 4);
                    if (dificultate == 1)
                        queue1.push(new Mesaj(id_agent, id_apel, dificultate));
                    else if (dificultate == 2)
                        queue2.push(new Mesaj(id_agent, id_apel, dificultate));
                    else
                        queue3.push(new Mesaj(id_agent, id_apel, dificultate));
                    messagesSent.incrementAndGet();
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            synchronized (this) {
                totalProducers.decrementAndGet();
                System.out.println("Producer finished");
                queue1.finish();
                queue2.finish();
                queue3.finish();
            }
        }
    }

    private static class Consumer extends Thread {
        MyQueue<Mesaj> queue;
        AtomicInteger totalConsumers;
        MyMap map;

        public Consumer(MyQueue<Mesaj> queue, AtomicInteger totalConsumers, MyMap map) {
            this.queue = queue;
            this.totalConsumers = totalConsumers;
            this.map = map;
        }

        @Override
        public void run() {
            while (queue.hasData()) {
                try {
                    Mesaj mesaj = queue.pop();
                    if (mesaj == null) {
                        continue;
                    }
                    sleep(25);
                    map.add(mesaj);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            totalConsumers.decrementAndGet();
            System.out.println("Consumer finished");
        }
    }

    private static class Supervisor extends Thread {
        private final MyQueue<Mesaj> queue1;
        private final MyQueue<Mesaj> queue2;
        private final MyQueue<Mesaj> queue3;
        private final MyMap map;
        private volatile boolean running = true;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public Supervisor(MyQueue<Mesaj> queue1,
                          MyQueue<Mesaj> queue2,
                          MyQueue<Mesaj> queue3,
                          MyMap map) {
            this.queue1 = queue1;
            this.queue2 = queue2;
            this.queue3 = queue3;
            this.map = map;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    HashMap<Integer, ArrayList<Mesaj>> copyMap;
                    synchronized (map) {
                        // Facem o copie a mapă pentru a evita ConcurrentModificationException
                        copyMap = new HashMap<>(map.map);
                    }
                    IOHandler.writeText("Waiting Calls for queue 1: " + queue1.size() + "\n");
                    IOHandler.writeText("Waiting Calls for queue 2: " + queue2.size() + "\n");
                    IOHandler.writeText("Waiting Calls for queue 3: " + queue3.size() + "\n");

                    // Raportarea numărului de apeluri rezolvate pentru fiecare agent
                    for (int agentId : copyMap.keySet()) {
                        ArrayList<Mesaj> resolvedCalls = copyMap.get(agentId);
                        IOHandler.writeText("Agent: " + agentId);
                        IOHandler.writeText(", resolved Calls: " + "\n");
                        for (Mesaj call : resolvedCalls) {
                            IOHandler.writeText(call.toString() + "\n");
                        }
                        IOHandler.writeText(dateFormat.format(new Date()) + "\n");
                    }
                    IOHandler.writeText("\n");
                    sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void stopSupervisor() {
            running = false;
        }
    }

}
