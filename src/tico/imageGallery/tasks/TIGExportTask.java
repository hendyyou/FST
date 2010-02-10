/*
 * File: TIGExportTask.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: Feb 20, 2008
 * 
 * Company: Universidad de Zaragoza, CPS, DIIS
 * 
 * License:
 * 		This program is free software; you can redistribute it and/or
 * 		modify it under the terms of the GNU General Public License
 * 		as published by the Free Software Foundation; either version 2
 * 		of the License, or (at your option) any later version.
 * 
 * 		This program is distributed in the hope that it will be useful,
 * 		but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 		MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 		GNU General Public License for more details.
 * 
 * 		You should have received a copy of the GNU General Public License
 * 		along with this program; if not, write to the Free Software Foundation,
 * 		Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package tico.imageGallery.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import tico.editor.TEditor;
import tico.imageGallery.dataBase.TIGDataBase;

public class TIGExportTask {
    private int lengthOfTask;
    private int current = 0;
    private String statMessage;
    private TEditor myEditor;
    private TIGDataBase myDataBase;
    private String myDirectoryPath;
    private boolean stop = false;
    
    private TIGExportTask myTask = this;
	private Vector myImages;

    public TIGExportTask () {
        //compute length of task ...
        //in a real program, this would figure out
        //the number of bytes to read or whatever
        lengthOfTask = 1000;
    }

    //called from ProgressBarDemo to start the task
    public void go(TEditor editor,TIGDataBase dataBase, String directoryPath, Vector images) {
        current = 0;
        this.myEditor = editor;
        this.myDataBase = dataBase;
        this.myDirectoryPath = directoryPath;
        this.myImages = images;
 
        final SwingWorker worker = new SwingWorker() {
            @Override
			public Object construct() {
                return new ActualTask(myEditor, myDataBase, myDirectoryPath, myImages);
            }
        };
    }

    //called from ProgressBarDemo to find out how much work needs to be done
    public int getLengthOfTask() {
        return lengthOfTask;
    }
    
    public void setLengthOfTask(int num) {
        lengthOfTask = num;
    }

    //called from ProgressBarDemo to find out how much has been done
    public int getCurrent() {
        return current;
    }

    public void stop() {
    	stop = true;
    }

    //called from ProgressBarDemo to find out if the task has completed
    public boolean done() {
        if (current >= lengthOfTask)
            return true;
        else
            return false;
    }

    public String getMessage() {
        return statMessage;
    }

    //the actual long running task, this runs in a SwingWorker thread
    public class ActualTask {
    	
        public ActualTask (TEditor editor,TIGDataBase dataBase, String directoryPath, Vector images) {
                  
    		//Copies the images from the source directory to the directory Images
    	
    		File myDirectory = new File(directoryPath);
    		
           	int i;
           	//dataBase.conectDB();
           	//images = new Vector();
           	//images = dataBase.allImageSearch();
           	
           	lengthOfTask = images.size();
           	
           //	String directory = directoryPath + "images" +  File.separator;
           //	File newDirectoryFolder = new File(directory);
			//newDirectoryFolder.mkdirs(); 
			
		/*	try{
				DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
				doc = domBuilder.newDocument();
			} catch (Exception exc) {
				System.out.println(exc.getMessage());
				System.out.println(exc.toString());    		
			}*/
			//System.out.println("Tamaño de images: " + images);
			// Create the data base node
			
			Element dataBaseXML = new Element("dataBase");
			//Element dbElement = doc.createElement("dataBase");
    		for (i = 0; ((i < images.size()) && !stop); i++){
    			
    			Vector imagen = new Vector(2);
    			imagen = (Vector) images.elementAt(i);
				String element = (String)imagen.elementAt(0);
    			current = i;
    			//String element = (String)(images.elementAt(i));
    			
    			String pathSrc = System.getProperty("user.dir")+File.separator+"images"+File.separator + 
    				element.substring(0,1).toUpperCase() + File.separator + element;
    				
    			String name = pathSrc.substring(pathSrc.lastIndexOf(File.separator) + 1,pathSrc.length());
    			
    			String pathDst = directoryPath + name;
				try {
					// Create channel on the source
					FileChannel srcChannel = new FileInputStream(pathSrc).getChannel();
					// Create channel on the destination
					FileChannel dstChannel = new FileOutputStream(pathDst).getChannel();
					// Copy file contents from source to destination
					dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
	    
					// Close the channels
					srcChannel.close();
					dstChannel.close();
				} catch (IOException exc) {
					System.out.println(exc.getMessage());
					System.out.println(exc.toString());    		
				}
        
				Vector<String> keyWords = new Vector<String>();
				keyWords = TIGDataBase.asociatedConceptSearch(element);
				
				// Create image node
				
				Element image = new Element("image");
				image.setAttribute("name", name);
				if (keyWords.size()!=0){//La imagen tiene categorías
					for (int k=0; k<keyWords.size(); k++){
						Element category = new Element("category");
						category.setText(keyWords.get(k).trim());
						image.addContent(category);
					}
				}
				dataBaseXML.addContent(image);
				//Element imageElement = doc.createElement("image");
				//Element imageNameElement = doc.createElement("name");
				//imageNameElement.appendChild(doc.createTextNode(name));
				//imageElement.appendChild(imageNameElement);
				/*for (int j = 0; j<keyWords.size();j++){
					Element keyWordElement = doc.createElement("keyWord");
					keyWordElement.appendChild(doc.createTextNode((String)keyWords.elementAt(j)));
					imageElement.appendChild(keyWordElement);
				}*/
					
					// Append language node
				//dbElement.appendChild(imageElement);
    		}
    		
    		Document doc=new Document(dataBaseXML);
					
    		try{
				XMLOutputter out = new XMLOutputter();
				FileOutputStream f = new FileOutputStream(directoryPath+"images.xml");
				out.output(doc,f);
				f.flush();
				f.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
    		current = lengthOfTask;
        }
    	
    }
}