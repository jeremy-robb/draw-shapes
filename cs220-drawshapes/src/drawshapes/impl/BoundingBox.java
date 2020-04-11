package drawshapes.impl;

import java.util.*;

/**
 * The bounding box of a 2D shape. The bounding box represents the
 * minimum and maximum X and Y coordinates.
 * 
 * You can add whatever methods to this class you think are necessary. 
 * 
 * @author jspacco
 *
 */
public class BoundingBox
{
	protected int left;
	protected int right;
	protected int top;
	protected int bottom;

	//To remember: left < right | bottom > top
	public BoundingBox(int left, int right, int top, int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;

	}

	public boolean intersect(BoundingBox B) {
		//if the calling Bounding Box is in any way intersecting B vertically, then we check if it intersects it horizontally, if it does return true
		if ((this.top <= B.top && this.bottom >= B.bottom) || (this.top > B.top && this.bottom > B.bottom && this.top <= B.bottom) 
				|| (this.top <= B.top && this.bottom < B.bottom && this.bottom > B.top) || (this.top == B.top && this.bottom == B.bottom) || (this.top > B.top && this.bottom < B.bottom))
			//top bottom has returned true
			if ((this.left <= B.left && this.right >= B.right) || (this.left > B.left && this.right > B.right && this.left <= B.right) 
					|| (this.left <= B.left && this.right < B.right && this.right > B.left) || (this.left == B.left && this.right == B.right) || (this.left > B.left && this.right < B.right))
				//left right has returned true
				return true;
		return false;
	}
	
	public boolean contains(int x, int y) {
		boolean left = false;
		boolean right = false;
		boolean top = false;
		boolean bottom = false;

		if (x > this.left)
			left = true;
		if (x < this.right)
			right = true;
		if (y > this.top)
			top = true;
		if (y < this.bottom)
			bottom = true;

		if (left == true && right == true && top == true && bottom == true)
			return true;
		else
			return false;
	}


}
