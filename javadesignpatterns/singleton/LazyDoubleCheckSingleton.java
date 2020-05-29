package javadesignpatterns.singleton;

import java.sql.Connection;

/**
 * @author wulizi
 * 懒汉式双重验证单例
 */
public class LazyDoubleCheckSingleton {
    private Connection connection;
    /**
     * 在多线程环境下指令重拍会导致instance实例化在connection之前
     * 添加volatile关键字防止指令重排
     */
    private volatile static LazyDoubleCheckSingleton instance;

    private LazyDoubleCheckSingleton(){}

    /**
     * 使用双重验证 判断两次既性能 又能保证同步
     */
    public static LazyDoubleCheckSingleton getInstance() {
        if (instance ==null){
            synchronized (LazyDoubleCheckSingleton.class){
                if (instance == null){
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
