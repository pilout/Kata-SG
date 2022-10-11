package bank.sg.costumer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import bank.sg.operation.Deposit;
import bank.sg.operation.Tranfert;
import bank.sg.operation.Withdrawal;

public class Account {
	private UUID id;
	private String name;
	private String surname;
	private Double amount;
	private Double maxOverdraft;
	private ArrayList<Tranfert> history;

	public Account(String name, String surname,Double amount) {
		this.name = name;
		this.surname = surname;
		this.id = UUID.randomUUID();
		this.setMaxOverdraft(0.0D);
		this.amount = amount;
		history = new ArrayList<>();
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public Double getMaxOverdraft() {
		return maxOverdraft;
	}

	public void setMaxOverdraft(Double maxOverdraft) {
		this.maxOverdraft = maxOverdraft;
	}

	public Deposit deposit(double amount, String reference) throws Exception {
		Deposit d = new Deposit(this, amount, reference);
		history.add(d);
		return d;
	}

	public Withdrawal withDrawal(double amount, String reference) throws Exception {
		Withdrawal w = new Withdrawal(this, amount, reference);
		history.add(w);
		return w;
	}

	public String drawHistory(LocalDate begin, LocalDate end) {
		StringBuilder sb = new StringBuilder();
		Double balance = 0.0D;
		DecimalFormat df = new DecimalFormat("#,###,###");

		for (var i = 0; i < history.size(); i++) {
			Tranfert t = history.get(i);
			if (t.getDate().isAfter(begin.minusDays(1)) && t.getDate().isBefore(end.plusDays(1))) {
				sb.append(t.toString() + "\n");
				balance += t.finalAmount();
			}
		}
		sb.append(df.format(this.amount) + " " + df.format(balance));

		return sb.toString();
	}
}
