package Panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

import static java.lang.System.exit;

class Login extends JPanel {
    Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    JButton create;
    JButton login;
    Color bg = Color.BLUE;
    JTextField namefield;
    JTextField passwordfield;
    JLabel namelabel, passwordlabel;
    JFrame parent;

    public Login(JFrame parent) {
        super(null);
        this.parent = parent;
        setSize(900, 500);
        setBackground(bg);
        setLayout(null);
        create = new JButton("Create Account");
        login = new JButton("Login");
        create.setBounds(325, 400, 100, 30);
        create.setBorder(solidBorder);
        create.setBackground(Color.YELLOW);
        login.setBounds(475, 400, 100, 30);
        login.setBorder(solidBorder);
        login.setBackground(Color.YELLOW);
        add(create);
        add(login);
        namelabel = new JLabel("Enter Username", 0);
        passwordlabel = new JLabel("Enter Password", 0);
        namefield = new JTextField();
        passwordfield = new JTextField();

        namelabel.setFont(new Font("Times New Roman", 1, 25));
        namefield.setFont(new Font("Times New Roman", 1, 25));
        passwordfield.setFont(new Font("Times New Roman", 1, 25));
        passwordlabel.setFont(new Font("Times New Roman", 1, 25));

        namelabel.setBackground(Color.YELLOW);
        passwordlabel.setBackground(Color.YELLOW);
        namelabel.setForeground(Color.BLACK);
        passwordlabel.setForeground(Color.BLACK);
        namelabel.setOpaque(true);
        passwordlabel.setOpaque(true);

        namelabel.setBounds(150, 150, 300, 50);
        passwordlabel.setBounds(150, 230, 300, 50);
        namefield.setBounds(500, 150, 300, 50);
        passwordfield.setBounds(500, 230, 300, 50);
        add(namefield);
        add(passwordfield);
        add(passwordlabel);
        add(namelabel);
        login.addActionListener(e -> {
            // JButton clickedButton = (JButton) e.getSource();
            final String username = namefield.getText();
            final String password = passwordfield.getText();
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            checkUser(username, password);

        });
        create.addActionListener(e -> {
            // JButton clickedButton = (JButton) e.getSource();
            Component n = parent.getComponentAt(-1000, -1000);
            JPanel n1 = (JPanel) n;
            login.setLocation(-1000, -1000);
            n1.setLocation(1, 1);
        });
    }

    boolean checkUser(String username, String password) {
        try {
            String u = "", p = "";
            int flag = 0;
            File userfile = new File("C:\\Users\\Sriniv\\OneDrive - SSN-Institute\\New folder\\OOPS\\miniproj");
            Scanner in = new Scanner(userfile);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
                int i = 0, beg = 0;
                while (line.charAt(i) != '\0') {
                    if (line.charAt(i) == ',') {
                        u = line.substring(beg, i);
                        System.out.println(u);
                        p = line.substring(i + 1);
                        System.out.println(p);
                        break;
                    }
                    i++;
                }
                if (u.equals(username) && p.equals(password)) {
                    flag = 1;
                } else {
                    flag = 0;
                }
            }
            if (flag == 1) {
                System.out.println("User Exists, go to main menu");
                return true;
            } else {
                System.out.println("Wrong Password");
                return false;
            }
        } catch (FileNotFoundException f) {
            System.out.println("File Not Found");
            exit(0);
            return false;
        }
    }
}

class NewUser extends JPanel {
    Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    JButton create;
    Color bg = Color.BLUE;
    JTextField namefield;
    JTextField passwordfield;
    JTextField retype;
    Path filename = Path.of("");
    boolean flag = false;

    NewUser() {
        super(null);
        setSize(900, 500);
        setBackground(bg);
        setLayout(null);
        create = new JButton("Create Account");
        create.setBounds(325, 400, 100, 30);
        create.setBorder(solidBorder);
        create.setBackground(Color.YELLOW);
        add(create);
        namefield = new JTextField("Enter Username");
        passwordfield = new JTextField("Enter Password");
        namefield.setBounds(300, 200, 300, 30);
        passwordfield.setBounds(300, 250, 300, 30);
        retype = new JTextField("Enter Retype");
        retype.setBounds(300, 300, 300, 30);
        add(namefield);
        add(passwordfield);
        add(retype);
        create.addActionListener(e -> {
            // JButton clickedButton = (JButton) e.getSource();
            flag = storeUser();
        });
    }

    boolean existinguser(String username) {
        try {
            String u = "", p = "";
            int flag = 0;
            File userfile = new File("");
            Scanner in = new Scanner(userfile);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
                int i = 0, beg = 0;
                while (line.charAt(i) != '\0') {
                    if (line.charAt(i) == ',') {
                        u = line.substring(beg, i);
                        System.out.println(u);
                        p = line.substring(i + 1);
                        System.out.println(p);
                        break;
                    }
                    i++;
                }
                if (u.equals(username)) {
                    flag = 1;
                } else {
                    flag = 0;
                }
            }
            if (flag == 1) {
                System.out.println("User Already exists");
                return true;
            } else {
                System.out.println("Proceed");
                return false;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    boolean storeUser() {
        final String username = namefield.getText();
        final String password = passwordfield.getText();
        final String retpass = retype.getText();
        boolean ch = false;
        if (existinguser(username))
            return false;
        if (!password.equals(retpass)) {
            System.out.println("Incorrect input");
            ch = false;
        } else {
            String info = username + "," + password;
            File filename = new File("assets\\users.txt");
            try {
                if (filename.exists() == false) {
                    System.out.println("We had to make a new file.");
                    filename.createNewFile();
                }
                PrintWriter out = new PrintWriter(new FileWriter(filename, true));
                out.append(info + "\n");
                out.close();
                ch = true;
            } catch (IOException i) {
                System.out.println("error");
                exit(0);
            }
        }
        return ch;
    }
}

public class logincreatetemp {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();
        Login login = new Login(mainWindow);
        mainWindow.setLayout(null);
        login.setLocation(1, 1);
        mainWindow.add(login);
        mainWindow.setSize(900, 500);
        mainWindow.setVisible(true);
        mainWindow.setResizable(false);
        NewUser n = new NewUser();
        n.setBounds(-1000, -1000, 900, 500);
        mainWindow.add(n);
        /*
         * if(login.create.isEnabled())
         * {
         * login.setLocation(-1000,-1000);
         * n.setLocation(1,1);
         * }
         */
        // mainWindow.dispatchEvent(new WindowEvent(mainWindow,
        // WindowEvent.WINDOW_CLOSING));
        /*
         * mainWindow.add(app);
         * mainWindow.setSize(380,450);
         * mainWindow.setVisible(true);
         */
    }
}