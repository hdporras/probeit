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
import probeIt.ui.avatar.video.*;
import pml.*;
import pml.impl.serializable.PMLConclusion;

/**
 *
 * @author paulo
 */
public class AvatarProduct extends AvatarGeneric {

    public AvatarProduct() {
        this("Product");
    }

    public AvatarProduct(String n) {
        name = n;
        updateQuestions();
        icon = new ImageIcon(getClass().getResource("/probeIt/image/avatar-product.PNG"));
    }

    @Override
    public void updateQuestions() {
      if ((!hasPublication)&&(!hasProduct)) {
          questions = new String[7];
          questions[0] = "Show explanation";
          questions[1] = "Show provenance graph";
          questions[2] = "Show underlying workflow";
          questions[3] = "Derived from raw data";
          questions[4] = "Derived from products";
          questions[5] = "Based on publications";
          questions[6] = "Bye";
      } else {
          questions = new String[8];
          questions[0] = "Show explanation";
          questions[1] = "Show provenance graph";
          questions[2] = "Show underlying workflow";
          questions[3] = "Derived from raw data";
          questions[4] = "Derived from products";
          questions[5] = "Based on publications";
          if (hasPublication) {
              questions[6] = "Talk to publication";
          } else {
              questions[6] = "Talk to product";
          }
          questions[7] = "Bye";
      }
    }

    public void performQuestion(int selection) {
            switch (selection) {
            	case 0: {	if (!ViewsModel.getInstance().hasJustification() ||
            				    !ViewsModel.getInstance().getJustification().getURI().equals(name)) {
            					ViewsModel.getInstance().setJustification(name);
            				}
            				((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.LOCAL_JUSTIFICATION_TAB);
            				break;
            			}
                case 1: {	if (!ViewsModel.getInstance().hasJustification() ||
                				!ViewsModel.getInstance().getJustification().getURI().equals(name)) {
                				ViewsModel.getInstance().setJustification(name);
                			}
    						((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.GLOBAL_JUSTIFICATION_TAB);
                            break;
                        }
                case 2: {
                            //AvatarView.getInstance().writeMessageMainPane("[WORKFLOW TAB]");
                			String workflowURI = ViewsModel.getInstance().getWorkflowURI();
                			if (workflowURI != null) {
            					ViewsModel.getInstance().setAnswer(name);
                			}
            				((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.WORKFLOW_TAB);
                			break;
                        }
                case 3: {
                            AvatarView.getInstance().setAnswerType(AvatarView.LIST_RAW_DATA);
                            QueryInMemoryJustification query = new QueryInMemoryJustification();
                            PMLQuery newQuery = query.execute(SPOntology.RAW_DATA);
                            ViewsModel.getInstance().setQuery(newQuery);
    						((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
                            /*if (rawDataByProdQuery.setRawData(AvatarView.getInstance().getAvatar().getName())) {
                                AvatarView.getInstance().showVideo(MediaPanel.HAS_ANSWER);
                            } else {
                                Comments.fire(Comments.NO_ANSWER);
                                AvatarView.getInstance().showVideo(MediaPanel.NO_ANSWER);
                            }*/
                            break;
                        }
                case 4: {
                            AvatarView.getInstance().setAnswerType(AvatarView.LIST_PRODUCT);
                            QueryInMemoryJustification query = new QueryInMemoryJustification();
                            PMLQuery newQuery = query.execute(SPOntology.SCIENTIFIC_PRODUCT);
                            ViewsModel.getInstance().setQuery(newQuery);
    						((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
                            /* if (productByProdQuery.setProducts(AvatarView.getInstance().getAvatar().getName())) {
                                 AvatarView.getInstance().showVideo(MediaPanel.HAS_ANSWER);
                            } else {
                                 Comments.fire(Comments.NO_ANSWER);
                                 AvatarView.getInstance().showVideo(MediaPanel.NO_ANSWER);
                            } */
                            break;
                        }
                case 5: {
                            AvatarView.getInstance().setAnswerType(AvatarView.LIST_PUBLICATION);
                            QueryInMemoryJustification query = new QueryInMemoryJustification();
                            PMLQuery newQuery = query.execute(SPOntology.SCIENTIFIC_PUBLICATION);
                            ViewsModel.getInstance().setQuery(newQuery);
    						((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
                            /*if (pubsByProdQuery.setPublication(AvatarView.getInstance().getAvatar().getName())) {
                                 AvatarView.getInstance().showVideo(MediaPanel.HAS_ANSWER);
                            } else {
                                 Comments.fire(Comments.NO_ANSWER);
                                 AvatarView.getInstance().showVideo(MediaPanel.NO_ANSWER);
                            }*/
                            break;
                        }
                case 6: if (hasPublication) {
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
                case 7: {
                           AvatarView.getInstance().oracle.resetAvatar();
                           AvatarView.getInstance().setAvatar(AvatarView.oracle);
                           break;
                        }
            }
    }

}
