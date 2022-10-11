package bank.sg.costumer;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.*;
import org.junit.runners.MethodSorters;

import bank.sg.operation.Deposit;
import bank.sg.operation.StatOperation;
import bank.sg.operation.Withdrawal;

@FixMethodOrder( MethodSorters.NAME_ASCENDING ) 
public class AccountTest {
	
	static Account testAccount = new Account("Paul", "Martin",100.0D);
	static Account testAccount2 = new Account("Pauline", "Martin",0.0D);

	@Test
	public void A_CanDeposite() throws Exception {
		Deposit d= testAccount.deposit(300.55D,"DEPOT 300.55");
	    assertEquals(400.55D, testAccount.getAmount());
	    assertEquals(d.getStat(), StatOperation.succes);
	}
	
	@Test
	public void B_CanWithDraw() throws Exception {
		Withdrawal w = testAccount.withDrawal(100.55D,"RETRAIT 100.55");
	    assertEquals(300.0D, testAccount.getAmount());
	    assertEquals(w.getStat(), StatOperation.succes);
	}
	
	@Test
	public void C_CantWithDrawOverdraft() throws Exception {
		Withdrawal w = testAccount.withDrawal(300.1D,"RETRAIT DECOUVERT NON AUTORISE");
	    assertEquals(300.0D, testAccount.getAmount());
	    assertEquals(w.getStat(), StatOperation.refuse);
	}
	
	@Test
	public void D_CanWithDrawOverdraft() throws Exception {
		testAccount.setMaxOverdraft(1D);
		Withdrawal w = testAccount.withDrawal(300.1D,"RETRAIT DECOUVERT -0.1");
	    assertEquals(-0.1D, testAccount.getAmount());
	    assertEquals(w.getStat(), StatOperation.succes);
	}
	
	
	@Test
	public void F_CheckCapacityDoubleDeposite() throws Exception {
		Deposit d = testAccount2.deposit(Double.MAX_VALUE-10.1D,"DEPOT MAX-10");
	    assertEquals(Double.MAX_VALUE-10.1D, testAccount2.getAmount());
	    assertEquals(d.getStat(), StatOperation.succes);
	    Deposit d2 =testAccount2.deposit(10.2,"DEPOT MAX +0.1");
	    assertEquals(Double.MAX_VALUE-10.1D, testAccount2.getAmount());
	    assertEquals(d2.getStat(), StatOperation.refuse);
	}
	
	
	@Test
	public void H_CanDrawOperation() throws Exception {
		var d = testAccount.deposit(3500, "DEPOT 3500");
		String res = d.toString();
		assertEquals(res, "DEPOT 10/10/2022 DEPOT 3500  3 500 succes");
			
	}
	
	
	@Test
	public void I_CanDrawHistory() throws Exception {
	
		String res = testAccount.drawHistory(LocalDate.now(), LocalDate.now());
		assertEquals(res,"DEPOT 10/10/2022 DEPOT 300.55  301 succes\nRETRAIT 10/10/2022 RETRAIT 100.55  101 succes\nRETRAIT 10/10/2022 RETRAIT DECOUVERT NON AUTORISE  300 refuse\nRETRAIT 10/10/2022 RETRAIT DECOUVERT -0.1  300 succes\nDEPOT 10/10/2022 DEPOT 3500  3 500 succes\n3 500 3 100");		
	}
	
	@Test
	public void J_NotValideOperation() throws Exception {
	
		Exception ex =assertThrows(Exception.class, ()->{
			 testAccount.deposit(-3500, "depot negatif");
		});
		assertEquals(ex.getMessage(), "The amount value must be positive");
			
	}
	

	
	
}
