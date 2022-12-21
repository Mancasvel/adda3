package test_students;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;
import org.jgrapht.graph.DefaultEdge;

import data.Persona;
import exercises.Exercise1;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Test_exercise1 {
	
	
	public static void main(String[] args) {
		
		List<Persona> testList = new ArrayList<>();
		Persona ptest1 = Persona.of(2, "Manuela", 1947, "Cadiz");
		testList.add(ptest1);
		Persona ptest2 = Persona.of(7, "Irene", 1928, "Madrid");
		testList.add(ptest2); //for part B
		Persona ptest3 = Persona.of(9, "Pedro", 1949, "Sevilla");
		testList.add(ptest3);
		Persona ptest4 = Persona.of(14, "Julia", 1996, "Jaen");
		testList.add(ptest4);
		
		testPartA("PI3E1A_DatosEntrada");
		testPartB("PI3E1B_DatosEntrada", ptest2);
		
		for (int a = 0; a < testList.size() - 1 ; a++) {
			testPartC("PI3E1B_DatosEntrada", testList.get(a), testList.get(a+1));
		}
		
		testPartD("PI3E1B_DatosEntrada");
		testPartE("PI3E1B_DatosEntrada");
	
	}

	//aux function for partA when city and year are repeated
		private static Boolean sameCityYear(Graph<Persona, DefaultEdge> gf, Set<DefaultEdge> set ) {
			Set<String> cities = new HashSet<String>();
			Set<Integer> years = new HashSet<Integer>();
			Boolean result = false;
			if (set.size() == 2) {
				for (DefaultEdge x : set) {
					cities.add(gf.getEdgeSource(x).city());
					years.add(gf.getEdgeSource(x).year());
				}
				
				result = years.size()==1 && cities.size()==1;
			}
			return result;
		}
		private static void testPartA(String file) {
			//read a graph from a txt -> GraphsReader
			Graph<Persona, DefaultEdge> g = GraphsReader.newGraph(
					"files/" + file + ".txt",
					p ->  Persona.ofFormat(p), 
					p -> new DefaultEdge(), 
					Graphs2 :: simpleDirectedGraph); 
			
			Predicate<Persona> pv = v -> sameCityYear(g, g.incomingEdgesOf(v));
			
			Exercise1.createViewA(file, g, pv, " Part A");
		
		}
		
		//auxiliar for PartB when they are descendants
		private static Set<Persona> descendants(Graph<Persona, DefaultEdge> gf, Persona user) {
			Set<Persona> descendants = new HashSet<>();
			if (gf.outDegreeOf(user) > 0) {
				for (DefaultEdge x : gf.outgoingEdgesOf(user)) {
					Persona nueva = gf.getEdgeTarget(x);
					descendants.add(nueva);
					descendants.addAll(descendants(gf, nueva));
				}
			}
			
			descendants.add(user);
			return descendants;
		}
		
		private static void testPartB(String file, Persona persona) {
			Graph<Persona, DefaultEdge> g = GraphsReader.newGraph(
					"files/" + file + ".txt",
					p -> Persona.ofFormat(p),
					p -> new DefaultEdge(),
					Graphs2::simpleDirectedGraph);
			
			Predicate<Persona> pv = v -> descendants(g, v).contains(persona);
			
			Exercise1.createViewB(file, g, pv, persona, " Part B");
		}
		
		private static void testPartC(String file, Persona persona1, Persona persona2) {
			Graph<Persona, DefaultEdge> g = GraphsReader.newGraph(
					"files/" + file + ".txt",
					p -> Persona.ofFormat(p),
					p -> new DefaultEdge(),
					Graphs2 ::simpleGraph);
			
			Exercise1.partC(g, file, persona1, persona2);
			
		}
		
		//auxiliar for when someone have sons with more than 1 person
		private static Boolean sonsDifferentPersons(Graph<Persona, DefaultEdge> gf, Persona person) {
			Set<Persona> result = new HashSet<>();
			if(gf.outDegreeOf(person) > 0) {
				for (DefaultEdge edge : gf.outgoingEdgesOf(person)) {
					Persona descendant = gf.getEdgeTarget(edge);
					for (DefaultEdge progEdge : gf.incomingEdgesOf(descendant)) {
						result.add(gf.getEdgeSource(progEdge));
					}
				}
			}
			return result.size() > 2; //check
		}
		
		private static void testPartD(String file) {
			Graph<Persona, DefaultEdge> g = GraphsReader.newGraph(
					"files/" + file + ".txt",
					p -> Persona.ofFormat(p),
					p -> new DefaultEdge(),
					Graphs2::simpleDirectedGraph);
			
			Predicate<Persona> pv = v -> sonsDifferentPersons(g, v);
			
			Exercise1.partD(g, file, pv, " Apartado D");
		}
		
		private static void testPartE(String file) {
			Graph<Persona, DefaultEdge> g = GraphsReader.newGraph(
					"files/" + file + ".txt",
					p -> Persona.ofFormat(p),
					p -> new DefaultEdge(),
					Graphs2::simpleGraph);
			
			var alg = new GreedyVCImpl<>(g);
			Set<Persona> minVertexCover = alg.getVertexCover();
			Predicate<Persona> pv = v -> minVertexCover.contains(v);
			
			Exercise1.partE(g, file, pv, " Apartado E");
		}
}
