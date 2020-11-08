package exceptions;

@SuppressWarnings("serial")
public class MultipleMonsterAdditionException extends RuntimeException {

	public MultipleMonsterAdditionException() {
	}

	public MultipleMonsterAdditionException(String message) {
		super(message);
	}

}
