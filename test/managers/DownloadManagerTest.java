package managers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import models.File;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class DownloadManagerTest extends UnitTest {

	private PersistenceManager pem;
	private DownloadManager dom;
	private String generateUrl;
	private File file;
	private User user;

	@Before
	public void generate() {
		file = new File("myfile", "http://localhost/myfile.pdf", "application/pdf");
		pem = mock(PersistenceManager.class);
		when(pem.findFirst(File.class, "uid=?", file.getUid())).thenReturn(file);
		M.instance().inject(PersistenceManager.class, pem);

		dom = M.get(DownloadManager.class);
		dom.initialize();
		generateUrl = dom.generateUrl(file.getUid());
		user = new User();
	}

	@Test
	public void testUrlGenerated() {
		assertNotNull(generateUrl);
	}

	@Test(expected = RuntimeException.class)
	public void testNonOwnedFileFailsForUser() {
		dom.getFile(generateUrl, user);
	}

	@Test
	public void testOwnedFileSucceedsForUser() {
		user.addFile(file);
		File file2 = dom.getFile(generateUrl, user);
		assertEquals(file, file2);
	}

}
