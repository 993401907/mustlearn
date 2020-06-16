package thread.workthread;

/**
 * @author wulizi
 * 书籍加工
 */
public class BookProduction extends BaseProduction {
    private int bookId;

    public BookProduction(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public void firstProcess() {
        System.out.println("第一次加工开始");
    }

    @Override
    public void secondProcess() {
        System.out.println("第二次加工开始");
    }

    public int getBookId() {
        return this.bookId;
    }
}
