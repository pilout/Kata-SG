package bank.sg.operation;

import bank.sg.costumer.Account;
//CLASS DE DEPOT
public class Deposit  extends Tranfert {


	public Deposit(Account account, double amount,String reference) throws Exception {
		super(account, amount,reference);
	}

	@Override
	public void tranfert(Account account) {
			double maxValueRest = Double.MAX_VALUE -account.getAmount();
			
			if(this.getAmount() > maxValueRest)
				this.setStat(StatOperation.refuse);
			else {
				account.setAmount(account.getAmount()+this.getAmount());
				this.setStat(StatOperation.succes);
			}
	}

	
	@Override
	public Double finalAmount() {
		return this.getAmount();
	}

	@Override
	public String toString() {
		return "DEPOT " + super.toString();
	}

}
