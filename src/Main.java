import javax.swing.plaf.*;
import java.awt.geom.*;
import java.awt.font.*;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.basic.*;
import java.awt.image.*;
import javax.swing.border.*;
import java.awt.*;
import java.lang.*;
import java.io.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;
import javax.swing.filechooser.*;
import java.beans.*;



class FrontEnd extends JApplet implements ActionListener
{
	JMenuBar 	jMenuBar1  			= new JMenuBar();

	JMenu 		jFileMenu	      		= new JMenu();
	JMenuItem 	jNewMenuItem 			= new JMenuItem();
	JMenuItem 	jOpenMenuItem  			= new JMenuItem();
	JMenuItem 	jSaveMenuItem  			= new JMenuItem();
	JMenuItem 	jSaveAsMenuItem 		= new JMenuItem();
	JMenuItem 	jExitMenuItem  			= new JMenuItem();

	JMenu 		jEditMenu			= new JMenu();
	JMenuItem 	jCutMenuItem  			= new JMenuItem();
	JMenuItem 	jCopyMenuItem  			= new JMenuItem();
	JMenuItem 	jPasteMenuItem  		= new JMenuItem();
	JMenuItem 	jClearMenuItem 			= new JMenuItem();
	JMenuItem	jChangeFontMenuItem		= new JMenuItem();

	JMenu 		jColorMenu			= new JMenu();
	JMenuItem 	jSetForegroundMenuItem 		= new JMenuItem();
	JMenuItem 	jSetBackgroundMenuItem 		= new JMenuItem();
	JMenu	 	jSetDrawColorMenu	 	= new JMenu();
	JMenuItem	jDrawColorMenuItem		= new JMenuItem();
	JMenuItem	jDrawGradientMenuItem		= new JMenuItem();
	JMenu 		jChangeColorMenu	 	= new JMenu();
	JMenuItem	jChangeColorMenuItem		= new JMenuItem();
	JMenuItem	jChangeGradientMenuItem		= new JMenuItem();



	JMenu		jThemeMenu			= new JMenu();
	JMenuItem	jWindowsThemeMenuItem 		= new JMenuItem();
	MetalTheme[] 	themes 				= 	{
									new DefaultMetalTheme(),
									new GreenMetalTheme(),
									new AquaMetalTheme(),
									new KhakiMetalTheme(),
									new DemoMetalTheme(),
									new ContrastMetalTheme(),
									new BigContrastMetalTheme(),
								};
	JMenu		jMetalThemeMenu			= new MetalThemeMenu("Metal",themes);

	JMenuItem	jMotifThemeMenuItem		= new JMenuItem();

	JMenu 		jHelpMenu      			= new JMenu();
	JMenuItem 	jAboutMenuItem 			= new JMenuItem();

	JPanel 		jPanToolBar   			= new JPanel();
	JPanel 		jPanFullArea  			= new JPanel();
	JPanel 		jPanFooter 			= new JPanel();
	JCheckBox	jChkCyclic			= new JCheckBox("Cyclic Fill");
	ShapeCanvas 	canvas 				= new ShapeCanvas();

	JButton 	jButText 			= new JButton();
	JButton 	jButLine 			= new JButton();
	JButton 	jButBezier 			= new JButton();
	JButton 	jButRectangle 			= new JButton();
	JButton 	jButCircle 			= new JButton();
	JButton 	jButRoundRect 			= new JButton();
	JButton 	jButImage 			= new JButton();
	JButton		jButFilledRectangle		= new JButton();
	JButton		jButFilledCircle		= new JButton();
	JButton		jButFilledRoundRect		= new JButton();

	JButton		jButForeground			= new JButton();
	JButton		jButBackground			= new JButton();


	JPopupMenu 	popupMenu 			= new JPopupMenu();

	JPanel 		jMousePosPanel			= new JPanel();
	JLabel 		jMousePosLabel			= new JLabel("Mouse Position");
	JLabel		jMouseXYLabel			= new JLabel();
	boolean 	inside				= true;

	JFrame 		jf 				= new JFrame("Please enter the string to add");
	JFrame 		frameProperties			= new JFrame("Properties");

	public static JColorChooser jColorChooser1 = new JColorChooser();

	static String 	metal				= "Metal";
	static String 	metalClassName 			= "javax.swing.plaf.metal.MetalLookAndFeel";

	static String 	motif 				= "Motif";
	static String 	motifClassName 			= "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

	static String 	windows				= "Windows";
	static String 	windowsClassName 		= "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";


	int 		curX 				= 0,
			curY 				= 0;

	static final boolean 	COLOR			= true;
	static final boolean 	GRADIENT		= false;
	static 	boolean		colorOrGradient		= FrontEnd.COLOR;
    	static final boolean 	CYCLIC 			= true;
    	static final boolean 	NONCYCLIC		= false;
    	static boolean		cyclicOrNot		= CYCLIC;


	static Color 		fgColor			= Color.black;
	static Color 		bgColor			= Color.white;
	static Color 		drawColor		= Color.black;
	static GradientPaint 	drawGradient		= new GradientPaint(100,100,Color.red,200,200,Color.white,cyclicOrNot);

	ArrayList 		shapes			= new ArrayList();
	// holds a list of the shapes that are displayed on the canvas

	boolean 		drawingShape 		= false;	//True if the user is drawing a shape.
	ShapeClass		shapeToBeEdited 	= null;
	ShapeClass		currentShape 		= null;
	FontSelection		fs			= new FontSelection();
	int 			shapeID 		= 0;

	static final int 	LINE 			= 0;
	static final int 	RECTANGLE 		= 1;
	static final int 	OVAL 			= 2;
	static final int 	ROUNDRECT 		= 3;
	static final int 	FILLEDRECTANGLE		= 4;
	static final int 	FILLEDOVAL		= 5;
	static final int	FILLEDROUNDRECT		= 6;
	static final int 	TEXT 			= 7;
	static final int 	BEZIER 			= 8;
	static final int 	IMAGE 			= 9;

	static final boolean 	FILLED			= true;
	static final boolean 	EMPTY			= false;

	boolean 		saved 			= true;
	boolean			drawBounds		= false;
	static File		image			= null;

	String 			fileSave 		= null;
	JButton 		butOK 			= new JButton("Ok");
	JButton 		butCancel		= new JButton("Cancel");
	static JFrame 		f;
	Font 			currentFont		= new Font("Arial",Font.PLAIN,10);
	Font			specificFont		= new Font("Arial",Font.PLAIN,10);
	JTextField 		t			= new JTextField(10);
	Graphics2D		g2d;
	int 			noOfBezierPoints	= 0;

	FileTrace		fileTrace 		= new FileTrace("Trace.txt", 0);
	//Construct the applet
	public FrontEnd()
	{
		butOK.addActionListener(this);
		butCancel.addActionListener(this);
	}
	//Initialize the applet

	public void init()
	{
		try
		{
			setSize(975,650);
			jMousePosPanel.setBounds(150,650,500,50);
			jMousePosPanel.setLayout(null);
			jMousePosLabel.setBounds(0,0,250,50);
			jMouseXYLabel.setText("(" + (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 + ", " + (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 + ")");
			jMouseXYLabel.setBounds(200,0,250,50);
			jMousePosPanel.add(jMousePosLabel);
			jMousePosPanel.add(jMouseXYLabel);
			jPanFullArea.add(jMousePosPanel);

			//MENU
			jFileMenu.setText("File");
			jNewMenuItem.setText("New");
			jOpenMenuItem.setText("Open");
			jSaveMenuItem.setText("Save");
			jSaveAsMenuItem.setText("Save As");
			jExitMenuItem.setText("Exit");

			jEditMenu.setText("Edit");
			jCutMenuItem.setText("Cut");
			jCopyMenuItem.setText("Copy");
			jPasteMenuItem.setText("Paste");
			jClearMenuItem.setText("Clear");
			jChangeFontMenuItem.setText("Change Font");

			jColorMenu.setText("Color");
			jSetForegroundMenuItem.setText("Set Foreground Color");
			jSetBackgroundMenuItem.setText("Set Backgroound Color");
			jSetDrawColorMenu.setText("Set Draw Color");
			jDrawColorMenuItem.setText("Color");
			jDrawGradientMenuItem.setText("Gradient");
			jChangeColorMenu.setText("Change Color");
			jChangeColorMenuItem.setText("Color");
			jChangeGradientMenuItem.setText("Gradient");

			jThemeMenu.setText("Theme");
			jWindowsThemeMenuItem.setText(windows);
			jMotifThemeMenuItem.setText(motif);

			jWindowsThemeMenuItem.setActionCommand(windowsClassName);
			jMotifThemeMenuItem.setActionCommand(motifClassName);


			jHelpMenu.setText("Help");
			jAboutMenuItem.setText("About");

			jFileMenu.setMnemonic('F');
			jEditMenu.setMnemonic('E');
			jColorMenu.setMnemonic('C');
			jThemeMenu.setMnemonic('T');
			jHelpMenu.setMnemonic('H');

			jNewMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
			jOpenMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
			jSaveMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
			jSaveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));

			jCutMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
			jCopyMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
			jPasteMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
			jClearMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
			jChangeFontMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));



			jMenuBar1.add(jFileMenu);
			jMenuBar1.add(jEditMenu);
			jMenuBar1.add(jColorMenu);
			jMenuBar1.add(jThemeMenu);
			jMenuBar1.add(jHelpMenu);

			jFileMenu.add(jNewMenuItem);
			jFileMenu.add(jOpenMenuItem);
			jFileMenu.add(jSaveMenuItem);
			jFileMenu.add(jSaveAsMenuItem);
			jFileMenu.add(jExitMenuItem);

			jEditMenu.add(jCutMenuItem);
			jEditMenu.add(jCopyMenuItem);
			jEditMenu.add(jPasteMenuItem);
			jEditMenu.add(jClearMenuItem);
			jEditMenu.add(jChangeFontMenuItem);

			//   			jThemeMenu.add(buttonGroup);

			jColorMenu.add(jSetForegroundMenuItem);
			jColorMenu.add(jSetBackgroundMenuItem);
			jSetDrawColorMenu.add(jDrawColorMenuItem);
			jSetDrawColorMenu.add(jDrawGradientMenuItem);
			jChangeColorMenu.add(jChangeColorMenuItem);
			jChangeColorMenu.add(jChangeGradientMenuItem);
			jColorMenu.add(jSetDrawColorMenu);
			jColorMenu.add(jChangeColorMenu);

			jThemeMenu.add(jWindowsThemeMenuItem);
			jThemeMenu.add(jMetalThemeMenu);
			jThemeMenu.add(jMotifThemeMenuItem);

			jHelpMenu.add(jAboutMenuItem);
			setJMenuBar(jMenuBar1);


			//PANEL
			this.getContentPane().add(jPanFullArea,BorderLayout.CENTER);
			jPanFullArea.setLayout(null);

			jPanToolBar.setPreferredSize(new Dimension(100, 10));
			jPanToolBar.setLayout(null);

