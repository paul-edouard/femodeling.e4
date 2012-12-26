package de.femodeling.e4.util.vpmtree;

import java.util.LinkedList;

public class CsvStructure {
	
	
	//Column 0
	public static int getLevel(String[] inputs){
		if(inputs.length<1)return -1;
		
		try{
		String s_l=inputs[0];
		String s_l_n=s_l.split("=")[1];
		return Integer.parseInt(s_l_n);
		
		}
		catch(Exception e){
			return -1;
		}
	}
	
	
	//Column 1
	private static String getPartId(String[] inputs){
		if(inputs.length<2)return "";
		return inputs[1];
	}
	
	//Column 2
	private static String getPartType(String[] inputs){
		if(inputs.length<3)return "";
		
		
		return inputs[2];
	}
	
	//Column 3
	private static String getPartName(String[] inputs){
		if(inputs.length<4)return "";
		return inputs[3];
	}
	
	//Column 4
	private static String getPartRevision(String[] inputs){
		if(inputs.length<5)return "";
		return inputs[4];
	}
	
	
	//Column 6-17
	private static VpmTmx getEdgeRelTmx(String[] inputs){
		if(inputs.length<18)return null;
		
		String[] values=new String[12];
		for(int i=0;i<12;i++){
			values[i]=inputs[i+6];
		}
		
		VpmTmx t=new VpmTmx(values, "relTmx");
		
		return t;
		
	}
	
	//Column 19
	private static String getPartCGRExported(String[] inputs){
		if(inputs.length<20)return "";
		if(inputs[19].equals("1")){
			return "true";
		}
		else
			return "false";
	}
	
	//Column 20
	private static String getPartCGRName(String[] inputs){
		if(inputs.length<21)return "";
		return inputs[20];
	}
	
	private static String getPartCGRFormat(String[] inputs){
		if(inputs.length<21)return "";
		if(inputs[20].endsWith("")){
			return "CGR19";
		}
		else
			return "";
	}
	
	//Column 21
	private static String getPartDescription(String[] inputs){
		if(inputs.length<22)return "";
		return inputs[21];
	}
	
	//Column 22
	private static String getEdgeId(String[] inputs){
		if(inputs.length<23)return "";
		return inputs[22];
	}
	
	
	//Column 25-36
		private static VpmTmx getEdgeAbsTmx(String[] inputs){
			if(inputs.length<37)return null;
			
			String[] values=new String[12];
			for(int i=0;i<12;i++){
				values[i]=inputs[i+25];
			}
			
			VpmTmx t=new VpmTmx(values, "absTmx");
			
			return t;
			
		}
	
	
	//Column 37
	private static String getPartCADName(String[] inputs){
		if(inputs.length<38)return "";
		return inputs[37];
	}
	
	private static String getPartCADExported(String[] inputs){
		if(inputs.length<38)return "";
		if(inputs[37].isEmpty()){
			return "false";
		}
		return "true";
	}
	
	private static String getPartCADFormat(String[] inputs){
		//TODO FORMAT
		
		if(inputs.length<38)return "";
		if(inputs[37].endsWith(".model")){
			return "CATIA";
		}
		else if(inputs[37].endsWith(".CATProduct")|| inputs[37].contains(".asm.")){
			return "asm";
		}
		else if(inputs[37].endsWith(".CATPart") || inputs[37].contains(".prt.")){
			return "prt";
		}
		return "";
	}
	
	
	//Column 38
	private static String getPartOwner(String[] inputs){
		if(inputs.length<39)return "";
		return inputs[38];
	}
	
	//Column 39
	private static String getPartStatusText(String[] inputs){
		if(inputs.length<40)return "";
		return inputs[39];
	}
	
