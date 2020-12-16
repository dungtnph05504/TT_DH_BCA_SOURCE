/**
 * 
 */
package com.nec.asia.nic.comp.trans.dto;

import java.util.Map;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jul 17, 2013
 */
public class InvestigationForm {
	
         private Map<String,Double> matchScore;
         //private 

		/**
		 * @return the matchScore
		 */
		public Map<String, Double> getMatchScore() {
			return matchScore;
		}

		/**
		 * @param matchScore the matchScore to set
		 */
		public void setMatchScore(Map<String, Double> matchScore) {
			this.matchScore = matchScore;
		}
	

}
