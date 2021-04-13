package ca.mcgill.ecse.carshop.view;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse223.carshop.controller.AppointmentController;
import ca.mcgill.ecse223.carshop.controller.CarShopController;
import ca.mcgill.ecse223.carshop.controller.TOAppointment;
import ca.mcgill.ecse223.carshop.controller.TOBookableService;
import ca.mcgill.ecse223.carshop.controller.TOComboItem;
import ca.mcgill.ecse223.carshop.controller.TOService;
import ca.mcgill.ecse223.carshop.controller.TOServiceBooking;
import ca.mcgill.ecse223.carshop.controller.TOServiceCombo;
import ca.mcgill.ecse223.carshop.controller.TOTimeSlot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
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
	
	// Header
	private JLabel headerTitle;
	private JButton logoutButton;
	
	// Login - Sign up
	private JLabel login;
	private JLabel signup;
	private JLabel orLabel;
	private JLabel loginErrorMessage;
	private JLabel signupErrorMessage;
	private JLabel loginUsername;
	private JLabel loginPassword;
	private JLabel signupUsername;
	private JLabel signupPassword;
	private JTextField loginUsernameField;
	private JTextField loginPasswordField;
	private JTextField signupUsernameField;
	private JTextField signupPasswordField;
	private JButton loginButton;
	private JButton signupButton;
	private JSeparator loginTopSeparator;
	
	// Update Account
	private JLabel updateAccount;
	private JLabel updateAccountErrorMessage;
	private JLabel updateAccountSuccessMessage;
	private JLabel updateUsername;
	private JLabel updatePassword;
	private JTextField updateUsernameField;
	private JTextField updatePasswordField;
	private JButton updateAccountButton;
	private JSeparator updateAccountTopSeparator;
	
	// Update Garage Hours
	private JLabel addNewLabel;
	private JLabel removeHoursLabel;
	private JLabel newHoursDayLabel;
	private JLabel newHoursOpenLabel;
	private JLabel newHoursCloseLabel;
	private JLabel removeHoursDayLabel;
	private JLabel newGarageHoursErrorMessage;
	private JLabel removeGarageHoursErrorMessage;
	private JLabel newGarageHoursSuccessMessage;
	private JLabel removeGarageHoursSuccessMessage;
	private JLabel timeSeparator1;
	private JLabel timeSeparator2;
	private JComboBox<String> newHoursDayBox;
	private JComboBox<String> removeHoursDayBox;
	private JComboBox<String> newHoursOpenHBox;
	private JComboBox<String> newHoursOpenMBox;
	private JComboBox<String> newHoursCloseHBox;
	private JComboBox<String> newHoursCloseMBox;
	private JButton addGarageHoursButton;
	private JButton removeGarageHoursButton;
	private JSeparator garageOpeningHoursTopSeparator;
	
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
	
	//update appt
	private JButton updateApptButton;
	private JDatePanelImpl datePanelUpdate;
	private JDatePickerImpl apptDatePickerUpdate;
	private JPanel optServicePanelUpdate;
	private List<OptServiceVisualizer> optServicesUpdate;
	private JSeparator horizontalLineTop;
	private JSeparator horizontalLineBottom;
	
	
	
	
	// data elements
	private String error = null;
	private String loginError = null;
	private String signupError = null;
	private String updateAccountError = null;
	private String updateAccountSuccess = null;
	private String newGarageHoursError = null;
	private String newGarageHoursSuccess = null;
	private String removeGarageHoursError = null;
	private String removeGarageHoursSuccess = null;
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
		
		// header
		headerTitle = new JLabel();
		headerTitle.setText("CarShop");
		Font titleFont = headerTitle.getFont();
		Map<TextAttribute, Object> titleAttributes = new HashMap<>(titleFont.getAttributes());
		titleAttributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
		titleAttributes.put(TextAttribute.SIZE, 24);
		headerTitle.setFont(titleFont.deriveFont(titleAttributes));
		logoutButton = new JButton();
		logoutButton.setText("Log out");
		logoutButton.setVisible(false);
		
		// login
		login = new JLabel();
		login.setText("Login");
		orLabel = new JLabel();
		orLabel.setText("OR");
		loginErrorMessage = new JLabel();
		loginErrorMessage.setForeground(Color.RED);
		Font underlinedFont = login.getFont();
		Map<TextAttribute, Object> underlinedAttributes = new HashMap<>(underlinedFont.getAttributes());
		underlinedAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		underlinedAttributes.put(TextAttribute.SIZE, 14);
		login.setFont(underlinedFont.deriveFont(underlinedAttributes));
		loginUsername = new JLabel();
		loginUsername.setText("Username: ");
		loginPassword = new JLabel();
		loginPassword.setText("Password: ");
		loginUsernameField = new JTextField(15);
		loginPasswordField = new JTextField(15);
		loginButton = new JButton();
		loginButton.setText("Login");
		loginTopSeparator = new JSeparator();
		
		// sign up
		signup = new JLabel();
		signup.setText("Sign Up");
		signup.setFont(underlinedFont.deriveFont(underlinedAttributes));
		signupErrorMessage = new JLabel();
		signupErrorMessage.setForeground(Color.RED);
		signupUsername = new JLabel();
		signupUsername.setText("Username: ");
		signupPassword = new JLabel();
		signupPassword.setText("Password: ");
		signupUsernameField = new JTextField(15);
		signupPasswordField = new JTextField(15);
		signupButton = new JButton();
		signupButton.setText("Sign Up");
		
		// Update Account
		updateAccount = new JLabel();
		updateAccount.setText("Update Account");
		updateAccount.setFont(underlinedFont.deriveFont(underlinedAttributes));
		updateAccountErrorMessage = new JLabel();
		updateAccountErrorMessage.setForeground(Color.RED);
		updateAccountSuccessMessage = new JLabel();
		updateAccountSuccessMessage.setForeground(new Color(0, 153, 0));
		updateUsername = new JLabel();
		updateUsername.setText("New username: ");
		updatePassword = new JLabel();
		updatePassword.setText("New password: ");
		updateUsernameField = new JTextField(15);
		updatePasswordField = new JTextField(15);
		updateAccountButton = new JButton();
		updateAccountButton.setText("Confirm changes");
		updateAccountTopSeparator = new JSeparator();
		hideUpdateAccountSection();
		
		// Update Garage Hours
		initializeGarageHoursComponent();
		hideUpdateGarageSection();
		
		// Cancel Appt
		cancelAppt = new JLabel();
		cancelAppt.setText("Update/Cancel Appointment");
