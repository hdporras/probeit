package probeIt.ui.workflow;

import java.awt.Point;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.Ontology;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFVisitor;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * @author Leonardo Salayandia
 *
 */
public class SAW {
	// ontologies
	static public final String OWL_URI = "http://www.w3.org/2002/07/owl";
	static public final String OWL_FILE = "owl.owl";
	static public final String WDO_URI = "http://trust.utep.edu/2.0/wdo.owl";
	static public final String WDO_FILE = "wdo.owl";
	static public final String PMLP_URI = "http://inference-web.org/2.0/pml-provenance.owl";
	static public final String PMLP_FILE = "pml-provenance.owl";
	static public final String DS_URI = "http://inference-web.org/2.0/ds.owl";
	static public final String DS_FILE = "ds.owl";
	// classes
	static public final String DATA_URI = PMLP_URI + "#Information";
	static public final String METHOD_URI = PMLP_URI + "#MethodRule";
	static public final String SAW_URI = WDO_URI + "#SemanticAbstractWorkflow";
	static public final String SOURCE_URI = PMLP_URI + "#Source";
	static public final String INFERENCE_ENGINE_URI = PMLP_URI + "#InferenceEngine";
	// data type properties
	static public final String X_URI = WDO_URI + "#hasXCoordinateValue";
	static public final String Y_URI = WDO_URI + "#hasYCoordinateValue";
	static public final String HAS_NAME_URI = PMLP_URI + "#hasName";
	// object properties
	static public final String HAS_COORDINATE_URI = WDO_URI + "#hasCoordinate";
	static public final String HAS_LABEL_COORDINATE_URI = WDO_URI + "#hasLabelCoordinate";
	static public final String IS_INPUT_TO_URI = WDO_URI + "#isInputTo";
	static public final String IS_OUTPUT_OF_URI = WDO_URI + "#isOutputOf";
	static public final String HAS_INFERENCE_ENGINE_URI = WDO_URI + "#hasInferenceEngine";
	static public final String PML_HAS_SOURCE_URI = PMLP_URI + "#hasSource";
	static public final String IS_DETAILED_BY_URI = WDO_URI + "#isDetailedBy";
	
	/**
	 * Get coordinate instance related to ins through hasLabelCoordinate or hasCoordinate
	 * If ins is of type wdo:Data, gets the coordinate instance for its label position
	 * Otherwise, gets coordinate instance for its node position
	 * @param ins
	 * @return Instance that represents a coordinate
	 */
	private static Individual getCoordinateInstance(OntClass ins) {
		Individual indCoord = null;
		if (ins != null) {
			RDFNode temp = (isDataType(ins)) ? 
					getRelatedRDFNode(ins, HAS_LABEL_COORDINATE_URI) :
					getRelatedRDFNode(ins, HAS_COORDINATE_URI);
			if (temp != null) {
				indCoord = temp.as(Individual.class);	
			}
		}
		return indCoord;
	}
	
	/**
	 * Get Java.awt.Point representing coordinate of ins
	 * @param ins
	 * @return A coordinate point of the instance specified, null if non exists
	 */
	public static Point getInstanceCoordinate(OntClass ins) {
		Point coord = null;
		Individual coordInd = getCoordinateInstance(ins);
		if (coordInd != null) {
			RDFVisitor tmpVisitor = new RDFVisitor() {
				@Override
				public Object visitBlank(Resource r, AnonId id) { return null; }
				@Override
				public Object visitLiteral(Literal l) {	return l.getInt(); }
				@Override
				public Object visitURI(Resource r, String uri) { return null; } 
			};
			RDFNode tmpx = getRelatedRDFNode(coordInd, X_URI);
			RDFNode tmpy = getRelatedRDFNode(coordInd, Y_URI);
			int x = (tmpx == null) ? 0 : (Integer) tmpx.visitWith(tmpVisitor);
			int y = (tmpy == null) ? 0 : (Integer) tmpy.visitWith(tmpVisitor);
			coord = new Point(x, y);
		}
		return coord;
	}
	
	/**
	 * Get RDFNode related to ins through specified property
	 * Assuming only one instance related; if more, returns one randomly
	 * @param ins
	 * @param propURI
	 * @return An RDFNode or null if none exist
	 */
	private static RDFNode getRelatedRDFNode(OntResource ins, String propURI) {
		RDFNode rdfNode = null;
		if (ins != null && propURI != null) {
			OntModel saw = ins.getOntModel();
			OntProperty prop = saw.getOntProperty(propURI);
			if (prop != null) {
				rdfNode = ins.getPropertyValue(prop);
			}
		}
		return rdfNode;
	}
	
