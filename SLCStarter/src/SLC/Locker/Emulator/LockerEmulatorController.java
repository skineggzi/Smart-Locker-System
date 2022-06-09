package SLC.Locker.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.logging.Logger;

public class LockerEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private LockerEmulator lockerEmulator;
    private MBox lockerDisplayMBox;
    private String pollResp;
    public ChoiceBox pollRespCBox;
    public Button closeDoorBtn;
    public String lockerId;
    public TextField lockerIDField;
    public TextField isDoorOpenField;
    public int who;

    public void initialize(String id, AppKickstarter appKickstarter, Logger log, LockerEmulator lockerEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.lockerEmulator = lockerEmulator;
        this.lockerDisplayMBox = appKickstarter.getThread("LockerDriver").getMBox();
        //this.pollRespCBox.setValue(this.pollResp);
        this.pollRespCBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pollResp = pollRespCBox.getItems().get(newValue.intValue()).toString();
            }
        });
        this.pollResp = pollRespCBox.getValue().toString();
    }

    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        //TODO
        switch (btn.getText()) {
            case "Close Door":
                if(who==0){
                    lockerDisplayMBox.send(new Msg(id, lockerDisplayMBox, Msg.Type.L_CloseByCustomer, lockerId));
                }else if(who==1){
                    lockerDisplayMBox.send(new Msg(id, lockerDisplayMBox, Msg.Type.L_CloseByDelivery, lockerId));
                }

                //reset
                closeDoorBtn.setDisable(true);
                lockerIDField.setText("--");
                isDoorOpenField.setText("Close");
                break;

            default:
                log.warning(id + ": unknown button: [" + btn.getText() + "]");
                break;
        }
    }

    public void enableButton(String lockerId, int who){
        closeDoorBtn.setDisable(false);
        this.lockerId = lockerId;
        //update info
        lockerIDField.setText(lockerId);
        isDoorOpenField.setText("Open");
        this.who = who;
    }

    public String getPollResp() {
        return pollResp;
    }
}
