package exercises;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import data.Actividad;
import us.lsi.colors.GraphColors;

public class Exercise3 {
	
	//auxiliar
		public static Coloring<Actividad> aux(Graph<Actividad, DefaultEdge> gf){
			VertexColoringAlgorithm<Actividad> alg = new GreedyColoring<>(gf);
			Coloring<Actividad> res = alg.getColoring();
			return res;
		}

		public static void partA(String file, Graph<Actividad, DefaultEdge> gf, String nameView) {
			String resultFiles = "files/exercise3/";
			Coloring<Actividad> col = aux(gf);
			List<Set<Actividad>> shifts = col.getColorClasses();
			
			System.out.println(file + nameView  + "Number of minimum shifts: " + col.getNumberColors() + resultFiles);
			
			for (int i = 0; i < shifts.size(); i ++) {
				System.out.println("Shift " + i + shifts.get(i));
			}
			
		}
		
		public static void partB(String file, Graph<Actividad, DefaultEdge> gf, String nameView) {
			String resultFiles = "files/exercise3/";
			
			Coloring<Actividad> col = aux(gf);
			Map<Actividad, Integer> m = col.getColors();
			
			GraphColors.toDot(gf,
					resultFiles + file + nameView + ".gv",
					v -> v.toString(),
					e -> "",
					v -> GraphColors.color(m.get(v)),
					e -> GraphColors.color(1));
				
			System.out.println(file + nameView + " representation generated in " + resultFiles);
		}
}
