package exercises;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import data.Carretera;
import data.Ciudad;
import data.Persona;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.graphs.views.SubGraphView;

public class Exercise1 {

	
	public static void createViewA(String file, Graph<Persona, DefaultEdge> gf,
			Predicate<Persona> pv, 
			String viewName) {
		
		// view of a graph -> subGraphView
		Graph<Persona, DefaultEdge>  view =
				SubGraphView.of(gf, //graph
						pv, // vertex predicate
						null); // edge predicate
		
		// display a graph -> GraphColors (.todot)
		
		GraphColors.toDot(gf, 
				"files/exercise1/" + file + viewName + ".gv", // graphviz to see it( graphviz online)
				
				v -> v.name(), // label vertex
				e -> "", // edge label
				
				v -> GraphColors.colorIf(Color.red,
						view.containsVertex(v)),
				e -> GraphColors.colorIf(Color.red, view.containsEdge(e))
				); 
		System.out.println(file + "Representation generated");
	}
	public static void createViewB(String file, Graph<Persona, DefaultEdge> gf,
			Predicate<Persona> pv,
			Persona person,
			String viewName) {
		
		Graph<Persona, DefaultEdge>  view =
				SubGraphView.of(gf, //graph
						pv, // vertex predicate
						null); // edge predicate
	
	String resultFiles = "files/exercise1/"; 
	//we define it in order to not repeat it again
	
	Map<Persona, Color> m = new HashMap<>(); 
	
	for(Persona p: gf.vertexSet()) {
		if(p.equals(person)) {
			m.put(p, Color.cyan); //different for the person is given
		}
		else if (view.containsVertex(p)){
			m.put(p, Color.green); //different for the ancestor of that person 
		}
		
		else {
			m.put(p, Color.black); 
		}
	}
	
	// display a graph -> GraphColors (.todot)
	GraphColors.toDot(gf,
			resultFiles + file + viewName + ".gv",
			v -> v.name(),
			e -> "",
			v -> GraphColors.color(m.get(v)),
			e -> GraphColors.colorIf(Color.green, view.containsEdge(e))				
			);
	
	System.out.println(file + viewName + " representation generated in " + resultFiles);
}
	
	public static void partC(Graph<Persona, DefaultEdge> g, String file, Persona person1, Persona person2) {

		DijkstraShortestPath<Persona, DefaultEdge> start = new DijkstraShortestPath<>(g);
		
		Integer numberEdges = start.getPath(person1, person2).getLength();
		
		//for siblings
		if (numberEdges == 2) {
			System.out.println(person1.toString() + " and "  + person2.toString() + " are siblings.");
		}
		//for cousins
		else if (numberEdges == 4) {
			System.out.println(person1.toString() + " and " + person2.toString() + " are cousins.");
		}
		//default
		else {
			System.out.println(person1.toString() + " and " + person2.toString() + " are others.");
		}
		
	}
	
public static void partD(Graph <Persona, DefaultEdge> gf, String file, Predicate<Persona> pv, String viewName) {
		
		Graph<Persona, DefaultEdge> view = SubGraphView.of(gf, pv,null);
		 
		String resultFiles = "files/exercise1/";
		
		GraphColors.toDot(gf,
				resultFiles + file + viewName + ".gv",
				v -> v.name(),
				e ->"",
				v -> GraphColors.colorIf(Color.magenta, view.containsVertex(v)),
				e -> GraphColors.colorIf(Color.magenta, view.containsEdge(e))
				);
		System.out.println(file + viewName + " representation generated in " + resultFiles);
	}
	
public static void partE(Graph <Persona, DefaultEdge> gf, String file, Predicate<Persona> pv, String viewName) {
	
	Graph<Persona, DefaultEdge> view = SubGraphView.of(gf, pv,null);
	 
	String resultFiles = "files/exercise1/";
	
	GraphColors.toDot(gf,
			resultFiles + file + viewName + ".gv",
			v -> v.name(),
			e ->"",
			v -> GraphColors.colorIf(Color.orange, view.containsVertex(v)),
			e -> GraphColors.colorIf(Color.orange, view.containsEdge(e))
			);
	System.out.println(file + viewName + " representation generated in " + resultFiles);
}
	
}
	
