package de.femodeling.e4.server.service.dao;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.femodeling.e4.internal.model.PartServerImpl;
import de.femodeling.e4.internal.model.ProjectServerImpl;
import de.femodeling.e4.model.core.part.Material;
import de.femodeling.e4.model.core.part.Part;
import de.femodeling.e4.model.core.part.Representation;
import de.femodeling.e4.model.core.part.Translation;
import de.femodeling.e4.model.core.part.Weight;
import de.femodeling.e4.model.xml.XmlFile;


public class PartDAOImpl extends XmlFile implements PartDAOIF {
	
	
	static final String DescriptionStr="description";
	static final String PartIdStr="part_id";
	static final String NameStr="name";
	static final String OwnerStr="owner";
	static final String RevisionStr="revision";
	static final String StatusStr="status";
	static final String StatusTextStr="statusText";
	static final String TypeTextStr="type_text";
	static final String TypeStr="type";
	static final String AnsaModuleIdStr="ansa_module_id";
	static final String CadFileNameStr="cad_file_name";
	
	private PartServerImpl part;
	
	
	private static Logger logger = Logger.getLogger(PartDAOImpl.class);

	
	public boolean savePart(PartServerImpl part, ProjectServerImpl proj){
		this.part=part;
		
		//Set the Ansa Module Id
		/*
		if(this.part.getAnsaModuleId() ==null || this.part.getAnsaModuleId().isEmpty()){
			HashSet<String> shortedKeySet=createAnsaShortedKeySet(proj);
			this.part.createUniqueAnsaModuleId(shortedKeySet);
		}
		*/
		
		String part_path=proj.getPartsDir()+File.separator+PartServerImpl.typeToDirectory(part.getType());
		File path=new File(part_path);
		if(!path.exists())path.mkdir();
		
		return saveAsXml(proj.getPartsDir()+File.separator+part.getXmlFileName());
	}
	
	/*
	private HashSet<String> createAnsaShortedKeySet(ProjectServerImpl proj){
		//Save the part ansa key in a set
		HashSet<String> shortedKeySet=new HashSet<String>();
		//Get the Ansa Module Id from rest
		LinkedList<PartServerImpl> p_server_l=getPartsFromProject(proj);
		for(PartServerImpl p:p_server_l)shortedKeySet.add(p.getAnsaModuleId());
				
		return 		shortedKeySet;
	}
	*/
	
	public List<PartServerImpl> saveParts(List<PartServerImpl> part_l, ProjectServerImpl proj){
		List<PartServerImpl> r_l=new LinkedList<PartServerImpl>();
		
		//Save the part ansa key in a set
		//HashSet<String> shortedKeySet=createAnsaShortedKeySet(proj);
		
		for(PartServerImpl p:part_l){
			
		//	p.createUniqueAnsaModuleId(shortedKeySet);
			
			if(!savePart(p,proj)){
				r_l.add(p);
			}
			
		}
		
		return r_l;
	}
	
	
	public LinkedList<PartServerImpl> getPartsFromProject(ProjectServerImpl proj,Part.Type type){
		
		LinkedList<PartServerImpl> p_l=new LinkedList<PartServerImpl>();
		
		String parts_path=proj.getPartsDir()+File.separator+PartServerImpl.typeToDirectory(type);
		File path=new File(parts_path);
		//logger.info("Directory : "+parts_path);
		
		if(path.exists() && path.isDirectory()){
			
			//Set the file filter
			String[] fileList=path.list(xmlFileFilter);
			
			//read the xml file
			for(int i=0;i<fileList.length;i++){
				part=new PartServerImpl();
				if(readFromXml(parts_path+File.separator+fileList[i])){
					p_l.add(part);
				}
			}
			
			
		}
		
		return p_l;
	}
	
	
	private FilenameFilter xmlFileFilter=new FilenameFilter() {
		
		@Override
		public boolean accept(File dir, String name) {
			
			return name.endsWith(".xml");
		}
	};
	
	
	@Override
	public int getNumberOfParts(ProjectServerImpl proj, Part.Type type) {
		
		String parts_path=proj.getPartsDir()+File.separator+PartServerImpl.typeToDirectory(type);
		File path=new File(parts_path);
		//logger.info("Directory : "+parts_path);
		
		if(path.exists() && path.isDirectory()){
			
			//Set the file filter
			String[] fileList=path.list(xmlFileFilter);
			
			return fileList.length;
			
			
		}
		
		return 0;
	}

	public LinkedList<PartServerImpl> getPartsFromProject(ProjectServerImpl proj){
		
		LinkedList<PartServerImpl> p_l=new LinkedList<PartServerImpl>();
		
		logger.info("Getting Parts from Project: "+proj.getName()+", id: "+proj.getLockableId());
		
		p_l.addAll(getPartsFromProject(proj,Part.Type.GROUP));
		p_l.addAll(getPartsFromProject(proj,Part.Type.MODEL));
		p_l.addAll(getPartsFromProject(proj,Part.Type.PARTIAL_MODEL));
		
		return p_l;
		
	}
	
