package configurator.swing.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Okno1 {

    private static String[] slowa_zakazane = {"motyla noga", "ale urwał", "kurde bele"};
    private JFrame frame;
    private JTextField textIp;
    private JTextField textPort;
    private JTextField textLimit;
    private String mConfigFilePath = "server.cfg";
    private String ip;
    private String port;
    private String limit;

    /**
     * Create the application.
     */


    public Okno1() {
        XmlReader reader = new XmlReader();
        reader.deserializeConfigFromFile(mConfigFilePath);
        this.ip = reader.getIp();
        this.port = reader.getPort();
        this.limit = reader.getLimit();
        initialize();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Okno1 window = new Okno1();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */

    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblIp = new JLabel("Adres IP");
        lblIp.setBounds(10, 11, 110, 30);
        frame.getContentPane().add(lblIp);

        JLabel lblPort = new JLabel("Port");
        lblPort.setBounds(283, 11, 110, 30);
        frame.getContentPane().add(lblPort);

        JLabel lblLimit = new JLabel("Limit Połączeń");
        lblLimit.setBounds(549, 11, 110, 30);
        frame.getContentPane().add(lblLimit);

        textIp = new JTextField();
        textIp.setText(ip);
        textIp.setBounds(111, 16, 114, 20);
        frame.getContentPane().add(textIp);
        textIp.setColumns(10);

        textPort = new JTextField();
        textPort.setText(port);
        textPort.setBounds(354, 16, 114, 20);
        frame.getContentPane().add(textPort);
        textPort.setColumns(10);

        textLimit = new JTextField();
        textLimit.setText(limit);
        textLimit.setBounds(671, 16, 114, 20);
        frame.getContentPane().add(textLimit);
        textLimit.setColumns(10);

        JButton btnZmien = new JButton("Zmień");
        btnZmien.setBounds(346, 538, 89, 23);
        frame.getContentPane().add(btnZmien);

        JButton btnUsun = new JButton("Usuń");
        btnUsun.setBounds(445, 538, 89, 23);
        frame.getContentPane().add(btnUsun);

        JButton btnOdswiez = new JButton("Odświeżyć");
        btnOdswiez.setBounds(544, 538, 89, 23);
        frame.getContentPane().add(btnOdswiez);

        JButton btnDodaj = new JButton("Dodaj");
        btnDodaj.setBounds(247, 538, 89, 23);
        frame.getContentPane().add(btnDodaj);

        JButton btnZapisz = new JButton("Zapisz");
        btnZapisz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if (Integer.parseInt(textPort.getText()) > 65535) {
                    JOptionPane.showMessageDialog(null, "Wpisz poprawny port w zakresie 1-65535");
                } else if (Integer.parseInt(textLimit.getText()) > 999) {
                    JOptionPane.showMessageDialog(null, "Wpisz poprawny limit połączeń w zakresie 1-999");
                } else {
                    String ip = textIp.getText();
                    int port = Integer.parseInt(textPort.getText());
                    int limit = Integer.parseInt(textLimit.getText());
                    XmlWriter1 writer = new XmlWriter1(ip, port, limit);
                    try {
                        writer.serializeServerConfig();
                        JOptionPane.showMessageDialog(null, "Konfigurację poprawnie zapisano do pliku server.cfg");
                    } catch (Exception e) {
                        System.out.printf("Error saving the config file\n");
                        e.printStackTrace();
                    }

                }

            }
        });
        btnZapisz.setBounds(696, 538, 89, 23);
        frame.getContentPane().add(btnZapisz);

        JButton btnAnuluj = new JButton("Anuluj");
        btnAnuluj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
            }
        });
        btnAnuluj.setBounds(795, 538, 89, 23);
        frame.getContentPane().add(btnAnuluj);

        JList list = new JList(slowa_zakazane);
        list.setBounds(10, 73, 874, 454);
        frame.getContentPane().add(list);
    }
}
