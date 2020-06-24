package thread.eventbus;

import java.lang.reflect.Method;

/**
 * @author wulizi
 * 程序出错的信息
 */
public interface EventContext {
    /**
     * 获取名称
     * @return 名称
     */
    String getSource();

    /**
     * 获取注册对象
     * @return 对象
     */
    Object getSubscriber();

    /**
     * 获取方法
     * @return 方法
     */
    Method getMethod();

    /**
     * 获取event
     * @return event
     */
    Object getEvent();
}
