/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 *
 * @author DELL
 */
public class Hvac implements Heater, AC, Fan, HumidityController, Ventilation{

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
	boolean startNavigation = false;
	
	double temp;
	double humid;
	double aqi;
	String aqiWarning;
	
	boolean fanStatus;
	int fanSpeed;
	String mode;
	
	//boolean acCompressor;
	boolean acStatus;
	double acTemp;
	boolean acFan = true;
	
	boolean heaterStatus;
	double heaterTemp;
	
	boolean humditiyController;

	boolean ventilator;
	boolean exhaustStatus;
	int exhaustSpeed;
	
	String recipent;
	
	
	
    public void init() {
        // TODO start asynchronous download of heavy resources
    	
    }
    
   
   
    
    public static void main(String args[])
    {
    	HvacController hcontrol = new HvacController();
    	HvacNavigation hnav = new HvacNavigation();
    	hnav.setVisible(true);
    	hcontrol.setVisible(true);
    	
    	hcontrol.setTitle("HVAC Controller");
    	hnav.setTitle("HVAC Navigation");
    	
    	Hvac hvac = new Hvac();
    	hvac.startNavigation = false;
    	
        ImageIcon img = new ImageIcon("res/Campus.png");
        
        Logger logger = Logger.getLogger(Hvac.class.getName());
        FileHandler logFile;
        try {
        	logFile = new FileHandler("HVACSystem.log", true);
        	logger.addHandler(logFile);
        	SimpleFormatter formatter = new SimpleFormatter();	
			logFile.setFormatter(formatter);
        	logger.info("Starting HVAC System");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        
        ButtonGroup grp = new ButtonGroup();
        grp.add(hcontrol.autoMode);
        grp.add(hcontrol.dryMode);
        grp.add(hcontrol.fanMode);
        grp.add(hcontrol.moistMode);
        grp.add(hcontrol.winterMode);
        grp.add(hcontrol.summerMode);
        
        
        
        JLabel thumb = new JLabel();
        thumb.setIcon(img);
    	Calendar calendar = Calendar.getInstance();
    	String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    	String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    	SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
    	Date date = new Date();
    	hcontrol.jTextArea1.setText(days[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DATE) + " " + 
    	months[calendar.get(Calendar.MONTH) - 1] +" " + calendar.get(Calendar.YEAR) + "\n" + formatter.format(date));

    	/*Action Listener for generating input which is generated randomly
    	 * for this action listener any mode should be selected otherwise this 
    	 * action listener would generate error message*/
    	hvac.recipent = "ekanshnishad@gmail.com";
    	
    	hvac.mode = "";
    	
    	
    	hcontrol.fanSpeedSetter.setMajorTickSpacing(50);
    	hcontrol.fanSpeedSetter.setMinorTickSpacing(10);
    	
    	
    	Timer dateTime = new Timer();
    	TimerTask task2 = new TimerTask() {

			@Override
			public void run() {
				Date dateobj = new Date();
				hcontrol.jTextArea1.setText(days[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DATE) + " " + 
				    	months[calendar.get(Calendar.MONTH) - 1] +" " + calendar.get(Calendar.YEAR) + "\n" + formatter.format(dateobj));
				
			}
    		
    	};
    	
    	dateTime.scheduleAtFixedRate(task2, 0, 1000);
    	
    	
    	hcontrol.settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Settings settings = new Settings();
				settings.setVisible(true);
				ButtonGroup admin = new ButtonGroup();
		        admin.add(settings.defaultAdmin);
		        admin.add(settings.customAdmin);
				
				settings.customAdmin.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						settings.customAddress.setEditable(true);
						
					}
					
				});
				
				settings.saveSettings.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(settings.customAdmin.isSelected())
						{
							hvac.recipent = settings.customAddress.getText();
						}
						else {
							hvac.recipent = "ekanshnishad@gmail.com";
						}
						
						settings.dispose();
					}
					
				});
				
				settings.cancelSettings.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						
						settings.dispose();
					}
					
				});
				
				settings.viewRecords.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						try{

					        if ((new File("HVACSystem.log")).exists()) {

					            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler HVACSystem.log");
					            p.waitFor();

					        } else {

					            logger.severe("File does not exist");

					        }

					      } catch (Exception ex) {
					    	  logger.severe("Error opening file");
					        ex.printStackTrace();
					      }
					}
					
				});
		
			}
    		
    	});
    	
    	
    	hcontrol.autoMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				hvac.mode = "AUTO";
				hcontrol.autoMode.setSelected(true);
				hcontrol.winterMode.setSelected(false);
				hcontrol.summerMode.setSelected(false);
				hcontrol.winterMode.setOpaque(false);
				hcontrol.summerMode.setOpaque(false);
				hcontrol.autoMode.setOpaque(true);
				hcontrol.fanMode.setSelected(false);
				hcontrol.dryMode.setSelected(false);
				hcontrol.moistMode.setSelected(false);
				hcontrol.fanMode.setOpaque(false);
				hcontrol.dryMode.setOpaque(false);
				hcontrol.moistMode.setOpaque(false);
			}
    		
    	});
    	
    	hcontrol.winterMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				hvac.mode = "WINTER";
				hcontrol.autoMode.setSelected(false);
				hcontrol.winterMode.setSelected(true);
				hcontrol.summerMode.setSelected(false);
				hcontrol.winterMode.setOpaque(true);
				hcontrol.summerMode.setOpaque(false);
				hcontrol.autoMode.setOpaque(false);
				hcontrol.fanMode.setSelected(false);
				hcontrol.dryMode.setSelected(false);
				hcontrol.moistMode.setSelected(false);
				hcontrol.fanMode.setOpaque(false);
				hcontrol.dryMode.setOpaque(false);
				hcontrol.moistMode.setOpaque(false);
			}
    		
    	});
    	
    	hcontrol.summerMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				hvac.mode = "SUMMER";
				hcontrol.autoMode.setSelected(false);
				hcontrol.winterMode.setSelected(false);
				hcontrol.summerMode.setSelected(true);
				hcontrol.fanMode.setSelected(false);
				hcontrol.dryMode.setSelected(false);
				hcontrol.moistMode.setSelected(false);
				hcontrol.fanMode.setOpaque(false);
				hcontrol.dryMode.setOpaque(false);
				hcontrol.moistMode.setOpaque(false);
				hcontrol.winterMode.setOpaque(false);
				hcontrol.summerMode.setOpaque(true);
				hcontrol.autoMode.setOpaque(false);
			
			}
    		
    	});
    	
    	hcontrol.dryMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				hvac.mode = "DRY";
				hcontrol.autoMode.setSelected(false);
				hcontrol.winterMode.setSelected(false);
				hcontrol.summerMode.setSelected(false);
				hcontrol.fanMode.setSelected(false);
				hcontrol.dryMode.setSelected(true);
				hcontrol.moistMode.setSelected(false);
				hcontrol.fanMode.setOpaque(false);
				hcontrol.dryMode.setOpaque(true);
				hcontrol.moistMode.setOpaque(false);
				hcontrol.winterMode.setOpaque(false);
				hcontrol.summerMode.setOpaque(false);
				hcontrol.autoMode.setOpaque(false);
			}
    		
    	});
    	
    	hcontrol.moistMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				hvac.mode = "MOIST";
				hcontrol.autoMode.setSelected(false);
				hcontrol.winterMode.setSelected(false);
				hcontrol.summerMode.setSelected(false);
				hcontrol.fanMode.setSelected(false);
				hcontrol.dryMode.setSelected(false);
				hcontrol.moistMode.setSelected(true);
				hcontrol.fanMode.setOpaque(false);
				hcontrol.dryMode.setOpaque(false);
				hcontrol.moistMode.setOpaque(true);
				hcontrol.winterMode.setOpaque(false);
				hcontrol.summerMode.setOpaque(false);
				hcontrol.autoMode.setOpaque(false);
			}
    		
    	});
    	
    	hcontrol.fanMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				hvac.mode = "FAN";
				hcontrol.autoMode.setSelected(false);
				hcontrol.winterMode.setSelected(false);
				hcontrol.summerMode.setSelected(false);
				hcontrol.fanMode.setSelected(true);
				hcontrol.dryMode.setSelected(false);
				hcontrol.moistMode.setSelected(false);
				hcontrol.fanMode.setOpaque(true);
				hcontrol.dryMode.setOpaque(false);
				hcontrol.moistMode.setOpaque(false);
				hcontrol.winterMode.setOpaque(false);
				hcontrol.summerMode.setOpaque(false);
				hcontrol.autoMode.setOpaque(false);
			}
    		
    	});
    	
    	hcontrol.getInput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(hvac.mode != "")
				{
					logger.info("Get input from the sensors");
					//String tempMode;

					double temptemp = 0, temphumid = 0;
					int tempaqi;
					
					
					if(hvac.mode == "AUTO")
					{
						Random rand = new Random();
						temptemp = rand.nextDouble()*98 - 49;
						temphumid = rand.nextDouble()*90 + 5;
						tempaqi = rand.nextInt(495);
					}
					else if(hvac.mode == "WINTER")
					{
						Random rand = new Random();
						temptemp = rand.nextDouble()*40 - 20;
						temphumid = rand.nextDouble()*70 + 25;
						
						tempaqi = rand.nextInt(495);
				
					}
					else if(hvac.mode == "SUMMER"){
						Random rand = new Random();
						temptemp = rand.nextDouble()*27 + 22;
						temphumid = rand.nextDouble()*45 + 5;
						tempaqi = rand.nextInt(495);
				
					}
					else if(hvac.mode == "DRY")
					{
						Random rand = new Random();
						temptemp = rand.nextDouble()*98 - 49;
						temphumid = rand.nextDouble()*90 + 5;
						tempaqi = rand.nextInt(495);
					}
					else if(hvac.mode == "MOIST")
					{
						Random rand = new Random();
						temptemp = rand.nextDouble()*98 - 49;
						temphumid = rand.nextDouble()*90 + 5;
						tempaqi = rand.nextInt(495);
					}
					else {
						Random rand = new Random();
						temptemp = rand.nextDouble()*98 - 49;
						temphumid = rand.nextDouble()*90 + 5;
						tempaqi = rand.nextInt(495);
					}
					
				
					temptemp = ((double)Math.round(temptemp * 100))/100;
					temphumid = ((double)Math.round(temphumid * 100))/100;
				
					hcontrol.temperature.setText(Double.toString(temptemp));
					hcontrol.humidity.setText(Double.toString(temphumid));
					hcontrol.airQuality.setText(Integer.toString(tempaqi));
				
				}
				else {
					String message = "Please select any mode";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
    		
    	});
    	

    	
    	/*We have made an action listener for the submit button here
    	 * This action listener would get all the values which we have set in the hvac
    	 * navigation screen. All the attributes of the HVAC class are set in this submit button.*/
    	hcontrol.submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				logger.info("Submitting all the data to HVAC class");
				hvac.temp = Double.parseDouble(hcontrol.temperature.getText());
				hvac.humid = Double.parseDouble(hcontrol.humidity.getText());
				hvac.aqi = Integer.parseInt(hcontrol.airQuality.getText());
				if(hcontrol.fanon.isSelected())
				{
					hvac.turnFanOn();
					logger.info("Turn Fan ON.");
					hvac.setSpeed( hcontrol.fanSpeedSetter.getValue() );
					logger.info("Setting Fan Speed to" + Integer.toString(hvac.fanSpeed));
				}
				else
					hvac.turnFanOff();
				
				
				
				
				/*Error message will be shown if the inputs are not in the particular range.*/
				if(hvac.mode == "")
				{
					String message = "Please select any mode.";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
					logger.warning(message);
				}
				else if(hvac.temp > 55 || hvac.temp < -50)
				{
					String message = "Temperature should be between -50 to 55 C.";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
					logger.warning(message);
					
				}
				else if(hvac.humid < 0 || hvac.humid > 100)
				{
					String message = "Humidity should be between 0 to 100 %.";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
					logger.warning(message);
					
				}
				else if(hvac.aqi < 0 || hvac.aqi > 500)
				{
					String message = "AQI level should be between 0 to 500.";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
					logger.warning(message);
					
				}
				else
				{
					/*all the calculations and configuration of our HVAC System would be set from
					 * here. Also all the attributes in the HVAC Navigation class and gui would be
					 * set here in this part of the code.
					 */
					hvac.startNavigation = true;
				
				
                    hnav.currentFanSpeed.setText(Integer.toString(hvac.fanSpeed) + "rpm");
		    	
                    if(hvac.mode == "AUTO") {
		    		
                    	hvac.acFan = true;
                    	if(hvac.temp > 29)
                    	{
                    		hvac.turnACOn();
                    		logger.info("Turning AC ON.");
                    		hvac.turnHeaterOff();
                    		hvac.setACTemp(24);
                    		logger.info("Set AC temperature to 24 degree Celsius.");
                    		hnav.currentACTemp.setText("24 C");
                    		hvac.turnCompressorOn();
                    	}
                    	else if(hvac.temp < 21)
                    	{
                    		
                    		hvac.turnACOff();
                    		hvac.turnCompressorOff();
                    		hvac.turnHeaterOn();
                    		hvac.setHeaterTemp(24);
		    			
                    		logger.info("Turning Heater ON.");
                    		logger.info("Set heater temperature to 24 degree Celsius.");
                    	}
                    	else {
                    		hvac.turnACOff();
                    	}
                    }
                    else if(hvac.mode == "WINTER")
                    {
                    	
                    	hvac.turnACOff();
                    	hnav.currentACTemp.setText("OFF");
                    	if(hvac.temp < 18)
                    	{
		    			
                    		hvac.turnHeaterOn();
                    		logger.info("Turning Heater ON.");
                    		hvac.setHeaterTemp(22);
                    		hnav.currentHeaterTemp.setText("22 C");;
                    	}
                    }
                    else if(hvac.mode == "SUMMER")
                    {
                    	hvac.turnHeaterOff();
                    	hvac.acFan = true;
                    	if(hvac.temp > 27)
                    	{
                    		hvac.turnACOn();
                    		logger.info("Turning AC ON.");
                    		hvac.setACTemp(25);
                    		logger.info("Set AC temperature to 25 degree Celsius.");
                    		hnav.currentACTemp.setText(Double.toString(hvac.acTemp));
                    		hvac.turnCompressorOn();
                    	}
                    }
                    else if(hvac.mode == "DRY")
					{
						if(hvac.temp < 10)
						{
							hvac.turnHeaterOn();
							hvac.setHeaterTemp(25);
							hvac.turnACOff();
						}
						else if(hvac.temp >42)
						{
							hvac.turnACOn();
							hvac.turnHeaterOff();
							hvac.setACTemp(25);
						}
						else {
							hvac.turnACOff();
							hvac.turnHeaterOff();
						}
					}
					else if(hvac.mode == "MOIST")
					{
						if(hvac.temp < 10)
						{
							hvac.turnHeaterOn();
							hvac.setHeaterTemp(25);
							hvac.turnACOff();
						}
						else if(hvac.temp >42)
						{
							hvac.turnACOn();
							hvac.turnHeaterOff();
							hvac.setACTemp(25);
						}
						else {
							hvac.turnACOff();
							hvac.turnHeaterOff();
						}
					}
					else {
						hvac.turnACOff();
						hvac.turnHeaterOff();
					}
						
                        
                        if(hvac.acStatus == true)
                        {
                            
                            hnav.currentACTemp.setText(Double.toString(hvac.acTemp) + " C");
                            
                            System.out.println("hello");
                            logger.info("Starting AC");
                            logger.info("Setting AC Temp to " + hvac.acTemp);
                        }
                        else
                        {
                            hnav.currentACTemp.setText("OFF");
                        }
                        
                        if(hvac.heaterStatus == true)
                        {
                             hnav.currentHeaterTemp.setText(Double.toString(hvac.heaterTemp) + " C");
                             logger.info("Starting Heater");
                             logger.info("Setting AC Temp to " + hvac.heaterTemp);
                        }
                        else
                        {
                            hnav.currentHeaterTemp.setText("OFF");
                        }
                        
                        
                        
                        
                        //setting the air quality level and proper warning message 
                        if(hvac.aqi <= 50)
						{
							hvac.setAqiWarning("Good air quality.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							
							
							logger.finest("Good Air Quality");
						}
						else if(hvac.aqi <= 100)
						{
							hvac.setAqiWarning("Air Quality is acceptable.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							
							
							logger.finer("Air Quality is acceptable.");
							
						}
						else if(hvac.aqi <= 150)
						{
							hvac.setAqiWarning("Air Quality is unhealty for sensitive groups. "
									+ "Sensitive group must take necessary precautions.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							
							
							logger.info("Air Quality is unhealty for sensitive groups. "
									+ "Sensitive group must take necessary precautions.");
							
						}
						else if(hvac.aqi <= 200)
						{
							hvac.setAqiWarning("Air Quality is unhealthy for everyone."
									+ " Faculties should ensure that no classes are being conducted.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							
							
							logger.warning("Air Quality is unhealthy for everyone."
									+ " Faculties should ensure that no classes are being conducted.");
						}
						else if(hvac.aqi <= 300)
						{
							hvac.setAqiWarning("Health Alert!!! Dont enter the CC3 building if there isnt any need.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							
							
							logger.severe("Health Alert!!! Dont enter the CC3 building if there isnt any need.");
						}
						else
						{
							hvac.setAqiWarning("Hazardous Air Quality. Evacuate the CC3 building as soon as possible");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							
							
							logger.severe("Hazardous Air Quality. Evacuate the CC3 building as soon as possible");
						}
                        
                        
                        if(hvac.aqi > 100)
    						hvac.setExhaustSpeed((int)((double)(hvac.aqi - 100)*2.88) + 1440);
                        else
						{
                        	hvac.setExhaustSpeed( 1440);
						}
                        
                        logger.info("Turning Exhaust ON.");
                        
                        logger.info("Setting exhaust speed to " + Integer.toString(hvac.exhaustSpeed));
                        
                        
                        
                        
                        //Timer to update the current temperature humidity and air quality index
                        Timer timer = new Timer();
        		    	TimerTask timerTask = new TimerTask() {

        					@Override
        					public void run() {
        						
        						if(hvac.mode == "AUTO")
        						{
        							if(hvac.heaterStatus)
        							{
        								if(hvac.temp < 21)
        								{
        									hvac.temp = hvac.temp + 0.004761904;
        								}
        								else
        								{
        									hvac.turnHeaterOff();
        								}
        							}
        							
        							if(hvac.acStatus)
        							{
        								if(hvac.temp > 21)
        								{
        									 hvac.temp = hvac.temp - 0.0019444;
        								}
        								else
        									hvac.turnCompressorOff();
        							}
        							else
        					 			hnav.currentACTemp.setText("OFF");
        							
        							
        							if(hvac.startNavigation)
        							{
        								if(hvac.humid < 37.5)
        								{
        									hvac.increaseHumidity();
        								}
        								else if(hvac.humid > 56.25)
        								{
        									hvac.decreaseHumidity();
        								}
        							}
        							
        							
        						}
        						else if(hvac.mode == "WINTER")
        						{
        							
        				    		if(hvac.temp < 18)
        				    		{
        				    			if(hvac.heaterStatus)
        				    			{
        				    				if(hvac.heaterTemp > hvac.temp)
        				    				{
        				    					hvac.temp = hvac.temp + 0.004761904;
        				    				}
        				    			}
        				    			else
        				    			{
        				    				hvac.turnHeaterOn();
        				    				hvac.setHeaterTemp(22);
        				    			}
        				    		}
        				    		
        				    		if(hvac.startNavigation)
        				    		{
        				    			if(hvac.humid < 25)
        								{
        				    				hvac.increaseHumidity();
        								}
        								else if(hvac.humid > 42)
        								{
        									hvac.decreaseHumidity();
        								}
        				    		}
        						}
        						else if(hvac.mode == "SUMMER")
        						{
        							if(hvac.acStatus)
        							{
        								if(hvac.temp >= hvac.acTemp)
        								{
        									hvac.temp = hvac.temp - 0.0019444;
        								}
        							}
        							
        							if(hvac.startNavigation)
        							{
        								if(hvac.humid < 40)
        								{
        									hvac.increaseHumidity();
        								}
        								else if(hvac.humid > 60)
        								{
        									hvac.decreaseHumidity();
        								}
        							}
        						}
        						else if(hvac.mode == "DRY")
        						{
                                                            if(hvac.heaterStatus)
                                                            {
            							if(hvac.temp < 10)
            							{
            								hvac.temp = hvac.temp + 0.004761904;
            							}
            							else
            							{
            								hvac.turnHeaterOff();
            							}
                                                            }
            							
                                                            if(hvac.acStatus)
                                                            {
            							if(hvac.temp > 42)
            							{
            								 hvac.temp = hvac.temp - 0.0019444;
            							}
            							else
            								hvac.turnCompressorOff();
                                                            }
                                                            else
            					 		hnav.currentACTemp.setText("OFF");
            							
                                                            if(hvac.startNavigation)
                                                            {
                                                                if(hvac.humid > 25)
        							{
                                                                    hvac.decreaseHumidity();
                                                                }
        								
                                                            }
        						}
        						else if(hvac.mode == "MOIST")
        						{
                                                            if(hvac.heaterStatus)
                                                            {
            							if(hvac.temp < 10)
            							{
            								hvac.temp = hvac.temp + 0.004761904;
            							}
            							else
            							{
            								hvac.turnHeaterOff();
            							}
                                                            }
            							
                                                            if(hvac.acStatus)
                                                            {
                                                                if(hvac.temp > 42)
            							{
            								 hvac.temp = hvac.temp - 0.0019444;
            							}
            							else
            								hvac.turnCompressorOff();
                                                            }
                                                            else
            					 		hnav.currentACTemp.setText("OFF");
            							
                                                            if(hvac.startNavigation)
                                                            {
                                                                if(hvac.humid < 60)
        							{
                                                                    hvac.increaseHumidity();
        							}
        								
                                                            }
        						}
        						else {
        							hvac.heaterStatus = false;
        							hvac.acStatus = false;
            					}
            						
        						
        						
        						hvac.turnExhaustOn();
        						if(hvac.aqi > 100)
        						hvac.setExhaustSpeed((int)((double)(hvac.aqi - 100)*2.88) + 1440);
        						else
        							hvac.setExhaustSpeed( 1440);
        						
        						if(hvac.aqi <= 50)
        						{
        							hvac.setAqiWarning("Good air quality.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							
        						}
        						else if(hvac.aqi <= 100)
        						{
        							hvac.setAqiWarning("Air Quality is acceptable.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							
        						}
        						else if(hvac.aqi <= 150)
        						{
        							hvac.setAqiWarning("Air Quality is unhealty for sensitive groups. "
        									+ "Sensitive group must take necessary precautions.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							
        						}
        						else if(hvac.aqi <= 200)
        						{
        							hvac.setAqiWarning("Air Quality is unhealthy for everyone."
        									+ " Faculties should ensure that no classes are being conducted.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							
        							
        						}
        						else if(hvac.aqi <= 300)
        						{
        							hvac.setAqiWarning("Health Alert!!! Dont enter the CC3 building if there isnt any need.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							
        						}
        						else
        						{
        							hvac.setAqiWarning("Hazardous Air Quality. Evacuate the CC3 building as soon as possible");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							
        						}
        						
        						//more exhuast speed means more pollution thus less purifying rate
        						if(hvac.aqi > 100)
        						{
        							if((((double)hvac.fanSpeed + (double)hvac.exhaustSpeed - 1440)/1487) <= 0.33)
        							{
        								hvac.aqi = hvac.aqi - 0.06;
        							}
        							else if((((double)hvac.fanSpeed + (double)hvac.exhaustSpeed - 1440)/1487) <= 0.66)
        							{
        								hvac.aqi = hvac.aqi - 0.04;
        							}
        							else {
        								hvac.aqi = hvac.aqi - 0.025;
        							}
        						}
        							
        						//hvac.openVentilator();
        					
        						
        						
        						
        						//all the values and attributes of HvacNavigation class are being set
        						
        						double temptemp = hvac.temp*100;
        						temptemp = (int)temptemp;
        						temptemp = temptemp/100;
        						
        						double temphumid = hvac.humid*100;
        						temphumid = (int)temphumid;
        						temphumid = temphumid/100;
        						hnav.currentTemp.setText(Double.toString(temptemp) + " \u00B0C");
        						hnav.currentHumidity.setText(Double.toString(temphumid) + " %");
        						hnav.currentFanSpeed.setText(Integer.toString(hvac.fanSpeed) + " rpm");
        						if(hvac.acStatus)
        							hnav.currentACTemp.setText(Double.toString(hvac.acTemp) + " \u00B0C");
        						else
        							hnav.currentACTemp.setText("OFF");
        						if(hvac.heaterStatus)
        							hnav.currentHeaterTemp.setText(Double.toString(hvac.heaterTemp) + " \u00B0C");
        						else
        							hnav.currentHeaterTemp.setText("OFF");
        						
        						hnav.currentExhaustSpeed.setText(Integer.toString(hvac.exhaustSpeed) + " rpm");
        						hnav.currentAqi.setText(Integer.toString((int)hvac.aqi));
        						
        						    						
        					}
        				
        		    		
        		    	};
        		    	
        		    	//for sending email
        		    	try {
        		    		logger.info("Preparing to send email.....");
							SendEmail.sendEmail(hvac.acStatus, hvac.acTemp, hvac.heaterStatus, hvac.heaterTemp, hvac.exhaustSpeed, hvac.fanSpeed
									, hvac.temp, hvac.humid, (int)hvac.aqi, hvac.aqiWarning,hvac.recipent);
							
							
							logger.info("Message Sent Successfully!!!");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
        		    	
        		    	timer.scheduleAtFixedRate(timerTask, 0, 1000);
        		    	
        		    
        		    	
		    	
                        
				}		
				
				
				
			}
			
    		    			
    	});
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }




	@Override
	public void turnFanOn() {
		this.fanStatus = true;
	}




	@Override
	public void turnFanOff() {
		this.fanStatus = false; 
		setSpeed(0);
	}




	@Override
	public void setSpeed(int speed) {
		this.fanSpeed = speed;
		// TODO Auto-generated method stub
		
	}




	



	@Override
	public void setACTemp(double temp) {
		// TODO Auto-generated method stub
		this.acTemp = temp;
	}




	@Override
	public void increaseACTemp(double temp) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void decreaseACTemp(double temp) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public boolean turboMode(boolean turbo) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean ACFan(boolean acFan) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public void turnHeaterOn() {
		this.heaterStatus = true;
	}




	@Override
	public void turnHeaterOff() {
		this.heaterStatus = false;
	}




	@Override
	public void increaseHeaterTemp(double temp) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void decreaseHeaterTemp(double temp) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void setHeaterTemp(double temp) {
		this.heaterTemp = temp;
		
	}




	@Override
	public void turnACOn() {
		this.acStatus = true;
	}




	@Override
	public void turnACOff() {
		this.acFan = false;
		//this.acCompressor = false;
		this.acStatus = false;
	}




	@Override
	public void turnCompressorOn() {
		
		//this.acCompressor = true;
		this.temp = this.temp - 0.016666;
	}




	@Override
	public void turnCompressorOff() {
		//this.acCompressor = false;
		setACTemp(this.temp);
		// TODO Auto-generated method stub
		
	}




	@Override
	public void turnHumidityControlOn() {
		this.humditiyController = true;
	}




	@Override
	public void turnHumidityControlOff() {
		this.humditiyController = false;
	}




	@Override
	public void increaseHumidity() {
		this.humid = this.humid + 0.002105379;
	}




	@Override
	public void decreaseHumidity() {
		this.humid = this.humid - 0.0438596;	
	}




	@Override
	public void turnExhaustOn() {
		// TODO Auto-generated method stub
		this.exhaustStatus = true;
	}




	@Override
	public void turnExhaustOff() {
		// TODO Auto-generated method stub
		this.exhaustStatus = false;
	}




	@Override
	public void setExhaustSpeed(int speed) {
		// TODO Auto-generated method stub
		this.exhaustSpeed = speed;
	}




	@Override
	public void increaseExhaustSpeed() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void decreaseExhaustSpeed() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void openVentilator() {
		// TODO Auto-generated method stub
		this.ventilator = true;
	}




	@Override
	public void closeVentilator() {
		// TODO Auto-generated method stub
		this.ventilator = false;
	}




	public String getAqiWarning() {
		return aqiWarning;
	}




	public void setAqiWarning(String aqiWarning) {
		this.aqiWarning = aqiWarning;
	}
	
	






    // TODO overwrite start(), stop() and destroy() methods
}
