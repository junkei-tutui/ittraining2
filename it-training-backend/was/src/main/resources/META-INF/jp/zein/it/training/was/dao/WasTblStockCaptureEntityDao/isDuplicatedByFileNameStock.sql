SELECT 
    EXISTS(
    SELECT *    
    FROM
        tbl_stock_capture
    WHERE filename_stock = /*filenameStock*/'00011' AND
          is_delete = false
    )
