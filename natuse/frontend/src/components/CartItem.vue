<template>
  <button @click="$emit('rem', i.id)">X</button>
  {{ i.name }} (x{{ i.quantity }})
</template>
<script>
export default { props: ['i'] }
</script>
