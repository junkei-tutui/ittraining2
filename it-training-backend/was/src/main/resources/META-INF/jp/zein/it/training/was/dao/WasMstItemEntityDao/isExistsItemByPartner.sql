SELECT 
    EXISTS(
    SELECT *    
    FROM
        mst_item
    WHERE partner_id = /*partnerId*/'00011' AND
          is_delete = false
    )