select
  /*%expand*/*
from
  tbl_stock_capture
where
  stock_capture_id = /* stockCaptureId */1
  and is_delete = 0
