<template>
  <upload-report
    :fileType="2"
    typeName="audit"
    pageName="年度审计报告"
    :isSearch="$auth('company:audit:search')"
    :isUpload="$auth('company:audit:upload')"
    :isDownload="$auth('company:audit:download')"
    :isView="$auth('company:audit:view')"
    :isDelete="$auth('company:audit:delete')">
  </upload-report>
</template>
<script>
import UploadReport from './UploadReport'
export default {
  components: {
    UploadReport
  }
}
</script>
