package SLC.OctopusReaderDriver;

import SLC.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;


//======================================================================
// BarcodeReaderDriver
public class OctopusReaderDriver extends HWHandler {
    //------------------------------------------------------------
    // BarcodeReaderDriver
    public OctopusReaderDriver(String id, AppKickstarter appKickstarter) {
	super(id, appKickstarter);
    } // BarcodeReaderDriver


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case OR_OctopusRead:
                slc.send(new Msg(id, mbox, Msg.Type.OR_OctopusRead, msg.getDetails()));
                break;

            case OR_GoActive:
                handleGoActive(msg.getDetails());
                break;

            case OR_GoStandby:
                handleGoStandby();
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg


    //------------------------------------------------------------
    // handleGoActive
    protected void handleGoActive(String lockerID) {
	    log.info(id + ": Go Active");
    } // handleGoActive


    //------------------------------------------------------------
    // handleGoStandby
    protected void handleGoStandby() {
	log.info(id + ": Go Standby");
    } // handleGoStandby


    //------------------------------------------------------------
    // handlePoll
    protected void handlePoll() {
        log.info(id + ": Handle Poll");
    } // handlePoll
} // BarcodeReaderDriver
