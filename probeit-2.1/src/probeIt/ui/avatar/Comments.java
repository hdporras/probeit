/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package probeIt.ui.avatar;

/**
 *
 * @author paulo
 */
public class Comments {

    public static final int WELCOME = 0;
    public static final int NO_ANSWER = 1;

    public static final String[] COMMENTS = {
            "How can I Help You?",
            "Sorry, no answers"
        };

    public static void fire (int comment) {
        AvatarView.getInstance().writeComment(COMMENTS[comment]);
    }

    public static void clear() {
        AvatarView.getInstance().writeComment("");
    }
}
