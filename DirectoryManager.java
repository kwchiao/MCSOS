import java.util.Hashtable;

class FileInfo{
    int diskNumber;
    int startingSector;
    int fileLength;

    FileInfo(int disk, int start, int length){
        diskNumber = disk;
        startingSector = start;
        fileLength = length;
    }
}

class DirectoryManager {
    Hashtable<String, FileInfo> T = new Hashtable<String, FileInfo>();

    void enter(StringBuffer key, FileInfo file){
        T.put(key.toString(), file);
    }

    FileInfo lookup(StringBuffer key){
        String k = key.toString();
        if (T.containsKey(k)){
            return T.get(k);
        }
        else {
            return null;
        }
    }
}
