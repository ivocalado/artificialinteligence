package br.ufal.ic.se.models;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Anderson Brandao
 *
 * @param <K> a key
 * @param <V> a value to be mapped to a key
 */
public class WMemory <K ,V> {
    private Map<K, V> deducedSymbols; 	
    private boolean loaded; 
    //TODO dummy variables just for testing methods - remove later
    private int c = 1;
    private boolean a = false;

    public WMemory() {
	deducedSymbols = new HashMap<K, V>();
	loaded = false;
    }	

    public V get(K query) {
	return deducedSymbols.get(query);				
    }

    public void put(K key, V value) {
	boolean before = false;
	if (deducedSymbols.get(key) != null)
	 before = (Boolean) deducedSymbols.get(key);
	deducedSymbols.put(key, value);
	boolean  after = (Boolean) deducedSymbols.get(key);
	loaded = true;
	if (a && (before!=after))
	    print();
    }	

    public void reset() {
	deducedSymbols.clear();
	loaded = false;
	c = 1;
    }	
    
    public boolean isLoaded() {
	return loaded;
    }
    
    public Map<K,V> dump() {
	return deducedSymbols;
    }

    //test method
    public void print() {
	System.out.println("------ WM after cicle "+ c++ +" ------");
	for (K key : deducedSymbols.keySet()) {
	    System.out.println("Key: " + key + " value: " + this.get(key));
	}
	System.out.println("______________________________");
    }
    //test method 
    public void setPrintable(boolean c) {
	a = c;
    }
}
