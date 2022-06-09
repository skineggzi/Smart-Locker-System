package SLC.SLSvr.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import SLC.BarcodeReaderDriver.Emulator.BarcodeReaderEmulator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Logger;

public class SLSvrEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private SLSvrEmulator slSvrEmulator;
    private MBox slsvrMBox;
    private String activationResp;
    private String standbyResp;
    private String pollResp;
    public TextField barcodeNumField;
    public TextField barcodeReaderStatusField;
    public TextArea barcodeReaderTextArea;
    public ChoiceBox standbyRespCBox;
    public ChoiceBox activationRespCBox;
    public ChoiceBox pollRespCBox;
    public Button btnSendBarcode;


    public void initialize(String id, AppKickstarter appKickstarter, Logger log, SLSvrEmulator slSvrEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.slSvrEmulator = slSvrEmulator;
        this.slsvrMBox = appKickstarter.getThread("SLSvr").getMBox();
        this.pollRespCBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pollResp = pollRespCBox.getItems().get(newValue.intValue()).toString();

            }
        });

        this.pollResp = pollRespCBox.getValue().toString();
    } // initialize

    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getText()) {
            case "Button1":
                slsvrMBox.send(new Msg(id, slsvrMBox, Msg.Type.setM_USED, ""));
                break;

            case "Button2":
                slsvrMBox.send(new Msg(id, slsvrMBox, Msg.Type.setM_EMPTY, ""));
                break;

            case "Button3":
                slsvrMBox.send(new Msg(id, slsvrMBox, Msg.Type.have_ML, ""));
                break;

            default:
                log.warning(id + ": unknown button: [" + btn.getText() + "]");
                break;
        }
    }

    public String getPollResp()       { return pollResp; }
}
