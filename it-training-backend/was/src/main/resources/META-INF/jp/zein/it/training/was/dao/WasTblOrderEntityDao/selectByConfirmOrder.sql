SELECT 
  A.*
 ,B.partner_name as partner_name
FROM
    tbl_order A
LEFT OUTER JOIN
	mst_partner B
ON
	A.partner_id = B.partner_id
AND	B.is_delete = false
WHERE
	A.order_status = /*@jp.zein.it.training.common.util.TrainingConsts$OrderStatus@CONFIRMED.getValue()*/'02'
AND A.is_delete = false
