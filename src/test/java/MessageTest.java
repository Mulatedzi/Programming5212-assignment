import com.mycompany.loginapp.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // ---------- Message Length Tests ----------
    @Test
    public void testMessageLengthValid() {
        String longMessage = "a".repeat(250);
        // Added "Developer" and "Sent" to match the Part 3 constructor
        Message msg = new Message(0, "+27718693002", longMessage, "Developer", "Sent");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthInvalid() {
        String tooLongMessage = "a".repeat(255);
        Message msg = new Message(0, "+27718693002", tooLongMessage, "Developer", "Sent");
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", msg.checkMessageLength());
    }

    // ---------- Recipient Tests ----------
    @Test
    public void testRecipientValid() {
        Message msg = new Message(0, "+27718693002", "Hi", "Developer", "Sent");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientInvalid() {
        Message msg = new Message(0, "08575975889", "Hi", "Developer", "Sent");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    // ---------- Message Hash Tests ----------
    @Test
    public void testMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "Developer", "Sent");
        String hash = msg.getMessageHash();
        assertTrue(hash.endsWith(":0:HITONIGHT"));
    }

    // ---------- Message ID Tests ----------
    @Test
    public void testMessageIDGenerated() {
        Message msg = new Message(0, "+27718693002", "Hi", "Developer", "Sent");
        assertTrue(msg.getMessageID().length() == 10);
    }
}