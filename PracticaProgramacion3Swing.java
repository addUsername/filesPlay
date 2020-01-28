package practicaprogramacion3swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

/**
 *
 * @author SERGI
 */
public class PracticaProgramacion3Swing {
    
        public static JButton crearTxt;
        public static MenuTxt mimenu;
        public static MenuBin mimenubin;
        public static JLabel print=new JLabel("<html><h2>Bienvenido!</h2><html>");
        public static JLabel output = new JLabel();
        public static JTextArea log = new JTextArea();
        public static JLabel files = new JLabel("");
        
        
	public static void main(String[] args) {
            
            FuncionesMenus.createFolder();
            JFrame frame = new JFrame("PracticaProgramacion3-Swing");
            //pillar ancho pantalla
            frame.setBounds(800,200, 380 ,650);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            placeComponents(frame);
            frame.setVisible(true);
            frame.setResizable(false);
	}

	private static void placeComponents(JFrame frame) {
		frame.setLayout(null);
                
                MenuBar menubar = new MenuBar();
                menubar.setBounds(0,0,400,25);
                frame.add(menubar);
                JLabel fileName = new JLabel("<html><h3>Selecione tipo de fichero</h3></html>");
		fileName.setBounds(10, 35, 200, 25);
		frame.add(fileName);
                
                //JLabel print = new JLabel("<html><h2>hdhf</h2><html>");
                print.setBounds(10,65,350,50);
                frame.add(print);
                
                output.setBounds(10,10,390,500);
                frame.add(output);
                
                log.setBounds(0,300,390,200);
                log.setBackground(Color.lightGray);
                log.append("----Log----\n");
                log.setEditable(false);
                frame.add(log);
                
                String[] s={".TXT",".BIN"};
                JComboBox miCombo = new JComboBox (s);
                miCombo.setBounds(210,35, 50, 25);
                frame.add(miCombo);
                                
                mimenu = new MenuTxt();
                mimenu.setBounds(10,135,340,400);
                frame.add(mimenu);
                mimenu.setVisible(true);
                
                mimenubin = new MenuBin();
                mimenubin.setBounds(10,135,340,400);
                frame.add(mimenubin);
               
                files.setBounds(0,480,390,200);
                frame.add(files);
                
                /*  Modo de ejemplo
		ActionListener myButtonListener = new MyButtonListener();
		registerButton.addActionListener(myButtonListener);
                */
                
                ItemListener myComboListener = new MyComboListener();
                miCombo.addItemListener(myComboListener);
                
	}
}
class MyButtonListener implements ActionListener {
    public static String fileName="";
    @Override
    public void actionPerformed(ActionEvent ae) {
        /* Decesed
        JButton source = (JButton) ae.getSource();
        JOptionPane.showMessageDialog(source, source.getText() + " button has been pressed");
        */
        String option = ae.getActionCommand();
        PracticaProgramacion3Swing.log.append(ae.getActionCommand()+"\n");
        switch(option){
            
            case "Crear txt":
            
                FuncionesMenus.newFile();
                FuncionesMenus.listFiles();                
                break;
                
            case "Crear bin":
                FuncionesMenus.newFileBin();
                FuncionesMenus.listFiles();                
                break;
            
            case "Eliminar txt":
            case "Eliminar bin":    
                FuncionesMenus.delFile();
                FuncionesMenus.listFiles();
                break;
            
            case "Renombrar txt":
            case "Renombrar bin":
                FuncionesMenus.renameFile();
                FuncionesMenus.listFiles();
                break;
            
            case "Escribir dni":
                FuncionesMenus.writeLine();
                FuncionesMenus.listFiles();
                break;
            case "Escribir DNI":
                FuncionesMenus.writeLineBin();
                FuncionesMenus.listFiles();
                
            case "Buscar dni":
                FuncionesMenus.getID();
                FuncionesMenus.listFiles();
                break;
                
            case "Buscar DNI":
                FuncionesMenus.getIDBin();
                FuncionesMenus.listFiles();
                
            case "Eliminar dni":
                FuncionesMenus.delLine();
                FuncionesMenus.listFiles();
                break;
            case "Eliminar DNI":
                FuncionesMenus.delLineBin();
                FuncionesMenus.listFiles();
                break;
            case "Extraer ficheros": 
                FuncionesMenus.getFiles();
                FuncionesMenus.listFiles();
                break;
            case "Almacenar":
                FuncionesMenus.getAll();
                FuncionesMenus.listFiles();
                break;
            
            default:
                break;
        }        
    }
}
class MyComboListener implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent ie) {
    JComboBox source = (JComboBox) ie.getSource();
        if(ie.getStateChange() == ItemEvent.SELECTED) {
            if(source.getSelectedItem().toString().equals(".TXT")){
               PracticaProgramacion3Swing.print.setText("<html><h4>Seleccione una de las opciones disponibles para trabajar con archivos .txt</h4></html>");
               PracticaProgramacion3Swing.mimenubin.setVisible(false);  
               PracticaProgramacion3Swing.mimenu.setVisible(true); 
            }else{
               PracticaProgramacion3Swing.print.setText("<html><h4>Seleccione una de las opciones disponibles para trabajar con archivos .bin</h4></html>");
               PracticaProgramacion3Swing.mimenubin.setVisible(true); 
               PracticaProgramacion3Swing.mimenu.setVisible(false);  
            }           
        }    
    }
}
class MyMenubarListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ae) {
        String option = ae.getActionCommand();
        
