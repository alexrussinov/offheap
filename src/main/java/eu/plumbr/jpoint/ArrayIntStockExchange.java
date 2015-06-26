package eu.plumbr.jpoint;

/**
 * Created by alex on 26.06.2015.
 */
public class ArrayIntStockExchange implements StockExchange {

    private  int[] orders = new int[TRADES_PER_DAY * 4];
    private int pointer = 0;
    private int  offset = 4;

    public void setOrders(int[] orders) {
        this.orders = orders;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPointer() {

        return pointer;
    }

    public int getOffset() {
        return offset;
    }

    public int[] getOrders() {

        return orders;
    }

    @Override
    public void order(int ticket, int amount, int price, boolean buy) {
        int[] orders = getOrders();
        orders[pointer] = ticket;
        orders[pointer + 1] = amount;
        orders[pointer + 2] = price;
        orders[pointer + 3] = buy ? 1 : 0;
        setPointer(getPointer() + getOffset());
    }

    @Override
    public double dayBalance() {
        int[] orders = getOrders();
        int i = 0;
        double result = 0.0;
        while(i < TRADES_PER_DAY * offset - 5){
            result = result + orders[i + 1] * orders[i + 2] * ((orders[i + 3] == 1) ? 1 : -1);
            i += 5;
        }
        return result;
    }
}
