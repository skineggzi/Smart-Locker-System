package SLC.Locker.Emulator;

import AppKickstarter.misc.Msg;
import SLC.Locker.LockerDriver;
import SLC.SLCStarter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class LockerEmulator extends LockerDriver {
    private SLCStarter slcStarter;
    private String id;
    private Stage myStage;
    private LockerEmulatorController lockerEmulatorController;

    public LockerEmulator(String id, SLCStarter slcStarter) {
        super(id, slcStarter);
        this.slcStarter = slcStarter;
        this.id = id;
    }

    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        String fxmlName = "LockerEmulator.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LockerEmulator.class.getResource(fxmlName));
        root = loader.load();
        lockerEmulatorController = (LockerEmulatorController) loader.getController();
        lockerEmulatorController.initialize(id, slcStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 350, 470));
        myStage.setTitle("Locker Display");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            slcStarter.stopApp();
            Platform.exit();
        });
        myStage.show();

    } // LockerEmulator

    //------------------------------------------------------------
    // handlePoll



    protected void handlePoll() {
        // super.handlePoll();
        switch (lockerEmulatorController.getPollResp()) {
            case "ACK":
                slc.send(new Msg(id, mbox, Msg.Type.PollAck, id + " is up!"));
                break;

            case "NAK":
                slc.send(new Msg(id, mbox, Msg.Type.PollNak, id + " is down!"));
                break;

            case "Ignore":
                // Just ignore.  do nothing!!
                break;
        }
    } // handlePoll


    protected void handleOpenByCustomer(String lockerId) {
        //super.handleOpenByCustomer(lockerId);
        lockerEmulatorController.enableButton(lockerId, 0); //0 is customer
    }

    protected void handleOpenByDelivery(String lockerID) {
        lockerEmulatorController.enableButton(lockerID, 1);//1 is Delivery
    }
}
