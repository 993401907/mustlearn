package thread.future;

/**
 * @author wulizi
 * 任务接口
 */
@FunctionalInterface
public interface Task<In,Out> {
    /**
     * 获取返回值
     * @param input 输入
     * @return 输出
     */
    Out get(In input);
}
