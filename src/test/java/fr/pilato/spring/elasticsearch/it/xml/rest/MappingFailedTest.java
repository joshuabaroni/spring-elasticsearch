/*
 * Licensed to David Pilato (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Author licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package fr.pilato.spring.elasticsearch.it.xml.rest;

import fr.pilato.spring.elasticsearch.it.BaseTest;
import org.elasticsearch.ElasticsearchStatusException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * We try to merge non merging mapping.
 * An exception should be raised.
 * @author David Pilato aka dadoonet
 *
 */
public class MappingFailedTest extends BaseTest {

	@Override
	public String indexName() {
		return null;
	}

	@Test
	void test_rest_client() {
		assertThrows(BeanCreationException.class, () -> {
			try {
				if (securityInstalled) {
					new ClassPathXmlApplicationContext("models/rest-xpack/mapping-failed/mapping-failed-context.xml");
				} else {
					new ClassPathXmlApplicationContext("models/rest/mapping-failed/mapping-failed-context.xml");
				}
			} catch (BeanCreationException e) {
				Throwable cause = e.getCause();
				assertEquals(ElasticsearchStatusException.class, cause.getClass());
				ElasticsearchStatusException responseException = (ElasticsearchStatusException) cause;
				assertThat(responseException.status().getStatus(), is(400));
				throw e;
			}
		});
	}
}
