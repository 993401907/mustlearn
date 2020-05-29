package javadesignpatterns.singleton;

/**
 * @author wulizi
 * 枚举单例模式
 */
public class EnumSingleton {
    private enum Holder {
        /**
         * 枚举
         */
        INSANSE;
        private EnumSingleton instance;

        Holder() {
            instance = new EnumSingleton();
        }

        private EnumSingleton getSingleton() {
            return instance;
        }
    }

    public static EnumSingleton getInstance() {
        return Holder.INSANSE.getSingleton();
    }
}
