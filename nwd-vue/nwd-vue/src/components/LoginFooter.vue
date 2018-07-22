<template>
  <section class="jk-form mt-015">
      <div class="jk-tel jk-inputWrap">
        <input type="number" class="inputCom jk-telTxt" v-model.number="uname">
      </div>
      <div class=" jk-inputWrap mt-02 rl">
        <input type="number" v-model.number="imgcode" class="inputCom">
      </div>
      <div class=" jk-inputWrap mt-02 rl jk-message">
        <input type="number" v-model.number="verifyCode" class="inputCom">
        <span>获取验证码</span>
      </div>
      <div class=" jk-inputWrap mt-02">
        <input type="button" value="登录" class="inputCom-btn" id="btnSubmit">
      </div>
      
      <div class="jk-agreen">
        <div class="jk-agreenCheckbox">
          <span>
          <input type="checkbox" checked="" class="input_check" id="check3" name="favourite">
          <label for="check3"></label>
          </span>
            <span class="font-color-light-gray">我已阅读并同意</span><span>《你我贷借款注册协议》</span>
      </div>

     <div class="tac fs-12 mt-03 lh-25 font-color-gray">
       <p>市场有风险，投资需谨慎</p>
       <p>上海你我贷互联网金融信息服务有限公司</p>
     </div>
    </div>
    </section>
</template>

<script>
export default {
  data () {
    return {
      msg: ''
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.mt-015{
  
  margin-top: 0.15rem !important;
}

.jk-inputWrap {
  width: 85%;
  margin: 0 auto;
}

.mt-02{
  
  margin-top:0.2rem !important ;
}
.rl{
  
  position: relative;
}

.inputCom{
  
  width: 100%;
  display: block;
  height: 0.5rem;
  width: 100%;
  background: #F5F5F5;
   border: 1px solid #EEEEEE;
   border-radius: 3px;
   padding-left: 15px;
   font-size:0.14rem;
   color: #333;
}
.inputCom-btn{
  
  
  display: block;
  height: 0.5rem;
  width: 100%;
  background: #5462EB;
   
   border-radius: 3px;
  
   font-size:0.16rem;
   color: #fff;
   text-align: center;
}
.inputCom-btn:active{
  
  background: #2D3FF1;;
}

.inputCom-act-btn{
  display: block;
  height: 0.5rem;
  width: 100%;
  background: linear-gradient(60deg, #ffb016, #fd4d46);
  background: -webkit-linear-gradient(60deg, #ffb016, #fd4d46);
  background: -moz-linear-gradient(60deg, #ffb016, #fd4d46);
  background: -o-linear-gradient(60deg, #ffb016, #fd4d46);
   border-radius: 3px;
   font-size:0.16rem;
   color: #fff;
   text-align: center;
}
.inputCom-act-btn:active{
  
  background: #2D3FF1;;
}

.jk-agreen {
  width: 100%;
  margin: 0.2rem auto 0.38rem;
  font-size: 0.12rem;
  color: #88868F;
  padding-left: 0.2rem;
}

.jk-confirmBorrowMonthSub .jk-agreenCheckbox  span{
  position: relative;
  
  top:13px;

}
.jk-agreenCheckbox  span{
  position: relative;
   

}

.jk-agreenCheckbox .input_check {
  position: absolute;
  visibility: hidden;
  vertical-align: middle;
}

</style>
