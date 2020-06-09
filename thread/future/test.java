package thread.future;

public class test {
    public static void main(String[] args) throws InterruptedException {
        FutureService<String ,Integer> service = FutureService.newService();

        Future<Integer> future = service.submit(String::length,"sb");
        System.out.println(future.get());
    }
}
