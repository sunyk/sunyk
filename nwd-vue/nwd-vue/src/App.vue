<template>
  <div>
    <header></header>
    <component-A></component-A>
    <login-Header></login-Header>
    <login-Footer></login-Footer>
  </div>
</template>

<script>

import LoginHeader from './components/LoginHeader.vue'
import LoginFooter from './components/LoginFooter.vue'

export default {
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  components: { LoginHeader,LoginFooter }
}
</script>

<style>

</style>
