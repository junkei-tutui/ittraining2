SELECT 
    EXISTS(
    SELECT *    
    FROM
        tbl_order_detail
    WHERE product_code = /*productCode*/'010012110101' AND
          is_delete = false
    )
