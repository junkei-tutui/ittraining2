SELECT 
    EXISTS(
    SELECT *    
    FROM
        tbl_order_detail
    WHERE product_code IN (
    			SELECT product_code 
    			FROM mst_product 
    			WHERE item_code = /*itemCode*/'01001211' AND 
    				  is_delete = true
    	  ) AND
          is_delete = false
    )
