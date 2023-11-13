CREATE DATABASE [demodb]
GO

USE [demodb];
GO

create schema cdcdemo
GO

CREATE TABLE cdcdemo.product (
                         Id INT NOT NULL IDENTITY,
                         Name TEXT NOT NULL,
                         Description TEXT NOT NULL,
                         PRIMARY KEY (Id)
);
GO

sys.sp_cdc_enable_db
GO

CREATE LOGIN DemoAdmin
    WITH PASSWORD    = N'Password123!',
    CHECK_POLICY     = OFF,
    CHECK_EXPIRATION = OFF;
GO

EXEC sp_addsrvrolemember
     @loginame = N'DemoAdmin',
     @rolename = N'sysadmin';
GO


EXEC sys.sp_cdc_enable_table
     @source_schema = N'cdcdemo',
     @source_name   = N'product',
     @role_name     = NULL,
     @supports_net_changes = 1;
GO	
	 