/*
 * File: TIGImportTask.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.imageGallery.dataBase.TIGDataBase;

public class TIGImportTask {
	private int lengthOfTask;
    private int current = 0;
    private String statMessage;
    private TEditor myEditor;
    private TIGDataBase myDataBase;
    private String myDirectoryPath;
    private String myImagesBehaviour;
    private String errorImages = "";
    private boolean stop = false;
    //private File imageFile;
        
    public TIGImportTask () {
        //compute length of task ...
        //in a real program, this would figure out
        //the number of bytes to read or whatever
        lengthOfTask = 1000;
    }

    //called from ProgressBarDemo to start the task
    public void go(TEditor editor,TIGDataBase dataBase, String directoryPath, String imagesBehaviour) {
        current = 0;
        this.myEditor = editor;
        this.myDataBase = dataBase;
        this.myDirectoryPath = directoryPath;
        this.myImagesBehaviour = imagesBehaviour;

        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                return new ActualTask(myEditor, myDataBase, myDirectoryPath, myImagesBehaviour);
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
    
    public String getErrorImages(){
    	return errorImages;
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
    
    private boolean exists(String list[], String name){
		boolean exists = false;
		int i = 0;
		while ((i<list.length) && !exists){
    		exists = name.equals(list[i]);
    		i++;
    	}
		return exists;
	}

    //the actual long running task, this runs in a SwingWorker thread
    public class ActualTask {
    	
    	//private final static int PREVIEW_WIDTH = 125;
    	//private final static int PREVIEW_HEIGHT = 125;	
    	
        public ActualTask (TEditor editor, TIGDataBase dataBase, String directoryPath, String myImagesBehaviour) {
                  
    		//Copies the images from the source directory to the directory images
    		//and renames them so that there are not characters like ' ' or '´'
        	
    		File myDirectory = new File(directoryPath);
    		String[] list = myDirectory.list();
    		
    		File fileXML = new File(directoryPath+"images.xml");
    		SAXBuilder builder = new SAXBuilder(false);      
	        try {
				Document docXML = builder.build(fileXML);
				Element root = docXML.getRootElement();
				List images = root.getChildren("image");
				Iterator j = images.iterator();
				int i=0;
				
				TIGDataBase.activateTransactions();
				
				while (j.hasNext() && !stop){
					 current = i;
					 i++;
					 Element image = (Element)j.next();
			         String name = image.getAttributeValue("name");
			         List categories = image.getChildren("category");
			         Iterator k = categories.iterator();
			        
			         if (exists(list, name)){

			        	 String pathSrc = directoryPath.concat(name);
			        	 
			        	 String pathDst = System.getProperty("user.dir") + File.separator + "images" + File.separator +
			        	 	name.substring(0,1).toUpperCase() +	File.separator;
			        	 			        	 
			        	 String folder = System.getProperty("user.dir") + File.separator + "images" + File.separator +
			        	 	name.substring(0,1).toUpperCase();
			        	 
			        	 if (myImagesBehaviour.equals(TLanguage.getString("TIGLoadDBDialog.REPLACE_IMAGES"))){
		    			 //Remplace
			        		 //Vector aux = new Vector();
			        		 Vector<Vector<String>> aux = TIGDataBase.imageSearchByName(name.substring(0, name.lastIndexOf('.')));
		    			     if (aux.size()!=0){
		    			    	 int idImage = TIGDataBase.imageKeySearchName(name.substring(0, name.lastIndexOf('.')));
		    			    	 TIGDataBase.deleteAsociatedOfImage(idImage);
		    			     }
		    			     pathDst = pathDst.concat(name);
			        	 }
		    			 
			        	 //Rename if image exists on data base
			        	 if (myImagesBehaviour.equals(TLanguage.getString("TIGLoadDBDialog.ADD_IMAGES"))){
			        	    Vector aux = new Vector();
		    				aux = TIGDataBase.imageSearchByName(name.substring(0, name.lastIndexOf('.')));
		    				int fileCount = 0;
		    				if (aux.size()!=0){
			    				while (aux.size()!=0){
			    					fileCount++;
			    					aux = TIGDataBase.imageSearchByName(name.substring(0, name.lastIndexOf('.'))+"_"+fileCount);			    					
			    				}
			    				pathDst = pathDst + name.substring(0, name.lastIndexOf('.')) + '_' + fileCount + 
	    							name.substring(name.lastIndexOf('.'),name.length());
			    				
			    				name = name.substring(0, name.lastIndexOf('.')) + '_' + fileCount + 
    								name.substring(name.lastIndexOf('.'),name.length());
		    				}else{
		    					pathDst = pathDst.concat(name);
		    				}
			        	 }
		    			
		    			 String pathThumbnail = (pathDst.substring(0,pathDst.lastIndexOf("."))).concat("_th.jpg");
			        	 
		    			//If the directory not exists, create!
		    			File newDirectoryFolder = new File(folder);
		    			if (!newDirectoryFolder.exists()){
		    				newDirectoryFolder.mkdirs(); 
		    			}
		    			 //Copy image
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
						
						//Insert in database
						TIGDataBase.insertImageDB(name.substring(0,name.lastIndexOf('.')), name);
						int idImage = TIGDataBase.imageKeySearchName(name.substring(0, name.lastIndexOf('.')));
						
						//Insert Categories and associate to image
						while (k.hasNext()){
				        	 Element category = (Element)k.next();
				        	 int idCategory = TIGDataBase.insertConceptDB(category.getValue());
				        	 TIGDataBase.insertAsociatedDB(idCategory, idImage);
				         }
						//Create thumbnail
					/*	File srcImage = new File(pathSrc);
			        	 ImageIcon imageIcon = null;
			        	 if (srcImage != null) {			        		 
			        		 // Test if need to be loaded with JAI libraries (different
		   					 // format than JPG, PNG and GIF)
		   					 if (TFileUtils.isJAIRequired(srcImage)) {
		   						 // Load it with JAI class
		   						 RenderedOp src_aux = JAI.create("fileload", srcImage.getAbsolutePath());
		   						 BufferedImage bufferedImage = src_aux.getAsBufferedImage();
		   						 imageIcon = new ImageIcon(bufferedImage);				
		   					 } else {
		   						 // Create it as usual
		   						 imageIcon = new ImageIcon(srcImage.getAbsolutePath());
		   					 }
			        	 }*/
			        	
						//createThumbnail(imageIcon, pathThumbnail);
			        	 
			         }
			         else {
			        	 errorImages = errorImages+System.getProperty("line.separator")+name;
			         }
				}
				TIGDataBase.executeQueries();
    		
    		current = lengthOfTask;
    		
	        } catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    /**
	 * Creates a thumbnail of <code>image</code> and saves it in the directory specified by <code>path</code>
	 * 
	 * @author Patricia M. Jaray
	 * 
	 * @param image The <code>image</code> to create the thumbnail
	 * @param path  The <code>path</code> to save the thumbnail 
	 */
    
   /* private void createThumbnail(ImageIcon image, String path){
    	final int PREVIEW_WIDTH = 125;
    	final int PREVIEW_HEIGHT = 125;	
    	try{
			int thumbWidth = PREVIEW_WIDTH;
			int thumbHeight = PREVIEW_HEIGHT;
			double thumbRatio = (double)thumbWidth / (double)thumbHeight;
			int imageWidth = image.getIconWidth();
			int imageHeight = image.getIconHeight();
			double imageRatio = (double)imageWidth / (double)imageHeight;
			if (thumbRatio < imageRatio) {
				thumbHeight = (int)(thumbWidth / imageRatio);
			} else {
				thumbWidth = (int)(thumbHeight * imageRatio);
			}
			BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(image.getImage(), 0, 0, thumbWidth, thumbHeight, null);
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
			int quality = 100;
			quality = Math.max(0, Math.min(quality, 100));
			param.setQuality((float)quality / 100.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(thumbImage);
			out.close(); 
			} catch (Exception ex) {
				System.out.println("Error creating THUMBNAIL");
				System.out.println(ex.toString());   
			}	
    }*/
}