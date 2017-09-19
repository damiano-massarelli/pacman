package it.uniroma3.pacman.characters.behaviours;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AbstractMovePolicyTest {
	
	private static final int MOVES_LIMIT = 5;
	
	private AbstractMovePolicyTestImpl policy;

	@Before
	public void setUp() throws Exception {
		this.policy = new AbstractMovePolicyTestImpl(MOVES_LIMIT);
		this.policy.setNextPolicy(new FrightenedMovePolicy());
	}

	@Test
	public void testHasNextTimeNotExpired() {
		assertFalse(this.policy.hasNext());
	}
	
	@Test
	public void testNextTimeNotExpired() {
		assertSame(this.policy, this.policy.next());
	}
	
	@Test
	public void testHasNextTimeNotExpiredByOne() {
		boolean lastResult = false;;
		for (int i = 0; i < MOVES_LIMIT - 1; i++) 
			lastResult = this.policy.hasNext();
		assertFalse(lastResult);
	}
	
	@Test
	public void testHasNextTimeExpired() {
		boolean lastResult = false;
		for (int i = 0; i < MOVES_LIMIT; i++)
			lastResult = this.policy.hasNext();
		assertTrue(lastResult);
	}
	
	@Test
	public void testNextTimeExpired() {
		for (int i = 0; i < MOVES_LIMIT; i++)
			this.policy.hasNext();
		assertSame(FrightenedMovePolicy.class, this.policy.next().getClass());
	}

}