//		Font font = cancelAppt.getFont();
//		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
//		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		cancelAppt.setFont(underlinedFont.deriveFont(underlinedAttributes));
		apptLabel = new JLabel();
		apptLabel.setText("Appointment:");
		cancelApptButton = new JButton();
		cancelApptButton.setText("Cancel");
		apptList = new JComboBox<String>(new String[0]);
		
		// Make appt
		makeApptLabel = new JLabel();
		makeApptLabel.setText("Make Appointment");
		makeApptLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
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
		datePanel = new JDatePanelImpl(model, p);
		apptDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		apptDateLabel = new JLabel();
		apptDateLabel.setText("Date:");
		
		optServicePanelUpdate = new JPanel();
		optServicePanelUpdate.setLayout(new BoxLayout(optServicePanelUpdate, BoxLayout.PAGE_AXIS));
		SqlDateModel model2 = new SqlDateModel();
		Properties p2 = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanelUpdate = new JDatePanelImpl(model2, p2);
		apptDatePickerUpdate = new JDatePickerImpl(datePanelUpdate, new DateLabelFormatter());
		updateApptButton = new JButton();
		updateApptButton.setText("Update");
		horizontalLineTop = new JSeparator();
		horizontalLineBottom = new JSeparator();
		
		hideAppointmentSection();
		
		// Action Listeners
		// Listeners for Login - Sign up
		loginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginButtonActionPerformed(evt);
			}
		});
		
		logoutButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logoutButtonActionPerformed(evt);
			}
		});
		
		signupButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				signupButtonActionPerformed(evt);
			}
		});
		
		updateAccountButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateAccountButtonActionPerformed(evt);
			}
		});
		
		newHoursDayBox.addActionListener (new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		    	newGarageHoursError = "";
				newGarageHoursSuccess = "";
				newGarageHoursErrorMessage.setText(newGarageHoursError);
				newGarageHoursSuccessMessage.setText(newGarageHoursSuccess);
				refreshData();
		    }
		});
		
		removeHoursDayBox.addActionListener (new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		    	removeGarageHoursError = "";
		    	removeGarageHoursSuccess = "";
		    	removeGarageHoursErrorMessage.setText(removeGarageHoursError);
		    	removeGarageHoursSuccessMessage.setText(removeGarageHoursSuccess);
				refreshData();
		    }
		});
		
		addGarageHoursButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addGarageHoursButtonActionPerformed(evt);
			}
		});
		
		removeGarageHoursButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateGarageHoursButtonActionPerformed(evt);
			}
		});
		
		// listeners for apptList
		cancelApptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelApptButtonActionPerformed(evt);
			}
		});
		
		updateApptButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateApptButtonActionPerformed(evt);
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
		
		apptList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				showOptionalServicesForUpdate(evt);
			}
		});
	
		
		// global settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Car Shop");
		

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		// Horizontal Layout
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				// Header Section
				.addGroup(layout.createSequentialGroup()
						.addComponent(headerTitle)
						.addGap(50)
						.addComponent(logoutButton)
						)
				// Login - Setup Section
				.addComponent(loginTopSeparator)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(login)
								.addComponent(loginUsername)
								.addComponent(loginPassword)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(loginErrorMessage)
								.addComponent(loginUsernameField)
								.addComponent(loginPasswordField)
								.addComponent(loginButton)
								)
						.addGap(50)
						.addComponent(orLabel)
						.addGap(50)
						.addGroup(layout.createParallelGroup()
								.addComponent(signup)
								.addComponent(signupUsername)
								.addComponent(signupPassword)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(signupErrorMessage)
								.addComponent(signupUsernameField)
								.addComponent(signupPasswordField)
								.addComponent(signupButton)
								)
						)
				// Update Account Section
				.addComponent(updateAccountTopSeparator)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(updateAccount)
								.addComponent(updateUsername)
								.addComponent(updatePassword)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(updateAccountErrorMessage)
								.addComponent(updateUsernameField)
								.addComponent(updatePasswordField)
								.addComponent(updateAccountButton)
								)
						.addComponent(updateAccountSuccessMessage)
						)
				// Update Garage Hours Section
				.addComponent(garageOpeningHoursTopSeparator)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(addNewLabel)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup()
												.addComponent(newHoursDayLabel)
												.addComponent(newHoursOpenLabel)
												.addComponent(newHoursCloseLabel)
												)
										.addGroup(layout.createParallelGroup()
												.addComponent(newHoursDayBox)
												.addGroup(layout.createSequentialGroup()
														.addComponent(newHoursOpenHBox)
														.addComponent(timeSeparator1)
														.addComponent(newHoursOpenMBox)
														)
												.addGroup(layout.createSequentialGroup()
														.addComponent(newHoursCloseHBox)
														.addComponent(timeSeparator2)
														.addComponent(newHoursCloseMBox)
														)
												.addComponent(addGarageHoursButton)
												.addComponent(newGarageHoursSuccessMessage)
												.addComponent(newGarageHoursErrorMessage)
												)
										)
								)
						.addGap(150)
						.addGroup(layout.createParallelGroup()
								.addComponent(removeHoursLabel)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup()
												.addComponent(removeHoursDayLabel)
												)
										.addGroup(layout.createParallelGroup()
												.addComponent(removeHoursDayBox)
												.addComponent(removeGarageHoursButton)
												.addComponent(removeGarageHoursErrorMessage)
												.addComponent(removeGarageHoursSuccessMessage)
												)
										)
								)
						)
				
				// Make-Update-Cancel Appointment Section
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
								.addComponent(optServicePanelUpdate)
								.addComponent(apptDatePickerUpdate)
								.addComponent(updateApptButton)
								.addComponent(cancelApptButton)))
				);
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {loginUsernameField, loginPasswordField, loginButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {signupUsernameField, signupPasswordField, signupButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updateUsernameField, updatePasswordField, updateAccountButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {newHoursDayBox, addGarageHoursButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {removeHoursDayBox, removeGarageHoursButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelAppt});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, updateApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, apptDatePickerUpdate});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, apptDatePicker});

		// Vertical Layout
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				// Header
				.addGroup(layout.createParallelGroup()
						.addComponent(headerTitle)
						.addComponent(logoutButton)
						)
				.addGap(20)
				// Login - Sign up Section
				.addGroup(layout.createParallelGroup()
						.addComponent(loginTopSeparator))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(login)
								.addComponent(loginErrorMessage)
								.addComponent(signup)
								.addComponent(signupErrorMessage)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(loginUsername)
								.addComponent(loginUsernameField)
								.addComponent(signupUsername)
								.addComponent(signupUsernameField)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(loginPassword)
								.addComponent(loginPasswordField)
								.addComponent(orLabel)
								
								.addComponent(signupPassword)
								.addComponent(signupPasswordField)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(loginButton)
								.addComponent(signupButton)
								)
						)
				// Update Account Section
				.addGroup(layout.createParallelGroup()
						.addComponent(updateAccountTopSeparator))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(updateAccount)
								.addComponent(updateAccountErrorMessage)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(updateUsername)
								.addComponent(updateUsernameField)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(updatePassword)
								.addComponent(updatePasswordField)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(updateAccountButton)
								.addComponent(updateAccountSuccessMessage)
								)
						)
				// Update Garage Hours Section
				.addComponent(garageOpeningHoursTopSeparator)
				.addGroup(layout.createParallelGroup()
						.addComponent(addNewLabel)
						.addComponent(removeHoursLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(newHoursDayLabel)
						.addComponent(newHoursDayBox)
						.addComponent(removeHoursDayLabel)
						.addComponent(removeHoursDayBox)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(newHoursOpenLabel)
						.addComponent(newHoursOpenHBox)
						.addComponent(timeSeparator1)
						.addComponent(newHoursOpenMBox)
						.addComponent(removeGarageHoursButton)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(newHoursCloseLabel)
						.addComponent(newHoursCloseHBox)
						.addComponent(timeSeparator2)
						.addComponent(newHoursCloseMBox)
						.addComponent(removeGarageHoursErrorMessage)
						.addComponent(removeGarageHoursSuccessMessage)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(addGarageHoursButton)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(newGarageHoursSuccessMessage)
						.addComponent(newGarageHoursErrorMessage)
						)
				
				// Make-Update-Cancel Appointment Section
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addComponent(errorMessage)
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
						.addComponent(optServicePanelUpdate))
				.addGroup(layout.createParallelGroup()
						.addComponent(apptDatePicker)
						.addComponent(apptDatePickerUpdate))
				.addGroup(layout.createParallelGroup()
						.addComponent(updateApptButton)
						.addComponent(makeApptButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(cancelApptButton))
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
			apptDatePickerUpdate.getModel().setValue(null);
		}

		// this is needed because the size of the window changes depending on whether an error message is shown or not
		pack();
	}
	
	private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
		loginError = "";
		if (loginUsernameField.getText().isBlank() || loginPasswordField.getText().isBlank()) {
			loginError = "Username and password must not be empty.";
		}
		// Check for other errors here //
		if (loginError.length() == 0) {
			try {
				if (CarShopController.logIn(loginUsernameField.getText(), loginPasswordField.getText())) {
					hideLoginSection();
					headerTitle.setText("Hi, " + CarShopController.getLoggedInUser() + "!");
					logoutButton.setVisible(true);
					if(CarShopController.isCustomerLoggedIn()) {
						showUpdateAccountSection();
						showAppointmentSection();
					} else if (CarShopController.isTechnicianLoggedIn()) {
						showUpdateGarageSection();
					}
				}
			} catch (Exception e) {
				loginError = e.getMessage();
			}
		}
		loginErrorMessage.setText(loginError);
		refreshData();
	}
	
	private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if (CarShopController.logout()) {
				showLoginSection();
				hideUpdateAccountSection();
				hideUpdateGarageSection();
				hideAppointmentSection();
				headerTitle.setText("CarShop");
				logoutButton.setVisible(false);
				loginUsernameField.setText("");
				loginPasswordField.setText("");
				updateUsernameField.setText("");
				updatePasswordField.setText("");
				updateAccountErrorMessage.setText("");
				updateAccountSuccessMessage.setText("");
			}
		} catch (Exception e) {
			loginError = e.getMessage();
		}
		refreshData();
	}
	
	private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		signupError = "";
		if (signupUsernameField.getText().isBlank() || signupPasswordField.getText().isBlank()) {
			signupError = "Username and password must not be empty.";
		}
		// Check for other errors here //
		if (signupError.length() == 0) {
			try {
				if (CarShopController.createCustomer(signupUsernameField.getText(), signupPasswordField.getText())) {
					loginUsernameField.setText(signupUsernameField.getText());
					loginPasswordField.setText(signupPasswordField.getText());
					signupUsernameField.setText("");
					signupPasswordField.setText("");
					
				}
			} catch (Exception e) {
				signupError = e.getMessage();
			}
		}
		signupErrorMessage.setText(signupError);
		refreshData();
	}
	
	private void updateAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {
		updateAccountError = "";
		updateAccountSuccess = "";
		if (updateUsernameField.getText().isBlank() || updatePasswordField.getText().isBlank()) {
			updateAccountError = "Username and password must not be empty.";
		}
		// Check for other errors here //
		if (updateAccountError.length() == 0) {
			try {
				if (CarShopController.updateCustomer(updateUsernameField.getText(), updatePasswordField.getText())) {
					updateAccountSuccess = "Account successfully updated!";
					updateUsernameField.setText("");
					updatePasswordField.setText("");
				}
			} catch (Exception e) {
				updateAccountError = e.getMessage();
			}
		}
		updateAccountErrorMessage.setText(updateAccountError);
		updateAccountSuccessMessage.setText(updateAccountSuccess);
		refreshData();
	}
	
	private void addGarageHoursButtonActionPerformed(java.awt.event.ActionEvent evt) {
		newGarageHoursError = "";
		newGarageHoursSuccess = "";
		// Check for other errors here //
		if (newGarageHoursError.length() == 0) {
			try {
				String day = (String)newHoursDayBox.getSelectedItem();
				String inputStartTime = newHoursOpenHBox.getSelectedItem() + ":" + newHoursOpenMBox.getSelectedItem();
				String inputEndTime = newHoursCloseHBox.getSelectedItem() + ":" + newHoursCloseMBox.getSelectedItem();
				String type = "";
				User user = User.getWithUsername(CarShopApplication.getLoggedInUser());
				if (user instanceof Technician) {
					type = String.valueOf(((Technician) user).getType());
				}
				if (CarShopController.addHoursToGarageOfTechnicianType(day, inputStartTime, inputEndTime, type)) {
					newGarageHoursSuccess = "Business hours successfully added!";
				}
			} catch (Exception e) {
				newGarageHoursError = e.getMessage();
			}
		}
		newGarageHoursErrorMessage.setText(newGarageHoursError);
		newGarageHoursSuccessMessage.setText(newGarageHoursSuccess);
		refreshData();
	}
	
	private void updateGarageHoursButtonActionPerformed(java.awt.event.ActionEvent evt) {
		removeGarageHoursError = "";
		removeGarageHoursSuccess = "";
		// Check for other errors here //
		if (removeGarageHoursError.length() == 0) {
			try {
				String day = (String)newHoursDayBox.getSelectedItem();
				String type = "";
				ArrayList<BusinessHour> bhOnThatDay = new ArrayList<BusinessHour>();
				User user = User.getWithUsername(CarShopApplication.getLoggedInUser());
				if (user instanceof Technician) {
					type = String.valueOf(((Technician) user).getType());
					for (BusinessHour bh : ((Technician) user).getGarage().getBusinessHours()) {
						if (bh.getDayOfWeek() == DayOfWeek.valueOf(day)) {
							bhOnThatDay.add(bh);
						}
					}
					if (bhOnThatDay.size() == 0) {
						removeGarageHoursError = "No existing hours on that day.";
					}
					else {
						for (BusinessHour bh : bhOnThatDay) {
							DateFormat dateFormat = new SimpleDateFormat("HH:mm");
							String start = dateFormat.format(bh.getStartTime());
							String end = dateFormat.format(bh.getEndTime());
							if (CarShopController.removeHoursToGarageOfTechnicianType(day, start, end, type)) {
								removeGarageHoursSuccess = "Business hours removed successfully!";
								System.out.println(removeGarageHoursSuccess);
							}
						}
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
				removeGarageHoursError = e.getMessage();
			}
		}
		removeGarageHoursErrorMessage.setText(removeGarageHoursError);
		removeGarageHoursSuccessMessage.setText(removeGarageHoursSuccess);
		refreshData();
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
						Time st = new Time(AppointmentController.parseDate(opt.getStartTime(), "HH:mm").getTime());
						Time et = AppointmentController.incrementTimeByMinutes(st, opt.getTOService().getDuration());
						// Double check that this gives the expected values
						Date sd = new Date(apptDatePicker.getModel().getYear() - 1900, apptDatePicker.getModel().getMonth(), apptDatePicker.getModel().getDay());
						timeSlots.add(new TOTimeSlot(sd, st, sd, et));
						services.add(opt.getTOService());
					}
				}

				AppointmentController.makeAppointmentFromView(false, CarShopApplication.getLoggedInUser(), toBs, services, timeSlots, new ArrayList<TOTimeSlot>());
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
		//refreshData();	
		pack();
	}
	
	private void showOptionalServicesForUpdate(java.awt.event.ActionEvent evt) {
		int selectedApp = apptList.getSelectedIndex();
		try {
			optServicePanelUpdate.removeAll();
			optServicesUpdate = new ArrayList<OptServiceVisualizer>();
		
		
			if(selectedApp >= 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH");
				SimpleDateFormat sdf2 = new SimpleDateFormat("mm");
				
				Date sd = appts.get(selectedApp).getToServiceBooking(0).getToTimeSlot().getStartDate();
				int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(sd));
				int month = Integer.parseInt(new SimpleDateFormat("MM").format(sd)) - 1;
				int day = Integer.parseInt(new SimpleDateFormat("dd").format(sd));
				apptDatePickerUpdate.getModel().setDate(year, month, day);
				apptDatePickerUpdate.getModel().setSelected(true);
				
				
				TOBookableService toBs =  appts.get(selectedApp).getToBookableService();
				if(toBs instanceof TOServiceCombo) {
					TOServiceCombo toSc = (TOServiceCombo) toBs;
					List<String> selectedServiceNames = AppointmentController.getSelectedServiceNames(appts.get(selectedApp));
					int idx = 0;
					for(int i = 0 ; i < toSc.getServices().size();i++) {
						OptServiceVisualizer visualizer = new OptServiceVisualizer(toSc.getService(i));
						if(selectedServiceNames.contains(visualizer.getTOService().getName())) {
							visualizer.setIsSelected(true);
							TOTimeSlot toTimeSlots = appts.get(selectedApp).getToServiceBooking(idx).getToTimeSlot();
							
							Time st = toTimeSlots.getStartTime();
							
							int startHour = Integer.parseInt(sdf.format(st));
							int startMin = Integer.parseInt(sdf2.format(st));
							visualizer.setStartTime(startHour, startMin);
							
							idx++;
						}
						
							
						optServicePanelUpdate.add(visualizer); 
						optServicesUpdate.add(visualizer);
					}
				} else {
					TOService toS = (TOService) toBs;
					OptServiceVisualizer visualizer = new OptServiceVisualizer(toS);
					
					Time st = appts.get(selectedApp).getToServiceBooking(0).getToTimeSlot().getStartTime();
					
					int startHour = Integer.parseInt(sdf.format(st));
					int startMin = Integer.parseInt(sdf2.format(st));
					visualizer.setStartTime(startHour, startMin);
					
					optServicePanelUpdate.add(visualizer); 
					optServicesUpdate.add(visualizer);
				}
			}
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		// update visuals
		//refreshData();
		pack();
	}
	
	private void updateApptButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// take services in optServicesUpdate
		// easiest is probably to delete the existing appointment, try creating a new one and if it fails set it back to the previous one
		error = "";
		int selectedAppt = apptList.getSelectedIndex();
		if(optServicePanelUpdate.getComponentCount() == 0) {
			error = "An appointment needs to be selected to update an appointment";
		}
		error = error.trim();
		
		if (error.length() == 0) {
			// call the controller
			try {
				TOBookableService toBs;
				if(optServicesUpdate.size() > 1) {
					toBs = AppointmentController.findServiceCombo(optServicesUpdate);
				} else {
					toBs =  optServicesUpdate.get(0).getTOService();
				}
				//TOBookableService toBs =  optServices.get(0).getTOService();
				List<TOService> services = new ArrayList<TOService>();
				List<TOTimeSlot> timeSlots = new ArrayList<TOTimeSlot>();
				for(OptServiceVisualizer opt: optServicesUpdate) {
					if(opt.getIsSelected()) {
						String tst = opt.getStartTime();
						Time st = new Time(AppointmentController.parseDate(opt.getStartTime(), "HH:mm").getTime());
						Time et = AppointmentController.incrementTimeByMinutes(st, opt.getTOService().getDuration());
						// Double check that this gives the expected values
						Date sd = new Date(apptDatePickerUpdate.getModel().getYear() - 1900, apptDatePickerUpdate.getModel().getMonth(), apptDatePickerUpdate.getModel().getDay());
						timeSlots.add(new TOTimeSlot(sd, st, sd, et));
						services.add(opt.getTOService());
					}
				}
				List<TOTimeSlot> toExclude = new ArrayList<TOTimeSlot>();
				for(TOServiceBooking toServiceBooking: appts.get(selectedAppt).getToServiceBookings()) {
					toExclude.add(toServiceBooking.getToTimeSlot());
				}
				
				AppointmentController.makeAppointmentFromView(false, CarShopApplication.getLoggedInUser(), toBs, services, timeSlots, toExclude);
				TOAppointment app = appts.get(selectedAppt);
				AppointmentController.deleteAppt(CarShopApplication.getLoggedInUser(), app.getMainServiceName(), app.getToServiceBooking(0).getToTimeSlot().getStartDate(), app.getToServiceBooking(0).getToTimeSlot().getStartTime());
			
			} catch (Exception e) {
				error = e.getMessage();
			}
		}

		// update visuals
		refreshData();	
	}
	
	@SuppressWarnings("rawtypes")
	private void initializeGarageHoursComponent() {
		addNewLabel = new JLabel();
		addNewLabel.setText("Add Garage Hours");
		Font underlinedFont = addNewLabel.getFont();
		Map<TextAttribute, Object> underlinedAttributes = new HashMap<>(underlinedFont.getAttributes());
		underlinedAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		underlinedAttributes.put(TextAttribute.SIZE, 14);
		addNewLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
		removeHoursLabel = new JLabel();
		removeHoursLabel.setText("Remove Garage Hours");
		removeHoursLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
		newHoursDayLabel = new JLabel();
		newHoursDayLabel.setText("Day");
		newHoursOpenLabel = new JLabel();
		newHoursOpenLabel.setText("Open");
		newHoursCloseLabel = new JLabel();
		newHoursCloseLabel.setText("Close");
		removeHoursDayLabel = new JLabel();
		removeHoursDayLabel.setText("Day");
		newGarageHoursErrorMessage = new JLabel();
		newGarageHoursErrorMessage.setForeground(Color.RED);
		removeGarageHoursErrorMessage = new JLabel();
		removeGarageHoursErrorMessage.setForeground(Color.RED);
		newGarageHoursSuccessMessage = new JLabel();
		newGarageHoursSuccessMessage.setForeground(new Color(0, 153, 0));
		removeGarageHoursSuccessMessage = new JLabel();
		removeGarageHoursSuccessMessage.setForeground(new Color(0, 153, 0));
		timeSeparator1 = new JLabel();
		timeSeparator1.setText(":");
		timeSeparator2 = new JLabel();
		timeSeparator2.setText(":");
		Dimension bigBox = new Dimension(150, 20);
		Dimension smallBox = new Dimension(20, 20);
		newHoursDayBox = new JComboBox<String>(new String[0]);
		newHoursDayBox.setSize(bigBox);
		newHoursDayBox.setMaximumSize(bigBox);
		newHoursDayBox.setPreferredSize(bigBox);
		removeHoursDayBox = new JComboBox<String>(new String[0]);
		removeHoursDayBox.setSize(bigBox);
		removeHoursDayBox.setMaximumSize(bigBox);
		removeHoursDayBox.setPreferredSize(bigBox);
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		for (String day : days) {
			newHoursDayBox.addItem(day);
			removeHoursDayBox.addItem(day);
		}
		newHoursOpenHBox = new JComboBox<String>(new String[0]);
		newHoursOpenMBox = new JComboBox<String>(new String[0]);
		newHoursCloseHBox = new JComboBox<String>(new String[0]);
		newHoursCloseMBox = new JComboBox<String>(new String[0]);
		JComboBox[] smallBoxes = {newHoursOpenHBox, newHoursOpenMBox, newHoursCloseHBox, newHoursCloseMBox};
		for (JComboBox box : smallBoxes) {
			box.setSize(smallBox);
			box.setMaximumSize(smallBox);
			box.setPreferredSize(smallBox);
		}
		for(int i = 0; i< 24; i++) {
			newHoursOpenHBox.addItem(String.valueOf(i));
			newHoursCloseHBox.addItem(String.valueOf(i));
		}
		for(int i = 0; i< 60; i++) {
			newHoursOpenMBox.addItem(String.valueOf(i));
			newHoursCloseMBox.addItem(String.valueOf(i));
		}
		addGarageHoursButton = new JButton();
		addGarageHoursButton.setText("Add business hours");
		removeGarageHoursButton = new JButton();
		removeGarageHoursButton.setText("Remove on that day");
		garageOpeningHoursTopSeparator = new JSeparator();
	}
	
	private void hideLoginSection() {
		loginTopSeparator.setVisible(false);
		login.setVisible(false);
		signup.setVisible(false);
		orLabel.setVisible(false);
		loginErrorMessage.setVisible(false);
		loginUsername.setVisible(false);
		loginPassword.setVisible(false);
		signupUsername.setVisible(false);
		signupPassword.setVisible(false);
		loginUsernameField.setVisible(false);
		loginPasswordField.setVisible(false);
		signupUsernameField.setVisible(false);
		signupPasswordField.setVisible(false);
		loginButton.setVisible(false);
		signupButton.setVisible(false);
	}
	
	private void showLoginSection() {
		loginTopSeparator.setVisible(true);
		login.setVisible(true);
		signup.setVisible(true);
		orLabel.setVisible(true);
		loginErrorMessage.setVisible(true);
		loginUsername.setVisible(true);
		loginPassword.setVisible(true);
		signupUsername.setVisible(true);
		signupPassword.setVisible(true);
		loginUsernameField.setVisible(true);
		loginPasswordField.setVisible(true);
		signupUsernameField.setVisible(true);
		signupPasswordField.setVisible(true);
		loginButton.setVisible(true);
		signupButton.setVisible(true);
	}
	
	private void hideUpdateAccountSection() {
		updateAccountTopSeparator.setVisible(false);
		updateAccount.setVisible(false);
		updateAccountErrorMessage.setVisible(false);
		updateAccountSuccessMessage.setVisible(false);
		updateUsername.setVisible(false);
		updatePassword.setVisible(false);
		updateUsernameField.setVisible(false);
		updatePasswordField.setVisible(false);
		updateAccountButton.setVisible(false);
	}
	
	private void showUpdateAccountSection() {
		updateAccountTopSeparator.setVisible(true);
		updateAccount.setVisible(true);
		updateAccountErrorMessage.setVisible(true);
		updateAccountSuccessMessage.setVisible(true);
		updateUsername.setVisible(true);
		updatePassword.setVisible(true);
		updateUsernameField.setVisible(true);
		updatePasswordField.setVisible(true);
		updateAccountButton.setVisible(true);
	}
	
	private void hideUpdateGarageSection() {
		addNewLabel.setVisible(false);
		removeHoursLabel.setVisible(false);
		newHoursDayLabel.setVisible(false);
		newHoursOpenLabel.setVisible(false);
		newHoursCloseLabel.setVisible(false);
		removeHoursDayLabel.setVisible(false);
		newGarageHoursErrorMessage.setVisible(false);
		newGarageHoursSuccessMessage.setVisible(false);
		removeGarageHoursErrorMessage.setVisible(false);
		removeGarageHoursSuccessMessage.setVisible(false);
		timeSeparator1.setVisible(false);
		timeSeparator2.setVisible(false);
		newHoursDayBox.setVisible(false);
		removeHoursDayBox.setVisible(false);
		newHoursOpenHBox.setVisible(false);
		newHoursOpenMBox.setVisible(false);
		newHoursCloseHBox.setVisible(false);
		newHoursCloseMBox.setVisible(false);
		addGarageHoursButton.setVisible(false);
		removeGarageHoursButton.setVisible(false);
		removeGarageHoursButton.setVisible(false);
		garageOpeningHoursTopSeparator.setVisible(false);
	}
	
	private void showUpdateGarageSection() {
		addNewLabel.setVisible(true);
		removeHoursLabel.setVisible(true);
		newHoursDayLabel.setVisible(true);
		newHoursOpenLabel.setVisible(true);
		newHoursCloseLabel.setVisible(true);
		removeHoursDayLabel.setVisible(true);
		newGarageHoursErrorMessage.setVisible(true);
		newGarageHoursSuccessMessage.setVisible(true);
		removeGarageHoursErrorMessage.setVisible(true);
		removeGarageHoursSuccessMessage.setVisible(true);
		timeSeparator1.setVisible(true);
		timeSeparator2.setVisible(true);
		newHoursDayBox.setVisible(true);
		removeHoursDayBox.setVisible(true);
		newHoursOpenHBox.setVisible(true);
		newHoursOpenMBox.setVisible(true);
		newHoursCloseHBox.setVisible(true);
		newHoursCloseMBox.setVisible(true);
		addGarageHoursButton.setVisible(true);
		removeGarageHoursButton.setVisible(true);
		removeGarageHoursButton.setVisible(true);
		garageOpeningHoursTopSeparator.setVisible(true);
	}
	
	private void hideAppointmentSection() {
		//Cancel appt
		cancelAppt.setVisible(false);
		apptList.setVisible(false);
		apptLabel.setVisible(false);
		cancelApptButton.setVisible(false);
		
		// Make Appt
		makeApptLabel.setVisible(false);
		bookableServiceList.setVisible(false);
		bookableServiceLabel.setVisible(false);
		makeApptButton.setVisible(false);
		
		optServicePanel.setVisible(false);
		
		
		datePanel.setVisible(false);
		apptDatePicker.setVisible(false);
		apptDateLabel.setVisible(false);
		
		//update appt
		updateApptButton.setVisible(false);
		datePanelUpdate.setVisible(false);
		apptDatePickerUpdate.setVisible(false);
		optServicePanelUpdate.setVisible(false);
		horizontalLineBottom.setVisible(false);
	}
	
	private void showAppointmentSection() {
		//Cancel appt
		cancelAppt.setVisible(true);
		apptList.setVisible(true);
		apptLabel.setVisible(true);
		cancelApptButton.setVisible(true);
		
		// Make Appt
		makeApptLabel.setVisible(true);
		bookableServiceList.setVisible(true);
		bookableServiceLabel.setVisible(true);
		makeApptButton.setVisible(true);
		
		optServicePanel.setVisible(true);
		
		
		datePanel.setVisible(true);
		apptDatePicker.setVisible(true);
		apptDateLabel.setVisible(true);
		
		//update appt
		updateApptButton.setVisible(true);
		datePanelUpdate.setVisible(true);
		apptDatePickerUpdate.setVisible(true);
		optServicePanelUpdate.setVisible(true);
		horizontalLineBottom.setVisible(true);
	}
	

}
