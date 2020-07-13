package main;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.sound.sampled.SourceDataLine;

public class Player_thread extends Thread{
	
	public DatagramSocket din;
	public SourceDataLine audio_out;
	byte[] byBuff = new byte[1024];
	
	
	public String aux="";///

	@Override
	public void run() {
		
	
		User.slider.setEnabled(true);
		int i=0;
		DatagramPacket incoming = new DatagramPacket(byBuff, byBuff.length);
		while (User.calling) {
			/*
			if (din.getInetAddress() == null) {
				System.out.println("no incoming transmission");
				break;
			}
			*/
			User.status.setText("Connected");
			User.status.setForeground(Color.green);
			try {
				din.receive(incoming);
				byBuff = incoming.getData();
				audio_out.write(byBuff, 0, byBuff.length);
				
				aux="recieved#"+i++;
				
				//System.out.println(aux);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		audio_out.close();
		audio_out.drain();
		System.out.println("Terminated{Thread_Player}");
		din.close();
		User.status.setText("Discnnected");
		User.status.setForeground(Color.RED);
		User.slider.setEnabled(false);
	
	}
}
