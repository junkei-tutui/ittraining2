SELECT
	A.*,
	B.item_code,
	C.item_name,
	B.color_code,
	D.color_name,
	B.size_code,
	E.size_name,
	C.classification_code,
	F1.generic_code_name AS classification_name,
	C.season_code,
	F2.generic_code_name AS season_name
FROM
	tbl_order_detail A
LEFT OUTER JOIN
	mst_product B
ON
	A.product_code = B.product_code
AND B.is_delete = false
LEFT OUTER JOIN
	mst_item C
ON
	B.item_code = C.item_code
AND C.is_delete = false
LEFT OUTER JOIN
	mst_color D
ON
	B.color_code = D.color_code
AND D.is_delete = false
LEFT OUTER JOIN
	mst_size E
ON
	B.size_code = E.size_code
AND E.is_delete = false
LEFT OUTER JOIN
	mst_generic_code_detail F1
ON
	C.classification_code = F1.generic_code
AND F1.generic_code_type_id = /*@jp.zein.it.training.common.util.TrainingConsts$GenericCodeType@CLASSIFICATION_CODE*/'002'
AND F1.is_delete = false
LEFT OUTER JOIN
	mst_generic_code_detail F2
ON
	C.season_code = F2.generic_code
AND F2.generic_code_type_id = /*@jp.zein.it.training.common.util.TrainingConsts$GenericCodeType@SEASON_CODE*/'003'
AND F2.is_delete = false
WHERE
	A.order_no = /* orderNo */1
AND A.is_delete = false
