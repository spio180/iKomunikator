package configurator.javafx.core;

import common.Serialization;
import common.ServerConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class MainWindow {
    private final String mPathName = "src/main/resources/server/server.cfg.bak";
    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnRemove;

    @FXML
    private TextField txtIP;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtConnectionLimit;

    @FXML
    private TableView<String> tbvZabronione;

    public MainWindow() {

    }

    @FXML
    private void OnOkMouseClicked() {
        this.WriteToConfig();
    }

    @FXML
    private void OnCancelMouseClicked() {
        Stage stage = (Stage) this.btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void OnRefreshMouseClicked() throws ParserConfigurationException, SAXException {
        this.ReadConfig();
    }

    @FXML
    private void OnAddMouseClicked() {
    }

    @FXML
    private void OnUpdateMouseClicked() {
    }

    @FXML
    private void OnRemoveMouseClicked() {
    }

    private void WriteToConfig() {
        try {
            ServerConfig conf = new ServerConfig();
            String applicationPath = new File(mPathName).getCanonicalPath();
            conf.setServerIp(this.txtIP.getText());
            conf.setPortNumber(Integer.parseInt(this.txtPort.getText()));
            conf.setConnectionLimit(Integer.parseInt(this.txtConnectionLimit.getText()));
            Serialization.SerializeServerConfig(conf, applicationPath);
            Stage stage = (Stage) this.btnCancel.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void ReadConfig() throws ParserConfigurationException, SAXException {
        try {
            ServerConfig conf = new ServerConfig();
            String applicationPath = new File(mPathName).getCanonicalPath();
            Serialization.DeserializeServerConfig(applicationPath, conf);
            this.txtConnectionLimit.setText(Integer.toString(conf.getConnectionLimit()));
            this.txtPort.setText(Integer.toString(conf.getPortNumber()));
            this.txtIP.setText(conf.getServerIp());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
