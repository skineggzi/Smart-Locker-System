package SLC.TouchDisplayHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

import static javafx.scene.paint.Color.RED;


//======================================================================
// TouchDisplayEmulatorController
public class TouchDisplayEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private TouchDisplayEmulator touchDisplayEmulator;
    private MBox touchDisplayMBox;
    private String selectedScreen;
    private String pollResp;
    public ChoiceBox screenSwitcherCBox;
    public ChoiceBox pollRespCBox;
    public TextField pwShow;
    public TextField PaymentTextArea;
    public Label errorMsgLabel;
    public Label lockerIDlabel, barcodeError;
    private boolean isClick = false;
    private String numberEnter = "";
    private String pw;
    public String lockerId;
    String pickupCode = "";
    public Rectangle left,right,locker1,locker2,locker3,locker4,locker5,locker6, locker20;



    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, TouchDisplayEmulator touchDisplayEmulator, String pollRespParam) {
        this.id = id;
	this.appKickstarter = appKickstarter;
	this.log = log;
	this.touchDisplayEmulator = touchDisplayEmulator;
	this.touchDisplayMBox = appKickstarter.getThread("TouchDisplayHandler").getMBox();
	this.pollResp = pollRespParam;
	this.pollRespCBox.setValue(this.pollResp);
        this.pollRespCBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pollResp = pollRespCBox.getItems().get(newValue.intValue()).toString();
            }
        });
        this.screenSwitcherCBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectedScreen = screenSwitcherCBox.getItems().get(newValue.intValue()).toString();
                switch (selectedScreen) {
                    case "Blank":
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "BlankScreen"));
                        break;

                    case "Main Menu":
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                        break;

                    case "Password":
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Password"));
                        break;

                    case "Scan Barcode":
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "ScanBarcode"));
                        break;

                    /*case "PenaltyAmount":
                        PaymentTextArea.setText("msg.getDetails()");
                        break;*/

                    case "Penalty":
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Penalty"));
                        break;

                    case "LockerLocation":
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "LockerLocation"));
                        break;
                }
            }
        });
        this.selectedScreen = screenSwitcherCBox.getValue().toString();
    } // initialize


    //------------------------------------------------------------
    // getSelectedScreen
    public String getSelectedScreen() {
        return selectedScreen;
    } // getSelectedScreen


    //------------------------------------------------------------
    // getPollResp
    public String getPollResp() {
        return pollResp;
    } // getPollResp


    //------------------------------------------------------------
    // td_mouseClick
    public void td_mouseClick(MouseEvent mouseEvent) throws SQLException {
        this.isClick = true;
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));
        // get which page and do diffs action
        if (Objects.equals(this.getPollResp(), "ACK")) {
            switch (selectedScreen) {
                case "Blank":
                    //In blank, click and go to main page
                    touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                    break;

                case "Main Menu":
                    //click to input password

                    if ((x >= 258 && x <= 413) && (y >= 180 && y <= 345)) {
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Password"));
                    }else if((x>=0&&x<=680)&&(y>=460&&y<=515)){ //click to scan barcode
                        //touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.RQ_BR_Active, ""));
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "ScanBarcode"));
                    }
                    break;

                case "Password":
                    if ((x >= 0 && x <= 214) && (y >= 156 && y <= 234)) {
                        numberEnter += "1";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 214 && x <= 428) && (y >= 156 && y <= 234)) {
                        numberEnter += "2";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 406 && x <= 640) && (y >= 156 && y <= 234)) {
                        numberEnter += "3";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 0 && x <= 214) && (y >= 234 && y <= 312)) {
                        numberEnter += "4";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 214 && x <= 428) && (y >= 234 && y <= 312)) {
                        numberEnter += "5";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 426 && x <= 640) && (y >= 234 && y <= 312)) {
                        numberEnter += "6";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 0 && x <= 214) && (y >= 312 && y <= 390)) {
                        numberEnter += "7";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 212 && x <= 426) && (y >= 312 && y <= 390)) {
                        numberEnter += "8";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 426 && x <= 640) && (y >= 312 && y <= 390)) {
                        numberEnter += "9";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 212 && x <= 426) && (y >= 390 && y <= 468)) {
                        numberEnter += "0";
                        pwShow.setText(numberEnter);
                    } else if ((x >= 0 && x <= 214) && (y >= 390 && y <= 468)) {
                        if (!numberEnter.isEmpty())
                            numberEnter = numberEnter.substring(0, numberEnter.length() - 1);
                        pwShow.setText(numberEnter);
                    } else if ((x >= 426 && x <= 640) && (y >= 390 && y <= 468)) {
                        pw = pwShow.getText();
                        //System.out.println(pickupCode);
                        log.info(id+": Pw input= " + pw);
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.PW_Verify, pw));


//                        LockerDB lDB = LockerDB.getLockerDB();
//                        if (lDB.checkLockerPw(pw)!=-1){
//                            //fixme
//                            //touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.PW_Vertify, String.valueOf(lDB.checkLockerPw(pw))));
//                            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "LockerConfirm"));
//                        }else{
//                            errorMsgLabel.setVisible(true);
//                            numberEnter="";
//                            pwShow.setText(numberEnter);
//                        }
                    }
                    else if ((x >= 2 && x <= 95) && (y >= 1 && y <= 67)){
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                    }
                    break;

                case "LockerLocation":

                    break;

                case "Scan Barcode":
                    if ((x>=112&&x<=291)&&(y>=168&&y<=378)) {
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.RQ_BR_Active, ""));
                        barcodeError.setVisible(false);
                    }
                    else if ((x>=331&&x<=512)&&(y>=167&&y<=379)){
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.RQ_BR_STANDBY, ""));
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "BlankScreen"));
                    }
                    break;

                case "Penalty":
                        if ((x >= 2 && x <= 95) && (y >= 1 && y <= 67)){
                        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                    }
                        break;
            }
            //touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.SetTimer, "MainMenu"));
        }
        // td_mouseClick
    }



//  public void showtheockerlocation(){Pane pane = (Pane) node;
  //  Rectangle rect = (Rectangle) pane.getChildren().get(0);
//    rect.setFill(Color.BLACK);}

    public boolean getIsClick() {
         return this.isClick;
    }

    public void setIsClick(boolean isClick) {
        this.isClick = isClick;
    }

 public void showLockerId(String lockerId){
     this.lockerId = lockerId;
     if(Objects.equals(lockerId, "1")){
         left.setFill(RED);
     locker1.setFill(RED);
     }
     else if (Objects.equals(lockerId, "2")){
         left.setFill(RED);
         locker2.setFill(RED);
     }
     else if (Objects.equals(lockerId,"3")){
         left.setFill(RED);
         locker3.setFill(RED);
     }
     else if (Objects.equals(lockerId,"4")){
         left.setFill(RED);
         locker4.setFill(RED);
     }
     else if (Objects.equals(lockerId,"5")){
         left.setFill(RED);
         locker5.setFill(RED);
     }else if(Objects.equals(lockerId,"20")){
         left.setFill(RED);
         locker20.setFill(RED);
     }
     lockerIDlabel.setText("Your locker ID is: " + lockerId);

    }

    public void setPenaltyAmount(String amount){
        PaymentTextArea.setText(amount);
        PaymentTextArea.setEditable(false);
    }

    public void setErrMsg(){
        errorMsgLabel.setVisible(true);
        numberEnter="";
        pwShow.setText(numberEnter);
    }

    public void setBarcodeError(){
        barcodeError.setVisible(true);
    }
} // TouchDisplayEmulatorController

