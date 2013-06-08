package com.openpojo.issues.issue36;

import org.junit.Test;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;

public class Issue36 {
	private static final Logger LOG = LoggerFactory.getLogger(Issue36.class);

	@Test
	public void test() {
		LOG.error("SomeMessage");
	}

}
