package ca.mcgill.ecse.carshop.view;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.carshop.controller.TOComboItem;
import ca.mcgill.ecse223.carshop.controller.TOService;

public class OptServiceVisualizer extends JPanel {
	private JCheckBox optServiceSelected;
	private JLabel optServiceName;
	private JLabel duration;
	private JComboBox<String> startHour;
	private JLabel stLabel;
	private JLabel separator;
	private JComboBox<String> startMin;
	
	private TOService toService;
	
	public OptServiceVisualizer(TOComboItem toComboItem) {	
		this.toService = toComboItem.getService();
		
		optServiceSelected = new JCheckBox();
		optServiceSelected.setSelected(toComboItem.getMandatory());
		optServiceSelected.setEnabled(!toComboItem.getMandatory());
		
		optServiceName = new JLabel();
		optServiceName.setText(toComboItem.getService().getName());
		
		duration = new JLabel();
		duration.setText("Duration: "+String.valueOf(toComboItem.getService().getDuration())+"min");
		
		stLabel = new JLabel();
		stLabel.setText("Start time:");
		
		startHour = new JComboBox<String>(new String[0]);
		for(int i = 0; i< 24; i++) {
			startHour.addItem(String.valueOf(i));
		}
		
		separator = new JLabel();
		separator.setText(":");
		
		startMin = new JComboBox<String>(new String[0]);
		for(int i = 0; i< 60; i++) {
			startMin.addItem(String.valueOf(i));
		}

		this.add(optServiceSelected);
		this.add(optServiceName);
		this.add(stLabel);
		this.add(startHour);
		this.add(separator);
		this.add(startMin);
		this.add(duration);
	}
	
	public OptServiceVisualizer(TOService toService) {	
		this.toService = toService;
		
		optServiceSelected = new JCheckBox();
		optServiceSelected.setSelected(true);
		optServiceSelected.setEnabled(false);
		
		optServiceName = new JLabel();
		optServiceName.setText(toService.getName());
		
		duration = new JLabel();
		duration.setText("Duration: "+String.valueOf(toService.getDuration())+"min");
		
		stLabel = new JLabel();
		stLabel.setText("Start time:");
		
		startHour = new JComboBox<String>(new String[0]);
		for(int i = 0; i< 24; i++) {
			startHour.addItem(String.valueOf(i));
		}
		
		separator = new JLabel();
		separator.setText(":");
		
		startMin = new JComboBox<String>(new String[0]);
		for(int i = 0; i< 60; i++) {
			startMin.addItem(String.valueOf(i));
		}

		this.add(optServiceSelected);
		this.add(optServiceName);
		this.add(stLabel);
		this.add(startHour);
		this.add(separator);
		this.add(startMin);
		this.add(duration);
	}
	
	public boolean getIsSelected() {
		return optServiceSelected.isSelected();
	}
	
	public String getStartTime() {
		return String.valueOf(startHour.getSelectedIndex())+":"+String.valueOf(startMin.getSelectedIndex());
	}
	
	public TOService getTOService() {
		return toService;
	}
}
