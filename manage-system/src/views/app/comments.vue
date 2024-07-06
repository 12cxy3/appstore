<template>
  <el-drawer v-model="drawerVisible" direction="rtl" @close="drawerClose">
    <template #header>
      <h1>App评论</h1>
    </template>
    <template #default>
      <div ref="commentRef" style="height: calc(100vh - 220px); overflow-y: auto" v-if="commentList.length">
        <div v-for="(item, index) in commentList" :key="index" class="comment-item">
          <div class="start">
            <img style="width: 50px; height: 50px; border-radius: 10px; border: 1px solid #f5f5f5" src="@/assets/images/user.png" />
            <div class="vertical" style="margin-left: 10px">
              <span>{{ item.realName }}</span>
              <el-rate v-model="item.score" disabled />
            </div>
          </div>
          <div style="margin: 10px 0">
            {{ item.comment }}
          </div>
          <div style="color: #565656" class="right">
            <span>{{ item.createDate }}</span>
          </div>
        </div>
        <div class="center load" v-if="commentTotal != 0 && commentList.length < commentTotal" @click="handleScroll">加载更多数据...</div>
      </div>
      <el-empty v-else description="暂无评论" />
      <el-drawer v-model="drawer1Visible" direction="btt" style="width: 30%; margin-left: 70%" size="auto" @close="drawer1Close(ruleFormRef)">
        <template #header>
          <div class="center">
            <h2>写评论</h2>
          </div>
        </template>
        <template #default>
          <el-form :model="commentForm" ref="ruleFormRef">
            <el-form-item prop="score">
              <div class="center" style="margin-bottom: 10px; width: 100%">
                <el-rate v-model="commentForm.score" size="large" />
                <el-text>{{ textList[commentForm.score] }}</el-text>
              </div>
            </el-form-item>
            <el-form-item prop="comment">
              <el-input type="textarea" :rows="8" maxlength="500" show-word-limit resize="none" placeholder="请输入评论" v-model="commentForm.comment"></el-input>
            </el-form-item>
          </el-form>
        </template>
        <template #footer>
          <div class="center">
            <el-button type="primary" style="width: 80%" @click="commentSubmit" :disabled="!commentForm.comment || !commentForm.score">发布</el-button>
          </div>
        </template>
      </el-drawer>
    </template>
    <template #footer>
      <div class="center">
        <el-button v-if="commentCount == 0 && iscomment" type="primary" style="width: 80%" @click="openDrawer">写评论</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<script lang="ts" setup>
import { reactive, ref, toRefs, onMounted, computed } from "vue";
import app from "@/constants/app";
import baseService from "@/service/baseService";
import { ElMessage, FormInstance } from "element-plus";

const emit = defineEmits(["callBack"]);
const commentRef = ref(null);
const ruleFormRef = ref<FormInstance>();
const fileUrl = ref(app.fileApi);
let drawerVisible = ref(false);
let drawer1Visible = ref(false);
let appId = ref(0);
let textList = ref(["点击星星评分", "很差", "不喜欢", "还行", "不错，推荐下载", "相当给力，强烈推荐"]);
let iscomment = ref(false);
let commentCount = ref(0);
let commentList = ref([]);
let screenHeight = ref(750);
let commentForm = ref({
  appId: 0,
  comment: "",
  score: 0
});
let addNum = ref(0);
let commentTotal = ref(0);
let commentDataForm = ref({
  limit: 20,
  appId: 0,
  page: 1
});

const init = (app_id: number, is_comment: boolean) => {
  // window.addEventListener("resize", handleResize());
  appId.value = app_id;
  iscomment.value = is_comment;
  getCommentCount();
  getcommentList(1);
  addNum.value = 0;
  drawerVisible.value = true;
};
const handleResize = () => {
  screenHeight.value = window.innerHeight;
};

defineExpose({
  init
});

const openDrawer = () => {
  drawer1Visible.value = true;
  commentForm.value.appId = appId.value;
};

const drawerClose = () => {
  commentList.value = [];
  commentTotal.value = 0;
  commentDataForm.value.appId = 0;
  commentDataForm.value.page = 1;
  commentForm.value.appId = 0;
  console.log("addNum", addNum.value);
  emit("callBack", addNum.value);
};

const getCommentCount = () => {
  baseService.get("app/appcomment/commentCount", { appId: appId.value }).then((res) => {
    commentCount.value = res.data;
  });
};

const drawer1Close = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};

//抽屉评论触底
const handleScroll = () => {
  let page = commentDataForm.value.page + 1;
  if (commentTotal.value != 0 && commentList.value.length >= commentTotal.value) {
    ElMessage.warning({
      message: "没有更多数据...",
      duration: 1000
    });
  } else {
    getcommentList(page);
  }
};

const getcommentList = (page: number) => {
  drawerVisible.value = true;
  commentDataForm.value.appId = appId.value;
  commentDataForm.value.page = page;
  baseService.get("app/appcomment/page", { ...commentDataForm.value }).then((res) => {
    if (res.code == 0) {
      commentList.value.push(...res.data.list);
      commentTotal.value = res.data.total;
    } else {
      ElMessage.success({
        message: "评论列表获取失败",
        duration: 1000
      });
    }
  });
};

const commentSubmit = () => {
  baseService.post("app/appcomment/", commentForm.value).then((res) => {
    if (res.code == 0) {
      addNum.value = 1;
      ElMessage.success({
        message: "评论成功",
        duration: 500,
        onClose: () => {
          drawer1Visible.value = false;
          commentCount.value = 1;
          commentList.value = [];
          getcommentList(1);
        }
      });
    } else {
      ElMessage.success({
        message: "评论失败",
        duration: 1000
      });
    }
  });
  // drawerVisible.value = false;
};
</script>

<style scoped>
.vertical {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
}

.start {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.center {
  display: flex;
  align-items: center;
  justify-content: center;
}

.right {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.comment-item {
  border: 1px solid #f5f5f5;
  border-radius: 10px;
  padding: 10px;
  background-color: #fefefe;
  margin-bottom: 10px;
}

.load:hover {
  cursor: pointer;
  color: #35bdaf;
}
</style>
