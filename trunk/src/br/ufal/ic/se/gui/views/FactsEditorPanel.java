package br.ufal.ic.se.appInterface.views;

import br.ufal.ic.se.appInterface.models.FactsEditorModel;

import java.util.Observable;
import java.util.Observer;

public class FactsEditorPanel implements Observer {
    
    public FactsEditorPanel(FactsEditorModel model) {
	model.addObserver(this);
    }
    
    public void update(Observable o, Object arg) {

    }
}
