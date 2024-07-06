<template>
  <el-card shadow="never" class="aui-card--fill" style="height: 100%; position: relative">
    <el-tabs v-model="dataForm.type" type="card" class="demo-tabs" @tab-change="query">
      <el-tab-pane v-for="item in typeList" :key="item.value" :label="item.label" :name="item.value"> </el-tab-pane>
    </el-tabs>
    <div class="input_ss">
      <el-input v-model="dataForm.appName" placeholder="输入App名称进行查询" clearable @change="query"></el-input>
    </div>
    <div v-if="dataList.length" class="app_list" v-infinite-scroll="handleScroll">
      <div v-for="(item, index) in dataList" :key="item" class="app_item" @click="clickItem(item)">
        <img :src="`${fileUrl}${item.icon}`" style="width: 80px; height: 80px; border-radius: 10px" />
        <el-tooltip class="box-item" effect="light" :content="item.name" placement="top">
          <span class="item-name">{{ item.name }}</span>
        </el-tooltip>
      </div>
    </div>
    <el-empty v-else description="该分类下暂无App" />

    <el-dialog v-model="dialogVisible" title="App详情" width="500" @close="dialogClose">
      <div class="detail">
        <img :src="`${fileUrl}${appDetail.icon}`" style="width: 100px; border-radius: 20px" />
        <div style="margin-left: 10px; width: 60%">
          <div style="font-size: 17px; font-weight: 600">{{ appDetail.name }}</div>
          <div>开发者： {{ appDetail.developer }}</div>
          <div>大 小：{{ calculate(appDetail.size) }}</div>
        </div>
        <div style="width: 20%; display: flex; justify-content: flex-end; align-items: center">
          <el-icon size="25px" class="icon" @click="down">
            <Download />
          </el-icon>
        </div>
      </div>
      <div class="tabs">
        <div @click="comment">
          <span>{{ appDetail.scoreNum }}</span>
          <span>评分</span>
        </div>
        <div @click="comment">
          <span>{{ appDetail.commentNum }}</span>
          <span>评论数</span>
        </div>
        <div @click="down">
          <span>{{ appDetail.downNum }}</span>
          <span>下载量</span>
        </div>
      </div>
      <div class="introduce">
        {{ appDetail.introduce }}
      </div>
      <comments v-if="dialogVisible" ref="commentRef" @callBack="callBack"></comments>
      <template #footer>
        <div class="dialog-footer">
          <!-- <el-button @click="dialogVisible = false">Cancel</el-button> -->
          <el-button type="primary" @click="dialogVisible = false"> 关闭 </el-button>
        </div>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, ref, toRefs, onMounted, computed, nextTick } from "vue";
import app from "@/constants/app";
import baseService from "@/service/baseService";
import { ElMessage, FormInstance } from "element-plus";
import comments from "./app/comments.vue";

let commentVisible = ref(false);
const commentRef = ref(null);
const fileUrl = ref(app.fileApi);
let total = ref(0);
let dataForm = ref({
  limit: 66,
  status: 1,
  type: 1,
  page: 1
});
let appDetail = ref({});
let dialogVisible = ref(false);
let iscomment = ref(false);
let dataList = ref([]);

const typeList = ref([
  {
    label: "社交类",
    value: 1
  },
  {
    label: "资讯类",
    value: 2
  },
  {
    label: "游戏类",
    value: 3
  },
  {
    label: "购物类",
    value: 4
  },
  {
    label: "工具类",
    value: 5
  },
  {
    label: "其他",
    value: 0
  }
]);

const calculate = computed(() => {
  return (bytes: number) => {
    if (bytes) {
      if (bytes === 0) return "0 Bytes";

      const units = ["Bytes", "KB", "MB", "GB", "TB"];
      let unitIndex = 0;

      while (bytes >= 1024 && unitIndex < units.length - 1) {
        bytes /= 1024;
        unitIndex++;
      }

      return `${bytes.toFixed(2)} ${units[unitIndex]}`;
    } else {
      return 0;
    }
  };
});

onMounted(() => {
  query();
});

const callBack = (addNum: number) => {
  appDetail.value.commentNum += addNum;
};

const clickItem = (item: any) => {
  iscomment.value = false;
  appDetail.value = item;
  dialogVisible.value = true;
  // commentCount(appDetail.value.id);
};

const dialogClose = () => {
  appDetail.value = {};
  dataList.value = [];
  getDataList(1);
};

//下载
const down = () => {
  appDetail.value.downNum = appDetail.value.downNum + 1;
  baseService.put("/app/appinfo/", appDetail.value).then((res) => {
    if (res.code == 0) {
      const link = document.createElement("a");
      link.style.display = "none";
      link.href = fileUrl.value + appDetail.value.url;
      link.setAttribute("download", appDetail.value.name); // 设置下载文件的名称
      document.body.appendChild(link);
      link.click(); // 模拟点击事件
      document.body.removeChild(link); // 下载完成后移除元素
    }
  });
};

//评论、评分
const comment = () => {
  nextTick(() => {
    let commentRefValue = commentRef.value;
    if (commentRefValue) {
      commentRefValue.init(appDetail.value.id, true);
    }
  });
};

const query = () => {
  dataList.value = [];
  total.value = 0;
  getDataList(1);
};

//app列表触底
const handleScroll = () => {
  let page = dataForm.value.page + 1;
  if (total.value != 0 && dataList.value.length >= total.value) {
    if (total.value > dataForm.value.limit) {
      ElMessage.warning({
        message: "没有更多数据...",
        duration: 1000
      });
    }
  } else {
    getDataList(page);
  }
};

const getDataList = (page: number) => {
  dataForm.value.page = page;
  baseService.get("/app/appinfo/page", { allShow: true, ...dataForm.value }).then((res) => {
    total.value = res.data.total;
    dataList.value.push(...res.data.list);
  });
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

.app_list {
  display: grid;
  grid-template-columns: repeat(11, 1fr);
  /* 6列，每列宽度相等 */
  grid-gap: 10px;
  /* max-height: calc(100vh - 260px); */
  height: 600px;
  overflow-y: auto;
}

.app_item {
  height: 130px;
  padding: 15px 0;
  border: 1px solid #f3f3f3;
  border-radius: 15px;
  transition: 1.5s;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}

.item-name {
  width: 85px;
  /* 防止文本换行 */
  white-space: nowrap;
  /* 隐藏超出容器的内容 */
  overflow: hidden;
  /* 使用省略号表示被截断的文本 */
  text-overflow: ellipsis;
  display: flex;
  justify-content: center;
}

.app_item:hover {
  background-color: #efefef;
  cursor: pointer;
  transition: 1.5s;
}

.input_ss {
  position: absolute;
  top: 20px;
  right: 20px;
}

.detail {
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  background-color: #efefef;
  padding: 10px;
  border-radius: 10px;
}

.tabs {
  margin: 10px 0;
  display: flex;
  justify-content: space-around;
  background-color: #efefef;
  padding: 10px;
  border-radius: 10px;

  > div {
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
    border: 1px solid #f7f7f7;
    border-radius: 10px;
    padding: 10px;
    width: 80px;
    height: 80px;
    transition: 0.5s;

    > span:first-child {
      font-size: 20px;
    }
  }

  > div:hover {
    cursor: pointer;
    background-color: #c6c6c6;
    transition: 0.5s;
  }
}

.introduce {
  background-color: #efefef;
  padding: 10px;
  border-radius: 10px;
}

.icon:hover {
  cursor: pointer;
}
</style>
