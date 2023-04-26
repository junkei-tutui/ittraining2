SET SESSION FOREIGN_KEY_CHECKS=0;

CREATE DATABASE IF NOT EXISTS training;
USE training;


/* Drop Tables */

DROP TABLE IF EXISTS tbl_order_detail;
DROP TABLE IF EXISTS mst_product;
DROP TABLE IF EXISTS mst_color;
DROP TABLE IF EXISTS mst_generic_code_detail;
DROP TABLE IF EXISTS mst_generic_code;
DROP TABLE IF EXISTS mst_item;
DROP TABLE IF EXISTS tbl_order;
DROP TABLE IF EXISTS mst_partner;
DROP TABLE IF EXISTS mst_size;
DROP TABLE IF EXISTS mst_user;
DROP TABLE IF EXISTS tbl_stock_capture;




/* Create Tables */

-- カラーマスタ
CREATE TABLE mst_color
(
	color_code varchar(2) NOT NULL COMMENT 'カラーコード',
	color_name varchar(64) COMMENT 'カラー名',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (color_code)
) COMMENT = 'カラーマスタ';


-- 汎用コードマスタ
CREATE TABLE mst_generic_code
(
	generic_code_type_id varchar(3) NOT NULL COMMENT '汎用コード種類ID',
	generic_code_type_name varchar(64) COMMENT '汎用コード種類名',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (generic_code_type_id)
) COMMENT = '汎用コードマスタ';


-- 汎用コード詳細
CREATE TABLE mst_generic_code_detail
(
	generic_code_type_id varchar(3) NOT NULL COMMENT '汎用コード種類ID',
	generic_code varchar(4) NOT NULL COMMENT '汎用コード',
	generic_code_name varchar(64) COMMENT '汎用コード名',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (generic_code_type_id, generic_code)
) COMMENT = '汎用コード詳細';


-- 商品マスタ
CREATE TABLE mst_item
(
	item_code varchar(8) NOT NULL COMMENT '商品コード',
	item_name varchar(64) COMMENT '商品名',
	category_code varchar(4) COMMENT 'カテゴリコード',
	classification_code varchar(4) COMMENT '分類コード',
	season_code varchar(3) COMMENT 'シーズンコード',
	partner_id varchar(16) NOT NULL COMMENT '取引先ID',
	purchase_price decimal(10) COMMENT '仕入価格（税抜）',
	purchase_price_intax decimal(11) COMMENT '仕入価格（税込）',
	selling_price decimal(10) COMMENT '販売価格（税抜）',
	selling_price_intax decimal(11) COMMENT '販売価格（税込）',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (item_code)
) COMMENT = '商品マスタ';


-- 取引先マスタ
CREATE TABLE mst_partner
(
	partner_id varchar(16) NOT NULL COMMENT '取引先ID',
	partner_name varchar(64) NOT NULL COMMENT '取引先名',
	partner_type int(1) DEFAULT 0 COMMENT '取引区分',
	zip varchar(7) COMMENT '郵便番号',
	address varchar(256) COMMENT '住所',
	tel_no varchar(13) COMMENT '電話番号',
	fax_no varchar(13) COMMENT 'FAX番号',
	responsible_party varchar(64) COMMENT '担当者',
	mail_address varchar(64) COMMENT 'メールアドレス',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (partner_id)
) COMMENT = '取引先マスタ';


-- SKUマスタ
CREATE TABLE mst_product
(
	product_code varchar(12) NOT NULL COMMENT 'SKUコード',
	item_code varchar(8) NOT NULL COMMENT '商品コード',
	color_code varchar(2) NOT NULL COMMENT 'カラーコード',
	size_code varchar(2) NOT NULL COMMENT 'サイズコード',
	purchase_price decimal(10) COMMENT '仕入価格（税抜）',
	purchase_price_intax decimal(11) COMMENT '仕入価格（税込）',
	selling_price decimal(10) COMMENT '販売価格（税抜）',
	selling_price_intax decimal(11) COMMENT '販売価格（税込）',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (product_code)
) COMMENT = 'SKUマスタ';


-- サイズマスタ
CREATE TABLE mst_size
(
	size_code varchar(2) NOT NULL COMMENT 'サイズコード',
	size_name varchar(64) COMMENT 'サイズ名',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (size_code)
) COMMENT = 'サイズマスタ';


