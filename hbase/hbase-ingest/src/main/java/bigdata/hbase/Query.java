package bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

import static bigdata.hbase.Ingest.TABLE_NAME;

/**
 * Created by qianxi.zhang on 5/2/17.
 */
public class Query {
  public static final String TABLE_NAME = "user_behavior";
  public static final String FAMILY_NAME_P = "p";
  public static final String FAMILY_NAME_B = "b";
  public static final String QUALIFIER_NAME_B_RID = "rid";

  public static Configuration getHBaseConfiguration() {
    Configuration conf = HBaseConfiguration.create();
    conf.set("hbase.zookeeper.quorum",
        "bigdata");
    conf.set("zookeeper.znode.parent", "/hbase");

    return conf;
  }

  public void process() throws IOException {
    //establish the connection to the cluster.
    Connection connection = ConnectionFactory.createConnection();
    //retrieve a handler to the target table
    Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

    Scan scan = new Scan();
    scan.addColumn(Bytes.toBytes(FAMILY_NAME_B), Bytes.toBytes(QUALIFIER_NAME_B_RID));
    scan.setMaxVersions(1000);
    scan.setCaching(100);
    ResultScanner results = table.getScanner(scan);

    for (Result result : results) {
      System.out.println(Bytes.toString(result.getRow()) + " : " + (result.isEmpty() ? 0 : result.listCells().size()));
    }
    table.close();
    connection.close();
  }

  public static void main(String[] args) throws IOException {
    Query query = new Query();
    query.process();
  }

}
