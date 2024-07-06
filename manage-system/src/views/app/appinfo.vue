<template>
  <div class="mod-app__appinfo">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-input v-model="state.dataForm.appName" placeholder="输入搜索APP" @change="state.getDataList()"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select placeholder="请选择App类型" v-model="state.dataForm.type" clearable @change="state.getDataList()">
          <el-option label="全部" value=""></el-option>
          <el-option v-for="(item, index) in typeList" :key="index" :value="item.value" :label="item.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select placeholder="请选择审核状态" v-model="state.dataForm.status" clearable @change="state.getDataList()">
          <el-option label="全部" value=""></el-option>
          <el-option label="待审核" :value="0"></el-option>
          <el-option label="通过" :value="1"></el-option>
          <el-option label="未通过" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('app:appinfo:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <!--      <el-form-item>
        <el-button v-if="state.hasPermission('app:appinfo:delete')" type="danger"
          @click="state.deleteHandle()">删除</el-button>
      </el-form-item> -->
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" style="width: 100%">
      <el-table-column show-overflow-tooltip prop="name" label="app名称" header-align="center" align="center"></el-table-column>
      <el-table-column prop="icon" label="app图标" header-align="center" align="center" width="110">
        <template v-slot="scope">
          <img :src="`${fileUrl}${scope.row.icon}`" style="width: 80px" />
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="introduce" label="介绍" header-align="center" align="center"></el-table-column>
      <el-table-column prop="size" label="大小" header-align="center" align="center">
        <template v-slot="scope">
          <span>{{ calculate(scope.row.size) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="类型" header-align="center" align="center">
        <template v-slot="scope">
          <span>{{ toLabel(scope.row.type) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="developer" label="开发者" header-align="center" align="center"></el-table-column>
      <el-table-column prop="downNum" label="下载量" header-align="center" align="center"></el-table-column>
      <el-table-column prop="scoreNum" label="评分" header-align="center" align="center"></el-table-column>
      <el-table-column prop="commentNum" label="评论数" header-align="center" align="center"></el-table-column>
      <el-table-column prop="status" label="状态" header-align="center" align="center">
        <template v-slot="scope">
          <el-tag v-if="scope.row.status == 0" type="info">待审核</el-tag>
          <el-tag v-else-if="scope.row.status == 1" type="success">审核成功</el-tag>
          <el-tag v-else-if="scope.row.status == 2" type="warning">审核拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createName" label="上传者" header-align="center" align="center"></el-table-column>
      <el-table-column prop="createDate" label="上传日期" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-popconfirm width="220" confirm-button-text="通过" cancel-button-text="拒绝" :icon="InfoFilled" cancel-button-type="warning" icon-color="#626AEF" title="请选择该款App的审核结果" @confirm="auditHandle(scope.row.id, 1)" @cancel="auditHandle(scope.row.id, 2)">
            <template #reference>
              <el-button v-if="state.hasPermission('app:appinfo:audit')" type="primary" link>审核</el-button>
            </template>
          </el-popconfirm>
          <el-button v-if="state.hasPermission('app:appinfo:update')" type="primary" link @click="comment(scope.row.id)">评论</el-button>
          <el-button v-if="state.hasPermission('app:appinfo:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="state.hasPermission('app:appinfo:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"> </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList">确定</add-or-update>
    <comments v-if="commentVisible" ref="commentRef"></comments>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs, onMounted, computed, nextTick } from "vue";
import AddOrUpdate from "./appinfo-add-or-update.vue";
import app from "@/constants/app";
import { InfoFilled } from "@element-plus/icons-vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";
import comments from "./comments.vue";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/app/appinfo/page",
  getDataListIsPage: true,
  exportURL: "/app/appinfo/export",
  deleteURL: "/app/appinfo"
});
const fileUrl = ref(app.fileApi);
const commentRef = ref(null);
let commentVisible = ref(false);

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

const state = reactive({ ...useView(view), ...toRefs(view) });

const comment = (id: number) => {
  commentVisible.value = true;
  nextTick(() => {
    let commentRefValue = commentRef.value;
    if (commentRefValue) {
      commentRefValue.init(id, false);
    }
  });
};

const calculate = computed(() => {
  return (bytes: number) => {
    if (bytes === 0) return "0 Bytes";

    const units = ["Bytes", "KB", "MB", "GB", "TB"];
    let unitIndex = 0;

    while (bytes >= 1024 && unitIndex < units.length - 1) {
      bytes /= 1024;
      unitIndex++;
    }

    return `${bytes.toFixed(2)} ${units[unitIndex]}`;
  };
});

const toLabel = computed(() => {
  return (type: number) => {
    // 使用 find 方法来查找匹配的项
    const item = typeList.value.find((item) => item.value === type);
    // 如果找到匹配的项，返回它的 label，否则返回 undefined
    return item ? item.label : undefined;
  };
});

onMounted(() => {
  // typeList.value =
});

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};

const auditHandle = (id?: number, status?: number) => {
  baseService.put("/app/appinfo/audit", { id, status }).then((res) => {
    ElMessage.success({
      message: "成功",
      duration: 500,
      onClose: () => {
        state.getDataList();
      }
    });
  });
};
</script>
