package javadesignpatterns.singleton;

/**
 * @author wulizi
 * holder单例模式
 */
public class HolderSingleton {
    private HolderSingleton() {
    }

    /**
     * 编译时收集至<clint>方法，改方法线程同步且只执行一次
     */
    private static class Holder {
        private static HolderSingleton instance = new HolderSingleton();
    }

    public static HolderSingleton getInstance() {
        return Holder.instance;
    }
}
