SELECT
  A.stock_capture_id
 ,A.upload_at
 ,A.created_by AS uploaded_user_id
 ,B.user_name  AS uploaded_user_name
 ,A.filename_stock
 ,A.filename_stock_result
 ,A.filename_cannotreserve
 FROM tbl_stock_capture A
 LEFT OUTER JOIN mst_user B ON
      A.created_by = B.user_id
 WHERE
    A.is_delete = false
 ORDER BY A.upload_at DESC