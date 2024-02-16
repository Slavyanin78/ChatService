import org.junit.Test

import org.junit.Assert.*
import ru.netology.Chat
import ru.netology.SocialService

class SocialServiceTest {

    @Test
    fun createChat() {
        val socialService = SocialService()
        val chat = socialService.createChat()
        assertEquals(1,chat.id)
    }

    @Test
    fun deleteChat() {
        val socialService = SocialService()
        val chat = socialService.createChat()
        socialService.deleteChat(chat.id)
        assertEquals(0, socialService.getChat().size)
    }


    @Test
    fun getUnreadChatsCount() {
        val socialService = SocialService()
        val chat1 = socialService.createChat()
        socialService.createMessage(chat1.id, 1, "Hello")
        val chat2 = socialService.createChat()
        socialService.createMessage(chat2.id, 1, "Hi")
        assertEquals(2, socialService.getUnreadChatsCount())
    }

    @Test
    fun getLastMessages() {
        val socialService = SocialService()
        val chat1 = socialService.createChat()
        socialService.createMessage(chat1.id, 1, "Hello")
        assertEquals("Chat 1: Hello", socialService.getLastMessages()[0])
    }

    @Test
    fun getMessagesFromChat() {
        val socialService = SocialService()
        val chat1 = socialService.createChat()
        socialService.createMessage(chat1.id, 1, "Hello")
        socialService.createMessage(chat1.id, 2, "Hi")
        val message = socialService.getMessagesFromChat(chat1.id, 2)
        assertEquals(2, message.size)
        assertEquals(true, message.all { it.isRead })
    }

    @Test
    fun createMessage() {
        val socialService = SocialService()
        val chat1 = socialService.createChat()
        socialService.createMessage(chat1.id, 1, "Hello")
        val message = socialService.getLastMessages()[0]
        assertEquals("Chat 1: Hello", message)
    }

    @Test
    fun deleteMessage() {
        val socialService = SocialService()
        val chat1 = socialService.createChat()
        socialService.createMessage(chat1.id, 1, "Hello")
        socialService.deleteMessage(chat1.id, 1)
        assertEquals(0, socialService.getMessagesFromChat(chat1.id, 10).size)
    }
}