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
    private val chats = mutableListOf<Chat>()//Список чатов, хранящихся в социальном сервисе

    //Создаём новый чат и добавляем его в список чатов
    fun createChat(): Chat {
        val newChat = Chat(chats.size + 1)
        chats.add(newChat)
        return newChat
    }

    //Удаляем чат по его идентификатору
    fun deleteChat(chatId: Int) {
        chats.removeIf { it.id == chatId }
    }

    //Возвращаем список всех чатов
    fun getChat(): List<Chat> = chats

    //Возвращаем количество непрочитанных чатов
    fun getUnreadChatsCount(): Int =
        chats.count { chat -> chat.messages.any { !it.isRead } }

    //Возвращаем последние сообщения из каждого чата
    fun getLastMessages(): List<String> {
        return chats.map { chat ->
            if (chat.messages.isEmpty()) {
                "No message"
            } else {
                "Chat ${chat.id}: ${chat.messages.last().content}"
            }
        }
    }
    //Возвращаем указанное количество последних сообщений и помечаем как прочитанные
    fun getMessagesFromChat(chatId: Int, messageCount: Int): List<Message> {
        val chat = chats.find { it.id == chatId }
        chat?.let { val messagesToReturn = it.messages.takeLast(messageCount)
        messagesToReturn.forEach { message -> message.isRead = true }
            return messagesToReturn
        }
        return emptyList()
    }
    // Создаём новое сообщение в указанном чате
    fun createMessage(chatId: Int, senderId: Int, content: String) {
        val chat = chats.find { it.id == chatId }
        chat?.messages?.add(Message(chat.messages.size + 1, senderId, content, false))
    }
    //Удаляем сообщение в указанном чате
    fun deleteMessage(chatId: Int, messageId: Int) {
        val chat = chats.find { it.id == chatId }
        chat?.messages?.removeIf { it.id == messageId }
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
    println(socialService.getChat())

    socialService.deleteChat(1)
    println(socialService.getChat())

}