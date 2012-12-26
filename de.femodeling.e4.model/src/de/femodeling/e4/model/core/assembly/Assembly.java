package de.femodeling.e4.model.core.assembly;

import de.femodeling.e4.model.core.LockableEntity;



public abstract class Assembly extends LockableEntity {
	
	static final long serialVersionUID=1L;
	
	private AssNode root;
	
	public enum Type { PDMU, FE_MODULE, NONE};
	private Type type;
	
	static final String PDMUStr="pdmu";
	static final String FE_MODULEStr="fe_module";
	

	public AssNode getRoot() {
		return root;
	}

	public void setRoot(AssNode root) {
		this.root = root;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public static String typeToString(Type type){
		if(type==null)return "unknow type";
		
		switch(type){
			case PDMU:return PDMUStr;
			case FE_MODULE:return FE_MODULEStr;
			default: return "unknow type";
		}
	}
	
	
	public static Type stringToType(String str){
		if(str==null || str.isEmpty())return Type.NONE;
		
		if(str.equals(PDMUStr))
			return Type.PDMU;
		else if(str.equals(FE_MODULEStr))
			return Type.FE_MODULE;
		else
			return Type.NONE;
	}
	

}