			//BUTTONS
			jButLine.setBounds(8, 96, 29, 31);
			jButLine.setIcon(new ImageIcon("Line.jpg"));
			jButRectangle.setBounds(51, 96, 29, 31);
			jButRectangle.setIcon(new ImageIcon("Rectangle.jpg"));
			jButCircle.setBounds(8, 143, 29, 31);
			jButCircle.setIcon(new ImageIcon("Circle.jpg"));
			jButRoundRect.setBounds(50, 143, 29, 31);
			jButRoundRect.setIcon(new ImageIcon("RoundRect.jpg"));
			jButFilledRectangle.setBounds(8, 190, 29, 31);
			jButFilledRectangle.setIcon(new ImageIcon("FilledRectangle.jpg"));
			jButFilledCircle.setBounds(51, 190, 29, 31);
			jButFilledCircle.setIcon(new ImageIcon("FilledCircle.jpg"));
			jButFilledRoundRect.setBounds(8, 237, 29, 31);
			jButFilledRoundRect.setIcon(new ImageIcon("FilledRoundRect.jpg"));
			jButText.setBounds(51, 237, 29, 31);
			jButText.setIcon(new ImageIcon("Alphabet.jpg"));
			jButBezier.setBounds(8, 284, 29, 31);
			jButBezier.setIcon(new ImageIcon("Bezier.jpg"));
			jButImage.setBounds(51,284,29,31);
			jButImage.setIcon(new ImageIcon("Image.jpg"));

			jButForeground.setBounds(8,378,29,31);
			jButForeground.setBackground(fgColor);
			jButBackground.setBounds(51,378,29,31);
			jButBackground.setBackground(bgColor);

			jChkCyclic.setBounds(8,428,100,18);

			jButText.setBorder(new LineBorder(Color.darkGray));
			jButLine.setBorder(new LineBorder(Color.darkGray));
			jButRectangle.setBorder(new LineBorder(Color.darkGray));
			jButCircle.setBorder(new LineBorder(Color.darkGray));
			jButRoundRect.setBorder(new LineBorder(Color.darkGray));
			jButFilledRectangle.setBorder(new LineBorder(Color.darkGray));
			jButFilledCircle.setBorder(new LineBorder(Color.darkGray));
			jButFilledRoundRect.setBorder(new LineBorder(Color.darkGray));
			jButBezier.setBorder(new LineBorder(Color.darkGray));
			jButImage.setBorder(new LineBorder(Color.darkGray));
			jButForeground.setBorder(new LineBorder(Color.darkGray));
			jButBackground.setBorder(new LineBorder(Color.darkGray));



			jPanToolBar.add( jButText, null);
			jPanToolBar.add( jButLine, null);
			jPanToolBar.add( jButRectangle, null);
			jPanToolBar.add( jButCircle, null);
			jPanToolBar.add( jButRoundRect, null);
			jPanToolBar.add( jButFilledRectangle, null);
			jPanToolBar.add( jButFilledCircle, null);
			jPanToolBar.add( jButFilledRoundRect, null);
			jPanToolBar.add( jButBezier, null);
			jPanToolBar.add( jButImage,null);
			jPanToolBar.add( jButForeground,null);
			jPanToolBar.add( jButBackground,null);
			jPanToolBar.add( jChkCyclic,null);


			//PANELS



			jPanToolBar.setBounds(0,0,120,getHeight() - 30);
			jPanFullArea.add(jPanToolBar);

			canvas.setBackground(FrontEnd.bgColor);
			canvas.setBounds(120,0 , getWidth(),getHeight() - 30);
			canvas.setBorder(new EtchedBorder());
			jPanFullArea.add(canvas);

			jPanFooter.setBounds(0,getHeight() - 31,getWidth(),29);
			jPanFullArea.add(jPanFooter);



			//EVENTS

			popupMenu.add("cut").addActionListener(this);
			popupMenu.add("copy").addActionListener(this);
			popupMenu.add("paste").addActionListener(this);
			popupMenu.add("set Color").addActionListener(this);
			popupMenu.add("set Gradient").addActionListener(this);
			popupMenu.add("properties").addActionListener(this);

			jNewMenuItem.addActionListener(this);
			jOpenMenuItem.addActionListener(this);
			jSaveMenuItem.addActionListener(this);
			jSaveAsMenuItem.addActionListener(this);
			jExitMenuItem.addActionListener(this);

			jCutMenuItem.addActionListener(this);
			jCopyMenuItem.addActionListener(this);
			jPasteMenuItem.addActionListener(this);
			jClearMenuItem.addActionListener(this);
			jChangeFontMenuItem.addActionListener(this);

			jSetForegroundMenuItem.addActionListener(this);
			jSetBackgroundMenuItem.addActionListener(this);
			jDrawColorMenuItem.addActionListener(this);
			jDrawGradientMenuItem.addActionListener(this);
			jChangeColorMenuItem.addActionListener(this);
			jChangeGradientMenuItem.addActionListener(this);

			jWindowsThemeMenuItem.addActionListener(this);
			jMotifThemeMenuItem.addActionListener(this);

			jAboutMenuItem.addActionListener(this);

			jButText.addActionListener(this);
			jButLine.addActionListener(this);
			jButRectangle.addActionListener(this);
			jButCircle.addActionListener(this);
			jButRoundRect.addActionListener(this);
			jButFilledRectangle.addActionListener(this);
			jButFilledCircle.addActionListener(this);
			jButFilledRoundRect.addActionListener(this);
			jButBezier.addActionListener(this);
			jButImage.addActionListener(this);
			jButForeground.addActionListener(this);
			jButBackground.addActionListener(this);
			jChkCyclic.addActionListener(this);

