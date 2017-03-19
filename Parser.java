import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

public class Parser {
    static File file = null;
    static public final String DELIMITER = "(?=LABEL: next)";
    
	public static void main(String[] args) throws IOException
	{
		createGui();
	}
	
	private static void createGui()
	{
		String path = null;
		
		JFrame frame = new JFrame("Text Parser");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		File curDir = new File(System.getProperty("user.dir"));
		JFileChooser fc = new JFileChooser(curDir);
		
		JLabel labelPath = new JLabel("Path:");
		JLabel labelFiles = new JLabel("Number of files:");
		JTextField numFiles = new JTextField();
		final JTextField filePath = new JTextField();
		
		JButton fileChooserButton = new JButton("Browse Files");
		JButton okButton = new JButton("Parse File");
		fileChooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.showOpenDialog(panel);
				file = fc.getSelectedFile();
				filePath.setText(file.getAbsolutePath());
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					readText(numFiles.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		panel.add(labelPath);
		panel.add(filePath);
		panel.add(fileChooserButton);
		panel.add(labelFiles);
		panel.add(numFiles);
		panel.add(okButton);
		
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private static void readText(String numFiles) throws IOException
	{
		int x = 0;
		try {
		    x = Integer.parseInt(numFiles);
		} catch (NumberFormatException e) {
		    System.out.println("Invalid File");
		    return;
		}
		
		if (x < 1)
		{
			System.out.println("Enter a positive Integer");
			return;
		}
		
		String uniqueId = "\\" + new SimpleDateFormat("yyyy MM dd HH mm ss").format(new Date());
		String path = System.getProperty("user.dir") + uniqueId;
		new File(path).mkdir();
		File logFile = null;
		
		String line = null;
		String allText = "";
		
		FileReader reader = new FileReader(file.getAbsolutePath());
		BufferedReader breader = new BufferedReader(reader);
		while ((line = breader.readLine()) != null)
		{
			allText = allText + line + System.getProperty("line.separator");
		}
		
		String[] s = allText.split(DELIMITER);
		String header = s[0];
		PrintWriter writer = null;
		int chunksPerText = (s.length-1) / Integer.parseInt(numFiles);
		int remainder = (s.length-1) % Integer.parseInt(numFiles);
		int counter = 0;
		int fileCounter = 1;
		String fileText = "";
		for (x = 0; x < Integer.parseInt(numFiles); x++)
		{
			if (writer != null)
			{
				writer.close();
			}
			fileText = header;
			logFile = new File(path + "\\" + x + ".txt");
			logFile.createNewFile();
			writer = new PrintWriter(logFile.getAbsolutePath(), "UTF-8");
			if (x != (Integer.parseInt(numFiles) - 1))
			{
				for (int y = 0; y < chunksPerText; y++)
				{
					fileText = fileText + s[fileCounter];
					fileCounter++;
				}
			}
			else
			{
				for (int y = fileCounter; y < s.length; y++)
				{
					fileText = fileText + s[fileCounter];
					fileCounter++;
				}
			}
			
			writer.write(fileText);
		}
		
		writer.close();
	}
	
}
