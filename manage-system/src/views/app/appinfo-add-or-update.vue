<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false"
    :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()"
      label-width="120px">
      <el-row :gutter="24">
        <el-col :span="8">
          <el-form-item label="App名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="app名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="类型" prop="type">
            <el-select v-model="dataForm.type" clearable>
              <el-option v-for="(item,index) in typeList" :label="item.label" :value="item.value"
                :key="index"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="开发者" prop="developer">
            <el-input v-model="dataForm.developer" placeholder="开发者"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="介绍" prop="introduce">
        <el-input type="textarea" :rows="3" resize="none" :maxlength="250" show-word-limit v-model="dataForm.introduce"
          placeholder="介绍"></el-input>
      </el-form-item>
      <el-row :gutter="24">
        <el-col :span="12">
          <el-form-item label="App图标" prop="icon">
            <ImageUpload @onSuccess="ImageSuccess" :fileUrl="dataForm.icon" v-if="visible"></ImageUpload>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="App上传" prop="url">
            <div>
              <fileUpload @onSuccess="fileSuccess" v-if="visible"></fileUpload>
            </div>

            <div v-if="dataForm.url">{{dataForm.url.split("\\")[dataForm.url.split("\\").length - 1]}}</div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
  import { reactive, ref } from "vue";
  import baseService from "@/service/baseService";
  import { ElMessage } from "element-plus";
  import ImageUpload from "@/components/upload/ImageUpload.vue"
  import fileUpload from "@/components/upload/fileUpload.vue"

  const emit = defineEmits(["refreshDataList"]);

  const visible = ref(false);
  const dataFormRef = ref();

  const dataForm = reactive({
    id: '', name: '', icon: '', type: '', introduce: '', size: '', developer: '', url: '', downNum: '', status: '', creator: '', createDate: '', updater: '', updateDate: ''
  });

  const typeList = ref([{
    label: '社交类',
    value: 1
  }, {
    label: '资讯类',
    value: 2
  }, {
    label: '游戏类',
    value: 3
  }, {
    label: '购物类',
    value: 4
  }, {
    label: '工具类',
    value: 5
  }, {
    label: '其他',
    value: 0
  }]);

  const rules = ref({
    name: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
    type: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
    introduce: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
    size: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
    developer: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
    url: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
    downNum: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
    status: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
  });

  interface ImageData {
      url: string;
      size: string;
  }

  const init = (id ?: number) => {
    visible.value = true;
    dataForm.id = "";

    // 重置表单数据
    if (dataFormRef.value) {
      dataFormRef.value.resetFields();
    }

    if (id) {
      getInfo(id);
    }
  };

  const ImageSuccess = (data : ImageData) => {
    dataForm.icon = data.url
  }

  const fileSuccess = (data : ImageData) => {
    dataForm.url = data.url
    dataForm.size = data.size
  }

  // 获取信息
  const getInfo = (id : number) => {
    baseService.get("/app/appinfo/" + id).then((res) => {
      Object.assign(dataForm, res.data);
    });
  };

  // 表单提交
  const dataFormSubmitHandle = () => {
    dataFormRef.value.validate((valid : boolean) => {
      if (!valid) {
        return false;
      }
      (!dataForm.id ? baseService.post : baseService.put)("/app/appinfo", dataForm).then((res) => {
        ElMessage.success({
          message: '成功',
          duration: 500,
          onClose: () => {
            visible.value = false;
            emit("refreshDataList");
          }
        });
      });
    });
  };

  defineExpose({
    init
  });
</script>
