CREATE OR REPLACE TABLE `tradehub.party` (
  party_id STRING,
  name STRING,
  email STRING
);

INSERT INTO `tradehub.party` (party_id, name, email) 
  VALUES ('1', 'John Doe', 'john.doe@example.com'),
         ('2', 'Jane Doe', 'jane.doe@example.com'),
         ('3', 'Peter Pan', 'peter.pan@example.com');