			jButText.setToolTipText("Text");
			jButLine.setToolTipText("Line");
			jButRectangle.setToolTipText("Rectangle");
			jButCircle.setToolTipText("Ellipse");
			jButRoundRect.setToolTipText("Rounded Rectangle");
			jButFilledRectangle.setToolTipText("Filled Rectangle");
			jButFilledCircle.setToolTipText("Filled Ellipse");
			jButFilledRoundRect.setToolTipText("Filled Rounded Rectangle");
			jButBezier.setToolTipText("Bezier");
			jButImage.setToolTipText("Image");
			jButForeground.setToolTipText("set Foreground");
			jButBackground.setToolTipText("set Background");
			jChkCyclic.setToolTipText("If checked, the colors in the Gradient will cycle through the two colors specified");


		}
		catch(Exception e)
		{
			fileTrace.write(e);
		}

	}

	public void closeTrace()
	{
		fileTrace.close();
	}

	public boolean askForSave(boolean cancel)
	{
		String s = new String("Do you want to save the file?");
		JOptionPane optionPane = new JOptionPane(s);
		int retval;
		if(cancel)
			retval = optionPane.showConfirmDialog(null,s,s,JOptionPane.YES_NO_CANCEL_OPTION);
		else
			retval = optionPane.showConfirmDialog(null,s,s,JOptionPane.YES_NO_OPTION);
		switch(retval)
		{
		case JOptionPane.YES_OPTION:
			if(fileSave == null)
			{
				FileChoosers fc = new FileChoosers(1);
				String strFileName = fc.shows();
				if(strFileName != null)
					fileSave = new String(strFileName);
			}
			save(fileSave);
			return true;
		case JOptionPane.NO_OPTION:
			return true;
		case JOptionPane.CANCEL_OPTION:
			return false;
		}
		return false;
	}

	public void newMenuHandler()
	{
		boolean b = true;
		if(!saved)
			b = askForSave(true);
		if(b)
		{
			fileSave = null;
			shapes = new ArrayList();
			fgColor = Color.black;
			bgColor = Color.white;
			canvas.setBackground(bgColor);
			repaint();
		}
	}

	public void openMenuHandler()
	{
		boolean b = true;
		if(!saved)
			b = askForSave(true);
		if(b)
		{
			FileChoosers fc = new FileChoosers(0);
			fileSave = new String(fc.shows());
			open(fileSave);
			repaint();
		}
	}

	public void saveMenuHandler()
	{
		if(fileSave == null)
		{
			FileChoosers fc = new FileChoosers(1);
			fileSave = new String(fc.shows());
		}
		save(fileSave);
	}

	private void saveAsMenuHandler()
	{
		FileChoosers fc = new FileChoosers(1);
		fileSave = new String(fc.shows());
		save(fileSave);
	}

	public void exitMenuHandler()
	{
		try
		{
			boolean b = true;
			if(!saved)
				b = askForSave(true);
			String ret = b?"True":"False";
			if(b)
			{
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			fileTrace.write(e);
		}
	}

	public void cutMenuHandler()
	{
		if(shapeToBeEdited != null)
		{
			shapes.remove(shapeToBeEdited);
			repaint();
			drawBounds = false;
		}
	}

	public void copyMenuHandler()
	{
	}

	public void pasteMenuHandler(int x,int y)
	{
		if(shapeToBeEdited != null)
		{
			ShapeClass temp = shapeToBeEdited.copy(x,y);
			shapes.add(shapes.size(),temp);
		}
		repaint();
	}

	private void clearMenuHandler()
	{
		shapes.clear();
		repaint();
	}

	private void changeFontMenuHandler()
	{
		fs.showDialog();
	}

	private void setForeGroundMenuHandler()
	{
		Color oldColor = fgColor;
		fgColor = jColorChooser1.showDialog(this,"Choose a color",fgColor);
		if(!oldColor.equals(fgColor) && bgColor != null)
		{
			repaint();
			jButForeground.setBackground(fgColor);
        	}
    	}

	private void setBackGroundMenuHandler()
	{
		Color oldColor = bgColor;
       		bgColor = jColorChooser1.showDialog(this,"Choose a background color",bgColor);
       		if(!oldColor.equals(bgColor) && bgColor != null)
       		{
			canvas.setBackground(bgColor);
			jButBackground.setBackground(bgColor);
			repaint();
       		}
    	}

	private void drawColorMenuHandler()
	{
		drawColor = jColorChooser1.showDialog(this,"Choose a color",drawColor);
		colorOrGradient = COLOR;
	}

	private void drawGradientMenuHandler()
	{
		Color color1 = jColorChooser1.showDialog(this,"Choose the first color",drawColor);
		if(color1 != null)
		{
			Color color2 = jColorChooser1.showDialog(this,"Choose the second color",drawColor);
			if(color2 != null)
			{
				drawGradient = new GradientPaint(100,100,color1,200,200,color2,cyclicOrNot);
				colorOrGradient = GRADIENT;
			}
		}
	}

	private void changeColorMenuHandler()
	{
		if(shapeToBeEdited != null)
		{
			Color oldColor = shapeToBeEdited.color;
			shapeToBeEdited.setColor(jColorChooser1.showDialog(this,"Choose a color",shapeToBeEdited.color));
			if(!oldColor.equals(shapeToBeEdited.color))
				repaint();
		}
	}

	private void changeGradientMenuHandler()
	{
		if(shapeToBeEdited != null)
		{
			Color color1 = jColorChooser1.showDialog(this,"Choose the first color",drawColor);
			if(color1 != null)
			{
				Color color2 = jColorChooser1.showDialog(this,"Choose the second color",drawColor);
				if(color2 != null)
				{
					GradientPaint gPaint = new GradientPaint(100,100,color1,200,200,color2,cyclicOrNot);
					shapeToBeEdited.setGradient(gPaint);
					repaint();
				}
			}
		}
	}

	private void showProperties()
	{
		
		if(shapeToBeEdited != null)
		{
			fileTrace.write("In showProperties. shapeIdentifier = " + shapeToBeEdited.shapeIdentifier);
			shapeToBeEdited.showProperties(shapes.indexOf(shapeToBeEdited));
			repaint();
		}
	}

	private void aboutMenuHandler()
	{
		JOptionPane.showMessageDialog(this,"Created by Gopala Krishna Sharma M P and Avinash B R");
	}

	private void butTextHandler()
	{
		jf.getContentPane().setLayout(null);
		jf.setSize(400,130);
		t.setBounds(10,10,380,20);
		t.setText("");
		butOK.setBounds(100,50,100,30);
		butCancel.setBounds(205, 50, 100, 30);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		jf.setLocation((d.width - jf.getContentPane().getWidth()) / 2,(d.height - jf.getContentPane().getHeight()) / 2);
		jf.getContentPane().add(t);
		jf.getContentPane().add(butOK);
		jf.getContentPane().add(butCancel);
		jf.setVisible(true);
    }

   	private void butLineHandler()
	{
		drawingShape = true;
		shapeID = LINE;
	}

	private void butRectangleHandler()
	{
		drawingShape = true;
		shapeID = RECTANGLE;
	}

	private void butCircleHandler()
	{
		drawingShape = true;
		shapeID = OVAL;
	}

	private void butRoundRectHandler()
	{
		drawingShape = true;
		shapeID = ROUNDRECT;
	}

	private void butFilledRectangleHandler()
	{
		drawingShape = true;
		shapeID = FILLEDRECTANGLE;
	}

	private void butFilledCircleHandler()
	{
		drawingShape = true;
		shapeID = FILLEDOVAL;
	}

	private void butFilledRoundRectHandler()
	{
		drawingShape = true;
		shapeID = FILLEDROUNDRECT;
	}


	private void butOKHandler()
	{
		TextShape t1=new TextShape(t.getText(),currentFont);
	       	shapes.add(t1);
	       	jf.setVisible(false);
	       	repaint();
	}

	private void butCancelHandler()
	{
		jf.setVisible(false);
		repaint();
	}


	private void butBezierHandler()
	{
		drawingShape = true;
		shapeID = BEZIER;
	}

	private void butImageHandler()
	{
		FileChoosers f = new FileChoosers(2);
		image = new File(f.shows());
		drawingShape = true;
		shapeID = IMAGE;
	}

	private void chkCyclicHandler()
	{
		cyclicOrNot = jChkCyclic.isSelected();
	}

	public void actionPerformed(ActionEvent ae)
	{
		Object o = ae.getSource();
		if(o == jNewMenuItem)
			newMenuHandler();
		else if(o == jOpenMenuItem)
			openMenuHandler();
		else if(o == jSaveMenuItem)
			saveMenuHandler();
		else if(o == jSaveAsMenuItem)
			saveAsMenuHandler();
		else if(o == jExitMenuItem)
			exitMenuHandler();
		else if(o == jCutMenuItem || ae.getActionCommand().equals("cut"))
			cutMenuHandler();
		else if(o == jCopyMenuItem || ae.getActionCommand().equals("copy"))
			copyMenuHandler();
		else if(o ==jPasteMenuItem)
			pasteMenuHandler(100,100);
		else if(ae.getActionCommand().equals("paste"))
			pasteMenuHandler(curX,curY);
		else if(ae.getActionCommand().equals("properties"))
			showProperties();
		else if(o ==jClearMenuItem)
			clearMenuHandler();
		else if(o == jChangeFontMenuItem)
			changeFontMenuHandler();
		else if(o == jChkCyclic)
			chkCyclicHandler();
		else if(o == jSetForegroundMenuItem || o == jButForeground)
			setForeGroundMenuHandler();
		else if(o == jSetBackgroundMenuItem || o == jButBackground)
			setBackGroundMenuHandler();
	       	else if(o == jDrawColorMenuItem)
			drawColorMenuHandler();
		else if(o == jDrawGradientMenuItem)
			drawGradientMenuHandler();
		else if(o == jChangeColorMenuItem || ae.getActionCommand().equals("set Color"))
			changeColorMenuHandler();
		else if(o == jChangeGradientMenuItem || ae.getActionCommand().equals("set Gradient"))
			changeGradientMenuHandler();
		else if(o ==jAboutMenuItem)
			aboutMenuHandler();
		else if(o == jButText)
			butTextHandler();
		else if(o == jButLine)
			butLineHandler();
		else if(o == jButRectangle)
			butRectangleHandler();
		else if(o == jButCircle)
			butCircleHandler();
		else if(o == jButRoundRect)
			butRoundRectHandler();
		else if(o == jButFilledRectangle)
			butFilledRectangleHandler();
		else if(o == jButFilledCircle)
			butFilledCircleHandler();
		else if(o == jButFilledRoundRect)
			butFilledRoundRectHandler();
		else if(o == butOK)
			butOKHandler();
		else if(o == butCancel)
			butCancelHandler();
		else if(o == jButBezier)
			butBezierHandler();
		else if(o == jButImage)
			butImageHandler();
		else
		{
			String lnfName = ae.getActionCommand();
			try
			{
				UIManager.setLookAndFeel(lnfName);
				SwingUtilities.updateComponentTreeUI(this);
			}
			catch (UnsupportedLookAndFeelException exc)
			{
				fileTrace.write("Unsupported L&F Error:" + exc);
			}
			catch (IllegalAccessException exc)
			{
				fileTrace.write("IllegalAccessException Error:" + exc);
			}
			catch (ClassNotFoundException exc)
			{
				fileTrace.write("ClassNotFoundException Error:" + exc);
			}
			catch (InstantiationException exc)
			{
				fileTrace.write("InstantiateException Error:" + exc);
			}
		}

	}


	void reDrawCanvas()
	{
		fileTrace.write("In reDrawCanvas");
		this.repaint();
	}

	void save(String filename)
	{
		saved = true;
		String ext = "Invalid Extension";
		int i = filename.lastIndexOf('.');
		if(i>0 && i<filename.length()-1)
		{
			ext = filename.substring(i+1).toLowerCase();
		}
		if(ext.equals("gag"))
		{
			try
			{
				RandomAccessFile fOut = new RandomAccessFile(new File(filename),"rw");
				i = 0;
				int size = shapes.size();
				fOut.writeShort(size);
                		fOut.writeInt(fgColor.getRGB());
				fOut.writeInt(bgColor.getRGB());
				ShapeClass s;
				while(i < size)
				{
					s = (ShapeClass)shapes.get(i);
					int sid = s.shapeIdentifier;
					fOut.writeShort(sid);
					if(sid == FrontEnd.TEXT)
					{
						fOut.writeShort(((TextShape)s).text.length());
	       	             			fOut.writeBytes(((TextShape)s).text);
						fOut.writeShort(((TextShape)s).font.getFamily().length());
						fOut.writeBytes(((TextShape)s).font.getFamily());
						fOut.writeShort(((TextShape)s).font.getStyle());
						fOut.writeShort(((TextShape)s).font.getSize());
					}
					else if(sid == FrontEnd.IMAGE)
					{
						fOut.writeShort(((ImageShape)s).imagePath.length());
						fOut.writeBytes(((ImageShape)s).imagePath);
					}
					else if(sid == FrontEnd.BEZIER)
					{
						fOut.writeShort(((BezierShape)s).nPoints);
						for(int index = 0;index < ((BezierShape)s).nPoints;index++)
						{
							fOut.writeShort(((BezierShape)s).points[index].x);
							fOut.writeShort(((BezierShape)s).points[index].y);
						}
					}
					fOut.writeBoolean(s.colorGradient);
					fOut.writeInt(s.gPaint.getColor1().getRGB());
					fOut.writeInt(s.gPaint.getColor2().getRGB());
					fOut.writeShort(s.left);
					fOut.writeShort(s.top);
					fOut.writeShort(s.width);
					fOut.writeShort(s.height);
					fOut.writeInt(s.color.getRGB());
					i++;
				}
				fOut.close();
			}
			catch(Exception e)
			{
			}
		}
		else
		{
			fileSave = null;
			JOptionPane.showMessageDialog(this,"Invalid extension","Invalid Extension",JOptionPane.ERROR_MESSAGE);
		}

	}


	void open(String f)
	{
		Color color1 = null,color2 = null;
		try
		{
			RandomAccessFile fIn = new RandomAccessFile(new File(f),"r");
			int i = 0;
			int size = fIn.readShort();
            fgColor = new Color(fIn.readInt());
            bgColor = new Color(fIn.readInt());
            canvas.setBackground(bgColor);
            canvas.setForeground(fgColor);
			shapes = new ArrayList();
			byte b[];
			b = new byte[1];
			ShapeClass s = null;
			while(i < size)
			{
				int sid = fIn.readShort();
				switch(sid)
				{
				case FrontEnd.LINE:s = new LineShape();
					break;
				case FrontEnd.RECTANGLE:s = new RectShape(EMPTY);
					break;
				case FrontEnd.OVAL:s = new OvalShape(EMPTY);
					break;
				case FrontEnd.ROUNDRECT:s = new RoundRectShape(EMPTY);
					break;
				case FrontEnd.FILLEDRECTANGLE:s = new RectShape(FILLED);
					break;
				case FrontEnd.FILLEDOVAL:s = new OvalShape(FILLED);
					break;
				case FrontEnd.FILLEDROUNDRECT:s = new RoundRectShape(FILLED);
					break;

				case FrontEnd.TEXT:int length = fIn.readShort();
					byte by[] = new byte[length];
					fIn.read(by);
					String str = new String(by);
					length = fIn.readShort();
					by = new byte[length];
					fIn.read(by);
					String fontName = new String(by);
					int style = fIn.readShort();
					int si = fIn.readShort();
					Font fo = new Font(fontName,style,si);
					s = new TextShape(str,fo);
					break;
				case FrontEnd.BEZIER:
					s = new BezierShape();
					if(s == null)
						fileTrace.write("in creating Bezier. S is null");
					((BezierShape)s).nPoints = fIn.readShort();
					for(int index = 0;index < ((BezierShape)s).nPoints;index++)
					{
						((BezierShape)s).points[index].x = fIn.readShort();
						((BezierShape)s).points[index].y = fIn.readShort();
               					}
					break;
				case FrontEnd.IMAGE:
					length = fIn.readShort();
					by = new byte[length];
					fIn.read(by);
					str = new String(by);
					s = new ImageShape(str);
					break;
				}
				if(s == null)
				{
					fileTrace.write("invalid file format");
					return;
				}
				s.colorGradient = fIn.readBoolean();
				color1 = new Color(fIn.readInt());
				color2 = new Color(fIn.readInt());
				s.left = fIn.readShort();
				s.top = fIn.readShort();
				s.width = fIn.readShort();
				s.height = fIn.readShort();
				s.color = new Color(fIn.readInt());
				s.gPaint = new GradientPaint(s.left,s.top + s.height / 2,color1,s.left + s.width,s.top + s.height,color2,cyclicOrNot);
				shapes.add(s);
				i++;
			}
			fIn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}






	class ShapeCanvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener
	{


		ShapeCanvas()
		{
			// Constructor: set background color to white
			// set up listeners to respond to mouse actions

			setBackground(bgColor);
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
		}

		public void paintComponent(Graphics g)
		{
			if(inside)
				jMouseXYLabel.setText("(" + curX + ", " + curY + ")");
			else
				jMouseXYLabel.setText("");
			g2d = (Graphics2D)g;
			// In the paint method, all the shapes in ArrayList are
			// copied onto the canvas.
			saved = false;
			super.paintComponent(g);  // First, fill with background color.

			int top = shapes.size();
			for (int i = 0; i < top; i++)
			{
				ShapeClass s = (ShapeClass)shapes.get(i);
				s.draw(g);
			}
			if(currentShape != null && drawBounds)
			{
				currentShape.drawBounds(g);
			}

		}


		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_DELETE)
				cutMenuHandler();
		}

		public void keyTyped(KeyEvent e)
		{
		}

		public void keyReleased(KeyEvent e)
		{
		}


		ShapeClass shape = null;

		ShapeClass clickedShape = null;  // This is the shape that the user clicks on.
		// It becomes the draggedShape is the user is
		// dragging, unless the user is invoking a
		// pop-up menu.  This variable is used in
		// actionPerformed() when a command from the
		// pop-up menu is processed.

		ShapeClass draggedShape = null;  // This is null unless a shape is being dragged.
		// A non-null value is used as a signal that dragging
		// is in progress, as well as indicating which shape
		// is being dragged.

		int prevDragX;  // During dragging, these record the x and y coordinates of the
		int prevDragY;  //    previous position of the mouse.

		int begX = 0,begY = 0;



		public void mousePressed(MouseEvent evt)
		{

			int x = evt.getX();  // x-coordinate of point where mouse was clicked
			int y = evt.getY();  // y-coordinate of point

			if(evt.isPopupTrigger())
			{
				boolean bEnablePopupMenu = true;
				if(shapeToBeEdited == null)
				{
					fileTrace.write("in popupTrigger. ShapeTOBeEdited = null;");
					bEnablePopupMenu = false;
				}
				else
					fileTrace.write("ShapeIdentifier = " + shapeToBeEdited.shapeIdentifier);

				MenuElement[] popupMenuItems = popupMenu.getSubElements();
				int length = popupMenuItems.length;
				for(int index = 0;index < length;index++)
				{
					popupMenu.getComponentAtIndex(index).setEnabled(false);
				}
				popupMenu.setEnabled(false);
				popupMenu.show(this,x,y);

				return;

			}

			if(drawingShape  && (shapeID != BEZIER))
			{
				switch(shapeID)
				{
				case FrontEnd.LINE:
					shape = new LineShape();
					shape.reShape(x,y,0,0);
					break;
				case FrontEnd.RECTANGLE:
					shape = new RectShape(EMPTY);
					shape.reShape(x,y,0,0);
					break;
				case FrontEnd.OVAL:
					shape = new OvalShape(EMPTY);
					shape.reShape(x,y,0,0);
					break;
				case FrontEnd.ROUNDRECT:
					shape = new RoundRectShape(EMPTY);
					shape.reShape(x,y,0,0);
					break;
				case FrontEnd.FILLEDRECTANGLE:
					shape = new RectShape(FILLED);
					shape.reShape(x,y,0,0);
					break;
				case FrontEnd.FILLEDOVAL:
					shape = new OvalShape(FILLED);
					shape.reShape(x,y,0,0);
					break;
				case FrontEnd.FILLEDROUNDRECT:
					shape = new RoundRectShape(FILLED);
					shape.reShape(x,y,0,0);
					break;
				case FrontEnd.IMAGE:
					shape = new ImageShape(image.getAbsolutePath());
					shape.reShape(x,y,0,0);
					break;

				default:
					fileTrace.write("Error in sid");
				}
				begX = x;
				begY = y;
				shapes.add(shape);
				//         	repaint();
			}
			if (draggedShape != null) 
			{
				return;
			}

			clickedShape = null;  // This will be set to the clicked shape, if any.
			for (int i = shapes.size() - 1; i >= 0; i--) 
			{
				// Check shapes from front to back.
				ShapeClass s = (ShapeClass)shapes.get(i);
				if (s.containsPoint(x,y)) 
				{
					clickedShape = s;
					shapeToBeEdited = clickedShape;
					currentShape = clickedShape;
					drawBounds = true;
					break;
				}

			}

			if (clickedShape == null) {
				// The user did not click on a shape.
				shapeToBeEdited = null;
				currentShape = null;
				return;
			}
			else if (evt.isShiftDown()) 
			{
				// Bring the clicked shape to the front
				shapes.remove(clickedShape);
				shapes.add(clickedShape);
				repaint();
			}
			else 
			{
				// Start dragging the shape.
				draggedShape = clickedShape;
				prevDragX = x;
				prevDragY = y;
			}
			prevDragX = x;
			prevDragY = y;
		}

		public void mouseDragged(MouseEvent evt)
		{
			// User has moved the mouse.  Move the dragged shape by the same amount.
			int x = evt.getX();
			int y = evt.getY();
			curX = evt.getX();
			curY = evt.getY();
			if(inside)
				jMouseXYLabel.setText("(" + curX + ", " + curY + ")");
			else
				jMouseXYLabel.setText("");

			if(drawingShape  && !(shapeID == BEZIER))
			{
				shapes.remove(shape);
				// Set the position and size of this shape.
				int mousex = x, mousey = y; // Top left corner of the rectangle.
				int w = 0, h = 0; // Width and height of the rectangle.
				// x,y,w,h must be computed from the coordinates
				// of the two corner points.
				int startx = begX,starty = begY;
				if (startx != x && starty != y)
				{
					if (x > startx)
					{
						mousex = startx;
						w = x - startx;
					}
					else
					{
						mousex = x;
						w = startx - x;
					}
					if (y > starty)
					{
						mousey = starty;
						h = y - starty;
					}
					else
					{
						mousey = y;
						h = starty - y;
					}
				}
				try
				{
					shape.reShape(mousex,mousey,w,h);
					shapes.add(shape);
					repaint();
				}
				catch(Exception e)
				{
				}
				return;
			}

			if (draggedShape == null) {
				// User did not click a shape.  There is nothing to do.
				return;
			}
			draggedShape.moveBy(x - prevDragX, y - prevDragY);
			prevDragX = x;
			prevDragY = y;
			repaint();      // redraw canvas to show shape in new position
			if(shapeToBeEdited != null)
				drawBounds = true;
		}

		public void mouseReleased(MouseEvent evt)
		{
			// User has released the mouse.  Move the dragged shape, and set
			// draggedShape to null to indicate that dragging is over.
			// If the shape lies completely outside the canvas, remove it
			// from the list of shapes (since there is no way to ever move
			// it back on screen).  However, if the event is a popup trigger
			// event, then show the popup menu instead.

			int x = evt.getX();
			int y = evt.getY();

			if(evt.isPopupTrigger())
			{
				popupMenu.show(this,x,y);
				return;
			}

			if(drawingShape && !(shapeID == BEZIER))
			{
				shapes.remove(shape);
				shape.reShape(shape.left,shape.top,shape.width,shape.height);
				shapes.add(shape);
				repaint();
				drawingShape = false;
				shape = null;
				return;
			}

			if (draggedShape == null) {
				// User did not click on a shape. There is nothing to do.
				shapeToBeEdited = null;
				return;
			}


			draggedShape.moveBy(x - prevDragX, y - prevDragY);
			repaint();
			draggedShape = null;  // Dragging is finished.

		}

		public void mouseEntered(MouseEvent evt)
		{
			inside = true;
			if(inside)
				jMouseXYLabel.setText("(" + curX + ", " + curY + ")");
			else
				jMouseXYLabel.setText("");
		}   // Other methods required for MouseListener and
		public void mouseExited(MouseEvent evt)
		{
			inside = false;
			if(inside)
				jMouseXYLabel.setText("(" + curX + ", " + curY + ")");
			else
				jMouseXYLabel.setText("");
		}    //              MouseMotionListener interfaces.
		public void mouseMoved(MouseEvent evt)
		{
			curX = evt.getX();
			curY = evt.getY();
			if(inside)
				jMouseXYLabel.setText("(" + curX + ", " + curY + ")");
			else
				jMouseXYLabel.setText("");
		}
		public void mouseClicked(MouseEvent evt)
		{
			int x = evt.getX();
			int y = evt.getY();
			if(drawingShape && shapeID == BEZIER)
			{
				if(shape == null)
					shape = new BezierShape();
				else
					shapes.remove(shape);
				((BezierShape)shape).reShapeBezier(x,y);
				shapes.add(shape);
				noOfBezierPoints++;
				if(noOfBezierPoints >= 4)
				{
					shape = null;
					drawingShape = false;
					noOfBezierPoints = 0;
				}
				repaint();
				return;
			}

			shapeToBeEdited = null;
			for (int i = shapes.size() - 1; i >= 0; i--)
			{
				// Check shapes from front to back.
				ShapeClass s = (ShapeClass)shapes.get(i);
				if (s.containsPoint(x,y))
				{
					fileTrace.write("In mouseClicked. shapeIdentifier = " + s.shapeIdentifier + ".");
					shapeToBeEdited = s;
					currentShape = s;
					drawBounds = true;
					//					shapeToBeEdited.drawBounds();
					break;
				}
			}
			if(shapeToBeEdited == null)
			{
				currentShape = null;
				shapeToBeEdited = null;
				drawBounds = false;
				repaint();
			}
		}


	}  // end class ShapeCanvas



	public class FontSelection extends JApplet implements ItemListener, ActionListener
	{
		JLabel fontLabel, sizeLabel, styleLabel;
		FontPanel fontC;
		JComboBox fonts, sizes, styles;
		int index = 0;
		String fontchoice = "fontchoice";
		int stChoice = 0;
		String siChoice = "10";
		JButton butOk = new JButton("Ok");
		JButton butCancel = new JButton("Cancel");
		boolean bSetCurFont = true;
		Font fontToBeReturned = null;

		public FontSelection()
		{
		}

		public FontSelection(boolean bSetCurFont)
		{
			this.bSetCurFont = bSetCurFont;
		}

		public void init()
		{

			getContentPane().setLayout( new BorderLayout() );

			JPanel topPanel = new JPanel();
			JPanel fontPanel = new JPanel();
			JPanel sizePanel = new JPanel();
			JPanel stylePanel = new JPanel();
			JPanel sizeAndStylePanel = new JPanel();
			JPanel buttonPanel = new JPanel();

			topPanel.setLayout( new BorderLayout() );
			fontPanel.setLayout( new GridLayout( 2, 1 ) );
			sizePanel.setLayout( new GridLayout( 2, 1 ) );
			stylePanel.setLayout( new GridLayout( 2, 1 ) );
			sizeAndStylePanel.setLayout( new BorderLayout() );

			topPanel.add( BorderLayout.WEST, fontPanel );
			sizeAndStylePanel.add( BorderLayout.WEST, sizePanel );
			sizeAndStylePanel.add( BorderLayout.CENTER, stylePanel );
			topPanel.add( BorderLayout.CENTER, sizeAndStylePanel );

			getContentPane().add( BorderLayout.NORTH, topPanel );

			fontLabel = new JLabel();
			fontLabel.setText("Fonts");
			Font newFont = getFont().deriveFont(1);
			fontLabel.setFont(newFont);
			fontLabel.setHorizontalAlignment(JLabel.CENTER);
			fontPanel.add(fontLabel);

			sizeLabel = new JLabel();
			sizeLabel.setText("Sizes");
			sizeLabel.setFont(newFont);
			sizeLabel.setHorizontalAlignment(JLabel.CENTER);
			sizePanel.add(sizeLabel);

			styleLabel = new JLabel();
			styleLabel.setText("Styles");
			styleLabel.setFont(newFont);
			styleLabel.setHorizontalAlignment(JLabel.CENTER);
			stylePanel.add(styleLabel);

			GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			String envfonts[] = gEnv.getAvailableFontFamilyNames();
			Vector vector = new Vector();
			for ( int i = 1; i < envfonts.length; i++ )
			{
				vector.addElement(envfonts[i]);
			}
			fonts = new JComboBox( vector );
			fonts.setMaximumRowCount( 9 );
			fonts.addItemListener(this);
			fontchoice = envfonts[0];
			fontPanel.add(fonts);

			String []size = new String[100];
			for(int i = 10;i < 100;i++)
				size[i - 10] = String.valueOf(i);
			sizes = new JComboBox( size );
			sizes.setMaximumRowCount( 20 );
			sizes.addItemListener(this);
			sizePanel.add(sizes);

			styles = new JComboBox( new Object[]
			{
				"PLAIN",
				"BOLD",
				"ITALIC",
				"BOLD & ITALIC"}
			);
			styles.setMaximumRowCount( 9 );
			styles.addItemListener(this);
			sizes.setMaximumRowCount( 9 );
			stylePanel.add(styles);

			fontC = new FontPanel();
			fontC.setBackground(Color.white);
			getContentPane().add( BorderLayout.CENTER, fontC);

			butOk.addActionListener(this);
			butCancel.addActionListener(this);
			buttonPanel.add(BorderLayout.WEST,butOk);
			buttonPanel.add(BorderLayout.EAST,butCancel);
			getContentPane().add(BorderLayout.SOUTH,buttonPanel);
		}

		public void actionPerformed(ActionEvent e)
		{
			Object o = e.getSource();
			if(o == butOk)
			{
				if(bSetCurFont)
				{
					currentFont = new Font((String)fonts.getSelectedItem(),styles.getSelectedIndex(),Integer.parseInt((String)sizes.getSelectedItem()));
					repaint();
				}
				else
				{
					specificFont = new Font((String)fonts.getSelectedItem(),styles.getSelectedIndex(),Integer.parseInt((String)sizes.getSelectedItem()));
				}
			}
			else if(o == butCancel)
			{
			}
			f.setVisible(false);
		}

		public void itemStateChanged(ItemEvent e)
		{
			if ( e.getStateChange() != ItemEvent.SELECTED )
			{
				return;
			}

			Object list = e.getSource();

			if ( list == fonts )
			{
				fontchoice = (String)fonts.getSelectedItem();
			}
			else if ( list == styles )
			{
				index = styles.getSelectedIndex();
				stChoice = index;
			}
			else
			{
				siChoice = (String)sizes.getSelectedItem();
			}
			fontC.changeFont(fontchoice, stChoice, siChoice);
		}

		void showDialog()
		{
			f = new JFrame("FontSelection");
			JApplet fontSelection = new FontSelection();
			f.getContentPane().add(fontSelection, BorderLayout.CENTER);
			fontSelection.init();
			f.setSize(new Dimension(550,250));
			f.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - f.getWidth()) / 2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - f.getHeight()) / 2);
			f.setVisible(true);
		}
	}


	class FontPanel extends JPanel
	{

		Font thisFont;

		public FontPanel()
		{
			thisFont = new Font("Arial", Font.PLAIN, 10);
		}

		// Resets thisFont to the currently selected fontname, size and style attributes.
		public void changeFont(String f, int st, String si)
		{
			Integer newSize = new Integer(si);
			int size = newSize.intValue();
			thisFont = new Font(f, st, size);
			repaint();
		}

		public void paintComponent (Graphics g)
		{
			super.paintComponent( g );
			int w = getWidth();
			int h = getHeight();

			g.setColor(Color.darkGray);
			g.setFont(thisFont);
			String change = "Pick a font, size, and style to change me";
			FontMetrics metrics = g.getFontMetrics();
			int width = metrics.stringWidth( change );
			int height = metrics.getHeight();
			g.drawString( change, w/2-width/2, h/2-height/2 );
		}
	}



	// ------- Nested class definitions for the abstract Shape class and three -----
	// -------------------- concrete subclasses of Shape. --------------------------


	abstract class ShapeClass
	{

		int left, top;      // Position of top left corner of rectangle that bounds this shape.
		int width, height;  // Size of the bounding rectangle.
		int shapeIdentifier;
		Color color = drawColor;  // Color of this shape.
		GradientPaint gPaint = null;
		boolean colorGradient = COLOR;
		Graphics2D ga;

		public ShapeClass()
		{
			this.color = drawColor;
			this.gPaint = drawGradient;
			this.colorGradient = colorOrGradient;
		}
		void reShapeBezier(int x,int y)
		{

		}

		ShapeClass copy(int x,int y)
		{
			ShapeClass temp = null;
			switch(shapeIdentifier)
			{
			case FrontEnd.LINE:temp = new LineShape();
				break;
			case FrontEnd.RECTANGLE:temp = new RectShape(EMPTY);
				break;
			case FrontEnd.OVAL:temp = new OvalShape(EMPTY);
				break;
			case FrontEnd.ROUNDRECT:temp = new RoundRectShape(EMPTY);
				break;
			case FrontEnd.FILLEDRECTANGLE:temp = new RectShape(FILLED);
				break;
			case FrontEnd.FILLEDOVAL:temp = new OvalShape(FILLED);
				break;
			case FrontEnd.FILLEDROUNDRECT:temp = new RoundRectShape(FILLED);
				break;
			case FrontEnd.TEXT:TextShape s = (TextShape)this;
				temp = new TextShape(s);
				((TextShape)temp).text = s.text;
				((TextShape)temp).font = s.font;
				break;
			case FrontEnd.BEZIER:BezierShape s1 = (BezierShape)this;
				temp = new BezierShape(s1);
				break;
			case FrontEnd.IMAGE:ImageShape s2 = (ImageShape)this;
				temp = new ImageShape(s2);
				break;
			}
			try
			{
				temp.shapeIdentifier = shapeIdentifier;
				temp.left = x;
				temp.top = y;
				temp.width = width;
				temp.height = height;
				temp.color = color;
				temp.colorGradient = colorGradient;
				temp.gPaint = gPaint;
			}
			catch(Exception e)
			{
			}
			return temp;

		}
		void reShape(int startx, int starty, int width, int height)
		{
			fileTrace.write("In reShape. startx = " + startx + ", starty = " + starty + ", width = " + width + ", height = " + height);
			this.left = startx;
			this.top = starty;
			this.width = width;
			this.height = height;
//			fileTrace.write("(" + this.left + " , " + this.top + ")" + "  (" + this.width + ", " + this.height + ")");
		}



		void setSize(int width, int height)
		{
			// Set the size of this shape
			if(width < 0)
			{
				left -= width;
			}
			if(height < 0)
				top -= height;
			this.width = width;
			this.height = height;
		}

		void moveBy(int dx, int dy)
		{
			if(shapeIdentifier == FrontEnd.BEZIER)
				((BezierShape)this).moveBy(dx,dy);
			// Move the shape by dx pixels horizontally and dy pixels vertically
			// (by changing the position of the top-left corner of the shape).
			//		fileTrace.write(left + "," + top);
			left += dx;
			top += dy;
		}

		void setColor(Color color)
		{
			// Set the color of this shape
			if(color != null)
			{
				this.color = color;
				colorGradient = COLOR;
			}
		}

		void setGradient(GradientPaint gPaint)
		{
			if(gPaint != null)
			{
				this.gPaint = gPaint;
				colorGradient = GRADIENT;
			}
		}

		void drawBounds(Graphics g)
		{
			int nTop = top;
			if(this.shapeIdentifier == TEXT)
				nTop = nTop - height;
			g.drawRect(left - 3,nTop - 3,6,6);
			g.drawRect(left + width - 3,nTop - 3,6,6);
			g.drawRect(left - 3,nTop + height - 3,6,6);
			g.drawRect(left + width - 3,nTop + height- 3,6,6);
		}
		abstract boolean containsPoint(int x, int y);
		abstract void draw(Graphics g);

		public void showProperties(int index)
		{
			shapeIdentifier = this.shapeIdentifier;
			boolean bColorValid = true, bColorOrGradient, bFontPresent;
			switch(this.shapeIdentifier)
			{
				case OVAL:
				case LINE:
				case RECTANGLE:
				case ROUNDRECT:
				case BEZIER:
				case IMAGE:
					bColorValid = false;
					break;
				default:
					bColorValid = true;
					break;
			}

			bColorOrGradient = (this.colorGradient == COLOR);
			bFontPresent = (this.shapeIdentifier == TEXT);
			fileTrace.write("FontPresent = " + bFontPresent);
			Font f = bFontPresent?((TextShape)this).font:null;
			fileTrace.write("Font = " + f);
			PropertiesDialog pDialog = new PropertiesDialog();
			fileTrace.write("In showProperties. values (left, top), (right, bottom): (" + left + ", " + top + "), (" + width + ", " + height + ")");
			pDialog.setProperties(left, top, width, height, bColorValid, color, gPaint.getColor1(), gPaint.getColor2(), bColorOrGradient, f, bFontPresent, index);
			pDialog.init();
			try
			{
				pDialog.showDialog();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}


		public class PropertiesDialog extends JApplet implements ActionListener, WindowListener
		{
			static final int WIDTH_GAP = 20;
			static final int HEIGHT_GAP = 10;
			static final int WIDTH = 50;
			static final int HEIGHT = 20;
			static final int LEFTMARGIN = 30;
			static final int TOPMARGIN = 30;
			static final int RADIOBUTTONDIFF = 5;

			static final int FRAMEWIDTH = 300;
			static final int FRAMEHEIGHT = 350;

			JTextField	txtLeft;
			JTextField 	txtWidth;
			JTextField 	txtTop;
			JTextField 	txtHeight;
			JCheckBox	chkColorOrGradient;
			JButton 	butColor;
			JButton		butGradientColor1;
			JButton		butGradientColor2;
			JButton 	butFont;
			JButton 	butOK;
			JButton 	butCancel;
			boolean		bChanged;
			ShapeClass	sClass = null;

			JLabel		labLeft 	= new JLabel("Left");
			JLabel		labWidth	= new JLabel("Width");
			JLabel 		labTop		= new JLabel("Top");
			JLabel 		labHeight	= new JLabel("Height");
			JLabel 		labColor	= new JLabel("Color");
			JLabel		labGradient	= new JLabel("Gradient");
			JLabel 		labColor1	= new JLabel("Color1");
			JLabel 		labColor2	= new JLabel("Color2");
			JLabel 		labFont		= new JLabel("Font");

			int lLeft;
			int lTop;
			int lWidth;
			int lHeight;
			Color displayColor;
			Color gradientColor1;
			Color gradientColor2;
			boolean bColorOrGradient;
			Font fTextFont;
			boolean bFontPresent;
			boolean bColorValid;
			Font fRet = null;

			public PropertiesDialog()
			{
			}

			public void setProperties(int lLeft, int lTop, int lWidth, int lHeight, boolean bColorValid, Color displayColor, Color gradientColor1, Color gradientColor2, boolean bColorOrGradient, Font fTextFont, boolean bFontPresent, int index)
			{
				this.lLeft = lLeft;
				this.lWidth = lWidth;
				this.lTop = lTop;
				this.lHeight = lHeight;

				this.bColorValid = bColorValid;

				this.displayColor = displayColor;
				this.gradientColor1 = gradientColor1;
				this.gradientColor2 = gradientColor2;
				this.bColorOrGradient = bColorOrGradient;
				this.fTextFont = fTextFont;
				this.bFontPresent = bFontPresent;
				sClass = (ShapeClass)shapes.get(index);

				this.bChanged = false;
			}

			public void init()
			{
				getContentPane().setLayout(null);
				getContentPane().setSize(FRAMEWIDTH, FRAMEHEIGHT);

				labLeft.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(labLeft);

				txtLeft = new JTextField("");
				txtLeft.setText(String.valueOf(this.lLeft));
				txtLeft.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(txtLeft);

				int lCount = 1;

				labTop.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(labTop);

				txtTop = new JTextField("");
				txtTop.setText(String.valueOf(this.lTop));
				txtTop.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(txtTop);

				lCount = 2;
				labWidth.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(labWidth);

				txtWidth = new JTextField("");
				txtWidth.setText(String.valueOf(this.lWidth));
				txtWidth.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(txtWidth);


				lCount = 3;
				labHeight.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(labHeight);

				txtHeight= new JTextField("");
				txtHeight.setText(String.valueOf(this.lHeight));
				txtHeight.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(txtHeight);

				lCount = 4;
				chkColorOrGradient = new JCheckBox("Color");
				chkColorOrGradient.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(chkColorOrGradient);


				lCount = 5;
				labColor = new JLabel("Color");
				labColor.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(labColor);

				butColor = new JButton();
				butColor.setBackground(displayColor);
				butColor.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(butColor);


				lCount = 6;
				labGradient = new JLabel("Gradient");
				labGradient.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(labGradient);

				butGradientColor1 = new JButton();
				butGradientColor1.setBackground(gradientColor1);
				butGradientColor1.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(butGradientColor1);

				butGradientColor2 = new JButton();
				butGradientColor2.setBackground(gradientColor2);
				butGradientColor2.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(butGradientColor2);


				lCount = 7;
				labFont = new JLabel("Font");
				labFont.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(labFont);

				if(bFontPresent && fTextFont != null)
					butFont = new JButton(fTextFont.getName());
				else
					butFont = new JButton("null");
				butFont.setFont(this.fTextFont);
				butFont.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, 4 * PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(butFont);


				lCount = 9;
				butOK = new JButton("Ok");
				butOK.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(butOK);

				butCancel = new JButton("Cancel");
				butCancel.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + 2 * PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, 2 * PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
				getContentPane().add(butCancel);



				chkColorOrGradient.addActionListener(this);
				butColor.addActionListener(this);
				butGradientColor1.addActionListener(this);
				butGradientColor2.addActionListener(this);
				butOK.addActionListener(this);
				butCancel.addActionListener(this);
				txtLeft.addActionListener(this);
				txtTop.addActionListener(this);
				txtWidth.addActionListener(this);
				txtHeight.addActionListener(this);
				butFont.addActionListener(this);

				if(!bColorValid)
				{
					butColor.setEnabled(false);
					butGradientColor1.setEnabled(false);
					butGradientColor2.setEnabled(false);
					chkColorOrGradient.setEnabled(false);
					butFont.setEnabled(false);
				}
				else
				{
					if(bColorOrGradient)
					{
						butColor.setEnabled(true);
						butGradientColor1.setEnabled(false);
						butGradientColor2.setEnabled(false);
						chkColorOrGradient.setSelected(bColorOrGradient);
					}
					else
					{
						butColor.setEnabled(false);
						butGradientColor1.setEnabled(true);
						butGradientColor2.setEnabled(true);
						chkColorOrGradient.setSelected(bColorOrGradient);
					}
				}
				if(!bFontPresent)
					butFont.setEnabled(false);
			}

			public void actionPerformed(ActionEvent e)
			{
				Object o = e.getSource();
				if(o == butOK)
				{
					int left = Integer.parseInt(txtLeft.getText());
					int width = Integer.parseInt(txtWidth.getText());
					int top = Integer.parseInt(txtTop.getText());
					int height = Integer.parseInt(txtHeight.getText());
					sClass.reShape(left, top, width, height);
//						sClass.moveBy(lLeft - left, lTop - top);
//						sClass.setSize(width, height);
					fileTrace.write("In actionPerformed. (left, top) (" + left + ", " + top + "),  (" + width + ", " + height + ")");
					if(chkColorOrGradient.isSelected())
					{
						colorGradient = COLOR;
						color = butColor.getBackground();
					}
					else
					{
						colorGradient = GRADIENT;
						gPaint = new GradientPaint(0, 0,butGradientColor1.getBackground(), 1000, 1000, butGradientColor2.getBackground());
					}

					if(bFontPresent)
					{
						((TextShape)sClass).setFont(fRet);
					}
					frameProperties.setVisible(false);
					reDrawCanvas();
					return;
				}
				else if(o == butCancel)
				{
					frameProperties.setVisible(false);
					reDrawCanvas();
					return;
				}

				bChanged = true;
				if(o == chkColorOrGradient)
				{
					if(chkColorOrGradient.isSelected())
					{
						butColor.setEnabled(true);
						butGradientColor1.setEnabled(false);
						butGradientColor2.setEnabled(false);
					}
					else
					{
						butGradientColor1.setEnabled(true);
						butGradientColor2.setEnabled(true);
						butColor.setEnabled(false);
					}
				}
				if(o == butColor)
				{
					Color c = jColorChooser1.showDialog(null, "Choose Color", butColor.getBackground());
					if(c != null)
						butColor.setBackground(c);
				}
				if(o == butGradientColor1)
				{
					Color c = jColorChooser1.showDialog(null, "Choose Color", butGradientColor1.getBackground());
					if(c != null)
						butGradientColor1.setBackground(c);
				}
				if(o == butGradientColor2)
				{
					Color c = jColorChooser1.showDialog(null, "Choose Color", butGradientColor2.getBackground());
					if(c != null)
						butGradientColor2.setBackground(c);
				}
				if(o == butFont)
				{
					FontSelection fSelection = new FontSelection(false);
					fSelection.showDialog();
					if(specificFont != null)
					{
						butFont.setText(specificFont.getName());
						butFont.setFont(specificFont);
					}
				}

			}

			public void showDialog()
			{
				JApplet appletProperties = new PropertiesDialog();
				((PropertiesDialog)appletProperties).setProperties(lLeft, lTop, lWidth, lHeight, bColorValid, displayColor, gradientColor1, gradientColor2, bColorOrGradient, fTextFont, bFontPresent, shapes.indexOf(sClass));
				appletProperties.init();
				frameProperties.getContentPane().add(appletProperties);
				((JComponent)frameProperties.getContentPane()).setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
				frameProperties.setSize(FRAMEWIDTH, FRAMEHEIGHT);
				frameProperties.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frameProperties.getWidth()) / 2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frameProperties.getHeight()) / 2);
				frameProperties.pack();
				frameProperties.setVisible(true);
				frameProperties.setResizable(false);
				frameProperties.addWindowListener(((PropertiesDialog)appletProperties));
			}

			public void windowActivated(WindowEvent e)
			{
			}

			public void windowClosed(WindowEvent e)
			{
				fileTrace.write("In WindowClosed");
				this.setVisible(false);
			}

			public void windowClosing(WindowEvent e)
			{
				this.setVisible(false);
				fileTrace.write("In WindowClosing");
			}

			public void windowDeactivated(WindowEvent e)
			{
			}

			public void windowDeiconified(WindowEvent e)
			{

			}

			public void windowIconified(WindowEvent e)
			{
			}

			public void windowOpened(WindowEvent e)
			{
			}

		}

	}  // end of class Shape


	class LineShape extends ShapeClass
	{
		LineShape()
		{
			super();
			shapeIdentifier = FrontEnd.LINE;
		}
		double slope = (height == 0)?0:width / height;
		double accuracy = .00000001;
		void draw(Graphics g)
		{
			ga = (Graphics2D)g;
			if(this.colorGradient == COLOR)
				ga.setColor(color);
			else
			{
				Color color1 = gPaint.getColor1();
				Color color2 = gPaint.getColor2();
				gPaint = new GradientPaint(left,top + height / 2,color1,left + width,top + height / 2,color2,cyclicOrNot);
				ga.setPaint(gPaint);
			}
			ga.drawLine(left,top,left + width,top + height);
		}

		boolean containsPoint(int x,int y)
		{

			// Check whether the shape contains the point (x,y).
			// By default, this just checks whether (x,y) is inside the
			// rectangle that bounds the shape.  This method should be
			// overridden by a subclass if the default behavior is not
			// appropriate for the subclass.
			if (x >= left && x < left+width && y >= top && y < top+height)
				return true;
			else
				return false;

		}


	}

	class RectShape extends ShapeClass
	{
		boolean filled;
		// This class represents rectangle shapes.
		RectShape(boolean filled)
		{
			super();
			this.filled = filled;
			if(filled)
				shapeIdentifier = FrontEnd.FILLEDRECTANGLE;
			else
				shapeIdentifier = FrontEnd.RECTANGLE;
		}
		void draw(Graphics g)
		{
			ga = (Graphics2D)g;
			if(this.colorGradient == COLOR)
				ga.setColor(color);
			else
			{
				Color color1 = gPaint.getColor1();
				Color color2 = gPaint.getColor2();
				gPaint = new GradientPaint(left,top + height / 2,color1,left + width,top + height / 2,color2,cyclicOrNot);
				ga.setPaint(gPaint);
			}
			if(filled)
				ga.fillRect(left,top,width,height);
			else
			{
				g.setColor(fgColor);
				fileTrace.write("In drawRect. (" + left + ", " + top + "),  (" + width + ", " + height + ")");
				g.drawRect(left,top,width,height);
			}
		}
		boolean containsPoint(int x, int y)
		{
			// Check whether the shape contains the point (x,y).
			// By default, this just checks whether (x,y) is inside the
			// rectangle that bounds the shape.  This method should be
			// overridden by a subclass if the default behavior is not
			// appropriate for the subclass.
			if (x >= left && x < left+width && y >= top && y < top+height)
				return true;
			else
				return false;
		}
	}


	class OvalShape extends ShapeClass
	{
		boolean filled;
		// This class represents oval shapes.
		OvalShape(boolean filled)
		{
			super();
			this.filled = filled;
			if(filled)
				shapeIdentifier = FrontEnd.FILLEDOVAL;
			else
				shapeIdentifier = FrontEnd.OVAL;
		}
		void draw(Graphics g)
		{
			ga = (Graphics2D)g;
			if(this.colorGradient == COLOR)
				ga.setColor(color);
			else
			{
				Color color1 = gPaint.getColor1();
				Color color2 = gPaint.getColor2();
				gPaint = new GradientPaint(left,top + height / 2,color1,left + width,top + height / 2,color2,cyclicOrNot);
				ga.setPaint(gPaint);
			}
			if(filled)
				ga.fillOval(left,top,width,height);
			else
			{
				g.setColor(fgColor);
				g.drawOval(left,top,width,height);
			}

		}
		boolean containsPoint(int x, int y)
		{
			if (x >= left && x < left+width && y >= top && y < top+height)
				return true;
			else
				return false;
		}
	}


	class RoundRectShape extends ShapeClass
	{
		boolean filled;
		// This class represents rectangle shapes with rounded corners.

		RoundRectShape(boolean filled)
		{
			super();
			this.filled = filled;
			if(filled)
				shapeIdentifier = FrontEnd.FILLEDROUNDRECT;
			else
				shapeIdentifier = FrontEnd.ROUNDRECT;
		}
		// (Note that it uses the inherited version of the
		// containsPoint(x,y) method, even though that is not perfectly
		// accurate when (x,y) is near one of the corners.)
		void draw(Graphics g)
		{
			fileTrace.write("In RoundRectShape::draw. (left, top) (" + left + ", " + top + "),  (" + width + ", " + height + ")");
			ga = (Graphics2D)g;
			if(this.colorGradient == COLOR)
				ga.setColor(color);
			else
			{
				Color color1 = gPaint.getColor1();
				Color color2 = gPaint.getColor2();
				gPaint = new GradientPaint(left,top + height / 2,color1,left + width,top + height / 2,color2,cyclicOrNot);
				ga.setPaint(gPaint);
			}
			if(filled)
				ga.fillRoundRect(left,top,width,height,width/3,height/3);
			else
			{
				g.setColor(fgColor);
				g.drawRoundRect(left,top,width,height,width/3,height/3);
			}
		}
		boolean containsPoint(int x, int y)
		{
			// Check whether the shape contains the point (x,y).
			// By default, this just checks whether (x,y) is inside the
			// rectangle that bounds the shape.  This method should be
			// overridden by a subclass if the default behavior is not
			// appropriate for the subclass.
			if (x >= left && x < left+width && y >= top && y < top+height)
				return true;
			else
				return false;
		}
	}

	class TextShape extends ShapeClass
   	{
	       	String text;
	       	Font font;
	       	public TextShape(String s,Font f)
	       	{
			super();
			font = f;
			text = s;
			AffineTransform aTransform = new AffineTransform();
			FontRenderContext fRenderContext = new FontRenderContext(aTransform, true, false);
			Rectangle2D rect = f.getStringBounds(s, fRenderContext);
			width = (int)rect.getWidth();
			height = (int)rect.getHeight();
			JLabel label = new JLabel(text);
			left = 100;
			top = 100;
			shapeIdentifier = FrontEnd.TEXT;
			color = fgColor;
		}
		public TextShape(TextShape s)
		{
			this.text = s.text;
			this.font = s.font;
		}

		public void setFont(Font f)
		{
			font = f;
			if(font != null)
			{
				AffineTransform aTransform = new AffineTransform();
				FontRenderContext fRenderContext = new FontRenderContext(aTransform, true, false);
				Rectangle2D rect = font.getStringBounds(text, fRenderContext);
				width = (int)rect.getWidth();
				height = (int)rect.getHeight();
			}

		}

		void draw(Graphics g)
		{
			ga = (Graphics2D)g;
			g.setFont(font);

			if(colorGradient == COLOR)
				ga.setColor(color);
			else
			{
				Color color1 = gPaint.getColor1();
				Color color2 = gPaint.getColor2();
				gPaint = new GradientPaint(left,top + height / 2,color1,left + width,top + height / 2,color2,cyclicOrNot);
				ga.setPaint(gPaint);
			}
			ga.drawString(text,left,top);
		}

		boolean containsPoint(int x,int y)
		{
			if (x >= left && x < left+width && y >= top - height && y < top)
				return true;
			else
				return false;
		}
	}

	class BezierShape extends ShapeClass
	{
		BezierPoint points[];
		int nPoints = 0;
		double prevX,prevY;
		class BezierPoint
		{
			int x,y;
			public BezierPoint(int x,int y)
			{
				this.x = x;
				this.y = y;
			}
		}
		public BezierShape()
		{
			super();
			this.color = drawColor;
			points = new BezierPoint[4];
			for(int i = 0;i < 4;i++)
				points[i] = new BezierPoint(0,0);
			shapeIdentifier = FrontEnd.BEZIER;
			width = 20;
			height = 20;
		}

		public BezierShape(BezierShape s)
		{
			this.nPoints = s.nPoints;
			this.points = s.points;
			this.prevX = s.prevX;
			this.prevY = s.prevY;
		}


		public void reShapeBezier(int x,int y)
		{
			if(nPoints == 0)
			{
				left = x;
				top = y;

			}
			points[nPoints++] = new BezierPoint(x,y);
		}

		public void draw(Graphics g)
		{
			ga = (Graphics2D)g;
			if(this.colorGradient == COLOR)
				ga.setColor(color);
			else
			{
				Color color1 = gPaint.getColor1();
				Color color2 = gPaint.getColor2();
				gPaint = new GradientPaint(left,top + height / 2,color1,left + width,top + height / 2,color2,cyclicOrNot);
				ga.setPaint(gPaint);
			}
			ArrayList curvePoints = new ArrayList();
			double t = 0;
			for(t = 0;t <= 1;t += .01)
				curvePoints.add(bezierPoints(points,t));
			BezierPoint q = new BezierPoint(0,0);;
			for(int i = 0;i < curvePoints.size();i++)
			{
				BezierPoint p = (BezierPoint)curvePoints.get(i);
				if(i == 0)
					q = (BezierPoint)curvePoints.get(i);
				g.drawLine(p.x,p.y,q.x,q.y);
				q = p;
			}
		}

		BezierPoint bezierPoints(BezierPoint[] points,double t)
		{
			double x = 0,y = 0;
			for(int i = 0;i < nPoints;i++)
			{
				double tPow = 1,comTPow = 1;
				for(int j = 0;j < nPoints - i - 1;j++)
					tPow *= t;
				for(int j = 0;j < i;j++)
					comTPow *= (1 - t);
				x += tPow * comTPow * points[i].x;
				y += tPow * comTPow * points[i].y;
			}
			if(t == 0.0f)
			{
				prevX = x;
				prevY = y;
			}
			return new BezierPoint((int)x,(int)y);
		}

		void moveBy(int dx,int dy)
		{
			for(int i = 0;i < points.length;i++)
			{
				points[i].x += dx;
				points[i].y += dy;
			}
			left += dx;
			top += dy;
		}
		boolean containsPoint(int x,int y)
		{
			if (x >= left - width && x < left + width && y >= top - height && y < top + height)
				return true;
			if (x >= points[nPoints - 1].x - width && x < points[nPoints - 1].x + width && y >= points[nPoints - 1].y - height && y < points[nPoints - 1].y + height)
				return true;
			else
				return false;

		}
	}

	class ImageShape extends ShapeClass
	{
		// This class represents rectangle shapes.
		Image image;
		String imagePath;
		public ImageShape(String image)
		{
			super();
			imagePath = image;
			ImageIcon icon = new ImageIcon(image);
			this.image = icon.getImage();
			shapeIdentifier = FrontEnd.IMAGE;
		}

		public ImageShape(ImageShape s)
		{
			this.image = s.image;
			this.imagePath = s.imagePath;
		}
		void draw(Graphics g)
		{
			ga = (Graphics2D)g;
			g.drawImage(this.image,left,top,width,height,new Color(Color.TRANSLUCENT),new Applet());
		}
		boolean containsPoint(int x, int y)
		{
			// Check whether the shape contains the point (x,y).
			// By default, this just checks whether (x,y) is inside the
			// rectangle that bounds the shape.  This method should be
			// overridden by a subclass if the default behavior is not
			// appropriate for the subclass.
			if (x >= left && x < left+width && y >= top && y < top+height)
				return true;
			else
				return false;
		}
	}
}

