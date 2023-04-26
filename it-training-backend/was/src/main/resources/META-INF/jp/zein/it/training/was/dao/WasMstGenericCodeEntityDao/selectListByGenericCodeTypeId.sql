SELECT B.* FROM mst_generic_code A
 INNER JOIN mst_generic_code_detail B
 ON 
     A.generic_code_type_id = /* genericCodeTypeId */'001'
 AND A.is_delete = false
 AND B.generic_code_type_id = A.generic_code_type_id
 AND B.is_delete = false
 ORDER BY B.generic_code
 