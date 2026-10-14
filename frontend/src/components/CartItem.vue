<template>
  <button @click="$emit('rem', i.id)">X</button>
  {{ i.name }} - ₹{{ i.price }}
</template>
<script>
export default { props: ['i'] }
</script>

