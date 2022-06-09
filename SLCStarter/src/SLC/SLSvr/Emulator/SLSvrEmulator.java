package SLC.SLSvr.Emulator;

import AppKickstarter.misc.Msg;
import SLC.BarcodeReaderDriver.Emulator.BarcodeReaderEmulator;
import SLC.BarcodeReaderDriver.Emulator.BarcodeReaderEmulatorController;
import SLC.Locker.Emulator.LockerEmulator;
import SLC.Locker.Emulator.LockerEmulatorController;
import SLC.SLCStarter;
import SLC.SLSvr.SLSvr;
import SLC.TouchDisplayHandler.Emulator.TouchDisplayEmulator;
import SLC.TouchDisplayHandler.Emulator.TouchDisplayEmulatorController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class SLSvrEmulator extends SLSvr {
    private final int WIDTH = 680;
    private final int HEIGHT = 570;
    private SLCStarter slcStarter;
    private String id;
    private Stage myStage;
    private SLSvrEmulatorController slSvrEmulatorController;

    public SLSvrEmulator(String id, SLCStarter slcStarter) {
        super(id, slcStarter);
        this.slcStarter = slcStarter;
        this.id = id;
    }
//SLSvrEmulator.fxml
    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        String fxmlName = "SLSvrEmulator.fxml";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SLSvrEmulator.class.getResource(fxmlName));
        root = loader.load();
        slSvrEmulatorController = (SLSvrEmulatorController) loader.getController();
        slSvrEmulatorController.initialize(id, slcStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 350, 470));
        myStage.setTitle("SLSvrEmulator");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            slcStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // LockerEmulator


    protected void handleGoActive() {
        // fixme
        super.handleGoActive();

    } // handleGoActive


    //------------------------------------------------------------
    // handleGoStandby
    protected void handleGoStandby() {
        // fixme
        super.handleGoStandby();
    } // handleGoStandby


    //------------------------------------------------------------
    // handlePoll
    protected void handlePoll() {
        // super.handlePoll();

        switch (slSvrEmulatorController.getPollResp()) {
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
} // handlePoll