	/**
	 * Gets comment for ins
	 * This method is preferred over OntResource.getComment because it attempts twice to get the comment:
	 * 1) using this application's default "EN" language setting, and 
	 * 2) not using the language setting for comments created using other applications.  
	 * @param ins
	 * @return
	 */
	public static String getSAWInstanceComment(OntClass ins) {
		String ans = null;
		if (ins != null) {
			ans = ins.getComment("EN");
			if (ans == null) {
				ans = ins.getComment(null);
			}
		}
		return ans;
	}
	
	/**
	 * Get label of ins
	 * @param ins
	 * @return
	 */
	public static String getSAWInstanceLabel(OntClass ins) {
		String ans = "";
		if (ins != null) {
			RDFNode temp = getRelatedRDFNode(ins, HAS_NAME_URI);
			if (temp != null) {
				RDFVisitor tmpVisitor = new RDFVisitor() {
					@Override
					public Object visitBlank(Resource r, AnonId id) { return null; }
					@Override
					public Object visitLiteral(Literal l) {	return l.getString(); }
					@Override
					public Object visitURI(Resource r, String uri) { return null; } 
				};
				ans = (String) temp.visitWith(tmpVisitor);	
			}
		}
		return ans;
	}
	
	private static String getClassLabel(OntClass cls) {
		String ans = null;
		if (cls != null) {
			ans = cls.getLabel("EN");
			if (ans == null || ans.isEmpty()) {
				ans = cls.getLabel(null);
			}
		}
		return ans;
	}
	
	public static String getClassLocalName(OntClass cls) {
		String ans = "";
		if (cls != null) {
			ans = getClassLabel(cls);
			if (ans == null || ans.isEmpty()) {
				if (cls.isAnon()) {
					ans = cls.getId().getLabelString();
				}
				else {
					ans = cls.getURI();
					int idx = ans.lastIndexOf("#");
					if (idx > 0) {
						ans = ans.substring(idx+1);
					}	
				}
			}
		}	
		return ans;
	}
	
	/**
	 * Returns the local name to be used for the specified class.
	 * The following rules are considered:
	 * 1) If the class has a label, use the label
	 * 2) If the class is an anonymous class, use its anon id
	 * 3) If the class is non-anonymous, use the local name portion of its URI
	 * @param cls Class for which to return its local name
	 * @return A string representing the local name of the class
	 */
	public static String getClassQName(OntClass cls) {
		String ans = "";
		if (cls != null) {
			ans = getClassLabel(cls);
			if (ans == null || ans.isEmpty()) {
				if (cls.isAnon()) {
					ans = cls.getId().getLabelString();
				}
				else {
					ans = cls.getURI();
					int idx = ans.lastIndexOf("#");
					if (idx > 0) {
						ans = ans.substring(idx+1);
					}	
				}
			}
		}	
		return ans;
	}
	
	/**
	 * Get QName of ins
	 * The QName of the instance will be either: insTypeLabel:insLabel or insTypeLabel 
	 * @param ins 
	 * @return A string
	 */
	public static String getSAWInstanceQName(OntClass ins) {		
		OntClass insType = getSAWInstanceType(ins);
		String typeQName = (insType == null) ? "" : getClassQName(insType);
		String insLabel = SAW.getSAWInstanceLabel(ins);
		return (insLabel != null && !insLabel.isEmpty()) ? typeQName + ":" + insLabel : typeQName;
	}
	
	/**
	 * Get instance related to dataIns through property wdo:isOutputOf 
	 * @param dataIns 
	 * @return 
	 */
	public static OntClass getIsOutputOf(OntClass dataIns) {
		OntClass methodIns = null;
		if (dataIns != null) {
			RDFNode temp = getRelatedRDFNode(dataIns, IS_OUTPUT_OF_URI);
			if (temp != null) {
				methodIns = temp.as(OntClass.class);	
			}
		}
		return methodIns;
	}
	
	/**
	 * Get instance related to dataIns through property wdo:isInputTo
	 * @param dataIns
	 * @return
	 */
	public static OntClass getIsInputTo(OntClass dataIns) {
		OntClass methodIns = null;
		if (dataIns != null) {
			RDFNode temp = getRelatedRDFNode(dataIns, IS_INPUT_TO_URI);
			if (temp != null) {
				methodIns = temp.as(OntClass.class);
			}
		}
		return methodIns;
	}
	
