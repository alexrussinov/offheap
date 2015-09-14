package eu.plumbr.jpoint;

/**
 * Created by alex on 26.06.2015.
 */
public class ArrayIntStockExchange implements StockExchange {

    private int recordsCount = 0;
    private int[] buffer;
    private final ArrayTrade flyweight = new ArrayTrade();

    private ArrayTrade get(final int index) {
        final int offset = index * flyweight.getObjectSize();
        flyweight.setObjectOffset(offset);
        return flyweight;
    }

    public ArrayIntStockExchange() {
        buffer = new int[TRADES_PER_DAY * flyweight.getObjectSize()];
    }

    @Override
    public void order(int ticket, int amount, int price, boolean buy) {
        ArrayTrade trade = get(recordsCount++);
        trade.setTicket(ticket);
        trade.setAmount(amount);
        trade.setPrice(price);
        trade.setBuy(buy);
    }

    @Override
    public double dayBalance() {
        double balance = 0;
        for (int i = 0; i < recordsCount; i++) {
            ArrayTrade trade = get(i);
            balance += trade.getAmount() * trade.getPrice() * (trade.isBuy() ? 1 : -1);
        }
        return balance;
    }

    private class ArrayTrade {
        private int offset = 0;

        private final int ticketOffset = offset += 0;
        private final int amountOffset = offset += 1;
        private final int priceOffset = offset += 1;
        private final int buyOffset = offset += 1;

        private final int objectSize = offset += 1;

        private int objectOffset;

        public int getObjectSize() {
            return objectSize;
        }

        void setObjectOffset(final int objectOffset) {

            this.objectOffset = objectOffset;
        }

        public void setTicket(final int ticket) {
            buffer[objectOffset + ticketOffset] = ticket;
        }

        public int getPrice() {
            return buffer[objectOffset + priceOffset];
        }

        public void setPrice(final int price) {
            buffer[objectOffset + priceOffset] = price;
        }

        public int getAmount() {

            return buffer[objectOffset + amountOffset];
        }

        public void setAmount(final int quantity) {
            buffer[objectOffset + amountOffset] = quantity;
        }

        public boolean isBuy() {
            return buffer[objectOffset + buyOffset] == 1;
        }

        public void setBuy(final boolean buy) {
            buffer[objectOffset + buyOffset]=  buy ? 1 : 0;
        }
    }
}
