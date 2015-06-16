package com.peer_server;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;


public interface rmi_interface extends java.rmi.Remote
{

	public void left_traversal(String peernm , int portnm) throws RemoteException;
	public void  right_traversal_two(String peernm , int portnm) throws RemoteException;
	public void right_traversal(String peernm , int portnm) throws RemoteException;
	public void left_traversal_two(String peernm , int portnm) throws RemoteException;
	public HashMap<String,String> left_peernm(String peernm , int portnm) throws RemoteException;
	public HashMap<String,String> right_peernm(String peernm , int portnm) throws RemoteException;
	public HashMap<String,String> right_peernm_two(String peernm , int portnm) throws RemoteException;
	public HashMap<String,String> left_peernm_two(String peernm , int portnm) throws RemoteException;
	public String run_dir() throws RemoteException ;
	public void comment1() throws RemoteException;
	public boolean search_file(String filename,String pathname ) throws RemoteException;
	public String ret_conts() throws RemoteException;
	public void comment() throws RemoteException;
}