class FileChoosers extends JPanel
{

	class MyFileFilter extends javax.swing.filechooser.FileFilter
	{
		private Hashtable filters = null;
		private String description = null;
		private String fullDescription = null;
		private boolean useExtensionsInDescription = true;

		public MyFileFilter()
		{
			this.filters = new Hashtable();
		}

		public MyFileFilter(String extension)
		{
			this(extension,null);
		}

		public MyFileFilter(String extension, String description)
		{
			this();
			if(extension!=null)
				addExtension(extension);
			if(description!=null)
				setDescription(description);
		}

		public MyFileFilter(String[] filters)
		{
			this(filters, null);
		}

		public MyFileFilter(String[] filters, String description)
		{
			this();
			for (int i = 0; i < filters.length; i++)
			{
				// add filters one by one
				addExtension(filters[i]);
			}
			if(description!=null)
				setDescription(description);
		}

		public boolean accept(File f)
		{
			if(f != null)
			{
				if(f.isDirectory())
				{
					return true;
				}
				String extension = getExtension(f);
				if(extension != null && filters.get(getExtension(f)) != null)
				{
					return true;
				};
			}
			return false;
		}

		public String getExtension(File f)
		{
			if(f != null)
			{
				String filename = f.getName();
				int i = filename.lastIndexOf('.');
				if(i>0 && i<filename.length()-1)
				{
					return filename.substring(i+1).toLowerCase();
				}
			}
			return null;
		}