	//Column 40
	private static LinkedList<VpmProject> getPartProjectList(String[] inputs){
		
		LinkedList<VpmProject> p_l=new LinkedList<VpmProject>();
		if(inputs.length<41)return p_l;
		
		String[] projectNames=inputs[40].split(",");
		for(int i=0;i<projectNames.length;i++){
			p_l.add(new VpmProject(projectNames[i]));
		}
		
		return p_l;
		
	}
	
	
	//Column 41
	private static String getPartCADSize(String[] inputs){
		if(inputs.length<42)return "";
		return inputs[41];
	}
	
	//Column 42
	private static String getPartCGRSize(String[] inputs){
		if(inputs.length<43)return "";
		return inputs[42];
	}
	
	//Column 43
	private static LinkedList<VpmMaterial> getPartMaterialList(String[] inputs){
		LinkedList<VpmMaterial> m_l=new LinkedList<VpmMaterial>();
		if(inputs.length<44)return m_l;
		
		if(inputs[43].isEmpty())return m_l;
		
		String[] m_s=inputs[43].split(";",-1);
		for(int i=0;i<m_s.length/3;i++){
			String mat_in="";
			for(int k=0;k<3;k++){
				if(k!=2)
					mat_in+=m_s[i*3+k]+";";
				else
					mat_in+=m_s[i*3+k];
			}
			
			VpmMaterial m=new VpmMaterial(mat_in);
			m_l.add(m);
		}
		return m_l;
	}
	
	
	//Column 44
	private static LinkedList<VpmWeight> getPartWeigthList(String[] inputs){
		
		LinkedList<VpmWeight> m_l=new LinkedList<VpmWeight>();
		if(inputs.length<45)return m_l;
		
		String[] projectNames=inputs[44].split(";",-1);
		
		if(projectNames.length!=21)return m_l;
		
		for(int i=0;i<4;i++){
			String mat_in="";
			for(int k=0;k<5;k++){
				if(k!=4)
					mat_in+=projectNames[i*5+k]+";";
				else
					mat_in+=projectNames[i*k];
				
			}
			
			VpmWeight w=new VpmWeight(mat_in);
			m_l.add(w);
			//System.out.println(w);
		}
		
		return m_l;
		
	}
	
	
	/*****************************************
	 *                                       *
	 *        PARTS, EDGES, NODES            *
	 *                                       *
	 *****************************************/
	public static VpmPart getPart(String[] inputs){
		VpmPart p=new VpmPart();
		
		p.setDescription(getPartDescription(inputs));
		p.setId(getPartId(inputs));
		p.setName(getPartName(inputs));
		p.setOwner(getPartOwner(inputs));
		p.setRevision(getPartRevision(inputs));
		p.setStatusText(getPartStatusText(inputs));
		p.setType(getPartType(inputs));
		
		//create the CAD file
		String cad_name=getPartCADName(inputs);
		if(!cad_name.isEmpty()){
			VpmLinkedFile f=new VpmLinkedFile(getPartCADExported(inputs),getPartCADFormat(inputs),getPartCADName(inputs),"",getPartCADSize(inputs));
			p.addFile(f);
		}
		
		//create the CGR File
		String cgr_name=getPartCGRName(inputs);
		if(!cgr_name.isEmpty()){
			VpmLinkedFile f=new VpmLinkedFile(getPartCGRExported(inputs),getPartCGRFormat(inputs),getPartCGRName(inputs),"",getPartCGRSize(inputs));
			p.addFile(f);
		}
		
		p.setMaterialList(getPartMaterialList(inputs));
		p.setProjectList(getPartProjectList(inputs));
		p.setWeightList(getPartWeigthList(inputs));
		
		
		return p;
	}
	
	public static VpmEdge getEdge(String[] inputs){
		
		VpmEdge e=new VpmEdge(getEdgeId(inputs),getEdgeAbsTmx(inputs), getEdgeRelTmx(inputs));
		return e;
		
	}
	
	public static VpmNode getNode(String[] inputs){
		VpmNode n=new VpmNode(getPartId(inputs));
		return n;
	}
	
	
}
