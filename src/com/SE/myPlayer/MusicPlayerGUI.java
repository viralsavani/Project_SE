/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SE.myPlayer;

import com.SE.Bean.ObjectBean;
import com.SE.Dao.SongDao;
import com.SE.Util.DBConnection;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Anand
 */
public class MusicPlayerGUI extends javax.swing.JFrame {

    private String songLocation, songName, lastOpen, columnName = "song_name";
    private final String tableName;
    private final int dropControl;
    private String[] songData;
    private int currentSongRow = -1, stopCheck = 0, threadStop = 0, next = 0, previous = 0, rowCount, pointerProgress = 0, pointerDegress = 0, pointerPause = 0, volume;
    private long progressClick, songLengthSeconds, progressOneSecond;
    private Timer volumeTimer, progressTimer, volumeImg;
    private File file;
    private BasicPlayer myPlayer;
    private float volumeSet;
    private FileInputStream fis;
    private Mp3File song;
    private Image ima;
    private Connection con = null;
    private Statement stmt = null;
    private static List<ObjectBean> list = new ArrayList<>();
    private MusicPlayerGUI mpg;
    private static ObjectBean bean;
    private List<String> finalString;

    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("MP3 Files (.mp3)", "mp3");
    SongDao sd = new SongDao();
    DBConnection db = new DBConnection();

    class MenuItemAction extends AbstractAction {

        String playlistName;

