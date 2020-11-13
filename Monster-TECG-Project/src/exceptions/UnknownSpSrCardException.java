package exceptions;

@SuppressWarnings("serial")
public class UnknownSpSrCardException extends UnexpectedFormatException {

	String unknownSpSr;
	
	public UnknownSpSrCardException() {
	}

	public UnknownSpSrCardException(String arg0) {
		super(arg0);
	}

	public UnknownSpSrCardException(String sFile, int sLine, String spsr) {
		super(sFile, sLine);
		this.unknownSpSr = spsr;
	}

	public UnknownSpSrCardException(String message, String sFile, int sLine, String spsr) {
		super(message, sFile, sLine);
		this.unknownSpSr = spsr;
	}

	public String getUnknownSpSr() {
		return unknownSpSr;
	}

	public void setUnknownSpSr(String unknownSpSr) {
		this.unknownSpSr = unknownSpSr;
	}

}
