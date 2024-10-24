package Panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.nio.file.Path;
import static java.lang.System.exit;

public class CreateUser extends JPanel {
    public Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    public JButton create;
    public Color bg = Color.BLUE;
    public JTextField namefield;
    public JTextField passwordfield;
    public JTextField retype;
    public Path filename = Path.of("");
    public boolean flag = false;
    public AppInterface parent;

    public CreateUser(AppInterface p) {
        super(null);
        parent = p;
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

    public boolean existinguser(String username) {
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

    public boolean storeUser() {
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