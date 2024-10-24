import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.WindowEvent;

import Panels.*;

public class mainApp extends JFrame implements AppInterface {
    public JPanel currentPanel;
    private MyMenu menuPage;

    public mainApp() {
        super();
        this.setLayout(null);
        this.setBounds(100, 100, 900, 500);
        this.getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPage = new MyMenu(this);
        menuPage.setLocation(-1000, -1000);
        menuPage.setVisible(false);
        add(menuPage);

    }

    public void destroy() {
        this.dispose();
    }

    public void displayMenu(String user) {
        this.remove(currentPanel);
        currentPanel = menuPage;
        menuPage.setVisible(true);
        menuPage.setLocation(0, 0);
    }

    public void call4x4() {
        App4_4 obj = new App4_4();
        currentPanel.setVisible(false);
        obj.setBounds(0, 0, 900, 500);
        currentPanel = obj;
        this.add(obj);
        revalidate();
        repaint();
    }

    public void call3x3() {
        App3_3 obj = new App3_3();
        currentPanel.setVisible(false);
        obj.setBounds(0, 0, 900, 500);
        currentPanel = obj;
        this.add(obj);
        revalidate();
        repaint();
    }

    public void showCreatePage() {
        CreateUser obj = new CreateUser(this);
        this.remove(currentPanel);
        obj.setBounds(0, 0, 900, 500);
        currentPanel = obj;
        add(obj);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        mainApp Window = new mainApp();
        // Login loginPage = new Login(Window);
        MyMenu menu = new MyMenu(Window);
        Window.currentPanel = menu;
        menu.setBounds(0, 0, 900, 500);
        Window.currentPanel = menu;
        Window.add(menu);
        Window.revalidate();
        Window.repaint();
    }

}
