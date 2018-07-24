package com.acme;

import com.loganalyser.logmodel.Log;
import com.loganalyser.logmodel.LogEntryItem;
import com.loganalyser.logprocessor.LogProcessor;
import com.loganalyser.logprocessor.LogProcessorBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LogAnalyserTest {
	Stream<LogEntryItem> logItemList;

	@Before
	public void setUp() {
		logItemList = Stream.of(
			new LogEntryItem("user1", "/"),
			new LogEntryItem("user1", "login"),
			new LogEntryItem("user1", "subscriber"),
			new LogEntryItem("user2", "/"),
			new LogEntryItem("user2", "login"),
			new LogEntryItem("user2", "subscriber"),
			new LogEntryItem("user3", "/"),
			new LogEntryItem("user3", "login"),
			new LogEntryItem("user3", "product"),
			new LogEntryItem("user1", "/"),
			new LogEntryItem("user4", "/"),
			new LogEntryItem("user4", "login"),
			new LogEntryItem("user4", "product"),
			new LogEntryItem("user5", "/"),
			new LogEntryItem("user5", "login"),
			new LogEntryItem("user5", "subscriber")
		);
	}
	@Test
	public void resultsReturnLimitAmount() {
		LogProcessor logProcessor = new LogProcessorBuilder(logItemList)
				.limit(1)
				.pageCount(3)
				.isDescOrder(false)
				.build();

		List<Map.Entry<Integer, Log>> ordered = logProcessor.getOrderedList();

		assertThat(ordered.size(), is(1));
	}

	@Test
	public void resultsReturnedInDescOrderWithVisitedCount() {
		LogProcessor logProcessor = new LogProcessorBuilder(logItemList)
				.limit(3)
				.pageCount(3)
				.isDescOrder(true)
				.build();

		List<Map.Entry<Integer, Log>> ordered = logProcessor.getOrderedList();
		
		assertThat(ordered.size(), is(3));
		assertThat(ordered.get(0).getValue().getOccurrences(), is(3));
		assertThat(ordered.get(1).getValue().getOccurrences(), is(2));
		assertThat(ordered.get(2).getValue().getOccurrences(), is(1));
	}

	@Test
	public void resultsReturnedInAscOrderWithVisitedCount() {
		LogProcessor logProcessor = new LogProcessorBuilder(logItemList)
				.limit(3)
				.pageCount(3)
				.isDescOrder(false)
				.build();

		List<Map.Entry<Integer, Log>> ordered = logProcessor.getOrderedList();

		assertThat(ordered.size(), is(3));
		assertThat(ordered.get(0).getValue().getOccurrences(), is(1));
		assertThat(ordered.get(1).getValue().getOccurrences(), is(2));
		assertThat(ordered.get(2).getValue().getOccurrences(), is(3));
	}

	@Test
	public void resultsPrintFormattedInAscOrder() {
		LogProcessor logProcessor = new LogProcessorBuilder(logItemList)
				.limit(3)
				.pageCount(3)
				.isDescOrder(false)
				.build();

		List<Map.Entry<Integer, Log>> ordered = logProcessor.getOrderedList();

		assertThat(ordered.get(0).getValue().getPageJourney().printList(), is("(login -> subscriber -> /)"));
	}

	@Test
	public void defaultsUsedIfBuilderOptionsNotSet() {
		LogProcessor logProcessor = new LogProcessorBuilder(logItemList).build();

		List<Map.Entry<Integer, Log>> ordered = logProcessor.getOrderedList();

		assertThat(ordered.size(), is(3));
		assertThat(ordered.get(0).getValue().getOccurrences(), is(3));
		assertThat(ordered.get(0).getValue().getPageJourney().printList(), is("(/ -> login -> subscriber)"));
	}
}
