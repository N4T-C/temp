<template>
  <div>
    <h1>Product CRUD Application</h1>
    <nav>
      <strong>Products:</strong>
      <router-link to="/create">Create</router-link> |
      <router-link to="/read">Read</router-link> |
      <router-link to="/update">Update</router-link> |
      <router-link to="/delete">Delete</router-link>
    </nav>
    <hr>
    <nav>
      <strong>Students:</strong>
      <router-link to="/students/create">Create</router-link> |
      <router-link to="/students/read">Read</router-link> |
      <router-link to="/students/update">Update</router-link> |
      <router-link to="/students/delete">Delete</router-link>
    </nav>
    <hr>
    <router-view />
  </div>
</template>
