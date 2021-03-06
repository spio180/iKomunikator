package client.core;

import common.Const;
import common.Message;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChatWindowController {
    public static volatile String loggedUserName = "";
    @FXML public Button butWyslij;
    @FXML public ListView<String> textChat;
    @FXML public ListView<String> userList;
    @FXML public TabPane tabChat;
    @FXML private Button butWyczysc;
    @FXML private TextField textToSend;
    private TcpClient tcpConnectionToServer;

    public void setTcpConnectionToServer(TcpClient connection){
        tcpConnectionToServer = connection;
    }

    @FXML private void butWyczyscClick(){
        textToSend.clear();
    }

    @FXML private void butWyslijClick(){
        String msg = textToSend.getText();
        String msgFull = ChatWindowController.loggedUserName.concat(" - ").concat(msg);

        if (msg.length() != 0)
        {
            Message message = new Message();
            message.addLineToMessageBody(Const.BODY, msg);
            message.setReceiver(Const.USER_ALL);
            message.setSender(ChatWindowController.loggedUserName);
            message.setType(Const.MSG_DO_WSZYSTKICH);

            tcpConnectionToServer.sendMessage(message);
            tcpConnectionToServer.listOfCom.add(msgFull);
            System.out.println(tcpConnectionToServer.listOfCom.size());
            textChat.setItems(FXCollections.observableArrayList(tcpConnectionToServer.listOfCom));
            textChat.scrollTo(tcpConnectionToServer.listOfCom.size()-1);
            textToSend.clear();
        }
    }

    public Boolean ContainsTab(String title) {
        Boolean result = false;

        for (Tab tab : this.tabChat.getTabs()) {
            if (tab.getText().toUpperCase() == title.toUpperCase()) {
                result = true;
            }
        }

        return result;
    }
}