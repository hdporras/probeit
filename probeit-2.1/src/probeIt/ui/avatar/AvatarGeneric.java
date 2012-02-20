/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package probeIt.ui.avatar;

import javax.swing.ImageIcon;
import probeIt.ui.avatar.video.MediaPanel;

/**
 *
 * @author paulo
 */
public class AvatarGeneric {

protected String name;
protected String[] questions;
protected ImageIcon icon;

public AvatarGeneric(String n) {
    name = n;
}

public AvatarGeneric() {
    this("Generic");
}

public AvatarGeneric (String n, String[] qs) {
    name = n;
    questions = qs;
}

public void setName(String newName) {
    name = newName;
}

public void resetAvatar() {
    selectedPublication = "";
    selectedProduct = "";
    hasPublication = false;
    hasProduct = false;
    updateQuestions();
}

public void performQuestion(int selection) {
}

public String getName() {
    return name;
}

public void setQuestions(String[] newQuestions) {
    questions = newQuestions;
}

public String[] getQuestions() {
    return questions;
}

public void updateQuestions() {
}

public ImageIcon getAvatarIcon() {
    return icon;
}

// PUBLICATIONS

protected String selectedPublication;
protected boolean hasPublication;

public String getSelectedPublication() {
    return selectedPublication;
}

public void clearPublication() {
    selectedPublication = "";
    hasPublication = false;
    updateQuestions();
    AvatarView.getInstance().setAvatar(this);
}

public void setSelectedPublication(String newPublication) {
    selectedPublication = newPublication;
    hasPublication = true;
    AvatarView.getInstance().showVideo(MediaPanel.SELECT_PUBLICATION);
    updateQuestions();
    AvatarView.getInstance().setAvatar(this);
}

// PRODUCTS

protected String selectedProduct;
protected boolean hasProduct;

public String getSelectedProduct() {
    return selectedProduct;
}

public void clearProduct() {
    selectedProduct = "";
    hasProduct = false;
    updateQuestions();
    AvatarView.getInstance().setAvatar(this);
}

public void setSelectedProduct(String newProduct) {
    selectedProduct = newProduct;
    hasProduct = true;
    AvatarView.getInstance().showVideo(MediaPanel.SELECT_PRODUCT);
    updateQuestions();
    AvatarView.getInstance().setAvatar(this);
}

}

