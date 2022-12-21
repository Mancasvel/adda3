package data;

public record Persona(Integer id, String name, Integer year, String city) {


	
	public static Persona ofFormat(String[] formato) {
		Integer id = Integer.parseInt(formato[0]);
		String name = formato[1];
		Integer year = Integer.parseInt(formato[2]);		
		String city = formato[3];
		
		return new Persona(id, name, year, city);
	}
	
	public static Persona of(Integer id, String name, Integer year, String city) {
		
		return new Persona(id, name, year, city);
	}
	
	@Override
	public String toString() {
		return this.name();
	}

	
	/*
	public static Carretera of(Double kms) {
		Double km = kms;
		String nomb = null;		
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}
	
	// Necesario para el Ejemplo 2 c)
	public static Carretera of(Double kms, String nombre) {
        Double km = kms;
        String nomb = nombre;           
        Integer id = num;
        num++;
        return new Carretera(id, km, nomb);
	} */
	
}
