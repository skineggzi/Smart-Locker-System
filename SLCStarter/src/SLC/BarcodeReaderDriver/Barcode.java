package SLC.BarcodeReaderDriver;

public class Barcode {
    private String barcodeID;
    private int lockerID;

    public Barcode(String barcodeID, int lockerID) {
        this.barcodeID = barcodeID;
        this.lockerID = lockerID;
    }

    public String getBarcodeID() {
        return barcodeID;
    }

    public void setBarcodeID(String barcodeID) {
        this.barcodeID = barcodeID;
    }

    public int getLockerID() {
        return lockerID;
    }

    public void setLockerID(int lockerID) {
        this.lockerID = lockerID;
    }

}
