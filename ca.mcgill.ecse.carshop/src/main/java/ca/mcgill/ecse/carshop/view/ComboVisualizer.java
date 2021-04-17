package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.carshop.controller.TOService;

public class ComboVisualizer extends JPanel {
	private JLabel service;
	private JLabel mandatory;
	private JCheckBox isMandatory;
	private HashMap<Integer, TOService> serviceMap;
	private JComboBox<String> services;
	private JButton deleteButton;
	
	public ComboVisualizer(List<TOService> toServices, CarShopPage page) {	

		
		services = new JComboBox<String>(new String[0]);
		serviceMap = new HashMap<Integer, TOService>();
		
		for(int i=0; i<toServices.size(); i++) {
			services.addItem(toServices.get(i).getName());
			serviceMap.put(i, toServices.get(i));
		}
		
		service = new JLabel();
		service.setText("Service:");
		
		mandatory = new JLabel();
		mandatory.setText("Mandatory:");
		
		isMandatory = new JCheckBox();
		
		deleteButton = new JButton();
		deleteButton.setText("X");
		deleteButton.setForeground(Color.RED);

		this.add(service);
		this.add(services);
		this.add(mandatory);
		this.add(isMandatory);
		this.add(deleteButton);
		
		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				page.removeOptComboItem(getVisualizer());
			}
		});

	}
	
	public TOService getSelectedService() throws Exception {
		TOService selectedService = null;
		int selectedIndex = services.getSelectedIndex();
		if(selectedIndex>=0) {
			selectedService = serviceMap.get(selectedIndex);
		} else {
			throw new Exception("Invalid optional service");
		}
		return selectedService;
	}
	
	public boolean isMnadatory() {
		return isMandatory.isSelected();
	}
	
	public void setMandatory(boolean isMandatory) {
		this.isMandatory.setSelected(isMandatory);
	}
	
	public void setSelectedService(int idx) {
		if(idx >= 0) {
			services.setSelectedIndex(idx);
		}
	}
	
	private ComboVisualizer getVisualizer() {
		return this;
	}
}
