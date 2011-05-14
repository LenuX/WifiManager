package object;

public class InputText extends Input {

	public InputText(String name, String value) {
		super(name, value);
		this.type="text";
	}

	@Override
	public String toString() {
		String s = "Object inputText : Name [" + name + "] , DefValue ["
				+ value + "] \n";
		return s;

	}

}
