package edu.rpi.tw.escience.waterquality.test;

//import com.google.common.io.CharStreams;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;

//import edu.rpi.tw.escience.WaterQualityPortal.WebService.WaterAgentInstance;
import edu.rpi.tw.escience.waterquality.Module;
import edu.rpi.tw.escience.waterquality.ModuleConfiguration;
import edu.rpi.tw.escience.waterquality.QueryMethod;
import edu.rpi.tw.escience.waterquality.Request;
import edu.rpi.tw.escience.waterquality.Resource;
import edu.rpi.tw.escience.waterquality.SemantAquaUI;
import edu.rpi.tw.escience.waterquality.query.Query;
import edu.rpi.tw.escience.waterquality.query.Query.Type;
import org.apache.http.client.methods.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;




public class testFacet implements Module{

	private static final String POL_NS = "http://escience.rpi.edu/ontology/semanteco/2/0/pollution.owl#";
	private static final String WATER_NS = "http://escience.rpi.edu/ontology/semanteco/2/0/water.owl#";
	private static final String RDFS_NS = "http://www.w3.org/2000/01/rdf-schema#";
	private static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	private static final String UNIT_NS = "http://sweet.jpl.nasa.gov/2.1/reprSciUnits.owl#";
	//private static final String TIME_NS = "http://www.w3.org/2006/time#";
	private static final String GEOSPECIES_NS = "http://rdf.geospecies.org/ont/geospecies.owl#";
	public static final String  TXN_NS = "http://lod.taxonconcept.org/ontology/txn.owl";
	private static final String WILDLIFE_NS = "http://www.semanticweb.org/ontologies/2012/2/wildlife.owl#";
	private static final String HEALTHEFFECT_NS = "http://escience.rpi.edu/ontology/semanteco/2/0/healtheffect.owl";
	private static final String PROV_NS = "http://www.w3.org/ns/prov#";
	private static final String DC_NS = "http://purl.org/dc/terms/";
	private static final String OWL_NS = "http://www.w3.org/2002/07/owl#";
	private static final String XSD_NS = "http://www.w3.org/2001/XMLSchema#";
	private static final String WGS_NS = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	private static final String FAILURE = "{\"success\":false}";
	private ModuleConfiguration config = null;
	private static final String BINDINGS = "bindings";
	private Logger log = Logger.getLogger(testFacet.class);
	private static final String SOURCE_VAR = "source";
	private static final String LABEL_VAR = "label";
	private static final String VALUE = "value";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public testFacet(final Request request, final ModuleConfiguration config){
		//super(request, config);
		
	}
	
	
	public void visit (OntModel model, Request request){}
	
	@Override
	public void visit(Query query, Request request) {
		
	}
//: )

	@Override
	public void visit(Model model, Request request) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visit(SemantAquaUI ui, Request request) {
		Resource res = null;
		res = config.getResource("test.js");
		ui.addFacet(res);
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getExtraVersion() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setModuleConfiguration(ModuleConfiguration config) {
		// TODO Auto-generated method stub
		this.config = config;
	}
	
	@QueryMethod
	public String queryBirdPopulation(Request request){
		
		final String siteUri = (String)request.getParam("organism");
		
		final Query query = config.getQueryFactory().newQuery(Type.SELECT);
		return null;
		
	}
	

	
	@QueryMethod
	public String queryBirdTaxonomy(Request request) throws IOException, JSONException{
		
		/*
		data.put(new JSONObject().put("Animalia", "Kingdom"));
		data.put(new JSONObject().put("Chordata", "Phylum"));
		data.put(new JSONObject().put("Aves", "Class"));
		data.put(new JSONObject().put("Passeriformes", "Order"));
		data.put(new JSONObject().put("Falconiformes", "Order"));
		data.put(new JSONObject().put("Fringillidae", "Family"));
		data.put(new JSONObject().put("Accipitridae", "Family"));
		data.put(new JSONObject().put("Acanthis", "Species"));
		data.put(new JSONObject().put("Accipiter", "Species"));
	    */	
		JSONArray data = new JSONArray();
		data.put(new JSONObject().put("Acanthis", "Fringillidae"));
		data.put(new JSONObject().put("Fringillidae", "Passeriformes"));
		data.put(new JSONObject().put("Passeriformes", "Aves"));
		data.put(new JSONObject().put("Aves", "Chordata"));
		data.put(new JSONObject().put("Chordata", "Animalia"));
		
		data.put(new JSONObject().put("Accipiter", "Accipitridae"));
		data.put(new JSONObject().put("Accipitridae", "Falconiformes"));
		data.put(new JSONObject().put("Falconiformes", "Aves"));
		data.put(new JSONObject().put("Aves", "Chordata"));
		data.put(new JSONObject().put("Chordata", "Animalia"));
		return data.toString();

		/*
		String endpoint = "";
		final Query query = config.getQueryFactory().newQuery(Type.SELECT);
		
		String responseStr = FAILURE;
		config.getQueryExecutor(request).accept("application/json").execute(query);
		String resultStr = config.getQueryExecutor(request).execute(endpoint, query);
		log.debug("Results: "+resultStr);
		if(resultStr == null) {
			return responseStr;
		}
		try {
			JSONObject results = new JSONObject(resultStr);
			JSONObject response = new JSONObject();
			JSONArray data = new JSONArray();
			response.put("success", true);
			response.put("data", data);
			results = results.getJSONObject("results");
			JSONArray bindings = results.getJSONArray(BINDINGS);
			for(int i=0;i<bindings.length();i++) {
				JSONObject binding = bindings.getJSONObject(i);
				String sourceUri = binding.getJSONObject(SOURCE_VAR).getString(VALUE);
				String labelStr = null;
				try {
					labelStr = binding.getJSONObject(LABEL_VAR).getString(VALUE);
				}
				catch(Exception e) { }
				if(labelStr == null) {
					labelStr = sourceUri.substring(sourceUri.lastIndexOf('/')+1).replace('-', '.');
				}
				JSONObject mapping = new JSONObject();
				mapping.put("uri", sourceUri);
				mapping.put(LABEL_VAR, labelStr);
				data.put(mapping);
			}
			responseStr = response.toString();
		} catch (JSONException e) {
			log.error("Unable to parse JSON results", e);
		}
		return responseStr;
		*/
		
	


		
	}
}