public class SolutionDanLi {
}

/**
 * 1.懒汉式 线程不安全
 */
class Singleton{

    /**
     * 以下实现中，私有静态变量 uniqueInstance 被延迟实例化，这样做的好处是，
     * 如果没有用到该类，那么就不会实例化 uniqueInstance，从而节约资源。
     *
     * 这个实现在多线程环境下是不安全的，如果多个线程能够同时进入 if (uniqueInstance == null) ，
     * 并且此时 uniqueInstance 为 null，那么会有多个线程执行 uniqueInstance = new Singleton();
     * 语句，这将导致实例化多次 uniqueInstance。
     */
    private static Singleton uniqueInstance;
    private Singleton(){

    }
    public static Singleton getUniqueInstance(){
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
}

/**
 * 2.饿汉式 线程安全
 */
/**
 * 线程不安全问题主要是由于 uniqueInstance 被实例化多次，
 * 采取直接实例化 uniqueInstance 的方式就不会产生线程不安全问题。
 * 但是直接实例化的方式也丢失了延迟实例化带来的节约资源的好处。
 */
//private static Singleton uniqueInstance = new Singleton();


/**
 * 3.懒汉式 线程安全
 * 只需要对 getUniqueInstance() 方法加锁，那么在一个时间点只能有一个线程能够进入该方法，
 * 从而避免了实例化多次 uniqueInstance。
 */
class Singleton2{
    private static Singleton2 uniqueInstance;
    private Singleton2(){

    }
    public static synchronized Singleton2 getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton2();
        }
        return uniqueInstance;
    }
}

/**
 * 4.双重校验锁 线程安全
 */
/**
 * uniqueInstance 只需要被实例化一次，之后就可以直接使用了。
 * 加锁操作只需要对实例化那部分的代码进行，只有当 uniqueInstance 没有被实例化时，才需要进行加锁。
 * 双重校验锁先判断 uniqueInstance 是否已经被实例化，如果没有被实例化，那么才对实例化语句进行加锁。
 */
class Singleton3 {

    private volatile static Singleton3 uniqueInstance;
    private Singleton3() {
    }

    public static Singleton3 getUniqueInstance() {
        /**
         * 第一个 if 语句用来避免 uniqueInstance 已经被实例化之后的加锁操作，
         * 而第二个 if 语句进行了加锁，所以只能有一个线程进入，就不会出现 uniqueInstance == null 时
         * 两个线程同时进行实例化操作。
         */
        if (uniqueInstance == null) {
            synchronized (Singleton3.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton3();
                }
            }
        }
        return uniqueInstance;
    }
}
/**
 * 静态内部类实现
 */

class Singleton4 {
    /**
     * 当 Singleton 类被加载时，静态内部类 SingletonHolder 没有被加载进内存。
     *  只有当调用 getUniqueInstance() 方法从而触发 SingletonHolder.INSTANCE 时 SingletonHolder 才会被加载
     *   ，此时初始化 INSTANCE 实例，并且 JVM 能确保 INSTANCE 只被实例化一次。
     */
    /**
     * 这种方式不仅具有延迟初始化的好处，而且由 JVM 提供了对线程安全的支持。
     */


    private Singleton4() {
    }

    private static class SingletonHolder {
        private static final Singleton4 INSTANCE = new Singleton4();
    }

    public static Singleton4 getUniqueInstance() {
        return SingletonHolder.INSTANCE;
    }
}

