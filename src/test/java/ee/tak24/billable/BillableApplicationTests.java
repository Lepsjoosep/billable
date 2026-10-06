package ee.tak24.billable;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class BillableApplicationTests {

	@Test
	void contextLoads() {
	}

}
