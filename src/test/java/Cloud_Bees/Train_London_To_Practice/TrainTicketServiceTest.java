package Cloud_Bees.Train_London_To_Practice;



import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.*;

public class TrainTicketServiceTest {

    private TrainTicketService service;

    @BeforeMethod
    public void setUp() {
        service = new TrainTicketService();
    }

    @Test
    public void testPurchaseTicket() {
        User user = new User("John", "Doe", "john.doe@example.com");
        TicketRequest request = new TicketRequest("London", "Paris", user, 5.0);
        Response response = service.purchaseTicket(request);
        Assert.assertEquals(200, response.getStatus());
        Ticket ticket = (Ticket) response.getEntity();
        Assert.assertNotNull(ticket);
        Assert.assertEquals("London", ticket.getFrom());
        Assert.assertEquals("Paris", ticket.getTo());
        Assert.assertEquals(user, ticket.getUser());
        Assert.assertEquals(5.0, ticket.getPricePaid());
    }

    @Test
    public void testGetReceipt() {
       
        Response purchaseResponse = service.purchaseTicket(new TicketRequest("London", "Paris", new User("Jane", "Roe", "jane.roe@example.com"), 5.0));
        Ticket purchasedTicket = (Ticket) purchaseResponse.getEntity();
        int ticketId = purchasedTicket.getId();

        Response response = service.getReceipt(ticketId);
        Assert.assertEquals(200, response.getStatus());
        Ticket ticket = (Ticket) response.getEntity();
        Assert.assertEquals(ticketId, ticket.getId());
        
    }

    @Test
    public void testRemoveUser() {
       
        User user = new User("Jane", "Roe", "jane.roe@example.com");
        service.purchaseTicket(new TicketRequest("London", "Paris", user, 5.0));
        int ticketId = 1; 

        Response response = service.removeUser(ticketId);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("User removed from the train", response.getEntity().toString());
    }

    @Test
    public void testGetSeatsBySection() {
        
        service.purchaseTicket(new TicketRequest("London", "Paris", new User("Alice", "Smith", "alice.smith@example.com"), 5.0));
        service.purchaseTicket(new TicketRequest("London", "Paris", new User("Bob", "Jones", "bob.jones@example.com"), 5.0));
        
        
        Response responseA = service.getSeatsBySection("A");
        Assert.assertEquals(200, responseA.getStatus());
        Ticket[] ticketsA = (Ticket[]) responseA.getEntity();
        Assert.assertTrue(ticketsA.length > 0, "Section A should have at least one ticket.");

        
        Response responseB = service.getSeatsBySection("B");
        Assert.assertEquals(200, responseB.getStatus());
        Ticket[] ticketsB = (Ticket[]) responseB.getEntity();
        Assert.assertTrue(ticketsB.length > 0, "Section B should have at least one ticket.");
    }

    @Test
    public void testModifySeat() {
        
        Response purchaseResponse = service.purchaseTicket(new TicketRequest("London", "Paris", new User("Charlie", "Brown", "charlie.brown@example.com"), 5.0));
        Ticket purchasedTicket = (Ticket) purchaseResponse.getEntity();
        int ticketId = purchasedTicket.getId();

       
        SeatModificationRequest modificationRequest = new SeatModificationRequest(null);
        modificationRequest.setNewSeat("B2");
        Response modifyResponse = service.modifySeat(ticketId, modificationRequest);
        Assert.assertEquals(200, modifyResponse.getStatus());

        
        Response receiptResponse = service.getReceipt(ticketId);
        Ticket updatedTicket = (Ticket) receiptResponse.getEntity();
        Assert.assertEquals("B2", updatedTicket.getSeat(), "Seat should be updated to B2.");
    }

   




}
