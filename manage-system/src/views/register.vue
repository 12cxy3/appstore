<template>
  <div class="rr-login">
    <div class="rr-login-wrap">
      <div class="rr-login-left hidden-sm-and-down">
        <p class="rr-login-left-title">app管理系统</p>
      </div>

      <div class="rr-login-right">
        <div class="rr-login-right-main">
          <h4 class="rr-login-right-main-title">注册</h4>
          <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
            <el-form-item prop="username" label="用户名">
              <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码" :class="{ 'is-required': !dataForm.id }">
              <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPassword" label="确认密码" :class="{ 'is-required': !dataForm.id }">
              <el-input v-model="dataForm.confirmPassword" type="password" placeholder="确认密码"></el-input>
            </el-form-item>
            <el-form-item prop="realName" label="真实姓名">
              <el-input v-model="dataForm.realName" placeholder="真实姓名"></el-input>
            </el-form-item>
            <el-form-item prop="mobile" label="手机号">
              <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
            </el-form-item>
            <el-form-item label-width="0">
              <!-- <div class="right"> -->
              <el-button type="primary" @click="dataFormSubmitHandle()" style="width: 100%">确定</el-button>
              <!-- </div> -->
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { CacheToken } from "@/constants/cacheKey";
import baseService from "@/service/baseService";
import { setCache } from "@/utils/cache";
import { ElMessage } from "element-plus";
import { getUuid } from "@/utils/utils";
import app from "@/constants/app";
import SvgIcon from "@/components/base/svg-icon/index";
import { useAppStore } from "@/store";
import { useRouter } from "vue-router";
import { isEmail, isMobile } from "@/utils/utils";

const store = useAppStore();
const router = useRouter();
const dataFormRef = ref();
const state = reactive({
  captchaUrl: "",
  loading: false,
  year: new Date().getFullYear()
});

const dataForm = reactive({
  id: "",
  username: "",
  deptId: "",
  deptName: "",
  password: "",
  confirmPassword: "",
  realName: "",
  gender: 0,
  email: "",
  mobile: "",
  status: 1
});

const login = reactive({ username: "", password: "", captcha: "", uuid: "" });

onMounted(() => {
  //清理数据
  store.logout();
  getCaptchaUrl();
});
const formRef = ref();

const validatePassword = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (!dataForm.id && !/\S/.test(value)) {
    return callback(new Error("必填项不能为空"));
  }
  callback();
};
const validateConfirmPassword = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (!dataForm.id && !/\S/.test(value)) {
    return callback(new Error("必填项不能为空"));
  }
  if (dataForm.password !== value) {
    return callback(new Error("确认密码与密码输入不一致"));
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
  deptName: [{ required: true, message: "必填项不能为空", trigger: "change" }],
  password: [{ validator: validatePassword, trigger: "blur" }],
  confirmPassword: [{ validator: validateConfirmPassword, trigger: "blur" }],
  realName: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  email: [{ validator: validateEmail, trigger: "blur" }],
  mobile: [{ validator: validateMobile, trigger: "blur" }]
});

const getCaptchaUrl = () => {
  login.uuid = getUuid();
  state.captchaUrl = `${app.api}/captcha?uuid=${login.uuid}`;
};

const onRefreshCode = () => {
  getCaptchaUrl();
};

const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    baseService
      .post("/sys/user/register", {
        ...dataForm
      })
      .then((res) => {
        ElMessage.success({
          message: "成功",
          duration: 500,
          onClose: () => {
            router.push("/");
          }
        });
      });
  });
};
</script>

<style lang="less" scoped>
@import url("@/assets/theme/base.less");
.rr-login {
  width: 100vw;
  height: 100vh;
  background: #019ec4;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  @media only screen and (max-width: 992px) {
    .rr-login-wrap {
      width: 96% !important;
    }
    .rr-login-right {
      width: 100% !important;
    }
  }

  .right {
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }

  &-wrap {
    margin: 0 auto;
    width: 1000px;
    box-shadow: -4px 5px 10px rgba(0, 0, 0, 0.4);
    animation-duration: 1s;
    animation-fill-mode: both;
    border-radius: 5px;
    overflow: hidden;
  }

  &-left {
    justify-content: center;
    flex-direction: column;
    background-color: @--color-primary;
    color: #fff;
    float: left;
    width: 50%;

    &-title {
      text-align: center;
      color: #fff;
      font-weight: 300;
      letter-spacing: 2px;
      font-size: 32px;
    }
  }

  &-right {
    border-left: none;
    color: #fff;
    background-color: #fff;
    width: 50%;
    float: left;

    &-main {
      margin: 0 auto;
      width: 65%;
      &-title {
        color: #333;
        margin-bottom: 40px;
        font-weight: 500;
        font-size: 24px;
        text-align: center;
        letter-spacing: 4px;
      }

      &-lang .iconfont {
        font-size: 20px;
        color: #606266;
        font-weight: 800;
        width: 20px;
        height: 20px;
      }

      .el-input__inner {
        border-width: 0;
        border-radius: 0;
        border-bottom: 1px solid #dcdfe6;
      }

      &-code {
        width: 100%;
        .el-space__item:first-child {
          flex: 1;
        }
      }
      &-btn {
        width: 100%;
        height: 45px;
        font-size: 18px !important;
        letter-spacing: 2px;
        font-weight: 300 !important;
        cursor: pointer;
        margin-top: 30px;
        font-family: neo, sans-serif;
        transition: 0.25s;
      }
    }
  }

  .login-footer {
    text-align: center;
    position: absolute;
    bottom: 0;
    padding: 20px;
    color: rgba(255, 255, 255, 0.6);
    p {
      margin: 10px 0;
    }
    a {
      padding: 0 5px;
      color: rgba(255, 255, 255, 0.6);
      &:focus,
      &:hover {
        color: #fff;
      }
    }
  }

  &-left,
  &-right {
    position: relative;
    min-height: 500px;
    align-items: center;
    display: flex;
  }

  @keyframes animate-down {
    0%,
    60%,
    75%,
    90%,
    to {
      animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
    }
    0% {
      opacity: 0;
      transform: translate3d(0, -3000px, 0);
    }
    60% {
      opacity: 1;
      transform: translate3d(0, 25px, 0);
    }
    75% {
      transform: translate3d(0, -10px, 0);
    }
    90% {
      transform: translate3d(0, 5px, 0);
    }
    to {
      transform: none;
    }
  }

  .animate-down {
    animation-name: animate-down;
  }
}
</style>
