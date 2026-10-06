package ee.tak24.billable;

import org.springframework.boot.SpringApplication;

public class TestBillableApplication {

	public static void main(String[] args) {
		SpringApplication.from(BillableApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
