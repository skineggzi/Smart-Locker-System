package SLC;

import AppKickstarter.timer.Timer;

import SLC.Locker.Emulator.LockerEmulator;
import SLC.Locker.LockerDB;
import SLC.OctopusReaderDriver.Emulator.OctopusReaderEmulator;
import SLC.OctopusReaderDriver.OctopusReaderDriver;
import SLC.SLC.SLC;
import SLC.BarcodeReaderDriver.BarcodeReaderDriver;
import SLC.BarcodeReaderDriver.Emulator.BarcodeReaderEmulator;
import SLC.SLSvr.Emulator.SLSvrEmulator;
import SLC.SLSvr.SLSvr;
import SLC.TouchDisplayHandler.Emulator.TouchDisplayEmulator;
import SLC.TouchDisplayHandler.TouchDisplayHandler;
import SLC.Locker.LockerDriver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.sql.SQLException;


//======================================================================
// SLCEmulatorStarter
public class SLCEmulatorStarter extends SLCStarter {
    //------------------------------------------------------------
    // main
    public static void main(String [] args) {
	    new SLCEmulatorStarter().startApp();

    } // main


    //------------------------------------------------------------
    // startHandlers
    @Override
    protected void startHandlers() {
        Emulators.slcEmulatorStarter = this;
        new Emulators().start();
    } // startHandlers


    //------------------------------------------------------------
    // Emulators
    public static class Emulators extends Application {
        private static SLCEmulatorStarter slcEmulatorStarter;

	//----------------------------------------
	// start
        public void start() {
            launch();
	} // start

	//----------------------------------------
	// start
        public void start(Stage primaryStage) throws SQLException {
            Timer timer = null;
            SLC slc = null;
            BarcodeReaderEmulator barcodeReaderEmulator = null;
            TouchDisplayEmulator touchDisplayEmulator = null;
            LockerEmulator lockerEmulator = null;
            OctopusReaderEmulator octopusReaderEmulator = null;
            SLSvrEmulator slSvrEmulator = null;
            // create emulators
            try {
                timer = new Timer("timer", slcEmulatorStarter);
                slc = new SLC("SLC", slcEmulatorStarter);
                barcodeReaderEmulator = new BarcodeReaderEmulator("BarcodeReaderDriver", slcEmulatorStarter);
                touchDisplayEmulator = new TouchDisplayEmulator("TouchDisplayHandler", slcEmulatorStarter);
                lockerEmulator = new LockerEmulator("LockerDriver", slcEmulatorStarter);
                octopusReaderEmulator = new OctopusReaderEmulator("OctopusReaderDriver", slcEmulatorStarter);
                slSvrEmulator = new SLSvrEmulator("SLSvr", slcEmulatorStarter);

            // start emulator GUIs
                barcodeReaderEmulator.start();
                touchDisplayEmulator.start();
                lockerEmulator.start();
                octopusReaderEmulator.start();
                slSvrEmulator.start();
            } catch (Exception e) {
                System.out.println("Emulators: start failed");
                e.printStackTrace();
                Platform.exit();
            }
            slcEmulatorStarter.setTimer(timer);
            slcEmulatorStarter.setSLC(slc);
            slcEmulatorStarter.setBarcodeReaderDriver(barcodeReaderEmulator);
            slcEmulatorStarter.setTouchDisplayHandler(touchDisplayEmulator);
            slcEmulatorStarter.setLockerDriver(lockerEmulator);
            slcEmulatorStarter.setOctopusReaderDriver(octopusReaderEmulator);
            slcEmulatorStarter.setSLSvr(slSvrEmulator);



            // start threads
            new Thread(timer).start();
            new Thread(slc).start();
            new Thread(barcodeReaderEmulator).start();
            new Thread(touchDisplayEmulator).start();
            new Thread(lockerEmulator).start();
            new Thread(octopusReaderEmulator).start();
            new Thread(slSvrEmulator).start();

            //Locker generator
            LockerDB lDB = LockerDB.getLockerDB();
            LockerDB.generateLocker();
            System.out.print(lDB.getLockerList());

        } // start
    } // Emulators


    //------------------------------------------------------------
    //  setters
    private void setTimer(Timer timer) {
        this.timer = timer;
    }
    private void setSLC(SLC slc) {
        this.slc = slc;
    }
    private void setBarcodeReaderDriver(BarcodeReaderDriver barcodeReaderDriver) {
        this.barcodeReaderDriver = barcodeReaderDriver;
    }
    private void setTouchDisplayHandler(TouchDisplayHandler touchDisplayHandler) {
        this.touchDisplayHandler = touchDisplayHandler;
    }
    private void setLockerDriver(LockerDriver lockerDriver) {
        this.lockerDriver = lockerDriver;
    }

    private void setOctopusReaderDriver(OctopusReaderDriver octopusReaderDriver) {
        this.octopusReaderDriver = octopusReaderDriver;
    }
    private void setSLSvr(SLSvr slSvr) {
        this.slSvr = slSvr;
    }
} // SLCEmulatorStarter
