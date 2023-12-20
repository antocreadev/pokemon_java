package cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

  static FileOutputStream fos = null;
  static ObjectOutputStream oos = null;
  static FileInputStream fis = null;
  static ObjectInput ois = null;

  public static void serialize(String filename, Object o) {
    try {
      fos = new FileOutputStream(Main.SAVES_PATH + filename);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(o);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        fos.close();
        oos.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static Object deserialize(String filename) {
    Object o = null;
    try {
      fis = new FileInputStream(Main.SAVES_PATH + filename);
      ois = new ObjectInputStream(fis);
      o = (Object) ois.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        fis.close();
        ois.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return o;
  }

  public static boolean fileExists(String filename) {
    File f = new File(Main.SAVES_PATH + filename);
    return f.exists();
  }

}
