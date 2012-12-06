package org.vkedco.mobappdev.object_ser_deser_00001;

/*
 ***********************************************************************
 * This project shows how to do object serialization/deserialization
 * via AsyncTask. The user can start a 2D Tic-Tac-Toe frame animation 
 * by selecting "Animate" on the options menu. 
 * 
 * As the animation is playing on the main UI thread, the user can click on 
 * "Serialize" to spawn a serialization AsyncTask off the main UI thread. 
 * When the serialization task finishes with a Toast, the user can select 
 * "Deserialize" to spawn a deserialization AsyncTask off the main UI thread.
 * 
 * The serialization AsyncTask (TTTGameStateTreeSerializer.java) inflates two 
 * TreeMap<String, BoardUtility> objects from assets/large_game_state_tree.txt 
 * and assets/small_game_tree.txt and then serializes both TreeMap<String, BoardUtility> 
 * objects onto the sdcard. Both text files encode Tic-Tac-Toe board utility trees.
 * The file small_game_state_tree.txt encodes a small incomplete tree.  The
 * file large_game_state_tree.txt encodes a game tree that contains 5478 states.
 * Each line has the following format:
 * 
 * x???o????;X;1:0 2:0 3:0 5:0 6:0 7:0 8:0
 * 
 * "x???0????" encodes the following board state ('?' stands for 'empty'):
 *
 * -------------
 * | x | ? | ? |
 * -------------
 * | ? | o | ? |
 * -------------
 * | ? | ? | ? |
 * -------------
 * 
 * 'X' following the board state encoding denotes the player whose turn it is to
 * play on this board state. The string "1:0 2:0 3:0 5:0 6:0 7:0 8:0" encodes
 * the move positions available to each X and their utilities (there are three 
 * possible utilities: -1 (loss for X), 0 (draw), 1 (win for X)).
 * 
 * The deserializaton AsyncTask (TTTGameStateTreeDeserializer.java) two TreeMap<String, BoardUtility> 
 * objects from sdcard files off the main UI thread. When an object is deserialized a Toast 
 * is shown on the main UI thread.
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com 
 ***********************************************************************
 */

import java.io.File;
import java.util.Random;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ObjectSerDeserAct extends Activity {
	// SER_FILE_DIRECTORY should be something like "/mnt/sdcard/".
	final static String SER_FILE_DIRECTORY         = Environment.getExternalStorageDirectory().getPath() + "/object_ser_deser/";
	final static String SMALL_GAME_STATE_TREE_FILE = "small_game_state_tree_map.ser";
	final static String LARGE_GAME_STATE_TREE_FILE = "large_game_state_tree_map.ser";
	final static String SMALL_GAME_STATE_TREE_ASSETS_FILE = "small_game_state_tree.txt";
	final static String LARGE_GAME_STATE_TREE_ASSETS_FILE = "large_game_state_tree.txt";
	
	File mSmallGameStateTreeSerFile = null;
	File mLargeGameStateTreeSerFile = null;
	LRPair<String, File> mSerPair01 = null;
	LRPair<String, File> mSerPair02 = null;
	
	ImageView mImgViewFrameAnimator = null;
	AnimationDrawable mAnimDraw     = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_ser_deser_act_layout);
        setUpSerFiles();
        mImgViewFrameAnimator = (ImageView) this.findViewById(R.id.img_view_frame);
    	mImgViewFrameAnimator.setBackgroundDrawable(null);
    }

    @SuppressWarnings("unchecked")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
		case R.id.menu_item_ser: 
			new TTTGameStateTreeSerializer(this).execute(mSerPair01, mSerPair02);
			return true;
		case R.id.menu_item_deser:
			new TTTGameStateTreeDeserializer(this).execute(mSmallGameStateTreeSerFile, mLargeGameStateTreeSerFile);
			return true;
		case R.id.menu_item_anim:
			switch ( new Random().nextInt(3) ) {
			case 0: animateTicTacToe_O_WINS(); break;
			case 1: animateTicTacToe_X_WINS(); break;
			case 2: animateTicTacToe_X_O_DRAW(); break;
			default: break;
			}
			return true;
		default: return true; 
		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.object_ser_deser_act_layout, menu);
        return true;
    }
    
    void setUpSerFiles() {
		File ser_dir = new File(SER_FILE_DIRECTORY);
		//Toast.makeText(this, ser_dir.getAbsolutePath(), Toast.LENGTH_LONG).show();
		ser_dir.mkdirs();
		mSmallGameStateTreeSerFile = new File(SER_FILE_DIRECTORY + SMALL_GAME_STATE_TREE_FILE);
		mLargeGameStateTreeSerFile = new File(SER_FILE_DIRECTORY + LARGE_GAME_STATE_TREE_FILE);
		mSerPair01 = new LRPair<String, File>("small_game_state_tree.txt", mSmallGameStateTreeSerFile);
		mSerPair02 = new LRPair<String, File>("large_game_state_tree.txt", mLargeGameStateTreeSerFile);
	}
    
    void animateTicTacToe_O_WINS() {
    	mImgViewFrameAnimator.setBackgroundResource(R.drawable.tic_tac_toe_o_wins_animation_list);
        mImgViewFrameAnimator.setVisibility(ImageView.VISIBLE);
        mAnimDraw = (AnimationDrawable) mImgViewFrameAnimator.getBackground();
    	if ( mAnimDraw.isRunning() ) mAnimDraw.stop();
    	mAnimDraw.start();
    }
    
    void animateTicTacToe_X_WINS() {
    	mImgViewFrameAnimator.setBackgroundResource(R.drawable.tic_tac_toe_x_wins_animation_list);
    	mImgViewFrameAnimator.setVisibility(ImageView.VISIBLE);
        mAnimDraw = (AnimationDrawable) mImgViewFrameAnimator.getBackground();
    	if ( mAnimDraw.isRunning() ) mAnimDraw.stop();
    	mAnimDraw.start();
    }
    
    void animateTicTacToe_X_O_DRAW() {
    	mImgViewFrameAnimator.setBackgroundResource(R.drawable.tic_tac_toe_x_o_draw_animation_list);
    	mImgViewFrameAnimator.setVisibility(ImageView.VISIBLE);
        mAnimDraw = (AnimationDrawable) mImgViewFrameAnimator.getBackground();
    	if ( mAnimDraw.isRunning() ) mAnimDraw.stop();
    	mAnimDraw.start();
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		if ( mAnimDraw == null ) return;
		if ( mAnimDraw.isRunning() ) mAnimDraw.stop();
		mAnimDraw = null;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if ( mAnimDraw == null ) return;
		if ( mAnimDraw.isRunning() ) mAnimDraw.stop();
		mAnimDraw = null;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if ( mAnimDraw == null ) return;
		if ( mAnimDraw.isRunning() ) mAnimDraw.stop();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if ( mAnimDraw == null ) return;
		if ( !mAnimDraw.isRunning() ) mAnimDraw.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if ( mAnimDraw == null ) return;
		if ( mAnimDraw.isRunning() ) mAnimDraw.stop();
	}
  
}
