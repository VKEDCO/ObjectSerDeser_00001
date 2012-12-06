package org.vkedco.mobappdev.object_ser_deser_00001;

/*
 *********************************************************************
 * Asynchronous task that inflates two TreeMap<String, BoardUtility> 
 * objects from assets/large_game_state_tree.txt and assets/small_game_tree.txt
 * and then serializes both TreeMap<String, BoardUtility> objects to
 * the sdcard.
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com 
 *********************************************************************
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class TTTGameStateTreeSerializer extends AsyncTask<LRPair<String, File>, Void, String> {
	
	ObjectSerDeserAct mThisAct = null;
	
	TTTGameStateTreeSerializer(ObjectSerDeserAct act) {
		mThisAct = act;
	}
	
	@Override
	protected void onPostExecute(String rslt) {
		Toast.makeText(mThisAct, rslt, Toast.LENGTH_LONG).show();
	}

	@Override
	protected String doInBackground(LRPair<String, File>... params) {
		
		StringBuilder sb = new StringBuilder();
		int map_counter = 0;
		TreeMap<String, BoardUtility> game_tree_map = null;
		for(LRPair<String, File> pair: params) {
			String assets_file = pair.getLeft();
			game_tree_map = inflateGameStateTreeFromAssetsTextFile(assets_file);
			sb.append("Serialized Map " + map_counter + " has " + game_tree_map.size() + " states\n");
			serializeGameStateTreeMap(game_tree_map, pair.getRight());
			map_counter++;
		}
		return sb.toString();
	}
	
	TreeMap<String, BoardUtility> inflateGameStateTreeFromAssetsTextFile(String file_path) {
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = mThisAct.getResources().getAssets()
					.open(file_path, Context.MODE_WORLD_READABLE);
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			TreeMap<String, BoardUtility> board_map = new TreeMap<String, BoardUtility>();
			while ((line = br.readLine()) != null) {
				processGameStateLine(line, board_map);
			}
			//Log.d("DBG", "number of states = " + board_map.size());
			return board_map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (is != null)
					is.close();
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}
	
	private void processGameStateLine(String txt, TreeMap<String, BoardUtility> board_map) {
		//Log.d("LINE", txt);
		if (txt == null)
			return;

		String[] parts = txt.trim().split(";");
		if (parts != null && parts.length == 3) {
			String board      = parts[0];
			String player     = parts[1];
			String move_utils = parts[2];
			BoardUtility bu = new BoardUtility(player);
			String[] move_util_array = move_utils.split(" ");
			if ( move_util_array.length == 1 && !move_util_array[0].contains(":") ) {
				bu.addMoveUtility(new MoveUtility((short)10, Short.parseShort(move_util_array[0])));
			}
			else {
				for (String mu : move_util_array) {
				String[] mu_parts = mu.split(":");
				bu.addMoveUtility(new MoveUtility(
						Short.parseShort(mu_parts[0]), Short
								.parseShort(mu_parts[1])));
				}
			}
			board_map.put(board, bu);
		}
	}
	
	void serializeGameStateTreeMap(TreeMap<String, BoardUtility> tree, File file) {
		ObjectOutputStream oos = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(tree);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	

}
