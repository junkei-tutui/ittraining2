SELECT 
    EXISTS(
    SELECT *    
    FROM
        tbl_order
    WHERE partner_id = /*partnerId*/'00011' AND
          is_delete = false
    )
