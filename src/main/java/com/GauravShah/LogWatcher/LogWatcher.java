package com.GauravShah.LogWatcher;

import jakarta.websocket.server.ServerEndpoint;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@ServerEndpoint("/log")
public class LogWatcher {
    public RandomAccessFile file;
    public long lastReadPosition;
    public int n = 10;
    public void readFile() {
        String filePath = "/Users/gauravshah/Desktop/logsfile.txt";
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(new File(filePath))) {
            String line;
            int linesRead = 0;
            List<String> lines = new ArrayList<>();
            file = new RandomAccessFile(filePath,"r");
            lastReadPosition = file.length()-1;

            while (linesRead < n && (line = reader.readLine()) != null) {
                lines.add(line);
                linesRead++;
            }
            for(int i = lines.size()-1; i>=0; i--){
                System.out.println(lines.get(i));
            }
            while(true){
                checkFile();
                Thread.sleep(1000);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("file was not found",e);
        } catch (IOException e) {
            throw new RuntimeException("IO exception",e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkFile() throws IOException, InterruptedException {
        try (RandomAccessFile file = new RandomAccessFile("/Users/gauravshah/Desktop/logsfile.txt", "r")) {
            file.seek(lastReadPosition);
            String line;
//            if(file.readLine()!=null && file.readLine().startsWith("\n")){
//                file.seek(lastReadPosition);
//            }
            while ((line = file.readLine()) != null) {
                System.out.println(line);
                lastReadPosition = file.getFilePointer();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}