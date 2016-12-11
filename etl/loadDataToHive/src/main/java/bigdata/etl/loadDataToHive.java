package bigdata.etl;

import java.sql.*;

/**
 * Created by qianxi.zhang on 11/25/16.
 */
public class loadDataToHive {
  private static String LOAD_CMD =
      "load data inpath '%s' overwrite into table record partition(partition_date='%s',hour_minute='%s')";

  private static String driverName = "org.apache.hive.jdbc.HiveDriver";

  public static void loadData(String dataDir, String date, String hour_minute) throws SQLException {
    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }
    Connection con =
        DriverManager.getConnection("jdbc:hive2://bigdata:10000/default", "bigdata", "bigdata");
    Statement stmt = con.createStatement();
    String sql = String.format(LOAD_CMD, dataDir, date, hour_minute);
    stmt.execute(sql);
  }

  public static void main(String[] args) throws SQLException {
    if (args.length != 3) throw new IllegalArgumentException("need 3 args");
    loadDataToHive.loadData(args[0], args[1], args[2]);
  }
}