		public void addExtension(String extension)
		{
			if(filters == null)
			{
				filters = new Hashtable(5);
			}
			filters.put(extension.toLowerCase(), this);
			fullDescription = null;
		}


		public String getDescription()
		{
			if(fullDescription == null)
			{
				if(description == null || isExtensionListInDescription())
				{
					fullDescription = description==null ? "(" : description + " (";
					// build the description from the extension list
					Enumeration extensions = filters.keys();
					if(extensions != null)
					{
						fullDescription += "." + (String) extensions.nextElement();
						while (extensions.hasMoreElements())
						{
							fullDescription += ", " + (String) extensions.nextElement();
						}
					}
					fullDescription += ")";
				}
				else
				{
					fullDescription = description;
				}
			}
			return fullDescription;
		}

		public void setDescription(String description)
		{
			this.description = description;
			fullDescription = null;
		}

		public void setExtensionListInDescription(boolean b)
		{
			useExtensionsInDescription = b;
			fullDescription = null;
		}

		public boolean isExtensionListInDescription()
		{
			return useExtensionsInDescription;
		}
	}

	JFileChooser chooser;
	MyFileFilter filter;
	static File currentDirectory = new File("c:\\");
	public FileChoosers(int b)
	{
		chooser = new JFileChooser();
		if(b == 1)
		{
			filter = new MyFileFilter("gag");
			filter.setDescription("Paint file");
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setApproveButtonMnemonic('s');
			chooser.setApproveButtonText("Save");
			chooser.setApproveButtonToolTipText("Save the file");
		}
		else if(b == 0)
		{
			filter = new MyFileFilter("gag");
			filter.setDescription("Paint file");
			chooser.setApproveButtonMnemonic('o');
			chooser.setApproveButtonText("Open");
			chooser.setApproveButtonToolTipText("Open a file");
		}
		else if(b == 2)
		{
			filter = new MyFileFilter();
			filter.addExtension("jpg");
			filter.addExtension("gif");
			filter.addExtension("bmp");
			filter.addExtension("jpeg");
			filter.setDescription("ImageFiles");
			chooser.setApproveButtonMnemonic('o');
			chooser.setApproveButtonText("Open");
			chooser.setApproveButtonToolTipText("Open a file");
		}
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(currentDirectory);
	}

