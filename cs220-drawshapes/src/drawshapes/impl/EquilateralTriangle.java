package drawshapes.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
//TODO: add resize method
public class EquilateralTriangle implements IShape {
	  private Point anchor;
	    private int length;
	    private Color color;
	    private int centerX;
		private int centerY;
	    private boolean selected;
	    private BoundingBox boundingBox;
		private boolean bProtect;
	    									//triangles top tip will be (length/2) units above the click point 
	    public EquilateralTriangle(Color color, int centerX, int centerY, int length) { 
	    	this.length = length;
	    	this.centerX = centerX;
			this.centerY = centerY;
	    	this.anchor = new Point(centerX - length/2, centerY - length/2);
	    	this.selected = false;
	    	this.color = color; 
	    	this.boundingBox = new BoundingBox(anchor.x, anchor.x + length, anchor.y, anchor.y + length); //same as square
	    }
	@Override
	public void draw(Graphics g) {
		if (selected) 
			g.setColor(getColor().darker());
		else
			g.setColor(color);
		Polygon p = new Polygon();
		p.addPoint(anchor.x + length/2, anchor.y); //top mid point
		p.addPoint(anchor.x, anchor.y + length); //lower left point
		p.addPoint(anchor.x + length, anchor.y + length); //lower right point
		g.fillPolygon(p);
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
			this.length = this.length + 25;
			this.anchor=new Point(centerX - length/2, centerY - length/2);
			this.boundingBox = new BoundingBox(anchor.x, anchor.x + length, anchor.y, anchor.y + length);
		}
		else if (this.length >= 25) {
			this.length = this.length - 25;
			this.anchor=new Point(centerX - length/2, centerY - length/2);
			this.boundingBox = new BoundingBox(anchor.x, anchor.x + length, anchor.y, anchor.y + length);
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
		text = text + "Triangle ";
		text = text + centerX + " " + centerY + " ";
		text = text + length + " ";
		//System.out.println(text);
		return text;
	}

}
