package test_students;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import data.Actividad;
import us.lsi.common.Files2;

public class Test_exercise3 {
	
	public static void main(String[] args) {
		
		testPartA("PI3E3A_DatosEntrada");
		testPartB("PI3E3B_DatosEntrada");
	}

	private static void testPartA(String file) {
		Graph<Actividad, DefaultEdge> g = new SimpleGraph<Actividad, DefaultEdge>(DefaultEdge.class);
		Files2.streamFromFile("files/" + file + ".txt").forEach(line -> {
			String[] s1 = line.trim().split(":");
			String[] s2 = s1[1].trim().split(",");
			
			for (int i = 0; i < s2.length - 1; i = i + 1) {
				if (!(g.containsVertex(getActividad(s2[i], g)))) g.addVertex(Actividad.of(s2[i]));
				for (int n = i + 1; n < s2.length; n = n+1) {
					if (!(g.containsVertex(getActividad(s2[n],g)))) g.addVertex(Actividad.of(s2[n]));
					g.addEdge(getActividad(s2[i], g), getActividad(s2[n], g));
				}
			}
		});
		
		exercises.Exercise3.partA(file, g, " Apartado A");
		

	}
	private static void testPartB(String file) {
		Graph<Actividad, DefaultEdge> g = new SimpleGraph<Actividad, DefaultEdge>(DefaultEdge.class);
		Files2.streamFromFile("files/" + file + ".txt").forEach(line -> {
			String[] s1 = line.trim().split(":");
			String[] s2 = s1[1].trim().split(",");
			
			for (int i = 0; i < s2.length - 1; i = i + 1) {
				if (!(g.containsVertex(getActividad(s2[i], g)))) g.addVertex(Actividad.of(s2[i]));
				for (int n = i + 1; n < s2.length; n = n+1) {
					if (!(g.containsVertex(getActividad(s2[n],g)))) g.addVertex(Actividad.of(s2[n]));
					g.addEdge(getActividad(s2[i], g), getActividad(s2[n], g));
				}
			}
		});
		
		exercises.Exercise3.partB(file, g, " Part B");

	}
	
	
	private static Actividad getActividad(String name, Graph<Actividad, DefaultEdge> gf) {
		Actividad objective = null;
		for (Actividad actividad : gf.vertexSet()) {
			if (actividad.name().equals(name.strip())) {
				return actividad;
			}
		}
		return objective;
	}
}
