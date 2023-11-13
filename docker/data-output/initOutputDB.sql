CREATE DATABASE [demodb]
GO

USE [demodb];
GO

create schema cdcdemo
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