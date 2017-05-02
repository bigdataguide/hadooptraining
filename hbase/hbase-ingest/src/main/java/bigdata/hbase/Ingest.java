package bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by qianxi.zhang on 5/1/17.
 */
public abstract class Ingest {
  public static final String TABLE_NAME = "user_behavior";
  public static final String SEPARATOR = ",";
  //Connection to the cluster.
  private Connection connection = null;
  //A lightweight handler for a specific table.
  private Table table = null;
  public static final String FAMILY_NAME_P = "p";
  public static final String FAMILY_NAME_B = "b";

  public static Configuration getHBaseConfiguration() {
    Configuration conf = HBaseConfiguration.create();
    conf.set("hbase.zookeeper.quorum",
        "bigdata");
    conf.set("zookeeper.znode.parent", "/hbase");

    return conf;
  }

  public void init() throws IOException {
    //establish the connection to the cluster.
    connection = ConnectionFactory.createConnection(getHBaseConfiguration());
    //retrieve a handler to the target table
    table = connection.getTable(TableName.valueOf(TABLE_NAME));
  }

  public void shutdown() throws IOException {
    if (table != null) {
      table.close();
    }
    if (connection != null) {
      connection.close();
    }
  }

  public void createTable() throws IOException {
    Admin admin = connection.getAdmin();

    if (!admin.tableExists(TableName.valueOf(TABLE_NAME))) {
      HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
      HColumnDescriptor columnDescriptor_1 = new HColumnDescriptor(Bytes.toBytes(FAMILY_NAME_P));
      HColumnDescriptor columnDescriptor_2 = new HColumnDescriptor(Bytes.toBytes(FAMILY_NAME_B));
      columnDescriptor_1.setMaxVersions(1);
      columnDescriptor_2.setMaxVersions(1000);
      tableDescriptor.addFamily(columnDescriptor_1);
      tableDescriptor.addFamily(columnDescriptor_2);
      admin.createTable(tableDescriptor);
    }
  }

  public void ingest(String path) throws IOException {
    init();
    createTable();
    FileSystem fs = null;
    Configuration conf = new Configuration();
    Path myPath = new Path(path);
    fs = myPath.getFileSystem(conf);
    FSDataInputStream hdfsInStream = fs.open(new Path(path));
    BufferedReader in = null;
    in = new BufferedReader(new InputStreamReader(hdfsInStream));
    String line = null;
    while ((line = in.readLine()) != null) {
      System.out.println(line);
      Put put = process(line);
      //send the data
      table.put(put);
    }
    if (in != null) {
      in.close();
    }
    shutdown();
  }

  abstract public Put process(String line);

}
