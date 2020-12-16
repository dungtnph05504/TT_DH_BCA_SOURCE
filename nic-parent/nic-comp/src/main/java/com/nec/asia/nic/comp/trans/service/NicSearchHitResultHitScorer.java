package com.nec.asia.nic.comp.trans.service;

import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;

public interface NicSearchHitResultHitScorer {
	public int getScore(NicSearchHitResult result);
}
