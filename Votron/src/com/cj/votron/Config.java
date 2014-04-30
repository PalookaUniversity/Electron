package com.cj.votron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.widget.ArrayAdapter;

class Config {
	
	Map<String,String>parameters = new HashMap<String,String>();
	public void setParam(String attr, String val){
		parameters.put(attr,val);
	}
	
	public String getParam(String attr){  return parameters.get(attr); }
	
		
	class Elections {

	  private ArrayList<String> electionsList = new ArrayList<String>();  		
		public ArrayList<String> getElectionsList() { return electionsList; }
		
		public void addElections(String electionName){ electionsList.add(electionName); }
		
		public void updateElections(){
			electionsList.add("London");
			electionsList.add("Paris");
			electionsList.add("Chatsworth");
		}
	}
	
	class Voters {

	  private ArrayList<String> votersList = new ArrayList<String>();  		
		public ArrayList<String> getVotersList() { return votersList; }
		
		public void addVoter(String electionName){ votersList.add(electionName); }
		
		public void updateVoters(){
			votersList.add("Larry");
			votersList.add("Moe");
			votersList.add("Curly");
		}
	}	
	
	private Elections elections = new Elections();
	Elections getElections() { return elections;}
		
	private Voters voters = new Voters();
	Voters getVoters() { return voters;}
	
	String debug1(String arg, Activity activity){
		String dbpq = "select*%7Bdbpedia%3ALos_Angeles+rdfs%3Alabel+%3Flabel%7D";
		ServerLink.getInstance().getDbpediaQuery(dbpq, activity);
		String result = "dbg1 tried";
		System.out.println(result);
		return result;
	}
	
	String debug2(String arg, Activity activity){
//		String result = ServerLink.getInstance().getPageText(
//				"http://devops102.wl.cj.com:12345/elections", activity);
		String result = "debug2 unused";
		System.out.println(result);
		return result +arg;
	}
	

	
	String debug3(String arg, Activity activity){
		return "Debug 3 called:"+arg;
	}
	
	static Config instance = new Config();
	static Config getInstance(){ return instance; }
	private Config(){}	
}
