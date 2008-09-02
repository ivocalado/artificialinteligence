
package br.ufal.ic.se.models;
import java.util.List;

/**
 * @author Anderson Brandao
 *
 */
public interface ConflictsResolutionStrategy {
    public List<HornClause> solveConflict(WMemory workMemory, List<HornClause> clausesList);
    
}
