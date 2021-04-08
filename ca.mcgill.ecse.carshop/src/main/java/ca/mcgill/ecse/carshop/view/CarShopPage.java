package ca.mcgill.ecse.carshop.view;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse223.carshop.controller.AppointmentController;
import ca.mcgill.ecse223.carshop.controller.TOAppointment;
import ca.mcgill.ecse223.carshop.controller.TOBookableService;
import ca.mcgill.ecse223.carshop.controller.TOComboItem;
import ca.mcgill.ecse223.carshop.controller.TOService;
import ca.mcgill.ecse223.carshop.controller.TOServiceBooking;
import ca.mcgill.ecse223.carshop.controller.TOServiceCombo;
import ca.mcgill.ecse223.carshop.controller.TOTimeSlot;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CarShopPage extends JFrame{

	private static final long serialVersionUID = -4426310869335015542L;
	
	// UI elements
	private JLabel errorMessage;
	// driver
	private JTextField driverNameTextField;
	private JLabel driverNameLabel;
	private JButton addDriverButton;
	private JComboBox<String> driverToggleList;
	private JLabel driverToggleLabel;
	private JButton sickButton;
	private JButton deleteDriverButton;
	// route
	private JTextField routeNumberTextField;
	private JLabel routeNumberLabel;
	private JButton addRouteButton;
	// bus
	private JTextField busLicencePlateTextField;
	private JLabel busLicencePlateLabel;
	private JButton addBusButton;
	private JComboBox<String> busToggleList;
	private JLabel busToggleLabel;
	private JButton repairButton;
	// bus assignment
	private JComboBox<String> busList;
	private JLabel busLabel;
	private JComboBox<String> routeList;
	private JLabel routeLabel;
	private JDatePickerImpl assignmentDatePicker;
	
	private JButton assignButton;

	
	//Cancel appt
	private JLabel cancelAppt;
	private JComboBox<String> apptList;
	private JLabel apptLabel;
	private JButton cancelApptButton;
	
	// Make Appt
	private JLabel makeApptLabel;
	private JComboBox<String> bookableServiceList;
	private JLabel bookableServiceLabel;
	private JButton makeApptButton;
	
	private JPanel optServicePanel;
	
	private HashMap<Integer, TOAppointment> appts;
	private HashMap<Integer, TOBookableService> bookableServices;
	private List<OptServiceVisualizer> optServices;
	
	private JDatePanelImpl datePanel;
	private JDatePickerImpl apptDatePicker;
	private JLabel apptDateLabel;
	
	
	
	
	// data elements
	private String error = null;
	// toggle sick status
	// private HashMap<Integer, Integer> drivers;
	// toggle repairs status
	// private HashMap<Integer, String> buses;
	// bus assignment
	// private HashMap<Integer, String> availableBuses;
	// private HashMap<Integer, TORoute> routes;

	/** Creates new form BtmsPage */
	public CarShopPage() {
		initComponents();
		refreshData();
	}

	/** This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		// Cancel Appt
		cancelAppt = new JLabel();
		cancelAppt.setText("Update/Cancel Appointment");
		Font font = cancelAppt.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		cancelAppt.setFont(font.deriveFont(attributes));
		apptLabel = new JLabel();
		apptLabel.setText("Appointment:");
		cancelApptButton = new JButton();
		cancelApptButton.setText("Cancel");
		apptList = new JComboBox<String>(new String[0]);
		
		// Make appt
		makeApptLabel = new JLabel();
		makeApptLabel.setText("Make Appointment");
		makeApptLabel.setFont(font.deriveFont(attributes));
		bookableServiceLabel = new JLabel();
		bookableServiceLabel.setText("Bookable Service:");
		bookableServiceList = new JComboBox<String>(new String[0]);
		optServicePanel = new JPanel();
		optServicePanel.setLayout(new BoxLayout(optServicePanel, BoxLayout.PAGE_AXIS));
		makeApptButton = new JButton();
		makeApptButton.setText("Make appointment");
		
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		apptDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		apptDateLabel = new JLabel();
		apptDateLabel.setText("Date:");
		
		
		// listeners for apptList
		cancelApptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelApptButtonActionPerformed(evt);
			}
		});
		
		makeApptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				makeApptButtonActionPerformed(evt);
			}
		});
		
		bookableServiceList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				showOptionalServices(evt);
			}
		});
	
		
		// global settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Car Shop");
		
		// horizontal line elements
		JSeparator horizontalLineTop = new JSeparator();
		JSeparator horizontalLineBottom = new JSeparator();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addComponent(horizontalLineTop)
				.addComponent(horizontalLineBottom)
				/*.addComponent(horizontalLineMiddle1)
				.addComponent(horizontalLineMiddle2)
				.addComponent(horizontalLineBottom)*/
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(bookableServiceLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(makeApptLabel)
								.addComponent(bookableServiceList)
								.addComponent(optServicePanel)
								.addComponent(apptDatePicker)
								.addComponent(makeApptButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(apptLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(cancelAppt)
								.addComponent(apptList)
								.addComponent(cancelApptButton)))
				);
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelAppt});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, apptDatePicker});

		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
						.addComponent(makeApptLabel)
						.addComponent(cancelAppt))
				.addGroup(layout.createParallelGroup()
						.addComponent(bookableServiceLabel)
						.addComponent(bookableServiceList)
						.addComponent(apptLabel)
						.addComponent(apptList))
				.addGroup(layout.createParallelGroup()
						.addComponent(optServicePanel)
						.addComponent(cancelApptButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(apptDatePicker))
				.addGroup(layout.createParallelGroup()
						.addComponent(makeApptButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				);
		
		pack();
	}

	private void refreshData() {
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			
			appts = new HashMap<Integer, TOAppointment>();
			apptList.removeAllItems();
			Integer idx = 0;
			for(TOAppointment a: AppointmentController.getAppointments()) {
				Date sd = a.getToServiceBooking(0).getToTimeSlot().getStartDate();
				Time st = a.getToServiceBooking(0).getToTimeSlot().getStartTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
				String appTime = sdf2.format(new Date(st.getTime()));
				String apptDate = sdf.format(sd)+"+"+appTime;
				appts.put(idx, a);
				apptList.addItem(a.getMainServiceName() + " " + apptDate);
				idx++;
			}
			apptList.setSelectedIndex(-1);
			
			
			bookableServices = new HashMap<Integer, TOBookableService>();
			bookableServiceList.removeAllItems();
			idx = 0;
			for(TOBookableService toBs: AppointmentController.getBookableServices()) {
				bookableServices.put(idx, toBs);
				bookableServiceList.addItem(toBs.getName());
				idx++;
			}
			bookableServiceList.setSelectedIndex(-1);
			
			apptDatePicker.getModel().setValue(null);
		}

		// this is needed because the size of the window changes depending on whether an error message is shown or not
		pack();
	}
	
	private void cancelApptButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		int selectedAppt = apptList.getSelectedIndex();
		if(selectedAppt < 0) {
			error = "Appointment needs to be selected to cancel";
		}
		error = error.trim();
		
		if (error.length() == 0) {
			// call the controller
			try {
				TOAppointment app = appts.get(selectedAppt);
				AppointmentController.cancelAppointment(CarShopApplication.getLoggedInUser(), app.getMainServiceName(), app.getToServiceBooking(0).getToTimeSlot().getStartDate(), app.getToServiceBooking(0).getToTimeSlot().getStartTime());
			} catch (Exception e) {
				error = e.getMessage();
			}
		}

		// update visuals
		refreshData();	
	}
	
	private void makeApptButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		int selectedBs = bookableServiceList.getSelectedIndex();
		if(optServicePanel.getComponentCount() == 0) {
			error = "A service needs to be selected to make an appointment";
		}
		error = error.trim();
		
		if (error.length() == 0) {
			// call the controller
			try {
				TOBookableService toBs;
				if(optServices.size() > 1) {
					toBs = AppointmentController.findServiceCombo(optServices);
				} else {
					toBs =  optServices.get(0).getTOService();
				}
				//TOBookableService toBs =  optServices.get(0).getTOService();
				List<TOService> services = new ArrayList<TOService>();
				List<TOTimeSlot> timeSlots = new ArrayList<TOTimeSlot>();
				for(OptServiceVisualizer opt: optServices) {
					if(opt.getIsSelected()) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Time st = new Time(AppointmentController.parseDate(opt.getStartTime(), "HH:mm").getTime());
						Time et = AppointmentController.incrementTimeByMinutes(st, opt.getTOService().getDuration());
						// Double check that this gives the expected values
						Date sd = new Date(apptDatePicker.getModel().getYear() - 1900, apptDatePicker.getModel().getMonth(), apptDatePicker.getModel().getDay());
						timeSlots.add(new TOTimeSlot(sd, st, sd, et));
						services.add(opt.getTOService());
					}
				}

				AppointmentController.makeAppointmentFromView(false, CarShopApplication.getLoggedInUser(), toBs, services, timeSlots);
			} catch (Exception e) {
				error = e.getMessage();
			}
		}

		// update visuals
		refreshData();	
	}
	
	private void showOptionalServices(java.awt.event.ActionEvent evt) {
		int selectedBs = bookableServiceList.getSelectedIndex();
		try {
			optServicePanel.removeAll();
			optServices = new ArrayList<OptServiceVisualizer>();
		
		
			if(selectedBs >= 0) {
				
					TOBookableService toBs =  bookableServices.get(selectedBs);
					if(toBs instanceof TOServiceCombo) {
						TOServiceCombo toSc = (TOServiceCombo) toBs;
						for(TOComboItem toCi: toSc.getServices()) {
							OptServiceVisualizer visualizer = new OptServiceVisualizer(toCi);
							optServicePanel.add(visualizer); 
							optServices.add(visualizer);
						}
					} else {
						TOService toS = (TOService) toBs;
						OptServiceVisualizer visualizer = new OptServiceVisualizer(toS);
						optServicePanel.add(visualizer); 
						optServices.add(visualizer);
					}
			}
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		// update visuals
		refreshData();	
	}

}
