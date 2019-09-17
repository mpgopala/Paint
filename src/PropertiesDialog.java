import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

public class PropertiesDialog extends JApplet implements ActionListener
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
	JTextField 	txtRight;
	JTextField 	txtTop;
	JTextField 	txtBottom;
	JCheckBox	chkColorOrGradient;
	JButton 	butColor;
	JButton		butGradientColor1;
	JButton		butGradientColor2;
	JButton 	butFont;
	JButton 	butOK;
	JButton 	butCancel;
	boolean		bChanged;

	JLabel		labLeft 	= new JLabel("Left");
	JLabel		labRight	= new JLabel("Right");
	JLabel 		labTop		= new JLabel("Top");
	JLabel 		labBottom	= new JLabel("Bottom");
	JLabel 		labColor	= new JLabel("Color");
	JLabel		labGradient	= new JLabel("Gradient");
	JLabel 		labColor1	= new JLabel("Color1");
	JLabel 		labColor2	= new JLabel("Color2");
	JLabel 		labFont		= new JLabel("Font");

	int lLeft;
	int lTop;
	int lRight;
	int lBottom;
	Color displayColor;
	Color gradientColor1;
	Color gradientColor2;
	boolean bColorOrGradient;
	Font fTextFont;
	boolean bFontPresent;
	boolean bColorValid;

	public PropertiesDialog(int lLeft, int lTop, int lRight, int lBottom, boolean bColorValid, Color displayColor, Color gradientColor1, Color gradientColor2, boolean bColorOrGradient, Font fTextFont, boolean bFontPresent)
	{
		this.lLeft = lLeft;
		this.lRight = lRight;
		this.lTop = lTop;
		this.lBottom = lBottom;

		this.bColorValid = bColorValid;

		this.displayColor = displayColor;
		this.gradientColor1 = gradientColor1;
		this.gradientColor2 = gradientColor2;
		this.bColorOrGradient = bColorOrGradient;
		this.fTextFont = fTextFont;
		this.bFontPresent = bFontPresent;

		this.bChanged = false;
	}

	public void init()
	{
		getContentPane().setLayout(null);
		getContentPane().setSize(FRAMEWIDTH, FRAMEHEIGHT);

		labLeft.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		getContentPane().add(labLeft);

		txtLeft = new JTextField();
		txtLeft.setText(String.valueOf(this.lLeft));
		txtLeft.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		getContentPane().add(txtLeft);

		int lCount = 1;

		labTop.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		getContentPane().add(labTop);

		txtTop = new JTextField();
		txtTop.setText(String.valueOf(this.lTop));
		txtTop.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		getContentPane().add(txtTop);

		lCount = 2;
		labRight.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		getContentPane().add(labRight);

		txtRight = new JTextField();
		txtRight.setText(String.valueOf(this.lRight));
		txtRight.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		getContentPane().add(txtRight);


		lCount = 3;
		labBottom.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		getContentPane().add(labBottom);

		txtBottom = new JTextField();
		txtBottom.setText(String.valueOf(this.lBottom));
		txtBottom.setBounds(PropertiesDialog.LEFTMARGIN + PropertiesDialog.WIDTH + PropertiesDialog.WIDTH_GAP, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		getContentPane().add(txtBottom);

		lCount = 4;
		chkColorOrGradient = new JCheckBox("Color");
		chkColorOrGradient.setBounds(PropertiesDialog.LEFTMARGIN, PropertiesDialog.TOPMARGIN + lCount * PropertiesDialog.HEIGHT + lCount * PropertiesDialog.HEIGHT_GAP, PropertiesDialog.WIDTH, PropertiesDialog.HEIGHT);
		chkColorOrGradient.setSelected(bColorOrGradient);
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

		if(!bColorValid)
		{
			butColor.setEnabled(false);
			butGradientColor1.setEnabled(false);
			butGradientColor2.setEnabled(false);
			chkColorOrGradient.setEnabled(false);
		}
		else
		{
			if(bColorOrGradient)
			{
				butColor.setEnabled(true);
				butGradientColor1.setEnabled(false);
				butGradientColor2.setEnabled(false);
			}
			else
			{
				butColor.setEnabled(false);
				butGradientColor1.setEnabled(true);
				butGradientColor2.setEnabled(true);
			}
		}
		if(!bFontPresent)
			butFont.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
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
	}

	public static void main(String args[])
	{
		JFrame frame = new JFrame();
		PropertiesDialog pDialog = new PropertiesDialog(100, 200, 300, 40, true, Color.red, Color.green, Color.blue, true, frame.getFont(), true);  ;
		pDialog.init();
		frame.getContentPane().add(pDialog);
		((JComponent)frame.getContentPane()).setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
		frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
}