import java.io.*;

class Printer {

    void print(StringBuffer data, BufferedWriter writer){
        try {
            writer.append(data.toString());
            writer.newLine();
        } catch (IOException e){
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
