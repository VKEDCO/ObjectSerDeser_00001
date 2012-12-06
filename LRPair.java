package org.vkedco.mobappdev.object_ser_deser_00001;

public class LRPair<LEFT_T, RIGHT_T> {
    private LEFT_T mLeft;
    private RIGHT_T mRight;
    final static String LB = "<";
    final static String RB = ">";
    final static String COMMA = ", ";

    public LRPair(LEFT_T left, RIGHT_T right) {
    	super();
    	mLeft  = left; mRight = right;
    }

    public int hashCode() {
    	int hashL, hashR;
    	if ( mLeft  != null ) hashL = mLeft.hashCode();  else hashL = 0;
    	if ( mRight != null ) hashR = mRight.hashCode(); else hashR = 0;
    	return (hashL + hashR) * hashR + hashL;
    }

    public boolean equals(Object o) {
    	if (o instanceof LRPair) {
    		@SuppressWarnings("unchecked")
			LRPair<RIGHT_T, LEFT_T> pair = (LRPair<RIGHT_T, LEFT_T>) o;
    		return 
    		((  mLeft == pair.getLeft() ||
    			( mLeft != null && pair.getLeft() != null &&
    			  mLeft.equals(pair.getLeft()))) &&
    		 (	mRight == pair.getRight() ||
    			( mRight != null && pair.getRight() != null &&
    			  mRight.equals(pair.getRight()))) );
    	}

    	return false;
    }

    public String toString() { return LB + mLeft + COMMA + mRight + RB; }

    public LEFT_T getLeft() { return mLeft; }
    public void setLeft(LEFT_T left) { mLeft = left; }

    public RIGHT_T getRight() { return mRight; }
    public void setRight(RIGHT_T right) { mRight = right; }
}
