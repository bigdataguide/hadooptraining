package bigdata.hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.exceptions.IllegalArgumentIOException;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by qianxi.zhang on 5/2/17.
 */
public class ProfileIngest extends Ingest {
  public static final String QUALIFIER_NAME_P_NAME = "name";
  public static final String QUALIFIER_NAME_P_GENDER = "gender";
  public static final String QUALIFIER_NAME_P_BIRTH = "birth";
  public static final String QUALIFIER_NAME_P_PROVINCE = "province";

  public Put process(String line) {
    String[] attributes = line.split(SEPARATOR);
    Put put = new Put(Bytes.toBytes(attributes[0]));
    put.addColumn(Bytes.toBytes(FAMILY_NAME_P), Bytes.toBytes(QUALIFIER_NAME_P_NAME), Bytes.toBytes(attributes[1]));
    put.addColumn(Bytes.toBytes(FAMILY_NAME_P), Bytes.toBytes(QUALIFIER_NAME_P_GENDER), Bytes.toBytes(attributes[2]));
    put.addColumn(Bytes.toBytes(FAMILY_NAME_P), Bytes.toBytes(QUALIFIER_NAME_P_BIRTH), Bytes.toBytes(attributes[3]));
    put.addColumn(Bytes.toBytes(FAMILY_NAME_P), Bytes.toBytes(QUALIFIER_NAME_P_PROVINCE), Bytes.toBytes(attributes[4]));

    return put;
  }

  public static void main(String[] args) throws IOException {
    if (args == null || args.length != 1)
      throw new IllegalArgumentIOException("path should be offered");
    ProfileIngest ingest = new ProfileIngest();
    ingest.ingest(args[0]);
  }
}
