import java.io.*;

class UserThread implements Runnable{
    String user;
    ResourceManager diskManager;
    DirectoryManager directoryManager;
    Disk[] disk;
    Printer[] printer;
    ResourceManager printerManager;

    UserThread(String u, Disk[] dk, ResourceManager dm, DirectoryManager di, Printer[] p, ResourceManager pm){
        user = u;
        diskManager = dm;
        directoryManager = di;
        disk = dk;
        printer = p;
        printerManager = pm;
    }

    public void run(){
        
        try {
            System.out.println("Starting UserThread " + user);
            FileInputStream fstream = new FileInputStream(user); 
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            int fileLength = 0, startingSector = 0, diskNumber = 0;
            String s = "", filename = "";
            StringBuffer strLine = new StringBuffer();

            while ((s = br.readLine()) != null){
                strLine.append(s);

                if (strLine.indexOf(".save") == 0){
                    filename = strLine.substring(6);
                    fileLength = 0;
                    
                    // request disk (need to know start sector in disk)
                    diskNumber = diskManager.request();
                    startingSector = disk[diskNumber].size;
                }
                else if (strLine.indexOf(".end") == 0){
                    // update size
                    disk[diskNumber].size = startingSector + fileLength;

                    // add entry to directorymanager
                    FileInfo fifo = new FileInfo(diskNumber, startingSector, fileLength);
                    directoryManager.enter(new StringBuffer(filename), fifo);

                    System.out.println(filename + " saved to Disk " + diskNumber);
                    // release disk
                    diskManager.release(diskNumber);
                }
                else if (strLine.indexOf(".print") == 0){
                    // lookup information
                    filename = strLine.substring(7);  
                    FileInfo fifo = directoryManager.lookup(new StringBuffer(filename));

                    // sent to print function
                    Thread object = new Thread(new PrintJobThread(fifo, filename, printerManager, printer, disk)); 
                    object.start(); 
                }
                else{
                    // write to system
                    disk[diskNumber].write(startingSector+fileLength, strLine);
                    Thread.sleep(200);
                    fileLength++;
                }
                //System.out.println(strLine.toString());
                strLine.setLength(0);
            }    
            fstream.close();
        } catch (FileNotFoundException e){
            System.err.println(e);
        } catch (Exception e) { 
            System.out.println (e); 
        } 
    } 

}