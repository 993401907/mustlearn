package thread.eventbus;

import java.util.concurrent.Executor;

/**
 * @author wulizi
 */
class ExecutorFactory {
    public static Executor preThreadExecutor = new PreThreadExecutor();
    public static Executor seqExecutor = new SeqExecutor();

    private static class PreThreadExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    private static class SeqExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}
