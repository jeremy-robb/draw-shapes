package drawshapes.impl;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Square implements IShape
{
	private Point anchor;
	private int length; //this will default to lengthX in cases for multiple lengths
	private Color color;
	private boolean selected;
	private int centerX;
	private int centerY;
	private BoundingBox boundingBox;
	private boolean bProtect = false; //this is to protect against right click spams

	public Square(Color color, int centerX, int centerY, int length) {
		this.length=length;
		this.centerX = centerX;
		this.centerY = centerY;
		this.anchor=new Point(centerX - length/2, centerY - length/2);
		this.selected=false;
		this.color=color;
		this.boundingBox = new BoundingBox(anchor.x, anchor.x + length, anchor.y, anchor.y + length);
	}
	@Override
	public void draw(Graphics g){ 
		if (selected) {
			g.setColor(getColor().darker());
		}
		else
			g.setColor(color);
		g.fillRect((int)anchor.x, (int)anchor.y,
				length, length);
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
		text = text + "Square ";
		text = text + centerX + " " + centerY + " ";
		text = text + length + " ";
		//System.out.println(text);
		return text;
	}
}
