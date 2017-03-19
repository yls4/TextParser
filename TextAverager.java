import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Component;

public class TextAverager {
    final static int width = 1000;
    final static int height = 420;
    final static String delimiter = " ";
	public static void main(String[] args) throws IOException
	{
		createGui();
	}
	
	private static void createGui()
	{	
		JFrame frame = new JFrame("Text Averager");
		SpringLayout layout = new SpringLayout();
		JPanel jpanel = new JPanel(layout);
		
		frame.setSize(width, height);
		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JLabel label1 = new JLabel("Text 1");
		jpanel.add(label1);
		layout.putConstraint(SpringLayout.WEST, label1, 130, SpringLayout.WEST, jpanel);
		
		JLabel label2 = new JLabel("Text 2");
		jpanel.add(label2);
		layout.putConstraint(SpringLayout.WEST, label2, 450, SpringLayout.WEST, jpanel);
		
		JLabel labelResult = new JLabel("Result");
		layout.putConstraint(SpringLayout.WEST, labelResult, 800, SpringLayout.WEST, jpanel);
		jpanel.add(labelResult);
		
		JLabel wlabel1 = new JLabel("Weight:");
		layout.putConstraint(SpringLayout.WEST, wlabel1, 10, SpringLayout.WEST, jpanel);
		layout.putConstraint(SpringLayout.NORTH, wlabel1, 300, SpringLayout.NORTH, jpanel);
		jpanel.add(wlabel1);
		
		JLabel wlabel2 = new JLabel("Weight:");
		layout.putConstraint(SpringLayout.WEST, wlabel2, 333, SpringLayout.WEST, jpanel);
		layout.putConstraint(SpringLayout.NORTH, wlabel2, 300, SpringLayout.NORTH, jpanel);
		jpanel.add(wlabel2);
		
		JTextArea text1 = new JTextArea(15, 25);
		JScrollPane sp = new JScrollPane(text1);
		text1.setBorder(border);
		layout.putConstraint(SpringLayout.WEST, sp, 10, SpringLayout.WEST, jpanel);
		layout.putConstraint(SpringLayout.NORTH, sp, 30, SpringLayout.NORTH, jpanel);
		text1.setLineWrap(true);
		jpanel.add(sp);
		
		JTextField weight1 = new JTextField(15);
		weight1.setBorder(border);
		layout.putConstraint(SpringLayout.WEST, weight1, 60, SpringLayout.WEST, jpanel);
		layout.putConstraint(SpringLayout.NORTH, weight1, 300, SpringLayout.NORTH, jpanel);
		jpanel.add(weight1);
		
		JTextArea text2 = new JTextArea(15, 25);
		JScrollPane sp2 = new JScrollPane(text2);
		text2.setBorder(border);
		layout.putConstraint(SpringLayout.WEST, sp2, 333, SpringLayout.WEST, jpanel);
		layout.putConstraint(SpringLayout.NORTH, sp2, 30, SpringLayout.NORTH, jpanel);
		text2.setLineWrap(true);
		jpanel.add(sp2);
		
		JTextField weight2 = new JTextField(15);
		weight2.setBorder(border);
		layout.putConstraint(SpringLayout.WEST, weight2, 383, SpringLayout.WEST, jpanel);
		layout.putConstraint(SpringLayout.NORTH, weight2, 300, SpringLayout.NORTH, jpanel);
		jpanel.add(weight2);
		
		final JTextArea output = new JTextArea(15, 25);
		output.setBorder(border);
		layout.putConstraint(SpringLayout.WEST, output, 666, SpringLayout.WEST, jpanel);
		layout.putConstraint(SpringLayout.NORTH, output, 30, SpringLayout.NORTH, jpanel);
		jpanel.add(output);
		
		JButton calculateButton = new JButton("Calculate Average");
		layout.putConstraint(SpringLayout.WEST, calculateButton, 490, SpringLayout.WEST, jpanel);
		layout.putConstraint(SpringLayout.NORTH, calculateButton, 345, SpringLayout.NORTH, jpanel);
		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = CalculateAverage(text1.getText(), text2.getText(), weight1.getText(), weight2.getText());
				output.setText(result);
			}
		});
		jpanel.add(calculateButton);
		
		frame.add(jpanel);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private static String CalculateAverage(String t1, String t2, String w1, String w2)
	{
		t1 = t1.trim();
		t2 = t2.trim();
		w1 = w1.trim();
		w2 = w2.trim();
		
		String[] text1 = t1.split(delimiter);
		String[] text2 = t2.split(delimiter);
		String output = "";
		
		for (String integer: text1) {
			if(!isInteger(integer) || integer.isEmpty()) {
				output += "Text 1 must contain only integers\n";
				break;
			}
		}
		
		for (String integer: text2) {
			if(!isInteger(integer) || integer.isEmpty()) {
				output += "Text 2 must contain only integers\n";
				break;
			}
		}
		
		if (!(text1.length == text2.length)) {
				output += "Text fields must have same number of integers\n";
		}
		
		if (!isInteger(w1) || !isInteger(w2) || w1.isEmpty() || w2.isEmpty())
		{
			output += "Must enter integers for weights\n";
		}
		
		if (!output.equals("")) {
			return output;
		}
		else {
			for (int i = 0; i < text1.length; i++) {
				float i1 = Float.parseFloat(text1[i]) * Float.parseFloat(w1);
				float i2 = Float.parseFloat(text2[i]) * Float.parseFloat(w2);
				float totalWeight = Float.parseFloat(w1) + Float.parseFloat(w2);
				output += (i1 + i2)/totalWeight + " ";
			}
		}
		return output;
	}
	
	private static boolean isInteger(String s)
	{
		try {
			Float.parseFloat(s);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
