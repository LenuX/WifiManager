package object;

public class InputHidden extends Input {

	public InputHidden(String name, String value) {
		super(name, value);
		this.type="hidden";
	}

	@Override
	public String toString() {
		String s = "Object inputHidden : Name [" + name + "] , DefValue ["
				+ value + "] \n";
		return s;

	}

}
