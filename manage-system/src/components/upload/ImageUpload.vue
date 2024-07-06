<template>
  <el-upload class="avatar-uploader" :action="uploadUrl" :show-file-list="false" :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload" accept="image/*">
    <img v-if="imageUrl || props.fileUrl" :src="`${fileUrl}${imageUrl || props.fileUrl}`" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon">
      <Plus />
    </el-icon>
  </el-upload>
</template>

<script lang="ts" setup>
  import { getToken } from "@/utils/cache";
  import app from "@/constants/app";
  import {
    ref, defineProps
  } from 'vue';
  import type { UploadProps } from 'element-plus'


  interface Props {
    fileUrl : string;
  }

  const props = defineProps<Props>();

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
    imageUrl.value = response.data.src
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