	public static boolean isDataSubClass(OntModel ontmodel, OntClass ontclass) {
		boolean ans = false;
		if (ontclass != null && ontmodel != null) {
			OntClass dataclass = ontmodel.getOntClass(DATA_URI);
			// check to see if there is a "substitute class" in the base wdo for the specified class
			// these classes are the ones that are used when harvesting from other ontologies, and have "see also" statement
			OntClass temp = ontmodel.getOntClass(ontclass.getURI());
			if (temp != null) {
				ans = temp.hasSuperClass(dataclass, false);
			}
			else {
				ans = ontclass.hasSuperClass(dataclass, false);	
			}
		}
		return ans;
	}
	
	public static boolean isMethodSubClass(OntModel ontmodel, OntClass ontclass) {
		boolean ans = false;
		if (ontclass != null && ontmodel != null) {
			OntClass methodclass = ontmodel.getOntClass(METHOD_URI);
			// check to see if there is a "substitute class" in the base wdo for the specified class
			// these classes are the ones that are used when harvesting from other ontologies, and have "see also" statement
			OntClass temp = ontmodel.getOntClass(ontclass.getURI());
			if (temp != null) {
				ans = temp.hasSuperClass(methodclass, false);
			}
			else {
				ans = ontclass.hasSuperClass(methodclass, false);	
			}
		}
		return ans;
	}
	
	/**
	 * Gets the type of ins
	 * @param ins SAW instance for which to return its type
	 * @return type of the specified instance, null if the instance does not have a type
	 */
	public static OntClass getSAWInstanceType(OntClass ins) {
		OntClass type = null;
		if (ins != null) {
			type = ins.listSuperClasses(true).next();
		}
		return type;
	}
	
	/**
	 * Check whether ins has the given type. If subtypes, also returns instances of subclasses of type
	 * @param type
	 * @param ins
	 * @param subtypes
	 * @return
	 */
	public static boolean isOfType(OntClass type, OntClass ins, boolean subtypes) {
		boolean ans = false;
		if (type != null && ins != null) {
			OntClass insType = getSAWInstanceType(ins);
			if (insType != null) {
				ans = (insType.equals(type));
				if (!ans && subtypes) {
					ans = (insType.hasSuperClass(type, false));
				}
			}
		}
		return ans;
	}
	
	/**
	 * Check whether ins has type wdo:SemanticAbstractWorkflow or a subclass of it
	 * @param ins
	 * @return
	 */
	public static boolean isSemanticAbstractWorkflowType(OntClass ins) {
		boolean ans = false;
		if (ins != null) {
			OntModel ontmodel = ins.getOntModel();
			OntClass type = ontmodel.getOntClass(SAW_URI);
			ans = isOfType(type, ins, true);	
		}
		return ans;
	}
	
	/**
	 * Check whether ins has type pmlp:Source or a subclass of it
	 * @param ins
	 * @return
	 */
	public static boolean isPMLSourceType(OntClass ins) {
		boolean ans = false;
		if (ins != null) {
			OntModel ontmodel = ins.getOntModel();
			OntClass type = ontmodel.getOntClass(SOURCE_URI);
			ans = isOfType(type, ins, true);	
		}
		return ans;
	}
	
	public static boolean isInferenceEngineType(OntClass ins) {
		boolean ans = false;
		if (ins != null) {
			OntModel ontmodel = ins.getOntModel();
			OntClass ieCls = ontmodel.getOntClass(INFERENCE_ENGINE_URI);
			ans = isOfType(ieCls, ins, false);
		}
		return ans;
	}
	
	/**
	 * Check whether ins has type wdo:Data or a subclass of it
	 * @param ins
	 * @return
	 */
	public static boolean isDataType(OntClass ins) {
		boolean ans = false;
		if (ins != null) {
			OntModel ontmodel = ins.getOntModel();
			OntClass type = ontmodel.getOntClass(DATA_URI);
			ans = isOfType(type, ins, true);	
		}
		return ans;
	}
	
	/**
	 * Check whether ins has type wdo:Method or a sublass of it
	 * @param ins
	 * @return
	 */
	public static boolean isMethodType(OntClass ins) {
		boolean ans = false;
		if (ins != null) {
			OntModel ontmodel = ins.getOntModel();
			OntClass type = ontmodel.getOntClass(METHOD_URI);
			ans = isOfType(type, ins, true);	
		}
		return ans;
	}
	
	/**
	 * Get SAW instance that describes the Semantic Abstract Workflow contained in ontmodel
	 * @param ontmodel
	 * @return
	 */
	public static OntClass getSAWSAWInstance(OntModel ontmodel) {
		OntClass ans = null;
		if (ontmodel != null) {
			OntClass type = ontmodel.getOntClass(SAW_URI);
			for (ExtendedIterator<OntClass> i = ontmodel.listNamedClasses(); i.hasNext(); ) {
				OntClass ins = i.next();
				if (ontmodel.isInBaseModel(ins) && ins.hasSuperClass(type, true)) {
					ans = ins;
					break;
				}
			}
		}
		return ans;
	}
	
