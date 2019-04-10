
public class MCSOS {

    final static int NUMBER_OF_USERS = 4;
    final static int NUMBER_OF_DISKS = 2;
    final static int NUMBER_OF_PRINTERS = 3;
    
    static Thread user[] = new Thread[NUMBER_OF_USERS];
    static Disk disk[] = new Disk[NUMBER_OF_DISKS];
    static Printer printer[] = new Printer[NUMBER_OF_PRINTERS];
    public static void main(String[] args){

        ResourceManager diskManager = new ResourceManager(NUMBER_OF_DISKS);
        ResourceManager printerManager = new ResourceManager(NUMBER_OF_PRINTERS);
        DirectoryManager directoryManager = new DirectoryManager();

        // initialize disks/printers
        for (int i = 0; i < NUMBER_OF_DISKS; i++){
            disk[i] = new Disk();
        }

        for (int i = 0; i < NUMBER_OF_PRINTERS; i++){
            printer[i] = new Printer();
        }

        for (int i = 1; i <= NUMBER_OF_USERS; i++) { 
            user[i-1] = new Thread(new UserThread("users/USER" + i, disk, diskManager, directoryManager, printer, printerManager));
            user[i-1].start();
        } 

    }


}