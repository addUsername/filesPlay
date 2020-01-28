/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaprogramacion3swing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author SERGI
 */
public class FuncionesMenus {
    
    public static String folderPath;
    public static File folder;
    public static File[] archivos = new File[10];
    //sera el indice del ultimo archivo o del archivo a manejar
    public static int i = 0;
    
    
    private static void updateArray(){
        // Esta funcion actualiza el array "archivos", cuando se elimina un archivo o
        // se renombra, en cuyo caso habra que insertar el file renombrado de manera manual
        
        File [] archivosOld = archivos;
        System.arraycopy(archivosOld, 0, archivos, 0, i);
        System.arraycopy(archivosOld, i+1, archivos, i, archivosOld.length - i - 1);
    }

    public static void listFiles(){
        String text = "<html>";
        for (File file:archivos){                     
                    try{                        
                        text += file.getName()+",  ";
                    }
                    catch (Exception e){ //Esta Excepcion ocurria si el array tiene un elemento vacio
                    }
                }        
        text += "</html>";        
        PracticaProgramacion3Swing.files.setText(text);
}
    private static String testString (String line){
        
        String[] lines = line.split("#");
        
        if (!( lines.length == 5)) return "Formato incorrecto, Introduce los 5 datos separados por '#'";
        
        //Regex
        String nombres= "[a-zA-Z]+" , dni = "[0-9]{8}[a-zA-Z]" , edad = "[0-9]+";
        
        for (int a = 0; a <3 ; a++)if (!lines[0].matches("[a-zA-Z]+")) return "Nombre o apellido incorrectos";
        
        if (!lines[3].matches(dni)) return "Dni incorrecto";
        
        if (!lines[4].matches(edad)) return "Edad incorrecta";
        
        return "true";
    }
    
    public static void createFolder(){        
        
        //Comprobamos si existe y creamos la carpeta
        File FicherosCreados = new File("FicherosCreados");
        
        if(FicherosCreados.mkdir()) PracticaProgramacion3Swing.output.setText("La carpeta FicherosCreados ha sido creada");
        PracticaProgramacion3Swing.output.setText("Los ficheros se guaradaran en ...\\FicherosCreados\\");
        
        folderPath = FicherosCreados.getAbsolutePath();
        folder = FicherosCreados;        
    }
    
    //menutxt
    
