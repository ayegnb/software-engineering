package kz.edu.nu.cs.se;

public class PaidState extends State {
    public PaidState(VendingMachine vendingmachine) {
        this.vendingMachine = vendingmachine;
        this.name = "paid";
    }

    public void insertCoin(int coin) {
        this.vendingMachine.balance += coin;
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
        this.vendingMachine.balance -= 200;
        this.vendingMachine.setCurrentState(this.vendingMachine.idle);
        return this.vendingMachine.balance;
    }
}
