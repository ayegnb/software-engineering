package kz.edu.nu.cs.se;

public class IdleState extends State {
    public IdleState(VendingMachine vendingmachine) {
        this.vendingMachine = vendingmachine;
        this.name = "idle";
    }

    public void insertCoin(int coin) {
        if (coin == 50 || coin == 100) {
            this.vendingMachine.balance += coin;
            this.vendingMachine.setCurrentState(this.vendingMachine.enteringCoins);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int refund() {
        return this.vendingMachine.balance;
    }

    @Override
    public int vend() {
        throw new IllegalStateException();
    }
}
