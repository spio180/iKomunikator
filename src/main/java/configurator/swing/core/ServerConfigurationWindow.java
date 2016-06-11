package iKomunikator_server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerConfigurationWindow {

	private JFrame frame;
	private JTextField textIp;
	private JTextField textPort;
	private JTextField textLimit;
	private JTextField textNewWord;
	private String mConfigFilePath = "server.cfg";
	private String ip;
	private String port;
	private String limit;
	// private String[] fWords = { "hitler", "litwa" };
	//private static List<String> listWords = new ArrayList<String>();
//	private String[] forbiddenWords;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerConfigurationWindow window = new ServerConfigurationWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public ServerConfigurationWindow() {
		XmlReader reader = new XmlReader();
		reader.deserializeConfigFromFile(mConfigFilePath);
		this.ip = reader.getIp();
		this.port = reader.getPort();
		this.limit = reader.getLimit();

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void validate() {
		if (Integer.parseInt(textPort.getText()) > 65535 || Integer.parseInt(textPort.getText()) < 1) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawny port w zakresie 1-65535");
		} else if (Integer.parseInt(textLimit.getText()) > 999 || Integer.parseInt(textLimit.getText()) < 1) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawny limit po³¹czeñ w zakresie 1-999");
		}
	}

	private void saveToXml() {
		String ip = textIp.getText();
		int port = Integer.parseInt(textPort.getText());
		int limit = Integer.parseInt(textLimit.getText());
		XmlWriter writer = new XmlWriter(ip, port, limit);
		try {
			writer.serializeServerConfig();
			JOptionPane.showMessageDialog(null, "Konfiguracjê poprawnie zapisano do pliku server.cfg");
		} catch (Exception e) {
			System.out.printf("Error saving the config file\n");
			if (Main.debug == true) {
				e.printStackTrace();
			}
		}
	}

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

		JLabel lblLimit = new JLabel("Limit Po\u0142\u0105cze\u0144");
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

		DefaultListModel<String> fWords = new DefaultListModel<String>();
		fWords.addElement("ktury");

		JList<String> list = new JList<String>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String selected = list.getSelectedValue().toString();
				textNewWord.setText(selected);
			}
		});
		list.setModel(fWords);
		list.setBounds(10, 73, 874, 454);
		frame.getContentPane().add(list);

		JButton btnZmien = new JButton("Zmie\u0144");
		btnZmien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				fWords.setElementAt(textNewWord.getText(), index);
			}
		});
		btnZmien.setBounds(346, 538, 89, 23);
		frame.getContentPane().add(btnZmien);

		JButton btnUsun = new JButton("Usu\u0144");
		btnUsun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				fWords.remove(index);
				textNewWord.setText("");
			}
		});
		btnUsun.setBounds(445, 538, 89, 23);
		frame.getContentPane().add(btnUsun);

		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fWords.addElement(textNewWord.getText());
				textNewWord.setText("");
			}
		});

		btnDodaj.setBounds(247, 538, 89, 23);
		frame.getContentPane().add(btnDodaj);

		JButton btnZapisz = new JButton("Zapisz");
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validate();
				saveToXml();
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

		textNewWord = new JTextField();
		textNewWord.setBounds(10, 539, 159, 20);
		frame.getContentPane().add(textNewWord);
		textNewWord.setColumns(10);
	}
}
