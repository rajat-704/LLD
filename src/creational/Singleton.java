package creational;


public class Singleton {
    String startString;
    private static Singleton instance;
    private Singleton(String s) {
        this.startString = s;
    }
    public static Singleton getInstance(String initString) {
        if (instance == null) {
            System.out.println("initialize the instance with " + initString);
            instance = new Singleton(initString);
        }
        return instance;
    }
}


/*
    Alternate using static inner class rather than static getInstance method
    Ex - The JVM won't load the inner class into memory until it's referenced in the getInstance()
         method, making it both lazy and thread-safe without needing explicit synchronization
    private static class SingletonHelper {
        private static final ThreadSafeSingleton INSTANCE = new ThreadSafeSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
*/
class ThreadSafeSingleton {
    String startString;
    private static volatile ThreadSafeSingleton instance;
    private ThreadSafeSingleton(String s) {
        this.startString = s;
    }
    public static ThreadSafeSingleton getInstance(String initString) {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class){
                System.out.println("initialize thread safe instance with " + initString);
                if (instance == null) {
                    instance = new ThreadSafeSingleton(initString);
                }
            }
        }
        return instance;
    }
}


class MainFactory {
    public static void main(String[] args) {
//        Singleton singleton = Singleton.getInstance("singleton");
//        Singleton singleton1 = Singleton.getInstance("singleton1");
//        Singleton singleton2 = Singleton.getInstance("singleton2");
//        System.out.println(singleton.startString);
//        System.out.println(singleton1.startString);
//        System.out.println(singleton2.startString);
        Thread th = new Thread(() -> {
            Singleton sgt = Singleton.getInstance("singleton4");
            System.out.println(sgt.startString);
        });
        Thread th2 = new Thread(() -> {
            Singleton sgt = Singleton.getInstance("singleton5");
            System.out.println(sgt.startString);
        });

        th.start();
        th2.start();

        Thread th3 = new Thread(() -> {
            ThreadSafeSingleton sgt = ThreadSafeSingleton.getInstance("singleton6");
            System.out.println(sgt.startString);
        });
        Thread th4 = new Thread(() -> {
            ThreadSafeSingleton sgt = ThreadSafeSingleton.getInstance("singleton7");
            System.out.println(sgt.startString);
        });

        th3.start();
        th4.start();
        System.out.println("Main thread executed");
    }
}