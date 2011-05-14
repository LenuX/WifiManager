package object;

public class InputCheckbox extends Input {

	public InputCheckbox(String name, String value) {
		super(name, value);
		this.type="checkbox";
	}

	@Override
	public String toString() {
		String s = "Object InputCheckbox : Name [" + name + "] , DefValue ["
				+ value + "] \n";
		return s;

	}

}
