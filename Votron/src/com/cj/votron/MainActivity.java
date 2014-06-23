package com.cj.votron;

import java.util.ArrayList;
import java.util.List;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;



/**********************************************************
 * 
 * @author gvamos
 * Todo:
 *   - How can I tell what sort of a JSON object am I holding (List, Hash,...?)
 *   - Do I need a unique parse process for each data type? (Voter, Election, Candidate...?)
 *
 **********************************************************/
public class MainActivity extends ActionBarActivity {
	
	private Spinner electionSpinner;
	List<String> electionsList;
	
	private Spinner voterSpinner;
	List<String> voterList;
	
	private Button btnConfig;
		
	public void addElectionsToSpinner(){
		
		electionSpinner = (Spinner) findViewById(R.id.electionSpinner);
		
		electionsList = new ArrayList<String>();
		electionsList.add("Federal");
		electionsList.add("State");
		electionsList.add("Local");
		ArrayAdapter<String> electionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,electionsList);
		electionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		electionSpinner.setAdapter(electionAdapter);
				
	}

	//add items into spinner dynamically
	public void addVotersToSpinner() {

		voterSpinner = (Spinner) findViewById(R.id.voterSpinner);
		
		voterList = new ArrayList<String>();
		voterList.add("Andy Adams");
		voterList.add("Billy Barnacle");
		voterList.add("Charlie Carbuncle");
		ArrayAdapter<String> voterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,voterList);
		voterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		voterSpinner.setAdapter(voterAdapter);
	}
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        

	    addElectionsToSpinner();
	    addVotersToSpinner();

	    
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
    }
    
	public void addListenerOnSpinnerItemSelection(){
		
		electionSpinner = (Spinner) findViewById(R.id.electionSpinner);				
		electionSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		
	}
    
	//get the selected dropdown list value
	public void addListenerOnButton() {

		electionSpinner = (Spinner) findViewById(R.id.electionSpinner);
		voterSpinner = (Spinner) findViewById(R.id.voterSpinner);
		
		btnConfig = (Button) findViewById(R.id.btnConfig);
		btnConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(MainActivity.this,
						"OnClickListener : " + 
						"\nElectionSpinner 1 : " + String.valueOf(electionSpinner.getSelectedItem()) +
						"\nVoterSpinner 2 : " + String.valueOf(voterSpinner.getSelectedItem()),
						Toast.LENGTH_SHORT).show();
			}

		});

	}
    

	



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
    	getMenuInflater().inflate(R.menu.actions, menu);
    	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        System.out.println("DBG: onOptionsItemSelected:" + id);
        Intent intent = null;
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.voters:
            	System.out.println("DBG: Voters Activity");
            	intent = new Intent(this, VotersActivity.class);
              break;
            case R.id.elections:
            	intent = new Intent(this, ElectionsActivity.class);
              break;
            case R.id.debug:
            	intent = new Intent(this, DebugActivity.class);
              break;
            default:
            	Log.i(this.getClass().getName(),":onOptionsItemSelected default:" + id);
                return super.onOptionsItemSelected(item);
        }
        if (null != intent){
        	System.out.println("DBG: intent action=" + intent.getAction());
        	startActivity(intent);
        }
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
