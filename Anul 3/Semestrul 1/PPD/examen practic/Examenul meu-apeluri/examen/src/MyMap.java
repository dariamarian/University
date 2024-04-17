import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyMap {
    HashMap<Integer, ArrayList<Mesaj>> map;
    Lock lock;

    public MyMap(){
        this.map = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    public void add(Mesaj value){
        lock.lock();
        try{
            int key = value.id_agent;
            if(map.containsKey(key)){
                ArrayList<Mesaj> list = map.get(key);
                list.add(value);
                Collections.sort(list, new Comparator<Mesaj>() {
                    @Override
                    public int compare(Mesaj msg1, Mesaj msg2) {
                        return Integer.compare(msg1.dificultate, msg2.dificultate);
                    }
                });
                map.put(key, list);
            }
            else{
                map.put(key, new ArrayList<>(List.of(value)));
            }
        }
        finally {
            lock.unlock();
        }
    }

    public int size(){
        lock.lock();
        try{
            int size = 0;
            for (int key : map.keySet()) {
                size += map.get(key).size();
            }
            return size;
        } finally {
            lock.unlock();
        }
    }
}
