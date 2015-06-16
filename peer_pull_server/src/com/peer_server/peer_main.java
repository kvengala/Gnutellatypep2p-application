/* Programming assignment 3 by Vengalathur Srikanth Kashyap and Vijai Bhaskar
 *  
 * 
 * */
package com.peer_server;

import java.io.BufferedReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class peer_main 
{
	public static String user_dir = System.getProperty("user.dir");
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static HashMap<String,String> rgthshmp= new HashMap<String,String>();
	private static HashMap<String,String> rgthshmp_two= new HashMap<String,String>();
	private static HashMap<String,String> lthshmp= new HashMap<String,String>();
	private static HashMap<String,String> lthshmp_two= new HashMap<String,String>();
	private static HashMap<String,String> filesmap = new HashMap<String,String>();
	public static int lthsm =2000;
	public static int rthsm =2010;
	 public static boolean server_availability(String val, int num) throws Exception
	{
		try 
		{
			(new Socket(val, num)).close();
			return true;
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return false;
		}
	}

	public static void main (String args[]) throws Exception
	{
		try
		{

		Scanner scr = new Scanner(System.in);
		int port_num = 2000;
		boolean con;
	    String peer_nm;
	    String run_dir;
	    int choose;
	    String disp_dir;
	    InetAddress ipaddress = InetAddress.getLocalHost();
		String ip = ipaddress.getHostAddress();
		peer_peer_fncs ppf = new peer_peer_fncs();
		
	    boolean check = new File("config.file").exists();
		if (check == false)
		{
	    System.out.println("give peer a name ");
	    peer_nm = br.readLine();
	    con = server_availability(ip, port_num);
		if(con == false)
		{	System.out.println("establishing the peer server");
			Registry register = LocateRegistry.createRegistry(port_num);
			register.bind(peer_nm, ppf);
		}
		else
		{
		do
		{
		port_num += 1;
		con = server_availability(ip, port_num);
		}while(con==true);
		System.out.println("establishing the peer server");
		Registry register = LocateRegistry.createRegistry(port_num);
		register.bind(peer_nm, ppf);
		}
		System.out.println("Writing details in the configuration file");
		set_values("PeerName", peer_nm);
		System.out.println("give the portnumber");
		set_values("portid", Integer.toString(port_num));
		set_values("IPAddress",ip );
		set_values("DownloadDirectory",user_dir);
		set_values("lftpnm","undef");
		set_values("lftprtnm","undef");
		set_values("rftpnm","undef");
		set_values("rftprtnm","undef");
		set_values("lftpnmtwo","undef");
		set_values("lftprtnmtwo","undef");
		set_values("rftpnmtwo","undef");
		set_values("rftprtnmtwo","undef");
		System.out.println("Enter the display directory name");
		disp_dir = br.readLine();
		set_values("FileListDIr",disp_dir);
		}
		else 
		{
			System.out.println("The config file is already set up");
			File file = new File(user_dir+"\\config.file");
			FileInputStream finpt = new FileInputStream(file);
			Properties property= new Properties();
			property.load(finpt);
			finpt.close();
			Registry register = LocateRegistry.createRegistry(Integer.parseInt(property.getProperty("portid")));
			register.bind(property.getProperty("PeerName"), ppf);
		}
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		
		do
			{
			String[] filelist = file_list(property.getProperty("FileListDIr"));
			for(int i=0;i<filelist.length;i++)
			{
				//System.out.println(filelist[i]);
				String timeStamp = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").format(new Date());
				//System.out.println(filelist[i]  +"  " +timeStamp );
				filesmap.put(filelist[i], timeStamp);
			}
			Date dt = new Date();
			long once_two_mins = 1000*60*2;
			//TimerTask check2  = new runinbackground();
		    // Timer timer = new Timer();
		     //timer.scheduleAtFixedRate(check2,dt.getTime(),once_two_mins);
		     
			System.out.println("Select from the follwoing options"
			
				+ "\n 1.look up files available in your directory"
				+ "\n 2.look the downloaded files"
				+ "\n 3.Commit changes with the other peer"
				+ "\n 4.Search for a file in other peers");
		System.out.println("\nSelect from the following options");
	   choose = scr.nextInt();
		switch(choose)
		{
		case 1:
			String[] filelist2 = file_list(property.getProperty("FileListDIr"));
			for(int i=0;i<filelist.length;i++)
			{
				System.out.println(filelist2[i]);
				String timeStamp = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").format(new Date());
				//filesmap.put(filelist2[1], timeStamp);
			}
			break;
			
		case 2:
			//Date date = new Date();
			String[] filelist1 = file_list(user_dir);
			for(int i=0;i<= filelist1.length;i++)
			{
				System.out.println(filelist1[i]);
				//String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				//filesmap.put(filelist1[1], timeStamp);
			}
			break;
		case 3:
			 long Starttm = System.nanoTime();
			int iter;
			int val1;
			String[] filelist3 = file_list(property.getProperty("FileListDIr"));
    		Registry regy2 = LocateRegistry.getRegistry(property.getProperty("IPAddress"),Integer.parseInt(prop.getProperty("portid")));
			rmi_interface lookup2 = (rmi_interface) regy2.lookup(property.getProperty("PeerName"));
			lookup2.left_traversal(property.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			int pnu = Integer.parseInt(property.getProperty("portid"));
			lookup2.right_traversal(property.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			lookup2.left_traversal_two(property.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			lookup2.right_traversal_two(property.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			//lthshmp = lookup2.left_peernm(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			//lthshmp_two = lookup2.left_peernm_two(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			//rgthshmp = lookup2.right_peernm(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			//rgthshmp_two = lookup2.right_peernm_two(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			for(int i=0;i< filelist3.length;i++)
			{
				System.out.println(filelist3[i]);
			}   
			    System.out.println("Select the file that is modified");
			    String fname = br.readLine();
				File fil = new File(prop.getProperty("FileListDIr")+"\\"+fname);
				Date lastModified = new Date(fil.lastModified());
				DateFormat d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String formatter = d.format(lastModified); 
				String oldtim = (String)filesmap.get(fname);
				//System.out.println(oldtim);
				//System.out.println(filelist3[i]);
				Date oldtimes = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss",Locale.ENGLISH).parse(oldtim);
				String [] val = prop.getProperty("PeerName").split("-");
				val1 =  Integer.parseInt(val[1]);
				for ( iter = pnu-1; iter >= lthsm ; iter-- ) 
				{
					String [] valq = prop.getProperty("PeerName").split("-");
					 val1 =  val1 -1 ;
					System.out.println("1 for"); 
					System.out.println(iter);
                     System.out.println(valq[0]+"-"+Integer.toString(val1));
					con = server_availability(ip, iter);
					if (con == true)
					{
						Registry reg = LocateRegistry.getRegistry(ip,iter);
						rmi_interface lookups = (rmi_interface) reg.lookup(valq[0]+"-"+Integer.toString(val1));
						String peer_dir = lookups.ret_conts();
						String [] fulldirs = peer_dir.split("=");
                        System.out.println("1 for"); 
						System.out.println(iter);
                         System.out.println(valq[0]+"-"+Integer.toString(val1));
                    boolean ispresent  = new File(fulldirs[0]+"\\"+fname).exists();
					if (ispresent == true)
					{
						lookups.comment();
					}
					
				}
				}
				pnu = Integer.parseInt(prop.getProperty("portid"));
				 con = server_availability(ip, pnu);
					String [] valq = prop.getProperty("PeerName").split("-");
				 val1 =  Integer.parseInt(valq[1]) ;
				 for ( iter = pnu+1; iter <= rthsm ; iter++ ) 
					{
				
						val1  += 1;
						con = server_availability(ip, iter);
						if (con == true)
						{
							Registry reg = LocateRegistry.getRegistry(ip,iter);
							rmi_interface lookups = (rmi_interface) reg.lookup(valq[0]+"-"+Integer.toString(val1));
							String peer_dir = lookups.ret_conts();
							String [] fulldirs = peer_dir.split("=");
						    System.out.println("2 for"); 
							System.out.println(iter);
	                         System.out.println(valq[0]+"-"+Integer.toString(val1));
	                    boolean ispresent  = new File(fulldirs[0]+"\\"+fname).exists();
						if (ispresent == true)
						{
							lookups.comment();
						}
						
						}
					}
				 long timetaken = System.nanoTime()- Starttm;
			System.out.println("The time  is " + timetaken/1e6 + "milliseconds");
					
            break;
		case 4:
			String fullreturnhname;
			String[]fullpathname;
			//int val1 ;
			Registry regy3 = LocateRegistry.getRegistry(property.getProperty("IPAddress"),Integer.parseInt(property.getProperty("portid")));
			rmi_interface lookup3 = (rmi_interface) regy3.lookup(property.getProperty("PeerName"));
			lookup3.left_traversal(property.getProperty("PeerName"), Integer.parseInt(property.getProperty("portid")));
			lookup3.right_traversal(property.getProperty("PeerName"), Integer.parseInt(property.getProperty("portid")));
			lookup3.left_traversal_two(property.getProperty("PeerName"), Integer.parseInt(property.getProperty("portid")));
			lookup3.right_traversal_two(property.getProperty("PeerName"), Integer.parseInt(property.getProperty("portid")));
			lthshmp = lookup3.left_peernm(property.getProperty("PeerName"), Integer.parseInt(property.getProperty("portid")));
			lthshmp_two = lookup3.left_peernm_two(property.getProperty("PeerName"), Integer.parseInt(property.getProperty("portid")));
			rgthshmp = lookup3.right_peernm(property.getProperty("PeerName"), Integer.parseInt(property.getProperty("portid")));
			rgthshmp_two = lookup3.right_peernm_two(property.getProperty("PeerName"), Integer.parseInt(property.getProperty("portid")));
			 pnu = Integer.parseInt(property.getProperty("portid"));
			System.out.println("Enter a file to be searched");
			String filename = br.readLine();
			 valq = property.getProperty("PeerName").split("-");
			 val1 =  Integer.parseInt(valq[1]);
			for (  iter = pnu-1; iter <= lthsm ; iter-- ) 
			{
				val1 =  val1 - 1;
				con = server_availability(ip, iter);
				if (con == true)
				{
				Registry reg = LocateRegistry.getRegistry(ip,iter);
				rmi_interface lookups = (rmi_interface) reg.lookup(valq[0]+"-"+Integer.toString(val1));
				fullreturnhname = lookups.ret_conts();
				fullpathname = fullreturnhname.split("=");
				boolean fls = lookups.search_file(filename, fullpathname[1]);
				
				if (fls==true )
				{
					System.out.println("The file is present in the following address \n" +fullpathname[1] );
					
				}else
				{
					System.out.println("\nThe file was searched in the following address " +fullpathname[1]  + "and not found");
				}
				}
				
			}
			pnu = Integer.parseInt(prop.getProperty("portid"));
			 con = server_availability(ip, pnu);
			 val1 =  Integer.parseInt(valq[1]);
				for (  iter = pnu+1; iter <= rthsm ; iter++ ) 
				{
					val1 =  val1 + 1;
					con = server_availability(ip, iter);
					if (con == true)
					{
					Registry reg = LocateRegistry.getRegistry(ip,iter);
					rmi_interface lookups = (rmi_interface) reg.lookup(valq[0]+"-"+Integer.toString(val1));
					fullreturnhname = lookups.ret_conts();
					fullpathname = fullreturnhname.split("=");
					boolean fls = lookups.search_file(filename, fullpathname[1]);
					if (fls==true )
					{
						System.out.println("The file is present in the following address \n" +fullpathname[1] );
						
					}else
					{
						System.out.println("\nThe file was searched in the following address " +fullpathname[1]  + "and not found");
					}
					}
					
				}

			System.out.println("Do you want to download the specified file "
								+ "\n y-yes and n -no");
			String downans = br.readLine();
			if (downans.equals("y"))
			{
				System.out.println("Enter the folder pathname ");
				String pnm = br.readLine();
				byte[] read = fileread(pnm,filename);
				if (read!=null)
				{
					filewrite(read,user_dir,filename);
					System.out.println("the file is moved");
				}
				else
				{
					System.out.println("the file was not present hence not moved");
				}
			}
			else
			{
				System.out.println("Continue...");
			}
			break;		
			
		}
		
			}while(choose != 5);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	//fof accessing the property file.
	public static Properties prop = new Properties();   
	public static  void set_values( String Title, String Value)
	{
   try
   {
	
	prop.setProperty(Title, Value);
	prop.store(new FileOutputStream("config.file"), null);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		
	}
	public static String[]  file_list(String address)
	{       String addr = address;
	        File directory = new File(addr);
	        File[] f = directory.listFiles();     
	        List<String> files = new ArrayList<String>();
	    for(int i=0; i < f.length; i++)
        {
	            if(f[i].isFile()){
	                files.add(f[i].getName());
	            }
	        }
	   return files.toArray(new String[]{});
	}
	public static  byte[]  fileread( String Folderaddress , String filepath) throws Exception 
	{
	String filepathname = Folderaddress+ "\\" + filepath;
	File filenm = new File(filepathname);
	byte[] bytearray = new byte[(int)filenm.length()];
	boolean fexists = filenm.exists();
	//inst.writeObject(filenm.getName());
	//bytearray = new byte[(int)filenm.length()];
	if (fexists == true)
	{
	FileInputStream fstr = new FileInputStream(filenm);
	BufferedInputStream bin = new BufferedInputStream(fstr);
	bin.read(bytearray,0, bytearray.length);
	}
	else {
		bytearray = null;
	}
	return bytearray;
	}
	// file write method to write data to a file 
	public static  void filewrite (byte[] bytes , String foldernam , String filename) throws Exception
	{
		String filepathname = foldernam+ "\\" + filename;
		int total = 0;
		int brd= 0;
		//File filenm = new File(filepathname);
		FileOutputStream fostr = new FileOutputStream(filepathname);
		BufferedOutputStream bost= new BufferedOutputStream(fostr);
		bost.write(bytes,0,bytes.length);
		bost.flush();
		bost.close();
	}
   /* public static  class runinbackground extends TimerTask
    {
       Thread t1 = new Thread(){    
    	public void run()
    	{
    		try
    		{
    		String[] filelist3 = file_list(prop.getProperty("FileListDIr"));	
    		Registry regy2 = LocateRegistry.getRegistry(prop.getProperty("IPAddress"),Integer.parseInt(prop.getProperty("portid")));
			rmi_interface lookup2 = (rmi_interface) regy2.lookup(prop.getProperty("PeerName"));
			lookup2.left_traversal(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			int pnu = Integer.parseInt(prop.getProperty("portid"));
			lookup2.right_traversal(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			lookup2.left_traversal_two(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			lookup2.right_traversal_two(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			lthshmp = lookup2.left_peernm(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			lthshmp_two = lookup2.left_peernm_two(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			rgthshmp = lookup2.right_peernm(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			rgthshmp_two = lookup2.right_peernm_two(prop.getProperty("PeerName"), Integer.parseInt(prop.getProperty("portid")));
			InetAddress ipaddress = InetAddress.getLocalHost();
			String ip = ipaddress.getHostAddress();
			for(int i=0;i< filelist3.length;i++)
			{
				File fil = new File(prop.getProperty("FileListDIr")+"\\"+filelist3[i]);
				Date lastModified = new Date(fil.lastModified());
				DateFormat d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String formatter = d.format(lastModified); 
				String oldtim = (String)filesmap.get(filelist3[i]);
				System.out.println(oldtim);
				System.out.println(filelist3[i]);
				Date oldtimes = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss",Locale.ENGLISH).parse(oldtim);
				String [] val = prop.getProperty("PeerName").split("-");
				int pij =  Integer.parseInt(val[1]);
				if (oldtimes.before(lastModified))
				{
				for ( int iter = pnu-1; iter <= lthsm ; iter-- ) 
				{
					String [] valq = prop.getProperty("PeerName").split("-");
					int val1 =  Integer.parseInt(valq[1]) - 1;
					Registry reg = LocateRegistry.getRegistry(ip,iter);
					rmi_interface lookups = (rmi_interface) reg.lookup(valq[0]+"-"+Integer.toString(val1));
					String peer_dir = lookups.ret_conts();
					String [] fulldirs = peer_dir.split("=");
					boolean ispresent  = new File(fulldirs[0]+"\\"+filelist3[i]).exists();
					if (ispresent == true)
					{
						lookups.comment();
					}
					
				}
				pnu = Integer.parseInt(prop.getProperty("portid"));
				boolean con = server_availability(ip, pnu);
				do
				{
				pnu += 1;
				con = server_availability(ip, pnu);
				String [] valq = prop.getProperty("PeerName").split("-");
				int val1 =  Integer.parseInt(valq[1]) + 1;
				Registry reg = LocateRegistry.getRegistry(ip,pnu);
				rmi_interface lookups = (rmi_interface) reg.lookup(valq[0]+"-"+Integer.toString(val1));
				String peer_dir = lookups.ret_conts();
				String [] fulldirs = peer_dir.split("=");
				boolean ispresent  = new File(fulldirs[0]+"\\"+filelist3[i]).exists();
				if (ispresent == true)
				{
					lookups.comment();
				}
				}while(con==true);
				}
				else
				{
					for ( int iter = pnu-1; iter <= lthsm ; iter-- ) 
					{
						String [] valq = prop.getProperty("PeerName").split("-");
						int val1 =  Integer.parseInt(valq[1]) - 1;
						Registry reg = LocateRegistry.getRegistry(ip,iter);
						rmi_interface lookups = (rmi_interface) reg.lookup(valq[0]+"-"+Integer.toString(val1));
						String peer_dir = lookups.ret_conts();
						String [] fulldirs = peer_dir.split("=");
						boolean ispresent  = new File(fulldirs[0]+"\\"+filelist3[i]).exists();
						if (ispresent == true)
						{
							lookups.comment1();
						}
						
					}
					pnu = Integer.parseInt(prop.getProperty("portid"));
					boolean con = server_availability(ip, pnu);
					do
					{
					pnu += 1;
					con = server_availability(ip, pnu);
					String [] valq = prop.getProperty("PeerName").split("-");
					int val1 =  Integer.parseInt(valq[1]) + 1;
					Registry reg = LocateRegistry.getRegistry(ip,pnu);
					rmi_interface lookups = (rmi_interface) reg.lookup(valq[0]+"-"+Integer.toString(val1));
					String peer_dir = lookups.ret_conts();
					String [] fulldirs = peer_dir.split("=");
					boolean ispresent  = new File(fulldirs[0]+"\\"+filelist3[i]).exists();
					if (ispresent == true)
					{
						lookups.comment1();
					}
					}while(con==true);
				}
			}
    		
		}catch(Exception e)
    		{
    		e.printStackTrace();
    		}
    	}
       };
       public void run()
       {
       	t1.start();
       }
    }*/
    
}
    