package ca.mcgill.ecse.carshop.view;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse223.carshop.controller.AppointmentController;
import ca.mcgill.ecse223.carshop.controller.CarShopController;
import ca.mcgill.ecse223.carshop.controller.TOAppointment;
import ca.mcgill.ecse223.carshop.controller.TOBookableService;
import ca.mcgill.ecse223.carshop.controller.TOBusinessHour;
import ca.mcgill.ecse223.carshop.controller.TOComboItem;
import ca.mcgill.ecse223.carshop.controller.TOService;
import ca.mcgill.ecse223.carshop.controller.TOServiceBooking;
import ca.mcgill.ecse223.carshop.controller.TOServiceCombo;
import ca.mcgill.ecse223.carshop.controller.TOTimeSlot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
	private JLabel timeLabel;
	private JLabel dateLabel;
	private JButton refreshButton;
	
	private JLabel noShowLabel;
	
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
	private JPasswordField loginPasswordField;
	private JTextField signupUsernameField;
	private JPasswordField signupPasswordField;
	private JButton loginButton;
	private JButton signupButton;
	private JSeparator loginTopSeparator;
	
	// Update Account
	private JLabel updateAccount;
	private JLabel updateAccountErrorMessage;
	private JLabel updateAccountSuccessMessage;
	private JLabel updateUsername;
	private JLabel updatePassword;
	private JLabel updatePasswordConfirm;
	private JTextField updateUsernameField;
	private JPasswordField updatePasswordField;
	private JPasswordField updatePasswordField2;
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
	
	private JSeparator verticalLineRight;
	
	private JLabel businessHourLabel;
	private JLabel tireGarageHourLabel;
	private JLabel engineGarageHourLabel;
	private JLabel transmissionGarageHourLabel;
	private JLabel elecGarageHourLabel;
	private JLabel fluidsGarageHourLabel;
	
	private JPanel businessHourPanel;
	private List<String> businessHours;
	private JPanel tireGarageHourPanel;
	private List<String> tireGarageHours;
	private JPanel engineGarageHourPanel;
	private List<String> engineGarageHours;
	private JPanel transmissionGarageHourPanel;
	private List<String> transmissionGarageHours;
	private JPanel elecGarageHourPanel;
	private List<String> elecGarageHours;
	private JPanel fluidsGarageHourPanel;
	private List<String> fluidsGarageHours;
	
	private JLabel show1;
	private JLabel show2;
	private JLabel show3;
	private JLabel show4;
	private JLabel show5;
	private JLabel show6;
	private JCheckBox showBusinessHours;
	private JCheckBox showtireGarageHours;
	private JCheckBox showengineGarageHours;
	private JCheckBox showtransmissionGarageHours;
	private JCheckBox showelecGarageHours;
	private JCheckBox showfluidsGarageHours;
	
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

	//add service
	private List<TOService> services;
	private List<TOServiceCombo> combos;
	
	//add service combo
	private JLabel addComboLabel;
	private JLabel nameLabel;
	private JTextField comboName;
	private JLabel mainServiceLabel;
	private HashMap<Integer, String> mainServiceMap;
	private JComboBox<String> mainService;
	private JLabel optService;
	private List<ComboVisualizer> comboVisualizerList;
	private JPanel optComboItemPanel;
	private JButton addOptComboItemButton;
	private JButton addComboButton;
	private JSeparator serviceComboTopSeparator;
	private JLabel serviceComboErrorMessage;
	
	//update service combo
	private JLabel updateComboLabel;
	private HashMap<Integer, TOServiceCombo> serviceComboMap;
	private JComboBox<String> serviceComboList;
	private JLabel nameLabel2;
	private JTextField comboName2;
	private JLabel mainServiceLabel2;
	private HashMap<Integer, String> mainServiceMap2;
	private JComboBox<String> mainService2;
	private JLabel optService2;
	private List<ComboVisualizer> comboVisualizerList2;
	private JPanel optComboItemPanel2;
	private JButton addOptComboItemButton2;
	private JButton updateComboButton;
	private JButton cancelComboButton;
	private JLabel updateserviceComboErrorMessage;

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
		dateLabel = new JLabel();
		timeLabel = new JLabel();
		try {
			dateLabel.setText(CarShopController.getSystemDate());
			timeLabel.setText(CarShopController.getSystemTime());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		refreshButton = new JButton();
		refreshButton.setText("Refresh");
		
		noShowLabel = new JLabel();
		noShowLabel.setText("");
		
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
		loginUsernameField.setMaximumSize(getPreferredSize());
		loginPasswordField = new JPasswordField(15);
		loginPasswordField.setMaximumSize(getPreferredSize());
		loginButton = new JButton();
		loginButton.setText("Login");
		loginTopSeparator = new JSeparator();
		
		// sign up
		signup = new JLabel();
		signup.setText("Customer Sign Up");
		signup.setFont(underlinedFont.deriveFont(underlinedAttributes));
		signupErrorMessage = new JLabel();
		signupErrorMessage.setForeground(Color.RED);
		signupUsername = new JLabel();
		signupUsername.setText("Username: ");
		signupPassword = new JLabel();
		signupPassword.setText("Password: ");
		signupUsernameField = new JTextField(15);
		signupUsernameField.setMaximumSize(getPreferredSize());
		signupPasswordField = new JPasswordField(15);
		signupPasswordField.setMaximumSize(getPreferredSize());
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
		updatePasswordConfirm = new JLabel();
		updatePasswordConfirm.setText("Confirm: ");
		updateUsernameField = new JTextField(15);
		updateUsernameField.setMaximumSize(getPreferredSize());
		updatePasswordField = new JPasswordField(15);
		updatePasswordField.setMaximumSize(getPreferredSize());
		updatePasswordField2 = new JPasswordField(15);
		updatePasswordField2.setMaximumSize(getPreferredSize());
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
		apptList.setMaximumSize(getPreferredSize());
		
		// Make appt
		makeApptLabel = new JLabel();
		makeApptLabel.setText("Make Appointment");
		makeApptLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
		bookableServiceLabel = new JLabel();
		bookableServiceLabel.setText("Bookable Service:");
		bookableServiceList = new JComboBox<String>(new String[0]);
		bookableServiceList.setMaximumSize(getPreferredSize());
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
		
		verticalLineRight = new JSeparator(SwingConstants.VERTICAL);
		
		businessHourLabel = new JLabel();
		businessHourLabel.setText("Business Hours");
		businessHourLabel.setFont(titleFont.deriveFont(underlinedAttributes));
		tireGarageHourLabel = new JLabel();
		tireGarageHourLabel.setText("Tire Garage Hours");
		tireGarageHourLabel.setFont(titleFont.deriveFont(underlinedAttributes));
		engineGarageHourLabel = new JLabel();
		engineGarageHourLabel.setText("Engine Garage Hours");
		engineGarageHourLabel.setFont(titleFont.deriveFont(underlinedAttributes));
		transmissionGarageHourLabel = new JLabel();
		transmissionGarageHourLabel.setText("Transmission Garage Hours");
		transmissionGarageHourLabel.setFont(titleFont.deriveFont(underlinedAttributes));
		elecGarageHourLabel = new JLabel();
		elecGarageHourLabel.setText("Electronics Garage Hours");
		elecGarageHourLabel.setFont(titleFont.deriveFont(underlinedAttributes));
		fluidsGarageHourLabel = new JLabel();
		fluidsGarageHourLabel.setText("Fluids Garage Hours");
		fluidsGarageHourLabel.setFont(titleFont.deriveFont(underlinedAttributes));
		
		businessHourPanel = new JPanel();
		businessHourPanel.setLayout(new BoxLayout(businessHourPanel, BoxLayout.PAGE_AXIS));
		businessHourPanel.setVisible(false);
		tireGarageHourPanel = new JPanel();
		tireGarageHourPanel.setLayout(new BoxLayout(tireGarageHourPanel, BoxLayout.PAGE_AXIS));
		tireGarageHourPanel.setVisible(false);
		engineGarageHourPanel = new JPanel();
		engineGarageHourPanel.setLayout(new BoxLayout(engineGarageHourPanel, BoxLayout.PAGE_AXIS));
		engineGarageHourPanel.setVisible(false);
		transmissionGarageHourPanel = new JPanel();
		transmissionGarageHourPanel.setLayout(new BoxLayout(transmissionGarageHourPanel, BoxLayout.PAGE_AXIS));
		transmissionGarageHourPanel.setVisible(false);
		elecGarageHourPanel = new JPanel();
		elecGarageHourPanel.setLayout(new BoxLayout(elecGarageHourPanel, BoxLayout.PAGE_AXIS));
		elecGarageHourPanel.setVisible(false);
		fluidsGarageHourPanel = new JPanel();
		fluidsGarageHourPanel.setLayout(new BoxLayout(fluidsGarageHourPanel, BoxLayout.PAGE_AXIS));
		fluidsGarageHourPanel.setVisible(false);
		 
		businessHours = new ArrayList<String>();
		tireGarageHours = new ArrayList<String>();
		engineGarageHours = new ArrayList<String>();
		transmissionGarageHours = new ArrayList<String>();
		elecGarageHours = new ArrayList<String>();
		fluidsGarageHours = new ArrayList<String>();
		 
		show1 = new JLabel();
		show1.setText("Show");
		show2 = new JLabel();
		show2.setText("Show");
		show3 = new JLabel();
		show3.setText("Show");
		show4 = new JLabel();
		show4.setText("Show");
		show5 = new JLabel();
		show5.setText("Show");
		show6 = new JLabel();
		show6.setText("Show");
		showBusinessHours = new JCheckBox();
		showBusinessHours.setSelected(false);
		showtireGarageHours = new JCheckBox();
		showtireGarageHours.setSelected(false);
		showengineGarageHours = new JCheckBox();
		showengineGarageHours.setSelected(false);
		showtransmissionGarageHours = new JCheckBox();
		showtransmissionGarageHours.setSelected(false);
		showelecGarageHours = new JCheckBox();
		showelecGarageHours.setSelected(false);
		showfluidsGarageHours = new JCheckBox();
		showfluidsGarageHours.setSelected(false);
		
		services = new ArrayList<TOService>();
		combos = new ArrayList<TOServiceCombo>();
		 
		addComboLabel = new JLabel();
		addComboLabel.setText("Add Service Combo");
		addComboLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
		nameLabel = new JLabel();
		nameLabel.setText("Name: ");
		comboName = new JTextField(15);
		comboName.setMaximumSize(getPreferredSize());
		mainServiceLabel = new JLabel();
		mainServiceLabel.setText("Main service: ");
		mainServiceMap = new HashMap<Integer, String>();
		mainService = new JComboBox<String>(new String[0]);
		mainService.setMaximumSize(getPreferredSize());
		optService = new JLabel();
		optService.setText("Services: ");
		comboVisualizerList = new ArrayList<ComboVisualizer>();
		optComboItemPanel = new JPanel();
		optComboItemPanel.setLayout(new BoxLayout(optComboItemPanel, BoxLayout.PAGE_AXIS));
		addOptComboItemButton = new JButton();
		addOptComboItemButton.setText("+");
		addComboButton = new JButton();
		addComboButton.setText("Add Service Combo");
		serviceComboTopSeparator = new JSeparator();
		serviceComboErrorMessage = new JLabel();
		serviceComboErrorMessage.setForeground(Color.RED);
		
		updateComboLabel = new JLabel();
		updateComboLabel.setText("Update Service Combo");
		updateComboLabel.setFont(titleFont.deriveFont(underlinedAttributes));
		serviceComboMap = new HashMap<Integer, TOServiceCombo>();
		serviceComboList = new JComboBox<String>(new String[0]);
		nameLabel2 = new JLabel();
		nameLabel2.setText("Name: ");
		comboName2 = new JTextField(15);
		comboName2.setMaximumSize(getPreferredSize());
		mainServiceLabel2 = new JLabel();
		mainServiceLabel2.setText("Main service: ");
		mainServiceMap2 = new HashMap<Integer, String>();
		mainService2 = new JComboBox<String>(new String[0]);
		mainService2.setMaximumSize(getPreferredSize());
		optService2 = new JLabel();
		optService2.setText("Services: ");
		comboVisualizerList2 = new ArrayList<ComboVisualizer>();
		optComboItemPanel2 = new JPanel();
		optComboItemPanel2.setLayout(new BoxLayout(optComboItemPanel2, BoxLayout.PAGE_AXIS));
		addOptComboItemButton2 = new JButton();
		addOptComboItemButton2.setText("+");
		updateComboButton = new JButton();
		updateComboButton.setText("Update Service Combo");
		cancelComboButton = new JButton();
		cancelComboButton.setText("Delete Service Combo");
		updateserviceComboErrorMessage = new JLabel();
		updateserviceComboErrorMessage.setForeground(Color.RED);
		
		hideAppointmentSection();
		hideServiceComboSection();
		hideUpdateServiceComboSection();
		
		// Action Listeners
		// Listeners for header
		refreshButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				refreshButtonActionPerformed(evt);
			}
		});
		
		
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
		
		showBusinessHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				businessHourPanel.setVisible(!businessHourPanel.isVisible());
				pack();
			}
		});
		
		showtireGarageHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tireGarageHourPanel.setVisible(!tireGarageHourPanel.isVisible());
				pack();
			}
		});
		
		showengineGarageHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				engineGarageHourPanel.setVisible(!engineGarageHourPanel.isVisible());
				pack();
			}
		});
		
		showtransmissionGarageHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				transmissionGarageHourPanel.setVisible(!transmissionGarageHourPanel.isVisible());
				pack();
			}
		});
		
		showelecGarageHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				elecGarageHourPanel.setVisible(!elecGarageHourPanel.isVisible());
				pack();
			}
		});
		
		showfluidsGarageHours.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fluidsGarageHourPanel.setVisible(!fluidsGarageHourPanel.isVisible());
				pack();
			}
		});
		
		addOptComboItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addOptServiceActionPerformed(evt);
			}
		});
		
		addComboButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addComboActionPerformed(evt);
			}
		});
		
		addOptComboItemButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addOptServiceActionPerformed2(evt);
			}
		});
		
		updateComboButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateComboActionPerformed(evt);
			}
		});
		
		cancelComboButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelComboActionPerformed(evt);
			}
		});
		
		serviceComboList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectedComboUpdated(evt);
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
		layout.setHorizontalGroup( layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						// Header Section
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(headerTitle)
										.addComponent(noShowLabel)
										)
								.addGap(50)
								.addComponent(logoutButton)
								.addGap(300)
								.addGroup(layout.createParallelGroup()
										.addComponent(dateLabel)
										.addComponent(timeLabel)
										)
								.addComponent(refreshButton)
								)
						// Login - Setup Section
						.addComponent(loginTopSeparator)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(loginUsername)
										.addComponent(loginPassword)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(login)
										.addComponent(loginErrorMessage)
										.addComponent(loginUsernameField)
										.addComponent(loginPasswordField)
										.addComponent(loginButton)
										)
								.addGap(50)
								.addComponent(orLabel)
								.addGap(50)
								.addGroup(layout.createParallelGroup()
										.addComponent(signupUsername)
										.addComponent(signupPassword)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(signup)
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
										.addComponent(updatePasswordConfirm)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(updateAccountErrorMessage)
										.addComponent(updateUsernameField)
										.addComponent(updatePasswordField)
										.addComponent(updatePasswordField2)
										.addComponent(updateAccountButton)
										)
								.addComponent(updateAccountSuccessMessage)
								)
						//add/update service combo
						.addComponent(serviceComboTopSeparator)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(nameLabel)
										.addComponent(mainServiceLabel)
										.addComponent(optService)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(addComboLabel)
										.addComponent(comboName)
										.addComponent(mainService)
										.addComponent(optComboItemPanel)
										.addComponent(addOptComboItemButton)
										.addComponent(addComboButton)
										.addComponent(serviceComboErrorMessage)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(updateComboLabel)
										.addComponent(nameLabel2)
										.addComponent(mainServiceLabel2)
										.addComponent(optService2)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(serviceComboList)
										.addComponent(comboName2)
										.addComponent(mainService2)
										.addComponent(optComboItemPanel2)
										.addComponent(addOptComboItemButton2)
										.addComponent(updateComboButton)
										.addComponent(cancelComboButton)
										.addComponent(updateserviceComboErrorMessage)
										)
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
						)
					
				.addGroup(layout.createSequentialGroup()
						.addComponent(verticalLineRight)
						)
					
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(businessHourLabel)
										.addComponent(showBusinessHours)
										.addComponent(show1)
										)
								.addComponent(businessHourPanel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(tireGarageHourLabel)
										.addComponent(showtireGarageHours)
										.addComponent(show2)
										)
								.addComponent(tireGarageHourPanel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(engineGarageHourLabel)
										.addComponent(showengineGarageHours)
										.addComponent(show3)
										)
								.addComponent(engineGarageHourPanel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(transmissionGarageHourLabel)
										.addComponent(showtransmissionGarageHours)
										.addComponent(show4)
										)
								.addComponent(transmissionGarageHourPanel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(elecGarageHourLabel)
										.addComponent(showelecGarageHours)
										.addComponent(show5)
										)
								.addComponent(elecGarageHourPanel)
								.addGroup(layout.createSequentialGroup()
										.addComponent(fluidsGarageHourLabel)
										.addComponent(showfluidsGarageHours)
										.addComponent(show6)
										)
								.addComponent(fluidsGarageHourPanel)
								)
						)
				);
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {loginUsernameField, loginPasswordField, loginButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {signupUsernameField, signupPasswordField, signupButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updateUsernameField, updatePasswordField, updatePasswordField2, updateAccountButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {newHoursDayBox, addGarageHoursButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {removeHoursDayBox, removeGarageHoursButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelAppt});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, updateApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, apptDatePickerUpdate});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, apptDatePicker});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {optComboItemPanel, addComboButton, mainService, comboName});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {optComboItemPanel2, cancelComboButton, updateComboButton, mainService2, comboName2, serviceComboList});
		
		// Vertical Layout
		layout.setVerticalGroup( layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						// Header
						.addGroup(layout.createParallelGroup()
								.addComponent(headerTitle)
								.addComponent(logoutButton)
								.addGroup(layout.createSequentialGroup()
										.addComponent(dateLabel)
										.addComponent(timeLabel)
										)
								.addComponent(refreshButton)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(noShowLabel)
								)
						.addGap(20)
						// Login - Sign up Section
						.addGroup(layout.createParallelGroup()
								.addComponent(loginTopSeparator))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(loginErrorMessage)
										.addComponent(signupErrorMessage)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(login)
										.addComponent(signup)
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
										.addComponent(updatePasswordConfirm)
										.addComponent(updatePasswordField2)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(updateAccountButton)
										.addComponent(updateAccountSuccessMessage)
										)
								)
						//add/update service combo
						.addGroup(layout.createParallelGroup()
								.addComponent(serviceComboTopSeparator)
								)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(addComboLabel)
										.addComponent(updateComboLabel)
										.addComponent(serviceComboList)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(nameLabel)
										.addComponent(comboName)
										.addComponent(nameLabel2)
										.addComponent(comboName2)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(mainServiceLabel)
										.addComponent(mainService)
										.addComponent(mainServiceLabel2)
										.addComponent(mainService2)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(optService)
										.addComponent(optComboItemPanel)
										.addComponent(optService2)
										.addComponent(optComboItemPanel2)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(addOptComboItemButton)
										.addComponent(addOptComboItemButton2)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(addComboButton)
										.addComponent(updateComboButton)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(serviceComboErrorMessage)
										.addComponent(cancelComboButton)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(updateserviceComboErrorMessage)
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
						
						)
					
					.addGroup(layout.createSequentialGroup()
							.addComponent(verticalLineRight)
							)
					
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(businessHourLabel)
									.addComponent(showBusinessHours)
									.addComponent(show1)
									)
							.addComponent(businessHourPanel)
							.addGroup(layout.createParallelGroup()
									.addComponent(tireGarageHourLabel)
									.addComponent(showtireGarageHours)
									.addComponent(show2)
									)
							.addComponent(tireGarageHourPanel)
							.addGroup(layout.createParallelGroup()
									.addComponent(engineGarageHourLabel)
									.addComponent(showengineGarageHours)
									.addComponent(show3)
									)
							.addComponent(engineGarageHourPanel)
							.addGroup(layout.createParallelGroup()
									.addComponent(transmissionGarageHourLabel)
									.addComponent(showtransmissionGarageHours)
									.addComponent(show4)
									)
							.addComponent(transmissionGarageHourPanel)
							.addGroup(layout.createParallelGroup()
									.addComponent(elecGarageHourLabel)
									.addComponent(showelecGarageHours)
									.addComponent(show5)
									)
							.addComponent(elecGarageHourPanel)
							.addGroup(layout.createParallelGroup()
									.addComponent(fluidsGarageHourLabel)
									.addComponent(showfluidsGarageHours)
									.addComponent(show6)
									)
							.addComponent(fluidsGarageHourPanel)
						)
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
			
			services.clear();
			for(TOBookableService s: AppointmentController.getBookableServices()) {
				if(s instanceof TOService) {
					services.add((TOService) s);
				}
			}
			
			idx = 0;
			mainService.removeAllItems();
			mainServiceMap.clear();
			for(TOService s: services) {
				mainService.addItem(s.getName());
				mainServiceMap.put(idx, s.getName());
				idx++;
			}
			mainService.setSelectedIndex(-1);
			
			serviceComboErrorMessage.setText("");
			comboName.setText("");
			optComboItemPanel.removeAll();
			
			combos.clear();
			for(TOBookableService s: AppointmentController.getBookableServices()) {
				if(s instanceof TOServiceCombo) {
					combos.add((TOServiceCombo) s);
				}
			}
			
			idx = 0;
			mainService2.removeAllItems();
			mainServiceMap2.clear();
			for(TOService s: services) {
				mainService2.addItem(s.getName());
				mainServiceMap2.put(idx, s.getName());
				idx++;
			}
			mainService2.setSelectedIndex(-1);
			
			idx = 0;
			serviceComboList.removeAllItems();
			serviceComboMap.clear();
			for(TOServiceCombo sc: combos) {
				serviceComboList.addItem(sc.getName());
				serviceComboMap.put(idx, sc);
				idx++;
			}
			serviceComboList.setSelectedIndex(-1);
			
			updateserviceComboErrorMessage.setText("");
			comboName2.setText("");
			optComboItemPanel2.removeAll();
			
			hideServiceComboFieldsUpdate();
			
			apptDatePicker.getModel().setValue(null);
			apptDatePickerUpdate.getModel().setValue(null);
			
			//update hours for each type
			businessHourPanel.removeAll();
			businessHours.clear();
			for(TOBusinessHour toHour: CarShopController.getBusinessHours("business")) {
				businessHourPanel.add(new BusinessHoursVisualizer(toHour));
				businessHours.add(toHour.getDayOfWeek());
			}
			businessHourPanel.setMaximumSize(tireGarageHourPanel.getPreferredSize());
			
			tireGarageHourPanel.removeAll();
			tireGarageHours.clear();
			for(TOBusinessHour toHour: CarShopController.getBusinessHours("tire")) {
				tireGarageHourPanel.add(new BusinessHoursVisualizer(toHour));
				tireGarageHours.add(toHour.getDayOfWeek());
			}
			tireGarageHourPanel.setMaximumSize(tireGarageHourPanel.getPreferredSize());
			
			engineGarageHourPanel.removeAll();
			engineGarageHours.clear();
			for(TOBusinessHour toHour: CarShopController.getBusinessHours("engine")) {
				engineGarageHourPanel.add(new BusinessHoursVisualizer(toHour));
				engineGarageHours.add(toHour.getDayOfWeek());
			}
			engineGarageHourPanel.setMaximumSize(tireGarageHourPanel.getPreferredSize());
			
			transmissionGarageHourPanel.removeAll();
			transmissionGarageHours.clear();
			for(TOBusinessHour toHour: CarShopController.getBusinessHours("transmission")) {
				transmissionGarageHourPanel.add(new BusinessHoursVisualizer(toHour));
				transmissionGarageHours.add(toHour.getDayOfWeek());
			}
			transmissionGarageHourPanel.setMaximumSize(tireGarageHourPanel.getPreferredSize());
			
			elecGarageHourPanel.removeAll();
			elecGarageHours.clear();
			for(TOBusinessHour toHour: CarShopController.getBusinessHours("electronics")) {
				elecGarageHourPanel.add(new BusinessHoursVisualizer(toHour));
				elecGarageHours.add(toHour.getDayOfWeek());
			}
			elecGarageHourPanel.setMaximumSize(tireGarageHourPanel.getPreferredSize());
			
			fluidsGarageHourPanel.removeAll();
			fluidsGarageHours.clear();
			for(TOBusinessHour toHour: CarShopController.getBusinessHours("fluids")) {
				fluidsGarageHourPanel.add(new BusinessHoursVisualizer(toHour));
				fluidsGarageHours.add(toHour.getDayOfWeek());
			}
			fluidsGarageHourPanel.setMaximumSize(tireGarageHourPanel.getPreferredSize());
			// Refresh time
			try {
				CarShopController.setToCurrentDate();
				dateLabel.setText(CarShopController.getSystemDate());
				timeLabel.setText(CarShopController.getSystemTime());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		// this is needed because the size of the window changes depending on whether an error message is shown or not
		pack();
	}
	
	private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
		refreshData();
	}
	
	private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
		loginError = "";
		if (loginUsernameField.getText().isEmpty()|| loginPasswordField.getText().isEmpty()) {
			loginError = "Username and password must not be empty.";
		}
		// Check for other errors here //
		if (loginError.length() == 0) {
			try {
				if (CarShopController.logIn(loginUsernameField.getText(), loginPasswordField.getText())) {
					hideLoginSection();
					headerTitle.setText("Hi, " + CarShopController.getLoggedInUser() + "!");
					logoutButton.setVisible(true);
					showUpdateAccountSection();
					if(CarShopController.isCustomerLoggedIn()) {
						showAppointmentSection();
						noShowLabel.setText("Number of no-shows: "+CarShopController.getLoggedInTOCustomer().getNoShowCount());
					} else if (CarShopController.isTechnicianLoggedIn()) {
						showUpdateGarageSection();
					} else {
						showServiceComboSection();
						showUpdateServiceComboSection();
					}
					errorMessage.setText("");
					loginErrorMessage.setText("");
					signupErrorMessage.setText("");
					updateAccountErrorMessage.setText("");
					newGarageHoursErrorMessage.setText("");
					removeGarageHoursErrorMessage.setText("");
					serviceComboErrorMessage.setText("");
					updateserviceComboErrorMessage.setText("");
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
				hideServiceComboSection();
				hideUpdateServiceComboSection();
				headerTitle.setText("CarShop");
				logoutButton.setVisible(false);
				loginUsernameField.setText("");
				loginPasswordField.setText("");
				updateUsernameField.setText("");
				updatePasswordField.setText("");
				updatePasswordField2.setText("");
				updateAccountErrorMessage.setText("");
				updateAccountSuccessMessage.setText("");
				error = "";
				noShowLabel.setText("");
			}
		} catch (Exception e) {
			loginError = e.getMessage();
		}
		refreshData();
	}
	
	private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		signupError = "";
		if (signupUsernameField.getText().isEmpty() || signupPasswordField.getText().isEmpty()) {
			signupError = "Username and password must not be empty.";
		} else if (signupUsernameField.getText().toLowerCase().contains("owner") || signupUsernameField.getText().toLowerCase().contains("technician")) {
			signupError = "Username cannot contain \"technician\" or \"owner\"";
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
		if (updateUsernameField.getText().isEmpty() || updatePasswordField.getText().isEmpty()) {
			updateAccountError = "Username and password must not be empty.";
		} else if(!Arrays.equals( updatePasswordField.getPassword(),  updatePasswordField2.getPassword())) {
			updateAccountError = "New passwords do not match";
		}
		// Check for other errors here //
		if (updateAccountError.length() == 0) {
			try {
				if (CarShopController.updateCustomer(updateUsernameField.getText(), updatePasswordField.getText())) {
					updateAccountSuccess = "Account successfully updated!";
					updateUsernameField.setText("");
					updatePasswordField.setText("");
					updatePasswordField2.setText("");
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
		if(CarShopController.isTechnicianLoggedIn()) {
			if(CarShopController.getLoggedInTechnicianType().toLowerCase().contains("tire")) {
				if(tireGarageHours.contains((String)newHoursDayBox.getSelectedItem())) {
					newGarageHoursError = "Business Hours already exist on that day.";
				}
			} else if (CarShopController.getLoggedInTechnicianType().toLowerCase().contains("engine")) {
				if(engineGarageHours.contains((String)newHoursDayBox.getSelectedItem())) {
					newGarageHoursError = "Business Hours already exist on that day.";
				}
			} else if (CarShopController.getLoggedInTechnicianType().toLowerCase().contains("transmission")) {
				if(transmissionGarageHours.contains((String)newHoursDayBox.getSelectedItem())) {
					newGarageHoursError = "Business Hours already exist on that day.";
				}
			} else if (CarShopController.getLoggedInTechnicianType().toLowerCase().contains("electronics")) {
				if(elecGarageHours.contains((String)newHoursDayBox.getSelectedItem())) {
					newGarageHoursError = "Business Hours already exist on that day.";
				}
			} else if (CarShopController.getLoggedInTechnicianType().toLowerCase().contains("fluids")) {
				if(fluidsGarageHours.contains((String)newHoursDayBox.getSelectedItem())) {
					newGarageHoursError = "Business Hours already exist on that day.";
				}
			}
		}
		if (newGarageHoursError.length() == 0) {
			try {
				String day = (String)newHoursDayBox.getSelectedItem();
				String inputStartTime = newHoursOpenHBox.getSelectedItem() + ":" + newHoursOpenMBox.getSelectedItem();
				String inputEndTime = newHoursCloseHBox.getSelectedItem() + ":" + newHoursCloseMBox.getSelectedItem();
				String type = CarShopController.getLoggedInTechnicianType();
				if (CarShopController.addHoursToGarageOfTechnicianType(day, inputStartTime, inputEndTime, type)) {
					newGarageHoursSuccess = "Business hours successfully added!";
				}
			} catch (Exception e) {
				//e.printStackTrace();
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
				if (CarShopController.removeGarageHoursOnDay(day)) {
					removeGarageHoursSuccess = "Business hours succesfully updated!";
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
				AppointmentController.cancelAppointment(CarShopController.getLoggedInUsername(), app.getMainServiceName(), app.getToServiceBooking(0).getToTimeSlot().getStartDate(), app.getToServiceBooking(0).getToTimeSlot().getStartTime());
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

				AppointmentController.makeAppointmentFromView(false, CarShopController.getLoggedInUsername(), toBs, services, timeSlots, new ArrayList<TOTimeSlot>());
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
				
				AppointmentController.makeAppointmentFromView(false, CarShopController.getLoggedInUsername(), toBs, services, timeSlots, toExclude);
				TOAppointment app = appts.get(selectedAppt);
				AppointmentController.deleteAppt(CarShopController.getLoggedInUsername(), app.getMainServiceName(), app.getToServiceBooking(0).getToTimeSlot().getStartDate(), app.getToServiceBooking(0).getToTimeSlot().getStartTime());
			
			} catch (Exception e) {
				error = e.getMessage();
			}
		}

		// update visuals
		refreshData();	
	}
	
	private void addOptServiceActionPerformed(ActionEvent evt) {
		String err = "";
		
		try {
			ComboVisualizer visualizer = new ComboVisualizer(services, this);
			optComboItemPanel.add(visualizer);
			comboVisualizerList.add(visualizer);
			
		} catch (Exception e) {
			err = e.getMessage();
		}
		serviceComboErrorMessage.setText(err);
		pack();
		
	}
	
	private void addComboActionPerformed(ActionEvent evt) {
		String err = "";
		
		try {
			
			int selectedMainService = mainService.getSelectedIndex();
			if(selectedMainService < 0) {
				throw new Exception("A main service needs to be selected");
			} else if (comboName.getText().length() == 0) {
				throw new Exception("The service combo must have a name");
			}
			
			TOService service = CarShopController.getTOService(mainServiceMap.get(selectedMainService));
			TOServiceCombo sc = new TOServiceCombo(comboName.getText(), service.getName());
			
			for(ComboVisualizer visualizer : comboVisualizerList) {
				TOService s = visualizer.getSelectedService();
				new TOComboItem(visualizer.isMnadatory(), s, sc);
			}
			
			CarShopController.addServiceComboFromView(sc);
			refreshData();
			
		} catch (Exception e) {
			err = e.getMessage();
		}
		serviceComboErrorMessage.setText(err);

		
	}
	
	private void selectedComboUpdated(ActionEvent evt) {
		int selectedCombo = serviceComboList.getSelectedIndex();
		try {
			if(selectedCombo >= 0) {
				TOServiceCombo sc = serviceComboMap.get(selectedCombo);
				
				comboName2.setText(sc.getName());
				TOComboItem mainService = sc.getService(0);
				int idx = -1;
				for(int j = 0; j< services.size();j++) {
					if(services.get(j).getName().equals(sc.getMainService())) {
						idx = j;
						break;
					}
				}
				mainService2.setSelectedIndex(idx);
				
				optComboItemPanel2.removeAll();
				comboVisualizerList2.clear();
				
				for(int i = 0; i<sc.getServices().size(); i++) {
					
					ComboVisualizer visualizer = new ComboVisualizer(services, this);
					String serviceName = sc.getService(i).getService().getName();
					idx = -1;
					for(int j = 0; j< services.size();j++) {
						if(services.get(j).getName().equals(serviceName)) {
							idx = j;
							break;
						}
					}
					visualizer.setSelectedService(idx);
					visualizer.setMandatory(sc.getService(i).getMandatory());
					optComboItemPanel2.add(visualizer);
					comboVisualizerList2.add(visualizer);
				}
				showServiceComboFieldsUpdate();
			}
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		// update visuals
		//refreshData();	
		pack();
		
	}
	
	private void addOptServiceActionPerformed2(ActionEvent evt) {
		String err = "";
		
		try {
			ComboVisualizer visualizer = new ComboVisualizer(services, this);
			optComboItemPanel2.add(visualizer);
			comboVisualizerList2.add(visualizer);
			
		} catch (Exception e) {
			err = e.getMessage();
		}
		updateserviceComboErrorMessage.setText(err);
		pack();
		
	}

	private void updateComboActionPerformed(ActionEvent evt) {
		String err = "";
		
		try {
			int selectedComboIdx = serviceComboList.getSelectedIndex();
			if(selectedComboIdx < 0) {
				throw new Exception("A service combo needs to be selected");
			}
			TOServiceCombo previous = serviceComboMap.get(selectedComboIdx);
			
			int selectedMainService = mainService2.getSelectedIndex();
			if(selectedMainService < 0) {
				throw new Exception("A main service needs to be selected");
			} else if (comboName2.getText().length() == 0) {
				throw new Exception("The service combo must have a name");
			}
			
			TOService service = CarShopController.getTOService(mainServiceMap2.get(selectedMainService));
			
			TOServiceCombo sc = new TOServiceCombo(comboName2.getText(), service.getName());
			
			for(ComboVisualizer visualizer : comboVisualizerList2) {
				TOService s = visualizer.getSelectedService();
				new TOComboItem(visualizer.isMnadatory(), s, sc);
			}

			CarShopController.updateServiceComboFromView(sc, previous.getName());
			refreshData();
			
		} catch (Exception e) {
			err = e.getMessage();
		}
		updateserviceComboErrorMessage.setText(err);
		pack();
		
	}
	
	private void cancelComboActionPerformed(ActionEvent evt) {
		String err = "";
		
		try {
			int selectedComboIdx = serviceComboList.getSelectedIndex();
			if(selectedComboIdx < 0) {
				throw new Exception("A service combo needs to be selected");
			}
			
			int selectedMainService = mainService2.getSelectedIndex();
			if(selectedMainService < 0) {
				throw new Exception("A main service needs to be selected");
			} else if (comboName2.getText().length() == 0) {
				throw new Exception("The service combo must have a name");
			}
			
			TOService service = CarShopController.getTOService(mainServiceMap2.get(selectedMainService));
			
			TOServiceCombo sc = new TOServiceCombo(comboName2.getText(), service.getName());
			
			for(ComboVisualizer visualizer : comboVisualizerList2) {
				TOService s = visualizer.getSelectedService();
				new TOComboItem(visualizer.isMnadatory(), s, sc);
			}

			CarShopController.cancelServiceComboFromView(sc);
			refreshData();
			
		} catch (Exception e) {
			err = e.getMessage();
		}
		updateserviceComboErrorMessage.setText(err);
		pack();
		
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
		updatePasswordConfirm.setVisible(false);
		updateUsernameField.setVisible(false);
		updatePasswordField.setVisible(false);
		updatePasswordField2.setVisible(false);
		updateAccountButton.setVisible(false);
	}
	
	private void showUpdateAccountSection() {
		updateAccountTopSeparator.setVisible(true);
		updateAccount.setVisible(true);
		updateAccountErrorMessage.setVisible(true);
		updateAccountSuccessMessage.setVisible(true);
		updateUsername.setVisible(true);
		updatePassword.setVisible(true);
		updatePasswordConfirm.setVisible(true);
		updateUsernameField.setVisible(true);
		updatePasswordField.setVisible(true);
		updatePasswordField2.setVisible(true);
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
	
	private void hideServiceComboSection() {
		addComboLabel.setVisible(false);
		nameLabel.setVisible(false);
		comboName.setVisible(false);
		mainServiceLabel.setVisible(false);
		mainService.setVisible(false);
		optService.setVisible(false);
		optComboItemPanel.setVisible(false);
		addOptComboItemButton.setVisible(false);
		addComboButton.setVisible(false);
		serviceComboTopSeparator.setVisible(false);
		serviceComboErrorMessage.setVisible(false);
	}
	
	private void showServiceComboSection() {
		addComboLabel.setVisible(true);
		nameLabel.setVisible(true);
		comboName.setVisible(true);
		mainServiceLabel.setVisible(true);
		mainService.setVisible(true);
		optService.setVisible(true);
		optComboItemPanel.setVisible(true);
		addOptComboItemButton.setVisible(true);
		addComboButton.setVisible(true);
		serviceComboTopSeparator.setVisible(true);
		serviceComboErrorMessage.setVisible(true);
	}
	
	private void hideServiceComboFieldsUpdate() {
		nameLabel2.setVisible(false);
		comboName2.setVisible(false);
		mainServiceLabel2.setVisible(false);
		mainService2.setVisible(false);
		optService2.setVisible(false);
		optComboItemPanel2.setVisible(false);
		addOptComboItemButton2.setVisible(false);
		updateComboButton.setVisible(false);
		cancelComboButton.setVisible(false);
		updateserviceComboErrorMessage.setVisible(false);
	}
	
	private void showServiceComboFieldsUpdate() {
		nameLabel2.setVisible(true);
		comboName2.setVisible(true);
		mainServiceLabel2.setVisible(true);
		mainService2.setVisible(true);
		optService2.setVisible(true);
		optComboItemPanel2.setVisible(true);
		addOptComboItemButton2.setVisible(true);
		updateComboButton.setVisible(true);
		cancelComboButton.setVisible(true);
		updateserviceComboErrorMessage.setVisible(true);
	}
	
	private void hideUpdateServiceComboSection() {
		updateComboLabel.setVisible(false);
		serviceComboList.setVisible(false);
		showServiceComboFieldsUpdate();
	}
	
	private void showUpdateServiceComboSection() {
		updateComboLabel.setVisible(true);
		serviceComboList.setVisible(true);
	}
	
	
	// helper methods
	
	public void removeOptComboItem(ComboVisualizer combo) {
		int idx = -1;
		for (int i = 0; i < comboVisualizerList.size(); i++) {
			if(combo.equals(comboVisualizerList.get(i))) {
				idx = i;
				break;
			}
		}
		if(idx >= 0) {
			comboVisualizerList.remove(idx);
			optComboItemPanel.remove(idx);
		} else {
			for (int i = 0; i < comboVisualizerList2.size(); i++) {
				if(combo.equals(comboVisualizerList2.get(i))) {
					idx = i;
					break;
				}
			}
			if(idx >= 0) {
				comboVisualizerList2.remove(idx);
				optComboItemPanel2.remove(idx);
			}
		}
		pack();
	}

}
