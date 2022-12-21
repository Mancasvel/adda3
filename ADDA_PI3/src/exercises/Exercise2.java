package exercises;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.SimpleWeightedGraph;

import data.Ciudad1;
import data.Trayecto;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Exercise2 {
	
	
	//representing graph views following given predicates
	public static void viewsCreation(String file, Graph<Ciudad1, Trayecto> gf, Predicate<Ciudad1> pv, Predicate<Trayecto> pa, String viewName) {
		Graph<Ciudad1, Trayecto> view = SubGraphView.of(gf, pv, pa);
		
		String resultFiles = "files/exercise2/";
		
		GraphColors.toDot(gf,
				resultFiles + file + viewName + ".gv",
				v -> v.name(),
				e -> "",
				v -> GraphColors.colorIf(Color.blue, view.containsVertex(v)),
				e -> GraphColors.colorIf(Color.blue, view.containsEdge(e)));
		
		System.out.println(file + viewName + " representation generated in " + resultFiles);
	}
	
	public static void partA(String file, Graph<Ciudad1, Trayecto> gf, String viewName) {
		String resultFiles = "files/exercise2/";
		
		var alg = new ConnectivityInspector<>(gf);
		Integer relatedCitiesG = alg.connectedSets().size();
		Map<Ciudad1, Integer> m = new HashMap<>();
		Integer n = 1;
		
		for (Set<Ciudad1> s : alg.connectedSets()) {
			for(Ciudad1 ciudad: s) {
				m.put(ciudad, n);
			}
			n++;
		}
		GraphColors.toDot(gf,
				resultFiles + file + viewName + ".gv",
				v -> v.name(),
				e -> "",
				v -> GraphColors.color(m.get(v)),
				e -> GraphColors.color(Color.black));
		
		
		System.out.println("Number of groups of related cities: " + relatedCitiesG);
		System.out.println("Composition of the groups: ");
		for (Set<Ciudad1> s : alg.connectedSets()) {
			System.out.println(s);
		}
		System.out.println(file + viewName + " representation generated in " + resultFiles);
	}
	
	
	//aux for b
	public static Set<Ciudad1> auxBestGroupScore(Set<Entry<Set<Ciudad1>, Integer>> set) {
		Integer groupScore = 0;
		Set<Ciudad1> result = new HashSet<Ciudad1>();
		for(Entry<Set<Ciudad1>, Integer> s : set) {
			if (s.getValue() > groupScore) {
				groupScore = s.getValue();
				result = s.getKey();
			}
		}
		return result;
	}
	
	public static void partB(String file, Graph<Ciudad1, Trayecto> gf, String viewName) {
		String resultFiles = "files/exercise2/";
		
		var alg = new ConnectivityInspector<>(gf);
		var m = new HashMap<Set<Ciudad1>, Integer>();
		for(Set<Ciudad1> s : alg.connectedSets()) {
			Integer sum = 0;
			for (Ciudad1 ciudad : s) {
				sum = sum + ciudad.score();
			}
			m.put(s, sum);
		}
		Set<Ciudad1> bestOptionScore = auxBestGroupScore(m.entrySet());

		GraphColors.toDot(gf,
				resultFiles + file + viewName + ".gv",
				v -> v.name(),
				e -> "",
				v -> GraphColors.colorIf(Color.black, bestOptionScore.contains(v)),
				e -> GraphColors.color(Color.orange));
		
		System.out.println(file + viewName + " representation generated in " + resultFiles);
	}
	
	
	//aux for c
	public static Set<Ciudad1> auxBestGroupPrice(Set<Entry<Set<Ciudad1>, Double>> set){
		Set<Ciudad1> res = new HashSet<Ciudad1>();
		Double minimum = 0.;
		Boolean first = true;
		 for (Entry<Set<Ciudad1>, Double> s : set) {
			 if (first == true) {
				 minimum = s.getValue();
				 res = s.getKey();
				 first = false;
			 }
			 else if (s.getValue() < minimum){
				 minimum = s.getValue();
				 res = s.getKey();
			 }
		 }
		 return res;
	}
	
	public static void partC(String file, SimpleWeightedGraph<Ciudad1, Trayecto> gf, List<Trayecto> path, Set<Ciudad1> cheaperRes, String viewName) {
		String resultFiles = "files/exercise2/";
		
		GraphColors.toDot(gf,
				resultFiles + file + viewName + ".gv",
				v -> v.name() ,
				e -> "",
				v -> GraphColors.colorIf(Color.magenta, cheaperRes.contains(v)),
				e -> GraphColors.colorIf(Color.magenta, path.contains(e)));
		
		System.out.println(file + viewName + " representation generated in " + resultFiles);

	}
	
	
	public static void partD(String file, SimpleWeightedGraph<Ciudad1, Trayecto> gf, GraphPath<Ciudad1, Trayecto> path, String viewName) {
		Predicate<Trayecto> pa = x -> path.getEdgeList().contains(x);
		Predicate<Ciudad1> pv = x -> path.getVertexList().contains(x);
		
		viewsCreation(file, gf, pv, pa, viewName);
	}
	

}

