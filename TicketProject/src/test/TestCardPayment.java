package test;

import machines.TicketMachine;
import paymentMethods.PaymentMethod;
import ticket.TicketType;

/**
 *
 * @author Manuele
 */
public class TestCardPayment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TicketMachine tMachine = new TicketMachine(5000, "10.87.156.248");
        
        tMachine.setTicketToSell(TicketType.SINGLE);
        tMachine.setPaymentMethod(PaymentMethod.CREDITCARD);
        
        tMachine.buyTicket();
        
        tMachine.setTicketToSell(TicketType.SINGLE);
        tMachine.setPaymentMethod(PaymentMethod.CREDITCARD);
        
        tMachine.buyTicket();
    }
}