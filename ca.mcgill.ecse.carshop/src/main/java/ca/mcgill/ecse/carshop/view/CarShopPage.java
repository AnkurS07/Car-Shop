package ca.mcgill.ecse.carshop.view;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;


import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.view.CarShopPage.TechnicianType;
import ca.mcgill.ecse223.carshop.controller.AppointmentController;
import ca.mcgill.ecse223.carshop.controller.CarShopController;
import ca.mcgill.ecse223.carshop.controller.TOAppointment;
import ca.mcgill.ecse223.carshop.controller.TOBookableService;
import ca.mcgill.ecse223.carshop.controller.TOComboItem;
import ca.mcgill.ecse223.carshop.controller.TOGarage;
import ca.mcgill.ecse223.carshop.controller.TOOwner;
import ca.mcgill.ecse223.carshop.controller.TOService;
import ca.mcgill.ecse223.carshop.controller.TOServiceBooking;
import ca.mcgill.ecse223.carshop.controller.TOServiceCombo;
import ca.mcgill.ecse223.carshop.controller.TOTechnician;
import ca.mcgill.ecse223.carshop.controller.TOTimeSlot;

import java.awt.Color;
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

    //add service
    private JLabel serviceErrorMessage;
    private JLabel durationErrorMessage;
    private JTextField serviceName;
    private JLabel serviceNameLabel;
    private JTextField duration;
    private JLabel durationLabel;
    private JButton addServiceButton;
    private JLabel garage;
    private JComboBox<TOTechnician.TechnicianType> technician;

    //update Service
    private JLabel servicetoerror;
    private JLabel changedname2error;
    private JLabel durationnameerror;
    private JLabel updateService;
    private JLabel serviceToBeUpdated;
    private JLabel changedName;
    private JLabel duration2;
    private JLabel garage2;
    private JComboBox<String> serviceto;
    private JTextField changedname2;
    private JComboBox<TOTechnician.TechnicianType> garagename;
    private JTextField durationname;
    private JButton updateServiceButton;

    
    
    //Define ServiceCombo
    private JLabel newComboName;
    private JLabel newMainServiceLabel;
    private JLabel newComboItemLabel;
    private JLabel newMandatoryComboItem;
    private JTextField newServiceComboNameText;
    private JButton defineServiceCombo;
    private JComboBox<String> mainServiceComboBox;
    private JScrollPane newMandatoryscrollpane;
    DefaultListModel<String> newComboListModel;
    DefaultListModel<String> newMandatoryComboItemListModel; 
    


    // data elements
    private String error = null;
    private String loginError = null;
    private String signupError = null;
    private String updateAccountError = null;
    private String updateAccountSuccess = null;
    private HashMap<Integer, TOTechnician> technicians;
    enum TechnicianType { Tire, Engine, Transmission, Electronics, Fluids }
    private HashMap<Integer, TOService> existingServices;
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

        //addService
        serviceNameLabel = new JLabel();
        serviceNameLabel.setText("Add Service: ");
        durationLabel = new JLabel();
        durationLabel.setText("Set Duration: ");
        addServiceButton = new JButton();
        addServiceButton.setText("Add Service");
        serviceName = new JTextField();
        duration = new JTextField();
        serviceErrorMessage = new JLabel();
        serviceErrorMessage.setForeground(Color.RED);
        garage = new JLabel();
        garage.setText("Technician");

        technician = new JComboBox<TOTechnician.TechnicianType>();
        technician.setModel(new DefaultComboBoxModel<>(TOTechnician.TechnicianType.values()));
        durationErrorMessage = new JLabel();
        durationErrorMessage.setForeground(Color.RED);

        //update Service
        updateService = new JLabel();
        updateService.setText("Choose Service");
        changedName = new JLabel();
        changedName.setText("New Service");
        duration2 = new JLabel();
        duration2.setText("Duration");
        garage2 = new JLabel();
        garage2.setText("Garage");
        changedname2 = new JTextField();
        garagename = new JComboBox<TOTechnician.TechnicianType>();
        garagename.setModel(new DefaultComboBoxModel<>(TOTechnician.TechnicianType.values()));
        durationname = new JTextField();
        serviceto = new JComboBox<String>(new String[0]);
        updateServiceButton = new JButton();
        updateServiceButton.setText("Update Service");
        changedname2error = new JLabel();
        durationnameerror = new JLabel();

        servicetoerror = new JLabel();

        //Service combo
      newComboName = new JLabel();
      newComboName.setText("New Service combo name");
      newServiceComboNameText = new JTextField();
      
      newMainServiceLabel = new JLabel();
      newMainServiceLabel.setText("New Main Service");
      
      
      newComboItemLabel = new JLabel();
      newComboItemLabel.setText("New Combo Item");
      
      newMandatoryComboItem = new JLabel();
      newMandatoryComboItem.setText("Mandatory Services");
      
      defineServiceCombo = new JButton();
      defineServiceCombo.setText("Define Service Combo");
      
      mainServiceComboBox = new JComboBox<String>();
      
      JList<String> newMandatoryList = new JList<String>(newMandatoryComboItemListModel);
      JList<String> newComboItemList = new JList<String>(newComboListModel);
      
      newMandatoryscrollpane = new JScrollPane();
      
      newMandatoryComboItemListModel = new DefaultListModel<String>();
      newComboListModel = new DefaultListModel<String>();
        
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
                        // Make-Update-Cancel Appointment Section
                        .addComponent(errorMessage)
                        .addComponent(horizontalLineTop)
                        .addComponent(horizontalLineBottom)
                        /*.addComponent(horizontalLineMiddle1)
                        .addComponent(horizontalLineMiddle2)
                        .addComponent(horizontalLineBottom)*/
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(bookableServiceLabel)
                                        .addComponent(serviceNameLabel)
                                        .addComponent(updateService)
                                        .addComponent(duration2)
                                        .addComponent(newComboName)
                                        .addComponent(newMandatoryComboItem))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(makeApptLabel)
                                        .addComponent(bookableServiceList)
                                        .addComponent(optServicePanel)
                                        .addComponent(apptDatePicker)
                                        .addComponent(makeApptButton)
                                        .addComponent(serviceName)
                                        .addComponent(serviceErrorMessage)
                                        .addComponent(addServiceButton)
                                        .addComponent(serviceto)
                                        .addComponent(servicetoerror)
                                        .addComponent(durationname)
                                        .addComponent(newServiceComboNameText)
                                        .addComponent(newMandatoryList))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(garage)
                                        .addComponent(changedName)
                                        .addComponent(updateServiceButton)
                                        .addComponent(newMainServiceLabel))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(technician)
                                        .addComponent(changedname2)
                                        .addComponent(changedname2error)
                                        .addComponent(mainServiceComboBox))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(apptLabel)
                                        .addComponent(durationLabel)
                                        .addComponent(garage2)
                                		.addComponent(newComboItemLabel))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(cancelAppt)
                                        .addComponent(apptList)
                                        .addComponent(optServicePanelUpdate)
                                        .addComponent(apptDatePickerUpdate)
                                        .addComponent(updateApptButton)
                                        .addComponent(cancelApptButton)
                                        .addComponent(durationErrorMessage)
                                        .addComponent(duration)
                                        .addComponent(garagename)
                                        .addComponent(durationnameerror)
                                        .addComponent(newComboItemList))
                                .addGroup(layout.createSequentialGroup())
                        ));


        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {serviceName, addServiceButton,duration, apptList, technician});

        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {loginUsernameField, loginPasswordField, loginButton});
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {signupUsernameField, signupPasswordField, signupButton});
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {updateUsernameField, updatePasswordField, updateAccountButton});
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelApptButton, });
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, cancelAppt});
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, updateApptButton});
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {apptList, apptDatePickerUpdate});
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptLabel, });
        layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookableServiceList, makeApptButton, });
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
                        // Add Service
                        .addGroup(layout.createParallelGroup()
                                .addComponent(serviceErrorMessage)
                                .addComponent(durationErrorMessage)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(serviceName)
                                .addComponent(serviceNameLabel)
                                .addComponent(duration)
                                .addComponent(durationLabel)
                                .addComponent(garage)
                                .addComponent(technician))
                        .addComponent(addServiceButton)

                        .addGroup(layout.createParallelGroup()
                                .addComponent(horizontalLineBottom))

                        //update Service
                        .addGroup(layout.createParallelGroup()
                                .addComponent(changedname2error)
                                .addComponent(servicetoerror))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(updateService)
                                .addComponent(serviceto)
                                .addComponent(changedName)
                                .addComponent(changedname2)
                                .addComponent(garage2)
                                .addComponent(garagename))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(durationnameerror))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(duration2)
                                .addComponent(durationname)
                                .addComponent(updateServiceButton))
                        
                        .addGroup(layout.createParallelGroup()
                        		.addComponent(newComboName)
                        		.addComponent(newMainServiceLabel)
                        		.addComponent(newComboItemLabel)
                        		.addComponent(newServiceComboNameText)
                        		.addComponent(mainServiceComboBox)
                        		.addComponent(newComboItemList))
                        .addGroup(layout.createParallelGroup()
                        		.addComponent(newMandatoryComboItem)
                        		.addComponent(newMandatoryList))
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

            garagename.removeAllItems();
            idx = 0;
            for (TOTechnician.TechnicianType a : TOTechnician.TechnicianType.values() ) {

                garagename.addItem(a);
                idx++;
            }
            
  
            

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
                    showUpdateAccountSection();
                    headerTitle.setText("Hi, " + CarShopController.getLoggedInUser() + "!");
                    logoutButton.setVisible(true);
                    if(CarShopController.isCustomerLoggedIn()) {
                        showAppointmentSection();
                    }
                    if(CarShopController.isOwnerLoggedIn()) {
                        showAddSection();
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
                hideAppointmentSection();
                headerTitle.setText("CarShop");
                logoutButton.setVisible(false);
                loginUsernameField.setText("");
                loginPasswordField.setText("");
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
        refreshData();
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

    private void addServiceButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        String serviceError = "";
        int strduration = 0;
        String name = null;
        String durationError = "";


        TOTechnician c = new TOTechnician((ca.mcgill.ecse223.carshop.controller.TOTechnician.TechnicianType) technician.getSelectedItem());
        TOGarage h = new TOGarage(c);

        if(error == null || error.length() == 0) {
            name = serviceName.getText();
            if(name.equals("")){
                serviceError = "Please enter a valid name";
                serviceErrorMessage.setText(serviceError);
            }
        }

        if(error==null || error.length()==0) {
            try {
                strduration = Integer.parseInt(duration.getText());
            }
            catch(NumberFormatException e) {
                durationError = "Please enter a valid duration";
                durationErrorMessage.setText(durationError);
            }
        }


        if(error==null || error.length()==0) {
            CarShopController.addServiceView(name, strduration, h);

        }

    }

    private void updateServiceButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedServices = serviceto.getSelectedIndex();
        String name = null;
        String changedName = null;
        int duration = 0;
        String changename2errortext = null;
        String durationnameerrortext = null;
        String g = serviceto.getSelectedItem().toString();
        String ser = null;

        TOTechnician c = new TOTechnician((ca.mcgill.ecse223.carshop.controller.TOTechnician.TechnicianType) technician.getSelectedItem());
        TOGarage h = new TOGarage(c);

        if(serviceto.getSelectedItem().toString().length()==0||serviceto.getSelectedItem()==null) {
            servicetoerror.setText("Choose a Service");
        }


        if(error == null || error.length() == 0) {
            name = changedname2.getText();
            if(name.equals("")){
                changename2errortext = "Please enter a valid name";
                changedname2error.setText(changename2errortext);
            }
        }

        if(error==null || error.length()==0) {
            try {
                duration = Integer.parseInt(durationname.getText());
            }
            catch(NumberFormatException e) {
                durationnameerrortext = "Please enter a valid duration";
                durationnameerror.setText(durationnameerrortext);
            }
        }


        if(error==null || error.length()==0){
            CarShopController.updateServiceView(g,changedname2.getText(),duration, h);
        }

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

        //service
        serviceName.setVisible(false);
        serviceNameLabel.setVisible(false);
        addServiceButton.setVisible(false);
        duration.setVisible(false);
        durationLabel.setVisible(false);
        garage.setVisible(false);
        technician.setVisible(false);

        //update Service
        updateService.setVisible(false);
        serviceto.setVisible(false);
        changedName.setVisible(false);
        changedname2.setVisible(false);
        garage2.setVisible(false);
        garagename.setVisible(false);
        duration2.setVisible(false);
        durationname.setVisible(false);
        updateServiceButton.setVisible(false);
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

        //service
        serviceName.setVisible(false);
        serviceNameLabel.setVisible(false);
        addServiceButton.setVisible(false);
        duration.setVisible(false);
        durationLabel.setVisible(false);
        garage.setVisible(false);
        technician.setVisible(false);

        //update Service
        updateService.setVisible(false);
        serviceto.setVisible(false);
        changedName.setVisible(false);
        changedname2.setVisible(false);
        garage2.setVisible(false);
        garagename.setVisible(false);
        duration2.setVisible(false);
        durationname.setVisible(false);
        updateServiceButton.setVisible(false);
    }

    private void showAddSection() {
        updateAccountTopSeparator.setVisible(false);
        updateAccount.setVisible(false);
        updateAccountErrorMessage.setVisible(false);
        updateAccountSuccessMessage.setVisible(false);
        updateUsername.setVisible(false);
        updatePassword.setVisible(false);
        updateUsernameField.setVisible(false);
        updatePasswordField.setVisible(false);
        updateAccountButton.setVisible(false);

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

        updateService.setVisible(true);
        serviceto.setVisible(true);
        changedName.setVisible(true);
        changedname2.setVisible(true);
        garage2.setVisible(true);
        garagename.setVisible(true);
        duration2.setVisible(true);
        durationname.setVisible(true);


        serviceName.setVisible(true);
        serviceNameLabel.setVisible(true);
        addServiceButton.setVisible(true);
        duration.setVisible(true);
        durationLabel.setVisible(true);
        garage.setVisible(true);
        technician.setVisible(true);
        updateServiceButton.setVisible(true);

    }


}