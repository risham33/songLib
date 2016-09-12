//Jasmine Feng and Risham Chokshi
//cs213 Assignment 1

package songList;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

public class SongLib extends JFrame implements ActionListener {
	
	
	
	//puts in all the information based on the index
	ArrayList <Song> Songs = new ArrayList<Song>();
	
	//to get the list from the Array into the Radio Button
	JList List;
	DefaultListModel songlist = new DefaultListModel();
	JScrollPane listScroller;
	
	//Display for Viewing Song
	JTextArea Display = new JTextArea("");
	JLabel Error = new JLabel("");
	//creating all the buttons
	JButton Edit = new JButton("Edit");
	JButton Add = new JButton("Add");
	JButton Delete = new JButton("Delete");
	
	//After clicking edit, these buttons should be there
	JButton Cancel = new JButton("Cancel");
	JButton Submit = new JButton("Submit");
	
	//After clicking Add, these buttons should be there
	//JButton Add_Cancel = new JButton("Cancel");
	//JButton Add_Submit = new JButton("Submit");
	
	//Creating Panel
	JPanel Song_List;
	JPanel Edit_List;
	JPanel Inside_Edit;
	JPanel AddPanel;
	JPanel Add_Inside;
	JPanel DeletePanel;
	String filename = "songs.txt";
	
	//TextBox
	JTextArea song_name = new JTextArea("Song",1,10);
	JTextArea artist_name = new JTextArea("Artist Name",1,10);
	JTextArea year = new JTextArea("Year",1,10);
	JTextArea album = new JTextArea("Album",1,10);
	//for add_inside panel
	JTextArea add_song_name = new JTextArea("Song",1,10);
	JTextArea add_artist_name = new JTextArea("Artist Name",1,10);
	JTextArea add_year = new JTextArea("Year",1,10);
	JTextArea add_album = new JTextArea("Album",1,10);
	
