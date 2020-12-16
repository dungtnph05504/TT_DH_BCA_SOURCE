package com.nec.asia.nic.comp.trans.service.impl;

import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultHitScorer;

public class NicSearchHitResultHitScorerFpImpl implements NicSearchHitResultHitScorer {

	@Override
	public int getScore(NicSearchHitResult result) {
		try {
			String hitPositionArry[] = result.getHitInfo().split(",");
			if (hitPositionArry != null) {
				Double highestScore = 0.0;
				for (int i = 0; i < hitPositionArry.length; i++) {
					String matchArry[] = hitPositionArry[i].split("=");
					Double score = Double.valueOf(matchArry[1]);
					if (score > highestScore) {
						highestScore = score;
					}
				}
				return highestScore.intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

}
