package SLC.TouchDisplayHandler;

import AppKickstarter.timer.Timer;
import SLC.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import javafx.application.Platform;

import java.util.Objects;


//======================================================================
// TouchDisplayHandler
public class TouchDisplayHandler extends HWHandler {
    //------------------------------------------------------------
    // TouchDisplayHandler
    public int timeID;
    public int timerid;
    public TouchDisplayHandler(String id, AppKickstarter appKickstarter) throws Exception {
	super(id, appKickstarter);
    } // TouchDisplayHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case TD_MouseClicked:
                slc.send(new Msg(id, mbox, Msg.Type.TD_MouseClicked, msg.getDetails()));
                //Timer.cancelTimer(String.valueOf(timerid), mbox, timeID);

                break;

            case TD_UpdateDisplay:
                //this.timeID = Timer.setTimer(String.valueOf(timerid), mbox, 5*1000);
                handleUpdateDisplay(msg);
                break;

            case RQ_BR_Active:
                slc.send(new Msg(id, mbox, Msg.Type.RQ_BR_Active, msg.getDetails()));
                break;

            case RQ_BR_STANDBY:
                slc.send(new Msg(id, mbox, Msg.Type.RQ_BR_STANDBY, msg.getDetails()));
                break;

            case PW_Verify:
                slc.send(new Msg(id, mbox, Msg.Type.PW_Verify, msg.getDetails()));
                break;

            case PW_Incorrect:
                handleIncorrect();
                break;

            case  passId:
                handlePassId(msg.getDetails());
                break;

            case TD_CloseDoor_Blackmain:
                handleCloseDoor_Blackmain();
                break;

            case WRONG_Barcode:
                BarcodeError();
                break;

            case PenaltyAmount:
                handlePenaltyAmount(msg.getDetails());
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg


    //------------------------------------------------------------
    // handleUpdateDisplay
    protected void handleUpdateDisplay(Msg msg) {
	log.info(id + ": update display -- " + msg.getDetails());

    } // handleUpdateDisplay

    protected void handleTimOutDisplay(String screen) {
        log.info(id + ": update display -- " + screen);

    }

    protected  void handlePassId(String lockerID){
    log.info(id + ": Pass Locker ID -- " + lockerID);
};

    //------------------------------------------------------------
    // handlePoll
    protected void handlePoll() {
        log.info(id + ": Handle Poll");
    } // handlePoll

    protected void handleIncorrect() {
        log.info(id + ": Incorrect pw");
    }

    protected void handlePenaltyAmount(String amount) {
        log.info(id + ": send penalty amount");
    }

    protected void handleCloseDoor_Blackmain(){
        log.info(id + ": Have been pick up items ");
}

    protected void BarcodeError(){
        log.info(id + "wrong barcode");
    }
}// TouchDisplayHandler
