package br.ufal.ic.se.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anderson Brandao
 *
 */

public class MostFactsMatched implements ConflictsResolutionStrategy {
   public List<HornClause> solveConflict(WMemory workMemory, List<HornClause> clausesList) {
       return new ArrayList<HornClause>();
   }
}
