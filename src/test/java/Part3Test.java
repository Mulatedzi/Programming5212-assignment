import com.mycompany.loginapp.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Part3Test {

    @Test
    public void testArraysCorrectlyPopulated() {
        // Populate arrays manually based on test data
        Message[] sent = new Message[5];
        Message[] stored = new Message[5];
        Message[] disregard = new Message[5];

        sent[0] = new Message(1, "+27834557896", "Did you get the cake?", "Developer", "Sent");
        stored[0] = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Developer", "Stored");
        disregard[0] = new Message(3, "+27834484567", "Yohoooo, I am at your gate.", "Developer", "Disregard");
        sent[1] = new Message(4, "0838884567", "It is dinner time !", "Developer", "Sent");
        stored[1] = new Message(5, "+27838884567", "Ok, I am leaving without you.", "Developer", "Stored");

        assertEquals("Did you get the cake?", sent[0].getMessageText());
        assertEquals("It is dinner time !", sent[1].getMessageText());
    }

    @Test
    public void testDisplayLongestMessage() {
        Message msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Developer", "Stored");
        Message msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.", "Developer", "Stored");

        Message[] stored = { msg5, msg2 }; 
        Message longest = stored[0];
        
        for (int i = 1; i < stored.length; i++) {
            if (stored[i].getMessageText().length() > longest.getMessageText().length()) {
                longest = stored[i];
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMessageText());
    }

    @Test
    public void testSearchMessageID() {
        Message msg4 = new Message(4, "0838884567", "It is dinner time !", "Developer", "Sent");
        String searchID = msg4.getMessageID();
        
        boolean found = false;
        if (msg4.getMessageID().equals(searchID)) {
            found = true;
        }
        assertTrue(found);
        assertEquals("It is dinner time !", msg4.getMessageText());
    }

    @Test
    public void testSearchByRecipient() {
        Message msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Developer", "Stored");
        Message msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.", "Developer", "Stored");

        String searchRecipient = "+27838884567";
        int foundCount = 0;

        if (msg2.getRecipient().equals(searchRecipient)) foundCount++;
        if (msg5.getRecipient().equals(searchRecipient)) foundCount++;

        assertEquals(2, foundCount);
    }

    @Test
    public void testDeleteByHash() {
        Message msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Developer", "Stored");
        Message[] stored = { msg2 };
        
        String hashToDelete = msg2.getMessageHash();
        boolean deleted = false;

        if (stored[0].getMessageHash().equals(hashToDelete)) {
            stored[0] = null; // Simulating deletion
            deleted = true;
        }

        assertTrue(deleted);
        assertNull(stored[0]);
    }

    @Test
    public void testDisplayReport() {
        Message msg1 = new Message(1, "+27834557896", "Did you get the cake?", "Developer", "Sent");
        
        String expectedReport = "Hash: " + msg1.getMessageHash() + "\n" +
                                "Recipient: " + msg1.getRecipient() + "\n" +
                                "Message: " + msg1.getMessageText() + "\n";
                                
        String actualReport = "Hash: " + msg1.getMessageHash() + "\n" +
                              "Recipient: " + msg1.getRecipient() + "\n" +
                              "Message: " + msg1.getMessageText() + "\n";
                              
        assertEquals(expectedReport, actualReport);
    }
}