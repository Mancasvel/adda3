package test_students;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleWeightedGraph;

import data.Ciudad1;
import data.Trayecto;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;

public class Test_exercise2 {
	
	public static void main(String[] args) {
		String data = "PI3E2_DatosEntrada";
		testPartA(data);
		testPartB(data);
		testPartC(data);
		testPartD(data);
	}
	
	public static void testPartA(String file) {
		
		Graph<Ciudad1, Trayecto> gf = GraphsReader.newGraph("files/" + file + ".txt",
				x -> Ciudad1.ofFormat(x),
				x -> Trayecto.ofFormat(x),
				Graphs2::simpleGraph);
		
		exercises.Exercise2.partA(file, gf, " Part A");
	}
		
	public static void testPartB(String file) {
		
		Graph<Ciudad1, Trayecto> gf = GraphsReader.newGraph("files/" + file + ".txt",
				x -> Ciudad1.ofFormat(x),
				x -> Trayecto.ofFormat(x),
				Graphs2::simpleGraph);
		
		exercises.Exercise2.partB(file, gf, " Part B");
	}

	
	public static void testPartC(String file) {
		Map<Set<Ciudad1>, Double> m = new HashMap<>();
		
		SimpleWeightedGraph<Ciudad1, Trayecto> g = GraphsReader.newGraph("files/" + file + ".txt",
				x -> Ciudad1.ofFormat(x),
				x -> Trayecto.ofFormat(x),
				Graphs2::simpleWeightedGraph,
				x -> x.price());
		
		var alg1 = new ConnectivityInspector<>(g);
		for(Set<Ciudad1> s: alg1.connectedSets()) {
			Graph<Ciudad1, Trayecto> view = SubGraphView.of(g,
					v -> s.contains(v),
					null);
			var alg2 = new HeldKarpTSP<Ciudad1, Trayecto>();
			GraphPath<Ciudad1, Trayecto> path = alg2.getTour(view);
			List<Trayecto> trayecto = path.getEdgeList();
			Double price = 0.;
			for (Trayecto t: trayecto) {
				price = price + t.price();
			}
			m.put(s, price);
		}
		Set<Ciudad1> cheaper = exercises.Exercise2.auxBestGroupPrice(m.entrySet());
		Graph<Ciudad1, Trayecto> view = SubGraphView.of(g, v -> cheaper.contains(v), null);
		var alg3 = new HeldKarpTSP<Ciudad1, Trayecto>();
		List<Trayecto> trayecto = alg3.getTour(view).getEdgeList();
		
		exercises.Exercise2.partC(file, g, trayecto, cheaper, " Part C");
	}
	
	public static void testPartD(String file) {
		SimpleWeightedGraph<Ciudad1, Trayecto> g = GraphsReader.newGraph("files/" + file + ".txt",
				x -> Ciudad1.ofFormat(x),
				x -> Trayecto.ofFormat(x),
				Graphs2::simpleWeightedGraph,
				x -> x.duration());
		
		Map<GraphPath<Ciudad1, Trayecto>, Double> m = new HashMap<GraphPath<Ciudad1, Trayecto>, Double>();
		
		Integer n = 1;
		
		var alg = new ConnectivityInspector<>(g);
		for(Set<Ciudad1> s : alg.connectedSets()) {
			Graph<Ciudad1, Trayecto> view = SubGraphView.of(g, v -> s.contains(v), null);
			var alg2 = new FloydWarshallShortestPaths<>(view);
			for(Ciudad1 firstCity : s) {
				for (Trayecto firstEdge : g.edgesOf(firstCity)) {
					for (Trayecto secondEdge : g.edgesOf(g.getEdgeTarget(firstEdge))) {
						Ciudad1 secondCity = g.getEdgeTarget(secondEdge);
						Integer jumps = alg2.getPath(firstCity, secondCity).getLength();
						if (jumps > 1 && g.edgesOf(secondCity).stream().allMatch(x -> g.getEdgeTarget(x) != firstCity && g.getEdgeSource(x) != firstCity)) {
							m.put(alg2.getPath(firstCity, secondCity), alg2.getPathWeight(firstCity, secondCity));
						}
					}
				}
			}
			
			GraphPath<Ciudad1, Trayecto> cheaper = Collections.min(m.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
			
			exercises.Exercise2.partD(file, g, cheaper, " Part D");
		}
		
		
	}
	
	
}
