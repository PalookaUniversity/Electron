package com.cj.votron;

import java.util.ArrayList;

import android.widget.ArrayAdapter;

class Config {
		
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
	
	String debug1(String arg){
		String result = ServerLink.getInstance().getPageText("http://devops102.wl.cj.com:12345/elections");
		System.out.println(result);
		return "Debug 1 called:"+arg;
	}
	
	String debug2(String arg){
		String dbpq = "select*%7Bdbpedia%3ALos_Angeles+rdfs%3Alabel+%3Flabel%7D";
		String result = ServerLink.getInstance().getDbpediaQuery(dbpq);
		System.out.println(result);
		return result;
	}
	
	String debug3(String arg){
		return "Debug 3 called:"+arg;
	}
	
	static Config instance = new Config();
	static Config getInstance(){ return instance; }
	private Config(){}	
}
