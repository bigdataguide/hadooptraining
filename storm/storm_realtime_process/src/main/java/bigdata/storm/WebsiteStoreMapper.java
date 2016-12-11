package bigdata.storm;

import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.tuple.ITuple;

/**
 * Created by qianxi.zhang on 11/26/16.
 */
public class WebsiteStoreMapper implements RedisStoreMapper {
  private RedisDataTypeDescription description;
  private final String hashKey = "website";

  public WebsiteStoreMapper() {
    description =
        new RedisDataTypeDescription(RedisDataTypeDescription.RedisDataType.HASH, hashKey);
  }

  public RedisDataTypeDescription getDataTypeDescription() {
    return description;
  }

  public String getKeyFromTuple(ITuple iTuple) {
    return iTuple.getStringByField("website");
  }

  public String getValueFromTuple(ITuple iTuple) {
    return iTuple.getStringByField("price");
  }
}
