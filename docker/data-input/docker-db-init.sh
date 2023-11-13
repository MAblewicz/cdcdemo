#!/bin/bash

echo "waiting 20s for the SQL Server to come up..."
sleep 20s

echo "running set up script..."
chmod 777 initInputDB.sql
/opt/mssql-tools/bin/sqlcmd -S "localhost" -U sa -P "${MSSQL_SA_PASSWORD}" -d master -i initInputDB.sql
