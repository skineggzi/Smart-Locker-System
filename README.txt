# COMP4107_Project
Smart Locker System

[5 main part of the architecture]
1. SLC
2. SLSvr
3. TouchDisplayHandler
4. BarcodeReaderDrive
5. OctopusReaderDriver
6. LockerDriver

[Structure of location of the directories and files]
SLC
|_SLC	//main part to process all the function with other emulator

SLSvr	//simple server, to request SLC(Have locker?/ Check barcode)
|_Emulator
|   |_SLSvrEmulator	//Server GUI
|   |_SLSvrEmulatorController	//Server controller
|_SLSvr

TouchDisplayHandler	//Touch screen
|_Emulator
|   |_TouchDisplayEmulator	//screen: blank, main, password, barcode, penalty, location
|   |_TouchDisplayEmulatorController	
|_TouchDisplayHandler

BarcodeReaderDriver	//barcode reader
|_Emulator
|   |_BarcodeReaderEmulator
|   |_BarcodeReaderEmulatorController	//active or standby + send barcode to server verify
|_BarcodeReaderDriver

Locker	//emulate a locker
|_Emulator
|   |_LockerEmulator
|   |_LockerEmulatorController	//open door or close door
|_LockerDriver

OctopusReaderDriver	//emulate Octopus Reader
|_Emulator
|   |_OctopusReaderEmulator
|   |_OctopusReaderEmulatorController	//active or standby + send Octopus card
|_OctopusReaderDriver


[Instructions for compiling]
IDE: IntelliJ
Java JDK: 1.8
Start main: SLCEmulatorStarter.java
Stop running: close the any one of GUI


[Default setting]
30 Locker
  -Locker1-10: size "S"
  -Locker11-20: size "M" 
  -Locker21-30: size "L" 
barcode
  -"4107-7014" (enable)
  -"2026-6202" (enable)
  -"1005-5001" (disable)

[User Guide]
1. Touch Screen: click --> click "Delivery login" --> click "Scan"
2. Barcode reader: send "Barcode 1"
3. In Run Console, there is a log message (e.g. Locker: 1, PW: 858600)
4. Locker: click "Close Door" button
5. Touch Screen: click "Finished". It will back to blank
6. Touch Screen: click --> click "Pick up" --> input passcode (e.g. 858600)
7. If late to get product, there will be penalty.
8. OctopusReader: send "Octopus 1"
7. Touch screen will show the locker location, and Locker is open.
8. Locker: click "Close Door" button. Touch Screen back to blank 

