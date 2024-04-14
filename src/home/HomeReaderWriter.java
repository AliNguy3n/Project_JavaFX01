package home;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
* @author Duc Linh
*/
public class HomeReaderWriter {
	String path ="src/data/hmdat.dat";
	File file = new File(path);
	public Homeboot homeReader() {
        Homeboot boot = new Homeboot();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] st = line.split("@");
                switch (st[0]) {
                    case "selectedDate" -> boot.setSelectedDate(st[1]);
                    case "selectedStatus" -> boot.setSelectedStatus(st[1]);
                    case "modeDoing" -> boot.setModeDoing(Boolean.parseBoolean(st[1]));
                    case "modeDelay" -> boot.setModeDelay(Boolean.parseBoolean(st[1]));
                    case "modePlan" -> boot.setModePlan(Boolean.parseBoolean(st[1]));
                    
                    default -> System.out.println("Line Error!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boot;
    }

    public void homeWriter(Homeboot boot) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            StringBuilder sb = new StringBuilder();
            sb.append("selectedDate@").append(boot.getSelectedDate()).append("\n");
            sb.append("selectedStatus@").append(boot.getSelectedStatus()).append("\n");
            sb.append("modeDoing@").append(boot.isModeDoing()).append("\n");
            sb.append("modeDelay@").append(boot.isModeDelay()).append("\n");
            sb.append("modePlan@").append(boot.isModePlan()).append("\n");
            bw.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
