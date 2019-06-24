/*
 Navicat Premium Data Transfer

 Source Server         : Lineview
 Source Server Type    : SQL Server
 Source Server Version : 14003048
 Source Host           : 10.99.255.254:1433
 Source Catalog        : Delai
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 14003048
 File Encoding         : 65001

 Date: 14/01/2019 09:16:30
*/


-- ----------------------------
-- Table structure for Bit_Signal_Config
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[Bit_Signal_Config]') AND type IN ('U'))
	DROP TABLE [dbo].[Bit_Signal_Config]
GO

CREATE TABLE [dbo].[Bit_Signal_Config] (
  [Id] int primary key identity(1,1),
  [Parent_Name] varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [Key] varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [PLC_Address] varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [Remark] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [Val] int  NOT NULL,
  [Create_Time] datetime  NOT NULL,
  [Last_Update_Time] datetime  NOT NULL,
  [Enable] tinyint  NOT NULL
)
GO

ALTER TABLE [dbo].[Bit_Signal_Config] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'Id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'组信号',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'Parent_Name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'实际信号',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'Key'
GO

EXEC sp_addextendedproperty
'MS_Description', N'PLC地址',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'PLC_Address'
GO

EXEC sp_addextendedproperty
'MS_Description', N'备注',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'Remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'位值',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'Val'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'Create_Time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'Last_Update_Time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否激活',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'COLUMN', N'Enable'
GO


-- ----------------------------
-- Indexes structure for table Bit_Signal_Config
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [IDX_Key_ParentName]
ON [dbo].[Bit_Signal_Config] (
  [Key] ASC,
  [Parent_Name] ASC
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'Bit_Signal_Config',
'INDEX', N'IDX_Key_ParentName'
GO

