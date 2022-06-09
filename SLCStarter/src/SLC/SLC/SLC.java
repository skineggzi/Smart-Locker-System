package SLC.SLC;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;
import SLC.Locker.Locker;
import SLC.Locker.LockerDB;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.Calendar;



//======================================================================
// SLC
public class SLC extends AppThread {
    private int pollingTime;
    private MBox barcodeReaderMBox;
    private MBox touchDisplayMBox;
	private MBox octopusReaderMBox;
	private MBox lockerDriverMBox;
	private MBox slsvrMBox;

	public int[] timerArrayLocker = new int[0];
	public String[] timerArraySize = new String[0];
	public int[] timerArray = new int[0];
	public int[] timerArrayTimes = new int[0];
	public String[] timerArrayStart = new String[0];


	//------------------------------------------------------------
    // SLC
    public SLC(String id, AppKickstarter appKickstarter) throws Exception {
	super(id, appKickstarter);
	pollingTime = Integer.parseInt(appKickstarter.getProperty("SLC.PollingTime"));
    } // SLC


    //------------------------------------------------------------
    // run
    public void run() {
	Timer.setTimer(id, mbox, pollingTime);
	log.info(id + ": starting...");

	barcodeReaderMBox = appKickstarter.getThread("BarcodeReaderDriver").getMBox();
	touchDisplayMBox = appKickstarter.getThread("TouchDisplayHandler").getMBox();
	octopusReaderMBox = appKickstarter.getThread("OctopusReaderDriver").getMBox();
	lockerDriverMBox = appKickstarter.getThread("LockerDriver").getMBox();
	slsvrMBox = appKickstarter.getThread("SLSvr").getMBox();

	for (boolean quit = false; !quit;) {
	    Msg msg = mbox.receive();
		LockerDB lDB = LockerDB.getLockerDB();
	    log.fine(id + ": message received: [" + msg + "].");

	    switch (msg.getType()) {
		case TD_MouseClicked:
		    log.info("MouseCLicked: " + msg.getDetails());
		    processMouseClicked(msg);
		    break;

		case TimesUp:
		    Timer.setTimer(id, mbox, pollingTime);
		    log.info("Poll: " + msg.getDetails());

			//log.info("Locker " + String.valueOf(timerArrayLocker[0]) + ": " +String.valueOf(timerArray[0]) + "(" + String.valueOf(timerArrayTimes[0]) + ")");
			String logString ="";
			if(timerArrayLocker.length>0 && timerArray.length>0 && timerArrayTimes.length>0) {
				/*for (int i = 0; i < timerArrayTimes.length - 1; i++) {
					timerArrayTimes[i] += 1;
				}*/

				for(int i=0;i<timerArray.length;i++) {
					logString += "Locker " + String.valueOf(timerArrayLocker[i]) + " " +timerArraySize[i] + ": " +String.valueOf(timerArray[i]) + "(" + String.valueOf(timerArrayTimes[i]) + ")" + ", ";
					lockerDriverMBox.send(new Msg(id, mbox, Msg.Type.PenaltyPlus, String.valueOf(timerArrayLocker[i]) + "," + timerArraySize[i] + "," + String.valueOf(timerArray[i]) + "," + String.valueOf(timerArrayTimes[i]) ));
					timerArrayTimes[i] += 1;
				}
				//log.info(logString);

			}else {
				log.info("No locker having penalty");
			}

		    barcodeReaderMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
		    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
			octopusReaderMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
			lockerDriverMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
			slsvrMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
		    break;

		case PenaltyPlus:
			//Timer.setTimer(id, mbox, 10000);
			//msg.getDetails();
			//log.info("Penalty!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		case PollAck:
		    log.info("PollAck: " + msg.getDetails());
		    break;
		//update pollnak
		case PollNak:
			log.info("PollNak: " + msg.getDetails());
			break;

		case RQ_BR_Active:
			barcodeReaderMBox.send(new Msg(id,mbox, Msg.Type.BR_GoActive,""));
			break;

		case RQ_BR_STANDBY:
			barcodeReaderMBox.send(new Msg(id,mbox, Msg.Type.BR_GoStandby,""));
			break;

		case PW_Verify:
			int lockerID = lDB.checkLockerPw(msg.getDetails());
			if (lockerID!=-1){
				//fixme
				int index=-1;
				for(int i=0;i<timerArrayLocker.length;i++){
					if(timerArrayLocker[i]==lockerID){
						index = i;
						log.info("!!find the locker ID in timerArrayLocker[]!! that's no." + index);
						break;
					}
				}
				if(index!=-1) {
					String str="";
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					str += formatter.format(date);

					String[] srtNowTime = str.split("-");
					String[] srtArrTime = timerArrayStart[index].split("-");
					int year = Integer.parseInt(srtNowTime[2]) - Integer.parseInt(srtArrTime[2]);
					int mouth = Integer.parseInt(srtNowTime[1]) - Integer.parseInt(srtArrTime[1]);
					int day = Integer.parseInt(srtNowTime[0]) - Integer.parseInt(srtArrTime[0]);
					int amountDay = day + 30*mouth + 365*year +1;
					if (amountDay <= 0) {
						lockerDriverMBox.send(new Msg(id, mbox, Msg.Type.L_OpenByCustomer, String.valueOf(lockerID)));
						//go to locker location page
						touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.passId, String.valueOf(lockerID)));

						//update status
						lDB.UdIsDoorOpen(lockerID, "OPEN");
						log.info("Lock " + lockerID + " is open");
						//remove in penalty list
						if(index>=0) {
							int[] timerArrayLockerNew = new int[timerArrayLocker.length - 1];
							int[] timerArrayNew = new int[timerArray.length - 1];
							String[] timerArraySizeNew = new String[timerArraySize.length - 1];
							int[] timerArrayTimesNew = new int[timerArrayTimes.length - 1];
							String[] timerArrayStartNew = new String[timerArrayStart.length - 1];
							for (int i = 0; i < index; i++) {
								timerArrayLockerNew[i] = timerArrayLocker[i];
								timerArraySizeNew[i] = timerArraySize[i];
								timerArrayNew[i] = timerArray[i];
								timerArrayTimesNew[i] = timerArrayTimes[i];
								timerArrayStartNew[i] = timerArrayStart[i];
							}
							if(index<timerArray.length) {
								for (int i = index; i < timerArray.length; i++) {
									if (i + 1 < timerArray.length) {
										timerArrayLockerNew[i] = timerArrayLocker[i+1];
										timerArraySizeNew[i] = timerArraySize[i+1];
										timerArrayNew[i] = timerArray[i+1];
										timerArrayTimesNew[i] = timerArrayTimes[i+1];
										timerArrayStartNew[i] = timerArrayStart[i+1];
									}
								}
							}
							timerArrayLocker = timerArrayLockerNew;
							timerArray = timerArrayNew;
							timerArraySizeNew = timerArraySize;
							timerArrayTimes = timerArrayTimesNew;
							timerArrayStartNew = timerArrayStart;
							log.info("penalty list updated, deleted index-" + index);

						}else{
							timerArrayLocker = new int[0];
							timerArray = new int[0];
							timerArraySize = new String[0];
							timerArrayTimes = new int[0];
							timerArrayStart = new String[0];
						}
						//remove in penalty list - end
					} else {
						touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Penalty"));
						if(timerArraySize[index]=="S") {
							int amount = amountDay * 10;
							log.info("Lock " + lockerID + " is not opened since you need to pay the penalty $" + amount);
							touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.PenaltyAmount, String.valueOf(amount)));
						}else if(timerArraySize[index]=="M") {
							int amount = amountDay * 20;
							log.info("Lock " + lockerID + " is not opened since you need to pay the penalty $" + amount);
							touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.PenaltyAmount, String.valueOf(amount)));
						}else if(timerArraySize[index]=="L") {
							int amount = amountDay * 30;
							log.info("Lock " + lockerID + " is not opened since you need to pay the penalty $" + amount);
							touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.PenaltyAmount, String.valueOf(amount)));
						}else{
							log.info("wrong locker size");
						}

						//active octopus reader
						octopusReaderMBox.send(new Msg(id, mbox, Msg.Type.OR_GoActive, String.valueOf(lockerID)));
					}
				}else{
					log.info("~~check locker ID for penalty error~~");
				}

				//touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.PW_Correct, "Correct"));
			}else{
				touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.PW_Incorrect, "Incorrect"));
			}
			break;


		case L_CloseByCustomer:
			touchDisplayMBox.send(new Msg(id,mbox,Msg.Type.TD_CloseDoor_Blackmain,""));
			lDB = LockerDB.getLockerDB();
			lDB.UdLockStatus(Integer.parseInt(msg.getDetails()), "EMPTY");
			lDB.UdIsDoorOpen(Integer.parseInt(msg.getDetails()), "CLOSE");
			log.info("Locker ID: "+msg.getDetails()+", status: EMPTY");
			//System.out.println(lDB.getLockerList());

			//TODO go to blank page
			break;

			case L_CloseByDelivery:
				//touchDisplayMBox.send(new Msg(id,mbox,Msg.Type.TD_CloseDoor_Blackmain,""));
				lDB = LockerDB.getLockerDB();
				lDB.UdLockStatus(Integer.parseInt(msg.getDetails()), "USED");
				lDB.UdIsDoorOpen(Integer.parseInt(msg.getDetails()), "CLOSE");
				log.info("Locker ID: "+msg.getDetails()+", status: USED");
				//System.out.println(lDB.getLockerList());

				String str="";
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				str += formatter.format(date);

				timerArray = Arrays.copyOf(timerArray,timerArray.length+1);
				timerArraySize = Arrays.copyOf(timerArraySize,timerArraySize.length+1);
				timerArrayLocker = Arrays.copyOf(timerArrayLocker,timerArrayLocker.length+1);
				timerArrayTimes = Arrays.copyOf(timerArrayTimes,timerArrayTimes.length+1);
				timerArrayStart = Arrays.copyOf(timerArrayStart,timerArrayStart.length+1);

				String tID = "Test"+String.valueOf(timerArray.length);

				timerArray[timerArray.length-1] = (Timer.setTimer(tID,lockerDriverMBox,3000));
				timerArrayLocker[timerArrayLocker.length-1] = Integer.parseInt(msg.getDetails());
				LockerDB lockerDB = LockerDB.getLockerDB();
				timerArraySize[timerArraySize.length-1] = lockerDB.getLockerSize(Integer.parseInt(msg.getDetails()));
				timerArrayTimes[timerArrayTimes.length-1] = 0;
				timerArrayStart[timerArrayStart.length-1] = str;


				//TODO go to blank page
				break;

		case BR_BarcodeRead:
			slsvrMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.BC_Verify, msg.getDetails()));
			break;

		case RP_BC_IsCorrect:
			//if true
			if(!Objects.equals(msg.getDetails(), "WRONG")){
				String[] split = msg.getDetails().split(",");
				lDB = LockerDB.getLockerDB();
				log.info("Locker: "+split[0]+", PW: "+split[1]);
				lDB.UdLockStatus(Integer.parseInt(split[0]), "USED");
				lDB.UdIsDoorOpen(Integer.parseInt(split[0]), "OPEN");
				lDB.UdLockerPw(Integer.parseInt(split[0]), split[1]);
				lockerDriverMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.L_OpenByDelivery, split[0]));
				//System.out.println(lDB.getLockerList());
			}else{
				touchDisplayMBox.send(new Msg(id,mbox,Msg.Type.WRONG_Barcode,""));
				log.info("No this barcode");
			}
			break;

		case OR_OctopusRead:
			System.out.println(msg.getDetails());
			lockerDriverMBox.send(new Msg(id, mbox, Msg.Type.L_OpenByCustomer, String.valueOf(msg.getDetails())));
			touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.passId, msg.getDetails()));
			break;

		case Terminate:
		    quit = true;
		    break;

			case isLocker:
				int whichLockerID = lDB.isLockerCanUse(msg.getDetails());
				if(whichLockerID==-1){
					log.info("No locker");
					slsvrMBox.send(new Msg(id, mbox, Msg.Type.RP_Have_L, "NO"));
				}else{
					log.info("Have locker");
					slsvrMBox.send(new Msg(id, mbox, Msg.Type.RP_Have_L, Integer.toString(whichLockerID)));
				}

				break;

		default:
		    log.warning(id + ": unknown message type: [" + msg + "]");
	    }
	}

	// declaring our departure
	appKickstarter.unregThread(this);
	log.info(id + ": terminating...");
    } // run


    //------------------------------------------------------------
    // processMouseClicked
    private void processMouseClicked(Msg msg) {
	// *** process mouse click here!!! ***
    } // processMouseClicked
} // SLC
