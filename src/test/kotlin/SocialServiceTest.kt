import org.junit.Assert.assertEquals
import org.junit.Test
import ru.netology.SocialService

class SocialServiceTest {
    @Test
    fun testCreateChat() {
        val socialService = SocialService()
        socialService.createChat()
        assertEquals(1, socialService.getChat().count())
    }

    @Test
    fun testDeleteChat() {
        val socialService = SocialService()
        socialService.createChat()
        socialService.deleteChat(1)
        assertEquals(0, socialService.getChat().count())
    }

    @Test
    fun testCreateMessage() {
        val socialService = SocialService()
        socialService.createChat()
        socialService.createMessage(1, 1, "Hello!")
        assertEquals(1, socialService.getMessagesFromChat(1, 1).size)
    }

    @Test
    fun testDeleteMessage() {
        val socialService = SocialService()
        socialService.createChat()
        socialService.createMessage(1, 1, "Hello!")
        socialService.deleteMessage(1, 1)
        assertEquals(0, socialService.getMessagesFromChat(1, 1).size)
    }

    @Test
    fun testGetUnreadChatsCount() {
        val socialService = SocialService()
        socialService.createChat()
        socialService.createMessage(1, 1, "Hello!")
        assertEquals(1, socialService.getUnreadChatsCount())
    }

    @Test
    fun testGetLastMessages() {
        val socialService = SocialService()
        socialService.createChat()
        socialService.createMessage(1, 1, "Hello!")
        assertEquals("Chat 1: Hello!" , socialService.getLastMessages().iterator().next())
    }
}