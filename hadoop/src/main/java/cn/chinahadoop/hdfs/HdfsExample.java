package cn.chinahadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsExample {

  public static void testMkdirPath(String path) throws Exception {
    FileSystem fs = null;
    try {
      System.out.println("Creating " + path + " on hdfs...");
      Configuration conf = new Configuration();
      // First create a new directory with mkdirs
      Path myPath = new Path(path);
      fs = myPath.getFileSystem(conf);

      fs.mkdirs(myPath);
      System.out.println("Create " + path + " on hdfs successfully.");
    } catch (Exception e) {
      System.out.println("Exception:" + e);
    } finally {
      if(fs != null)
        fs.close();
    }
  }

  public static void testDeletePath(String path) throws Exception {
    FileSystem fs = null;
    try {
      System.out.println("Deleting " + path + " on hdfs...");
      Configuration conf = new Configuration();
      Path myPath = new Path(path);
      fs = myPath.getFileSystem(conf);

      fs.delete(myPath, true);
      System.out.println("Deleting " + path + " on hdfs successfully.");
    } catch (Exception e) {
      System.out.println("Exception:" + e);
    } finally {
      if(fs != null)
        fs.close();
    }
  }

  public static void main(String[] args) {
    try {
      //String path = "hdfs:namenodehost:8020/test/mkdirs-test";
      String path = "/test/mkdirs-test";
      testMkdirPath(path);
      //testDeletePath(path);
    } catch (Exception e) {
      System.out.println("Exceptions:" + e);
    }
    System.out.println("timestamp:" + System.currentTimeMillis());
  }
}
