package SLC.Locker;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.Msg;
import SLC.HWHandler.HWHandler;

public class LockerDriver extends HWHandler{

    // LockerDriver
    public LockerDriver(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    } // LockerDriver

    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case L_OpenByCustomer:
                //slc.send(new Msg(id, mbox, Msg.Type.L_OpenByCustomer, msg.getDetails()));
                handleOpenByCustomer(msg.getDetails());
                break;

            case L_OpenByDelivery:
                //slc.send(new Msg(id, mbox, Msg.Type.L_OpenByCustomer, msg.getDetails()));
                handleOpenByDelivery(msg.getDetails());
                break;

            case L_CloseByDelivery:
                handleCloseByDelivery();
                slc.send(new Msg(id, mbox, Msg.Type.L_CloseByDelivery, msg.getDetails()));
                break;

            case L_CloseByCustomer:
                handleCloseByCustomer();
                slc.send(new Msg(id, mbox, Msg.Type.L_CloseByCustomer, msg.getDetails()));
                //System.out.println(msg.getDetails());
                break;

            case PenaltyPlus:
                handlePenalty(msg.getDetails());
                break;


            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    }

    //------------------------------------------------------------
    // handleOpenByCustomer
    protected void handleOpenByCustomer(String lockerID) {
        log.info(id + ": Open door by Customer");
    }

    protected void handleOpenByDelivery(String lockerID) {
        log.info(id + ": Open door by Delivery");
    }

    //------------------------------------------------------------
    // handleCloseByDelivery
    protected void handleCloseByDelivery() {
        log.info(id + ": Close door by Delivery");
    } // handleCloseByDelivery

    //------------------------------------------------------------
    // handleCloseByCustomer
    protected void handleCloseByCustomer() {
        log.info(id + ": Close door by Customer");
    } // handleCloseByDelivery

    protected void handlePenalty(String msg) {
        String[] splitMsgDetails = msg.split(",");
        slc.send(new Msg(id, mbox, Msg.Type.PenaltyPlus, msg));
        log.info("Locker: " + splitMsgDetails[0] + ",Size: "+ splitMsgDetails[1] +"~ You are got penalty " + splitMsgDetails[3] + " times." + "(the penalty code is " + splitMsgDetails[2] + ")");
    }

    //------------------------------------------------------------
    // handlePoll
    protected void handlePoll() {
        log.info(id + ": Handle Poll");
    } // handlePoll
} // LockerDriver

