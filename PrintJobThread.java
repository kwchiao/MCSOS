import java.io.*;

class PrintJobThread implements Runnable{

    String filename;
    FileInfo fifo;
    ResourceManager printerManager;
    Printer[] printer;
    Disk[] disk;

    PrintJobThread(FileInfo fi, String file, ResourceManager pm, Printer[] p, Disk[] dk){
        filename = file;
        fifo = fi;
        printerManager = pm;
        printer = p;
        disk = dk;
    }

    public void run(){
        try {
            int p = printerManager.request();
            try {
                BufferedWriter wr = new BufferedWriter(new FileWriter("printer/PRINTER" + (p+1), true));

                for (int i = 0; i < fifo.fileLength; i++){
                    StringBuffer data = new StringBuffer();
                    disk[fifo.diskNumber].read(fifo.startingSector+i, data);
                    Thread.sleep(200);
                    printer[p].print(data, wr);
                    Thread.sleep(2750);
                }

                System.out.println(filename + " printed to PRINTER" + (p+1));
                wr.close();
            } catch (IOException e){
                System.err.println(e);
            }
            printerManager.release(p);
        } catch (InterruptedException e){
            System.err.println(e);
        } catch (Exception e){
            System.err.println(e);
        }
    }
}