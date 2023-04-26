SELECT 
  A.order_no
 ,A.line_no
 ,A.product_code
 ,B.item_code
 ,C.item_name
 ,B.color_code
 ,D.color_name
 ,B.size_code
 ,E.size_name
 ,A.quantity
 ,A.selling_price
 ,A.selling_price_intax
 ,A.order_line_status
 ,A.subtotal_selling_price
 ,A.subtotal_selling_price_intax
FROM tbl_order_detail A
INNER JOIN mst_product B ON
     A.order_no = /* orderNo */1
 AND A.is_delete = false
 AND B.product_code = A.product_code
 AND B.is_delete = false
INNER JOIN mst_item C ON
     C.item_code = B.item_code
 AND C.is_delete = false
LEFT OUTER JOIN mst_color D ON
     D.color_code = B.color_code
 AND D.is_delete = false
LEFT OUTER JOIN mst_size E ON
     E.size_code = B.size_code
 AND E.is_delete = false
ORDER BY A.line_no
