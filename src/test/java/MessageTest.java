import com.mycompany.loginapp.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // ---------- Message Length Tests ----------
    @Test
    public void testMessageLengthValid() {
        String longMessage = "a".repeat(250);
        Message msg = new Message(0, "+27718693002", longMessage);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthInvalid() {
        String tooLongMessage = "a".repeat(255);
        Message msg = new Message(0, "+27718693002", tooLongMessage);
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", msg.checkMessageLength());
    }

    // ---------- Recipient Tests ----------
    @Test
    public void testRecipientValid() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientInvalid() {
        Message msg = new Message(0, "08575975889", "Hi");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    // ---------- Message Hash Tests ----------
    @Test
    public void testMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.getMessageHash();
        // Checks if the hash ends with the expected format (since ID is random, we check the end)
        assertTrue(hash.endsWith(":0:HITONIGHT"));
    }

    // ---------- SentMessage Tests ----------
    @Test
    public void testSentMessageSend() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    @Test
    public void testSentMessageDiscard() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertEquals("Press 0 to delete the message.", msg.SentMessage(3));
    }

    @Test
    public void testSentMessageStore() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertEquals("Message successfully stored.", msg.SentMessage(2));
    }

    // ---------- Message ID Tests ----------
    @Test
    public void testMessageIDGenerated() {
        Message msg = new Message(0, "+27718693002", "Hi");
        assertTrue(msg.checkMessageID());
        assertEquals(10, msg.getMessageID().length());
    }
}