	public String shows()
	{
		int retval = chooser.showDialog(this, null);
		if(retval == JFileChooser.APPROVE_OPTION)
		{
			File theFile = chooser.getSelectedFile();
			if(theFile != null)
			{
				String filename = "";
				filename =theFile.getAbsolutePath();
				currentDirectory = new File(theFile.getParent());
				return filename;
			}
		}
		return null;
	}
}




class AquaMetalTheme extends DefaultMetalTheme
{

	private final ColorUIResource primary1 = new ColorUIResource(102, 153, 153);
	private final ColorUIResource primary2 = new ColorUIResource(128, 192, 192);
	private final ColorUIResource primary3 = new ColorUIResource(159, 235, 235);

    public String getName() { return "Aqua"; }
    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }
}


class ContrastMetalTheme extends DefaultMetalTheme
{

	private final ColorUIResource primary1 = new ColorUIResource(0, 0, 0);
	private final ColorUIResource primary2 = new ColorUIResource(204, 204, 204);
	private final ColorUIResource primary3 = new ColorUIResource(255, 255, 255);
	private final ColorUIResource primaryHighlight = new ColorUIResource(102,102,102);

	private final ColorUIResource secondary2 = new ColorUIResource(204, 204, 204);
	private final ColorUIResource secondary3 = new ColorUIResource(255, 255, 255);
	private final ColorUIResource controlHighlight = new ColorUIResource(102,102,102);

