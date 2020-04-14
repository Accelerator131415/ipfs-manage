package application;

import java.awt.EventQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.Controller.CenterController;
import application.view.MainGUI;

public class Main {

public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					
					//window.frmIpfs.setVisible(true);
					new Thread(window).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
}
