package object;

import java.util.ArrayList;

public class InputMenu extends Input {

	private ArrayList<String> list;
	private int indexOfdefvalue;

	public InputMenu(String name, String value) {
		super(name, value);
		this.type="menu";
		list = null;
	}

	public InputMenu(String name, String value, ArrayList<String> list,
			int indexOfdefvalue) {
		super(name, value);
		this.indexOfdefvalue = indexOfdefvalue;
		this.setList(list);
	}

	@Override
	public String toString() {
		String s = "Object inputMenu : Name [" + name + "] , DefValue ["
				+ value + "] , options: {";

		for (String string : list) {
			s += "[" + string + "]";
		}
		s += "}\n";
		return s;

	}

	/* GET SET */

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setIndexOfdefvalue(int indexOfdefvalue) {
		this.indexOfdefvalue = indexOfdefvalue;
	}

	public int getIndexOfdefvalue() {
		return indexOfdefvalue;
	}

}
