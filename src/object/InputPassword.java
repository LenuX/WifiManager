package object;

public class InputPassword extends Input {

	public InputPassword(String name, String value) {
		super(name, value);
		this.type="password";
	}

	@Override
	public String toString() {
		String s = "Object inputPassword : Name [" + name
				+ "] , DefValue [" + value + "] \n";
		return s;

	}

}
