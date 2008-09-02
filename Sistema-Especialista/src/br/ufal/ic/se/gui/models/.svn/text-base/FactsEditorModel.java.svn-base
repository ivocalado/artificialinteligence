package br.ufal.ic.se.gui.models;

import br.ufal.ic.se.node.TLetter;
import br.ufal.ic.se.models.KB;

import java.sql.*;
import java.util.*;


public class FactsEditorModel extends Observable {
    private KB knowledgeBase;
    private Map<TLetter, String> factsToUpdate;
    
    public FactsEditorModel(KB knowledgeBase) {
	this.knowledgeBase = knowledgeBase;
	factsToUpdate = new HashMap<TLetter, String>();
    }
    
    public void addVariable(TLetter symbol) {	
	knowledgeBase.tell(symbol, "");
	setChanged();
	notifyObservers(knowledgeBase);
    }
    
    public void addVariable(TLetter symbol, String description) {	
	knowledgeBase.tell(symbol, description);
	setChanged();
	notifyObservers(knowledgeBase);
    }
    
    public void removeVariable(TLetter symbol) {
	//knowledgeBase
	setChanged();
	notifyObservers(knowledgeBase);
    }
    
    public void setVariableDescription(TLetter symbol, String newDescription) {
	setChanged();
	notifyObservers(knowledgeBase);
    }    
    
    public void update(Observable o, Object arg) {

    }
}
