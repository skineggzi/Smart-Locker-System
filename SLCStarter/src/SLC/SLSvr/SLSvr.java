package SLC.SLSvr;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.Msg;
import AppKickstarter.timer.Timer;
import SLC.HWHandler.HWHandler;
import SLC.Locker.LockerDB;

import java.util.Objects;

public class SLSvr extends HWHandler {
    public SLSvr(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    }
    public static String[] isBarcode = new String[]{"4107-7014","2026-6202", ""};
    public String whichLocker;
    public int timerID;
    protected void processMsg(Msg msg) {

        LockerDB lDB = LockerDB.getLockerDB();
        int min = 100000;
        int max = 999999;
        int randomPw = (int)(Math.random()*(max-min+1)+min);
        switch (msg.getType()) {
            case BC_Verify:
                //for(int i=0; i<isBarcode.length; i++){
                    if(Objects.equals(msg.getDetails(), isBarcode[0])){
                        slc.send(new Msg(id, mbox, Msg.Type.RP_BC_IsCorrect, "1,"+randomPw));
                        //TODO remove barcode

                    }else if(Objects.equals(msg.getDetails(), isBarcode[1])){
                        slc.send(new Msg(id, mbox, Msg.Type.RP_BC_IsCorrect, "5,"+randomPw));
                    }else if(Objects.equals(msg.getDetails(), isBarcode[2])){
                        slc.send(new Msg(id, mbox, Msg.Type.RP_BC_IsCorrect, whichLocker+","+randomPw));
                    }else{
                        slc.send(new Msg(id, mbox, Msg.Type.RP_BC_IsCorrect, "WRONG"));
                    }
                //}
                break;

            case setM_USED:
                for(int i=10; i<=20;i++){
                    lDB.UdLockStatus(i, "USED");
                }
                log.info("Locker(M) set all to USED");

                break;
            case setM_EMPTY:
                for(int i=10; i<=20;i++){
                    lDB.UdLockStatus(i, "EMPTY");
                }
                log.info("Locker(M) set all to EMPTY");
                break;
            case have_ML:
                this.timerID =  Timer.setTimer(id, mbox, 5*1000);
                break;

            case RP_Have_L:
                String whichLocker = msg.getDetails();
                this.whichLocker = whichLocker;
                if(Objects.equals(whichLocker, "NO")){
                    this.timerID =  Timer.setTimer(id, mbox, 5*1000);
                }else{
                    isBarcode[2] = "1005-5001";
                }
                break;

            case TimesUp:
                slc.send(new Msg(id, mbox, Msg.Type.isLocker, "M"));

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    }
    protected void handlePoll() {
        log.info(id + ": Handle Poll");
    }

    //------------------------------------------------------------
    // handleGoActive
    protected void handleGoActive() {
        log.info(id + ": Go Active");
    } // handleGoActive


    //------------------------------------------------------------
    // handleGoStandby
    protected void handleGoStandby() {
        log.info(id + ": Go Standby");
    } // handleGoStandby


}
