package examples;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;

public class Example_3 {
	
	public static void example3(Graph<String, DefaultEdge> gf, String file) {
		 //var for not repeating the types like GreedyColoring
		var alg = new GreedyColoring<> (gf);
		Coloring<String> solution = alg.getColoring();
		System.out.println("Necessary tables: " + solution.getNumberColors());
		
		System.out.println("Composition of each table:");
		List<Set<String>> tables = solution.getColorClasses();
		for (int i = 0; i < tables.size(); i++) {
			System.out.println("Table bumber " +  (i + 1) + ": " + tables.get(i));
			
		}
		Map<String, Integer> color_per_vertex = solution.getColors();
		// para el greedy coloring el getColors y el getNumberColors hay que tenerlos en cuenta y el getColoring
		
		GraphColors.toDot(gf, 
				
				"result/ejemplo3/" + file + ".gv",  // where it will be saved and the extension of the file
				v -> v.toString(),
				e -> "", // default edge so ""
				v ->  GraphColors.color(color_per_vertex.get(v)),
				e -> GraphColors.style(Style.dotted)
				);
		
		System.out.println(file + " generated!");
	}

}
