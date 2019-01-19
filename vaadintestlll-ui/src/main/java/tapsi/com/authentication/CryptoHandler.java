package tapsi.com.authentication;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CryptoHandler {

    private String adminAcc = "GDAdmin-1234";

    private File sekiFile;
    private File usFile;

    private SecretKey originalKey;

    private Map<String, String> userMap = new HashMap<>();

    public CryptoHandler() {
        String sekiPath = System.getProperty("java.io.tmpdir");
        String usPath = System.getProperty("java.io.tmpdir");

        //String sekiPath = "/home/pi/GeoDoorServer";
        //String usPath = "/home/pi/GeoDoorServer";

        if (System.getProperty("os.name").equals("Linux")) {
            sekiPath += "/seki.txt";
            usPath += "/us.txt";

        } else if (System.getProperty("os.name").equals("Windows 10")) {
            sekiPath += "seki.txt";
            usPath += "us.txt";
        }

        System.out.println(sekiPath + "\n" + usPath);
        System.out.println(System.getProperty("os.name"));

        sekiFile = new File(sekiPath);
        usFile = new File(usPath);

        if (!checkFiles()) {
            if (!createFiles()) {
                if (!deleteFiles())
                    return;
                else if (!createFiles())
                    return;
                else
                    writeUserToFile();
            } else {
                writeUserToFile();
            }
        }
        readFiles();
    }

    public Map<String, String> getUserMap() {
        return userMap;
    }

    private boolean createFiles() {
        boolean result = true;
        try {
            if (!sekiFile.createNewFile()) {
                System.out.println("Couldn't create seki");
                result = false;
            }
            if (!usFile.createNewFile()) {
                System.out.println("Couldn't create us");
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    private boolean deleteFiles() {
        boolean result = true;
        if (!sekiFile.delete()) {
            System.out.println("Couldn't delete seki");
            result = false;
        }
        if (!usFile.delete()) {
            System.out.println("Couldn't delete us");
            result = false;
        }
        return result;
    }

    private boolean checkFiles() {
        return sekiFile.exists() && usFile.exists();
    }

    private void readFiles() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(sekiFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String lineSeki;
        String resultSeki = "";
        try {
            while ((lineSeki = br.readLine()) != null) {
                resultSeki += lineSeki;
            }
            byte[] decodedKey = Base64.getDecoder().decode(resultSeki);
            originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader br2 = null;
        try {
            br2 = new BufferedReader(new FileReader(usFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        readUsers();
    }

    public void readUsers() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(usFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String lineUS;
        try {
            while ((lineUS = br.readLine()) != null) {
                String temp = decrypt(lineUS,originalKey);
                String user = temp.substring(0, temp.indexOf("-"));
                String pass = temp.substring(temp.indexOf("-")+1, temp.length());
                userMap.putIfAbsent(user, pass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeUserToFile() {
        KeyGenerator keygenerator = null;
        try {
            keygenerator = KeyGenerator.getInstance("DES");
            originalKey = keygenerator.generateKey();

            String encodedKey = Base64.getEncoder().encodeToString(originalKey.getEncoded());
            File dir = new File(sekiFile.getParentFile(), sekiFile.getName());
            PrintStream writer = new PrintStream(dir);
            writer.println(encodedKey);
            writer.close();

            writeUserToFile(adminAcc);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeUserToFile(String valToEncrypt) {
        try {
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            byte[] text = valToEncrypt.getBytes("UTF8");

            desCipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] textEncrypted = desCipher.doFinal(text);
            String textString = Base64.getEncoder().encodeToString(textEncrypted);

            try {
                File dir2 = new File(usFile.getParentFile(), usFile.getName());
                PrintStream writer2 = new PrintStream(new FileOutputStream(dir2, false));
                writer2.println(textString);
                writer2.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String decrypt(String value, SecretKey myDesKey) {
        String s = null;
        try {
            Cipher desCipher;

            byte[] textDecrypted;
            byte[] original = Base64.getDecoder().decode(value);
            desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            textDecrypted = desCipher.doFinal(original);
            s = new String(textDecrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return s;
    }
}

