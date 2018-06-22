package txz.study.example;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018/6/21.
 */
public class queue {
    private LinkedList<String> bag = new LinkedList<String>();

    private int max = 10;
    private Lock lock = new ReentrantLock() ;
    private Condition condition = lock.newCondition();

    public String get() {
        String res = "";
        lock.lock();
        try {
            while (bag.size() == 0) {
                condition.await();
            }
            res = bag.removeLast();
            System.out.println(bag.size()+"取出。。" + res);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return  res;
    }

    public void put(String s) {
        lock.lock();
        try {
            while (bag.size() == max) {
                condition.await();
            }
            bag.add(s);
            System.out.println(bag.size()+"放入。。" + s);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public synchronized String gett() {
        String s ="";

        try {
            while (bag.size() == 0) {
                this.wait();
            }
            s = bag.removeLast();
            System.out.println(bag.size()+" get:"+s);
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    public synchronized void puts(String s) {
        try {
            while (bag.size() == 10) {
                this.wait();
            }
            bag.add(s);
            System.out.println(bag.size()+" put:"+s);
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
