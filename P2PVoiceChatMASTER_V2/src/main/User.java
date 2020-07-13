package main;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class User extends JFrame {

	public PrintWriter pw;
	public static boolean calling = false;
	public static void main(String[] args) {
		
	User cf = new User();
	cf.setVisible(true);
	
	
	}
	
	
	
 public int port = 8888;
 public String add_server = "192.168.0.12";
 TargetDataLine audio_in;
 SourceDataLine audio_out;
 FloatControl gain;
 

 //////////////////////////////////////////////////////////////////////////////////////////////	

 public static AudioFormat getaudAudioFormat() {

  float sampleRate = 44100;
  int sampleSizeInBits = 16;
  int channels = 2;
  boolean signed = true;
  return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, false);


 }
 ///////////////////////////////////////////////////////////////////////////////////////////

 private JPanel contentPane;

 public User() {
 	setResizable(false);
 	setTitle("P2P PROJECT");
 	setAlwaysOnTop(true);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setBounds(100, 100, 300, 200);
  contentPane = new JPanel();
  contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
  setContentPane(contentPane);
  contentPane.setLayout(new MigLayout("", "[][][][][][][][grow][][][grow][][][][][][]", "[][][][][][grow][][grow][]"));
        
        lblNewLabel_1 = new JLabel("Use common format: XXX.XXX.XXX.XXX");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel_1, "cell 2 0 12 1,alignx center,aligny center");
      
        JLabel lblNewLabel = new JLabel("Enter your peer address here:");
        lblNewLabel.setFont(new Font("Helvetica", Font.PLAIN, 11));
        contentPane.add(lblNewLabel, "cell 2 2 12 1,alignx center,aligny center");
      
        btn_start = new JButton("Start");
        btn_start.setFont(new Font("Helvetica", Font.PLAIN, 11));
        btn_start.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
	   
    	 add_server = add_field.getText();
    	if (add_server.equals("")) {
			
    		 JOptionPane.showMessageDialog(null, "Address field empty.");
    		 return;
		}
    	else if(!Pattern.matches("\\d*\\.\\d*\\.\\d*\\.\\d*", add_server)){
    		
    		JOptionPane.showMessageDialog(null, "Address not valid");
   		 return;
    		
    	}
    	 add_field.setEnabled(false);
    	   
	   btn_start.setEnabled(false);
	   btn_stop.setEnabled(true);
	   User.calling = true;
          try {
           init_audio();
          } catch (UnknownHostException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
          } catch (SocketException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
          } catch (LineUnavailableException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
          } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         }
        });
        contentPane.add(btn_start, "cell 2 3");
      
      add_field = new JTextField();
      
      contentPane.add(add_field, "cell 7 3,alignx center,aligny center");
      add_field.setColumns(10);
      
        btn_stop = new JButton("Stop");
        btn_stop.setEnabled(false);
        btn_stop.setFont(new Font("Helvetica", Font.PLAIN, 11));
        btn_stop.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
          User.calling = false;
          btn_start.setEnabled(true);
          btn_stop.setEnabled(false);
          add_field.setEnabled(true);
         }
        });
        contentPane.add(btn_stop, "cell 13 3");
        
        status = new JLabel("Disconnected");


        status.setHorizontalAlignment(SwingConstants.CENTER);
        status.setFont(new Font("SimSun", Font.PLAIN, 15));
        status.setForeground(Color.RED);
        contentPane.add(status, "cell 7 4,alignx center,aligny center");
        
        mute = new JButton("Mute");
        mute.setHorizontalTextPosition(SwingConstants.CENTER);
        mute.setFont(new Font("Helvetica", Font.PLAIN, 11));
        contentPane.add(mute, "cell 13 4");
        
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		
        	switch (slider.getValue()) {
			case 0: 
				gain.setValue((float) -80.0); System.out.println((FloatControl)audio_out.getControl(FloatControl.Type.MASTER_GAIN));
				break;
				
			case 1:gain.setValue((float) -1.6); System.out.println((FloatControl)audio_out.getControl(FloatControl.Type.MASTER_GAIN));
				break;
				
			case 2:gain.setValue((float) 2.2); System.out.println((FloatControl)audio_out.getControl(FloatControl.Type.MASTER_GAIN));
				break;
				
			case 3:gain.setValue((float) 2.8); System.out.println((FloatControl)audio_out.getControl(FloatControl.Type.MASTER_GAIN));
				break;

			case 4:gain.setValue((float) 3.6); System.out.println((FloatControl)audio_out.getControl(FloatControl.Type.MASTER_GAIN));
				break;

			case 5:
				break;

			case 6:
				break;

			case 7:
				break;

			case 8:
				break;

			case 9:
				break;


			case 10:
				break;

			default:
				break;
			}
        		
        	}
        });
        slider.setValue(5);
        slider.setMaximum(10);
        slider.setMajorTickSpacing(1);
        contentPane.add(slider, "cell 0 6 8 1,alignx center,aligny center");
        
        
 }
 
 private JButton btn_start;
 private JButton btn_stop;
 private JTextField add_field;
 public static JLabel status;
 public static String statstr;
 private JLabel lblNewLabel_1;
 public static JSlider slider;
 private JButton mute;
/////////////////////////////////////////////////////////////////////////////////////////////////////
 public void init_audio() throws LineUnavailableException, IOException {

	AudioFormat format = getaudAudioFormat();
	 //AudioFormat format = new AudioFormat(44100, 10, 2, true, false);
  DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
  DataLine.Info info_out = new DataLine.Info(SourceDataLine.class, format);
  
   
  if (!AudioSystem.isLineSupported(info)||!AudioSystem.isLineSupported(info_out)) {
   System.out.println("Error");
   System.exit(0);
  }
  
  //Client_voice.calling = true;///mora da se ukljuci prije zbog sinhronizacije
  

  
  audio_in = (TargetDataLine) AudioSystem.getLine(info);
  audio_in.open(format);
  audio_in.start();
  RecThread r = new RecThread();
  InetAddress inet = InetAddress.getByName(add_server);
  r.audio_in = audio_in;
  r.dout = new DatagramSocket();
  r.server_ip = inet;
  r.server_port = port;
  r.start();

  
  audio_out = (SourceDataLine) AudioSystem.getLine(info_out);
  audio_out.open(format);
  audio_out.start();
  Player_thread p = new Player_thread();
  p.din = new DatagramSocket(8888);
  p.audio_out = audio_out;
  p.start();
  
  
  gain = (FloatControl)audio_out.getControl(FloatControl.Type.MASTER_GAIN);
  /*System.out.println(gain);
  gain.setValue((float) 1.2);
  //btn_start.setEnabled(false);
  //btn_stop.setEnabled(true);
  
  System.out.println((FloatControl)audio_out.getControl(FloatControl.Type.MASTER_GAIN));*/
  
 }
 
//////////////////////////////////////////////////////////////////////////////////////



}