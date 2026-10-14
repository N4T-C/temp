<template>
  <button @click="$emit('add', p)">+ Cart</button>
  <button @click="$emit('del', p.id)">Del</button>
  {{ p.name }} - ₹{{ p.price }}
</template>
<script>
export default { props: ['p'] }
</script>
