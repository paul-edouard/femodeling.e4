package de.femodeling.e4.model.core.connection;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.femodeling.e4.model.xml.XmlElementIF;


public class ConnectionParameter implements XmlElementIF, Serializable{
	
	static final long serialVersionUID=1L;
	
	public static final String Element="Element";
	public static final String Aenderung="Änderung";
	public static final String Bemerkung="Bemerkung";
	public static final String Process="Process";
	public static final String Side="Side";
	public static final String X="X";
	public static final String Y="Y";
	public static final String Z="Z";
	public static final String Normale_X="Normale X";
	public static final String Normale_Y="Normale Y";
	public static final String Normale_Z="Normale Z";
	public static final String Start_X="Start-X";
	public static final String Start_Y="Start-Y";
	public static final String Start_Z="Start-Z";
	public static final String End_X="End-X";
	public static final String End_Y="End-Y";
	public static final String End_Z="End-Z";
	public static final String Linsendurchmesser="Linsendurchmesser";
	public static final String Linsendurchmesser2="Linsendurchmesser2";
	public static final String NietDurchmesser="NietDurchmesser";
	public static final String Niettyp="Niettyp";
	public static final String Matrizentyp="Matrizentyp";
	public static final String Ergebnis="Ergebnis";
	public static final String Matrizentypen_i_O_="Matrizentypen_i.O.";
	public static final String FDS_Durchmesser="FDS-Durchmesser";
	public static final String FDS_Laenge="FDS-Laenge";
	public static final String Nutzbare_Gewindelaenge="Nutzbare_Gewindelaenge";
	public static final String Stumpfnahtdicke__s_="Stumpfnahtdicke_(s)";
	public static final String Kehlnahtdicke__a_="Kehlnahtdicke_(a)";
	public static final String Nahtlaenge="Nahtlaenge";
	public static final String Klebstofftyp="Klebstofftyp";
	public static final String Klebstoff1="Klebstoff1";
	public static final String Klebstoff2="Klebstoff2";
	public static final String Klebeschicht1="Klebeschicht1";
	public static final String Klebeschicht2="Klebeschicht2";
	public static final String Klebenahtbreite="Klebenahtbreite";
	public static final String Werkstoff1="Werkstoff1";
	public static final String Werkstoff2="Werkstoff2";
	public static final String Werkstoff3="Werkstoff3";
	public static final String Zusatzwerkstoff_Fuellgrad="Zusatzwerkstoff Fuellgrad";
	public static final String Haertestufe="Haertestufe";
	public static final String Gewindedurchmesser="Gewindedurchmesser";
	public static final String Drehmoment="Drehmoment";
	public static final String Fuegerichtung="Fuegerichtung";
	public static final String TuckerbolzenDurchmesser="TuckerbolzenDurchmesser";
	public static final String TuckerbolzenLaenge="TuckerbolzenLaenge";
	public static final String Clinchdurchmesser__Aussen_="Clinchdurchmesser (Aussen)";
	public static final String Dicke1="Dicke1";
	public static final String Dicke2="Dicke2";
	public static final String Dicke3="Dicke3";
	public static final String Dicke4="Dicke4";
	public static final String Fuegepaket="Fuegepaket";
	public static final String Sachnummer="Sachnummer";
	public static final String Bolzentyp="Bolzentyp";
	public static final String Normteilnummer="Normteilnummer";
	public static final String Ausrichtung="Ausrichtung";
	public static final String Rohbauteil="Rohbauteil";
	public static final String Blechdicke__mm_="Blechdicke (mm)";
	public static final String Fachbereich="Fachbereich";
	public static final String Funktion="Funktion";
	public static final String Befestigungselement="Befestigungselement";
	public static final String Drehmoment__Nm_="Drehmoment (Nm)";
	public static final String KD_Teil_ZSB="KD-Teil/ZSB";
	public static final String DITAX_X="DITAX X";
	public static final String DITAX_Y="DITAX Y";
	public static final String DITAX_Z="DITAX Z";
	public static final String Dokupflichtig="Dokupflichtig";
	public static final String F8_1CA_LL="F8.1CA LL";
	public static final String F8_1CA_RL="F8.1CA RL";
	public static final String F8_1KA_LL="F8.1KA LL";
	public static final String F8_1KA_RL="F8.1KA RL";
	public static final String F8_2CA_LL="F8.2CA LL";
	public static final String F8_2CA_RL="F8.2CA RL";
	public static final String F8_2KA_LL="F8.2KA LL";
	public static final String F8_2KA_RL="F8.2KA RL";
	public static final String F9_1CA_LL="F9.1CA LL";
	public static final String F9_1CA_RL="F9.1CA RL";
	public static final String F9_1KA_LL="F9.1KA LL";
	public static final String F9_1KA_RL="F9.1KA RL";
	public static final String F9_2CA_LL="F9.2CA LL";
	public static final String F9_2CA_RL="F9.2CA RL";
	public static final String F9_2KA_LL="F9.2KA LL";
	public static final String F9_2KA_RL="F9.2KA RL";
	public static final String F9_2TA_LL="F9.2TA LL";
	public static final String F9_2TA_RL="F9.2TA RL";
	public static final String F9_3CA_LL="F9.3CA LL";
	public static final String F9_3CA_RL="F9.3CA RL";
	public static final String F9_3KA_LL="F9.3KA LL";
	public static final String F9_3KA_RL="F9.3KA RL";
	public static final String F9_4CA_LL="F9.4CA LL";
	public static final String F9_4CA_RL="F9.4CA RL";
	public static final String F9_4NA_LL="F9.4NA LL";
	public static final String F9_4NA_RL="F9.4NA RL";

	private static Logger logger = Logger.getLogger(ConnectionParameter.class);
	
	private String name;
	private String value;
	
	
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
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VtaParameter [name=" + name + ", value=" + value + "]";
	}

	/***********************************
	 *                                 *
	 *	  INITIALIZE AS ELEMENT        *
	 *                                 *
	 ***********************************/
	
	public String getTagName(){return "Parameter";}
	
	public void init(Element el){
		
		if(el.getTagName().equals(this.getTagName())){
			
			name=el.getAttribute("Name");
			value=el.getTextContent();
			
		}else{
			logger.warn("this is not a valid "+this.getTagName()+ "element");
		}
	}	
	
	
	
	/***********************************
	 *                                 *
	 *	    RETURN AS ELEMENT          *
	 *                                 *
	 ***********************************/	
	
	
	public Element toDomElement(Document doc){
		Document Doc=doc;
		Element e=Doc.createElement(this.getTagName());
		
		e.setAttribute("Name", this.name);
		e.setTextContent(this.value);
		
		return e;
	}
	
	
}
