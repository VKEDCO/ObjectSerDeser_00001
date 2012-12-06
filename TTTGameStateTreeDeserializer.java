package org.vkedco.mobappdev.object_ser_deser_00001;

/*
 *********************************************************************
 * Asynchronous task that deserializes two TreeMap<String, BoardUtility>
 * objects from sdcard. When an object is deserialized a Toast is shown on
 * the main UI thread.
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com 
 *********************************************************************
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.TreeMap;
import android.os.AsyncTask;
import android.widget.Toast;

class TTTGameStateTreeDeserializer extends AsyncTask<File, Void, String>
{
	LinkedList<TreeMap<String, BoardUtility>> mGameStateTreeMaps = null;
	ObjectSerDeserAct mThisAct = null;
	
	@Override
	protected void onPreExecute() {
		mGameStateTreeMaps = new LinkedList<TreeMap<String, BoardUtility>>();
	}
	
	TTTGameStateTreeDeserializer(ObjectSerDeserAct act) {
		mThisAct = act;
	}
	
	@Override
	protected String doInBackground(File... params) {
		
		for(File f: params)
			deserializeGameStateTree(f);
		StringBuilder sb = new StringBuilder();
		int map_counter = 0;
		for(TreeMap<String, BoardUtility> tm: mGameStateTreeMaps) {
			sb.append("Deserialized Map " + map_counter + " has " + tm.size() + " states\n");
			map_counter++;
		}
		return sb.toString();
	}
	
	@Override
	protected void onPostExecute(String rslt) {
		Toast.makeText(mThisAct, rslt, Toast.LENGTH_LONG).show();
	}
	
	@SuppressWarnings("unchecked")
	private TreeMap<String, BoardUtility> deserializeGameStateTree(File game_state_tree_file) {
		TreeMap<String, BoardUtility> game_state_tree_map = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(game_state_tree_file));
			game_state_tree_map = (TreeMap<String, BoardUtility>) ois.readObject();
			mGameStateTreeMaps.add(game_state_tree_map);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( ois != null)
					ois.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return game_state_tree_map;
	}
	
	LinkedList<TreeMap<String, BoardUtility>> getGameStateTreeMaps() {
		return this.mGameStateTreeMaps;
	}
}
