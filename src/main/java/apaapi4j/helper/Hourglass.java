package apaapi4j.helper;

public class Hourglass {
	final Clock clock;
	final long capacity;
	long inverted;

	Hourglass(long capacity, Clock clock) {
		this.capacity = capacity;
		this.clock = clock;
		this.inverted = -capacity;
	}
	
	public Hourglass(long capacity) {
		this(capacity, new Clock());
	}

	public boolean isEmpty() {
		return !isFull();
	}

	public boolean isFull() {
		return now() < inverted + capacity;
	}

	public void invert() {
		this.inverted = clock.currentTimeMillis();
	}

	long now() {
		return clock.currentTimeMillis();
	}
}
