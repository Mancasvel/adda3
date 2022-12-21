package data;

public record Ciudad1(String name,Integer puntuacion) {
	
	// puntuacion de 1-5
	public static Ciudad1 ofFormat(String[] formato) {
		String name = formato[0];
		Integer puntuacion = Integer.parseInt(formato[1].replace("p", ""));
		return new Ciudad1(name, puntuacion);
	}
	
	public static Ciudad1 of(String name, Integer puntuacion) {
		return new Ciudad1(name, puntuacion);
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	public Integer score() {
		
		return this.puntuacion;
	}
}
