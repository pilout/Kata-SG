package bank.sg.operation;

import bank.sg.costumer.Account;

public class Withdrawal extends Tranfert {

	
	public Withdrawal (Account account, Double amount,String reference) throws Exception {
		super(account, amount,reference);
	}

	@Override
	public void tranfert(Account account) {
		
		if(account.getAmount()-this.getAmount() <= -account.getMaxOverdraft())
			this.setStat(StatOperation.refuse);
		else {
			account.setAmount(account.getAmount()-this.getAmount());
			this.setStat(StatOperation.succes);
		}
			
		
	}
	
	@Override
	public Double finalAmount() {
		return -this.getAmount();
	}
	
	@Override
	public String toString() {
		return "RETRAIT " + super.toString();
	}

}
