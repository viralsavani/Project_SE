/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SE.Dao;

import com.SE.Util.DBConnection;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Anand
 */
public class SongDao {

    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private final DBConnection db = new DBConnection();

    public String[] selectSong(int rowCount, String tableName, String columnName) {

        String[] songData = new String[rowCount];
        int i = 0;

        try {
            con = db.getCon();
            stmt = con.createStatement();
            ResultSet rs;

            switch (tableName) {
                case "library":
                    rs = stmt.executeQuery("select * from library order by " + columnName + "");
                    break;
                case "playlist":
                    rs = stmt.executeQuery("select * from library order by " + columnName + "");
                    break;
                default:
                    rs = stmt.executeQuery("Select library.id_songs, library.song_location, library.song_name, library.song_album, library.song_artist, library.genre, library.year, library.time, library.comment from playlist INNER JOIN library ON library.id_songs = playlist.id_songs AND playlist.playlist_name = '" + tableName + "' order by " + columnName + "");
                    break;
            }

            while (rs.next()) {
                songData[i] = rs.getString(2);
                i++;
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }

        return songData;
    }

    public void addSong(String[] fileDetail) {
        try {
            con = db.getCon();

            String query = "insert into library values (null,?,?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, fileDetail[0]);
            pstmt.setString(2, fileDetail[1]);
            pstmt.setString(3, fileDetail[2]);
            pstmt.setString(4, fileDetail[3]);
            pstmt.setString(5, fileDetail[4]);
            pstmt.setString(6, fileDetail[5]);
            pstmt.setString(7, fileDetail[6]);
            pstmt.setString(8, fileDetail[7]);

            pstmt.executeUpdate();

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in Stmt " + e);
        }
    }

    public void deleteSong(String songLocation, int rowNumber) {
        String uniqueID;

        try {
            con = db.getCon();
            stmt = con.createStatement();
            ResultSet rs;

            String query = "select * from library";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rowNumber != -1) {
                rs.next();
                rowNumber--;
            }

            uniqueID = rs.getString(1);

            query = "delete from library where id_songs = (?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, uniqueID);
            pstmt.executeUpdate();

            query = "delete from playlist where id_songs = (?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, uniqueID);
            pstmt.executeUpdate();

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error in DeleteSong Method....." + ex);
        }
    }

    public int newTreeNode(String nodeName) {
        try {
            con = db.getCon();

            String query = "insert into playlist_name values (null,?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, nodeName);

            pstmt.executeUpdate();
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            String msg = "Playlist with same name already exists";
            JOptionPane.showMessageDialog(new Frame(), msg, "Duplicate entry", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        return 1;
    }

    public void deletePlaylist(String playlistName) {
        try {
            con = db.getCon();
            stmt = con.createStatement();
            String queryOne = "delete from playlist_name where pn_name = '" + playlistName + "'";
            String queryTwo = "delete from playlist where playlist_name = '" + playlistName + "'";
            stmt.executeUpdate(queryOne);
            stmt.executeUpdate(queryTwo);

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("ERROR IN DELETE PLAYLIST" + ex);
        }
    }

    public List<String> getPlaylistNames() {
        List<String> playListNames = new ArrayList<>();
        try {
            con = db.getCon();
            stmt = con.createStatement();
            String query = "select * from playlist_name";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                playListNames.add(rs.getString(2));
            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error in getPlaylistNames method........" + ex);
        }

        return playListNames;
    }

    public List<String> getColNames() {
        List<String> colNames = new ArrayList<>();
        try {
            con = db.getCon();
            stmt = con.createStatement();
            String query = "select * from col_name";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                colNames.add(rs.getString(2));
                colNames.add(rs.getString(3));
            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error in getColNames method........" + ex);
        }

        return colNames;
    }

    public void setCol(String colName) {
        try {
            con = db.getCon();
            stmt = con.createStatement();

            String query = "select col_name,col_status from col_name where col_name = '" + colName + "'";
            ResultSet rs = stmt.executeQuery(query);

            rs.next();

            if (rs.getInt("col_status") == 0) {
                query = "update col_name SET col_status = 1 where col_name = '" + rs.getString("col_name") + "'";
                stmt.executeUpdate(query);
            } else {
                query = "update col_name SET col_status = 0 where col_name = '" + rs.getString("col_name") + "'";
                stmt.executeUpdate(query);
            }

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error in setCol method........" + ex);
        }
    }

    public int songLocationToID(String songLocation) {
        int id = 0;

        try {
            con = db.getCon();

            String query = "select id_songs from library where song_location= (?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, songLocation);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }
            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Errro in songLocationToID...." + ex);
        }

        return id;
    }

    public void addSongToPlaylist(int songID, String playlistName) {

        try {
            con = db.getCon();
            String query = "insert into playlist values (null, ?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, songID);
            pstmt.setString(2, playlistName);
            pstmt.executeUpdate();

            if (con != null) {

                stmt.close();
                con.close();

            }
        } catch (SQLException ex) {
            System.out.println("Error in addSongToPlaylist...." + ex);
        }
    }

    public void deleteSongWhenNotLibrary(String deleteLocation) {
        String uniqueID;
        try {
            con = db.getCon();
            stmt = con.createStatement();
            ResultSet rs;

            String query = "select * from library where song_location = (?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, deleteLocation);
            rs = pstmt.executeQuery();

            rs.next();
            uniqueID = rs.getString(1);

            query = "delete from library where id_songs = (?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, uniqueID);
            pstmt.executeUpdate();

            query = "delete from playlist where id_songs = (?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, uniqueID);
            pstmt.executeUpdate();

            if (con != null) {
                stmt.close();
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error in DeleteSong Method....." + ex);
        }
    }

    public String getLocations(String splitLocations) {
        try {
            con = db.getCon();
            ResultSet rs;
            String query = "select song_location from library where song_name = (?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, splitLocations);
            rs = pstmt.executeQuery();

            rs.next();
            
            splitLocations = rs.getString("song_location");
            
            if (con != null) {

                stmt.close();
                con.close();

            }
        } catch (SQLException ex) {
            System.out.println("Error in getLocations...." + ex);
        }
        return splitLocations;
    }
}
