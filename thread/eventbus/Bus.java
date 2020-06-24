package thread.eventbus;

/**
 * @author wulizi
 * eventBus接口
 */
public interface Bus {
    /**
     * 获取名称
     * @return .
     */
    String getBusName();

    /**
     * 注册事件
     * @param object 注册对象
     */
    void register(Object object);

    /**
     * 解除注册事件
     * @param object 注册对象
     */
    void unregister(Object object);

    /**
     * 提交默认事件
     * @param event event
     */
    void post(Object event);

    /**
     * 提交指定topic事件
     * @param event event
     * @param topic 主题
     */
    void post(Object event,String topic);

    /**
     * 关闭
     */
    void close();
}
