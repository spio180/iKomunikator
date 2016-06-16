package client.core;

import common.Const;
import common.Message;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class ChatWindowController {
	public static volatile String loggedUserName = "";
	@FXML
	public Button butWyslij;
	@FXML
	public ListView<String> textChat;
	@FXML
	public ListView<String> userList;
	@FXML
	public TabPane tabChat;
	@FXML
	private Button butWyczysc;
	@FXML
	private TextField textToSend;
	private TcpClient tcpConnectionToServer;

	public void setTcpConnectionToServer(TcpClient connection) {
		tcpConnectionToServer = connection;
	}

	@FXML
	private void butWyczyscClick() {
		textToSend.clear();
	}

	@FXML
	private void OnListaUzytkownikowMouseClick(MouseEvent event) {
		if (this.tabChat != null) {
			if (userList.getSelectionModel().getSelectedItem() != null) {
				String uzytkownik = userList.getSelectionModel().getSelectedItem().toString();
				
				if (this.ContainsTab(uzytkownik)){
					for (Tab tab : this.tabChat.getTabs()) {
						if (Objects.equals(uzytkownik, tab.getText())) {
							this.tabChat.getSelectionModel().select(tab);
						}
					}
				}
				else{
					if (!Objects.equals(uzytkownik,loggedUserName)) {
						ListView<String> listView = new ListView<String>();
						listView.setId(uzytkownik.concat("_chat"));
						String leadMessage = "Rozpocznij czat z " + uzytkownik;
						listView.getItems().add(leadMessage);
						Tab tab = new Tab();
						tab.setText(uzytkownik);
						tab.setClosable(true);
						tab.setContent(listView);
						this.tabChat.getTabs().add(tab);
					}
				}
					
			}
		}
	}

	/*
	@FXML
	private void butWyslijClick() {

		String msg = loggedUserName.concat(" - ").concat(textToSend.getText());
		

		if (msg.length() != 0) {
			Message message = new Message();
			
			if (this.tabChat != null && this.tabChat.getTabs().size()>0) {
				int selectedIndex = this.tabChat.getSelectionModel().getSelectedIndex();
				Tab tab = this.tabChat.getTabs().get(selectedIndex);
				
				if (tab.getText() == "Wszyscy") {
					message.setType(Const.MSG_DO_WSZYSTKICH);
					message.setReceiver(tab.getText());
				}
				else {
					message.setType(Const.MSG_DO_UZYTKOWNIKA);
					message.setReceiver(Const.USER_ALL);
				}
				message.setType(Const.MSG_DO_WSZYSTKICH); /// �atka na komunikacj�
				message.addLineToMessageBody(Const.BODY, msg);			
				message.setSender(ChatWindowController.loggedUserName);
				tcpConnectionToServer.sendMessage(message);
				tcpConnectionToServer.listaListChatow.get(tab.getText()).add(msg);
				textChat.setItems(FXCollections.observableArrayList(tcpConnectionToServer.listaListChatow.get(tab.getText())));
				textToSend.clear();
			}
		}
	}
*/
	
	@FXML private void butWyslijClick(){
        wyslijWiadomosc();
    }

    private void wyslijWiadomosc() {

		String msg = textToSend.getText();
		

		if (msg.length() != 0) {
			Message message = new Message();
			
			if (this.tabChat != null && this.tabChat.getTabs().size()>0) {
				int selectedIndex = this.tabChat.getSelectionModel().getSelectedIndex();
				Tab tab = this.tabChat.getTabs().get(selectedIndex);
				
				if (tab.getText() == "Wszyscy") {
					message.setType(Const.MSG_DO_WSZYSTKICH);
					message.setReceiver(tab.getText());
				}
				else {
					message.setType(Const.MSG_DO_UZYTKOWNIKA);
					message.setReceiver(Const.USER_ALL);
				}
				message.setType(Const.MSG_DO_WSZYSTKICH); /// �atka na komunikacj�
				message.addLineToMessageBody(Const.BODY, msg);			
				message.setSender(ChatWindowController.loggedUserName);
				tcpConnectionToServer.sendMessage(message);
				tcpConnectionToServer.listaListChatow.get(tab.getText()).add(loggedUserName.concat(" - ").concat(msg));
				textChat.setItems(FXCollections.observableArrayList(tcpConnectionToServer.listaListChatow.get(tab.getText())));
				textToSend.clear();
			}
		}
    }
    
    
    @FXML public void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            wyslijWiadomosc();
        }
    }	
	
	
	public Boolean ContainsTab(String title) {
		for (Tab tab : this.tabChat.getTabs()) {
			if (Objects.equals(tab.getText(),title)) {
				return true;
			}
		}

		return false;
	}
}
