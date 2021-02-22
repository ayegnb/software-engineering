package kz.edu.nu.cs.se;

public class EnteringCoinsState extends State {
    public EnteringCoinsState(VendingMachine vendingmachine) {
        this.vendingMachine = vendingmachine;
        this.name = "enteringCoins";
    }

    public void insertCoin(int coin) {
        this.vendingMachine.balance += coin;
        if (this.vendingMachine.balance >= 200) {
            this.vendingMachine.setCurrentState(this.vendingMachine.paid);
        }
    }

    @Override
    public int refund() {
        int refund = this.vendingMachine.balance;
        this.vendingMachine.balance = 0;
        this.vendingMachine.setCurrentState(this.vendingMachine.idle);
        return refund;
    }

    @Override
    public int vend() {
        throw new IllegalStateException();
    }
}
