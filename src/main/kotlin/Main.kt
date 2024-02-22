package ru.netology

data class Message(
    val id: Int, val senderId: Int,
    val content: String, var isRead: Boolean
)

data class Chat(
    val id: Int,
    val messages: MutableList<Message> = mutableListOf()
)

class SocialService {
    private val chats = mutableListOf<Chat>()

    fun createChat(): Chat = Chat(chats.size + 1).also { chats.add(it) }

    fun deleteChat(chatId: Int) {
        chats.removeIf { it.id == chatId }
    }

    fun getChat(): Sequence<Chat> = chats.asSequence()

    fun getUnreadChatsCount(): Int =
        chats.asSequence().count { chat -> chat.messages.any { !it.isRead } }

    fun getLastMessages(): Sequence<String> =
        chats.asSequence()
            .map { chat ->
                if (chat.messages.isEmpty()) "No message"
                else "Chat ${chat.id}: ${chat.messages.last().content}"
            }

    fun getMessagesFromChat(chatId: Int, messageCount: Int): List<Message> =
        chats
            .find { it.id == chatId }
            ?.messages
            ?.takeLast(messageCount)
            ?.onEach { it.isRead = true }
            ?.toList()
            ?: emptyList()

    fun createMessage(chatId: Int, senderId: Int, content: String) {
        chats.find { it.id == chatId }?.messages?.add(Message(chats.flatMap { it.messages }.size + 1, senderId, content, false))
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        chats.find { it.id == chatId }?.messages?.removeIf { it.id == messageId }
    }
}

fun main() {
    val socialService = SocialService()
    val chat1 = socialService.createChat()

    socialService.createMessage(chat1.id, 1, "Hello")
    socialService.createMessage(chat1.id, 2, "Hi")

    val chat2 = socialService.createChat()

    socialService.createMessage(chat2.id, 1, "How are you?")

    println("Количество непрочитанных чатов: ${socialService.getUnreadChatsCount()}")
    println("Последние сообщения: ")
    socialService.getLastMessages().forEach { println(it) }

    socialService.getMessagesFromChat(chat1.id, 2).forEach { println(it.content) }
    socialService.deleteMessage(chat1.id, 1)
}
