package ca.mcgill.ecse.carshop.view;

import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.carshop.controller.TOBusinessHour;

public class BusinessHoursVisualizer extends JPanel {
	
	private JLabel day;
	private JLabel openingHour;
	private JLabel closingHour;
	
	public BusinessHoursVisualizer(TOBusinessHour toHour) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		day = new JLabel();
		day.setText("\t"+toHour.getDayOfWeek());
		
		openingHour = new JLabel();
		openingHour.setText("Opens: " + sdf.format(toHour.getStartTime()));
		
		closingHour = new JLabel();
		closingHour.setText("Closes: " + sdf.format(toHour.getEndTime()));
		
		this.add(day);
		this.add(openingHour);
		this.add(closingHour);
		
	}
	

}
