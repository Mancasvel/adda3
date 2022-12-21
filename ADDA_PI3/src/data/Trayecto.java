package data;

public record Trayecto(Double price, Double duration) {

	// precio y duracion
	public static Trayecto ofFormat(String[] formato) {
		Double price = Double.parseDouble(formato[2].replace("euros", ""));
		Double duration = Double.parseDouble(formato[3].replace("min", ""));
		
		return new Trayecto(price, duration);
	}


	public Double price() {
		
		return this.price;
	}
	public String toString() {
		return "Trayecto: " + this.price().toString() + ", " + this.duration().toString();
	}
}
