package examples;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import data.Carretera;
import data.Ciudad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.graphs.views.SubGraphView;

public class Example_1 {
	
	public static void example1(String file, Graph<Ciudad, Carretera> g,
			Predicate<Ciudad> pv, 
			Predicate<Carretera> pe,
			String viewName) {
		
		// view of a graph -> subGraphView
		Graph<Ciudad, Carretera>  view =
				SubGraphView.of(g, //graph
						pv, // vertex predicate
						pe); // edge predicate
		
		// display a graph -> GraphColors (.todot)
		
		GraphColors.toDot(view, 
				"files/example1" + file + viewName + ".gv", // graphviz to see it( graphviz online)
				
				v -> v.nombre(), // label vertex
				e -> e.nombre(), // edge label
				
				v -> GraphColors.colorIf(Color.red,
						view.edgesOf(v).size() > 1),
				e -> GraphColors.style(Style.solid)
				
				); 
		
		
		System.out.println(file + "Representation generated");
		
		
	}
	

}