	public SongLib(String title) throws IOException{
		super(title);
		
		// use FlowLayout
		setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		
		//do the list
		Song_List = new JPanel(new GridLayout(0, 1));
		//Song_List.setBackground(Color.red);
		Song_List.setPreferredSize(new Dimension(250,200));
	
		readFile(filename);
		
		//add word wrap to Display
		Display.setLineWrap(true);
		Display.setOpaque(false);
		Display.setWrapStyleWord(true);
		
		//add_song_name.setAutoscrolls(true);
		//DefaultCaret caret = (DefaultCaret)add_song_name.getCaret();
		//caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//adding each component to the panel
		//adding to the list
		
		for(int i =0; i<Songs.size(); i++){
			String temp = Songs.get(i).getSongsName();
			if(temp!=null && Songs.get(i).getSongsArtist()!=null){
				//temp = temp + "~ " + Songs.get(i).getSongsArtist();
				songlist.addElement(temp);
			}
			}
	
		List = new JList(songlist);
		List.setVisibleRowCount(-1);
		//change the Display
		
		
		
		if(Songs.size()>=1){
			List.setSelectedIndex(0);
			String year_temp= ""; String album_temp = "";
			if(Songs.get(List.getSelectedIndex()).getSongsYear()==null)
				year_temp = "";
			else
				year_temp = Songs.get(List.getSelectedIndex()).getSongsYear().trim();
			if(Songs.get(List.getSelectedIndex()).getSongsAlbum()==null)
				album_temp = "";
			else
				album_temp = Songs.get(List.getSelectedIndex()).getSongsAlbum().trim();
			
			Display.setText("Details: " + Songs.get(List.getSelectedIndex()).getSongsName()+", "+Songs.get(List.getSelectedIndex()).getSongsArtist()+", "+ year_temp+", "+ album_temp);
			
			//this.Display.setText("Details: " + Songs.get(0).getSongsName()+", "+Songs.get(0).getSongsArtist()+", "+ Songs.get(0).getSongsYear()+", "+ Songs.get(0).getSongsAlbum());
		}
		
		List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listScroller = new JScrollPane(List);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		JLabel label = new JLabel("Playlist");
		label.setLabelFor(List);
		Song_List.add(label);
		Song_List.add(listScroller);
		Song_List.add(Display);
		//add the display for the first label
		
		//do the edit panel
		Edit_List = new JPanel(new FlowLayout(FlowLayout.RIGHT,1,2));
		Edit_List.setPreferredSize(new Dimension(150,250));
		
		
		Inside_Edit = new JPanel(new GridLayout(0,1));
		Inside_Edit.setPreferredSize(new Dimension(100,180));
		Inside_Edit.add(new JLabel("Song:"));
		Inside_Edit.add(song_name);
		Inside_Edit.add(new JLabel("Artist:"));
		Inside_Edit.add(artist_name);
		Inside_Edit.add(new JLabel("Year:"));
		Inside_Edit.add(year);
		Inside_Edit.add(new JLabel("Album:"));
		Inside_Edit.add(album);
		Inside_Edit.add(Cancel);
		Inside_Edit.add(Submit);
		
		
		Edit_List.add(Edit);
		
		//added this!! 
		
		Edit_List.add(Inside_Edit);
		Inside_Edit.setVisible(false);
		
		
		//do the add_delete panel
		Add_Inside = new JPanel(new GridLayout(0,1));
		Add_Inside.setPreferredSize(new Dimension(100,180));
		Add_Inside.add(new JLabel("Song:"));
		Add_Inside.add(add_song_name);
		Add_Inside.add(new JLabel("Artist:"));
		Add_Inside.add(add_artist_name);
		Add_Inside.add(new JLabel("Year:"));
		Add_Inside.add(add_year);
		Add_Inside.add(new JLabel("Album:"));
		Add_Inside.add(add_album);
		Add_Inside.add(Add);
				
		AddPanel = new JPanel(new FlowLayout());
		
		
		
		//added this!
		
		AddPanel.add(Delete);
		AddPanel.add(Add_Inside);
		//add all the panels to frame
		
		//Error.setText("Error: cannot be added");
		Error.setForeground(Color.red);
		add(Song_List);
		add(Edit_List);
		add(AddPanel);
		add(Error);
		
		Add.addActionListener(this);
		Delete.addActionListener(this);
		Edit.addActionListener(this);
		Cancel.addActionListener(this);
		Submit.addActionListener(this);
		//for testing purposes:
		
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	
            	try {
					updateFile();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
                System.exit(0);
            }
        });
		
		List.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				if(List.getSelectedIndex()!=-1){
					String year_temp= ""; String album_temp = "";
					if(Songs.get(List.getSelectedIndex()).getSongsYear()==null)
						year_temp = "";
					else
						year_temp = Songs.get(List.getSelectedIndex()).getSongsYear().trim();
					if(Songs.get(List.getSelectedIndex()).getSongsAlbum()==null)
						album_temp = "";
					else
						album_temp = Songs.get(List.getSelectedIndex()).getSongsAlbum().trim();
					
					Display.setText("Details: " + Songs.get(List.getSelectedIndex()).getSongsName()+", "+Songs.get(List.getSelectedIndex()).getSongsArtist()+", "+ year_temp+", "+ album_temp);
					song_name.setText(Songs.get(List.getSelectedIndex()).getSongsName());
					artist_name.setText(Songs.get(List.getSelectedIndex()).getSongsArtist());
					year.setText(year_temp);
					album.setText(album_temp);
				
				}
			}});
				
		    
			
		
	}
	
	
	
	public void SortList(){
		if(Songs.isEmpty()!=true)
		Collections.sort(Songs, new SongComparator());
		
	}
	
	public boolean FindList(Song comp){
		if(Songs.isEmpty()!=true)
		return Songs.contains(comp);
		//else
		return false;
	}	
	
	public void RedoList(){
		
		for(int i =Songs.size()-1; i>=0; i--){
			songlist.remove(i);
			}
		
		for(int i =0; i<Songs.size(); i++){
			String temp = Songs.get(i).getSongsName();
			if(temp!=null && Songs.get(i).getSongsArtist()!=null){
				//temp = temp + "~ " + Songs.get(i).getSongsArtist();
				songlist.addElement(temp);
			}
			}
	}
	
	public void readFile(String fileName) throws IOException{
		
		File file = new File (filename);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		
		while((line = br.readLine()) != null) {
			
			if(line.length()>1){
				//System.out.println(line);
			Song song = tokenize(line);
			Songs.add(song);
		}
		}
		
		SortList();
		
	}
	
	private Song tokenize(String line) {
		
		
		String[] tokens = new String[4];
		tokens[0]=null;
		tokens[1] = null;
		tokens[2] = null;
		tokens[3] = null;
		
		String [] tokens_hold = line.split("\\|");
		
		for(int i =0; i<tokens_hold.length;i++){
			tokens[i] = tokens_hold[i];
		}
		
		//System.out.println(tokens[0]+tokens[1]+tokens[2]+tokens[3]);
		Song song = new Song(tokens[0], tokens[1], tokens[2], tokens[3]);
		
		return song;
	}
	
	private void print(){
		for(int i=0; i<Songs.size(); i++) {
			System.out.println(Songs.get(i).SongsName + " " +Songs.get(i).SongsArtist + " " + Songs.get(i).SongsYear + " " +Songs.get(i).SongsAlbum  );
		}
	}
	
	private void updateFile() throws IOException {
		File file = new File(filename);
		String line = "";
		
		//if file doesnt exist, create it
		if(!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(int i=0; i<Songs.size(); i++) {
			String year_temp,album_temp;
			if(Songs.get(i).getSongsYear()==null)
				year_temp = "";
			else
				year_temp = Songs.get(i).getSongsYear().trim();
			if(Songs.get(i).getSongsAlbum()==null)
				album_temp = "";
			else
				album_temp = Songs.get(i).getSongsAlbum().trim();
			
			line += Songs.get(i).SongsName + "|" + Songs.get(i).SongsArtist + "|" + year_temp + "|" + album_temp +"\n";
			//System.out.println(line);
			bw.write(line);
			line = "";
		}
		
		bw.close();
		
		
	}
	
	
	public int indexOf(Object o) {
	    if (o != null && (o instanceof Song)) {
	    	Song song2 = (Song) o;
	        for (int i = 0; i < Songs.size(); i++)
	            if (song2.equals(Songs.get(i)))
	                return i;
	      }
	    return -1;
	}
	
	
	public int EditList(String name, String Artist, String Year, String album, int choice){
		
		if(choice==0){
			if(name!=null&&Artist!=null){
				Song temp = new Song(name,Artist,Year,album);
				if(FindList(temp)!=true){
					Songs.add(temp);
					String temp2 = Songs.get(Songs.size()-1).getSongsName() + "~ " + Songs.get(Songs.size()-1).getSongsArtist();
					songlist.addElement(temp2);
					Error.setText("");
					SortList();
					//find the index in the arraylist
					int index = indexOf(temp);
					
					return index;					
					}
				else{ //the name already exists
					Error.setText("Error: This song already Exist");
					Error.setForeground(Color.red);
					
				}
			}
		
		}
		else if(choice==1){
			boolean t = false;
			if(name!=null&&Artist!=null){
				for(int i =0; i<Songs.size();i++){
					if(name.equalsIgnoreCase(Songs.get(i).getSongsName())&&Artist.equalsIgnoreCase(Songs.get(i).getSongsArtist())){
						 Songs.remove(i); t=true;
						 break;
					}
				}
				if(t==false){//it was not found
					Display.setText("Error:Cannot be deleted");
				}
			
			}
			
		}
		
			return -1;		
	}
	
	//method is for 
	//this substring may have to be fixed -_- (if songs have ", " in them.)
	private String[] separateSelection(String selection) {
		String[] ans;
		
		ans = selection.split("~ ");
		
		//System.out.println("" + ans[0] + ans[1]);
		
		return ans;
	}
	
	@Override
	public void actionPerformed(ActionEvent event){
		
		if(event.getSource() == Add) {
			Error.setText("");
			//System.out.println("Add button clicked");
			
			if(Inside_Edit.isVisible()==true)
				Inside_Edit.setVisible(false);
			
			//System.out.println("You Selected : " + List.getSelectedValue());
			
			String songName = add_song_name.getText().trim(); 
			String artistName = add_artist_name.getText().trim();
			String yearValue = add_year.getText().trim();
			String albumName = add_album.getText().trim();
			
			
			
			//two types of checks
			if(songName==null || songName.length()==0 || artistName==null || artistName.length()==0){
				//throw an error
				Error.setText("Error: Fill in Song and Artist");
			}
			else{
				
				if(yearValue == null || yearValue.length()==0) yearValue = null;
				if(albumName == null || albumName.length() == 0) albumName = null;
			
			int index = EditList(songName, artistName, yearValue, albumName, 0);
			RedoList(); // adding to the list with the sorting
			
			
			if(index!=-1){ 
				List.setSelectedIndex(index);
				List.ensureIndexIsVisible(List.getSelectedIndex());
			}
			else if(Songs.isEmpty()!=true){
				List.setSelectedIndex(0);
				List.ensureIndexIsVisible(List.getSelectedIndex());
			}
				
			}
			}
		
		else if(event.getSource() == Delete) {
			Error.setText("");
			//System.out.println("Delete button clicked");
			
			if(Inside_Edit.isVisible()==true)
				Inside_Edit.setVisible(false);
			
			if(Songs.isEmpty()!=true && List.getSelectedValue()!=null){
			//String[] selection = separateSelection(List.getSelectedValue().toString());
			
			EditList(Songs.get(List.getSelectedIndex()).getSongsName(), Songs.get(List.getSelectedIndex()).getSongsArtist(), null, null, 1);
			songlist.remove(List.getSelectedIndex());
			
			if(List.getSelectedIndex()!=-1){
				List.setSelectedIndex(List.getSelectedIndex());
				List.ensureIndexIsVisible(List.getSelectedIndex());
			}
			else if(Songs.isEmpty()!=true){
				List.setSelectedIndex(0);
				List.ensureIndexIsVisible(List.getSelectedIndex());
			}
			if(Songs.isEmpty()==true)
				Display.setText("");
				Error.setText("");
				}
			else
			{
				Error.setText("Error:Cannot be deleted");
			}
		}
		
		else if(event.getSource() == Edit) {
			Error.setText("");
			//System.out.println("Edit button clicked");
			
			//first get index, check if edit matches, and then change by delete
			if(Songs!= null && List.getSelectedIndex()!=-1 && Inside_Edit.isVisible()==false){
				
				Inside_Edit.setVisible(true);
				String year_temp= ""; String album_temp = "";
				if(Songs.get(List.getSelectedIndex()).getSongsYear()==null)
					year_temp = "";
				else
					year_temp = Songs.get(List.getSelectedIndex()).getSongsYear().trim();
				if(Songs.get(List.getSelectedIndex()).getSongsAlbum()==null)
					album_temp = "";
				else
					album_temp = Songs.get(List.getSelectedIndex()).getSongsAlbum().trim();
				
				
				song_name.setText(Songs.get(List.getSelectedIndex()).getSongsName());
				artist_name.setText(Songs.get(List.getSelectedIndex()).getSongsArtist());
				year.setText(year_temp);
				album.setText(album_temp);
							
			}
			
			else if(Inside_Edit.isVisible()==true)
				Inside_Edit.setVisible(false);
			
			else
			{
				Error.setText("Error: Add to the list first");
			}
		}
		
		else if(event.getSource() == Cancel){
			//System.out.println("Cancel is clicked");
			Inside_Edit.setVisible(false);
			Error.setText("");
		}
		
		else if(event.getSource() == Submit){
			Error.setText("");
			//it takes in all the fields checks if it exists
			String songName = song_name.getText().trim();
			String artistName = artist_name.getText().trim();
			String yearValue = year.getText().trim();
			String albumName = album.getText().trim();
			
			if(songName == null || songName.length()==0 || artistName == null || artistName.length()==0)
				Error.setText("Error: Fill in Song and Artist");
			else{
				
				if(yearValue == null || yearValue.length()==0) yearValue = null;
				if(albumName == null || albumName.length() == 0) albumName = null;
			
			Song temp = new Song(songName,artistName,yearValue,albumName);
			int index = indexOf(temp);
			//System.out.println(index+" "+ songName+artistName+yearValue+albumName);
			//check if index is found
			//this exists
			boolean Situation;
			boolean Situation2;
			if(index!=-1 && Songs.get(List.getSelectedIndex()).getSongsAlbum() == null) {
				Situation = true;
			}
			else {
				Situation = index!=-1 && (Songs.get(List.getSelectedIndex()).getSongsAlbum().equalsIgnoreCase(albumName))!=true;
			}
			if(index!=-1 && Songs.get(List.getSelectedIndex()).getSongsYear() == null) {
				Situation2 = true;
			}
			else {
				Situation2 = index!=-1 && (Songs.get(List.getSelectedIndex()).getSongsYear().equalsIgnoreCase(yearValue))!=true;

			}
			if(index == -1 || Situation || Situation2){ //not found
				//removing
				//System.out.println("here");
				//String[] selection = separateSelection(List.getSelectedValue().toString());
				//EditList(selection[0], selection[1], null, null, 1);
				EditList(Songs.get(List.getSelectedIndex()).getSongsName(), Songs.get(List.getSelectedIndex()).getSongsArtist(), null, null, 1);
				songlist.remove(List.getSelectedIndex());
				//adding again
				index = EditList(songName, artistName, yearValue, albumName, 0);
				RedoList(); // adding to the list with the sorting
				
				
				if(index!=-1){ 
					List.setSelectedIndex(index);
					List.ensureIndexIsVisible(List.getSelectedIndex());
				}
				else if(Songs.isEmpty()!=true){
					List.setSelectedIndex(0);
					List.ensureIndexIsVisible(List.getSelectedIndex());
				}
				
				
				
			}
			else {
				//it is found
				Error.setText("Error: Song already exist");
			}
		}
		}
		
		else {
			System.out.println("error in action performed");
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JFrame Play_List = new SongLib("Play Your Music!");
		Play_List.setSize(450,500);
		Play_List.setResizable(false);
		Play_List.setLocationRelativeTo(null);
		Play_List.setVisible(true);
	}
	
	

}