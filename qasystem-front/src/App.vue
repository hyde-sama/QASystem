<template>
  <div class="app">
    <button class="chat-button" :style="{ top: buttonPosition.top + 'px', left: buttonPosition.left + 'px' }"
            @click="toggleChat">
      <span v-if="!chatVisible">聊天</span>
      <span v-else>关闭</span>
    </button>
    <div class="chat-window" v-show="chatVisible"
         :style="{ top: windowPosition.top + 'px', left: windowPosition.left + 'px' }">
      <div class="chat-messages">
        <div class="chat-message" v-for="(message, index) in messages" :key="index">
          <div class="chat-message-content" v-if="message.type === 'user'">{{ message.content }}</div>
          <div class="chat-message-content" v-else>{{ message.content }}</div>
        </div>
      </div>
      <div class="chat-input">
        <input type="text" v-model="messageInput" @keydown.enter="sendMessage">
        <button @click="sendMessage">发送</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      chatVisible: false,
      messages: [],
      messageInput: '',
      buttonPosition: {
        top: 20,
        left: 20
      },
      windowPosition: {
        top: 100,
        left: 100
      }
    }
  },
  methods: {
    toggleChat() {
      this.chatVisible = !this.chatVisible
    },
    sendMessage() {
      const message = this.messageInput.trim()
      if (!message) {
        return
      }
      this.messages.push({
        type: 'user',
        content: message
      })
      this.messageInput = ''
      this.scrollToEnd()
      this.askBot(message)
    },
    async askBot(question) {
      try {
        const params = new URLSearchParams()
        const headers = {'Content-Type': 'application/x-www-form-urlencoded'}
        params.append('question', question)
        const response = await axios.post('http://localhost:9999/qa/ask', params, {headers})
        const data = await response.data
        const message = data
        this.messages.push({
          type: 'bot',
          content: message
        })
        this.scrollToEnd()
      } catch (error) {
        console.error(error)
      }
    },
    scrollToEnd() {
      this.$nextTick(() => {
        const chatWindow = this.$el.querySelector('.chat-window')
        chatWindow.scrollTop = chatWindow.scrollHeight
      })
    },

    handleMouseDown(event) {
      let startX = event.pageX
      let startY = event.pageY
      const buttonPosition = this.buttonPosition
      const handleMouseMove = (event) => {
        const offsetX = event.pageX - startX
        const offsetY = event.pageY - startY
        this.buttonPosition = {
          top: buttonPosition.top + offsetY,
          left: buttonPosition.left + offsetX
        }
        this.windowPosition = {
          top: this.windowPosition.top + offsetY,
          left: this.windowPosition.left + offsetX
        }
        startX = event.pageX
        startY = event.pageY
      }
      const handleMouseUp = () => {
        document.removeEventListener('mousemove', handleMouseMove)
        document.removeEventListener('mouseup', handleMouseUp)
      }
      document.addEventListener('mousemove', handleMouseMove)
      document.addEventListener('mouseup', handleMouseUp)
    }
  },
  handleTouchStart(event) {
    const touch = event.touches[0]
    let startX = touch.pageX
    let startY = touch.pageY
    const buttonPosition = this.buttonPosition
    const handleTouchMove = (event) => {
      const touch = event.touches[0]
      const offsetX = touch.pageX - startX
      const offsetY = touch.pageY - startY
      this.buttonPosition = {
        top: buttonPosition.top + offsetY,
        left: buttonPosition.left + offsetX
      }
      this.windowPosition = {
        top: this.windowPosition.top + offsetY,
        left: this.windowPosition.left + offsetX
      }
      startX = touch.pageX
      startY = touch.pageY
    }
    const handleTouchEnd = () => {
      document.removeEventListener('touchmove', handleTouchMove)
      document.removeEventListener('touchend', handleTouchEnd)
    }
    document.addEventListener('touchmove', handleTouchMove)
    document.addEventListener('touchend', handleTouchEnd)
  },

  mounted() {
    document.addEventListener('mousedown', this.handleMouseDown)
    document.addEventListener('touchstart', this.handleTouchStart)
  },

  beforeDestroy() {
    document.removeEventListener('mousedown', this.handleMouseDown)
    document.removeEventListener('touchstart', this.handleTouchStart)
  }

}

</script>

<style scoped>
.app {
  position: relative;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.chat-button {
  position: absolute;
  width: 100px;
  height: 40px;
  border-radius: 20px;
  background-color: #007bff;
  color: #fff;
  font-size: 16px;
  text-align: center;
  line-height: 40px;
  cursor: pointer;
}

.chat-window {
  position: absolute;
  width: 400px;
  max-height: 400px;
  overflow-y: auto;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  z-index: 999;
}

.chat-messages {
  padding: 10px;
}

.chat-message {
  margin-bottom: 10px;
}

.chat-message-content {
  padding: 5px 10px;
  border-radius: 10px;
}

.chat-message-content:last-child {
  margin-bottom: 0;
}

.chat-input {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-top: 1px solid #f0f0f0;
}

.chat-input input {
  flex: 1;
  padding: 5px;
  margin-right: 10px;
  border: none;
  border-radius: 5px;
}

.chat-input button {
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: #fff;
  font-size: 14px;
  cursor: pointer;
}

.chat-input button:hover {
  background-color: #0062cc;
}

@media screen and (max-width: 576px) {
  .chat-window {
    width: 100%;
    height: 100%;
    border-radius: 0;
    max-height: none;
  }
}
</style>
