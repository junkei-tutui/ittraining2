SELECT
  A.item_code
 ,A.item_name
 ,A.category_code
 ,A.classification_code
 ,A.season_code
 ,A.partner_id AS supplier_id
 ,B.partner_name AS supplier_name
 ,A.purchase_price
 ,A.purchase_price_intax
 ,A.selling_price
 ,A.selling_price_intax
FROM mst_item A
INNER JOIN mst_partner B ON
 /*%if targetClassificationCode != null && targetClassificationCode.length() > 0 */
  A.classification_code = /* targetClassificationCode */'0002' AND
 /*%end*/
 /*%if likeItemName != null && likeItemName.length() > 0 */
  A.item_name like /*'%' + likeItemName + '%'*/'%目%' AND
 /*%end*/
      A.is_delete = false
  AND B.partner_id = A.partner_id
  AND B.is_delete = false
 ORDER BY A.item_name