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
	private void validate() {
		if (Integer.parseInt(textPort.getText()) > 65535 || Integer.parseInt(textPort.getText()) < 1) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawny port w zakresie 1-65535");
		} else if (Integer.parseInt(textLimit.getText()) > 999 || Integer.parseInt(textLimit.getText()) < 1) {
			JOptionPane.showMessageDialog(null, "Wpisz poprawny limit połączeń w zakresie 1-999");
		}
	}

	private void saveToXml() {
		String ip = textIp.getText();
		int port = Integer.parseInt(textPort.getText());
		int limit = Integer.parseInt(textLimit.getText());
		XmlWriter writer = new XmlWriter(ip, port, limit, fWordsArray);
		try {
			writer.serializeServerConfig();
			JOptionPane.showMessageDialog(null, "Konfiguracjďż˝ poprawnie zapisano do pliku server.cfg");
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
		frmIkonfigurator.setTitle("Konfigurator serwera iKomunikator");
		frmIkonfigurator.setResizable(false);
		frmIkonfigurator.setBounds(100, 100, 900, 600);
		frmIkonfigurator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIkonfigurator.getContentPane().setLayout(null);

		JLabel lblIp = new JLabel("Adres IP");
		lblIp.setBounds(10, 11, 110, 30);
		frmIkonfigurator.getContentPane().add(lblIp);

		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(283, 11, 110, 30);
		frmIkonfigurator.getContentPane().add(lblPort);

		JLabel lblLimit = new JLabel("Limit Połączeń");
		lblLimit.setBounds(549, 11, 110, 30);
		frmIkonfigurator.getContentPane().add(lblLimit);

		textIp = new JTextField();
		textIp.setText(ip);
		textIp.setBounds(111, 16, 114, 20);
		frmIkonfigurator.getContentPane().add(textIp);
		textIp.setColumns(10);

		textPort = new JTextField();
		textPort.setText(port);
		textPort.setBounds(354, 16, 114, 20);
		frmIkonfigurator.getContentPane().add(textPort);
		textPort.setColumns(10);

		textLimit = new JTextField();
		textLimit.setText(limit);
		textLimit.setBounds(671, 16, 114, 20);
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
		list.setBounds(10, 73, 874, 454);
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
		btnZmien.setBounds(437, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnZmien);

		JButton btnUsun = new JButton("Usuń");
		btnUsun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				fWords.remove(index);
				textNewWord.setText("");
			}
		});
		btnUsun.setBounds(536, 538, 89, 23);
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

		btnDodaj.setBounds(338, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnDodaj);

		JButton btnZapisz = new JButton("Zapisz");
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jListToArray();
				validate();
				saveToXml();
			}
		});

		btnZapisz.setBounds(696, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnZapisz);

		JButton btnAnuluj = new JButton("Zamknij");
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmIkonfigurator.dispose();
			}
		});
		btnAnuluj.setBounds(795, 538, 89, 23);
		frmIkonfigurator.getContentPane().add(btnAnuluj);

		textNewWord = new JTextField();
		textNewWord.setBounds(79, 539, 237, 20);
		frmIkonfigurator.getContentPane().add(textNewWord);
		textNewWord.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Lista wyrażeń zabronionych:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 52, 215, 14);
		frmIkonfigurator.getContentPane().add(lblNewLabel);
		
		JLabel lblWyraenie = new JLabel("Wyrażenie:");
		lblWyraenie.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWyraenie.setBounds(10, 541, 77, 14);
		frmIkonfigurator.getContentPane().add(lblWyraenie);
	}
}
