package movieTicketSystem.View;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieSelectionView extends JFrame {

    final int MOVIE_TAB_INDEX = 0;
    final int SIGNUP_TAB_INDEX = 1;
    final int PURCHASE_TAB_INDEX = 2;


    private JButton purchaseButton;
    private JButton showLoginFormButton;
    private JPanel mainPanel;

    private JLabel movieLabel;
    private JComboBox movieSelectorComboBox;
    private JComboBox theatreSelectionComboBox;
    private JLabel showtimeLabel;
    private JComboBox showtimeSelectorComboBox;
    private JPanel seatPanel;

    private JLabel screenLabel;
    private JPanel loginPanel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private JTabbedPane tabbedPane;
    private JPanel signupPanel;
    private JButton signupButton;
    private JButton leaveSignupButton;
    private JPanel purchaseTab;
    private boolean loginFormShowing;

    private int selectedSeatCount;


    // menu options
    ArrayList<String> movieOptions = new ArrayList<String>();
    ArrayList<String> theatreOptions = new ArrayList<String>();
    ArrayList<String> timeOptions = new ArrayList<String>();

    JButton[][] seats;
    boolean loggedIn;

    public void toggleLoginForm() {
        if (loginFormShowing) {
            loginPanel.setVisible(false);
            showLoginFormButton.setText("Show Login Form");
            this.setSize(new Dimension(720, 520));
        } else {
            loginPanel.setVisible(true);
            showLoginFormButton.setText("Hide Login Form");
            this.setSize(new Dimension(720, 620));
        }
        loginFormShowing = !loginFormShowing;
    }


    public MovieSelectionView() {

        // set default values
        loginPanel.setVisible(false);
        setView("main");
        setLoggedIn(false);
        seats = new JButton[10][10];
        purchaseButton.setEnabled(false);

        // general form setup
        dropdownMenuSetup();
        createSeats();


        // set up content pane
        setContentPane(tabbedPane);
        setTitle("Movie Selection Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        seatPanel.setLayout(new BoxLayout(seatPanel, BoxLayout.PAGE_AXIS));


        // add action listeners
        signupButton.addActionListener(new signupButtonListener());
        leaveSignupButton.addActionListener(new backToMainButtonListener());

        setVisible(true);
        this.setSize(new Dimension(720, 520));

    }

    private void dropdownMenuSetup() {
        // setup dropdown menus
        movieSelectorComboBox.setModel(new DefaultComboBoxModel(movieOptions.toArray()));
        movieSelectorComboBox.setFocusable(false);
        movieSelectorComboBox.setSelectedIndex(-1);
        movieSelectorComboBox.setEnabled(false);

        theatreSelectionComboBox.setModel(new DefaultComboBoxModel(theatreOptions.toArray()));
        theatreSelectionComboBox.setFocusable(false);
        theatreSelectionComboBox.setSelectedIndex(-1);
        theatreSelectionComboBox.setEnabled(false);

        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions.toArray()));
        showtimeSelectorComboBox.setFocusable(false);
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(false);
    }


    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;

        if (loggedIn) {
            loginButton.setText("Log Out");
            usernameTextField.setEnabled(false);
            passwordTextField.setEnabled(false);
            showLoginFormButton.setEnabled(false);
        } else {
            loginButton.setText("Log In");
            usernameTextField.setEnabled(true);
            passwordTextField.setEnabled(true);
            showLoginFormButton.setEnabled(true);
        }
    }

    public void createSeats() {
        int width = 60;
        int height = 30;

        for (int i = 0; i < 10; i++) {
            JPanel x = new JPanel();
            x.setLayout(new BoxLayout(x, BoxLayout.LINE_AXIS));
            for (int j = 0; j < 10; j++) {
                JButton btn = new JButton("" + (char) (i + 65) + (j));
                btn.setEnabled(false);
                btn.setMinimumSize(new Dimension(width, height));
                btn.setMaximumSize(new Dimension(width, height));

                btn.addActionListener(new seatButtonColorChangeListener());

                x.add(btn);
                seats[i][j] = btn;
            }
            seatPanel.add(x);
        }
        this.setVisible(true);
    }


    public void disableAllSeats() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                seats[i][j].setEnabled(false);
                seats[i][j].setBackground(null);
            }
        }
    }

    public void setSeats(int[][] showtimeSeats) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                seats[i][j].setEnabled(showtimeSeats[i][j] == 1);
            }
        }
        this.setVisible(true);
    }


    public void setTheatreOptions(ArrayList<String> theatreOptions) {
        this.theatreOptions = theatreOptions;
        theatreSelectionComboBox.setModel(new DefaultComboBoxModel(theatreOptions.toArray()));
        theatreSelectionComboBox.setSelectedIndex(-1);
        theatreSelectionComboBox.setEnabled(true);
    }

    public void setTimeOptions(ArrayList<String> timeOptions) {
        this.timeOptions = timeOptions;
        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions.toArray()));
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(true);
    }

    public void clearShowtimeOptions() {
        this.timeOptions = new ArrayList<String>();
        showtimeSelectorComboBox.setModel(new DefaultComboBoxModel(timeOptions.toArray()));
        showtimeSelectorComboBox.setSelectedIndex(-1);
        showtimeSelectorComboBox.setEnabled(false);
    }

    public void setMovieOptions(ArrayList<String> movieOptions) {
        this.movieOptions = movieOptions;
        movieSelectorComboBox.setModel(new DefaultComboBoxModel(movieOptions.toArray()));
        movieSelectorComboBox.setSelectedIndex(-1);
        movieSelectorComboBox.setEnabled(true);
    }

    public void setView(String viewName) {
        switch (viewName) {
            case "main":
                tabbedPane.setEnabledAt(MOVIE_TAB_INDEX, true);
                tabbedPane.setEnabledAt(SIGNUP_TAB_INDEX, false);
                tabbedPane.setEnabledAt(PURCHASE_TAB_INDEX, false);
                tabbedPane.setSelectedIndex(MOVIE_TAB_INDEX);
                break;
            case "signup":
                tabbedPane.setEnabledAt(MOVIE_TAB_INDEX, false);
                tabbedPane.setEnabledAt(SIGNUP_TAB_INDEX, true);
                tabbedPane.setEnabledAt(PURCHASE_TAB_INDEX, false);
                tabbedPane.setSelectedIndex(SIGNUP_TAB_INDEX);
                break;
            case "purchase":
                tabbedPane.setEnabledAt(MOVIE_TAB_INDEX, false);
                tabbedPane.setEnabledAt(SIGNUP_TAB_INDEX, false);
                tabbedPane.setEnabledAt(PURCHASE_TAB_INDEX, true);
                tabbedPane.setSelectedIndex(PURCHASE_TAB_INDEX);
                break;
        }
    }

    // getters and setters
    public String getMovieInput() {
        return (movieSelectorComboBox.getSelectedItem() == null) ? "null" : movieSelectorComboBox.getSelectedItem().toString();
    }

    public String getTheatreInput() {
        return (theatreSelectionComboBox.getSelectedItem() == null) ? "null" : theatreSelectionComboBox.getSelectedItem().toString();
    }

    public String getShowtimeInput() {
        return (showtimeSelectorComboBox.getSelectedItem() == null) ? "null" : showtimeSelectorComboBox.getSelectedItem().toString();
    }

    public String getUserName() {
        return this.usernameTextField.getText();
    }

    public String getPassword() {
        return this.passwordTextField.getText();
    }

    public JButton[][] getSeats() {
        return seats;
    }

    public int getSelectedSeatCount() {
        return selectedSeatCount;
    }

    public void incrementSelectedSeats() {
        this.selectedSeatCount++;
    }

    public void decrementSelectedSeats() {
        this.selectedSeatCount--;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }



    // ******************* ACTION LISTENERS **************************
    public void addMovieComboBoxActionListener(ActionListener a) {
        movieSelectorComboBox.addActionListener(a);
    }

    public void removeMovieComboBoxActionListener(ActionListener a) {
        movieSelectorComboBox.removeActionListener(a);
    }

    public void addTheatreComboBoxActionListener(ActionListener a) {
        theatreSelectionComboBox.addActionListener(a);
    }

    public void removeTheatreComboBoxActionListener(ActionListener a) {
        theatreSelectionComboBox.removeActionListener(a);
    }

    public void addShowtimeComboBoxActionListener(ActionListener a) {
        showtimeSelectorComboBox.addActionListener(a);
    }

    public void removeShowtimeComboBoxActionListener(ActionListener a) {
        showtimeSelectorComboBox.removeActionListener(a);
    }

    public void addPurchaseButtonActionListener(ActionListener a) {
        purchaseButton.addActionListener(a);
    }

    public void addShowLoginButtonActionListener(ActionListener a) {
        showLoginFormButton.addActionListener(a);
    }

    public void addLoginButtonListener(ActionListener a) {
        loginButton.addActionListener(a);
    }


    class seatButtonColorChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton x = (JButton) e.getSource();
            if (x.getBackground() == Color.green) {
                x.setBackground(null);
                decrementSelectedSeats();
            } else {
                x.setBackground(Color.green);
                incrementSelectedSeats();
            }

            // enable/disable purchase button based on selected seat count
            purchaseButton.setEnabled(getSelectedSeatCount() > 0);
        }
    }

    class signupButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setView("signup");
        }
    }

    class backToMainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setView("main");
        }
    }


    // INTELLIJ CODE DO NOT TOUCH

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        tabbedPane = new JTabbedPane();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMinimumSize(new Dimension(600, 168));
        mainPanel.setPreferredSize(new Dimension(600, 168));
        tabbedPane.addTab("Movie Selection", mainPanel);
        movieLabel = new JLabel();
        movieLabel.setText("  Movie");
        mainPanel.add(movieLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("  Theatre");
        mainPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        purchaseButton = new JButton();
        purchaseButton.setText("Purchase");
        panel1.add(purchaseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showLoginFormButton = new JButton();
        showLoginFormButton.setText("Show Log In Form");
        panel1.add(showLoginFormButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        signupButton = new JButton();
        signupButton.setText("Sign Up");
        panel1.add(signupButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        movieSelectorComboBox = new JComboBox();
        mainPanel.add(movieSelectorComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        theatreSelectionComboBox = new JComboBox();
        mainPanel.add(theatreSelectionComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showtimeLabel = new JLabel();
        showtimeLabel.setText("  Showtime");
        mainPanel.add(showtimeLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showtimeSelectorComboBox = new JComboBox();
        mainPanel.add(showtimeSelectorComboBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridBagLayout());
        mainPanel.add(seatPanel, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        screenLabel = new JLabel();
        screenLabel.setHorizontalAlignment(0);
        screenLabel.setHorizontalTextPosition(0);
        screenLabel.setText("Screen");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        seatPanel.add(screenLabel, gbc);
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(loginPanel, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        usernameTextField = new JTextField();
        loginPanel.add(usernameTextField, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordTextField = new JTextField();
        loginPanel.add(passwordTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Username");
        loginPanel.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Password");
        loginPanel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loginButton = new JButton();
        loginButton.setText("Log In");
        loginPanel.add(loginButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        signupPanel.setEnabled(true);
        tabbedPane.addTab("Sign Up", signupPanel);
        final JLabel label4 = new JLabel();
        label4.setText("Hello world");
        signupPanel.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        leaveSignupButton = new JButton();
        leaveSignupButton.setText("Return To Main Menu");
        signupPanel.add(leaveSignupButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        purchaseTab = new JPanel();
        purchaseTab.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Purchase", purchaseTab);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return tabbedPane;
    }

}
