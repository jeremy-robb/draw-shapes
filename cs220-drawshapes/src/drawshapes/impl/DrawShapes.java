package drawshapes.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.sun.org.apache.xalan.internal.xsltc.dom.AbsoluteIterator;
import java.lang.Math;
@SuppressWarnings("serial")
public class DrawShapes<T> extends JFrame
{	//the phantom box lingers!!
	// The enum tracks the different types of shape
	// An enum is used for value that are constants
	PrintWriter out;
	int itemR;
	int itemG;
	int itemB;
	String itemShape;
	int itemX;
	int itemY;
	int itemLength;
	public void createPrinter() {
		try {
			out = new PrintWriter("out.txt");
		}catch (Exception e) {
			//System.out.println("Problem with printer");
		}
	}
	public void getPrinter() {
		try {
			System.setIn(new FileInputStream("out.txt"));
			 sc = new Scanner(System.in);
		}catch (Exception e) {
			//System.out.println("Problem with printer");
		}
	}
		Scanner sc;
	private enum ShapeType {
		SQUARE,
		CIRCLE,
		RECTANGLE,
		TRIANGLE
	}
	public Color randomize() {
		r = (float) Math.random();
		g = (float) Math.random();
		b = (float) Math.random();
		random = new Color(r, g, b, 1);
		return random;
	}
	Point fp;
	Color phantom = new Color(.4f, 0, .8f, .5f);
	Color random = new Color(1f, 1, 1f, 1f);
	float r;
	float g;
	float b;
	List<IShape> selectedShapes =new LinkedList<IShape>();
	List<IShape> phantomSelectedShapes =new LinkedList<IShape>();
	// The panel we will draw the shapes on
	private DrawShapesPanel shapePanel;
	// The scene containing all of the shapes
	private Scene scene;
	// Default shape type is square
	private ShapeType shapeType = ShapeType.SQUARE;
	// The color we will use to draw the next shape
	private Color color = Color.RED;

