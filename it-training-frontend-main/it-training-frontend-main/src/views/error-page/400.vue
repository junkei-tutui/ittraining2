<template>
  <div class="errPage-container">
    <h1 class="text-jumbo text-ginormous">
      {{ message }}
    </h1>
  </div>
</template>

<script>

export default {
  name: "Page400",
  computed: {
    message() {
      return "400  Bad Request ";
    },
  },
};
</script>

