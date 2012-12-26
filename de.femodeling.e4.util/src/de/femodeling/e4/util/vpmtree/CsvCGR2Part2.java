package de.femodeling.e4.util.vpmtree;

/* CsvCGR2Part2.java */
/**
#######################################################################################################

		Programm zum Ändern der CGR-Dateinamen in einer CSV-Datei


                Das Programm liest die CSV ein und nennt die Dateinamen um, wie Catpart.



"" -> Modell.              .replace("|Modell.","Modell|").replace ("|Teilmodell-V5-fix."," Teilmodell-V5-fix|").replace ("|Teilmodell-V5.","Teilmodell-V5|").replace ("|ZSB-manuell.","ZSB-manuell|").replace ("|Teilmodell.","Teilmodell|");
"" -> Teilmodell-V5-fix.
"" -> Teilmodell.
"" -> Teilmodell-V5.
"" -> ZSB-manuell.

ZSB -> Modell-V5.
ZSB -> Modell-V5-fix.
ZSB -> ZSB-manuell.
ZSB -> ZSB.
ZSB -> Modell-ZSB.

Wenn in Spalte CATIA-Name enthält .cgr -> für CGR-Name übernehmen




#######################################################################################################
*
*/

/*

Input: CSV-Datei
Variablen

*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//import au.com.bytecode.opencsv.CSVReader;
//import au.com.bytecode.opencsv.CSVWriter;

public class CsvCGR2Part2 {

final static int CGR = 20;
final static int Part = 37;


  public static void main(String[] args) 
  {
    if (args.length != 3) {
        System.out.println("java CsvCGR2Part2 inputfile listfile outputfile");
        System.exit(1);
    }
    try {
       BufferedReader br = new BufferedReader(new FileReader(args[0]));           //inputfile
       BufferedReader brList = new BufferedReader(new FileReader(args[1]));       //listfile
       //f1 = new FileWriter("buffer.txt");
       BufferedWriter f2 = new BufferedWriter(new FileWriter(args[2]));           //outputfile

       String zeile;
       String zeileNeu;
       //TO Change
       String spalteNeu="";
       
       
       while ((zeile = br.readLine()) != null) {
           //System.out.println(zeile.substring(19,21));

           String[] spalten = zeile.split("\\|");
           int numberOfSpalten = spalten.length;
		
           //System.out.println(numberOfSpalten + ": ->" + spalten[CGR] + "<->" + spalten[Part] + "<-");
           
           if (spalten[Part].indexOf(".model") > 0) {
               spalteNeu = spalten[Part].replace(".model", ".cgr");
               System.out.println("---> enthaelt .model  ");

           } else if (spalten[Part].indexOf(".CATPart") > 0){
               
               //*********************************************************************************************
               //CODE: Suche suchString = spalten[Part] + "."; in Datei listfile und gibt Anfangspunkt aus
        	   /*
        	   int posfirst = name.IndexOf(spalten[Part] + ".");
        	   if (pos == -1) {
        		   System.out.println("Datei : " + spalten[Part] + "ist in Liste nicht enthalten");
			   } else {
				   int poslast = name.lastIndexOf(spalten[Part] + ".");
			   014       name = name.substring(0, pos + 1) + "Test" + 
			   015              name.substring(pos + 1);
			   }



               //** 2009-11-27-16.19.48.cgr
               //** 12345678901234567890123
               //**         10        20

 
               //Alt: spalteNeu = spalten[Part].replace(".CATPart", ".cgr");

               //CODE: cgrName = brList. entnehme von Anfangspunkt bis Anfangspunkt + 23
		       s1 = s1.substring(0,s1.length()-1);
				*/

               System.out.println("---> enthaelt .CATPart ");

              //*********************************************************************************************



           } else if (spalten[Part].indexOf(".cgr") > 0){
               spalteNeu = spalten[Part];
               System.out.println("---> enthaelt .cgr ");
           } else {
               spalteNeu = "";
           } 

           spalten[CGR] = spalteNeu;
           System.out.println("-> "+ spalten[CGR]);

           //zeileNeu = spalten[CGR] + "|"+ spalten[Part] + "|" + spalteNeu + "|"; 
           zeileNeu = "";           

           for (int j = 0; j < numberOfSpalten; ++j) {
              zeileNeu = zeileNeu + spalten[j]+"|";
           }
           //System.out.println(zeileNeu);
           
	   zeileNeu = zeileNeu.replace("|Modell.","Modell|").replace ("|Teilmodell-V5-fix.","Teilmodell-V5-fix|").replace ("|Teilmodell-V5.","Teilmodell-V5|").replace ("|ZSB-manuell.","ZSB-manuell|").replace ("|Teilmodell.","Teilmodell|");

           f2.write(zeileNeu);
	   f2.newLine();

        }
        br.close();
        brList.close();
        f2.close();
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }
}

