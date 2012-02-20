/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package probeIt.ui.avatar.queries;

import java.util.*;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;

import probeIt.ui.avatar.*;

/**
 * @author paulo
 */
public class ProductsByType {

	private edu.utep.trust.provenance.RDFStore proxy;
	
    public ProductsByType() {
    	edu.utep.trust.provenance.RDFStore_Service service = new edu.utep.trust.provenance.RDFStore_Service();
    	proxy = service.getRDFStoreHttpPort();
    }
    
    public String doQuery(String typeURI) {
    	String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
    	"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
    	"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
    	"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
    	"PREFIX pmlj: <http://inference-web.org/2.0/pml-justification.owl#>" +
    	"PREFIX pmlp: <http://inference-web.org/2.0/pml-provenance.owl#>" +
    	"PREFIX pml-sparql: <http://trust.utep.edu/sparql-pml#>" +
    	"PREFIX ds: <http://inference-web.org/2.0/ds.owl#>" +
        "SELECT ?NodeSet WHERE {" +
        "?NodeSet pmlj:hasConclusion ?Information . " +
        "?Information a <" + typeURI + "> . " +
    	"}";
	
    	String products_pml = proxy.doQueryForPMLQuery(query);
    	//System.out.println(products_pml);
    	return products_pml;  // returns the URI of a PML query

    	//ResultSet results = ResultSetFactory.fromXML(pml_person);
    }

}
