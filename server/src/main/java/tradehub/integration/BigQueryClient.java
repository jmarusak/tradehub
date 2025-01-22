package tradehub.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

@Component
public class BigQueryClient {

  @Autowired
  private BigQuery bigQuery;

  public TableResult execute(String query) {
    QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

    TableResult result;
    try {
      result = bigQuery.query(queryConfig);
    }
    catch (InterruptedException | BigQueryException e) {
      throw new RuntimeException(e);
    }

    return result;
  }
}
