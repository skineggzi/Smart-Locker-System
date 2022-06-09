package SLC.BarcodeReaderDriver;

import SLC.Locker.Locker;
import SLC.Locker.LockerDB;
import SLC.Locker.LockerDriver;

import java.util.ArrayList;
import java.util.Objects;

public class BarcodeDB {
    private ArrayList<Barcode> barcodes = new ArrayList<>();

    public ArrayList<Barcode> getBarcodes() {
        return barcodes;
    }



    // add||set new barcode and storage **From delivery company send request
    public void setBarcodes(String barcodeID, int lockerID){
        barcodes.add(new Barcode(barcodeID, lockerID));
        //set locker status to RESERVED
        //lockerDB.setLockerStatus(lockerID, "RESERVED");
    }

    //remove barcode if used
    public void removeBarcode(String barcodeID){
        for(int i=0; i<barcodes.size(); i++){
            if(Objects.equals(barcodes.get(i).getBarcodeID(), barcodeID)){
                barcodes.remove(i);
            }
        }
    }
}
