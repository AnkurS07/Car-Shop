package ca.mcgill.ecse.carshop.view;


import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse223.carshop.controller.AppointmentController;
import ca.mcgill.ecse223.carshop.controller.CarShopController;
import ca.mcgill.ecse223.carshop.controller.TOAppointment;
import ca.mcgill.ecse223.carshop.controller.TOBookableService;
import ca.mcgill.ecse223.carshop.controller.TOBusiness;
import ca.mcgill.ecse223.carshop.controller.TOBusinessHour;
import ca.mcgill.ecse223.carshop.controller.TOComboItem;
import ca.mcgill.ecse223.carshop.controller.TOGarage;
import ca.mcgill.ecse223.carshop.controller.TOService;
import ca.mcgill.ecse223.carshop.controller.TOServiceBooking;
import ca.mcgill.ecse223.carshop.controller.TOServiceCombo;
import ca.mcgill.ecse223.carshop.controller.TOTechnician;
import ca.mcgill.ecse223.carshop.controller.TOTimeSlot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

	// Start appointment
	private JLabel startAppointment;
	private JLabel startAppointmentErrorMessage;
	private JLabel startAppointmentSuccessMessage;
	private JLabel startAppointmentLabel;
	private JComboBox<String> appointmentsBooked;
	private JButton startAppointmentButton;
	private JButton registerNoShowButton;
	private JLabel registerNoShowErrorMessage;
	private JLabel registerNoShowSuccessMessage;
	private JSeparator startAppointmentTopSeparator;

	// End appointment
	private JLabel endAppointment;
	private JLabel endAppointmentErrorMessage;
	private JLabel endAppointmentSuccessMessage;
	private JLabel endAppointmentLabel;
	private JComboBox<String> appointmentsInProgress;
	private JButton endAppointmentButton;
	private JSeparator endAppointmentTopSeparator;

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
	private HashMap<Integer, TOAppointment> appointments;
	private HashMap<Integer, TOAppointment> appointmentsStarted;
	private HashMap<Integer, TOAppointment> bookedAppts;
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

	//addBusinessHour
	private JLabel addBusinessHourLabel;
	private JTextField addBusinessHourStart;
	private JTextField addBusinessHourEnd;
	private JComboBox<String> selectBusinessHourDay;
	private JLabel addBusinessHourDayLabel;
	private JLabel addBusinessHourStartLabel;
	private JLabel addBusinessHourEndLabel;
	private JButton addBusinessHourAddButton;
	private JButton removeBusinessHourButton;
	private JLabel businessHourUpdateSuccessLabel;
	private JLabel businessHourUpdateErrorLabel;

	//setupBusinessInfo
	private JSeparator businessInfoTopSeparator;
	private JLabel setUpBusinessInfoLabel;
	private JButton businessInfoButton;
	private JTextField setBusinessName;
	private JTextField setBusinessAddress;
	private JTextField setBusinessEmail;
	private JTextField setBusinessPhone;
	private JButton businessInfoAddBusinessHourButton;
	private JLabel businessNameLabel;
	private JLabel businessAddressLabel;
	private JLabel businessEmailLabel;
	private JLabel businessPhoneLabel;	
	private JLabel businessInfoUpdateSuccessLabel;
	private JLabel businessInfoUpdateErrorLabel;

	//view business info
	private JLabel showBusinessInfo;
	private JLabel businessInfoName;
	private JLabel businessInfoAddress;
	private JLabel businessInfoEmail;
	private JLabel businessInfoPhone;


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

	private String addBusinessInfoSuccessMessage = null;
	private String addBusinessInfoErrorMessage = null;
	private String addBusinessHourSuccessMessage = null;
	private String addBusinessHourErrorMessage = null;

	private String startAppointmentError = null;
	private String startAppointmentSuccess = null;
	private String registerNoShowError = null;
	private String registerNoShowSuccess = null;
	private String endAppointmentError = null;
	private String endAppointmentSuccess = null;

	private HashMap<Integer, TOTechnician> technicians;
	enum TechnicianType { Tire, Engine, Transmission, Electronics, Fluids }
	private HashMap<Integer, TOService> existingServices;

	//add service
	private List<TOService> services;
	private List<TOServiceCombo> combos;
	private JLabel serviceErrorMessage;
	private JTextField setserviceName;
	private JLabel serviceNameLabel;
	private JTextField duration;
	private JLabel durationLabel;
	private JButton addServiceButton;
	private JLabel garage;
	private JComboBox<TOTechnician.TechnicianType> technician;
	private JLabel serviceLabel;

	//update Service
	private JLabel UpdateServiceLabel;
	private JSeparator servicesep;
	private JLabel changedNameError;

	private JLabel updateService;
	private JLabel changedName;
	private JLabel durationLabel2;
	private JLabel garage2;
	private JComboBox<String> serviceto;
	private JTextField setChangedName;
	private JComboBox<TOTechnician.TechnicianType> technician2;
	private JTextField duration2;
	private JButton updateServiceButton;

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

	//view appointments by date 
	private JLabel viewAppointments;
	private JDatePickerImpl apptViewDatePicker;
	private JLabel apptViewDateLabel;
	private JTable apptViewDateTable;
	private JScrollPane scrollView;
	private JDatePickerImpl apptViewDatePicker2; 
	private JLabel apptViewDateLabel2; 
	private JLabel viewAppointments2; 
	private JTable apptViewDateTable2;
	private JScrollPane scrollView2;


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



		this.add(new JScrollPane(), BorderLayout.CENTER);



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

		// Start appointment
		startAppointment = new JLabel("Start Appointment");
		startAppointment.setFont(underlinedFont.deriveFont(underlinedAttributes));
		startAppointmentErrorMessage = new JLabel();
		startAppointmentErrorMessage.setForeground(Color.RED);
		startAppointmentSuccessMessage = new JLabel();
		startAppointmentSuccessMessage.setForeground(new Color(0, 153, 0));
		registerNoShowErrorMessage = new JLabel();
		registerNoShowErrorMessage.setForeground(Color.RED);
		registerNoShowSuccessMessage = new JLabel();
		registerNoShowSuccessMessage.setForeground(new Color(0, 153, 0));
		startAppointmentLabel = new JLabel("Appointment: ");
		appointmentsBooked = new JComboBox<String>(new String[0]);
		appointmentsBooked.setMaximumSize(getPreferredSize());
		startAppointmentButton = new JButton();
		startAppointmentButton.setText("Start");
		registerNoShowButton = new JButton();
		registerNoShowButton.setText("Register no show");
		startAppointmentTopSeparator = new JSeparator();

		// End appointment
		endAppointment = new JLabel("End Appointment");
		endAppointment.setFont(underlinedFont.deriveFont(underlinedAttributes));
		endAppointmentErrorMessage = new JLabel();
		endAppointmentErrorMessage.setForeground(Color.RED);
		endAppointmentSuccessMessage = new JLabel();
		endAppointmentSuccessMessage.setForeground(new Color(0, 153, 0));
		endAppointmentLabel = new JLabel("Appointment: ");
		appointmentsInProgress = new JComboBox<String>(new String[0]);
		appointmentsInProgress.setMaximumSize(getPreferredSize());
		endAppointmentButton = new JButton();
		endAppointmentButton.setText("End");
		endAppointmentTopSeparator = new JSeparator();


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





		// Set up business info

		setUpBusinessInfoLabel = new JLabel();
		setUpBusinessInfoLabel.setText("Setup Business Information");
		setUpBusinessInfoLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
		businessInfoButton = new JButton();
		businessInfoButton.setText("Setup/Update Business Info");
		businessInfoButton.setMaximumSize(getPreferredSize());
		businessNameLabel = new JLabel();
		businessAddressLabel = new JLabel();
		businessEmailLabel = new JLabel();
		businessPhoneLabel = new JLabel();
		businessNameLabel.setText("Business Name:");
		setBusinessName = new JTextField(15);
		setBusinessName.setMaximumSize(getPreferredSize());
		businessAddressLabel.setText("Business Address:");
		setBusinessAddress = new JTextField(15);
		setBusinessAddress.setMaximumSize(getPreferredSize());
		businessEmailLabel.setText("Business Email:");
		businessEmailLabel.setMaximumSize(getPreferredSize());
		setBusinessEmail = new JTextField(15);
		setBusinessEmail.setMaximumSize(getPreferredSize());
		businessPhoneLabel.setText("Business Phone Number:");
		setBusinessPhone = new JTextField(15);
		setBusinessPhone.setMaximumSize(getPreferredSize());
		businessInfoAddBusinessHourButton = new JButton();
		businessInfoAddBusinessHourButton.setText("Add Business Hour");
		businessInfoAddBusinessHourButton.setMaximumSize(getPreferredSize());
		businessInfoUpdateSuccessLabel = new JLabel();
		businessInfoUpdateSuccessLabel.setText("");
		businessInfoUpdateErrorLabel = new JLabel();
		businessInfoUpdateErrorLabel.setText("");
		businessInfoUpdateErrorLabel.setForeground(Color.RED);
		businessInfoUpdateSuccessLabel.setForeground(new Color(0, 153, 0));
		businessHourUpdateSuccessLabel = new JLabel();
		businessHourUpdateSuccessLabel.setText("");
		businessHourUpdateErrorLabel = new JLabel();
		businessHourUpdateErrorLabel.setText("");
		businessHourUpdateErrorLabel.setForeground(Color.RED);
		businessHourUpdateSuccessLabel.setForeground(new Color(0, 153, 0));
		businessInfoTopSeparator = new JSeparator();
		showBusinessInfo = new JLabel();
		showBusinessInfo.setText("Business Info");
		showBusinessInfo.setFont(underlinedFont.deriveFont(underlinedAttributes));
		businessInfoName = new JLabel();
		businessInfoName.setText("Name: ");
		businessInfoAddress = new JLabel();
		businessInfoAddress.setText("Address: ");
		businessInfoEmail = new JLabel();
		businessInfoEmail.setText("Email: ");
		businessInfoPhone = new JLabel();
		businessInfoPhone.setText("Phone: ");

		addBusinessHourLabel = new JLabel();
		addBusinessHourLabel.setText("Add Business Hours");
		addBusinessHourLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
		addBusinessHourDayLabel = new JLabel();
		addBusinessHourStartLabel = new JLabel();
		addBusinessHourEndLabel = new JLabel();
		addBusinessHourAddButton = new JButton();
		addBusinessHourDayLabel.setText("Day");
		addBusinessHourStartLabel.setText("Start Time");
		addBusinessHourStart = new JTextField();
		addBusinessHourStart.setMaximumSize(getPreferredSize());
		addBusinessHourEnd = new JTextField();
		addBusinessHourEnd.setMaximumSize(getPreferredSize());
		addBusinessHourEndLabel.setText("End Time");
		addBusinessHourAddButton.setText("Add Business Hour");
		removeBusinessHourButton = new JButton();
		removeBusinessHourButton.setText("Remove on that day");
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		selectBusinessHourDay = new JComboBox<String>(days);
		selectBusinessHourDay.setMaximumSize(getPreferredSize());
		


		updateComboLabel = new JLabel();
		updateComboLabel.setText("Update Service Combo");
		updateComboLabel.setFont(titleFont.deriveFont(underlinedAttributes));
		serviceComboMap = new HashMap<Integer, TOServiceCombo>();
		serviceComboList = new JComboBox<String>(new String[0]);
		serviceComboList.setMaximumSize(getPreferredSize());
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

		//addService
		serviceNameLabel = new JLabel();
		serviceNameLabel.setText("Add Service");
		serviceNameLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
		durationLabel = new JLabel();
		durationLabel.setText("Set Duration: ");
		addServiceButton = new JButton();
		addServiceButton.setText("Add Service");
		setserviceName = new JTextField();
		duration = new JTextField();
		serviceErrorMessage = new JLabel();
		serviceErrorMessage.setForeground(Color.RED);
		garage = new JLabel();
		garage.setText("Technician");
		serviceLabel = new JLabel("Service name");
		technician = new JComboBox<TOTechnician.TechnicianType>();
		technician.setModel(new DefaultComboBoxModel<>(TOTechnician.TechnicianType.values()));
		setserviceName.setMaximumSize(getPreferredSize());
		duration.setMaximumSize(getPreferredSize());
		technician.setMaximumSize(getPreferredSize());
		servicesep = new JSeparator();


		//update Service
		UpdateServiceLabel = new JLabel();
		UpdateServiceLabel.setText("Update Service");
		UpdateServiceLabel.setFont(underlinedFont.deriveFont(underlinedAttributes));
		updateService = new JLabel();
		updateService.setText("Choose Service");
		changedName = new JLabel();
		changedName.setText("New Service");
		durationLabel2 = new JLabel();
		durationLabel2.setText("Duration");
		garage2 = new JLabel();
		garage2.setText("Garage");
		setChangedName = new JTextField();
		technician2 = new JComboBox<TOTechnician.TechnicianType>();
		technician2.setModel(new DefaultComboBoxModel<>(TOTechnician.TechnicianType.values()));
		duration2 = new JTextField();
		serviceto = new JComboBox<String>(new String[0]);
		updateServiceButton = new JButton();
		updateServiceButton.setText("Update Service");
		changedNameError = new JLabel();
		changedNameError.setForeground(Color.RED);
		duration2.setMaximumSize(getPreferredSize());
		setChangedName.setMaximumSize(getPreferredSize());
		technician2.setMaximumSize(getPreferredSize());
		serviceto.setMaximumSize(getPreferredSize());

		//view appointment by date for user
		SqlDateModel appointmentsViewer = new SqlDateModel();
		LocalDate date = LocalDate.now();
		appointmentsViewer.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
		appointmentsViewer.setSelected(true);
		Properties pO = new Properties();
		pO.put("text.today", "Today");
		pO.put("text.month", "Month");
		pO.put("text.year", "Year");
		JDatePanelImpl appointmentDatePanel = new JDatePanelImpl(appointmentsViewer, pO);
		apptViewDatePicker = new JDatePickerImpl(appointmentDatePanel, new DateLabelFormatter());
		apptViewDateLabel = new JLabel();
		apptViewDateLabel.setText("Date:");
		viewAppointments = new JLabel();
		viewAppointments.setText("View appointments by date");
		viewAppointments.setFont(underlinedFont.deriveFont(underlinedAttributes));
		String [] viewColumns = {"Start Time", "End Time", "Garage", "Service", "Status"};
		
		apptViewDateTable = new JTable(new DefaultTableModel(new Object[][] {}, viewColumns));
		scrollView = new JScrollPane(apptViewDateTable);

		
		//view appointment by date for owner 
		SqlDateModel appointmentsViewer2 = new SqlDateModel();
		appointmentsViewer.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
		appointmentsViewer.setSelected(true);
		JDatePanelImpl appointmentDatePanel2 = new JDatePanelImpl(appointmentsViewer2, pO);
		apptViewDatePicker2 = new JDatePickerImpl(appointmentDatePanel2, new DateLabelFormatter());
		apptViewDateLabel2 = new JLabel();
		apptViewDateLabel2.setText("Date:");
		viewAppointments2 = new JLabel();
		viewAppointments2.setText("View appointments by date");
		viewAppointments2.setFont(underlinedFont.deriveFont(underlinedAttributes));
		String [] viewColumns2 = {"User", "Start Time", "End Time", "Garage", "Service", "Status"};
		apptViewDateTable2 = new JTable(new DefaultTableModel(new Object[][] {}, viewColumns2));
		scrollView2 = new JScrollPane(apptViewDateTable2);
	
		

		hideAppointmentSection();
		hideStartEndAppointmentSection();
		hideServiceComboSection();
		hideUpdateServiceComboSection();
		hideService();
		hideBusinessSetupSection();



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

		startAppointmentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startAppointmentButtonActionPerformed(evt);
			}
		});

		endAppointmentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				endAppointmentButtonActionPerformed(evt);
			}
		});

		registerNoShowButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				registerNoShowButtonActionPerformed(evt);
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

		businessInfoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				businessInfoButtonActionPerformed(evt);
			}
		});

		addBusinessHourAddButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addBusinessHourAddButtonActionPerformed(evt);
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

		removeBusinessHourButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeBusinessHourButtonActionPerformed(evt);
			}

		});

		addServiceButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					addServiceButtonActionPerformed(evt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		updateServiceButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateServiceButtonActionPerformed(evt);
			}
		});
		
		apptViewDatePicker.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				showAppointments(evt);
				
			}
		});
		
		apptViewDatePicker2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt ) {
				
				showAppointments2(evt);
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
								.addGap(100)
								.addGroup(layout.createParallelGroup()
										.addComponent(setUpBusinessInfoLabel)
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup()
														.addComponent(businessNameLabel)
														.addComponent(businessAddressLabel)
														.addComponent(businessEmailLabel)
														.addComponent(businessPhoneLabel)
														)
												.addGap(100)
												.addGroup(layout.createParallelGroup()
														.addComponent(setBusinessName)
														.addComponent(setBusinessAddress)
														.addComponent(setBusinessEmail)
														.addComponent(setBusinessPhone)
														.addComponent(businessInfoButton)
														.addComponent(businessInfoUpdateSuccessLabel)
														.addComponent(businessInfoUpdateErrorLabel)
														)
												)

										)
								.addGap(100)
								.addGroup(layout.createParallelGroup()
										.addComponent(addBusinessHourLabel)
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup()
														.addComponent(addBusinessHourDayLabel)
														.addComponent(addBusinessHourStartLabel)
														.addComponent(addBusinessHourEndLabel)
														.addComponent(viewAppointments2)
														.addComponent(apptViewDateLabel2)
														.addComponent(apptViewDatePicker2)
														.addComponent(scrollView2)
														)
												.addGroup(layout.createParallelGroup()
														.addComponent(selectBusinessHourDay)
														.addComponent(addBusinessHourStart)
														.addComponent(addBusinessHourEnd)
														.addComponent(addBusinessHourAddButton)
														.addComponent(removeBusinessHourButton)
														.addComponent(businessHourUpdateErrorLabel)
														.addComponent(businessHourUpdateSuccessLabel)
														)
												)
										)
								)

						// Setup Business Info Section	
						//.addComponent(businessInfoTopSeparator)
						//						.addGroup(layout.createSequentialGroup()
						//								
						//								)



						//addService/Update Service
						.addComponent(servicesep)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(serviceNameLabel)
										.addComponent(serviceErrorMessage)

										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup()
														.addComponent(serviceLabel)
														.addComponent(durationLabel)
														.addComponent(garage)

														)
												.addGap(80)
												.addGroup(layout.createParallelGroup()
														.addComponent(setserviceName)
														.addComponent(duration)
														.addComponent(technician)
														.addComponent(addServiceButton))
												)
										)

								.addGap(100)
								.addGroup(layout.createParallelGroup()
										.addComponent(UpdateServiceLabel)
										.addComponent(changedNameError)

										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup()
														.addComponent(updateService)
														.addComponent(changedName)
														.addComponent(garage2)
														.addComponent(durationLabel2))
												.addGap(160)
												.addGroup(layout.createParallelGroup()
														.addComponent(serviceto)
														.addComponent(setChangedName)
														.addComponent(technician2)
														.addComponent(duration2)
														.addComponent(updateServiceButton)
														)
												)

										)
								)







						// Start and end Appointment Section
						.addComponent(startAppointmentTopSeparator)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(startAppointment)
										.addComponent(startAppointmentLabel)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(appointmentsBooked)
										.addComponent(startAppointmentButton)
										.addComponent(registerNoShowButton)
										.addComponent(startAppointmentErrorMessage)
										.addComponent(startAppointmentSuccessMessage)
										.addComponent(registerNoShowErrorMessage)
										.addComponent(registerNoShowSuccessMessage)
										)
								.addGap(100)
								.addGroup(layout.createParallelGroup()
										.addComponent(endAppointment)
										.addComponent(endAppointmentLabel)
										)
								.addGap(40)
								.addGroup(layout.createParallelGroup()
										.addComponent(appointmentsInProgress)
										.addComponent(endAppointmentButton)
										.addComponent(endAppointmentErrorMessage)
										.addComponent(endAppointmentSuccessMessage)
										)
								)


						//add/update service combo

						.addComponent(serviceComboTopSeparator)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(addComboLabel)
										.addComponent(nameLabel)
										.addComponent(mainServiceLabel)
										.addComponent(optService)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(comboName)
										.addComponent(mainService)
										.addComponent(optComboItemPanel)
										.addComponent(addOptComboItemButton)
										.addComponent(addComboButton)
										.addComponent(serviceComboErrorMessage)
										)
								.addGap(100)
								.addGroup(layout.createParallelGroup()
										.addComponent(updateComboLabel)
										.addComponent(nameLabel2)
										.addComponent(mainServiceLabel2)
										.addComponent(optService2)
										)
								.addGap(40)
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
								.addGap(100)
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
										.addComponent(bookableServiceLabel)
										.addComponent(updateService)
										.addComponent(durationLabel2)
										.addComponent(apptViewDateLabel))
								.addGroup(layout.createParallelGroup()
										.addComponent(makeApptLabel)
										.addComponent(bookableServiceList)
										.addComponent(optServicePanel)
										.addComponent(apptDatePicker)
										.addComponent(makeApptButton)

										.addComponent(serviceto)

										.addComponent(duration2)
										.addComponent(viewAppointments)
										.addComponent(apptViewDatePicker)
										.addComponent(scrollView)

										)
								.addGroup(layout.createParallelGroup()

										.addComponent(changedName)
										.addComponent(updateServiceButton))
								.addGroup(layout.createParallelGroup()

										.addComponent(setChangedName)
										.addComponent(changedNameError))
								.addGroup(layout.createParallelGroup()
										.addComponent(apptLabel)

										.addComponent(garage2))
								.addGroup(layout.createParallelGroup()
										.addComponent(cancelAppt)
										.addComponent(apptList)
										.addComponent(optServicePanelUpdate)
										.addComponent(apptDatePickerUpdate)
										.addComponent(updateApptButton)
										.addComponent(cancelApptButton)


										.addComponent(technician2)
										))
						)

				.addGroup(layout.createSequentialGroup()
						.addComponent(verticalLineRight)
						)
				


				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(showBusinessInfo)
								.addComponent(businessInfoName)
								.addComponent(businessInfoAddress)
								.addComponent(businessInfoEmail)
								.addComponent(businessInfoPhone)
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
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updateUsernameField, updatePasswordField, updatePasswordField2, updateAccountButton, setBusinessName, startAppointmentButton, addComboButton,setChangedName, technician2, serviceto, duration2,technician, duration, setserviceName, updateServiceButton, addServiceButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {setBusinessName, setBusinessAddress, setBusinessEmail, setBusinessPhone, businessInfoButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {selectBusinessHourDay, addBusinessHourStart, addBusinessHourEnd, addBusinessHourAddButton, removeBusinessHourButton, endAppointmentLabel, updateComboLabel, serviceComboList});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {newHoursDayBox, addGarageHoursButton, updateAccountButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {removeHoursDayBox, removeGarageHoursButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelAppt});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, updateApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, apptDatePickerUpdate});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {startAppointmentButton, registerNoShowButton, endAppointmentButton, appointmentsBooked, appointmentsInProgress});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, apptDatePicker});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {optComboItemPanel, addComboButton, mainService, comboName});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {optComboItemPanel2, cancelComboButton, updateComboButton, mainService2, comboName2, serviceComboList});

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updateUsername, businessPhoneLabel, startAppointment, addComboLabel, newHoursOpenLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {serviceComboList, endAppointmentButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updateAccount, newHoursOpenLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addBusinessHourStartLabel, updateComboLabel, endAppointmentLabel});

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {viewAppointments});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptViewDateLabel, apptViewDatePicker});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {scrollView});

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
						.addComponent(updateAccountTopSeparator)
						.addGroup(layout.createParallelGroup()
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
								.addGroup(layout.createSequentialGroup()

										.addComponent(setUpBusinessInfoLabel)
										.addGroup(layout.createParallelGroup()
												.addComponent(businessNameLabel)
												.addComponent(setBusinessName))
										.addGroup(layout.createParallelGroup()		
												.addComponent(businessAddressLabel)
												.addComponent(setBusinessAddress))
										.addGroup(layout.createParallelGroup()
												.addComponent(businessEmailLabel)
												.addComponent(setBusinessEmail))
										.addGroup(layout.createParallelGroup()
												.addComponent(businessPhoneLabel)
												.addComponent(setBusinessPhone)
												)
										.addComponent(businessInfoButton)
										.addComponent(businessInfoUpdateSuccessLabel)
										.addComponent(businessInfoUpdateErrorLabel)
										)
								.addGroup(layout.createSequentialGroup()

										.addComponent(addBusinessHourLabel)
										.addGroup(layout.createParallelGroup()
												.addComponent(addBusinessHourDayLabel)
												.addComponent(selectBusinessHourDay))
										.addGroup(layout.createParallelGroup()		
												.addComponent(addBusinessHourStartLabel)
												.addComponent(addBusinessHourStart))
										.addGroup(layout.createParallelGroup()
												.addComponent(addBusinessHourEndLabel)
												.addComponent(addBusinessHourEnd))
										.addComponent(addBusinessHourAddButton)
										.addComponent(removeBusinessHourButton)
										.addComponent(businessHourUpdateErrorLabel)
										.addComponent(businessHourUpdateSuccessLabel)
										)
								)


						// Setup Business Info Section
						//						.addComponent(businessInfoTopSeparator)
						//						.addGroup(layout.createParallelGroup()
						//								
						//								)


						// Add/Update Service 
						.addComponent(servicesep)
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(serviceNameLabel)
										.addGroup(layout.createParallelGroup()
												.addComponent(serviceLabel)
												.addComponent(setserviceName))
										.addGroup(layout.createParallelGroup()
												.addComponent(durationLabel)
												.addComponent(duration))
										.addGroup(layout.createParallelGroup()
												.addComponent(garage)
												.addComponent(technician))
										.addComponent(addServiceButton)
										.addComponent(serviceErrorMessage)


										)
								.addGroup(layout.createSequentialGroup()
										.addComponent(UpdateServiceLabel)
										.addGroup(layout.createParallelGroup()
												.addComponent(updateService)
												.addComponent(serviceto))
										.addGroup(layout.createParallelGroup()
												.addComponent(changedName)
												.addComponent(setChangedName))
										.addGroup(layout.createParallelGroup()
												.addComponent(garage2)
												.addComponent(technician2))
										.addGroup(layout.createParallelGroup()
												.addComponent(durationLabel2)
												.addComponent(duration2))
										.addComponent(updateServiceButton)
										.addComponent(changedNameError))
								.addGroup(layout.createSequentialGroup()
										.addComponent(viewAppointments2)
										.addGroup(layout.createParallelGroup()
												.addComponent(apptViewDateLabel2)
												)
										.addGroup(layout.createParallelGroup()
												.addComponent(apptViewDatePicker2))
												
										
										.addGroup(layout.createParallelGroup()
												.addComponent(scrollView2))
										)
								)
						




						// Start Appointment Section

						.addGroup(layout.createParallelGroup()
								.addComponent(startAppointmentTopSeparator))
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(startAppointment)
										.addGroup(layout.createParallelGroup()
												.addComponent(startAppointmentLabel)
												.addComponent(appointmentsBooked)
												)
										.addComponent(startAppointmentButton)
										.addComponent(registerNoShowButton)
										.addComponent(startAppointmentErrorMessage)
										.addComponent(startAppointmentSuccessMessage)
										.addComponent(registerNoShowErrorMessage)
										.addComponent(registerNoShowSuccessMessage)
										)
								.addGroup(layout.createSequentialGroup()
										.addComponent(endAppointment)
										.addGroup(layout.createParallelGroup()
												.addComponent(endAppointmentLabel)
												.addComponent(appointmentsInProgress)
												)
										.addComponent(endAppointmentButton)
										.addComponent(endAppointmentErrorMessage)
										.addComponent(endAppointmentSuccessMessage)
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

						.addGroup(layout.createParallelGroup()
								.addComponent(viewAppointments))
						.addGroup(layout.createParallelGroup()
								.addComponent(apptViewDateLabel)
								.addComponent(apptViewDatePicker)

								)
						.addGroup(layout.createParallelGroup()
								.addComponent(scrollView)
								)
						)

				.addGroup(layout.createSequentialGroup()
						.addComponent(verticalLineRight)
						)


				.addGroup(layout.createSequentialGroup()
						.addComponent(showBusinessInfo)
						.addComponent(businessInfoName)
						.addComponent(businessInfoAddress)
						.addComponent(businessInfoEmail)
						.addComponent(businessInfoPhone)
						.addGap(50)
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
			appointments = new HashMap<Integer, TOAppointment>();
			appointmentsStarted = new HashMap<Integer, TOAppointment>();
			apptList.removeAllItems();
			appointmentsBooked.removeAllItems();
			appointmentsInProgress.removeAllItems();

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

			idx = 0;
			for(TOAppointment a: AppointmentController.getAllAppointments()) {
				Date sd = a.getToServiceBooking(0).getToTimeSlot().getStartDate();
				Time st = a.getToServiceBooking(0).getToTimeSlot().getStartTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
				String appTime = sdf2.format(new Date(st.getTime()));
				String apptDate = sdf.format(sd)+"+"+appTime;
				appointments.put(idx, a);
				appointmentsBooked.addItem(a.getMainServiceName() + " " + apptDate);
				idx++;
			}
			appointmentsBooked.setSelectedIndex(-1);

			idx = 0;
			for(TOAppointment a: AppointmentController.getStartedAppointments()) {
				Date sd = a.getToServiceBooking(0).getToTimeSlot().getStartDate();
				Time st = a.getToServiceBooking(0).getToTimeSlot().getStartTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
				String appTime = sdf2.format(new Date(st.getTime()));
				String apptDate = sdf.format(sd)+"+"+appTime;
				appointmentsStarted.put(idx, a);
				appointmentsInProgress.addItem(a.getMainServiceName() + " " + apptDate);
				idx++;
			}
			appointmentsInProgress.setSelectedIndex(-1);


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

			TOBusiness business = CarShopController.getBusiness();
			if(business != null) {
				businessInfoName.setText("Name: "+ CarShopController.getBusiness().getName());
				businessInfoAddress.setText("Address: "+ CarShopController.getBusiness().getAddress());
				businessInfoEmail.setText("Email: "+ CarShopController.getBusiness().getEmail());
				businessInfoPhone.setText("Phone: "+ CarShopController.getBusiness().getPhoneNumber());

			}

			technician.removeAllItems();
			idx = 0;
			for (TOTechnician.TechnicianType a : TOTechnician.TechnicianType.values() ) {

				technician.addItem(a);
				idx++;
			}

			existingServices = new HashMap <Integer, TOService>();
			serviceto.removeAllItems();
			int index=0;

			for(TOService service: CarShopController.getExistingServices()) {
				existingServices.put(index, service);
				serviceto.addItem(service.getName());
				index++;
			}
			serviceto.setSelectedIndex(-1);

			technician2.removeAllItems();
			idx = 0;
			for (TOTechnician.TechnicianType a : TOTechnician.TechnicianType.values() ) {

				technician2.addItem(a);
				idx++;
			}

			// Refresh time
			try {
				CarShopController.setToCurrentDate();
				dateLabel.setText(CarShopController.getSystemDate());
				timeLabel.setText(CarShopController.getSystemTime());
			} catch (Exception e) {
				System.out.println(e.getMessage());

			}


			setserviceName.setText("");
			duration.setText("");
			duration2.setText("");
			setChangedName.setText("");






			// this is needed because the size of the window changes depending on whether an error message is shown or not
			pack();
		}
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
					hideService();
					hideLoginSection();
					headerTitle.setText("Hi, " + CarShopController.getLoggedInUser() + "!");
					logoutButton.setVisible(true);
					showUpdateAccountSection();
					if(CarShopController.isCustomerLoggedIn()) {
						hideService();
						showAppointmentSection();
						noShowLabel.setText("Number of no-shows: "+CarShopController.getLoggedInTOCustomer().getNoShowCount());
					} else if (CarShopController.isTechnicianLoggedIn()) {
						showUpdateGarageSection();
						hideService();
					} else {

						showBusinessSetupSection();

						showStartEndAppointmentSection();
						showService();
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
				hideStartEndAppointmentSection();
				hideServiceComboSection();
				hideService();
				hideBusinessSetupSection();

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
				errorMessage.setText("");
				loginErrorMessage.setText("");
				signupErrorMessage.setText("");
				updateAccountErrorMessage.setText("");
				newGarageHoursErrorMessage.setText("");
				removeGarageHoursErrorMessage.setText("");
				serviceComboErrorMessage.setText("");
				error = "";
				startAppointmentErrorMessage.setText("");
				startAppointmentSuccessMessage.setText("");
				registerNoShowErrorMessage.setText("");
				registerNoShowSuccessMessage.setText("");
				endAppointmentErrorMessage.setText("");
				endAppointmentSuccessMessage.setText("");
				updateserviceComboErrorMessage.setText("");
				noShowLabel.setText("");

				signupUsernameField.setText("");
				signupPasswordField.setText("");

				businessInfoUpdateSuccessLabel.setText("");
				businessInfoUpdateErrorLabel.setText("");
				businessHourUpdateErrorLabel.setText("");
				businessHourUpdateSuccessLabel.setText("");
				serviceErrorMessage.setText("");
				changedNameError.setText("");
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
				if(e instanceof NullPointerException) {
					newGarageHoursError = "You first need to set up the business";
				}
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
				String day = (String)removeHoursDayBox.getSelectedItem();
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

	private void startAppointmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
		startAppointmentError = "";
		startAppointmentSuccess = "";
		registerNoShowError = "";
		registerNoShowSuccess = "";
		endAppointmentError = "";
		endAppointmentSuccess = "";

		int selectedAppt = appointmentsBooked.getSelectedIndex();
		if(selectedAppt < 0) {
			startAppointmentError = "Appointment needs to be selected to start";
		}
		startAppointmentError = startAppointmentError.trim();

		if (startAppointmentError.length() == 0) {
			// call the controller
			try {
				TOAppointment app = appointments.get(selectedAppt);
				AppointmentController.startAppointmentFromView(app);
			} catch (Exception e) {
				startAppointmentError = e.getMessage();
			}
		}

		if (startAppointmentError.length() == 0) {
			startAppointmentSuccess = "Appointment successfully started";
		}

		startAppointmentErrorMessage.setText(startAppointmentError);
		startAppointmentSuccessMessage.setText(startAppointmentSuccess);
		registerNoShowErrorMessage.setText(registerNoShowError);
		registerNoShowSuccessMessage.setText(registerNoShowSuccess);
		endAppointmentErrorMessage.setText(endAppointmentError);
		endAppointmentSuccessMessage.setText(endAppointmentSuccess);
		// update visuals
		refreshData();	
	}

	private void endAppointmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
		startAppointmentError = "";
		startAppointmentSuccess = "";
		registerNoShowError = "";
		registerNoShowSuccess = "";
		endAppointmentError = "";
		endAppointmentSuccess = "";

		int selectedAppt = appointmentsInProgress.getSelectedIndex();
		if(selectedAppt < 0) {
			endAppointmentError = "Appointment needs to be selected to end";
		}
		endAppointmentError = endAppointmentError.trim();

		if (endAppointmentError.length() == 0) {
			// call the controller
			try {
				TOAppointment app = appointmentsStarted.get(selectedAppt);
				AppointmentController.endAppointmentFromView(app);
			} catch (Exception e) {
				endAppointmentError = e.getMessage();
			}
		}

		if (endAppointmentError.length() == 0) {
			endAppointmentSuccess = "Appointment successfully ended";
		}

		startAppointmentErrorMessage.setText(startAppointmentError);
		startAppointmentSuccessMessage.setText(startAppointmentSuccess);
		registerNoShowErrorMessage.setText(registerNoShowError);
		registerNoShowSuccessMessage.setText(registerNoShowSuccess);
		endAppointmentErrorMessage.setText(endAppointmentError);
		endAppointmentSuccessMessage.setText(endAppointmentSuccess);
		// update visuals
		refreshData();	
	}

	private void registerNoShowButtonActionPerformed(java.awt.event.ActionEvent evt) {
		startAppointmentError = "";
		startAppointmentSuccess = "";
		registerNoShowError = "";
		registerNoShowSuccess = "";
		endAppointmentError = "";
		endAppointmentSuccess = "";

		int selectedAppt = appointmentsBooked.getSelectedIndex();
		if(selectedAppt < 0) {
			registerNoShowError = "Appointment needs to be selected to register no show";
		}
		registerNoShowError = registerNoShowError.trim();

		if (registerNoShowError.length() == 0) {
			// call the controller
			try {
				TOAppointment app = appointments.get(selectedAppt);
				AppointmentController.registerNoShowFromView(app);
			} catch (Exception e) {
				registerNoShowError = e.getMessage();
			}
		}

		if (registerNoShowError.length() == 0) {
			registerNoShowSuccess = "Registered no show successfully";
		}

		startAppointmentErrorMessage.setText(startAppointmentError);
		startAppointmentSuccessMessage.setText(startAppointmentSuccess);
		registerNoShowErrorMessage.setText(registerNoShowError);
		registerNoShowSuccessMessage.setText(registerNoShowSuccess);
		endAppointmentErrorMessage.setText(endAppointmentError);
		endAppointmentSuccessMessage.setText(endAppointmentSuccess);
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
				if(e instanceof NullPointerException) {
					error = "Impossible to create appointment";
				}
				else {
					error = e.getMessage();
				}

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

	private void businessInfoButtonActionPerformed(ActionEvent evt) {
		addBusinessInfoErrorMessage = "";
		addBusinessInfoSuccessMessage = "";
		try {
			String name = setBusinessName.getText();
			String address = setBusinessAddress.getText();
			String phoneNumber = setBusinessPhone.getText();
			String email = setBusinessEmail.getText();
			if(name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
				addBusinessInfoErrorMessage = "Field(s) should not be empty";
			}
			else {
				if(CarShopController.getBusiness() == null) {
					CarShopController.SetUpBusinessInformation(name, address, phoneNumber, email);
					addBusinessInfoSuccessMessage = "The business " + name + " was set successfully"; 
				}
				else {
					CarShopController.updateBusinessInformation(name, address, phoneNumber, email);
					addBusinessInfoSuccessMessage = "The business " + name + " was updated successfully"; 
				} 
			}
		} catch (Exception e) {
			addBusinessInfoErrorMessage = e.getMessage();
		}

		businessInfoUpdateSuccessLabel.setText(addBusinessInfoSuccessMessage);
		businessInfoUpdateErrorLabel.setText(addBusinessInfoErrorMessage);
		refreshData();
	}

	private void addBusinessHourAddButtonActionPerformed(ActionEvent evt) {
		addBusinessHourErrorMessage = "";
		addBusinessHourSuccessMessage = "";
		try {
			String day = (String) selectBusinessHourDay.getSelectedItem();
			if(addBusinessHourStart.getText().matches("\\d{2}:\\d{2}") && addBusinessHourEnd.getText().matches("\\d{2}:\\d{2}")) {
				if(CarShopController.getBusiness() == null) {
					addBusinessHourErrorMessage ="Set Up Business Information First!";
				}
				else {
					Time startTime = new Time(AppointmentController.parseDate(addBusinessHourStart.getText(), "HH:mm").getTime());
					Time endTime = new Time(AppointmentController.parseDate(addBusinessHourEnd.getText(), "HH:mm").getTime());
					Time limit = new Time(AppointmentController.parseDate("24:00", "HH:mm").getTime());
					if (startTime.after(limit) || endTime.after(limit)) {
						addBusinessHourErrorMessage = "Time cannot exceed 23:59";
					}
					else {
						CarShopController.addBusinessHourFromDayAndTime(day, startTime, endTime);
						addBusinessHourSuccessMessage = "Business Hour added";
					}
		
				}
			} else {
				addBusinessHourErrorMessage ="Time should be of format HH:mm";
			}

		} catch (Exception e) {
			addBusinessHourErrorMessage = e.getMessage();
		}
		businessHourUpdateErrorLabel.setText(addBusinessHourErrorMessage);
		businessHourUpdateSuccessLabel.setText(addBusinessHourSuccessMessage);
		refreshData();
	}


	private void removeBusinessHourButtonActionPerformed(ActionEvent evt) {
		addBusinessHourErrorMessage = "";
		addBusinessHourSuccessMessage = "";
		try {
			String day = (String) selectBusinessHourDay.getSelectedItem();
			if(CarShopController.getBusiness() == null) {
				addBusinessHourErrorMessage ="Set Up Business Information First!";
			}
			else {
				if(CarShopController.removeBusinessHoursOnThatDay(day)) {
					addBusinessHourSuccessMessage = "Business hours successfully removed!";
				}
			}
		} catch (Exception e) {
			addBusinessHourErrorMessage = e.getMessage();
		}
		businessHourUpdateErrorLabel.setText(addBusinessHourErrorMessage);
		businessHourUpdateSuccessLabel.setText(addBusinessHourSuccessMessage);
		refreshData();
	}


	private void selectedComboUpdated(ActionEvent evt) {
		int selectedCombo = serviceComboList.getSelectedIndex();
		try {
			if(selectedCombo >= 0) {
				TOServiceCombo sc = serviceComboMap.get(selectedCombo);

				if(sc != null) {
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

	private void addServiceButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
		String serviceError = "";
		int strduration = 0;
		String name = null;
		String durationError = "";
		serviceErrorMessage.setForeground(Color.RED);

		TOTechnician c = new TOTechnician((ca.mcgill.ecse223.carshop.controller.TOTechnician.TechnicianType) technician.getSelectedItem());
		TOGarage h = new TOGarage(c);

		if(error == null || error.length() == 0) {
			name = setserviceName.getText();
			if(name.equals("")){
				serviceError = "Please enter a valid name";
				serviceErrorMessage.setText(serviceError);
			}
			else if(duration.getText().isEmpty()||duration.getText().contains("-")) {
				serviceErrorMessage.setText("Please enter a valid duration");
			}
			else{
				try {
					strduration = Integer.parseInt(duration.getText());
					CarShopController.addServiceView(name, strduration, h);
					serviceErrorMessage.setForeground(new Color(0, 153, 0));
					serviceErrorMessage.setText("Service successfully added");

				}
				catch(Exception e) {
					serviceErrorMessage.setText(e.getMessage());
				}
			}

		}

		refreshData();
		pack();

	}


	private void updateServiceButtonActionPerformed(java.awt.event.ActionEvent evt) {

		String name = null;

		int duration = 0;
		String changename2errortext = null;
		String durationnameerrortext = null;

		changedNameError.setForeground(Color.RED);

		TOTechnician c = new TOTechnician((ca.mcgill.ecse223.carshop.controller.TOTechnician.TechnicianType) technician.getSelectedItem());
		TOGarage h = new TOGarage(c);

		if(serviceto.getSelectedItem()==null||serviceto.getSelectedItem().toString().length()==0) {
			changedNameError.setText("Choose a Service");
		}

		String g = serviceto.getSelectedItem().toString();

		if(error == null || error.length() == 0) {
			name = setChangedName.getText();
			if(name.equals("")){
				changename2errortext = "Please enter a valid name";
				changedNameError.setText(changename2errortext);
			}
		}

		if(error==null || error.length()==0) {
			try {
				duration = Integer.parseInt(duration2.getText());
			}
			catch(Exception e) {
				durationnameerrortext = "Please enter a valid duration";
				changedNameError.setText(durationnameerrortext);
			}
		}


		if(error==null || error.length()==0){
			try {
				CarShopController.updateServiceView(g,setChangedName.getText(),duration, h);
				changedNameError.setForeground(new Color(0, 153, 0));
				changedNameError.setText("Service successfully updated");
			} catch (Exception e) {
				changedNameError.setText(e.getMessage());
			}
		}
		refreshData();
		pack();
	}

	private void showAppointments(ActionEvent evt) {
		
		JDatePanelImpl picker = (JDatePanelImpl)evt.getSource();
		Date newDate = new Date((picker.getModel().getYear())-1900, picker.getModel().getMonth(), picker.getModel().getDay());
		//String user = CarShopController.getLoggedInUser();
		List <String[]> data = AppointmentController.getApptInfo(newDate);
		DefaultTableModel model = (DefaultTableModel)(apptViewDateTable.getModel());
		model.setNumRows(0);
		for (String[] s: data) {
			model.addRow(s);
		}
	}
	private void showAppointments2(ActionEvent evt) {
		JDatePanelImpl picker = (JDatePanelImpl)evt.getSource();
		Date newDate = new Date((picker.getModel().getYear())-1900, picker.getModel().getMonth(), picker.getModel().getDay());
		List <String[]> data = AppointmentController.getApptInfo2(newDate);
		DefaultTableModel model = (DefaultTableModel)(apptViewDateTable2.getModel());
		model.setNumRows(0);
		for (String[] s: data) {
			model.addRow(s);
		}
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

		//view appt 
		viewAppointments.setVisible(false);
		apptViewDateLabel.setVisible(false);
		scrollView.setVisible(false);
		apptViewDatePicker.setVisible(false);

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

		//view appt 
		viewAppointments.setVisible(true);
		apptViewDateLabel.setVisible(true);
		scrollView.setVisible(true);
		apptViewDatePicker.setVisible(true);


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

	private void showBusinessSetupSection() {
		addBusinessHourLabel.setVisible(true);
		addBusinessHourStart.setVisible(true);
		addBusinessHourEnd.setVisible(true);
		selectBusinessHourDay.setVisible(true);
		addBusinessHourDayLabel.setVisible(true);
		addBusinessHourStartLabel.setVisible(true);
		addBusinessHourEndLabel.setVisible(true);
		addBusinessHourAddButton.setVisible(true);
		removeBusinessHourButton.setVisible(true);
		businessHourUpdateSuccessLabel.setVisible(true);
		businessHourUpdateErrorLabel.setVisible(true);
		businessInfoTopSeparator.setVisible(true);
		setUpBusinessInfoLabel.setVisible(true);
		businessInfoButton.setVisible(true);
		setBusinessName.setVisible(true);
		setBusinessAddress.setVisible(true);
		setBusinessEmail.setVisible(true);
		setBusinessPhone.setVisible(true);
		businessInfoAddBusinessHourButton.setVisible(true);
		businessNameLabel.setVisible(true);
		businessAddressLabel.setVisible(true);
		businessEmailLabel.setVisible(true);
		businessPhoneLabel.setVisible(true);	
		businessInfoUpdateSuccessLabel.setVisible(true);
		businessInfoUpdateErrorLabel.setVisible(true);
		//view appointments 
		viewAppointments2.setVisible(true);
		apptViewDateLabel2.setVisible(true);
		apptViewDatePicker2.setVisible(true);
		scrollView2.setVisible(true);
		
	}

	private void hideBusinessSetupSection() {
		addBusinessHourLabel.setVisible(false);
		addBusinessHourStart.setVisible(false);
		addBusinessHourEnd.setVisible(false);
		selectBusinessHourDay.setVisible(false);
		addBusinessHourDayLabel.setVisible(false);
		addBusinessHourStartLabel.setVisible(false);
		addBusinessHourEndLabel.setVisible(false);
		addBusinessHourAddButton.setVisible(false);
		removeBusinessHourButton.setVisible(false);
		businessHourUpdateSuccessLabel.setVisible(false);
		businessHourUpdateErrorLabel.setVisible(false);
		businessInfoTopSeparator.setVisible(false);
		setUpBusinessInfoLabel.setVisible(false);
		businessInfoButton.setVisible(false);
		setBusinessName.setVisible(false);
		setBusinessAddress.setVisible(false);
		setBusinessEmail.setVisible(false);
		setBusinessPhone.setVisible(false);
		businessInfoAddBusinessHourButton.setVisible(false);
		businessNameLabel.setVisible(false);
		businessAddressLabel.setVisible(false);
		businessEmailLabel.setVisible(false);
		businessPhoneLabel.setVisible(false);	
		businessInfoUpdateSuccessLabel.setVisible(false);
		businessInfoUpdateErrorLabel.setVisible(false);
		//view appointments
		viewAppointments2.setVisible(false);
		apptViewDateLabel2.setVisible(false);
		apptViewDatePicker2.setVisible(false);
		scrollView2.setVisible(false);
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


	private void showStartEndAppointmentSection() {
		// Start appointment
		startAppointment.setVisible(true);
		startAppointmentLabel.setVisible(true);
		appointmentsBooked.setVisible(true);
		startAppointmentButton.setVisible(true);
		registerNoShowButton.setVisible(true);
		startAppointmentTopSeparator.setVisible(true);
		startAppointmentErrorMessage.setVisible(true);
		startAppointmentSuccessMessage.setVisible(true);
		registerNoShowErrorMessage.setVisible(true);
		registerNoShowSuccessMessage.setVisible(true);

		// End appointment
		endAppointment.setVisible(true);
		endAppointmentLabel.setVisible(true);
		appointmentsInProgress.setVisible(true);
		endAppointmentButton.setVisible(true);		
		endAppointmentTopSeparator.setVisible(true);
		endAppointmentErrorMessage.setVisible(true);
		endAppointmentSuccessMessage.setVisible(true);
	}

	private void hideStartEndAppointmentSection() {
		// Start appointment
		startAppointment.setVisible(false);
		startAppointmentLabel.setVisible(false);
		appointmentsBooked.setVisible(false);
		startAppointmentButton.setVisible(false);
		registerNoShowButton.setVisible(false);
		startAppointmentTopSeparator.setVisible(false);
		startAppointmentErrorMessage.setVisible(false);
		startAppointmentSuccessMessage.setVisible(false);
		registerNoShowErrorMessage.setVisible(false);
		registerNoShowSuccessMessage.setVisible(false);

		// End appointment
		endAppointment.setVisible(false);
		endAppointmentLabel.setVisible(false);
		appointmentsInProgress.setVisible(false);
		endAppointmentButton.setVisible(false);		
		endAppointmentTopSeparator.setVisible(false);
		endAppointmentErrorMessage.setVisible(false);
		endAppointmentSuccessMessage.setVisible(false);
	}

	private void showService() {
		servicesep.setVisible(true);
		updateService.setVisible(true);
		serviceto.setVisible(true);
		changedName.setVisible(true);
		setChangedName.setVisible(true);
		garage2.setVisible(true);
		technician2.setVisible(true);
		durationLabel2.setVisible(true);
		duration2.setVisible(true);


		setserviceName.setVisible(true);
		serviceNameLabel.setVisible(true);
		addServiceButton.setVisible(true);
		duration.setVisible(true);
		durationLabel.setVisible(true);
		garage.setVisible(true);
		technician.setVisible(true);
		updateServiceButton.setVisible(true);
		serviceLabel.setVisible(true);
		UpdateServiceLabel.setVisible(true);
	}

	private void hideService() {
		//service
		servicesep.setVisible(false);
		setserviceName.setVisible(false);
		serviceNameLabel.setVisible(false);
		addServiceButton.setVisible(false);
		duration.setVisible(false);
		durationLabel.setVisible(false);
		garage.setVisible(false);
		technician.setVisible(false);
		serviceLabel.setVisible(false);

		//update Service
		updateService.setVisible(false);
		serviceto.setVisible(false);
		changedName.setVisible(false);
		setChangedName.setVisible(false);
		garage2.setVisible(false);
		technician2.setVisible(false);
		durationLabel2.setVisible(false);
		duration2.setVisible(false);
		updateServiceButton.setVisible(false);
		UpdateServiceLabel.setVisible(false);
	}
}


