import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MorraTest {




	Server serverconnection;

	@BeforeEach
	void instantiate() {
		serverconnection = new Server();
	}

	@Test
	void check_result_player1()
	{

		int result = serverconnection.game_Decider(10,10,3);
		assertEquals(1,result,"Check your conditions");
	}
	@Test
	void check_result_player2()
	{
		int result = serverconnection.game_Decider(10,5,10);
		assertEquals(2,result,"Check your conditions");
	}

	@Test
	void check_result_TIE()
	{
		int result = serverconnection.game_Decider(10,5,3);
		assertEquals(0,result,"Check your conditions");
	}
	@Test
	void check_values_range()
	{

		int result = serverconnection.check_guess(12,5);
		assertEquals(-1,result,"Check your conditions");
	}

	@Test
	void check_values_wrongrange()
	{
		int result = serverconnection.check_guess(9,5);
		assertEquals(1,result,"Check your conditions");
	}

	@Test
	void not_equal()
	{
		int result = serverconnection.check_guess(9,5);
		assertNotEquals(2,result,"Check your conditions");
	}

	@Test
	void check_result_TIE_1()
	{
		int result = serverconnection.game_Decider(10,10,10);
		assertNotEquals(0,result,"Check your conditions");
	}



}