    public static void newFile(){
        
        //Esta comprobacion evita sobreescribir ficheros
        String new_file_path="";
        if (!new File(folderPath+"\\FicheroTexto.txt").exists()){
           new_file_path = folderPath+"\\FicheroTexto.txt";
        }else{
            new_file_path = folderPath+"\\FicheroTexto"+Integer.toString(1+folder.list().length)+".txt";
        }
        
        PracticaProgramacion3Swing.output.setText(new_file_path);
        File txt = new File(new_file_path);
        
        //Incluimos TEST.txt al array si se encuentra en la carpeta de trabajo
        if( new File (folderPath+"\\"+"TEST.txt").exists()) archivos[9] = (new File (folderPath+"\\"+"test.txt"));      
        
        try {
            if(txt.createNewFile()){
                String alert = ("\t\n\nArchivo "+txt.getName()+" creado");
                PracticaProgramacion3Swing.output.setText(alert);
            }
            
            //Buscamos el primer archivos[i]=null para asignale el fichero txt
            i = 0;            
            for (File file:archivos) if (file!=null) i++; else break;
            if (!(i>=archivos.length -1)) archivos[i]=txt; else PracticaProgramacion3Swing.output.setText("<html>Se ha alcanzado el limite maximo de archivos en el array"+
                    " reinicie el programa <br> o borre algun archivo</html>");
        } catch (IOException ex) {
            Logger.getLogger(FuncionesMenus.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    public static void delFile(){
        
        String fileName = JOptionPane.showInputDialog("Introduzca el fichero a borrar");
        int indexFile=0;
        try{
            for(File file:archivos){
                if (file.getName().equals(fileName)){
                    i = indexFile;
                    file.delete();
                    JOptionPane.showMessageDialog(null,file.getName() + " ha sido borrado con exito");
                    updateArray();
                    break;
                }
                indexFile++;
            }
        }catch(NullPointerException e){
            PracticaProgramacion3Swing.output.setText("Archivo no encontrado/no modificable");}                
    }
    public static void renameFile(){
        
        String fileName = JOptionPane.showInputDialog("Introduzca el fichero a renombrar");
        PracticaProgramacion3Swing.output.setText("Introduzca el nombre.extension del fichero a renombrar");        
        
        String newName = JOptionPane.showInputDialog("Introduzca el nombre nuevo");
        
                try{
                    int indexOf=0;
                    for (File file:archivos){

                        if (file.getName().equals(fileName)){
                            File renamedFile = new File (folderPath+"\\"+newName);
                            if (file.renameTo(renamedFile)) JOptionPane.showMessageDialog(null,file.getName() + " renombrado a"+newName+" con exito");
                            archivos[indexOf] = renamedFile;
                            break;
                        }
                        indexOf++;
                    }
                }catch (NullPointerException e){
                    PracticaProgramacion3Swing.output.setText("Archivo no encontrado/no modificable");
                }       
    }
    public static void writeLine(){
        
        String fileName = JOptionPane.showInputDialog("Introduzca el fichero a escribir");      
                    
        int j = 0;
        String text = "";

        while( j < 3 ){
            text = JOptionPane.showInputDialog("<html>Introduzca la linea a escribir en formato<br> 'Nombre#Apellido1#Apellido2#DNI#Edad'</html>");                                    

            //comprobamos que cumpla con el formato dni
            if (!(testString(text)).equals("true")){
                PracticaProgramacion3Swing.output.setText(testString(text));
            }else{break;}
            j++;
        }

        for (File file:archivos){

            if (file != null && file.getName().equals(fileName)){
                FileWriter fw = null;

                try {
                    fw = new FileWriter(file, true);    //true para append
                    fw.write(text+"\n");
                    fw.close();
                    JOptionPane.showMessageDialog(null,file.getName() + " modificado con exito");

                    break;

                } catch (IOException ex) {
                    PracticaProgramacion3Swing.output.setText("Error al escribir en el fichero");
                }

            }
        }                        
        
    }
    public static void getID(){
        
        String fileName = JOptionPane.showInputDialog("Introduzca el fichero a consultar");
        String dni = JOptionPane.showInputDialog("Introduzca DNI a buscar");
        int a=0;

        while ( a < 3){
            if(dni.matches("[0-9]{8}[a-zA-Z]?"))break; 
            else PracticaProgramacion3Swing.output.setText("DNI incorrecto, Introduzca DNI en formato 12345678x");
            dni = JOptionPane.showInputDialog("DNI incorrecto");
            a++;                    
        }                
        //input.close();

        int b = 0;

        for(File file:archivos){

                //este bucle salva NullPointerException, que generan los indices null en archivos[]..
                if (file != null && file.getName().equals(fileName)){
                    Scanner lines;
                    try {
                        //FileNotFoundException es innecesaria pues ya hemos comprobado 2 veces que el file existe
                        lines = new Scanner(file);

                        while (lines.hasNextLine()) {
                            String line = lines.nextLine();
                            /*
                            REGEX apunte
                            ^ = inicio, . = cualquier caracter, * = 0 a n veces, $ = Fin
                            */
                            if (line.matches("^.*"+dni+".*$")){
                                JOptionPane.showMessageDialog(null,line);
                                b++;
                            }
                        }
                    }catch (FileNotFoundException e){}
                }
            }               
        if (b > 1)PracticaProgramacion3Swing.output.setText("------------\nWARNING!! Se encontraron "+b+" coincidencias!,Se desaconseja el uso de esta columna como valor clave\n------------");
        if (b == 0)PracticaProgramacion3Swing.output.setText("No se encontraron coincidencias");
        // line.close();//No es necesario
                   
        }
    public static void delLine(){
        
        //CREAR fileNew, LEER fileOld, ESCRIBIR fileNew, BORRAR fileOld, RENOMBRAR fileNew        
        String fileName = JOptionPane.showInputDialog("Introduzca el fichero a consultar");
        
        

        String dni = JOptionPane.showInputDialog("Introduzca DNI a eliminar");
        int b=0;
        for (File file:archivos){

            if (file != null && file.getName().equals(fileName)){
                try {
                    File tempFile = new File (folderPath+"\\"+"tempFile.txt");
                    //CREAR fileNew (throw IOException)
                    tempFile.createNewFile();

                    //(throw IOException)
                    FileWriter fw = new FileWriter(tempFile, true);
                    Scanner lines = new Scanner(file);

                    boolean zeroMatches = true;
                    String line;

                    while (lines.hasNextLine()) {
                        //LEER fileOld
                        line = lines.nextLine();

                        //ESCRIBIR fileNew
                        if (line.matches("^.*"+dni+".*$")){
                            zeroMatches = false;
                            PracticaProgramacion3Swing.output.setText("Entrada borrada con exito");
                        } else {
                            fw.write(line+"\n");
                            fw.flush();
                        }
                    }
                    lines.close();
                    fw.close(); 

                    if (zeroMatches) { 
                        tempFile.delete();
                        PracticaProgramacion3Swing.output.setText("DNI no encontrado");
                        break;
                    }
                    //BORRAR fileOld           
                    file.delete();
                    i = b;
                    updateArray();

                    //RENOMBRAR fileNew
                    File renamedFile = new File (folderPath+"\\"+fileName);
                    if (tempFile.renameTo(renamedFile)) JOptionPane.showMessageDialog(null,"Archivo guardado con exito");
                    else PracticaProgramacion3Swing.output.setText("No se puede modificar el archivo, cierrelo primero");
                } catch (IOException ex) {
                    Logger.getLogger(FuncionesMenus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        b++;
        }
        
    }
    public static void getFiles(){
        
        //Esta funcion manejara la opcion 7, por mantener el estilo se ha decidido no implementar este bucle en el case 7: de menuTxt
        String fileName = JOptionPane.showInputDialog("\nIntroduzca el nombre del fichero a consultar");
        
                if(fileName.matches("^.*[.]txt$")){
                    oddThings(fileName);
                    evenThings(fileName);
                }  else{
                    oddThingsBin(fileName);
                    evenThingsBin(fileName);
                }
                
        
    }
    private static void oddThings(String fileName){
        
        for (File file:archivos){

            if (file != null && file.getName().equals(fileName)){
                try {
                    //Generamos un path valido                    
                    String path_lineasPares_txt;
                    
                    if (!new File (folderPath+"\\TextoLineasPares.txt").exists()){
                        path_lineasPares_txt = folderPath+"\\TextoLineasPares.txt";
                    }
                    else{
                        path_lineasPares_txt = folderPath+"\\TextoLineasPares-"+file.getName();
                    }
                    File oddFile = new File (path_lineasPares_txt);
                    
                    //File evenFile = new File (folderPath+"\\"+file.getName()+"-Impares.txt");
                    FileWriter fw = new FileWriter(oddFile);
                    Scanner lines = new Scanner (file);
                    
                    oddFile.createNewFile();
                    //columnas pares
                    String[] column = new String[5];
                    String allColumns = "";
                    //Lineas pares
                    String line;
                    int a = 0;
                    while (lines.hasNextLine()) {
                        line = lines.nextLine();
                        column = line.split("#");
                        allColumns += column[0]+"#"+column[2]+"#"+column[4]+"\n";
                        if (a%2==0){
                        fw.write(line+"\n");
                        //IMPORTANTE!
                        fw.flush();                        
                        }
                        a++;                        
                    }
                    fw.write(allColumns);
                    fw.close();
                    
                    //FEEDBACK al usuario
                    if (oddFile.length() > 0) JOptionPane.showMessageDialog(null,"Archivo "+oddFile.getName()+" generado con exito");
                }catch(Exception e){}
                
            }
        }          
    }
    private static void evenThings(String fileName){
        
        for (File file:archivos){

            if (file != null && file.getName().equals(fileName)){
                try {
                    //Generamos un path valido                    
                    String path_lineasImpares_txt;
                    
                    if (!new File (folderPath+"\\TextoLineasImpares.txt").exists()){
                        path_lineasImpares_txt = folderPath+"\\TextoLineasImpares.txt";
                    }
                    else{
                        path_lineasImpares_txt = folderPath+"\\TextoLineasImpares-"+file.getName();
                    }
                    File evenFile = new File (path_lineasImpares_txt);
                    
                    FileWriter fw = new FileWriter(evenFile);
                    Scanner lines = new Scanner (file);
                    
                    evenFile.createNewFile();
                    //columnas Impares
                    String[] column = new String[5];
                    String allColumns = "";
                    //Lineas Impares
                    String line;
                    int a = 0;
                    while (lines.hasNextLine()) {
                        line = lines.nextLine();
                        column = line.split("#");
                        allColumns += column[1]+"#"+column[3]+"\n";
                        if (a%2==1){
                        fw.write(line+"\n");
                        fw.flush();                        
                        }
                        a++;                        
                    }
                    fw.write(allColumns);
                    fw.close();
                    
                    //FEEDBACK al usuario
                    if (evenFile.length() > 0) JOptionPane.showMessageDialog(null,"Archivo "+evenFile.getName()+" generado con exito");
                }catch(Exception e){}
                
            }
        }
        
    }
    public static void getAll(){
        
        int index = 0;
        //comprobamos length para evitar IndexOutOfBoundsExceptions
        if (folder.list().length <= archivos.length){
            PracticaProgramacion3Swing.output.setText("<html>Array actualizado, ahora podra modificar todos<br> los ficheros de la carpeta de trabajo</html>");
            
        for(String file:folder.list()){
            archivos[index]= new File (folderPath+"//"+file);
            index++;
        }
        }else JOptionPane.showMessageDialog(null,"Demasiados archivos a almacenar");
    }
    
    //menuBinarios
    
    public static void newFileBin(){
        
        //Esta comprobacion evita sobreescribir ficheros
        String new_file_path;
        if (!new File(folderPath+"\\FicheroBinario.bin").exists()){
           new_file_path = folderPath+"\\FicheroBinario.bin";
        }else{
            new_file_path = folderPath+"\\FicheroBinario"+Integer.toString(1+folder.list().length)+".bin";
        }
        PracticaProgramacion3Swing.output.setText(new_file_path);       
        File bin = new File(new_file_path);
        if( new File (folderPath+"\\"+"TEST.bin").exists()) archivos[9] = (new File (folderPath+"\\"+"TEST.bin"));
        //Incluimos TEST.bin al array si se encuentra en la carpeta de trabajo     
        
        try {
            if(bin.createNewFile()){
                String alert = ("\t\n\nArchivo "+bin.getName()+" creado");
                PracticaProgramacion3Swing.output.setText(alert);
            }            
            //Buscamos el primer archivos[i]=null para asignale el fichero bin
            i = 0;            
            for (File file:archivos) if (file!=null) i++; else break;
            if (!(i>=archivos.length -1)) archivos[i]=bin; else PracticaProgramacion3Swing.output.setText("Se ha alcanzado el limite maximo de archivos en el array"+
                    " reinicie el programa o borre algun archivo");
        } catch (IOException ex) {
            Logger.getLogger(FuncionesMenus.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    public static void writeLineBin(){
        
         String fileName = JOptionPane.showInputDialog("Introduzca el fichero a escribir"); 
                    
        int j = 0;
        String text = "";

        while( j < 3 ){
            text = JOptionPane.showInputDialog("<html>Introduzca la linea a escribir en formato<br> 'Nombre#Apellido1#Apellido2#DNI#Edad'</html>");                                    

            //comprobamos que cumpla con el formato dni
            if (!(testString(text)).equals("true")){
                PracticaProgramacion3Swing.output.setText(testString(text));
            }else{break;}
            j++;
        }

        for (File file:archivos){

            if (file != null && file.getName().equals(fileName)){

                try {
                            //FileNotFoundException
                            RandomAccessFile fich = new RandomAccessFile(folderPath+"\\"+fileName, "rw");
                            //Calculamos la posicion de la nueva linea, al final (append)
                            long lenOld = fich.length();
                            fich.seek(lenOld);
                            //Escribimos la linea
                            fich.writeBytes(text + "\n");
                            
                            //br.close();
                            //file.close();
                            fich.close();
                    JOptionPane.showMessageDialog(null,file.getName() + " modificado con exito");

                    break;

                } catch (IOException ex) {
                    PracticaProgramacion3Swing.output.setText("Error al escribir en el fichero");
                }

            }
        }
    }
    public static void getIDBin() {
        
        String fileName = JOptionPane.showInputDialog("Introduzca el fichero a consultar");
        String dni = JOptionPane.showInputDialog("Introduzca DNI a buscar");
        int a=0;

        while ( a < 3){
            if(dni.matches("[0-9]{8}[a-zA-Z]?"))break; 
            else PracticaProgramacion3Swing.output.setText("DNI incorrecto, Introduzca DNI en formato 12345678x");
            dni = JOptionPane.showInputDialog("DNI incorrecto");
            a++;                    
        }                
        //input.close();

        int b = 0;
        String lines;
        for(File archivo:archivos){

                //este bucle salva NullPointerException, que generan los indices null en archivos[]..
                if (archivo != null && archivo.getName().equals(fileName)){
                    try {
                            RandomAccessFile fich = new RandomAccessFile(folderPath+"\\"+fileName, "rw");
                            //Creamos un FileReader para obtener un BufferedReader
                            FileReader file = new FileReader(fich.getFD());
                            //Creamos un BufferedReader para ir recorriendo el fichero linea a linea
                            BufferedReader br = new BufferedReader(file);
                            
                            while((lines = br.readLine()) != null){
                            
                                if (lines.matches("^.*"+dni+".*$")){
                                    JOptionPane.showMessageDialog(null,lines);
                                    b++;
                                }
                            }
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(FuncionesMenus.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                                Logger.getLogger(FuncionesMenus.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
            }
        }
        if (b > 1)PracticaProgramacion3Swing.output.setText("------------\nWARNING!! Se encontraron "+b+" coincidencias!,Se desaconseja el uso de esta columna como valor clave\n------------");
        if (b == 0)PracticaProgramacion3Swing.output.setText("No se encontraron coincidencias");
        // line.close();//No es necesario
                   
        }
    public static void delLineBin(){
        //CREAR fileNew, LEER fileOld, ESCRIBIR fileNew, BORRAR fileOld, RENOMBRAR fileNew        
        String fileName = JOptionPane.showInputDialog("Introduzca el fichero a consultar");
        
        

        String dni = JOptionPane.showInputDialog("Introduzca DNI a eliminar");
        int b=0;
        String lines;
        boolean zeroMatches=true;
        for (File archivo:archivos){
                    //este bucle salva NullPointerException, que generan los indices null en archivos[]..
                    if (archivo != null && archivo.getName().equals(fileName)){

                        try {
                            RandomAccessFile fich = new RandomAccessFile(folderPath+"\\"+fileName, "rw");
                            //Creamos un FileReader para obtener un BufferedReader
                            FileReader file = new FileReader(fich.getFD());
                            //Creamos un BufferedReader para ir recorriendo el fichero linea a linea
                            BufferedReader br = new BufferedReader(file);
                            
                            
                            File tempFile = new File (folderPath+"\\"+"tempFile.bin");
                            //CREAR fileNew (throw IOException)
                            tempFile.createNewFile();
                            
                            RandomAccessFile temp = new RandomAccessFile(folderPath+"\\temp.bin", "rw");
                            
                            int seek=0;
                            temp.seek(seek);
                            while((lines = br.readLine()) != null){
                            
                                if (lines.matches("^.*"+dni+".*$")){
                                    zeroMatches = false;
                                    PracticaProgramacion3Swing.output.setText("Entrada borrada con exito");
                                } else {
                                    temp.writeBytes(lines+"\n");
                                    seek+=lines.getBytes("UTF-8").length;
                                    temp.seek(seek);
                                }
                            }    
                            archivo.delete();
                            i = b;
                            updateArray();

                            File renamedFile = new File (folderPath+"\\"+fileName);
                            if (tempFile.renameTo(renamedFile)) JOptionPane.showMessageDialog(null,"Archivo guardado con exito");  
                            if(zeroMatches)PracticaProgramacion3Swing.output.setText("No se encontraron coincidencias");
                            
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(FuncionesMenus.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(FuncionesMenus.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    
                    } 
                b++;
                }
        
    }    
    private static void oddThingsBin(String fileName){
        
        for (File file:archivos){
            if (file != null && file.getName().equals(fileName)){
                try {
                    //Generamos un path valido                    
                    String path_lineasPares_bin;
                    
                    if (!new File (folderPath+"\\BinarioLineasPares.bin").exists()){
                        path_lineasPares_bin = folderPath+"\\BinarioLineasPares.bin";
                    }
                    else{
                        //Evitar sobreescribir
                        path_lineasPares_bin = folderPath+"\\BinarioLineasPares-"+file.getName();
                    }
                    File oddFile = new File (path_lineasPares_bin);
                    RandomAccessFile oddBin = new RandomAccessFile(oddFile, "rw");
                    
                    oddFile.createNewFile();
                    
                    RandomAccessFile fich = new RandomAccessFile(folderPath+"\\"+fileName, "rw");
                    //Creamos un FileReader para obtener un BufferedReader
                    FileReader archivo = new FileReader(fich.getFD());
                    //Creamos un BufferedReader para ir recorriendo el fichero linea a linea
                    BufferedReader br = new BufferedReader(archivo);
                                      
                    
                    //columnas pares
                    String[] column = new String[5];
                    String allColumns = "";
                    
                    int a = 0;
                    int seek=0;
                    oddBin.seek(seek);
                    String lines;
                    while((lines = br.readLine()) != null){
                        
                        column = lines.split("#");
                        allColumns += column[0]+"#"+column[2]+"#"+column[4]+"\n";
                        
                        if (a%2==0){
                        oddBin.writeBytes(lines+"\n");
                        seek += lines.getBytes("UTF-8").length + 1;
                        oddBin.seek(seek);
                        }
                        a++;
                    }   
                    oddBin.writeBytes(allColumns);
                    //FEEDBACK al usuario
                    if (oddFile.length() > 0) JOptionPane.showMessageDialog(null,"Archivo "+oddFile.getName()+" generado con exito");
                } catch (FileNotFoundException ex) {
                    PracticaProgramacion3Swing.output.setText("Archivo no encontrado");
                } catch (IOException ex) {
                    PracticaProgramacion3Swing.output.setText("No se puede leer/escribir el archivo");
                }
            }
        }          
    }      
    private static void evenThingsBin(String fileName){
        for (File file:archivos){
            if (file != null && file.getName().equals(fileName)){
                try {
                    //Generamos un path valido                    
                    String path_lineasImpares_bin;
                    
                    if (!new File (folderPath+"\\BinarioLineasImpares.bin").exists()){
                        path_lineasImpares_bin = folderPath+"\\BinarioLineasImpares.bin";
                    }
                    else{
                        //Evitar sobreescribir
                        path_lineasImpares_bin = folderPath+"\\BinarioLineasImpares-"+file.getName();
                    }
                    File evenFile = new File (path_lineasImpares_bin);
                    RandomAccessFile evenBin = new RandomAccessFile(evenFile, "rw");
                    
                    evenFile.createNewFile();
                    
                    RandomAccessFile fich = new RandomAccessFile(folderPath+"\\"+fileName, "rw");
                    //Creamos un FileReader para obtener un BufferedReader
                    FileReader archivo = new FileReader(fich.getFD());
                    //Creamos un BufferedReader para ir recorriendo el fichero linea a linea
                    BufferedReader br = new BufferedReader(archivo);
                                      
                    
                    //columnas pares
                    String[] column = new String[5];
                    String allColumns = "";
                    
                    int a = 0;
                    int seek=0;
                    evenBin.seek(seek);
                    String lines;
                    while((lines = br.readLine()) != null){
                        
                        column = lines.split("#");
                        allColumns += column[1]+"#"+column[3]+"\n";
                        
                        if (a%2==1){
                        evenBin.writeBytes(lines+"\n");
                        seek += lines.getBytes("UTF-8").length + 1;
                        evenBin.seek(seek);
                        }
                        a++;
                    }   
                    evenBin.writeBytes(allColumns);
                    evenBin.close();
                    //FEEDBACK al usuario
                    if (evenFile.length() > 0)JOptionPane.showMessageDialog(null,"Archivo "+evenFile.getName()+" generado con exito");
                } catch (FileNotFoundException ex) {
                    PracticaProgramacion3Swing.output.setText("Archivo no encontrado");
                } catch (IOException ex) {
                    PracticaProgramacion3Swing.output.setText("No se puede leer/escribir el archivo");
                }
            }
        }          
    }
}
    