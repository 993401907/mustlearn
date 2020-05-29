package thread.obserable;

public class Main {
    public static void main(String[] args) {

        final TaskLifecycle<String> lifecycle = new TaskLifecycle.EmptyTaskLifecycle<>() {
            @Override
            public void onFinish(Thread t, String result) {
                System.out.println("线程" + t.getName() + " 已经完成");
                System.out.println(result);
            }
        };
        Observable observable = new ObservableThread<>(lifecycle, () -> {
            return "哈哈";
        });
        observable.start();
    }
}
