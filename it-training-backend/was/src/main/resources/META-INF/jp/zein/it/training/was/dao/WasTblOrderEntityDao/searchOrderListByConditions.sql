SELECT 
  A.order_no
 ,A.order_subject
 ,A.partner_id as client_id
 ,B.partner_name as client_name
 ,A.delivery_zip
 ,A.delivery_address
 ,A.order_date
 ,A.delivery_date
 ,A.order_status
 ,A.total_order_amount
 ,A.total_order_amount_intax
 FROM tbl_order A
 INNER JOIN mst_partner B 
 ON
 /*%if targetOrderDate != null */
  A.order_date = /* targetOrderDate */'2021-05-01' AND
 /*%end*/
 /*%if targetOrderStatus != null && targetOrderStatus.length() > 0 */
  A.order_status = /* targetOrderStatus */'0' AND
 /*%end*/
  A.is_delete = false AND
  B.partner_id = A.partner_id AND 
 /*%if likeClientName != null && likeClientName.length() > 0 */
  B.partner_name like /*'%' + likeClientName + '%'*/'%取引先%' AND
 /*%end*/
  B.is_delete = false
 ORDER BY A.order_date DESC