    public void addCustomEntriesToTable(UIDefaults table)
    {

        Border blackLineBorder = new BorderUIResource(new LineBorder( getBlack() ));
        Border whiteLineBorder = new BorderUIResource(new LineBorder( getWhite() ));

		Object textBorder = new BorderUIResource( new CompoundBorder(
			blackLineBorder,
			new BasicBorders.MarginBorder()));

        table.put( "ToolTip.border", blackLineBorder);
		table.put( "TitledBorder.border", blackLineBorder);
        table.put( "Table.focusCellHighlightBorder", whiteLineBorder);
        table.put( "Table.focusCellForeground", getWhite());

        table.put( "TextField.border", textBorder);
        table.put( "PasswordField.border", textBorder);
        table.put( "TextArea.border", textBorder);
        table.put( "TextPane.font", textBorder);


    }
    public ColorUIResource getAcceleratorForeground() { return getBlack(); }
    public ColorUIResource getAcceleratorSelectedForeground() { return getWhite(); }
    public ColorUIResource getControlHighlight() { return super.getSecondary3(); }
    public ColorUIResource getFocusColor() { return getBlack(); }
    public ColorUIResource getHighlightedTextColor() { return getWhite(); }
    public ColorUIResource getMenuSelectedBackground() { return getBlack(); }
    public ColorUIResource getMenuSelectedForeground() { return getWhite(); }
    public String getName() { return "Contrast"; }
    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }
    public ColorUIResource getPrimaryControlHighlight() { return primaryHighlight;}
    protected ColorUIResource getSecondary2() { return secondary2; }
    protected ColorUIResource getSecondary3() { return secondary3; }
    public ColorUIResource getTextHighlightColor() { return getBlack(); }
}

