package bigdata.hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.exceptions.IllegalArgumentIOException;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by qianxi.zhang on 5/2/17.
 */
public class RecordIngest extends Ingest {
  public static final String QUALIFIER_NAME_B_RID = "rid";

  public Put process(String line) {
    String[] attributes = line.split(SEPARATOR);
    Put put = new Put(Bytes.toBytes(attributes[1]));
    put.addColumn(Bytes.toBytes(FAMILY_NAME_B), Bytes.toBytes(QUALIFIER_NAME_B_RID), Long.valueOf(attributes[3]), Bytes.toBytes(attributes[0]));
    return put;
  }

  public static void main(String[] args) throws IOException {
    if (args == null || args.length != 1)
      throw new IllegalArgumentIOException("path should be offered");
    RecordIngest ingest = new RecordIngest();
    ingest.ingest(args[0]);
  }
}
