/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsymptoticFreedom;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author Geonmo
 */
public class ViewPanel extends javax.swing.JPanel {
    boolean meson ;
    static ArrayList<Quark> quark_list = new ArrayList<>();
    
    /**
     * Creates new form ViewPanel
     */
    int selected_particle;
    public ViewPanel() {
        initComponents();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**
    public int getQuarkSize(){
        return image_x_pos.size();
    }
     
    public double getDR(int quark_idx, int mouse_xpos, int mouse_ypos){
        double distance_x = image_x_pos.get( quark_idx)-mouse_xpos;
        double distance_y = image_y_pos.get( quark_idx)-mouse_ypos;
        return Math.sqrt( distance_x*distance_x + distance_y*distance_y);
    }
    public void moveQuarkPosition( int quark_idx, int x_pos, int y_pos){
        image_x_pos.set(quark_idx,image_x_pos.get(quark_idx)+x_pos);
        image_y_pos.set(quark_idx,image_y_pos.get(quark_idx)+y_pos);
    }
    */

    public void buildMeson(String flavour1, String flavour2){
        Random random = new Random();        
        String color_charge;
        int color = random.nextInt(3)+1;
        if ( color == 1 ) {
            color_charge = "red";
        }
        else if (color ==2 ) {
            color_charge = "green";
        }
        else {
            color_charge = "blue";
        }        
        //System.out.format("Color charge : %s\n",color_charge ); 
        Quark quark1,quark2;
        quark1 = new Quark(flavour1, color_charge );
        quark2 = new Quark(flavour2, color_charge );        
        meson = true;
        
        Point q1_pos,q2_pos;
        q1_pos = new Point((int)(this.getWidth()/2.0-40),(int)(this.getHeight()/2.0));
        q2_pos = new Point((int)(this.getWidth()/2.0+40),(int)(this.getHeight()/2.0));        
        quark1.setPos(q1_pos);                
        quark2.setPos(q2_pos);
        quark_list.clear();
        quark_list.add(quark1);
        quark_list.add(quark2);
        
    }
    public void buildBaryon(String flavour1, String flavour2, String flavour3){
        
            Random random = new Random();
            String[] color_charge = new String[3];
            
            int index1 = random.nextInt(3);
            int index2;// = -9;
            int index3;// = -9;
            
            if ( random.nextInt(2) == 0) {
                index2 = index1 -1 ;
                index3 = index1 +1 ;
            }
            else {
                index2 = index1+1;
                index3 = index1-1;
            }
            if ( index2 ==-1 ) index2 = 2;
            if ( index3 ==-1 ) index3 = 2;
            if ( index2 == 3 ) index2 = 0;
            if ( index3 == 3 ) index3 = 0;
            
            color_charge[index1] ="red";
            color_charge[index2] ="green";
            color_charge[index3] ="blue";            
            
            Quark quark1,quark2,quark3;
            quark1 = new Quark(flavour1,color_charge[0]);
            quark2 = new Quark(flavour2,color_charge[1]);
            quark3 = new Quark(flavour3,color_charge[2]);            
            meson = false;            
            
            quark1.setPos((int)(this.getWidth()/2.0-40),(int)(this.getHeight()/2.0-40));
            quark2.setPos((int)(this.getWidth()/2.0),(int)(this.getHeight()/2.0));
            quark3.setPos((int)(this.getWidth()/2.0+40),(int)(this.getHeight()/2.0-40));
            
            quark_list.clear();
            quark_list.add(quark1);
            quark_list.add(quark2);
            quark_list.add(quark3);
    }

    
    public void resetPanel(){
        // 이미지 정리
        System.out.println("Before clear quark_list.");
        quark_list.clear();
        System.out.println("clear quark_list.");
        if ( selected_particle == 1 ) {
            System.out.println("pion");
            buildMeson("u","dbar");
            repaint();
        }
        else if ( selected_particle == 2 ){
            System.out.println("kaon");
            buildMeson("u","sbar");
            repaint();
        }
        else if ( selected_particle == 3 ){
            System.out.println("Proton");
            buildBaryon("u","u","d");
            repaint();
        }
        else if ( selected_particle == 4 ){
            System.out.println("Neutron");
            buildBaryon("u","d","d");
            repaint();
        }
    }
    
    public void setParticle(int selected_particle) {
        this.selected_particle = selected_particle;
    }    
    
    public int getQuarkSize(){
        return quark_list.size();
    }
    public Point getPos(int idx){
        return quark_list.get(idx).pos;
    }
    public void nextStep(){
        for( int i= 0 ; i < getQuarkSize(); i++){
            for (int j=i+1 ; j< getQuarkSize(); j++){
                Quark quark1,quark2;
                quark1 = quark_list.get(i);
                quark2 = quark_list.get(j);
                
                int distance_x = quark1.pos.x- quark2.pos.x;                
                int distance_y = quark1.pos.y- quark2.pos.y;                
                double angle = Math.atan2(distance_y, distance_x);
                //System.out.printf("dis_x:%d, dis_y:%d, angle:%f\n",distance_x,distance_y,angle);
                
                Quark quark_lit = new Quark(quark2.flavour,quark2.color);
                int pos_x,pos_y;
                if ( distance_x==0) pos_x =quark2.pos.x;
                else {
                    pos_x = quark2.pos.x - distance_x/Math.abs(distance_x);
                }
                if ( distance_y==0) pos_y = quark2.pos.y;
                else {
                    pos_y = quark2.pos.y - distance_y/Math.abs(distance_y);
                }                
                quark_lit.setPos( pos_x , pos_y);
                                
                double potential = quark1.calculatePotential(quark2);
                double next_potential = quark1.calculatePotential(quark_lit);
                double delta_potential = next_potential-potential;                
                //System.out.printf("cur_poten : %f, next_poten: %f, delta_poten : %f\n",potential, next_potential,delta_potential);
                System.out.println(potential);
                System.out.println(next_potential);
                System.out.println(delta_potential);
                
                int value_x = (int)(delta_potential*Math.cos(angle)/10);
                int value_y = (int)(delta_potential*Math.sin(angle)/10);
                
                
                
                quark1.setMomentumChange(new Point(-value_x,0));
                quark2.setMomentumChange(new Point(+value_x,0));
                quark1.setMomentumChange(new Point(0,-value_y));
                quark2.setMomentumChange(new Point(0,+value_y));
                
                System.out.println("Pos:");
                System.out.println(quark1.pos);
                System.out.println(quark2.pos);
                
                quark1.translate(quark1.momentum.x,quark1.momentum.y);
                quark2.translate(quark2.momentum.x,quark2.momentum.y);
                System.out.println("momentum");
                System.out.printf("valuex :%d valuey :%d\n",value_x,value_y);
                System.out.println(quark1.momentum);
                System.out.println(quark2.momentum);
                if ( quark1.momentum.distance(new Point(0,0))>10 || quark2.momentum.distance(new Point(0,0))>10 ) {
                    quark1.friction();
                    quark2.friction();
                }                    
            }
        }
    }
    
    
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
        //System.out.println("Draw paint");
        for( int i=0 ; i< quark_list.size(); i++){                
                for ( int j=i+1 ; j< quark_list.size(); j++){                    
                    //System.out.println("Draw line");
                    int ref_x,ref_y,target_x,target_y ;
                    ref_x = quark_list.get(i).pos.x;
                    target_x = quark_list.get(j).pos.x;
                    ref_y = quark_list.get(i).pos.y;
                    target_y = quark_list.get(j).pos.y;
                    //System.out.format("%d %d %d %d\n", ref_x, target_x, ref_y, target_y);
                    grphcs.drawLine(ref_x, ref_y, target_x, target_y);
                }                
                Quark quark;
                quark = quark_list.get(i);
                grphcs.drawImage(quark.img, quark.pos.x-(int)quark.radius/2,quark.pos.y-(int)quark.radius/2, this);
            }
    }
        
    
    
    
}
