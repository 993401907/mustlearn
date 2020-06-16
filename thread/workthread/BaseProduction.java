package thread.workthread;

/**
 * @author wulizi
 * 生产
 */
public abstract class BaseProduction {

    /**
     * 开始加工
     */
    protected void begin() {
        this.firstProcess();
        this.secondProcess();
    }
    public void firstProcess(){}

    public void secondProcess(){}
}
