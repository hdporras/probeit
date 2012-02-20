package probeIt.ui.avatar.triplestore;

import java.util.Vector;

import pml.PMLNode;
import pml.loading.Loader;
import probeIt.ProbeIt;
import probeIt.graphics.CanvasDrawer_Query;
import probeIt.ui.WindowApplication;
import probeIt.ui.query.AnswerPanel;
import probeIt.ui.query.QueryBuilder;
import probeIt.ui.workers.SwingWorker;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;

public class ListAuthors extends IndividualComboBox {
	
	private static final long serialVersionUID = 1L;
    private Vector<Individual> individuals;
    
	public ListAuthors() {
		super();
		//loadAuthors();
	}
	
	public static String stripURI(String formatURI)
	{
		int start = formatURI.indexOf('#') + 1;
		String name = formatURI.substring(start);
		
		return name;
	}
	
	public class QueryRDFStore extends SwingWorker {
		
		public QueryRDFStore() {
		}

		public Object construct()
		{
			edu.utep.trust.provenance.RDFStore_Service service = new edu.utep.trust.provenance.RDFStore_Service();
			edu.utep.trust.provenance.RDFStore proxy = service.getRDFStoreHttpPort();
			String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
			"PREFIX pmlj: <http://inference-web.org/2.0/pml-justification.owl#>" +
			"PREFIX pmlp: <http://inference-web.org/2.0/pml-provenance.owl#>" +
			"PREFIX pml-sparql: <http://trust.utep.edu/sparql-pml#>" +
			"PREFIX ds: <http://inference-web.org/2.0/ds.owl#>" +
			"select ?URI ?NAME where {" +
			"?URI a pmlp:Person ." +
			"?URI pmlp:hasName ?NAME ." +
			"}";
			String pml_person = proxy.doQuery(query);
			
			return pml_person;
		}

		public void finished()
		{
			WindowApplication.getInstance().disableProgressBar();
		}
	}
	
	public void loadAuthors() {
		
		// just need to load authors once per session
		if (individuals != null) {
			//System.out.println("loadAuthors: already loaded");
			return;
		}
		
		//System.out.println("loadAuthors: loading...");
 		individuals = new Vector<Individual>();
	
		WindowApplication.getInstance().enableProgressBar("Loading authors... ");
		QueryRDFStore query = new QueryRDFStore();
		query.start();
		
		ResultSet results = ResultSetFactory.fromXML((String)query.get());
		
		String personURI = "", personName = "";
		
		individuals.add(new Individual("Choose Source", " -- Choose Source -- ", "Choose Source"));
		
		// try web service and update local store	
		if(results != null)
			while(results.hasNext())
			{
				QuerySolution QS = results.nextSolution();
				personName = QS.get("?NAME").toString();

				personURI = QS.get("?URI").toString();
				
				personName = personName.substring(0, personName.indexOf('^'));
				
				if(personURI == null || personName == null)
				{
					System.out.println("Null Pretty Name Conversion");
					break;
				}
				else
				{
					individuals.add(new Individual(personURI, personName, personURI));
				}
			}
		// if web service not reached, populate from local store

		this.setIndividuals(individuals);
	}
}

