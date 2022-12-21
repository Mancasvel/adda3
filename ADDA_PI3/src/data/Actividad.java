package data;

public record Actividad(String name) {

	public static Actividad of(String name) {
		
		return new Actividad(name.trim());
	}

	public String name() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}

}
