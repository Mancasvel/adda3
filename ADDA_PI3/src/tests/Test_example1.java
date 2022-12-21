package tests;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import data.Carretera;
import data.Ciudad;
import examples.Example_1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Test_example1 {
	
	public static void main(String[] args) {
		testsExample1("Andalucia");
		testsExample1("Castilla la Mancha");
	}
	
	private static void testsExample1(String file) {
		// read a graph from a txt -> GraphsReader
		Graph<Ciudad, Carretera> g = GraphsReader.newGraph(
				
				"files/" + file + ".txt",
				
				Ciudad ::ofFormat, //Ciudad factory method
                Carretera::ofFormat, //Carretera factory method
                Graphs2::simpleGraph); // Graph factory method
		
		GraphColors.toDot(g, 
				"files/example1/" + file +  ".gv", // graphviz
				v -> v.nombre(), // label vertex
				e -> e.nombre(), // edge label
				
				v -> GraphColors.color(Color.red),
				e -> GraphColors.style(Style.solid)
				
				); 
		
		
		
		Predicate<Ciudad> pv1 = c -> c.nombre().contains("e");
		Predicate<Carretera> pe1 = ca -> ca.km() > 200;
		Example_1.example1(file, g, pv1, pe1, " B1");
		
		
		// cities with less than 500.000 inhabitants and roads whose origin or destination city has a name of more than 5 characters and have more than 100 KM OF DISTANCE
		
		Predicate<Ciudad> pv2 = c -> c.habitantes()< 500000;
		Predicate<Carretera> pe2 = ca -> ca.km() > 100 &&
				(g.getEdgeSource(ca).nombre().length() >5 ||
						g.getEdgeTarget(ca).nombre().length() > 5);
		
		
		Example_1.example1(file, g, pv2, pe2, " B2");
	}
	
	
	

}
