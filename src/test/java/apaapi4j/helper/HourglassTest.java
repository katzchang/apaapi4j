package apaapi4j.helper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class HourglassTest {
	@Test
	public void 砂時計() throws Exception {
		ClockMock clock = new ClockMock(0);
		Hourglass hg = new Hourglass(100, clock);
		
		assertThat(hg.isEmpty(), is(true));
		assertThat(hg.isFull(), is(false));
		
		clock.proceed(50);
		
		hg.invert();
		assertThat(hg.isEmpty(), is(false));
		assertThat(hg.isFull(), is(true));
		
		clock.proceed(99);
		
		assertThat(hg.isEmpty(), is(false));
		assertThat(hg.isFull(), is(true));
		
		clock.proceed(1);
		
		assertThat(hg.isEmpty(), is(true));
		assertThat(hg.isFull(), is(false));
		
		hg.invert();
		assertThat(hg.isEmpty(), is(false));
		assertThat(hg.isFull(), is(true));
	}
}
