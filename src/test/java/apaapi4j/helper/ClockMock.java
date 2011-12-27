package apaapi4j.helper;

public class ClockMock extends Clock {
	long currentTimeMillis;

	public ClockMock(long currentTimeMillis) {
		this.currentTimeMillis = currentTimeMillis;
	}

	void proceed(long diff) {
		currentTimeMillis = currentTimeMillis + diff;
	}

	@Override
	public long currentTimeMillis() {
		return currentTimeMillis;
	}
}