        switch(option){            
            case "Salir":
                System.exit(0);
                break; 
                
            case "Acerca de..":
                JOptionPane.showMessageDialog(null,"<html><H3> PracticaProgramacion3Swing<html><H3><br>Version 1.0<br>");
                break;
            
            default:
                break;
        }        
    }
}

class MenuTxt extends JPanel{

    public static JPanel menu;
    public MenuTxt(){
        setVisible(false);
        ActionListener myButtonListener = new MyButtonListener();
        //Botones
        JButton creartxt = new JButton("Crear txt");
        creartxt.setBounds(10, 10, 100, 25);
        add(creartxt);
        creartxt.addActionListener(myButtonListener);
        
        JButton eliminartxt = new JButton("Eliminar txt");
        eliminartxt.setBounds(120, 10, 100, 25);
        add(eliminartxt);
        eliminartxt.addActionListener(myButtonListener);
        
        JButton renombrartxt = new JButton("Renombrar txt");
        renombrartxt.setBounds(230, 10, 100, 25);
        add(renombrartxt);
        renombrartxt.addActionListener(myButtonListener);
        
        JButton escribirtxt = new JButton("Escribir dni");
        escribirtxt .setBounds(10, 35, 100, 25);
        add(escribirtxt );
        escribirtxt.addActionListener(myButtonListener);
        
        JButton buscartxt = new JButton("Buscar dni");
        buscartxt.setBounds(120, 35, 100, 25);
        add(buscartxt);
        buscartxt.addActionListener(myButtonListener);
        
        JButton eliminarcampotxt = new JButton("Eliminar dni");
        eliminarcampotxt.setBounds(230, 35, 100, 25);
        add(eliminarcampotxt);
        eliminarcampotxt.addActionListener(myButtonListener);
                
        JButton extraertxt = new JButton("Extraer ficheros");
        extraertxt.setBounds(10, 70 , 100, 25);
        add(extraertxt);
        extraertxt.addActionListener(myButtonListener);
                
        JButton almacenartxt = new JButton("Almacenar");
        almacenartxt.setBounds(120, 70, 100, 25);
        add(almacenartxt);
        almacenartxt.addActionListener(myButtonListener);   
    }
}
class MenuBin extends JPanel{

    public static JPanel menu;
    public MenuBin(){
        setVisible(false);
        ActionListener myButtonListener = new MyButtonListener();
        //Botones
        JButton crearbin = new JButton("Crear bin");
        crearbin.setBounds(10, 10, 100, 25);
        add(crearbin);
        crearbin.addActionListener(myButtonListener);
        
        JButton eliminarbin = new JButton("Eliminar bin");
        eliminarbin.setBounds(120, 10, 100, 25);
        add(eliminarbin);
        eliminarbin.addActionListener(myButtonListener);
        
        JButton renombrarbin = new JButton("Renombrar bin");
        renombrarbin.setBounds(230, 10, 100, 25);
        add(renombrarbin);
        renombrarbin.addActionListener(myButtonListener);
        
        JButton Escribirbin = new JButton("Escribir DNI");
        Escribirbin.setBounds(10, 35, 100, 25);
        add(Escribirbin );
        Escribirbin.addActionListener(myButtonListener);
        
        JButton buscarbin = new JButton("Buscar DNI");
        buscarbin.setBounds(120, 35, 100, 25);
        add(buscarbin);
        buscarbin.addActionListener(myButtonListener);
        
        JButton eliminarcampobin = new JButton("Eliminar DNI");
        eliminarcampobin.setBounds(230, 35, 100, 25);
        add(eliminarcampobin);
        eliminarcampobin.addActionListener(myButtonListener);
        
        JButton extraerbin = new JButton("Extraer ficheros");
        extraerbin.setBounds(10, 70 , 100, 25);
        add(extraerbin);
        extraerbin.addActionListener(myButtonListener);
                
        JButton almacenarbin = new JButton("Almacenar");
        almacenarbin.setBounds(120, 70, 100, 25);
        add(almacenarbin);
        almacenarbin.addActionListener(myButtonListener);
        
    }
}
class MenuBar extends JMenuBar{
    MenuBar(){
        
    JMenu archivo = new JMenu ("Archivo");
    add(archivo);    
    JMenu ayuda = new JMenu ("Ayuda");
    add(ayuda);
    
    JMenuItem salir = new JMenuItem("Salir");
    archivo.add(salir);
    JMenuItem acercade = new JMenuItem("Acerca de..");
    ayuda.add(acercade);

    ActionListener myMenubarListener =new MyMenubarListener();
    salir.addActionListener(myMenubarListener);
    acercade.addActionListener(myMenubarListener);
    ayuda.addActionListener(myMenubarListener);        
    }
}