        public MenuItemAction(String playlistName) {
            super(playlistName);
            this.playlistName = playlistName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                finalString = convertSelectedRowToSongLocations();
                songAddPlaylistFromLibrary(playlistName, finalString);
            } catch (IOException | UnsupportedTagException | InvalidDataException ex) {
                System.out.println("Error in MenuItemActionClass....." + ex);
            }
        }
    }

    class ColShowAction extends AbstractAction {

        String colName;

        public ColShowAction(String colName) {
            super(colName);
            this.colName = colName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            sd.setCol(colName);
            refereshColumnPopUp();

            for (ObjectBean list1 : list) {
                if (!list1.getTitle().equals("library")) {
                    list1.getMpg().getSongTable(list1.getTitle());
                } else {
                    list1.getMpg().getSongTable(list1.getLastOpen());
                }
            }
        }
    }

    /**
     * Creates new form MusicPlayerGUI
     *
     * @param tableName
     * @param dropControl
     * @param lastOpen
     * @throws java.sql.SQLException
     */
    public MusicPlayerGUI(String tableName, int dropControl, String lastOpen) throws SQLException {
        this.tableName = tableName;
        this.lastOpen = tableName;
        this.dropControl = dropControl;

        initComponents();
        setDefaultClipArt();
        song_FileChooser.setMultiSelectionEnabled(true);
        song_FileChooser.setFileFilter(fileFilter);
        folder_Playlist_Tree.setRootVisible(false);

        play_Pause_Button.requestFocus();

        URL iconURL = getClass().getResource("/Images/IconFrame.png");
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());

        addJMenuItemsToPopUP();
        refereshColumnPopUp();

        treeReferesh();
        folder_Playlist_Tree.setSelectionRow(0);
        getSongTable(tableName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        song_FileChooser = new javax.swing.JFileChooser();
        songTable_PopUp = new javax.swing.JPopupMenu();
        addPopUp = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        deletePopUp = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        addToPlaylistPopUp = new javax.swing.JMenu();
        folderTree_PopUp = new javax.swing.JPopupMenu();
        openPlaylistNewWindow_PopUp = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        deletePlaylist_PopUp = new javax.swing.JMenuItem();
        columnShow_PopUp = new javax.swing.JPopupMenu();
        Pane_MusicPlayer = new javax.swing.JPanel();
        Pane_ThumbNail = new javax.swing.JPanel();
        clipArt = new javax.swing.JLabel();
        stop_Button = new javax.swing.JButton();
        play_Pause_Button = new javax.swing.JToggleButton();
        last_Song_Button = new javax.swing.JButton();
        next_Song_Button = new javax.swing.JButton();
        volume_Slider = new javax.swing.JSlider();
        progressBar = new javax.swing.JProgressBar();
        songLabel = new javax.swing.JLabel();
        totaltimeLabel = new javax.swing.JLabel();
        remainTimeLabel = new javax.swing.JLabel();
        volumeLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Pane_FolderView = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        folder_Playlist_Tree = new javax.swing.JTree();
        Pane_Table = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        songData_Table = new javax.swing.JTable();
        Pane_Option = new javax.swing.JPanel();
        search_Button = new javax.swing.JButton();
        search_TextField = new javax.swing.JTextField();
        addSong_Button = new javax.swing.JButton();
        deleteSong_Button = new javax.swing.JButton();
        Menu = new javax.swing.JMenuBar();
        Menu_File = new javax.swing.JMenu();
        openMenu = new javax.swing.JMenuItem();
        createPlaylist = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenu = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        addSongMenu = new javax.swing.JMenuItem();
        deleteSongMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        NextMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();

        song_FileChooser.setAcceptAllFileFilterUsed(false);

        addPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file plus.png"))); // NOI18N
        addPopUp.setText("Add Song to Library");
        addPopUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPopUpActionPerformed(evt);
            }
        });
        songTable_PopUp.add(addPopUp);
        songTable_PopUp.add(jSeparator2);

        deletePopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file delete.png"))); // NOI18N
        deletePopUp.setText("Delete Song from Library");
        deletePopUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePopUpActionPerformed(evt);
            }
        });
        songTable_PopUp.add(deletePopUp);
        songTable_PopUp.add(jSeparator4);

        addToPlaylistPopUp.setText("Add Songs to Playlist");
        songTable_PopUp.add(addToPlaylistPopUp);

        openPlaylistNewWindow_PopUp.setText("Open Playlist in new Window");
        openPlaylistNewWindow_PopUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPlaylistNewWindow_PopUpActionPerformed(evt);
            }
        });
        folderTree_PopUp.add(openPlaylistNewWindow_PopUp);
        folderTree_PopUp.add(jSeparator3);

        deletePlaylist_PopUp.setText("Delete Playlist");
        deletePlaylist_PopUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePlaylist_PopUpActionPerformed(evt);
            }
        });
        folderTree_PopUp.add(deletePlaylist_PopUp);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Music Player");
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        Pane_MusicPlayer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Pane_MusicPlayer.setOpaque(false);

        Pane_ThumbNail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Pane_ThumbNail.setMaximumSize(new java.awt.Dimension(102, 102));
        Pane_ThumbNail.setPreferredSize(new java.awt.Dimension(102, 102));
        Pane_ThumbNail.setLayout(new java.awt.GridLayout(1, 0));
        Pane_ThumbNail.add(clipArt);

        stop_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stop.png"))); // NOI18N
        stop_Button.setMaximumSize(new java.awt.Dimension(39, 39));
        stop_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stop_ButtonActionPerformed(evt);
            }
        });

        play_Pause_Button.setForeground(new java.awt.Color(240, 240, 240));
        play_Pause_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/play.png"))); // NOI18N
        play_Pause_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                play_Pause_ButtonActionPerformed(evt);
            }
        });

        last_Song_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/prev 1.png"))); // NOI18N
        last_Song_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                last_Song_ButtonMouseClicked(evt);
            }
        });

        next_Song_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/next 1.png"))); // NOI18N
        next_Song_Button.setBorderPainted(false);
        next_Song_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                next_Song_ButtonMouseClicked(evt);
            }
        });

        volume_Slider.setMajorTickSpacing(50);
        volume_Slider.setMinorTickSpacing(5);
        volume_Slider.setOrientation(javax.swing.JSlider.VERTICAL);
        volume_Slider.setPaintTicks(true);
        volume_Slider.setSnapToTicks(true);
        volume_Slider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                volume_SliderMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                volume_SliderMouseReleased(evt);
            }
        });

        progressBar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        progressBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progressBarMouseClicked(evt);
            }
        });

        songLabel.setText("No song");

        totaltimeLabel.setText("0:00:00");

        remainTimeLabel.setText("0:00:00");

        volumeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/soundMedium.png"))); // NOI18N

        jLabel1.setText("/");

        javax.swing.GroupLayout Pane_MusicPlayerLayout = new javax.swing.GroupLayout(Pane_MusicPlayer);
        Pane_MusicPlayer.setLayout(Pane_MusicPlayerLayout);
        Pane_MusicPlayerLayout.setHorizontalGroup(
            Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pane_MusicPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Pane_ThumbNail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(play_Pause_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Pane_MusicPlayerLayout.createSequentialGroup()
                        .addGroup(Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Pane_MusicPlayerLayout.createSequentialGroup()
                                .addComponent(songLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pane_MusicPlayerLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(last_Song_Button)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stop_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(next_Song_Button)
                                .addGap(68, 68, 68)))
                        .addComponent(remainTimeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totaltimeLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volume_Slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volumeLabel)
                .addContainerGap(368, Short.MAX_VALUE))
        );
        Pane_MusicPlayerLayout.setVerticalGroup(
            Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pane_MusicPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(volume_Slider, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(Pane_MusicPlayerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(volumeLabel)
                            .addGroup(Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(Pane_ThumbNail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(Pane_MusicPlayerLayout.createSequentialGroup()
                                    .addGroup(Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pane_MusicPlayerLayout.createSequentialGroup()
                                            .addGap(33, 33, 33)
                                            .addGroup(Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(totaltimeLabel)
                                                .addComponent(remainTimeLabel)
                                                .addComponent(jLabel1))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(Pane_MusicPlayerLayout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addGroup(Pane_MusicPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(last_Song_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(stop_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(next_Song_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(2, 2, 2)
                                            .addComponent(songLabel)
                                            .addGap(2, 2, 2)))
                                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Pane_MusicPlayerLayout.createSequentialGroup()
                                    .addGap(58, 58, 58)
                                    .addComponent(play_Pause_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        folder_Playlist_Tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        folder_Playlist_Tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                folder_Playlist_TreeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(folder_Playlist_Tree);

        javax.swing.GroupLayout Pane_FolderViewLayout = new javax.swing.GroupLayout(Pane_FolderView);
        Pane_FolderView.setLayout(Pane_FolderViewLayout);
        Pane_FolderViewLayout.setHorizontalGroup(
            Pane_FolderViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pane_FolderViewLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        Pane_FolderViewLayout.setVerticalGroup(
            Pane_FolderViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        songData_Table.setDragEnabled(true);
        songData_Table.setDropMode(javax.swing.DropMode.ON);
        songData_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                songData_TableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(songData_Table);

        javax.swing.GroupLayout Pane_TableLayout = new javax.swing.GroupLayout(Pane_Table);
        Pane_Table.setLayout(Pane_TableLayout);
        Pane_TableLayout.setHorizontalGroup(
            Pane_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
        );
        Pane_TableLayout.setVerticalGroup(
            Pane_TableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
        );

        Pane_Option.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        search_Button.setText("Search");

        addSong_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file plus.png"))); // NOI18N
        addSong_Button.setToolTipText("Add Song to Library");
        addSong_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSong_ButtonActionPerformed(evt);
            }
        });

        deleteSong_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file delete.png"))); // NOI18N
        deleteSong_Button.setToolTipText("Delete Song from Library");
        deleteSong_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSong_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Pane_OptionLayout = new javax.swing.GroupLayout(Pane_Option);
        Pane_Option.setLayout(Pane_OptionLayout);
        Pane_OptionLayout.setHorizontalGroup(
            Pane_OptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pane_OptionLayout.createSequentialGroup()
                .addComponent(addSong_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteSong_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search_Button))
        );
        Pane_OptionLayout.setVerticalGroup(
            Pane_OptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteSong_Button)
            .addGroup(Pane_OptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(search_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(addSong_Button))
        );

        Menu_File.setText("File");

        openMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file up.png"))); // NOI18N
        openMenu.setText("Open");
        openMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuActionPerformed(evt);
            }
        });
        Menu_File.add(openMenu);

        createPlaylist.setText("Create Playlist");
        createPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPlaylistActionPerformed(evt);
            }
        });
        Menu_File.add(createPlaylist);
        Menu_File.add(jSeparator1);

        exitMenu.setText("Exit");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        Menu_File.add(exitMenu);

        Menu.add(Menu_File);

        jMenu1.setText("Edit");

        addSongMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file plus.png"))); // NOI18N
        addSongMenu.setText("Add Song to Library");
        addSongMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSongMenuActionPerformed(evt);
            }
        });
        jMenu1.add(addSongMenu);

        deleteSongMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file delete.png"))); // NOI18N
        deleteSongMenu.setText("Delete Song");
        deleteSongMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSongMenuActionPerformed(evt);
            }
        });
        jMenu1.add(deleteSongMenu);

        Menu.add(jMenu1);

        jMenu2.setText("Control");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, 0));
        jMenuItem2.setText("Play");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        NextMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.InputEvent.CTRL_MASK));
        NextMenuItem.setText("Next");
        NextMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(NextMenuItem);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Previous");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem3.setText("Play Recent");
        jMenu2.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Go to Current Song");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);
        jMenu2.add(jSeparator5);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Increase Volume");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Decrease Volume");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);
        jMenu2.add(jSeparator6);

        Menu.add(jMenu2);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane_MusicPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Pane_FolderView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Pane_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Pane_Option, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Pane_Option, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Pane_MusicPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Pane_FolderView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Pane_Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void addSong_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSong_ButtonActionPerformed
        int returnVal = song_FileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = song_FileChooser.getSelectedFiles();
            String fileLocations = Arrays.toString(files);
            try {
                finalString = convertFileString(fileLocations);
                songAddDB(finalString);
                bean = list.get(0);
                bean.getMpg().folder_Playlist_Tree.setSelectionRow(0);
            } catch (IOException | UnsupportedTagException | InvalidDataException ex) {
                System.out.println("Error in SongAddDB method from GUI..." + ex);
            }
        }
    }//GEN-LAST:event_addSong_ButtonActionPerformed

    private void songData_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songData_TableMouseClicked
        try {
            if (evt.getClickCount() == 2 | next == 1 | previous == 1 && SwingUtilities.isLeftMouseButton(evt)) {
                songPlay();
            } else if (SwingUtilities.isRightMouseButton(evt)) {
                Point point = evt.getPoint();
                int alreadySelectedRow = songData_Table.getSelectedRow();
                int currentRow = songData_Table.rowAtPoint(point);
                songData_Table.setRowSelectionInterval(alreadySelectedRow, currentRow);
                if (songData_Table.isRowSelected(currentRow)) {
                    songTable_PopUp.show(songData_Table, evt.getX(), evt.getY());
                } else {
                    songTable_PopUp.show(songTable_PopUp, evt.getX(), evt.getY());
                }
            }
        } catch (Exception e) {
            songPlay();
        };
    }//GEN-LAST:event_songData_TableMouseClicked

    private void deleteSong_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSong_ButtonActionPerformed
        if (songData_Table.getSelectedRowCount() != 0) {
            try {
                songDeleteDB();
            } catch (SQLException | BasicPlayerException | IOException ex) {
                System.out.println("Error in deleteSongButton........" + ex);
            }
        }
    }//GEN-LAST:event_deleteSong_ButtonActionPerformed

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuActionPerformed

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed
        int returnVal = song_FileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = song_FileChooser.getSelectedFile();
            songLocation = file.getAbsolutePath();
            try {
                if (threadStop != 0) {
                    stop();
                }
                stopCheck = 0;
                clipArtSet(songLocation);
                setSongName(songLocation);
                pauseSet();
                play(file);
                threadStop = 1;
            } catch (IOException | InvalidDataException | UnsupportedTagException | BasicPlayerException ex) {
                System.out.println("Error in Open method....." + ex);
            }
        }
    }//GEN-LAST:event_openMenuActionPerformed

    private void addSongMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSongMenuActionPerformed
        addSong_ButtonActionPerformed(evt);
    }//GEN-LAST:event_addSongMenuActionPerformed

    private void deleteSongMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSongMenuActionPerformed
        deleteSong_ButtonActionPerformed(evt);
    }//GEN-LAST:event_deleteSongMenuActionPerformed

    private void addPopUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPopUpActionPerformed
        addSong_ButtonActionPerformed(evt);
    }//GEN-LAST:event_addPopUpActionPerformed

    private void deletePopUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePopUpActionPerformed
        deleteSong_ButtonActionPerformed(evt);
    }//GEN-LAST:event_deletePopUpActionPerformed

    private void progressBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progressBarMouseClicked
        try {
            if (songLocation != null) {
                progressClick = (long) (((double) evt.getX() / (double) progressBar.getWidth()) * progressBar.getMaximum());
                progressBarSeek(progressClick);
            }
        } catch (BasicPlayerException ex) {
            System.out.println("Error in progressBarMouseClicked method....." + ex);
        }
    }//GEN-LAST:event_progressBarMouseClicked

    private void next_Song_ButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_next_Song_ButtonMouseClicked
        nextSongSelect();
        next = 1;
        songData_TableMouseClicked(evt);
    }//GEN-LAST:event_next_Song_ButtonMouseClicked

    private void last_Song_ButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_last_Song_ButtonMouseClicked
        previousSongSelect();
        previous = 1;
        songData_TableMouseClicked(evt);
    }//GEN-LAST:event_last_Song_ButtonMouseClicked

    private void play_Pause_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play_Pause_ButtonActionPerformed
        if (songLocation != null) {
            if (play_Pause_Button.isSelected()) {
                try {
                    if (stopCheck == 0) {
                        resume();
                    } else {
                        play(file);
                        stopCheck = 0;
                    }
                } catch (BasicPlayerException | IOException ex) {
                    System.out.println("Error in Play_Pause_ButtonActionPerformed method...." + ex);
                }
            } else {
                try {
                    pause();
                } catch (BasicPlayerException | IOException ex) {
                    System.out.println("Error in Play_Pause_ButtonActionPerformed method...." + ex);
                }
            }
        } else {
            nextSongSelect();
            next = 1;
            songData_TableMouseClicked(null);
        }
    }//GEN-LAST:event_play_Pause_ButtonActionPerformed

    private void stop_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stop_ButtonActionPerformed
        try {
            if (songLocation != null) {
                stop();
            }
        } catch (BasicPlayerException | IOException ex) {
            System.out.println("Error in Stop Button Method....." + ex);
        }
    }//GEN-LAST:event_stop_ButtonActionPerformed

    private void volume_SliderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volume_SliderMousePressed
        setVolumeImg();
    }//GEN-LAST:event_volume_SliderMousePressed

    private void volume_SliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volume_SliderMouseReleased
        stopVolumeImg();
    }//GEN-LAST:event_volume_SliderMouseReleased

    private void folder_Playlist_TreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_folder_Playlist_TreeMouseClicked
        DefaultMutableTreeNode selectedNode;

        if (evt.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(evt)) {
            selectedNode = (DefaultMutableTreeNode) folder_Playlist_Tree.getSelectionPath().getLastPathComponent();
            getSongTable(selectedNode.toString());
            lastOpen = selectedNode.toString();
            for (ObjectBean list1 : list) {
                if (list1.getTitle().equals("library")) {
                    list1.setLastOpen(lastOpen);
                }
            }
        } else if (SwingUtilities.isRightMouseButton(evt)) {
            DefaultTreeModel myModel = (DefaultTreeModel) folder_Playlist_Tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) myModel.getRoot();

            TreeNode[] nodes = myModel.getPathToRoot(root);
            TreePath treepath = new TreePath(nodes);
            folder_Playlist_Tree.setSelectionPath(treepath);
            folder_Playlist_Tree.scrollPathToVisible(treepath);

            TreePath path = folder_Playlist_Tree.getPathForLocation(evt.getX(), evt.getY());
            folder_Playlist_Tree.setSelectionPath(path);
            folder_Playlist_Tree.scrollPathToVisible(path);
            if (!folder_Playlist_Tree.isSelectionEmpty()) {
                selectedNode = (DefaultMutableTreeNode) folder_Playlist_Tree.getSelectionPath().getLastPathComponent();
                if (!"playlist".equals(selectedNode.toString()) && !"library".equals(selectedNode.toString())) {
                    folderTree_PopUp.show(folder_Playlist_Tree, evt.getX(), evt.getY());
                }
            }
        }
    }//GEN-LAST:event_folder_Playlist_TreeMouseClicked

    private void createPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPlaylistActionPerformed
        String input = JOptionPane.showInputDialog("Enter playlist Name: ");
        if (input == null) {
        } else if (!input.equals("")) {
            int validInput = sd.newTreeNode(input);
            if (validInput == 1) {
                treeReferesh();
                DefaultTreeModel model = (DefaultTreeModel) folder_Playlist_Tree.getModel();
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                DefaultMutableTreeNode playlist = (DefaultMutableTreeNode) model.getChild(root, 1);
                DefaultMutableTreeNode newPlaylist = (DefaultMutableTreeNode) model.getChild(playlist, model.getChildCount(playlist) - 1);

                getSongTable(newPlaylist.toString());

                TreeNode[] nodes = model.getPathToRoot(newPlaylist);
                TreePath treepath = new TreePath(nodes);

                folder_Playlist_Tree.setExpandsSelectedPaths(true);
                folder_Playlist_Tree.setSelectionPath(treepath);
                folder_Playlist_Tree.scrollPathToVisible(treepath);

                addJMenuItemsToPopUP();

                lastOpen = input;
                for (ObjectBean list1 : list) {
                    if (list1.getTitle().equals("library")) {
                        list1.setLastOpen(lastOpen);
                    }
                }
            } else {
                createPlaylistActionPerformed(evt);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Enter Valid Playlist Name", "Error in Name", JOptionPane.ERROR_MESSAGE);
            createPlaylistActionPerformed(evt);
        }
    }//GEN-LAST:event_createPlaylistActionPerformed

    private void openPlaylistNewWindow_PopUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openPlaylistNewWindow_PopUpActionPerformed
        try {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) folder_Playlist_Tree.getSelectionPath().getLastPathComponent();
            String selectedNodeName = selectedNode.toString();
            mpg = new MusicPlayerGUI(selectedNodeName, 1, selectedNodeName);
            mpg.setVisible(true);
            mpg.setTitle("Playlist - " + selectedNodeName);
            mpg.Pane_FolderView.setVisible(false);
            mpg.createPlaylist.setVisible(false);
            mpg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            int controlVari = 0;
            ObjectBean beanTemp = new ObjectBean();
            for (ObjectBean list1 : list) {
                if (list1.getTitle().equals(selectedNodeName)) {
                    list1.getMpg().dispose();
                    beanTemp = list1;
                    controlVari = 1;
                }
            }
            if (controlVari != 0) {
                list.remove(beanTemp);
            }

            bean = new ObjectBean();
            bean.setMpg(mpg);
            bean.setTitle(selectedNodeName);
            bean.setLastOpen("empty");
            list.add(bean);

            getSongTable("library");
            lastOpen = "library";

            for (ObjectBean list1 : list) {
                if (list1.getTitle().equals("library")) {
                    list1.setLastOpen(lastOpen);
                }
            }
            folder_Playlist_Tree.setSelectionRow(0);
        } catch (SQLException ex) {
            System.out.println("Error in Opening playlist in new windows..." + ex);
        }
    }//GEN-LAST:event_openPlaylistNewWindow_PopUpActionPerformed

    private void deletePlaylist_PopUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePlaylist_PopUpActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete PLAYLIST", "Delete Playlist", JOptionPane.OK_CANCEL_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) folder_Playlist_Tree.getSelectionPath().getLastPathComponent();
            sd.deletePlaylist(selectedNode.toString());
            ObjectBean beanx = new ObjectBean();
            for (ObjectBean list1 : list) {
                if (list1.getTitle().equals(selectedNode.toString())) {
                    list1.getMpg().dispose();
                    beanx = list1;
                }
            }
            list.remove(beanx);
            addJMenuItemsToPopUP();

            getSongTable("library");
            treeReferesh();
        }
    }//GEN-LAST:event_deletePlaylist_PopUpActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            if (songLocation != null) {
                stop();
            }
        } catch (BasicPlayerException | IOException ex) {
            System.out.println("Error in Stop Button Method....." + ex);
        }
    }//GEN-LAST:event_formWindowClosed

    private void NextMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextMenuItemActionPerformed
        nextSongSelect();
        next = 1;
        songData_TableMouseClicked(null);
    }//GEN-LAST:event_NextMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        previousSongSelect();
        previous = 1;
        songData_TableMouseClicked(null);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        play_Pause_ButtonActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        songData_Table.scrollRectToVisible(songData_Table.getCellRect(songData_Table.getSelectedRow(), 0, true));
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        int volume = volume_Slider.getValue();
        if (volume > 95) {
            volume_Slider.setValue(100);
        } else {
            volume_Slider.setValue(volume + 5);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        int volume = volume_Slider.getValue();
        if (volume < 5) {
            volume_Slider.setValue(0);
        } else {
            volume_Slider.setValue(volume - 5);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    public void setMain(MusicPlayerGUI mpg) {
        bean = new ObjectBean();
        bean.setMpg(mpg);
        bean.setTitle("library");
        bean.setLastOpen("library");
        list.add(bean);
    }

    public static void main(String args[]) throws SQLException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MusicPlayerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        MusicPlayerGUI mpgMain = new MusicPlayerGUI("library", 0, "library");
        mpgMain.setVisible(true);

        mpgMain.setMain(mpgMain);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Menu;
    private javax.swing.JMenu Menu_File;
    private javax.swing.JMenuItem NextMenuItem;
    private javax.swing.JPanel Pane_FolderView;
    private javax.swing.JPanel Pane_MusicPlayer;
    private javax.swing.JPanel Pane_Option;
    private javax.swing.JPanel Pane_Table;
    private javax.swing.JPanel Pane_ThumbNail;
    private javax.swing.JMenuItem addPopUp;
    private javax.swing.JMenuItem addSongMenu;
    private javax.swing.JButton addSong_Button;
    private javax.swing.JMenu addToPlaylistPopUp;
    private javax.swing.JLabel clipArt;
    private javax.swing.JPopupMenu columnShow_PopUp;
    private javax.swing.JMenuItem createPlaylist;
    private javax.swing.JMenuItem deletePlaylist_PopUp;
    private javax.swing.JMenuItem deletePopUp;
    private javax.swing.JMenuItem deleteSongMenu;
    private javax.swing.JButton deleteSong_Button;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JPopupMenu folderTree_PopUp;
    private javax.swing.JTree folder_Playlist_Tree;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JButton last_Song_Button;
    private javax.swing.JButton next_Song_Button;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JMenuItem openPlaylistNewWindow_PopUp;
    private javax.swing.JToggleButton play_Pause_Button;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel remainTimeLabel;
    private javax.swing.JButton search_Button;
    private javax.swing.JTextField search_TextField;
    private javax.swing.JTable songData_Table;
    private javax.swing.JLabel songLabel;
    private javax.swing.JPopupMenu songTable_PopUp;
    private javax.swing.JFileChooser song_FileChooser;
    private javax.swing.JButton stop_Button;
    private javax.swing.JLabel totaltimeLabel;
    private javax.swing.JLabel volumeLabel;
    private javax.swing.JSlider volume_Slider;
    // End of variables declaration//GEN-END:variables

    private void play(File file) throws BasicPlayerException, FileNotFoundException, IOException {
        myPlayer = new BasicPlayer();

        fis = new FileInputStream(file);
        setProgressBar();
        progressOneSecond = (fis.available() / songLengthSeconds);
        progressTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pointerProgress < songLengthSeconds) {
                    progressBar.setValue(Math.round(progressOneSecond * pointerProgress));
                    if (pointerPause == 0) {
                        pointerProgress++;
                        pointerDegress--;
                        remainTimeLabel.setText(getTime(pointerProgress));
                        totaltimeLabel.setText(getTime(pointerDegress));
                    }
                } else {
                    try {
                        stop();
                    } catch (BasicPlayerException | IOException ex) {
                        System.out.println("Error in stop Method from progressTimer....." + ex);
                    }
                }
            }
        });
        progressTimer.start();

        myPlayer.open(file);
        myPlayer.play();
        pauseSet();

        volumeTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    volumeSet = ((float) volume_Slider.getValue() / 100);
                    myPlayer.setGain(volumeSet);
                } catch (BasicPlayerException ex) {
                    System.out.println("Error in volumeTimer....." + ex);
                }
            }
        });
        volumeTimer.start();
    }

    private void songPlay() {
        next = 0;
        previous = 0;
        try {
            if (threadStop != 0) {
                stop();
            }
            stopCheck = 0;

            currentSongRow = songData_Table.getSelectedRow();
            songLocation = songData[currentSongRow];

            setSongName(songLocation);
            clipArtSet(songLocation);
            pauseSet();

            file = new File(songLocation);
            play(file);
            threadStop = 1; // int for controling thread from basicPlayer.
        } catch (IOException | InvalidDataException | UnsupportedTagException ex) {
            System.out.println("Error in SongData_TableMouseClicked Method from MusicPlayerGui class...." + ex);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(MusicPlayerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setProgressBar() throws IOException {
        progressBar.setMinimum(0);
        progressBar.setMaximum(fis.available());
    }

    private void progressBarSeek(long progressClick) throws BasicPlayerException {
        progressBar.setValue((int) progressClick);
        pointerProgress = (int) (progressClick / progressOneSecond);
        myPlayer.seek(progressClick);
    }

    private void stop() throws BasicPlayerException, IOException {
        playSet();
        stopCheck = 1;  // Variable for controlling play method after stop button in pressed
        myPlayer.stop();
        volumeTimer.stop();
        progressTimer.stop();
        pointerPause = 0;
        pointerProgress = 0;
        progressBar.setValue(0);
        remainTimeLabel.setText("0:00:00");
    }

    private void pause() throws BasicPlayerException, IOException {
        pointerPause = 1;
        playSet();
        myPlayer.pause();
    }

    private void resume() throws BasicPlayerException, IOException {
        pointerPause = 0;
        myPlayer.resume();
        pauseSet();
    }

    private void playSet() throws IOException {
        ima = ImageIO.read(getClass().getResource("/Images/play.png"));
        play_Pause_Button.setIcon(new ImageIcon(ima));
        play_Pause_Button.setSelected(false);
    }

    private void pauseSet() throws IOException {
        ima = ImageIO.read(getClass().getResource("/Images/pause.png"));
        play_Pause_Button.setIcon(new ImageIcon(ima));
        play_Pause_Button.setSelected(true);
    }

    private void setSongName(String songLocation) throws IOException, UnsupportedTagException, InvalidDataException {
        songName = songLocation.substring(songLocation.lastIndexOf("\\") + 1, songLocation.length());
        songName = songName.substring(0, songName.indexOf("."));
        songLabel.setText(songName);
    }

    private void clipArtSet(String SongLocation) throws IOException, InvalidDataException, UnsupportedTagException {
        song = new Mp3File(SongLocation);
        songLengthSeconds = song.getLengthInSeconds();
        totaltimeLabel.setText(getTime(songLengthSeconds));
        pointerDegress = (int) songLengthSeconds;
        if (song.hasId3v2Tag()) {
            ID3v2 id3v2tag = song.getId3v2Tag();
            byte[] imageData = id3v2tag.getAlbumImage();
            if (imageData != null) {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                ImageIcon icon = new ImageIcon(img.getScaledInstance(102, 102, 12));
                clipArt.setIcon(icon);
            } else {
                setDefaultClipArt();
            }
        }
    }

    private void setDefaultClipArt() {
        URL iconURL = getClass().getResource("/Images/albumart.jpg");
        ImageIcon icon = new ImageIcon(iconURL);
        clipArt.setIcon(icon);
    }

    private void nextSongSelect() {
        if ((rowCount - 1) == currentSongRow) {
            currentSongRow = 0;
            songData_Table.setRowSelectionInterval(currentSongRow, currentSongRow);
        } else {
            currentSongRow++;
            songData_Table.setRowSelectionInterval(currentSongRow, currentSongRow);
        }
    }

    private void previousSongSelect() {
        if (currentSongRow == 0 || currentSongRow == -1) {
            currentSongRow = (rowCount - 1);
            songData_Table.setRowSelectionInterval(currentSongRow, currentSongRow);
        } else {
            currentSongRow--;
            songData_Table.setRowSelectionInterval(currentSongRow, currentSongRow);
        }
    }

    public List<String> convertFileString(String fileLocations) {
        fileLocations = fileLocations.substring(1, fileLocations.length() - 1);
        String[] finalLocations = fileLocations.split(",\\s");
        finalString = Arrays.asList(finalLocations);

        return finalString;
    }

    public void songAddDB(List<String> finalString) throws IOException, UnsupportedTagException, InvalidDataException {
        for (String finalStrings : finalString) {
            if (finalStrings.endsWith(".mp3") | finalStrings.endsWith(".MP3")) {
                file = new File(finalStrings);
                String fileDetail[] = new String[8];
                fileDetail[0] = file.getAbsolutePath();
                fileDetail[1] = file.getName();
                Mp3File mp3File = new Mp3File(fileDetail[0]);
                long seconds = mp3File.getLengthInSeconds();
                if (mp3File.hasId3v2Tag()) {
                    ID3v2 id3v2 = mp3File.getId3v2Tag();

                    fileDetail[2] = id3v2.getAlbum();
                    fileDetail[3] = id3v2.getAlbumArtist();
                    fileDetail[4] = id3v2.getGenreDescription();
                    fileDetail[5] = id3v2.getYear();
                    fileDetail[7] = id3v2.getComment();
                } else {
                    fileDetail[2] = "Unknown";
                    fileDetail[3] = "Unknown";
                    fileDetail[4] = "Unknown";
                    fileDetail[5] = "Unknown";
                }
                fileDetail[6] = getTime(seconds);
                sd.addSong(fileDetail);

                for (ObjectBean list1 : list) {
                    if (list1.getTitle().equals("library")) {
                        list1.getMpg().getSongTable("library");
                    }
                }
            }
        }
    }

    public List<String> convertSelectedRowToSongLocations() {
        int[] selectedRows = songData_Table.getSelectedRows();
        String[] songLocations = new String[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            songLocations[i] = songData[selectedRows[i]];
        }
        finalString = Arrays.asList(songLocations);
        return finalString;
    }

    public void songAddPlaylistFromLibrary(String playlistName, List<String> songLocations) throws IOException, UnsupportedTagException, InvalidDataException {
        int idOfSelectedRow;
        List<String> notInLibrary = new ArrayList<>();
        for (String songLocationTemp : songLocations) {
            idOfSelectedRow = sd.songLocationToID(songLocationTemp);
            if (idOfSelectedRow != 0) {
                sd.addSongToPlaylist(idOfSelectedRow, playlistName);
            } else {
                notInLibrary.add(songLocationTemp);
            }

            for (ObjectBean list1 : list) {
                if (list1.getTitle().equals(playlistName)) {
                    list1.getMpg().getSongTable(playlistName);
                } else if (list1.getLastOpen().equals(playlistName)) {
                    list1.getMpg().getSongTable(playlistName);
                }
            }
        }
        if (!notInLibrary.isEmpty()) {
            songAddDB(notInLibrary);
            songAddPlaylistFromLibrary(playlistName, notInLibrary);
        }
    }

    private void getSongTable(String tableName) {
        tableReferesh(songData_Table, tableName, columnName);
        rowCount = songData_Table.getRowCount();
        songData = sd.selectSong(rowCount, tableName, columnName);
    }

    private void songDeleteDB() throws SQLException, BasicPlayerException, IOException {
        int[] songLocations = songData_Table.getSelectedRows();
        String deleteLocation;

        ArrayUtils.reverse(songLocations);
        int currentlyPlaying = 0;
        for (int temp : songLocations) {
            deleteLocation = songData[temp];
            for (ObjectBean list1 : list) {
                if (deleteLocation.equals(list1.getMpg().songLocation)) {
                    currentlyPlaying = 1;
                }
            }
            if (currentlyPlaying != 0) {
                int reply = JOptionPane.showConfirmDialog(new Frame(), "Song is currently playing. \n Do you still want to delete it from library?", "Delete file", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    if (lastOpen.equals("library")) {
                        sd.deleteSong(deleteLocation, temp);
                    } else {
                        sd.deleteSongWhenNotLibrary(deleteLocation);
                    }
                    for (ObjectBean list1 : list) {
                        if (deleteLocation.equals(list1.getMpg().songLocation)) {
                            list1.getMpg().stop();
                            list1.getMpg().setDefaultClipArt();
                            list1.getMpg().songLocation = null;
                            list1.getMpg().songLabel.setText("No Song");
                            list1.getMpg().totaltimeLabel.setText("/ 00:00");
                        }
                    }
                }
            } else {
                if (lastOpen.equals("library")) {
                    sd.deleteSong(deleteLocation, temp);
                } else {
                    sd.deleteSongWhenNotLibrary(deleteLocation);
                }
            }

        }

        for (ObjectBean list1 : list) {
            if (!list1.getTitle().equals("library")) {
                list1.getMpg().getSongTable(list1.getTitle());
            } else {
                list1.getMpg().getSongTable(list1.getLastOpen());
            }
        }
    }

    private String getTime(long seconds) {
        String time;
        int min, sec;

        min = (int) seconds / 60;
        sec = (int) seconds % 60;

        if (min < 10) {
            time = "0:0" + Integer.toString(min);
        } else {
            time = "0:" + Integer.toString(min);
        }
        if (sec < 10) {
            time = time + ":0" + Integer.toString(sec);
        } else {
            time = time + ":" + Integer.toString(sec);
        }
        return time;
    }

    private void setVolumeImg() {
        volumeImg = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                volume = volume_Slider.getValue();
                try {
                    if (volume > 60) {
                        ima = ImageIO.read(getClass().getResource("/Images/soundMax.png"));
                        volumeLabel.setIcon(new ImageIcon(ima));
                    } else if (volume < 60 && volume > 0) {
                        ima = ImageIO.read(getClass().getResource("/Images/soundMedium.png"));
                        volumeLabel.setIcon(new ImageIcon(ima));
                    } else if (volume == 0) {
                        ima = ImageIO.read(getClass().getResource("/Images/soundMute.png"));
                        volumeLabel.setIcon(new ImageIcon(ima));
                    }
                } catch (IOException ex) {
                    System.out.println("Error in Timer Volume Image....." + ex);
                }
            }
        });
        volumeImg.start();
    }

    private void stopVolumeImg() {
        volumeImg.stop();
    }

    MouseAdapter ma = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                int col = songData_Table.columnAtPoint(e.getPoint());
                String name = songData_Table.getColumnName(col);
                switch (name) {
                    case "name":
                        columnName = "song_name";
                        break;
                    case "Album":
                        columnName = "song_album";
                        break;
                    case "Artist":
                        columnName = "song_artist";
                        break;
                    case "Genre":
                        columnName = "genre";
                        break;
                    case "Year":
                        columnName = "year";
                        break;
                    case "Time":
                        columnName = "time";
                        break;
                    case "Comment":
                        columnName = "comment";
                        break;
                }
                getSongTable(tableName);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                columnShow_PopUp.show(songData_Table, e.getX(), e.getY());
            }
        }
    };

    public void tableReferesh(JTable songData_Table, String tableName, String columName) {
        int emptyResultSet = 0;

        try {
            con = db.getCon();
            stmt = con.createStatement();
            ResultSet rs;

            switch (tableName) {
                case "library":
                    rs = stmt.executeQuery("select * from library order by " + columName + "");
                    break;
                case "playlist":
                    rs = stmt.executeQuery("select * from library order by " + columName + "");
                    break;
                default:
                    rs = stmt.executeQuery("Select library.id_songs, library.song_location, library.song_name, library.song_album, library.song_artist, library.genre, library.year, library.time, library.comment from playlist INNER JOIN library ON library.id_songs = playlist.id_songs AND playlist.playlist_name = '" + tableName + "' order by " + columName + "");
                    break;
            }

            DefaultTableModel myModel = new DefaultTableModel() {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] songsColumnsName = {"Location", "Name", "Album", "Artist", "Genre", "Year", "Time", "Comment"};
            myModel.setColumnIdentifiers(songsColumnsName);

            ResultSetMetaData rsmd = rs.getMetaData();
            int colNumbers = rsmd.getColumnCount();

            Object[] objects = new Object[colNumbers];

            while (rs.next()) {
                emptyResultSet = 1;
                for (int i = 0; i < colNumbers - 1; i++) {
                    objects[i] = rs.getObject(i + 2);
                }
                myModel.addRow(objects);
            }

            if (emptyResultSet == 0) {
                myModel.addRow(objects);
            }

            songData_Table.setModel(myModel);

            rs = stmt.executeQuery("select col_name from col_name where col_status = 0");

            while (rs.next()) {
                songData_Table.removeColumn(songData_Table.getColumn(rs.getString(1)));
            }

            songData_Table.getTableHeader().removeMouseListener(ma);
            songData_Table.getTableHeader().addMouseListener(ma);
            songData_Table.setDragEnabled(true);
            songData_Table.setDropTarget(new DropTarget() {
                @Override
                public synchronized void drop(DropTargetDropEvent dtde) {

                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    Transferable t = dtde.getTransferable();

                    try {
                        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            Object fileList = t.getTransferData(DataFlavor.javaFileListFlavor);
                            String files = fileList.toString();
                            finalString = convertFileString(files);
                            if (dropControl == 0 && lastOpen.equals("library")) {
                                songAddDB(finalString);
                            } else if (dropControl == 0 && !lastOpen.equals("library")) {
                                songAddPlaylistFromLibrary(lastOpen, finalString);
                                getSongTable(lastOpen);
                            } else {
                                songAddPlaylistFromLibrary(tableName, finalString);
                            }
                        } else if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                            Object fileList = t.getTransferData(DataFlavor.stringFlavor);
                            String fileListString = fileList.toString();

                            fileListString = Arrays.toString(fileListString.split("\\n"));

                            String[] splitLocations = fileListString.split(",\\s");

                            for (int i = 0; i < splitLocations.length; i++) {
                                if (i == 0) {
                                    splitLocations[i] = splitLocations[i].substring(1, splitLocations[i].indexOf(".mp3") + 4);
                                } else {
                                    splitLocations[i] = splitLocations[i].substring(0, splitLocations[i].indexOf(".mp3") + 4);
                                }
                            }
                            
                            splitLocations = sd.getLocations(splitLocations);
                            
                            finalString = Arrays.asList(splitLocations);

                            if (dropControl == 0 && lastOpen.equals("library")) {
                                songAddDB(finalString);
                            } else if (dropControl == 0 && !lastOpen.equals("library")) {
                                songAddPlaylistFromLibrary(lastOpen, finalString);
                                getSongTable(lastOpen);
                            } else {
                                songAddPlaylistFromLibrary(tableName, finalString);
                            }
                        }
                    } catch (UnsupportedFlavorException | IOException | InvalidDataException | UnsupportedTagException ex) {
                        System.out.println("Error in second drop flavour............" + ex);
                    }
                }
            });

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }
    }

    private void treeReferesh() {
        DefaultTreeModel myModel = (DefaultTreeModel) folder_Playlist_Tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) myModel.getRoot();
        root.removeAllChildren();

        DefaultMutableTreeNode library = new DefaultMutableTreeNode("library");
        DefaultMutableTreeNode playlist = new DefaultMutableTreeNode("playlist");

        myModel.insertNodeInto(library, root, root.getChildCount());
        myModel.insertNodeInto(playlist, root, root.getChildCount());

        try {
            con = db.getCon();
            stmt = con.createStatement();
            String node;

            ResultSet rs = stmt.executeQuery("select pn_name from playlist_name");

            while (rs.next()) {
                node = rs.getString(1);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(node);
                myModel.insertNodeInto(newNode, playlist, playlist.getChildCount());
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        myModel.reload();
        folder_Playlist_Tree.setModel(myModel);
    }

    private void addJMenuItemsToPopUP() {
        finalString = sd.getPlaylistNames();

        JMenuItem menuItems;
        addToPlaylistPopUp.removeAll();
        for (String menuItemName : finalString) {
            menuItems = new JMenuItem(new MenuItemAction(menuItemName));
            addToPlaylistPopUp.add(menuItems);
        }
    }

    private void refereshColumnPopUp() {
        finalString = sd.getColNames();

        JCheckBoxMenuItem menuItems;
        columnShow_PopUp.removeAll();
        for (int i = 0; i < finalString.size() - 1; i += 2) {
            if (!finalString.get(i).equals("Location") && !finalString.get(i).equals("Name")) {
                menuItems = new JCheckBoxMenuItem(new ColShowAction(finalString.get(i)));
                if (finalString.get(i + 1).equals("1")) {
                    menuItems.setState(true);
                }
                columnShow_PopUp.add(menuItems);
            }
        }
    }
}
