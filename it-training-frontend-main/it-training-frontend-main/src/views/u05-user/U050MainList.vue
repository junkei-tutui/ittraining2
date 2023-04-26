<template>
  <!--
   -- U-050 : ユーザー検索・一覧
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="18" class="card-title page-title">
            <span>ユーザー検索・一覧</span>
          </el-col>
          <!-- （空きエリア） -->
          <el-col :span="3"><br /></el-col>
          <!--追加ボタン -->
          <el-col :span="3" align="center">
            <el-button
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="training-main-button"
              @click="onAddUser"
            >追加</el-button>
          </el-col>
        </el-row>
      </div>
      <!--画面コンテンツエリア -->
      <div class="card-content" style="min-height: calc(100vh - 210px)">
        <!--検索部 -->
        <div class="group-box">
          <span class="group-box-title">
            <span class="item-header">検索条件</span>
          </span>
          <el-form ref="form" label-position="left" :model="form">
            <el-row :gutter="10">
              <!-- ユーザー名 -->
              <el-col :span="3">
                <span class="grid-content-header">ユーザー名</span>
              </el-col>
              <el-col :span="4">
                <div class="grid-content">
                  <tr-input
                    v-model="form.likeUserName"
                    prop-name="likeUserName"
                    :maxlength="64"
                    item-label="ユーザー名"
                    check-rule="default" />
                </div>
              </el-col>
              <!-- （空き領域） -->
              <el-col :span="14"><br /></el-col>
              <!-- 検索ボタン -->
              <el-col :span="3">
                <div class="grid-content">
                  <el-button
                    class="inner-card-button"
                    type="training-sub-button"
                    @click="showUserList"
                  >検索</el-button>
                </div>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <!--一覧部 -->
        <el-table
          v-loading="listLoading"
          :data="
            userList ?
              userList.slice(
                (currentPage - 1) * pagesize,
                currentPage * pagesize
              ) : null
          "
          border
          fit
          highlight-current-row
          style="width: 100%; padding: 5px;"
        >
          <!-- 行番号 -->
          <el-table-column
            width="50px"
            header-align="center"
            label="#"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{ pagesize * (currentPage-1) + scope.$index + 1 }}</span>
            </template>
          </el-table-column>

          <!-- ユーザーID -->
          <el-table-column
            header-align="center"
            align="left"
            label="ユーザーID"
            prop="userId"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.userId }}</span>
            </template>
          </el-table-column>

          <!-- ユーザー名 -->
          <el-table-column
            header-align="center"
            align="left"
            label="ユーザー名"
            prop="userName"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.userName }}</span>
            </template>
          </el-table-column>

          <!-- 編集 -->
          <el-table-column
            width="100px"
            prop="oper"
            align="center"
            label="編集"
          >
            <template slot-scope="scope">
              <el-button
                class="icon-button"
                type="primary"
                size="mini"
                icon="training-btn-line-icon el-icon-edit-outline"
                round
                @click="onEditUser(scope.row)"
              />
            </template>
          </el-table-column>

          <!-- 削除 -->
          <el-table-column
            width="100px"
            prop="oper"
            align="center"
            label="削除"
          >
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.userId !== myUserId"
                v-loading.fullscreen.lock="fullscreenLoading"
                class="icon-button"
                type="danger"
                size="mini"
                icon="training-btn-line-icon el-icon-close"
                circle
                @click="onDeleteUser(scope.row)"
              />
            </template>
          </el-table-column>
        </el-table>
        <!-- ページネーション部 -->
        <el-row style="padding-top: 20px">
          <el-col :span="24" style="text-align: right">
            <el-pagination
              background
              layout="total, prev, pager, next"
              :page-size="pagesize"
              :total="userList ? userList.length : 0"
              @current-change="onCurrentChange"
            />
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b501GetUserList, b502DeleteUser } from "@/api/user";
import { setViewStatus, getViewStatus, setParameter } from "@/utils/support";
import { messageSuccess } from "@/utils/support";

export default {
  // --- 画面名 ---
  name: "UserList",
  // --- 表示フィルター関数定義 ---
  filters: {},
  // --- データプロパティ定義 ---
  data() {
    return {
      // リスト読み込み用ローディング画面設定フラグ
      listLoading: true,
      // ローディング画面設定（ユーザー削除時に設定）
      fullscreenLoading: false,
      // 入力フォーム
      form: {
        likeUserName: null,
      },
      // ユーザー一覧情報
      userList: [],
      // ページネーション情報
      currentPage: 1,
      pagesize: 10,
    };
  },
  // --- 算出プロパティ定義 ---
  computed: {
    /**
     * 自身のユーザーID
     */
    myUserId: {
      get() {
        // ストアより自身のユーザーIDを取得する
        return this.$store.getters.id;
      },
    },
  },
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
      // 画面初期情報は同期して取得する
      (async () => {
        // 検索条件パラメータを取得する。
        const conditions = await getViewStatus("UserListConditions");
        if (conditions) {
          // 保持していた検索条件（ユーザー名）を初期入力値として設定
          this.form.likeUserName = conditions.likeUserName;
        }
        // ユーザー一覧を取得する
        await this.showUserList();
      })();
    },
    /**
     *  ユーザー一覧取得
     */
    async showUserList() {
      this.listLoading = true;
      // B501:ユーザー検索一覧取得
      const paramater = {
        likeUserName: this.form.likeUserName,
      };
      await b501GetUserList(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`userList:${JSON.stringify(response.data)}`);

        // ページネイションを行っている一覧の場合はページNoを1に戻す
        this.currentPage = 1;
        // 一覧データを画面情報に設定
        this.userList = response.data;
        // ローディング解除
        this.listLoading = false;
        // 検索時の条件を保持する。
        setViewStatus("UserListConditions", {
          // 検索条件（ユーザー名）を保持
          likeUserName: this.form.likeUserName,
        });
      }).catch(
        // エラーの場合はローディングのみ解除
        this.listLoading = false,
      );
    },
    /**
     * ユーザー追加
     */
    onAddUser() {
      // 取引先追加画面
      this.$router.push({ name: "UserInsert" });
      setParameter("UserEdit", {
        // ユーザーIDを未設定で起動する
        userId: null,
      });
    },
    /**
     * ユーザー編集
     * @param { Object } row - 選択行情報
     */
    onEditUser(row) {
      // ユーザー編集画面
      this.$router.push({ name: "UserEdit" });
      setParameter("UserEdit", {
        // ユーザーIDを設定
        userId: row.userId,
      });
    },
    /**
     * ユーザー削除
     * @param { Object } row - 選択行情報
     */
    onDeleteUser(row) {
      // 確認メッセージの表示
      this.$confirm(`ユーザーを削除します。<br>一度削除すると戻すことはできません<br>よろしいですか。`, {
        dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
        type: "warning",
      }).then(() => {
        // 画面ロック
        this.fullscreenLoading = true;
        // B502:ユーザー削除
        const parameter = {
          userId: row.userId,
        };
        b502DeleteUser(parameter).then((response) => {
          // 完了メッセージを表示
          messageSuccess("ユーザーを削除しました。");
          // 画面ロック解除
          this.fullscreenLoading = false;
          // 一覧画面を再表示する
          this.init();
        }).catch(
          // エラーの場合は画面ロック解除のみ
          this.fullscreenLoading = false,
        );
      }).catch((err) => { if (err !== "cancel") throw err; });
    },
    /**
     *  ページネーション処理
     * @param { Number } currentPage - ページ番号
     */
    onCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
  },
};
</script>

<style scoped>
</style>
