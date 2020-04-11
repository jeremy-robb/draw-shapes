package drawshapes.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rectangle implements IShape {

	private Point anchor;
    private int lengthY;
    private int lengthX;
    private int centerX;
	private int centerY;
    private Color color;
    private boolean selected;
    private BoundingBox boundingBox;
	private boolean bProtect;
    
    public Rectangle(Color color, int centerX, int centerY, int lengthX, int lengthY) {
    	this.lengthX = lengthX;
    	this.lengthY = lengthY;
    	this.centerX = centerX;
		this.centerY = centerY;
    	this.anchor = new Point(centerX - lengthX/2, centerY - lengthY/2); //equation for square + individual lengths
    	this.selected = false;
    	this.color = color;
    	this.boundingBox = new BoundingBox(anchor.x, anchor.x + lengthX, anchor.y, anchor.y + lengthY);
    }
	@Override
	public void draw(Graphics g) {
		if (selected) 
			g.setColor(getColor().darker());
		else
			g.setColor(color);
		g.fillRect((int)anchor.x, (int)anchor.y, lengthX, lengthY); //x coord, y coord, width, height
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

	public void resize(boolean b) {
		if (b) {
			this.lengthY = this.lengthY + 15;
			this.lengthX = this.lengthX + 25;
			this.anchor=new Point(centerX - lengthX/2, centerY - lengthY/2);
			this.boundingBox = new BoundingBox(anchor.x, anchor.x + lengthX, anchor.y, anchor.y + lengthY);
		}
		else if (this.lengthY >= 25 && this.lengthY >= 15) {
			this.lengthY = this.lengthY - 15;
			this.lengthX = this.lengthX - 25;
			this.anchor=new Point(centerX - lengthX/2, centerY - lengthY/2);
			this.boundingBox = new BoundingBox(anchor.x, anchor.x + lengthX, anchor.y, anchor.y + lengthY);
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
		text = text + "Rectangle ";
		text = text + centerX + " " + centerY + " ";
		text = text + lengthX + " ";
		text = text + lengthY + " ";
		//System.out.println(text);
		return text;

	}
}