	public DrawShapes(int width, int height)
	{
		setTitle("Draw Shapes!");

		scene = new Scene();

		// create our canvas, add to this frame's content pane
		shapePanel = new DrawShapesPanel(width, height, scene);

		// Sets the pane where we'll draw things to our special shape panel
		this.getContentPane().add(shapePanel, BorderLayout.CENTER);
		// We can't resize the JFrame
		this.setResizable(false);
		this.pack();
		this.setLocation(100,100);

		// Add key and mouse listeners to our canvas
		initializeMouseListener();
		initializeKeyListener();

		// initialize the menu options
		initializeMenu();

		// Handle closing the window.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// The System.exit method shuts down the JVM and
				// tells it to clean up any open resources
				// like GUI objects, open files, open network connections
				// and so on. This may not be necessary but often you see
				// System.exit used with GUI programs.
				System.exit(0);
			}
		});
	}

	private void initializeMouseListener()
	{
		// Add a listener for mouse motions (i.e. moving the mouse)
		shapePanel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				scene.setPhantom(true);
				Point np = e.getPoint(); //newest point
				////System.out.printf("mouse drag! (%d, %d)\n", e.getX(), e.getY());
				scene.setp(new Rectangle(phantom, ((fp.x + np.x)/2), ((fp.y + np.y)/2), 
						Math.abs(np.x - fp.x), Math.abs(np.y - fp.y))); 
				repaint();
			}
		});

		// Listen for mouse clicks
		shapePanel.addMouseListener(new MouseAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
			 */
			public void mouseClicked(MouseEvent e)
			{
				// mouseClicked() is called when you press and release a mouse button
				// WITHOUT MOVING THE MOUSE. If you move the mouse, instead you get a 
				// mousePressed(), some number mouse mouseDragged(), then a mouseReleased().

				if (e.getButton()==MouseEvent.BUTTON1) { //left click
					if (color.equals(random))
						color = randomize();
					////System.out.printf("Left click at (%d, %d)\n", e.getX(), e.getY());
					if (shapeType == ShapeType.SQUARE) {
						scene.addShape(new Square(color, 
								e.getX(), 
								e.getY(),
								100));
					} else if (shapeType == ShapeType.CIRCLE){
						scene.addShape(new Circle(color,
								e.getX(), e.getY(),
								100));
					} else if (shapeType == ShapeType.RECTANGLE) {
						scene.addShape(new Rectangle(color, e.getX(), e.getY(), 125, 75));
					} else if (shapeType == ShapeType.TRIANGLE) {
						scene.addShape(new EquilateralTriangle(color, e.getX(), e.getY(), 100));
					}
				} else if (e.getButton()==MouseEvent.BUTTON3) {
					// handle right-click
					// right-click is button #3, middle button (if there is one) is button #2
					////System.out.printf("Right click at (%d, %d)\n", e.getX(), e.getY());
					//if the point selected contains a shape then do this
					List<IShape> tempshape =new LinkedList<IShape>();
					tempshape.addAll(scene.select(e.getPoint()));
					if (tempshape.size() > 0) //if there is a shape where you right clicked
						selectedShapes.addAll(tempshape);
					else { //if there's not a shape where you right clicked
						for (int i = 0; i < selectedShapes.size(); i++) 
							selectedShapes.get(i).setSelected(false);
						//System.out.println("list clear");
						selectedShapes.clear();
					}
					tempshape.clear(); //this intermediary is to make right clicking nothing unselect all shapes
					if (selectedShapes.size() > 0)
						for (int i = 0; i < selectedShapes.size(); i++) 
							selectedShapes.get(i).setSelected(true);


				} else if (e.getButton() == MouseEvent.BUTTON2){
					////System.out.printf("Middle click at (%d, %d)\n", e.getX(), e.getY());
				}
				// repaint() tells the JFrame to re-draw itself, which has the effect
				// of calling the paint() method for the DrawShapesPanel, which is what
				// tells the scene to draw itself
				repaint();
			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
			 */
			public void mousePressed(MouseEvent e)
			{

				fp = e.getPoint(); //gets the origin for phantom box
				if (e.getButton()==MouseEvent.BUTTON1) {
					// Press left mouse button
					////System.out.printf("Pressed left button at (%d, %d)\n", e.getX(), e.getY());
				} else if (e.getButton()==MouseEvent.BUTTON3) {
					// Press right mouse button
					//System.out.printf("Pressed right button at (%d, %d)\n", e.getX(), e.getY());
				} else if (e.getButton() == MouseEvent.BUTTON2){
					// Press middle mouse button (if your mouse has a middle button)
					////System.out.printf("Pressed middle (%d, %d)\n", e.getX(), e.getY());
				}

			}

			/* (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
			 */
			public void mouseReleased(MouseEvent e)
			{
				//TODO: select all boxes that phantom box intersects - add all variables to that thing
				if (scene.getPhantom() == true) {
					for (int i = 0; i < scene.getShapeList().size(); i++) {
						if (scene.getShapeList().get(i).intersects(scene.getp()))
							if (!(selectedShapes.contains(scene.getShapeList().get(i))))
								selectedShapes.add(scene.getShapeList().get(i));
					}
					if (selectedShapes.size() > 0)
						for (int i = 0; i < selectedShapes.size(); i++) 
							selectedShapes.get(i).setSelected(true);
					scene.setp(null);
					scene.setPhantom(false);
				}
				// Called when you release the button you clicked
				if (e.getButton()==MouseEvent.BUTTON1) {
					// Press left mouse button
					////System.out.printf("Released left button at (%d, %d)\n", e.getX(), e.getY());
				} else if (e.getButton()==MouseEvent.BUTTON3) {
					// Press right mouse button
					//System.out.printf("Released right button at (%d, %d)\n", e.getX(), e.getY());
				} else if (e.getButton() == MouseEvent.BUTTON2){
					// Press middle mouse button (if your mouse has a middle button)
					////System.out.printf("Released middle (%d, %d)\n", e.getX(), e.getY());
				}
				repaint();
			}

		});
	}

	private void initializeMenu()
	{
		// menu bar
		JMenuBar menuBar = new JMenuBar();

		// file menu
		JMenu fileMenu=new JMenu("File");
		menuBar.add(fileMenu);
		// load
		JMenuItem loadItem = new JMenuItem("Load");
		fileMenu.add(loadItem);
		loadItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getPrinter();
				//System.out.println("pre pre test");
				while(sc.hasNext()) {
					itemR = sc.nextInt();
					itemG = sc.nextInt();
					itemB = sc.nextInt();
					itemShape = sc.next();
					itemX = sc.nextInt();
					itemY = sc.nextInt();
					itemLength = sc.nextInt();
					//System.out.println("items fired");
					Color recolor = new Color(itemR, itemG, itemB, 255);
					if (itemShape.equals("Square"))
						scene.addShape(new Square(recolor, itemX, itemY,itemLength));
					else if (itemShape.equals("Rectangle")) 
						scene.addShape(new Rectangle(recolor, itemX, itemY, itemLength, sc.nextInt()));
					else if (itemShape.equals("Circle")) 
						scene.addShape(new Circle(recolor, itemX, itemY, itemLength));
					else
						scene.addShape(new EquilateralTriangle(recolor, itemX, itemY, itemLength));
				}
				repaint();
				//System.out.println(e.getActionCommand());
			}
		});
		// save
		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);
		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createPrinter();
				for (int i = 0; i < scene.getShapeList().size(); i++)
				out.write(scene.getShapeList().get(i).save());
				out.close();
			}
		});
		fileMenu.addSeparator();
		// edit
		JMenuItem itemExit = new JMenuItem ("Exit");
		fileMenu.add(itemExit);
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=e.getActionCommand();
				//System.out.println(text);
				System.exit(0);
			}
		});

		// color menu
		JMenu colorMenu = new JMenu("Color");
		menuBar.add(colorMenu);

		// red color
		JMenuItem redColorItem= new JMenuItem ("Red");
		colorMenu.add(redColorItem);
		redColorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=e.getActionCommand();
			//	//System.out.println(text);
				// change the color instance variable to red
				color = Color.RED;
			}
		});


		// blue color
		JMenuItem blueColorItem = new JMenuItem ("Blue");
		colorMenu.add(blueColorItem);
		blueColorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=e.getActionCommand();
				////System.out.println(text);
				// change the color instance variable to blue
				color = Color.BLUE;
			}
		});

		// green color
		JMenuItem greenColorItem = new JMenuItem ("Green");
		colorMenu.add(greenColorItem);
		greenColorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = e.getActionCommand();
				//System.out.println(text);
				// change the color instance variable to green
				color = Color.green;
			}
		});

		// Magenta color
		JMenuItem MagentaColorItem = new JMenuItem ("Magenta");
		colorMenu.add(MagentaColorItem);
		MagentaColorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=e.getActionCommand();
				//System.out.println(text);
				// change the color instance variable to Magenta
				color = Color.MAGENTA;
			}
		});

		JMenuItem RandomColorItem = new JMenuItem ("Random");
		colorMenu.add(RandomColorItem);
		RandomColorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=e.getActionCommand();
				//System.out.println(text);
				color = randomize();
			}
		});

		// shape menu
		JMenu shapeMenu = new JMenu("Shape");
		menuBar.add(shapeMenu);

		// square
		JMenuItem squareItem = new JMenuItem("Square");
		shapeMenu.add(squareItem);
		squareItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Square");
				shapeType = ShapeType.SQUARE;
			}
		});

		// circle
		JMenuItem circleItem = new JMenuItem("Circle");
		shapeMenu.add(circleItem);
		circleItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Circle");
				shapeType = ShapeType.CIRCLE;
			}
		});

		// rectangle
		JMenuItem rectangleItem = new JMenuItem("Rectangle");
		shapeMenu.add(rectangleItem);
		rectangleItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Rectangle");
				shapeType = ShapeType.RECTANGLE;
			}
		});
		// triangle
		JMenuItem triangleItem = new JMenuItem("Triangle");
		shapeMenu.add(triangleItem);
		triangleItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Triangle");
				shapeType = ShapeType.TRIANGLE;
			}
		});



		// new menu option
		JMenu operationModeMenu=new JMenu("new menu option");
		menuBar.add(operationModeMenu);

		// draw option
		JMenuItem drawItem=new JMenuItem("option #1");
		operationModeMenu.add(drawItem);
		drawItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=e.getActionCommand();
				//System.out.println(text);
			}
		});

		// select option
		JMenuItem selectItem=new JMenuItem("option #2");
		operationModeMenu.add(selectItem);
		selectItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=e.getActionCommand();
				//System.out.println(text);
			}
		});

		//change colors to selected color option
		JMenuItem colorItem=new JMenuItem("change colors");
		operationModeMenu.add(colorItem);
		colorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < selectedShapes.size(); i++) {
					selectedShapes.get(i).setColor(color);
				}
				repaint();
			}
		});

		JMenuItem iSizeItem=new JMenuItem("increase size");
		operationModeMenu.add(iSizeItem);
		iSizeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < selectedShapes.size(); i++)
					selectedShapes.get(i).resize(true);;
					repaint();
			}
		});

		JMenuItem dSizeItem=new JMenuItem("decrease size");
		operationModeMenu.add(dSizeItem);
		dSizeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < selectedShapes.size(); i++)
					selectedShapes.get(i).resize(false);;
					repaint();
			}
		});

		JMenuItem randomColor=new JMenuItem("randomize color");
		operationModeMenu.add(randomColor);
		randomColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < selectedShapes.size(); i++) 
					selectedShapes.get(i).setColor(randomize());;
					repaint();
			}
		});
		// set the menu bar for this frame
		this.setJMenuBar(menuBar);
	}

	private void initializeKeyListener()
	{
		shapePanel.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				//System.out.println("key typed: " +e.getKeyChar());
				//System.out.println(e.getKeyCode());
				if (e.getKeyCode() == 127 || e.getKeyCode() == 8) {
					scene.getShapeList().removeAll(selectedShapes);
					repaint();
				}
			}
			public void keyReleased(KeyEvent e){
				// TODO implement this method if you need it
			}
			public void keyTyped(KeyEvent e) {
				// TODO implement this method if you need it
			}
		});
	}

	public static void main(String[] args)
	{
		DrawShapes shapes=new DrawShapes(700, 600);
		shapes.setVisible(true);
	}

}
