package SLC.OctopusReaderDriver.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


//======================================================================
// BarcodeReaderEmulatorController
public class OctopusReaderEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private OctopusReaderEmulator octopusReaderEmulator;
    private MBox octopusReaderMBox;
    private String activationResp;
    private String standbyResp;
    private String pollResp;
    public TextField octopusNumField;
    public TextField octopusReaderStatusField;
    public TextArea octopusReaderTextArea;
    public ChoiceBox standbyRespCBox;
    public ChoiceBox activationRespCBox;
    public ChoiceBox pollRespCBox;
    public Button btnSendOctopus;
    public String lockerID;


    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, OctopusReaderEmulator octopusReaderEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
	this.log = log;
	this.octopusReaderEmulator = octopusReaderEmulator;
	this.octopusReaderMBox = appKickstarter.getThread("OctopusReaderDriver").getMBox();
        this.activationRespCBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                activationResp = activationRespCBox.getItems().get(newValue.intValue()).toString();
                appendTextArea("Activation Response set to " + activationRespCBox.getItems().get(newValue.intValue()).toString());
            }
        });
        this.standbyRespCBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                standbyResp = standbyRespCBox.getItems().get(newValue.intValue()).toString();
                appendTextArea("Standby Response set to " + standbyRespCBox.getItems().get(newValue.intValue()).toString());
            }
        });
	this.pollRespCBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pollResp = pollRespCBox.getItems().get(newValue.intValue()).toString();
                appendTextArea("Poll Response set to " + pollRespCBox.getItems().get(newValue.intValue()).toString());
            }
        });
        this.activationResp = activationRespCBox.getValue().toString();
        this.standbyResp = standbyRespCBox.getValue().toString();
        this.pollResp = pollRespCBox.getValue().toString();
        this.goStandby();
    } // initialize


    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
	Button btn = (Button) actionEvent.getSource();

	switch (btn.getText()) {
	    case "Octopus 1":
	        octopusNumField.setText(appKickstarter.getProperty("OctopusReader.Octopus1"));
	        break;

	    case "Octopus 2":
            octopusNumField.setText(appKickstarter.getProperty("OctopusReader.Octopus2"));
		break;

	    case "Octopus 3":
            octopusNumField.setText(appKickstarter.getProperty("OctopusReader.Octopus3"));
		break;

	    case "Reset":
            octopusNumField.setText("");
		break;

	    case "Send Octopus":

            octopusReaderMBox.send(new Msg(id, octopusReaderMBox,Msg.Type.OR_OctopusRead,lockerID));
            octopusReaderTextArea.appendText("Sending octopus " + octopusNumField.getText()+"\n");
            btnSendOctopus.setDisable(true);
            octopusNumField.setText("");
            goStandby();
		break;

	    case "Activate/Standby":
            octopusReaderMBox.send(new Msg(id, octopusReaderMBox, Msg.Type.OR_GoActive, octopusNumField.getText()));
            octopusReaderTextArea.appendText("Removing card\n");
		break;

	    default:
	        log.warning(id + ": unknown button: [" + btn.getText() + "]");
		break;
	}
    } // buttonPressed


    //------------------------------------------------------------
    // getters
    public String getActivationResp() { return activationResp; }
    public String getStandbyResp()    { return standbyResp; }
    public String getPollResp()       { return pollResp; }


    //------------------------------------------------------------
    // goActive
    public void goActive(String lockerID) {
        btnSendOctopus.setDisable(false);
        updateOctopusReaderStatus("Active");
        this.lockerID = lockerID;
    } // goActive


    //------------------------------------------------------------
    // goStandby
    public void goStandby() {
        appendTextArea("Standby");
        updateOctopusReaderStatus("Standby");
    } // goStandby


    //------------------------------------------------------------
    // updateBarcodeReaderStatus
    private void updateOctopusReaderStatus(String status) {
        octopusReaderStatusField.setText(status);
    } // updateBarcodeReaderStatus


    //------------------------------------------------------------
    // appendTextArea
    public void appendTextArea(String status) {
        octopusReaderTextArea.appendText(status+"\n");
    } // appendTextArea
} // BarcodeReaderEmulatorController
