package frc.robot.utility.InfoTracker;

import java.io.File;

public class OwnFile extends File{
    public OwnFile(java.lang.String pathName) {
    super(pathName);
    //TODO Auto-generated constructor stub
  }

    /** The Singleton Instance. */
    private static OwnFile file;

  /**
   * Returns the File instance.
   *
   * @return the instance
   */
  public static synchronized OwnFile getInstance(java.lang.String pathName) {
    if (file == null) {
        file = new OwnFile(pathName);
    }
    return file;
  }
    
}
