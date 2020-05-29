package thread.obserable;

import java.util.concurrent.Callable;

/**
 * @author wulizi
 * 线程观察者实现类
 */
public class ObservableThread<T> extends Thread implements Observable {
    private TaskLifecycle<T> taskLifecycle;
    private Callable<T> callable;
    private Cycle cycle;

    public ObservableThread(TaskLifecycle<T> taskLifecycle, Callable<T> callable) {
        if (callable == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        this.taskLifecycle = taskLifecycle;
        this.callable = callable;
    }

    /**
     * 默认使用生命周期的空实现
     */
    public ObservableThread(Callable<T> callable) {
        this(new TaskLifecycle.EmptyTaskLifecycle<>(), callable);
    }
    @Override
    public final void run() {
        this.update(Cycle.STATRING, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = this.callable.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception ex) {
            this.update(Cycle.ERROR, null, ex);
        }
    }



    @Override
    public Cycle getCycle() {
        return this.cycle;
    }

    /**
     * 根据线程状态调用线程生命周期的方法
     */
    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;
        if (taskLifecycle == null) {
            return;
        }
        try {
            switch (cycle) {
                case STATRING:
                    taskLifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    taskLifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    taskLifecycle.onFinish(currentThread(), result);
                    break;
                case ERROR:
                    taskLifecycle.onError(currentThread(), e);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.ERROR) {
                throw ex;
            }
        }

    }
}