-- ユーザーマスタ
CREATE TABLE mst_user
(
	user_id varchar(64) NOT NULL COMMENT 'ユーザーID',
	user_password varchar(256) NOT NULL COMMENT 'ユーザーパスワード',
	user_name varchar(64) COMMENT 'ユーザー名',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (user_id)
) COMMENT = 'ユーザーマスタ';


-- 受注テーブル
CREATE TABLE tbl_order
(
	order_no int(10) NOT NULL AUTO_INCREMENT COMMENT '受注番号',
	order_subject varchar(128) COMMENT '受注件名',
	partner_id varchar(16) NOT NULL COMMENT '取引先ID',
	order_date date NOT NULL COMMENT '受注日',
	delivery_date date COMMENT '納期',
	delivery_zip varchar(7) COMMENT '納品先郵便番号',
	delivery_address varchar(256) COMMENT '納品先住所',
	responsible_party varchar(64) COMMENT '担当者',
	order_status varchar(2) DEFAULT '01' NOT NULL COMMENT '受注受付ステータス',
	total_order_amount decimal(16) DEFAULT 0 NOT NULL COMMENT '合計受注金額（税抜）',
	total_order_amount_intax decimal(16) DEFAULT 0 NOT NULL COMMENT '合計受注金額（税込）',
	client_order_no varchar(10) COMMENT '客先注文番号',
	note varchar(512) COMMENT '備考',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (order_no)
) COMMENT = '受注テーブル';


-- 受注明細テーブル
CREATE TABLE tbl_order_detail
(
	order_no int(10) NOT NULL COMMENT '受注番号',
	line_no int(3) NOT NULL COMMENT '明細番号',
	product_code varchar(12) NOT NULL COMMENT 'SKUコード',
	quantity decimal(3) DEFAULT 0 NOT NULL COMMENT '数量',
	selling_price decimal(10) DEFAULT 0 NOT NULL COMMENT '販売価格（税抜）',
	selling_price_intax decimal(11) DEFAULT 0 NOT NULL COMMENT '販売価格（税込）',
	order_line_status varchar(2) DEFAULT '01' NOT NULL COMMENT '受注明細ステータス',
	subtotal_selling_price decimal(14) DEFAULT 0 NOT NULL COMMENT '小計販売価格（税抜）',
	subtotal_selling_price_intax decimal(14) DEFAULT 0 NOT NULL COMMENT '小計販売価格（税込）',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (order_no, line_no)
) COMMENT = '受注明細テーブル';


-- 在庫取込管理テーブル
CREATE TABLE tbl_stock_capture
(
	stock_capture_id bigint NOT NULL AUTO_INCREMENT COMMENT '在庫取込管理ID',
	upload_at datetime COMMENT 'アップロード日時',
	filename_stock varchar(128) COMMENT '在庫ファイル名',
	filename_stock_result varchar(128) COMMENT '引当後在庫ファイル名',
	filename_cannotreserve varchar(128) COMMENT '引当不可リスト名',
	is_delete tinyint(1) DEFAULT false NOT NULL COMMENT '削除フラグ',
	created_at datetime NOT NULL COMMENT '登録日時',
	created_by varchar(64) COMMENT '登録者',
	modify_at datetime COMMENT '更新日時',
	modify_by varchar(64) COMMENT '更新者',
	PRIMARY KEY (stock_capture_id)
) COMMENT = '在庫取込管理テーブル';



/* Create Foreign Keys */

ALTER TABLE mst_product
	ADD FOREIGN KEY (color_code)
	REFERENCES mst_color (color_code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mst_generic_code_detail
	ADD FOREIGN KEY (generic_code_type_id)
	REFERENCES mst_generic_code (generic_code_type_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mst_product
	ADD FOREIGN KEY (item_code)
	REFERENCES mst_item (item_code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mst_item
	ADD FOREIGN KEY (partner_id)
	REFERENCES mst_partner (partner_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tbl_order
	ADD FOREIGN KEY (partner_id)
	REFERENCES mst_partner (partner_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tbl_order_detail
	ADD FOREIGN KEY (product_code)
	REFERENCES mst_product (product_code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mst_product
	ADD FOREIGN KEY (size_code)
	REFERENCES mst_size (size_code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tbl_order_detail
	ADD FOREIGN KEY (order_no)
	REFERENCES tbl_order (order_no)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



