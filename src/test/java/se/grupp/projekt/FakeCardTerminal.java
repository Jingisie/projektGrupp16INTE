package se.grupp.projekt;

// En enkel terminal med kortsaldo, används bara i tester
class FakeCardTerminal implements PaymentTerminal {
    private long balanceInOre;

    FakeCardTerminal(long balanceInOre) {
        this.balanceInOre = balanceInOre;
    }

    @Override
    public PaymentResult charge(Money amount) {
        if (amount.getAmountInOre() > balanceInOre) {
            return new PaymentResult(PaymentStatus.DECLINED);
        }
        balanceInOre -= amount.getAmountInOre();
        return new PaymentResult(PaymentStatus.APPROVED);
    }

    long getBalanceInOre() {
        return balanceInOre;
    }
}