	/**
	 * Get Inference Engine instance related to methodIns through property hasInferenceEngine
	 * @param methodIns
	 * @return
	 */
	public static Individual getInferenceEngine(OntClass methodIns) {
		Individual ans = null;
		if (methodIns != null) {		
			RDFNode temp = getRelatedRDFNode(methodIns, HAS_INFERENCE_ENGINE_URI);
			if (temp != null) {
				ans = temp.as(Individual.class);
			}
		}
		return ans;
	}
	
	/**
	 * Get pml source instance related to methodIns through property pmlp:hasSource
	 * @param methodIns
	 * @return
	 */
	public static Individual getPMLSource(OntClass methodIns) {
		Individual ans = null;
		if (methodIns != null) {		
			RDFNode temp = getRelatedRDFNode(methodIns, PML_HAS_SOURCE_URI);
			if (temp != null) {
				ans = temp.as(Individual.class);
			}
		}
		return ans;
	}
	
	/**
	 * 
	 * @param methodInd
	 * @return
	 */
	public static Ontology getDetailedBy(OntClass methodInd) {
		Ontology ans = null;
		if (methodInd != null) {		
			RDFNode temp = getRelatedRDFNode(methodInd, IS_DETAILED_BY_URI);
			if (temp != null) {
				ans = temp.as(Ontology.class);
			}
		}
		return ans;
	}
	
	/**
	 * List instances included in saw that have the specified type
	 * If type not specified, returns all instances of the saw
	 * If subtypes, also return instances of subclasses of type
	 * @param saw 
	 * @param type
	 * @param subtypes
	 * @return iterator of instances; if saw and type are null, an empty iterator is returned.
	 */
	public static Iterator<OntClass> listSAWInstances(OntModel saw, OntClass type, boolean subtypes) {
		ArrayList<OntClass> ans = new ArrayList<OntClass>();
		if (saw != null) {
			String ns = null;
			OntClass sawsawIns = getSAWSAWInstance(saw);
			if (sawsawIns != null) {
				ns = URI.getNameSpace(sawsawIns.getURI());
			}
			// if type is not null, apply type filter on search
			if (type != null && ns != null) {
				for (ExtendedIterator<OntClass> i=saw.listNamedClasses(); i.hasNext(); ) {
					OntClass ins = i.next();
					if (saw.isInBaseModel(ins) && isOfType(type, ins, subtypes)) {
						if (ns.equals(URI.getNameSpace(ins.getURI()))) {
							ans.add(ins);
						}
					}
				}
			}
			// if type is null and a saw was specified, return all individuals of saw 
			else if (type == null) {
				for (ExtendedIterator<OntClass> i = saw.listNamedClasses(); i.hasNext(); ) {
					OntClass ins = i.next();
					if (saw.isInBaseModel(ins)) {
						if (ns.equals(URI.getNameSpace(ins.getURI()))) {
							ans.add(ins);
						}	
					}
				}	
			}	
		}
		return ans.iterator();
	}
	
	/**
	 * List instances of subclasses of wdo:Method included in saw
	 * @param saw 
	 * @return iterator of instances
	 */
	public static Iterator<OntClass> listMethodSAWInstances(OntModel saw) {
		OntClass type = saw.getOntClass(METHOD_URI);
		return listSAWInstances(saw, type, true);
	}
	
	/**
	 * List instances of subclasses of pmlp:Source included in saw
	 * @param saw
	 * @return
	 */
	public static Iterator<OntClass> listSourceSAWInstances(OntModel saw) {
		OntClass type = saw.getOntClass(SOURCE_URI);
		return listSAWInstances(saw, type, true);
	}
	
	/**
	 * List instances of subclasses of wdo:Data included in saw
	 * @param saw
	 * @return iterator of instances
	 */
	public static Iterator<OntClass> listDataSAWInstances(OntModel saw) {
		OntClass type = saw.getOntClass(DATA_URI);
		return listSAWInstances(saw, type, true);
	}
	
