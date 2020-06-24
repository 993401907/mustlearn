package thread.eventbus;

import java.lang.reflect.Method;

/**
 * @author wulizi
 * 注册的方法和对象类
 */
public class Subscriber {
    private Object subsObj;
    private Method subsMethod;



    /**
     * 是否可用 标记删除
     */
    private boolean isDisabled;
    public Subscriber(Object subsObj, Method subsMethod) {
        this.subsObj = subsObj;
        this.subsMethod = subsMethod;
    }

    public Object getSubsObj() {
        return subsObj;
    }

    public Method getSubsMethod() {
        return subsMethod;
    }

    public boolean isDisabled() {
        return isDisabled;
    }
    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
