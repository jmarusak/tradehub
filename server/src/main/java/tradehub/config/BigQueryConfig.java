package tradehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;

@Configuration
public class BigQueryConfig {

  @Bean
  public BigQuery bigQuery() throws BigQueryException {
   
    String project = System.getenv("GOOGLE_CLOUD_PROJECT");
    BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
    return bigquery;
  }
}
