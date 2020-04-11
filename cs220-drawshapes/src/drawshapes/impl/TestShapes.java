package drawshapes.impl;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
public class TestShapes {
	BoundingBox mb = new BoundingBox(2, 4, 2, 4); //main box, all boxes will be relative to this - 1 ^ 0 means outside of box (less than), 3 means inside the box, 5 ^ 6  means outside the box (greater than)
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@Test
	public void testContains() { //this checks all 9 contains sectors
		assertFalse(mb.contains(1, 1)); //top left
		assertFalse(mb.contains(3, 1)); //top mid
		assertFalse(mb.contains(5, 1)); //top right
		assertFalse(mb.contains(1, 3)); //mid right
		assertTrue(mb.contains(3, 3)); //mid mid - this one should be the true one
		assertFalse(mb.contains(5, 3)); //mid right
		assertFalse(mb.contains(1, 5)); //bottom left
		assertFalse(mb.contains(3, 5)); //bottom mid
		assertFalse(mb.contains(5, 5)); //bottom right
		
	}
	@Test
	public void testIntersectsTrue() { //this contains all intersecting equations - 16 in total (view last 7)
		BoundingBox tl = new BoundingBox(1, 3, 1, 3); //top left does intersect
		BoundingBox tm = new BoundingBox(1, 5, 1, 3); //top mid (left and right)
		BoundingBox tr = new BoundingBox(3, 5, 1, 3); //top right
		BoundingBox lm = new BoundingBox(1, 3, 1, 5); //left mid
		BoundingBox lb = new BoundingBox(1, 3, 3, 5); //left bottom
		BoundingBox bm = new BoundingBox(1, 5, 3, 5); //bottom middle
		BoundingBox br = new BoundingBox(3, 5, 3, 5); //bottom right
		BoundingBox rm = new BoundingBox(3, 5, 1, 5); //right middle
		BoundingBox mm = new BoundingBox(1, 5, 1, 5); //Middle Middle (MM is the bigger one)
		BoundingBox CLR = new BoundingBox(1, 5, 3, 3); //goes through left to right, touches no corners
		BoundingBox CTB = new BoundingBox(3, 3, 1, 5); //goes through top to bottom, touches no corners
		BoundingBox l = new BoundingBox(1, 3, 3, 3); //touches only left and middle 
		BoundingBox r = new BoundingBox(3, 5, 3, 3); //touches only right and middle
		BoundingBox t = new BoundingBox(3, 3, 1, 3); //touches only top and middle
		BoundingBox b = new BoundingBox(3, 3, 3, 5); //touches only bottom and middle
		BoundingBox m = new BoundingBox(3, 3, 3, 3); // everything is inside of the BB
		assertTrue(mb.intersect(tl));
		assertTrue(mb.intersect(tm));
		assertTrue(mb.intersect(tr));
		assertTrue(mb.intersect(lm));
		assertTrue(mb.intersect(lb));
		assertTrue(mb.intersect(bm));
		assertTrue(mb.intersect(br));
		assertTrue(mb.intersect(rm));
		assertTrue(mb.intersect(mm));
		assertTrue(mb.intersect(CLR));
		assertTrue(mb.intersect(CTB));
		assertTrue(mb.intersect(l));
		assertTrue(mb.intersect(r));
		assertTrue(mb.intersect(t));
		assertTrue(mb.intersect(b));
		assertTrue(mb.intersect(m));
	}
	
	@Test	//this contains all non-intersecting equations - 16 ways in total -- footnote, if box does not contain c it means it is both the midpoint and the second letters points
	public void testIntersectsFalse() { //if it contains two m (as to note be the same as the intersecting names), it is all inclusive of the first letters section, for example tm is the top right, top mid, and top left section
		BoundingBox trc = new BoundingBox(5, 6, 0, 1); //top right corner
		BoundingBox trm = new BoundingBox(3, 5, 0, 1); //top right mid
		BoundingBox tmm = new BoundingBox(1, 5, 0, 1); //top mid
		BoundingBox tlm = new BoundingBox(1, 3, 0, 1); //top left mid
		BoundingBox tlc = new BoundingBox(0, 1, 0, 1); //tables, ladders & chairs
		BoundingBox ltm = new BoundingBox(0, 1, 1, 3); //left top mid
		BoundingBox lmm = new BoundingBox(0, 1, 1, 5); //left mid
		BoundingBox lbm = new BoundingBox(0, 1, 3, 5); //left bottom mid
		BoundingBox lbc = new BoundingBox(0, 1, 5, 6); //left bottom corner
		BoundingBox blm = new BoundingBox(1, 3, 5, 6); //bottom left mid
		BoundingBox bmm = new BoundingBox(1, 5, 5, 6); //bottom mid
		BoundingBox brm = new BoundingBox(3, 5, 5, 6); //bottom right mid
		BoundingBox brc = new BoundingBox(5, 6, 5, 6); //bottom right corner
		BoundingBox rbm = new BoundingBox(5, 6, 3, 5); //right bottom mid
		BoundingBox rmm = new BoundingBox(5, 6, 1, 5); //right mid
		BoundingBox rtm = new BoundingBox(5, 6, 1, 3); //right top mid
		assertFalse(mb.intersect(trc));
		assertFalse(mb.intersect(trm));
		assertFalse(mb.intersect(tmm));
		assertFalse(mb.intersect(tlc));
		assertFalse(mb.intersect(ltm));
		assertFalse(mb.intersect(lmm));
		assertFalse(mb.intersect(lbm));
		assertFalse(mb.intersect(lbc));
		assertFalse(mb.intersect(blm));
		assertFalse(mb.intersect(bmm));
		assertFalse(mb.intersect(brm));
		assertFalse(mb.intersect(brc));
		assertFalse(mb.intersect(rbm));
		assertFalse(mb.intersect(rmm));
		assertFalse(mb.intersect(rtm));
		assertFalse(mb.intersect(tlm));
	}
	@Test
	public void testSquaresIntersect() {
		IShape s1 = new Square(Color.RED, 50, 50, 50);
		IShape s2 = new Square(Color.RED, 80, 80, 100);
		s2.save();
		assertTrue(s1.intersects(s2));
	}

}
