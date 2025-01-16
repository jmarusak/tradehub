package tradehub;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class SimpleBigQueryClient
{
  public static void main(String... args) {
    String projectId = System.getenv("GOOGLE_CLOUD_PROJECT");
    System.out.println("Project ID: " + projectId);

    getAllParties(projectId);

    System.exit(0);
  }

  public static void getAllParties(String projectId) {
    try {
      BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

      QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT * FROM tradehub.party")
        .setUseLegacySql(false)
        .build();

      JobId jobId = JobId.newBuilder().setProject(projectId).build();

      Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
      
      queryJob = queryJob.waitFor();

      if (queryJob == null) {
        throw new RuntimeException("Job no longer exists");
      } else if (queryJob.getStatus().getError() != null) {
        throw new RuntimeException(queryJob.getStatus().getError().toString());
      }

      TableResult result = queryJob.getQueryResults();

      for (FieldValueList row : result.iterateAll()) {
        String partyId = row.get("party_id").getStringValue();
        String name = row.get("name").getStringValue();
        String email = row.get("email").getStringValue();
        System.out.printf("%s | %s | %s\n", partyId, name, email);
      }

    }
    catch (BigQueryException | InterruptedException e) {
      System.out.println("SimpleBigQuery.getAllParties failed: \n" + e.toString());
    }
  }
}
