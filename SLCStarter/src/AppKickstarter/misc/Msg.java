package AppKickstarter.misc;


//======================================================================
// Msg
public class Msg {
    private String sender;
    private MBox senderMBox;
    private Type type;
    private String details;

    //------------------------------------------------------------
    // Msg
    /**
     * Constructor for a msg.
     * @param sender id of the msg sender (String)
     * @param senderMBox mbox of the msg sender
     * @param type message type
     * @param details details of the msg (free format String)
     */
    public Msg(String sender, MBox senderMBox, Type type, String details) {
	this.sender = sender;
	this.senderMBox = senderMBox;
	this.type = type;
	this.details = details;
    } // Msg


    //------------------------------------------------------------
    // getSender
    /**
     * Returns the id of the msg sender
     * @return the id of the msg sender
     */
    public String getSender()     { return sender; }


    //------------------------------------------------------------
    // getSenderMBox
    /**
     * Returns the mbox of the msg sender
     * @return the mbox of the msg sender
     */
    public MBox   getSenderMBox() { return senderMBox; }


    //------------------------------------------------------------
    // getType
    /**
     * Returns the message type
     * @return the message type
     */
    public Type   getType()       { return type; }


    //------------------------------------------------------------
    // getDetails
    /**
     * Returns the details of the msg
     * @return the details of the msg
     */
    public String getDetails()    { return details; }


    //------------------------------------------------------------
    // toString
    /**
     * Returns the msg as a formatted String
     * @return the msg as a formatted String
     */
    public String toString() {
	return sender + " (" + type + ") -- " + details;
    } // toString


    //------------------------------------------------------------
    // Msg Types
    /**
     * Message Types used in Msg.
     * @see Msg
     */
    public enum Type {
        /** Terminate the running thread */	Terminate,
	/** Generic error msg */		Error,
	/** Set a timer */			SetTimer,
	/** Set a timer */			CancelTimer,
	/** Timer clock ticks */		Tick,
	/** Time's up for the timer */		TimesUp,
	/** Health poll */			Poll,
	/** Health poll +ve acknowledgement */	PollAck,
        /** Health poll -ve acknowledgement */	PollNak,
	/** Update Display */			TD_UpdateDisplay,
	/** Mouse Clicked */			TD_MouseClicked,
        /** Barcode Reader Go Activate */	BR_GoActive,
        /** Barcode Reader Go Standby */	BR_GoStandby,
        /** Card inserted */			BR_BarcodeRead,
        /** Locker open by customer*/        L_OpenByCustomer,
        /** Locker open by delivery */        L_OpenByDelivery,
        /** Locker Close by Delivery */        L_CloseByDelivery,
        /** Locker Close by customer */        L_CloseByCustomer,
        /** Barcode Reader Go Activate */	OR_GoActive,
        /** Barcode Reader Go Standby */	OR_GoStandby,
        /** Card inserted */			OR_OctopusRead,
        /** Active Barcode reader */  RQ_BR_Active,
        /** Standby Barcode reader */  RQ_BR_STANDBY,
        /** verify pw */ PW_Verify,
        /** pw correct */  PW_Correct,
        /** pw not correct */  PW_Incorrect,
        /** pass locker id to TD */ passId,
        /** after pickup turn to main */  TD_CloseDoor_Blackmain,
        /** Penalty adding */ PenaltyPlus,
        /** request Server verify bc */ BC_Verify,
        /** reply from server verify bc */ RP_BC_IsCorrect,
        /** wrong br */ WRONG_Barcode,
        setM_USED,
        setM_EMPTY,
        have_ML,
        isLocker,
        PenaltyAmount,
        RePenalty,
        RP_Have_L,

    } // Type
} // Msg
