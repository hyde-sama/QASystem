<template>
  <div v-if="show" class="chat-box">
    <div class="messages">
      <div v-for="message in messages" :key="message.id">{{ message.text }}</div>
    </div>
    <div class="input-box">
      <input v-model="inputText" @keydown.enter="sendMessage" placeholder="Type your message...">
      <button @click="toggleChatBox">Close</button>
    </div>
  </div>
  <button v-else class="open-chat" @click="toggleChatBox">Open Chat</button>
</template>

<script>
import {mapState, mapMutations} from 'vuex'

export default {
  computed: {
    ...mapState(['show', 'messages'])
  },
  methods: {
    ...mapMutations(['toggleShow', 'addMessage']),
    toggleChatBox() {
      this.toggleShow()
    },
    sendMessage() {
      if (!this.inputText) {
        return
      }
      const message = {id: Date.now(), text: this.inputText}
      this.addMessage(message)
      this.inputText = ''
    }
  },
  data() {
    return {
      inputText: ''
    }
  }
}
</script>

<style>
.chat-box {
  position: fixed;
  right: 20px;
  bottom: 20px;
  width: 300px;
  height: 400px;
  border: 1px solid #ccc;
  background-color: #fff;
}

.messages {
  height: 80%;
  overflow-y: scroll;
}

.input-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
}

.open-chat {
  position: fixed;
  right: 20px;
  bottom: 20px;
  padding: 10px;
  background-color: #2196F3;
  color: #fff;
  border-radius: 50%;
  border: none;
  outline: none;
  cursor: pointer;
}

.open-chat:hover {
  background-color: #0b7dda;
}
</style>
