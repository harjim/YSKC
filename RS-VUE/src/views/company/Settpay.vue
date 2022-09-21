<template>
  <upload-report
    :fileType="3"
    typeName="settpay"
    pageName="年度汇算清缴报告"
    :isSearch="$auth('company:settpay:search')"
    :isUpload="$auth('company:settpay:upload')"
    :isDownload="$auth('company:settpay:download')"
    :isView="$auth('company:settpay:view')"
    :isDelete="$auth('company:settpay:delete')">
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
