public class Airport {
	
	private int id;		//gives each airport an id number
	private double lat;	//stores airports longitude
	private double lon;	//stores airports latitude
	
	public Airport() {
		this.id = 0;
		this.lat = 0;
		this.lon = 0;
	}
	
	public Airport(int id, double lat, double lon) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
	}
	
	public int getId() {
		return this.id;
	}
	
	public double getLat() {
		return this.lat;
	}
	
	public double getLon() {
		return this.lon;
	}

}