	public static OntModel readSAW(String url) {
		OntModelSpec ontModelSpec;
		OntModel saw = null;
		if (url != null) {
			// initialize the ontology document manager
			OntDocumentManager docmgr = OntDocumentManager.getInstance();
			docmgr.reset(true);
			docmgr.clearCache();
			docmgr.setProcessImports(true);
			docmgr.setCacheModels(true);
			
			// initialize the file manager to cache ontologies
			FileManager fmgr = FileManager.get();
			fmgr.resetCache();
			fmgr.setModelCaching(true);
			docmgr.setFileManager(fmgr);
			
			// initialize the ontology specification to use for ontology models
			ontModelSpec = new OntModelSpec(OntModelSpec.OWL_DL_MEM_TRANS_INF);
			ontModelSpec.setDocumentManager(docmgr);
			
			// Load ontologies in order of dependence: Wdo imports PMLP, PMLP imports DS
			// Therefore, load DS, then PMLP, then Wdo
			OntModel owl = ModelFactory.createOntologyModel(ontModelSpec);
			OntModel ds = ModelFactory.createOntologyModel(ontModelSpec);
			OntModel pmlp = ModelFactory.createOntologyModel(ontModelSpec);
			OntModel wdo = ModelFactory.createOntologyModel(ontModelSpec);
			
			// if files deployed within a jar, use the getResourceAsStream method to read ontology
			// otherwise, read from file system directly
			
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			InputStream inStream;
			
			inStream = cl.getResourceAsStream("OWL_FILE");
			if (inStream != null) {
				owl.read(inStream, null);
			}
			else {
				owl.read("file:lib/" + OWL_FILE, OWL_URI, null);
			}
			
			inStream = cl.getResourceAsStream(DS_FILE);
			if (inStream != null) {
				ds.read(inStream, null);
			}
			else {
				ds.read("file:lib/" + DS_FILE, DS_URI, null);
			}
			ds.addSubModel(owl, true);
			
			inStream = cl.getResourceAsStream(PMLP_FILE);
			if (inStream != null) {
				pmlp.read(inStream, null);
			}
			else {
				pmlp.read("file:lib/" + PMLP_FILE, PMLP_URI, null);
			}
			pmlp.addSubModel(ds, true);
			
			inStream = cl.getResourceAsStream(WDO_FILE);
			if (inStream != null) {
				wdo.read(inStream, null);
			}
			else {
				wdo.read("file:lib/" + WDO_FILE, WDO_URI, null);
			}
			wdo.addSubModel(pmlp, true);
			
			docmgr.addModel(OWL_URI, owl);
			docmgr.addModel(DS_URI, ds);
			docmgr.addModel(PMLP_URI, pmlp);
			docmgr.addModel(WDO_URI, wdo);
			
			docmgr.addIgnoreImport(OWL_URI);
			docmgr.addIgnoreImport(DS_URI);
			docmgr.addIgnoreImport(PMLP_URI);
			docmgr.addIgnoreImport(WDO_URI);
			
			docmgr.addAltEntry(OWL_URI, OWL_FILE);
			docmgr.addAltEntry(DS_URI, DS_FILE);
			docmgr.addAltEntry(PMLP_URI, PMLP_FILE);
			docmgr.addAltEntry(WDO_URI, WDO_FILE);
			
			// load wdo
			saw = ModelFactory.createOntologyModel(ontModelSpec);
			saw.read(url);
			saw.addSubModel(wdo, true);
			setImports(saw, ontModelSpec);
			
			docmgr.addModel(url, saw);
			docmgr.addIgnoreImport(url);
		}
		return saw;
	}
	
	private static void setImports(OntModel ontmodel, OntModelSpec ontModelSpec) {
		Set<String> importedURIs = ontmodel.listImportedOntologyURIs(true);
		for (Iterator<String> i = importedURIs.iterator(); i.hasNext(); ) {
			String importedURI = i.next();
			OntModel importedDoc = getOntModel(importedURI, ontModelSpec);
			if (importedDoc == null) {
				importedDoc = ModelFactory.createOntologyModel(ontModelSpec);
				importedDoc.read(importedURI);
			}
			ontmodel.addSubModel(importedDoc, false);	
		}
		ontmodel.rebind();
	}
	
	public static OntModel getOntModel(String uri, OntModelSpec ontModelSpec) {
		// some URIs are encoded with an ending #. Remove it for comparison
		String temp = uri.split("#")[0];
		Model model = ontModelSpec.getDocumentManager().getModel(temp);
		// if not found, try again with # included in the end
		if (model == null) {
			model = ontModelSpec.getDocumentManager().getModel(temp + "#");
		}
		OntModel ontmodel = null;
		if (model != null) {
			try {
				ontmodel = (OntModel) model;
			}
			catch (Exception e) {
				ontmodel = ModelFactory.createOntologyModel(ontModelSpec, model);
			}	
		}
		return ontmodel;
	}
}
