package controllers;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import models.User;
/**
 * This class handles password-related operations, such as hashing passwords,
 * validating password strength, and verifying user input against stored password hashes. It
 * uses PBKDF2 with HMAC SHA1 for hashing.
 */
public class PasswordManager {
	/**
	 * The salt array used in the password hashing process.
	 */
	private static final byte[] salt = {
			(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78,
			(byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF,
			(byte) 0xFE, (byte) 0xDC, (byte) 0xBA, (byte) 0x98,
			(byte) 0x76, (byte) 0x54, (byte) 0x32, (byte) 0x10
	};
	/**
	 * The default hashed password, generated using the hash method for the default password 'password'.
	 */
	public static final String defaultpassword=hash("password");

	/**
	 * Hashes a given password using PBKDF2 with HMAC SHA1.
	 *
	 * @param password The password to hash.
	 * @return The hashed password.
	 */
	public static String hash(String password) {
		try{KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = f.generateSecret(spec).getEncoded();
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(hash);}catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

			e.printStackTrace();}
		return password;
	}
	/**
	 * Compares a user-inputted password with a stored hashed password.
	 *
	 * @param userinput   The user-inputted password.
	 * @param storedhash  The stored hashed password.
	 * @return true if the input matches the stored hash, false otherwise.
	 */
	public static boolean matchPassword(String userinput, String storedhash) {
		return(hash(userinput).equals(storedhash));
	}
	/**
	 * Stores a new password for a user after checking its strength.
	 *
	 * @param newpass The new password to be stored.
	 * @param u       The user for whom the password is to be stored.
	 * @return true if the password is successfully stored, false otherwise.
	 */
	public static boolean storeNewPassword(String newpass, User u) {

		if(!checkPasswordStrength(newpass)) {
			return false;
		}
		u.setPasswordFromUnhashed(newpass);
		return true;
	}

	/**
	 * Checks the strength of a password based on specific criteria (length, character types).
	 *
	 * @param newpass The password to check.
	 * @return true if the password meets the strength criteria, false otherwise.
	 */
	public static boolean checkPasswordStrength(String newpass) {
		String regex = "^(?=.*[0-9])"
				+ "(?=.*[a-z])(?=.*[A-Z])"
				+ "(?=.*[@#$%^&+=!])"
				+ "(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(regex);
		if(hash(newpass).equals(defaultpassword)) {
			System.out.println("\tThe password you entered matches the default password. Please choose some other password.");
			return false;
		}
		if (hash(newpass).equals(UserHandler.getCurrentUserInstance().getPassword())) {
			System.out.println("\tThe password you entered matches your previous password. Please choose some other password.");
			return false;
		}
		if(newpass.length()<8) {
			System.out.println("\tYour password must be atleast 8 characters long!");
			return false;
		}
		if(newpass.length()>20) {
			System.out.println("\tYour password must be atmost 20 characters long!");
			return false;
		}
		Matcher m = p.matcher(newpass);
		if(!m.matches()) {
			System.out.println("\tYour password must contain atleast one digit, one UPPERCASE letter, one lowercase letter, one special character and no whitespaces!");
			return false;
		}
		return true;

	}
}