package javadesignpatterns.singleton;

/**
 * @author wulizi
 * 饿汉式单例 线程安全 不能实现懒加载
 */
public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();


    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}
