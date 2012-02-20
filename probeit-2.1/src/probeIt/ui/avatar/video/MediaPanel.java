package probeIt.ui.avatar.video;

// Fig 21.6: MediaPanel.java
// A JPanel the plays media from a URL
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.net.URL;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JPanel;
import java.net.*;

public class MediaPanel extends JPanel
{
    private URL mediaURL;
    private Player mediaPlayer;

    public static String FUN = "file:///c:/tmp/AlienSong.mpg";
    public static String SIMPLE_WELCOME = "file:///c:/NetBeansProjects/AvatarWithVideo/src/edu/utep/trust/video/oracle/simple-welcome-crop.mpg";
    public static String FIRST_WELCOME = "file:///c:/NetBeansProjects/AvatarWithVideo/src/edu/utep/trust/video/oracle/first-welcome-crop.mpg";
    public static String HAS_ANSWER = "file:///c:/NetBeansProjects/AvatarWithVideo/src/edu/utep/trust/video/oracle/has-answers-crop.mpg";
    public static String NO_ANSWER = "file:///c:/NetBeansProjects/AvatarWithVideo/src/edu/utep/trust/video/oracle/no-answers-crop.mpg";
    public static String SELECT_PUBLICATION = "file:///c:/NetBeansProjects/AvatarWithVideo/src/edu/utep/trust/video/oracle/select-publication-crop.mpg";
    public static String CONNECT_PUBLICATION = "file:///c:/NetBeansProjects/AvatarWithVideo/src/edu/utep/trust/video/oracle/connect-publication-crop.mpg";
    public static String SELECT_PRODUCT = "file:///c:/NetBeansProjects/AvatarWithVideo/src/edu/utep/trust/video/oracle/select-product-crop.mpg";
    public static String CONNECT_PRODUCT = "file:///c:/NetBeansProjects/AvatarWithVideo/src/edu/utep/trust/video/oracle/connect-product-crop.mpg";

    //URL tempURL = new URL("http://www.linux-video.net/Samples/Mpeg1/AlienSong.mpg");
    //URL tempURL = new URL("file:///c:/tmp/AlienSong.mpg");
    
    public void invokeMediaPanel(String requestedURL) {
        try {
            mediaURL = new URL(requestedURL);
        } catch(MalformedURLException e) {
	    e.printStackTrace();
        }
        try
        {

           // create a player to play the media specified in the URL
           mediaPlayer = Manager.createRealizedPlayer( mediaURL );

           // get the components for the video and the playback controls
           Component video = mediaPlayer.getVisualComponent();
           //Component controls = mediaPlayer.getControlPanelComponent();

           removeAll();
           if ( video != null )
              add( video, BorderLayout.CENTER ); // add video component

           invalidate();
           validate();
           
           //if ( controls != null )
           //   add( controls, BorderLayout.SOUTH ); // add controls

           mediaPlayer.start(); // start playing the media clip
         } // end try
         catch ( NoPlayerException noPlayerException )
         {
             System.err.println( "No media player found" );
         } // end catch
         catch ( CannotRealizeException cannotRealizeException )
         {
             System.err.println( "Could not realize media player" );
         } // end catch
         catch ( IOException iOException )
         {
             System.err.println( "Error reading from the source" );
         } // end catch
    }

    public MediaPanel()
   {
      setLayout( new BorderLayout() ); // use a BorderLayout
      
      // Use lightweight components for Swing compatibility
      Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );
      
   } // end MediaPanel constructor

} // end class MediaPanel
