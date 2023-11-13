#!/bin/bash

echo "waiting 20s for the SQL Server to come up..."
sleep 20s

echo "running set up script..."
chmod 777 initOutputDB.sql
/opt/mssql-tools/bin/sqlcmd -S "localhost,1433" -U sa -P "${MSSQL_SA_PASSWORD}" -d master -i initOutputDB.sql
