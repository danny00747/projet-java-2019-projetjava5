package client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class ToolBar extends JFrame {
    
    private static final long serialVersionUID = 500331469341413571L;
    
    
    //Fenetre principale
    public ToolBar() {
        super("JToolBar sample");
        this.setSize(1200,930);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // ICreation + injection bare d'outil
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.add(this.createToolBar(), BorderLayout.EAST);
    }
    private void textCenter(JButton x) {
    	x.setHorizontalTextPosition(JButton.CENTER);
    	x.setVerticalTextPosition(JButton.BOTTOM);
    }
    

    /* Méthode de construction de la barre d'outils */
    private JToolBar createToolBar() {

        // La barre d'outils
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setOrientation(SwingConstants.VERTICAL);
        
        
        JTextField text = new JTextField("WEAPONS :");
        text.setEditable(false);
        toolBar.add(text);
        toolBar.addSeparator();
        
        JButton btnPrecision = new JButton( new ImageIcon("img/precision.png"));
        btnPrecision.setText("Precision shoot");
        btnPrecision.addActionListener( this::btnNewListener);
        textCenter(btnPrecision);
        toolBar.add(btnPrecision);

        JButton btnMissile = new JButton( new ImageIcon("img/missile.jpg"));
        btnMissile.setText("Missile barage");
        textCenter(btnMissile);
        toolBar.add( btnMissile );
        toolBar.addSeparator();

        JButton btnAirstrike = new JButton( new ImageIcon("img/airstrike.png"));
        btnAirstrike.setText("Airstrike");
        textCenter(btnAirstrike);
        toolBar.add(btnAirstrike);
        toolBar.addSeparator();
        
        JButton btnRadar = new JButton( new ImageIcon("img/radar.png"));
        btnRadar.setText("Radar discovery");
        textCenter(btnRadar);
        toolBar.add(btnRadar);

        return toolBar;
    }

    private void btnNewListener( ActionEvent event ) {
        JOptionPane.showMessageDialog(this, "Button clicked !");
    }
   
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        ToolBar frame = new ToolBar();
        frame.setVisible(true);
    }
}
