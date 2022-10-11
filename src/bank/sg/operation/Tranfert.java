package bank.sg.operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import bank.sg.costumer.Account;

public abstract class Tranfert implements ITranfert {

	private LocalDate date;
	private Double amount;
	private StatOperation stat;
	private String reference;

	public Tranfert(Account account, double amount, String reference) throws Exception {
		if (amount <= 0.0D )
			throw new Exception("The amount value must be positive");

		this.setAmount(BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).doubleValue());
		this.setReference(reference);
		this.tranfert(account);
		this.setDate(LocalDate.now());

	}

	public StatOperation getStat() {
		return stat;
	}

	public void setStat(StatOperation stat) {
		this.stat = stat;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
		String result = stat == StatOperation.refuse ? "refuse" : "succes"; 
		DecimalFormat df = new DecimalFormat("#,###,###");
		return formatters.format(date) + " " + this.reference + " " + " " + df.format(this.getAmount()) + " " + result;
	}

}
