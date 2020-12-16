package com.nec.asia.nic.comp.trans.service.impl;

import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultHitScorer;

public class NicSearchHitResultHitScorerTextualImpl implements NicSearchHitResultHitScorer {

	@Override
	public int getScore(NicSearchHitResult result) {
		try {
			return result.getHitInfo().split(",").length;
		} catch (Exception e) {
			return 0;
		}
	}
}
