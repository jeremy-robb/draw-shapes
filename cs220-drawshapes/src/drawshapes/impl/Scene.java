package drawshapes.impl;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A scene of shapes.  Uses the Model-View-Controller (MVC) design pattern,
 * though note that model knows something about the view, as the draw() 
 * method both in Scene and in Shape uses the Graphics object. That's kind of sloppy,
 * but it also helps keep things simple.
 * 
 * @author jspacco
 *
 */
public class Scene implements Iterable<IShape>
{
	Color holder = new Color(1, 1, 1, 0.5f); //does nothing, temp holder for p
	private List<IShape> shapeList=new LinkedList<IShape>();
	private boolean phantom; //says whether phantom box is activated
	private Rectangle p = new Rectangle(holder, 1, 1, 1, 1); //describes phantom box
	
	public Rectangle getp() {
		return p;
	}
	public void setp(Rectangle p) {
		this.p = p;
	}
	public boolean getPhantom() {
		return phantom;
	}
	public void setPhantom(boolean phantom) {
		this.phantom = phantom;
	}

	/**
	 * Draw all the shapes in the scene using the given Graphics object.
	 * @param g
	 */
	public void draw(Graphics g) {
		for (IShape s : shapeList) {
			s.draw(g);
		}
		if (phantom)
			p.draw(g);
	}

	public Iterator<IShape> iterator() {
		return shapeList.iterator();
	}

	/**
	 * Return a list of shapes that contain the given point.
	 * @param point The point
	 * @return A list of shapes that contain the given point.
	 */
	public List<IShape> select(Point point)
	{
		List<IShape> containsList =new LinkedList<IShape>();
		for (int i = 0; i < shapeList.size(); i++) 
			if (shapeList.get(i).contains(point)) 
				containsList.add(shapeList.get(i));
		return containsList;
	}

	/**
	 * Return a list of shapes in the scene that intersect the given shape.
	 * @param s The shape
	 * @return A list of shapes intersecting the given shape.
	 */
	public List<IShape> select(IShape s)
	{
		List<IShape> intersectsList =new LinkedList<IShape>();
		for(int i = 0; i < shapeList.size(); i++) 
			if (!(shapeList.get(i).equals(s)) && shapeList.get(i).intersects(s))
				intersectsList.add(shapeList.get(i));
		return intersectsList;
	}

	/**
	 * Add a shape to the scene.  It will be rendered next time
	 * the draw() method is invoked.
	 * @param s
	 */
	public void addShape(IShape s) {
		shapeList.add(s);
	}

	/**
	 * Remove a given shape from the scene so that it will not be
	 * rendered the next time the draw() method is invoked.
	 * @param s
	 */
	public void removeShape(IShape s){
		shapeList.remove(s);
	}

	/**
	 * Remove a list of shapes from the given scene.
	 * @param shapesToRemove
	 */
	public void removeShapes(Collection<IShape> shapesToRemove) {
		shapeList.removeAll(shapesToRemove);
	}
	public List<IShape> getShapeList() {
		return shapeList;
	}

}
