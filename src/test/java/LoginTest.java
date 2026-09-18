import com.mycompany.loginapp.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for Login.
 * Uses exact test data from the assignment brief.
 */
public class LoginTest {

    private final Login login = new Login();

    // ---------- Username Tests ----------
    @Test
    public void testUserNameValid() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUserNameInvalid() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    // ---------- Password Tests ----------
    @Test
    public void testPasswordComplexityValid() {
        assertTrue(login.checkPasswordComplexity("Ch&sec@ke99!"));
    }

    @Test
    public void testPasswordComplexityInvalid() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // ---------- Cell Phone Tests ----------
    @Test
    public void testCellPhoneValid() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneInvalid() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ---------- Registration Messages ----------
    @Test
    public void testRegisterUserUsernameValid() {
        String result = login.registerUser("kyl_1", "Pass@123", "+27831234567", "John", "Doe");
        assertEquals("Cell phone number successfully added.", result);
    }

    @Test
    public void testRegisterUserUsernameInvalid() {
        String result = login.registerUser("kyle!!!!!!!", "Pass@123", "+27831234567", "John", "Doe");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    @Test
    public void testRegisterUserPasswordInvalid() {
        String result = login.registerUser("kyl_1", "password", "+27831234567", "John", "Doe");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }

    @Test
    public void testRegisterUserCellPhoneInvalid() {
        String result = login.registerUser("kyl_1", "Pass@123", "08966553", "John", "Doe");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
    }

    // ---------- Login Tests ----------
    @Test
    public void testLoginSuccessful() {
        login.registerUser("john_doe", "Pass@123", "+27831234567", "John", "Doe");
        assertTrue(login.loginUser("john_doe", "Pass@123"));
    }

    @Test
    public void testLoginFailed() {
        login.registerUser("john_doe", "Pass@123", "+27831234567", "John", "Doe");
        assertFalse(login.loginUser("john_doe", "WrongPass"));
    }

    // ---------- Login Status Messages ----------
    @Test
    public void testLoginStatusSuccess() {
        login.registerUser("jane_doe", "Secure@99", "+27839876543", "Jane", "Doe");
        boolean success = login.loginUser("jane_doe", "Secure@99");
        assertEquals("Welcome Jane, Doe it is great to see you again.", login.returnLoginStatus(success));
    }

    @Test
    public void testLoginStatusFailure() {
        Login freshLogin = new Login();
        assertEquals("Username or password incorrect, please try again.", freshLogin.returnLoginStatus(false));
    }
}