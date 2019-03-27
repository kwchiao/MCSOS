
class Disk {
    static final int NUM_SECTORS = 1024;
    StringBuffer sectors[] = new StringBuffer[NUM_SECTORS];
    int size = 0;
    
    // only 1 can write to Disk
    void write(int sector, StringBuffer data){
        sectors[sector] = new StringBuffer(data);
    } 

    void read(int sector, StringBuffer data){
        data.setLength(0);
        data.append(sectors[sector]);
    } 
}

