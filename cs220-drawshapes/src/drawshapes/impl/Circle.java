package drawshapes.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

//TODO: add resize method
/**
 * THIS CLASS IS NOT FINISHED
 * 
 * Add and compute the bounding box.
 * 
 * Implement the rest of the methods.
 * 
 * @author jspacco
 *
 */
public class Circle implements IShape
{
	
    private Color color;
    private int diameter;
	private Point anchor;
	private int centerX;
	private int centerY;
    private boolean selected;
    private BoundingBox boundingBox;
	private boolean bProtect;
    
    public Circle(Color color, int centerX, int centerY, int diameter) {
        this.color = color;
        this.centerX = centerX;
		this.centerY = centerY;
        this.diameter= diameter;
        this.selected = false;
        this.anchor = new Point(centerX - diameter/2, centerY - diameter/2);
        this.boundingBox = new BoundingBox(anchor.x, anchor.x + diameter, anchor.y, anchor.y + diameter);
    }

    @Override
    public void draw(Graphics g) {
    	if (selected) 
			g.setColor(getColor().darker());
		else
			g.setColor(color);
        g.fillOval((int)centerX - diameter/2,
                (int)centerY - diameter/2,
                diameter,
                diameter);
    }

    @Override
	public boolean intersects(IShape other) {
		return boundingBox.intersect(other.getBoundingBox());
	}

	@Override
	public boolean contains(Point point) {
		return boundingBox.contains(point.x, point.y);
	}

	@Override
	 public Color getColor(){
        return color;
    }

	@Override
	 public void setColor(Color color) {
        this.color=color;
    }

	@Override
	public boolean isSelected() {
        return this.selected;
    }

	@Override
	public void setSelected(boolean b) {
		this.selected = b;
		if (b == true && bProtect == false) {
			bProtect = true;
			setColor(getColor().darker());
		}else if (b == false && bProtect == true){
			bProtect = false;
			setColor(getColor().brighter());
		}
	}
	@Override
	public Point getAnchorPoint() {
        return this.anchor;
    }

	@Override
	public void setAnchorPoint(Point p) {
        this.anchor = p;
    }

	@Override
	public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

	@Override
	public void resize(boolean b) {
		if (b) {
			this.diameter = this.diameter + 25;
			this.anchor=new Point(centerX - diameter/2, centerY - diameter/2);
			this.boundingBox = new BoundingBox(anchor.x, anchor.x + diameter, anchor.y, anchor.y + diameter);
		}
		else if (this.diameter >= 25) {
			this.diameter = this.diameter - 25;
			this.anchor=new Point(centerX - diameter/2, centerY - diameter/2);
			this.boundingBox = new BoundingBox(anchor.x, anchor.x + diameter, anchor.y, anchor.y + diameter);
		}
		else
			System.out.println("Item cannot be smaller");
	}

	@Override
	public String save() {
		String text = "";
		text = text + color.getRed() + " ";
		text = text + color.getGreen() + " ";
		text = text + color.getBlue() + " ";
		text = text + "Square ";
		text = text + centerX + " " + centerY + " ";
		text = text + diameter + " ";
		//System.out.println(text);
		return text;
	}
}
