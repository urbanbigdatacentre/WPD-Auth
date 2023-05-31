# wpdAuth.load module
This module is responsible to load the organizations dump from [Cemaden Educations web site](http://educacao.cemaden.gov.br/site/organization/). The dump is loaded from the csv file [here](https://github.com/IGSD-UoW/wpdAuth/blob/main/load/educacao.cemaden-organization-dump.csv) in this folder following the [ddl structure](https://github.com/IGSD-UoW/wpdAuth/blob/main/db/ddl.sql) to the entity 'educemaden_organizations'. In order to avoid SQL errors, there are no indexes on 'educemaden_organizations' entity, and for each load this entity should be truncated.

# Dependencies

- Make sure the setup was finished successfully according to the instruction in the root [README file](https://github.com/IGSD-UoW/wpdAuth/blob/main/README.md).

# How to run this load

Once the project setup was finished successfully, follow the steps below:

- Start the PostgreSQL and run the scripts to create the database and get the load data.

```console
  $ psql -d wpdauth -c "TRUNCATE TABLE auth.educemaden_organizations;"
  $ psql -d wpdauth -c "\COPY auth.educemaden_organizations(id,active,name,creation_date,inep_code,phone,type,website,login,address,responsible) FROM '/<absolute path>/educacao.cemaden-organization-dump.csv' DELIMITER ',' CSV HEADER;"
  $ psql -d wpdauth -c "SELECT * FROM auth.educemaden_organizations;"
```

# How to append schools

To avoid truncating the entire table and load the data again, it is possible to append new schools to the table. To do so, follow the steps below:

- Start the PostgreSQL and run the scripts to append the schools in the append file.

```console
  $ psql -d wpdauth -c "\COPY auth.educemaden_organizations(id,active,name,creation_date,inep_code,phone,type,website,login,address,responsible) FROM '/<absolute path>/educacao.cemaden-organization-append.csv' DELIMITER ',' CSV HEADER;"
  $ psql -d wpdauth -c "SELECT * FROM auth.educemaden_organizations;"
```

Alternatively, it is possible to use the [pgAdmin](https://www.pgadmin.org/) to run an script similar to the one below:

insert
```sql
INSERT INTO 
  auth.educemaden_organizations (id,active,name,creation_date,inep_code,phone,type,website,login,address,responsible)
VALUES ('idvalue', 'True/False', 'Org Name','YYYY-MM-dd hh:mm:ss.0','1111111','(11) 223344556','SCHOOL/CIVIL_DEFENCE','NULL','111','112','112');
```
