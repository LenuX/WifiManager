package object;

public class InputSubmit extends Input {
	
	
	
	public InputSubmit(String name, String value) {
		super(name, value);
		this.type="submit";
	}

	@Override
	public String toString() {
		String s = "Object inputSubmit : Name [" + name + "] , DefValue ["+ value + "] \n";
		return s;

	}
}
