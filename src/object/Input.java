package object;

public class Input {

	protected String name;
	protected String value;
	protected String type;
	

	public Input(String name, String value) {
		this.name = name;
		this.value = value;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getType() {
		return type;
	}
	
	

}
