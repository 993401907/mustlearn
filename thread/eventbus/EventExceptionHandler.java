package thread.eventbus;

/**
 * @author wulizi
 * 出错的方法
 */
public interface EventExceptionHandler {
    /**
     * 出错执行
     * @param cause 原因
     * @param context 信息
     */
    void handle(Throwable cause, EventContext context);
}
