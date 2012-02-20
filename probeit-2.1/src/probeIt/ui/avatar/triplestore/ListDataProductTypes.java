package probeIt.ui.avatar.triplestore;

import java.util.Vector;

import probeIt.ProbeIt;
import probeIt.ui.WindowApplication;
import probeIt.ui.avatar.triplestore.ListAuthors.QueryRDFStore;
import probeIt.ui.workers.SwingWorker;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;

public class ListDataProductTypes extends IndividualComboBox {

	private static final long serialVersionUID = 1L;
	private Vector<Individual> individuals;

	public ListDataProductTypes() {
		super();
		//queryDataProductTypes();
	}
	
	public class QueryRDFStore extends SwingWorker {
		
		public QueryRDFStore() {
		}

		public Object construct()
		{
			edu.utep.trust.provenance.RDFStore_Service service = new edu.utep.trust.provenance.RDFStore_Service();
			edu.utep.trust.provenance.RDFStore proxy = service.getRDFStoreHttpPort();
			String types = proxy.getInformationSubclasses();

			return types;
		}

		public void finished()
		{
			WindowApplication.getInstance().disableProgressBar();			
		}
	}
	
	public static String stripURI(String formatURI)
	{
		int start = formatURI.indexOf('#') + 1;
		String name = formatURI.substring(start);
		
		return name;
	}

	public void loadTypes() {

		// just need to load authors once per session
		if (individuals != null) {
			//System.out.println("loadAuthors: already loaded");
			return;
		}

		individuals = new Vector<Individual>();
		
		WindowApplication.getInstance().enableProgressBar("Loading types... ");						
		QueryRDFStore query = new QueryRDFStore();
		query.start();
		
		ResultSet results = ResultSetFactory.fromXML((String)query.get());
		
		String type;
		
//		System.out.println(types);

		individuals.add(new Individual("Choose Type", " -- Choose Type -- ", "Choose Type"));
		
		// try web service and update local store	
		if(results != null)
			while(results.hasNext())
			{
				QuerySolution QS = results.nextSolution();
				type = QS.get("?informationSubclass").toString();

				String prettyName = QS.get("?subclassLabel").toString();
				
				if(prettyName.contains("@")){
					prettyName = prettyName.substring(0, prettyName.indexOf('@'));
				}
				
				if(type == null || prettyName == null)
				{
					System.out.println("Null Pretty Name Conversion");
					break;
				}
				else
				{
					individuals.add(new Individual(type, prettyName, type));
				}

				//			System.out.println(results.nextSolution().get("?x").toString());
			}
		// if web service not reached, populate from local store

		this.setIndividuals(individuals);
	}
}

