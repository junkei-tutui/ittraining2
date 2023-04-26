<template>
  <!--
   -- U-021 : 取引先詳細
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="18" class="card-title page-title">
            <span>取引先詳細</span>
          </el-col>
          <!--編集ボタン -->
          <el-col :span="3" align="right">
            <el-button
              class="card-button"
              type="training-main-button"
              @click="onEdit"
            >編集</el-button>
          </el-col>
          <!--戻るボタン -->
          <el-col :span="3" align="right">
            <el-button
              class="card-button"
              type="training-main-button"
              @click="onBackList"
            >戻る</el-button>
          </el-col>
        </el-row>
      </div>
      <!--画面コンテンツエリア -->
      <div class="card-content" style="min-height: calc(100vh - 210px)">
        <!--取引先情報部 -->
        <!-- 取引先ID -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">取引先ID</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ partnerDetail.partnerId }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 取引先名 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">取引先名</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ partnerDetail.partnerName }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 住所 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">住所</span>
          </el-col>
          <el-col :span="8">
            <div class="grid-content">
              <span>{{ partnerDetail.address | addressWithZip(partnerDetail.zip) }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 電話番号 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">電話番号</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ partnerDetail.telNo }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- FAX -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">ＦＡＸ</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ partnerDetail.faxNo }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 取引先担当者 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">取引先担当者</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ partnerDetail.responsibleParty }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- メールアドレス -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">メールアドレス</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ partnerDetail.mailAddress }}</span>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b211GetPartnerDetail } from "@/api/partner";
import { getParameter, setParameter } from "@/utils/support";

export default {
  // --- 画面名 ---
  name: "PartnerInquiry",
  // --- 表示フィルター関数定義 ---
  filters: {
    /**
     * 郵便番号付き住所表示フィルター
     * @param {Number} address 住所
     * @param {Number} zipCode 郵便番号
     * @returns {String} - 郵便番号付き住所
     */
    addressWithZip: (address, zipCode) => {
      let res = address;
      if (zipCode) {
        res = `〒${zipCode.slice(0, 3)}-${zipCode.slice(3, zipCode.length)} ${res}`;
      }
      return res;
    },
  },
  // --- データプロパティ定義 ---
  data() {
    return {
      // 取引先情報
      partnerDetail: {},
    };
  },
  // --- 算出プロパティ定義 ---
  computed: {},
  // --- 画面生成イベント ---
  created() {
    // 初期表示処理実行
    this.init();
  },
  // --- メソッド定義 ---
  methods: {
    /**
     *  初期表示
     */
    init() {
      // 画面間パラメータの取得
      const params = getParameter("PartnerEdit");
      // 画面間パラメータチェック
      if ((!params.partnerId) || (params.partnerId.length <= 0)) {
        // 取引先IDが設定していない場合はエラーページへ遷移する。
        location.replace("/400");
        return;
      }
      // 画面初期情報は同期して取得する
      (async () => {
        // 取引先詳細情報を取得する
        await this.showPartnerDetail(params.partnerId);
      })();
    },
    /**
     *  取引先詳細情報取得
     *  @param {String} partnerId 取引先ID
     */
    async showPartnerDetail(partnerId) {
      // B211:取引先詳細取得
      const paramater = {
        partnerId: partnerId,
      };
      await b211GetPartnerDetail(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`partnerDetail：${JSON.stringify(response.data)}`);

        // 取得した取引先情報をそのままDOMに設定
        this.partnerDetail = response.data;
      }).catch(
        // 特に処理なし
      );
    },
    /**
     * 取引先編集画面へ遷移
     */
    onEdit() {
      // 取引先編集画面
      this.$router.push({ name: "PartnerEdit" });
      setParameter("PartnerEdit", {
        // 取引先IDを設定する。
        partnerId: this.partnerDetail.partnerId,
      });
    },
    /**
     * 一覧へ戻る
     */
    onBackList() {
      this.$router.push({ name: "PartnerList" });
    },
  },
};
</script>

<style scoped>
</style>
