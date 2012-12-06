package org.vkedco.mobappdev.object_ser_deser_00001;

/*
 ********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com 
 ********************************************************
 */

import java.io.Serializable;
import java.util.LinkedList;

class MoveUtility implements Serializable {
	private static final long serialVersionUID = 1L;
	short mPos;
	short mUtil;
	
	public MoveUtility(short pos, short util) {
		mPos = pos;
		mUtil = util;
	}
}

class BoardUtility implements Serializable {
	private static final long serialVersionUID = 1L;
	String mPlayer;
	LinkedList<MoveUtility> mMoveUtils;
	
	public BoardUtility(String player) {
		mPlayer = player;
		mMoveUtils = new LinkedList<MoveUtility>();
	}
	
	void addMoveUtility(MoveUtility mu) {
		mMoveUtils.add(mu);
	}
}
