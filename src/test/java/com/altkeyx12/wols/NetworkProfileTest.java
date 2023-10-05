package com.altkeyx12.wols;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.EnumSet;
import java.util.Set;

public class NetworkProfileTest {

	private static final String USER_PRINCIPAL_ROOT = "root";
	
	public static void main(String... args) {
		NetworkProfileTest t = new NetworkProfileTest();
		t.testWriteRoot();
		t.testCheckJava();
	}
	
	public void testCheckJava() {
		System.out.println("java.home = " + System.getProperty("java.home"));
		Path java = Paths.get(System.getProperty("java.home"), "bin", "java");
		try {
			PosixFileAttributeView posixView = Files.getFileAttributeView(
					java,PosixFileAttributeView.class);
			PosixFileAttributes attrs = posixView.readAttributes();
			
			System.out.println("r: " + attrs.permissions().contains(PosixFilePermission.OTHERS_READ));
			System.out.println("w: " + attrs.permissions().contains(PosixFilePermission.OTHERS_WRITE));
			System.out.println("x: " + attrs.permissions().contains(PosixFilePermission.OTHERS_EXECUTE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testWriteRoot() {
		Path path = Paths.get("test.txt");
		try {
			Files.write(path, "test".getBytes());			
			
			Set<PosixFilePermission> permissions = EnumSet.of(
					PosixFilePermission.OWNER_READ,
					PosixFilePermission.OWNER_WRITE);
			Files.setPosixFilePermissions(path, permissions);
			
			// lookup "root"
			UserPrincipal root = path
					.getFileSystem()
					.getUserPrincipalLookupService()
					.lookupPrincipalByName(USER_PRINCIPAL_ROOT);
			Files.setOwner(path, root);
			
			
			GroupPrincipal group = path
					.getFileSystem()
					.getUserPrincipalLookupService()
					.lookupPrincipalByGroupName(USER_PRINCIPAL_ROOT);
					
			Files
				.getFileAttributeView(path, PosixFileAttributeView.class)
				.setGroup(group);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
