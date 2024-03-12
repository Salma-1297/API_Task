package Cloud_Bees.Train_London_To_Practice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Path("/train")
public class TrainTicketService {

	private final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();
	private final AtomicInteger ticketIdGenerator = new AtomicInteger();

	@POST
	@Path("/purchaseTicket")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response purchaseTicket(TicketRequest request) {
		int ticketId = ticketIdGenerator.incrementAndGet();
		Ticket ticket = new Ticket(ticketId, request.getFrom(), request.getTo(), request.getUser(),
				request.getPricePaid(), assignSeat());
		tickets.put(ticketId, ticket);
		return Response.ok(ticket).build();
	}

	@GET
	@Path("/receipt/{ticketId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReceipt(@PathParam("ticketId") int ticketId) {
		Ticket ticket = tickets.get(ticketId);
		if (ticket == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Ticket not found").build();
		}
		return Response.ok(ticket).build();
	}

	@GET
	@Path("/seats/{section}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSeatsBySection(@PathParam("section") String section) {

		// Filter the tickets map to get a list of tickets in the given section
		List<Ticket> ticketsInSection = tickets.values().stream()
				.filter(ticket -> ticket.getSeat().startsWith(section.toUpperCase())).collect(Collectors.toList());

		// Convert the list to an array of Ticket objects
		Ticket[] ticketsArray = ticketsInSection.toArray(new Ticket[0]);

		// Build and return the response with the array of tickets
		return Response.ok(ticketsArray).build();
	}

	@DELETE
	@Path("/user/{ticketId}")
	public Response removeUser(@PathParam("ticketId") int ticketId) {
		Ticket removed = tickets.remove(ticketId);
		if (removed == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Ticket not found").build();
		}
		return Response.ok().entity("User removed from the train").build();
	}

	@PUT
	@Path("/user/{ticketId}/seat")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifySeat(@PathParam("ticketId") int ticketId, SeatModificationRequest request) {
		Ticket ticket = tickets.get(ticketId);
		if (ticket == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Ticket not found").build();
		}
		ticket.setSeat(request.getNewSeat());
		return Response.ok().entity("Seat updated successfully").build();
	}

	private String assignSeat() {
		// Simplified seat assignment logic
		return Math.random() < 0.5 ? "A" + ticketIdGenerator.get() : "B" + ticketIdGenerator.get();
	}
}
