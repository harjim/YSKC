<template>
  <div class="user-info">
    <div class="info">
      <img src="http://project.gdyskc.com/avatar.jpg" />
      <div class="user-desc">
        <span>昵称：xxxx</span>
        <span>登录名：xxxxxxx</span>
        <span class="name">个性签名：xxxxxxxxxx</span>
      </div>
    </div>
  </div>
  <van-row class="user-links">
    <van-col span="6">
      <van-icon name="pending-payment" />
      客户
    </van-col>
    <van-col span="6">
      <van-icon name="records" />
      合同
    </van-col>
    <van-col span="6">
      <van-icon name="tosend" />
      项目
    </van-col>
    <van-col span="6">
      <van-icon name="logistics" />
      查新
    </van-col>
    <van-col span="6">
      <van-icon name="logistics" />
      知识产权
    </van-col>
    <van-col span="6">
      <van-icon name="logistics" />
      服务
    </van-col>
  </van-row>
  <van-row class="user-links">
    <van-col span="6">
      <van-icon name="pending-payment" />
      客户
    </van-col>
    <van-col span="6">
      <van-icon name="records" />
      合同
    </van-col>
    <van-col span="6">
      <van-icon name="tosend" />
      项目
    </van-col>
    <van-col span="6">
      <van-icon name="logistics" />
      查新
    </van-col>
    <van-col span="6">
      <van-icon name="logistics" />
      知识产权
    </van-col>
    <van-col span="6">
      <van-icon name="logistics" />
      服务
    </van-col>
  </van-row>
</template>
<script setup>
/* 
import { useUserStore } from '@/store/user'
import * as dd from 'dingtalk-jsapi'

const userStore = useUserStore()

async function testClick() {
  alert(JSON.stringify(userStore.info))
  alert(dd.env.platform)
  const res = await dd.runtime.permission.requestAuthCode({
    corpId: window.__corpId__,
    onSuccess: (res) => {
      // 调用成功时回调
      console.log(res)
    },
    onFail: (err) => {
      console.log(err)
    }
  })
  console.log(res)
} */
</script>
<style lang="less">
.user-info {
  width: 94%;
  margin: 10px;
  height: 115px;
  // background: linear-gradient(90deg, @primary, #51c7c7);
  box-shadow: 0 2px 5px #269090;
  border-radius: 6px;
  .info {
    position: relative;
    display: flex;
    width: 100%;
    height: 100%;
    padding: 25px 20px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    img {
      width: 60px;
      height: 60px;
      border-radius: 50%;
      margin-top: 4px;
    }
    .user-desc {
      display: flex;
      flex-direction: column;
      margin-left: 10px;
      line-height: 20px;
      font-size: 14px;
      // color: #fff;
      span {
        // color: #fff;
        font-size: 14px;
        padding: 2px 0;
      }
    }
  }
}
.user {
  &-links {
    padding: 15px 0;
    font-size: 12px;
    text-align: center;
    background-color: #fff;
    margin-bottom: 15px;
    .van-icon {
      display: block;
      font-size: 24px;
    }
    .van-col {
      margin-bottom: 10px;
    }
  }
}
</style>