	/***********************************
	 *                                 *
	 *		       XML                 *
	 *                                 *
	 ***********************************/
	
	/**
	 * return the TAG Name used in the xml file
	 */
	public String getTagName(){return "part";}
	
	/**
	 * initializes the users map from a xml element
	 */
	public void init(Element Root){
		
		if(Root.getTagName().equals(this.getTagName())){
			
			part.setDescription(Root.getAttribute(DescriptionStr));
			part.setPartId(Root.getAttribute(PartIdStr));
			part.setName(Root.getAttribute(NameStr));
			part.setOwner(Root.getAttribute(OwnerStr));
			part.setRevision(Root.getAttribute(RevisionStr));
			part.setStatus(Integer.parseInt(Root.getAttribute(StatusStr)));
			part.setStatusText(Root.getAttribute(StatusTextStr));
			part.setType(Part.stringToType(Root.getAttribute(TypeStr)));
			part.setTypeText(Root.getAttribute(TypeTextStr));
			part.setAnsaModuleId(Root.getAttribute(AnsaModuleIdStr));
			part.setCadFileName(Root.getAttribute(CadFileNameStr));
			
			
			part.setLockableId(Root.getAttribute("id"));
			
			NodeList Children=Root.getChildNodes();

			for(int i=0;i<Children.getLength();i++){
				Node child = Children.item(i);
				if(child instanceof Element){
					Element childElement=(Element)child;
					
					//Weight
					if(childElement.getTagName().equals(Weight.getTagName())){
						Weight w=new Weight();
						w.setDate(childElement.getAttribute(Weight.DateStr));
						w.setEvaluation(childElement.getAttribute(Weight.EvaluationStr));
						w.setWeightTyp(childElement.getAttribute(Weight.WeightTypStr));
						w.setValue(childElement.getAttribute(Weight.ValueStr));
						w.setOriginSystem(childElement.getAttribute(Weight.OriginSystemStr));
						
						part.addWeigth(w);
						
					}
					//Material
					else if(childElement.getTagName().equals(new Material().getTagName())){
						Material mat=new Material();mat.init(childElement);
						part.addMaterial(mat);
					}
					//Representation
					else if(childElement.getTagName().equals(new Representation().getTagName())){
						Representation rep=new Representation();rep.init(childElement);
						rep.setPartId(part.getLockableId());
						part.addRepresentation(rep);
					}
					//Translation
					else if(childElement.getTagName().equals(new Translation().getTagName())){
						Translation trans=new Translation();trans.init(childElement);
						part.setTranslation(trans);
					}
					
				}
			}
			
			
		}
	}
	
	
	/**
	 * export the user map in a xml element
	 */
	public Element toDomElement(Document doc){
		Element e=doc.createElement(this.getTagName());
			
		e.setAttribute(DescriptionStr, part.getDescription());
		e.setAttribute(PartIdStr, part.getPartId());
		e.setAttribute(NameStr, part.getName());
		e.setAttribute(OwnerStr, part.getOwner());
		e.setAttribute(RevisionStr, part.getRevision());
		e.setAttribute(StatusStr, String.valueOf(part.getStatus()));
		e.setAttribute(StatusTextStr, part.getStatusText());
		e.setAttribute(TypeStr, Part.typeToString(part.getType()));
		e.setAttribute(TypeTextStr, part.getTypeText());
		e.setAttribute(AnsaModuleIdStr, part.getAnsaModuleId());
		e.setAttribute(CadFileNameStr, part.getCadFileName());
		
		//Weight
		for(Weight w:part.getWeightList()){
		
			Element w_e=doc.createElement(Weight.getTagName());
			w_e.setAttribute(Weight.DateStr, w.getDate());
			w_e.setAttribute(Weight.EvaluationStr, w.getEvaluation());
			w_e.setAttribute(Weight.WeightTypStr, w.getWeightTyp());
			w_e.setAttribute(Weight.ValueStr, w.getValue());
			w_e.setAttribute(Weight.OriginSystemStr, w.getOriginSystem());
			
			e.appendChild(w_e);
		}
		//Material
		for(Material mat:part.getMaterialList()){
			e.appendChild(mat.toDomElement(doc));
		}
		//Representation
		for(Representation rep:part.getRepresentationList()){
			e.appendChild(rep.toDomElement(doc));
		}
		//Translation
		if(part.getTranslation()!=null)
			e.appendChild(part.getTranslation().toDomElement(doc));
		
		e.setAttribute("id", part.getLockableId());
		
		return e;
	  }
	
	
	
	

}
