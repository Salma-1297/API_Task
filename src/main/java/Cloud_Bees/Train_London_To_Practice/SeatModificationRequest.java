package Cloud_Bees.Train_London_To_Practice;

public class SeatModificationRequest {
	private String newSeat;

	public SeatModificationRequest(String newSeat) {
		this.newSeat = newSeat;
	}

	public String getNewSeat() {
		return newSeat;
	}

	public void setNewSeat(String newSeat) {
		this.newSeat = newSeat;
	}

	@Override
	public String toString() {
		return "SeatModificationRequest{" + "newSeat='" + newSeat + '\'' + '}';
	}
}
