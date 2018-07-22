<template>
  <div class="hello">
      {{header}}
    </div>
</template>

<script>
export default {
  data () {
    return {
      header: ''
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello{
	background: url("./assets/h5/topbanner.jpg") no-repeat center;
}
</style>