class BigContrastMetalTheme extends ContrastMetalTheme
{

	private final FontUIResource controlFont = new FontUIResource("Dialog", Font.BOLD, 24);
	private final FontUIResource systemFont = new FontUIResource("Dialog", Font.PLAIN, 24);
	private final FontUIResource windowTitleFont = new FontUIResource("Dialog", Font.BOLD, 24);
	private final FontUIResource userFont = new FontUIResource("SansSerif", Font.PLAIN, 24);
	private final FontUIResource smallFont = new FontUIResource("Dialog", Font.PLAIN, 20);


    public void addCustomEntriesToTable(UIDefaults table)
    {
		super.addCustomEntriesToTable(table);

		final int internalFrameIconSize = 30;
		table.put("InternalFrame.closeIcon", MetalIconFactory.getInternalFrameCloseIcon(internalFrameIconSize));
		table.put("InternalFrame.maximizeIcon", MetalIconFactory.getInternalFrameMaximizeIcon(internalFrameIconSize));
		table.put("InternalFrame.iconizeIcon", MetalIconFactory.getInternalFrameMinimizeIcon(internalFrameIconSize));
		table.put("InternalFrame.minimizeIcon", MetalIconFactory.getInternalFrameAltMaximizeIcon(internalFrameIconSize));


		Border blackLineBorder = new BorderUIResource( new MatteBorder( 2,2,2,2, Color.black) );
		Border textBorder = blackLineBorder;

        table.put( "ToolTip.border", blackLineBorder);
		table.put( "TitledBorder.border", blackLineBorder);


        table.put( "TextField.border", textBorder);
        table.put( "PasswordField.border", textBorder);
        table.put( "TextArea.border", textBorder);
        table.put( "TextPane.font", textBorder);

        table.put( "ScrollPane.border", blackLineBorder);

        table.put( "ScrollBar.width", new Integer(25) );



    }
    public FontUIResource getControlTextFont() { return controlFont;}
    public FontUIResource getMenuTextFont() { return controlFont;}
    public String getName() { return "Big Contrast"; }
    public FontUIResource getSubTextFont() { return smallFont;}
    public FontUIResource getSystemTextFont() { return systemFont;}
    public FontUIResource getUserTextFont() { return userFont;}
    public FontUIResource getWindowTitleFont() { return windowTitleFont;}
}


class DemoMetalTheme extends DefaultMetalTheme
{

	private final FontUIResource controlFont = new FontUIResource("Dialog", Font.BOLD, 18);
	private final FontUIResource systemFont = new FontUIResource("Dialog", Font.PLAIN, 18);
	private final FontUIResource userFont = new FontUIResource("SansSerif", Font.PLAIN, 18);
	private final FontUIResource smallFont = new FontUIResource("Dialog", Font.PLAIN, 14);

    public void addCustomEntriesToTable(UIDefaults table)
    {
		super.addCustomEntriesToTable(table);

		final int internalFrameIconSize = 22;
		table.put("InternalFrame.closeIcon", MetalIconFactory.getInternalFrameCloseIcon(internalFrameIconSize));
		table.put("InternalFrame.maximizeIcon", MetalIconFactory.getInternalFrameMaximizeIcon(internalFrameIconSize));
		table.put("InternalFrame.iconizeIcon", MetalIconFactory.getInternalFrameMinimizeIcon(internalFrameIconSize));
		table.put("InternalFrame.minimizeIcon", MetalIconFactory.getInternalFrameAltMaximizeIcon(internalFrameIconSize));



		table.put( "ScrollBar.width", new Integer(21) );



    }
    public FontUIResource getControlTextFont() { return controlFont;}
    public FontUIResource getMenuTextFont() { return controlFont;}
    public String getName() { return "Presentation"; }
    public FontUIResource getSubTextFont() { return smallFont;}
    public FontUIResource getSystemTextFont() { return systemFont;}
    public FontUIResource getUserTextFont() { return userFont;}
    public FontUIResource getWindowTitleFont() { return controlFont;}
}

class GreenMetalTheme extends DefaultMetalTheme
{

	// greenish colors
	private final ColorUIResource primary1 = new ColorUIResource(51, 102, 51);
	private final ColorUIResource primary2 = new ColorUIResource(102, 153, 102);
	private final ColorUIResource primary3 = new ColorUIResource(153, 204, 153);

    public String getName() { return "Green"; }
    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }
}





class KhakiMetalTheme extends DefaultMetalTheme
{

	private final ColorUIResource primary1 = new ColorUIResource( 87,  87,  47);
	private final ColorUIResource primary2 = new ColorUIResource(159, 151, 111);
	private final ColorUIResource primary3 = new ColorUIResource(199, 183, 143);

	private final ColorUIResource secondary1 = new ColorUIResource( 111,  111,  111);
	private final ColorUIResource secondary2 = new ColorUIResource(159, 159, 159);
	private final ColorUIResource secondary3 = new ColorUIResource(231, 215, 183);

    public String getName() { return "Khaki"; }
    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }
    protected ColorUIResource getSecondary1() { return secondary1; }
    protected ColorUIResource getSecondary2() { return secondary2; }
    protected ColorUIResource getSecondary3() { return secondary3; }
}



class UISwitchListener implements PropertyChangeListener
{
	JComponent componentToSwitch;

    public UISwitchListener(JComponent c)
    {
        componentToSwitch = c;
    }
    public void propertyChange(PropertyChangeEvent e)
    {
        String name = e.getPropertyName();
		if (name.equals("lookAndFeel"))
		{
			SwingUtilities.updateComponentTreeUI(componentToSwitch);
			componentToSwitch.invalidate();
			componentToSwitch.validate();
			componentToSwitch.repaint();
		}
    }
}

class MetalThemeMenu extends JMenu implements ActionListener
{

	MetalTheme[] themes;
	public MetalThemeMenu(String name, MetalTheme[] themeArray)
	{
		super(name);
		themes = themeArray;
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < themes.length; i++)
		{
			JRadioButtonMenuItem item = new JRadioButtonMenuItem( themes[i].getName() );
			group.add(item);
			add( item );
			item.setActionCommand(i+"");
			item.addActionListener(this);
			if ( i == 0)
				item.setSelected(true);
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		String numStr = e.getActionCommand();
		MetalTheme selectedTheme = themes[ Integer.parseInt(numStr) ];
		MetalLookAndFeel.setCurrentTheme(selectedTheme);
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch (Exception ex)
		{
			System.out.println("Failed loading Metal");
			System.out.println(ex);
		}
	}
}



public class Main extends JPanel implements WindowListener
{
	JFrame f;
	FrontEnd F;
	public void create()
	{
		F=new FrontEnd();
		F.init();
		f=new JFrame();
		((JComponent)f.getContentPane()).setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		f.pack();
		f.setVisible(true);
		f.getContentPane().add(F);
		f.addWindowListener(this);
		UIManager.addPropertyChangeListener(new UISwitchListener(F.getRootPane()));
	}

	public void windowActivated(WindowEvent e)
	{
	}

	public void windowClosed(WindowEvent e)
	{
		F.askForSave(false);
		System.exit(0);
	}

	public void windowClosing(WindowEvent e)
	{
		F.askForSave(false);
		F.closeTrace();
		System.exit(0);
	}

	public void windowDeactivated(WindowEvent e)
	{
	}

	public void windowDeiconified(WindowEvent e)
	{

	}

	public void windowIconified(WindowEvent e)
	{
	}

	public void windowOpened(WindowEvent e)
	{
	}
	public static void main(String args[])
	{
		Main m = new Main();
		m.create();
	}
}