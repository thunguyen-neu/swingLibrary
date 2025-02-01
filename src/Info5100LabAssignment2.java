import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import javax.imageio.ImageIO;

public class Info5100LabAssignment2 extends JFrame {

    // Declare components
    private JTextField firstNameField, lastNameField, ageField, phoneField, emailField;
    private JComboBox<String> genderComboBox;
    private JLabel photoLabel;
    private File selectedPhoto;
    private JButton submitButton, uploadButton;

    public Info5100LabAssignment2() {
        setTitle("Student Registration Form");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        addInputRow("First Name:", firstNameField = new JTextField(20));
        addInputRow("Last Name:", lastNameField = new JTextField(20));
        addInputRow("Gender:", genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"}));
        addInputRow("Age:", ageField = new JTextField(5));
        addInputRow("Phone Number:", phoneField = new JTextField(10));
        addInputRow("Email Address:", emailField = new JTextField(20));
        
        JPanel photoPanel = new JPanel();
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.X_AXIS)); // Horizontal layout for photo and button
        photoPanel.add(new JLabel("Upload Photo (Optional):"));
        uploadButton = new JButton("Choose Photo");
        photoPanel.add(uploadButton);
        photoLabel = new JLabel();
        photoPanel.add(photoLabel);
        add(photoPanel);

        submitButton = new JButton("Submit");
        add(submitButton);

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadPhoto();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitProfile();
            }
        });

        setVisible(true);
    }

    private void addInputRow(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); 
        panel.add(new JLabel(labelText)); 
        panel.add(inputField);
        add(panel);
    }

    private void uploadPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedPhoto = fileChooser.getSelectedFile();
            try {
                Image img = ImageIO.read(selectedPhoto);
                ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                photoLabel.setIcon(icon);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

private void submitProfile() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String ageText = ageField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || ageText.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required (except for the photo).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age = 0;
        try {
            age = Integer.parseInt(ageText);
            if (age < 18 || age > 100) {
                JOptionPane.showMessageDialog(this, "Age must be between 18 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Phone Number (must be 10 digits)
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone number must be 10 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Email
        if (!email.matches("[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Profile Information String
        String gender = (String) genderComboBox.getSelectedItem();
        String profileInfo = "Name: " + firstName + " " + lastName + "\n" +
                             "Gender: " + gender + "\n" +
                             "Age: " + age + "\n" +
                             "Phone Number: " + phone + "\n" +
                             "Email Address: " + email;

        // Show Profile Information with photo
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.X_AXIS)); // Horizontal alignment for photo and text

        // Create panel for text info
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Vertical layout for text
        textPanel.add(new JLabel("Name: " + firstName + " " + lastName));
        textPanel.add(new JLabel("Gender: " + gender));
        textPanel.add(new JLabel("Age: " + age));
        textPanel.add(new JLabel("Phone Number: " + phone));
        textPanel.add(new JLabel("Email Address: " + email));

        // Add the text panel and photo panel to the profile panel
        profilePanel.add(textPanel);

        if (selectedPhoto != null) {
            try {
                Image img = ImageIO.read(selectedPhoto);
                JLabel imageLabel = new JLabel(new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                profilePanel.add(imageLabel);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Show the profile information in a dialog box
        JOptionPane.showMessageDialog(this, profilePanel, "Profile Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Info5100LabAssignment2();
    }
}