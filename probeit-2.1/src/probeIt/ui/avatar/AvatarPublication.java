/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package probeIt.ui.avatar;

import javax.swing.ImageIcon;

import probeIt.ui.model.ViewsModel;
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.avatar.queries.*;
import pml.*;

/**
 *
 * @author paulo
 */
public class AvatarPublication extends AvatarGeneric {

    //public static ReferencesByPublication refByPubQuery = new ReferencesByPublication();
    //public static ProductsByPublication prodByPubQuery = new ProductsByPublication();

    public AvatarPublication() {
        this("Publication");
    }
    
    public AvatarPublication(String n) {
        name = n;
        ViewsModel.getInstance().setAnswer(name);
		((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.ANSWER_TAB);
        updateQuestions();
        icon = new ImageIcon(getClass().getResource("/probeIt/image/avatar-publication.PNG"));
    }

    @Override
    public void updateQuestions() {
      if ((!hasPublication)&&(!hasProduct)) {
          questions = new String[4];
          questions[0] = "Show provenance";
          questions[1] = "Show references";
          questions[2] = "Show used products";
          questions[3] = "Bye";
      } else {
           questions = new String[5];
          questions[0] = "Show provenance";
          questions[1] = "Show references";
          questions[2] = "Show used product";
          if (hasPublication) {
              questions[3] = "Talk to publication";
          } else {
              questions[3] = "Talk to product";
          }
          questions[4] = "Bye";
      }
    }

    public void performQuestion(int selection) {
            switch (selection) {
              case 0: {	 ViewsModel.getInstance().setJustification(name);
              			 //((ProbeItView)ViewsManager.getInstance().getViewPane()).resetAnswerTab();
              			 ((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.GLOBAL_JUSTIFICATION_TAB);
              			 break;
              		  }
              case 1: {
                         AvatarView.getInstance().setAnswerType(AvatarView.LIST_PUBLICATION);
                         QueryInMemoryJustification query = new QueryInMemoryJustification();
                         PMLQuery newQuery = query.execute(SPOntology.SCIENTIFIC_PUBLICATION);
                         ViewsModel.getInstance().setQuery(newQuery);
 						 ((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
                         /*if (!refByPubQuery.setPublications(AvatarView.getInstance().getAvatar().getName()))
                             Comments.fire(Comments.NO_ANSWER); */
                         break;
                      }
              case 2: {  
                         AvatarView.getInstance().setAnswerType(AvatarView.LIST_PRODUCT);
                         QueryInMemoryJustification query = new QueryInMemoryJustification();
                         PMLQuery newQuery = query.execute(SPOntology.SCIENTIFIC_PRODUCT);
                         ViewsModel.getInstance().setQuery(newQuery);
 						 ((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
                         /*if (!prodByPubQuery.setProducts(AvatarView.getInstance().getAvatar().getName()))
                             Comments.fire(Comments.NO_ANSWER);*/
                         break;
                      }
              case 3: if (hasPublication) {
                         AvatarView.getInstance().setAvatar(new AvatarPublication(selectedPublication));
                         break;
                      } if (hasProduct) {
                         AvatarView.getInstance().setAvatar(new AvatarProduct(selectedProduct));
                         break;
                      } else {
                         AvatarView.getInstance().oracle.resetAvatar();
                         AvatarView.getInstance().setAvatar(AvatarView.oracle);
                         break;
                      }
              case 4: {
                        AvatarView.getInstance().oracle.resetAvatar();
                        AvatarView.getInstance().setAvatar(AvatarView.oracle);
                        break;
                      }
            }
    }

}
