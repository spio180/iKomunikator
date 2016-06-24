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
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class ServerConfigurationWindow {

	private JFrame frmIkonfigurator;
	private JTextField textIp;
	private JTextField textPort;
	private JTextField textLimit;
	private JTextField textNewWord;
	private String mConfigFilePath = "server.cfg";
	private String ip;
	private String port;
	private String limit;
	private String[] fWordsArray;
	private DefaultListModel<String> fWords = new DefaultListModel<String>();



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerConfigurationWindow window = new ServerConfigurationWindow();
					window.frmIkonfigurator.setVisible(true);
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
		this.fWords = reader.getfWords();

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	/*
	private boolean validate() {
		boolean result = true;
		
		
		if (Integer.parseInt(textPort.getText()) > 65535 || Integer.parseInt(textPort.getText()) < 1) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawny port w zakresie 1-65535");
		} else if (Integer.parseInt(textLimit.getText()) > 999 || Integer.parseInt(textLimit.getText()) < 1) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawny limit połączeń w zakresie 1-999");
		}
	}
	*/
	private boolean validate() {
		boolean result = true;
		String validIpMask = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		if (result && this.textIp.getText().length()==0) {
			result = false;
			JOptionPane.showMessageDialog(null, "Podaj Adres IP!");
			this.textIp.requestFocus();
		}

		if (result && !this.textIp.getText().matches(validIpMask)) {
			result = false;
			JOptionPane.showMessageDialog(null, "Poprawny adres to: [0-255].[0-255].[0-255].[0-255] !");
			this.textIp.requestFocus();
		}

		if (result && this.textPort.getText().length()==0) {
			result = false;
			JOptionPane.showMessageDialog(null, "Podaj numer portu serwera [0-65535] !");
			this.textPort.requestFocus();
		}

		if (result && !this.textPort.getText().matches("[0-9]+")) {
			result = false;
			JOptionPane.showMessageDialog(null, "Niepoprawny numer portu !\nWpisz wartość z przedziału 0-65535 !");
			this.textPort.requestFocus();
		}
		
		if (result && this.textPort.getText().length()==0) {
			result = false;
			JOptionPane.showMessageDialog(null, "Niepoprawny numer portu !\nWpisz wartość z przedziału 0-65535 !");
			this.textPort.requestFocus();
		}

		if (result && this.textPort.getText().length()>5) {
			result = false;
			JOptionPane.showMessageDialog(null, "Niepoprawny numer portu !\nWpisz wartość z przedziału 0-65535 !");
			this.textPort.requestFocus();
		}

		if (result && (Integer.parseInt(this.textPort.getText())<0 || Integer.parseInt(this.textPort.getText())>65535)) {
			result = false;
			JOptionPane.showMessageDialog(null, "Niepoprawny numer portu !\nWpisz wartość z przedziału 0-65535 !");
			this.textPort.requestFocus();
		}
		
		if (result && this.textLimit.getText().length()==0) {
			result = false;
			JOptionPane.showMessageDialog(null, "Niepoprawny limit połączeń !\nWpisz wartość z przedziału 0-999 !");
			this.textLimit.requestFocus();
		}

		if (result && !this.textLimit.getText().matches("[0-9]+")) {
			result = false;
			JOptionPane.showMessageDialog(null, "Niepoprawny limit połączeń !\nWpisz warto�� z przedzia�u 0-999 !");
			this.textLimit.requestFocus();
		}

		if (result && this.textLimit.getText().length()>3) {
			result = false;
			JOptionPane.showMessageDialog(null, "Niepoprawny limit połączeń !\nWpisz warto�� z przedzia�u 0-999 !");
			this.textLimit.requestFocus();
		}

		if (result && (Integer.parseInt(this.textLimit.getText())<0 || Integer.parseInt(this.textLimit.getText())>999)) {
			result = false;
			JOptionPane.showMessageDialog(null, "Niepoprawny limit połączeń !\nWpisz warto�� z przedzia�u 0-999 !");
			this.textLimit.requestFocus();
		}

		return result;
	}

	private void saveToXml() {
		String ip = textIp.getText();
		int port = Integer.parseInt(textPort.getText());
		int limit = Integer.parseInt(textLimit.getText());
		XmlWriter writer = new XmlWriter(ip, port, limit, fWordsArray);
		try {
			writer.serializeServerConfig();
			JOptionPane.showMessageDialog(null, "Konfigurację poprawnie zapisano do pliku server.cfg");
		} catch (Exception e) {
			System.out.printf("Error saving the config file\n");
			//if (Main.debug == true) {
			//	e.printStackTrace();
			//}
		}
	}
	
	private void jListToArray(){
		fWordsArray = new String[fWords.size()];
		for (int i=0; i < fWords.size(); i++){
			fWordsArray[i]=fWords.get(i).toString();
		}
	}

	private void initialize() {
		frmIkonfigurator = new JFrame();
		frmIkonfigurator.setResizable(false);
		frmIkonfigurator.setTitle("Konfigurator serwera iKomunikator");
		frmIkonfigurator.setBounds(100, 100, 800, 600);
		frmIkonfigurator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIkonfigurator.getContentPane().setLayout(null);

		JLabel lblIp = new JLabel("Adres IP");
		lblIp.setBounds(10, 11, 110, 30);
		frmIkonfigurator.getContentPane().add(lblIp);

		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(218, 11, 110, 30);
		frmIkonfigurator.getContentPane().add(lblPort);

		JLabel lblLimit = new JLabel("Limit Połączeń");
		lblLimit.setBounds(393, 11, 110, 30);
		frmIkonfigurator.getContentPane().add(lblLimit);

		textIp = new JTextField();
		textIp.setText(ip);
		textIp.setBounds(62, 16, 114, 20);
		frmIkonfigurator.getContentPane().add(textIp);
		textIp.setColumns(10);

		textPort = new JTextField();
		textPort.setText(port);
		textPort.setBounds(246, 16, 114, 20);
		frmIkonfigurator.getContentPane().add(textPort);
		textPort.setColumns(10);

		textLimit = new JTextField();
		textLimit.setText(limit);
		textLimit.setBounds(481, 16, 114, 20);
		frmIkonfigurator.getContentPane().add(textLimit);
		textLimit.setColumns(10);

//		fWords.addElement("ktury");

		final JList<String> list = new JList<String>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String selected = list.getSelectedValue().toString();
				textNewWord.setText(selected);
			}
		});
		list.setModel(fWords);
		list.setBounds(10, 73, 764, 423);
		frmIkonfigurator.getContentPane().add(list);

		JButton btnZmien = new JButton("Zmień");
		btnZmien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textNewWord.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Wprowadź wyrażenie !");
				} else if (textNewWord.getText().length() > 50) {
					JOptionPane.showMessageDialog(null, "Maksymalna długość wyrażenia to 50 znaków!");
				} else {
					int index = list.getSelectedIndex();
					fWords.setElementAt(textNewWord.getText(), index);
					textNewWord.setText("");
				}

			}
		});
		btnZmien.setBounds(178, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnZmien);

		JButton btnUsun = new JButton("Usuń");
		btnUsun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				fWords.remove(index);
				textNewWord.setText("");
			}
		});
		btnUsun.setBounds(277, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnUsun);

		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textNewWord.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Wprowadź wyrażenie !");
				} else if (textNewWord.getText().length() > 50) {
					JOptionPane.showMessageDialog(null, "Maksymalna długość wyrażenia to 50 znaków!");
				} else {
					fWords.addElement(textNewWord.getText());
					textNewWord.setText("");
				}

			}
		});

		btnDodaj.setBounds(79, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnDodaj);

		JButton btnZapisz = new JButton("Zapisz");
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jListToArray();
				if (validate()) {
					saveToXml();
				}
			}
		});

		btnZapisz.setBounds(586, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnZapisz);

		JButton btnAnuluj = new JButton("Zamknij");
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmIkonfigurator.dispose();
			}
		});
		btnAnuluj.setBounds(685, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnAnuluj);

		textNewWord = new JTextField();
		textNewWord.setBounds(79, 507, 287, 20);
		frmIkonfigurator.getContentPane().add(textNewWord);
		textNewWord.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Lista wyrażeń zabronionych:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 52, 215, 14);
		frmIkonfigurator.getContentPane().add(lblNewLabel);
		
		JLabel lblWyraenie = new JLabel("Wyrażenie:");
		lblWyraenie.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWyraenie.setBounds(10, 509, 77, 14);
		frmIkonfigurator.getContentPane().add(lblWyraenie);
	}
}
