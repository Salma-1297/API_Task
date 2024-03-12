package Cloud_Bees.Train_London_To_Practice;

public class Ticket {
	private int id;
	private String from;
	private String to;
	private User user;
	private double pricePaid;
	private String seat;

	public Ticket(int id, String from, String to, User user, double pricePaid, String seat) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.user = user;
		this.pricePaid = pricePaid;
		this.seat = seat;
	}

	public int getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public User getUser() {
		return user;
	}

	public double getPricePaid() {
		return pricePaid;
	}

	public String getSeat() {
		return seat;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPricePaid(double pricePaid) {
		this.pricePaid = pricePaid;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	@Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", user=" + user +
                ", pricePaid=" + pricePaid +
                ", seat='" + seat + '\'' +
                '}';
}
}