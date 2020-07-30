USE salesCdc;

EXEC sys.sp_cdc_enable_db;

EXEC sys.sp_cdc_enable_table
	@source_schema = 'dbo',
	@role_name = NULL,
	@source_name = 'customer',
  	@supports_net_changes = 1;

EXEC sys.sp_cdc_enable_table
	@source_schema = 'dbo',
	@role_name = NULL,
	@source_name = 'sale',
  	@supports_net_changes = 1;