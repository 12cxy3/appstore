<template>
  <el-upload v-model:file-list="fileList" class="upload-demo" :action="uploadUrl" multiple :on-remove="handleRemove"
    :on-success="handleAvatarSuccess" :limit="1" accept=".apk" ref="upload">
    <el-button type="primary">点击上传</el-button>
  </el-upload>
</template>

<script lang="ts" setup>
  import { getToken } from "@/utils/cache";
  import app from "@/constants/app";
  import {
    ref, defineProps, watch
  } from 'vue';
  import type { UploadProps ,UploadInstance, UploadRawFile } from 'element-plus'

  const emit = defineEmits(["onSuccess"]);
  const fileUrl = ref(app.fileApi);
  const uploadUrl = ref(`${app.api}/sys/oss/upload?token=${getToken()}`)
  const imageUrl = ref('')

  const handleAvatarSuccess : UploadProps['onSuccess'] = (
    response,
    uploadFile
  ) => {
    let fileInfo = {
      size: uploadFile.size,
      url: response.data.src
    }
    emit("onSuccess", fileInfo)
  }

  const handleRemove : UploadProps['onRemove'] = (file, uploadFiles) => {
    upload.value!.clearFiles()
    let fileInfo = {
      size: 0,
      url: ''
    }
    emit("onSuccess", fileInfo)
  }
</script>

<style scoped>
  .avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }

  .avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
  }

  .el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
  }
</style>
