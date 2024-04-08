package settings;

/**
* @author Duc Linh
*/
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class SettingsIO {
	String path = "src/data/stdat.dat";
	private static String encrypt(String input, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decrypt(String input, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
        return new String(decryptedBytes);
    }
	private String settingcrypto(String st ,boolean mode) throws Exception {
		String rs=null;
		String secretKeyString ="mecAudiBmwtoYotahOndasuzukilamBo";
		SecretKey secretKey = new SecretKeySpec(secretKeyString.getBytes(),"AES" );
		if(mode) {
			rs = encrypt(st,secretKey);
		}else {
			rs= decrypt(st, secretKey);
		}
						
		return rs;
	}
	public ObSetting SettingsReader() throws Exception {
		ObSetting obsettings = new ObSetting();
		File file = new File(path);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String data =br.readLine();
			while(data !=null) {				
				String[] tmp = (settingcrypto(data, false)).split("@");
				obsettings.setValue(tmp[0], tmp[1]);
				data= br.readLine();
			}
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return obsettings;
	}
	public boolean SettingsWriter(ObSetting obsettings) throws IOException,Exception {
		
		try {
			Map<String, String> list = obsettings.settings;
			File file = new File(path);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			String st = "";
			for (Map.Entry<String, String> e : list.entrySet()) {	
				st += settingcrypto(e.getKey() + "@" +e.getValue(), true)+"\n";
			}
			
			bw.write(st);
			bw.flush();
			bw.close();
			fw.close();
			System.out.println("Da chay writer");
		}catch(IOException ex) {
			return false;
		}
		return true;
	}
}
