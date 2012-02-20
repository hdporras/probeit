/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package probeIt.ui.avatar;

import javax.swing.JComponent;
import javax.swing.ImageIcon;

import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.avatar.video.*;
import probeIt.ui.model.ViewsModel;

/**
 *
 * @author paulo
 */
public class AvatarOracle extends AvatarGeneric {

public AvatarOracle() {
    name = "CyberShARE";
    resetAvatar();
    updateQuestions();
    icon = new ImageIcon(getClass().getResource("/probeIt/image/avatar-oracle.PNG"));
    
}

@Override
public void updateQuestions() {
    if ((!hasPublication)&&(!hasProduct)) {
        questions = new String[3];
        questions[0] = "List publications by ...";
        questions[1] = "List products of type ...";
        questions[2] = "Bye";
    } else {
        questions = new String[4];
        questions[0] = "List publications by ...";
        questions[1] = "List products of type ...";
        if (hasPublication) {
            questions[2] = "Talk to publication";
        } else {
            questions[2] = "Talk to product";
        }
        questions[3] = "Bye";
    }
}


@Override
public void performQuestion(int selection) {
        //System.out.println("sel:" + selection + " hasPub:" + hasPublication + " hasPro:" + hasProduct);
        switch (selection) {
            case 0: {   // query publications
                        resetAvatar();
                        AvatarView.getInstance().setAvatar(this);
                        AvatarView.getInstance().setAnswerType(AvatarView.LIST_PUBLICATION);
                        AvatarView.authorUI.authorDialog.getListAuthors().loadAuthors();
                        AvatarView.authorUI.setVisible(true);
                        break;
                    }
            case 1: {   // query products
                        resetAvatar();
                        AvatarView.getInstance().setAvatar(this);
                        AvatarView.getInstance().setAnswerType(AvatarView.LIST_PRODUCT);
                        AvatarView.typeUI.typeDialog.getListDataProductType().loadTypes();
                        AvatarView.typeUI.setVisible(true);
                        break;
                    }
            case 2: if (hasPublication) {
                        AvatarView.getInstance().showVideo(MediaPanel.CONNECT_PUBLICATION);
                        if (AvatarView.getInstance().getHasVideo()) {
                           try
                           {
                              Thread.sleep(2000);  // a little delay to let the clip ends
                           } catch (InterruptedException ie)
                           {
                               ie.printStackTrace();
                           }
                        }
                    	ViewsModel.getInstance().setAnswer(selectedPublication);
                        AvatarView.getInstance().setAvatar(new AvatarPublication(selectedPublication));
                        break;
                    } if (hasProduct) {
                        AvatarView.getInstance().showVideo(MediaPanel.CONNECT_PRODUCT);
                        try
                        {
                            Thread.sleep(2000);
                        } catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                    	ViewsModel.getInstance().setAnswer(selectedProduct);
                        AvatarView.getInstance().setAvatar(new AvatarProduct(selectedProduct));
                        break;
                    } else {
                        System.exit(0); break;
                    }
            case 3: System.exit(0); break;
        }
}


}
