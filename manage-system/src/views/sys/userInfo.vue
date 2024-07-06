<template>
  <el-card>
    <div style="padding: 50px 400px">
      <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
        <el-form-item prop="username" label="用户名">
          <el-input readonly v-model="dataForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="realName" label="姓名">
          <el-input v-model="dataForm.realName" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item prop="gender" label="性别">
          <ren-radio-group v-model="dataForm.gender" dict-type="gender"></ren-radio-group>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="mobile" label="手机号">
          <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
        </el-form-item>
      </el-form>
      <div class="center">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
      </div>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted } from "vue";
import baseService from "@/service/baseService";
import { isEmail, isMobile } from "@/utils/utils";
import { IObject } from "@/types/interface";
import { ElMessage } from "element-plus";

const dataFormRef = ref();

const dataForm = reactive({
  id: "",
  username: "",
  realName: "",
  gender: 0,
  email: "",
  mobile: "",
  status: 1
});

onMounted(() => {
  init();
});

const validatePassword = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (!dataForm.id && !/\S/.test(value)) {
    return callback(new Error("必填项不能为空"));
  }
  callback();
};

const validateEmail = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (value && !isEmail(value)) {
    return callback(new Error("邮箱格式错误"));
  }
  callback();
};
const validateMobile = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (value && !isMobile(value)) {
    return callback(new Error("手机格式错误"));
  }
  callback();
};
const rules = ref({
  username: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  realName: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  email: [{ validator: validateEmail, trigger: "blur" }],
  mobile: [{ validator: validateMobile, trigger: "blur" }]
});

const init = (id?: number) => {
  dataForm.id = "";
  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }
  getInfo();
};

// 获取信息
const getInfo = () => {
  baseService.get(`/sys/user/info`).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/sys/user", {
      ...dataForm
    }).then((res) => {
      ElMessage.success({
        message: "成功",
        duration: 500
      });
    });
  });
};

defineExpose({
  init
});
</script>

<style lang="less" scoped>
.center {
  display: flex;
  align-items: center;
  justify-content: center;
}

.mod-sys__user {
  .role-list {
    .el-select {
      width: 100%;
    }
  }
}
</style>
