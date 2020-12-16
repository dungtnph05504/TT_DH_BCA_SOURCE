package com.nec.asia.nic.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;

public class SiteRepositoryComparator implements Comparator<SiteRepository> {
	private Collator collator;

	public SiteRepositoryComparator() {
		collator = Collator.getInstance();
	}

	public SiteRepositoryComparator(Locale locale) {
		collator = Collator.getInstance(locale);
	}

	@Override
	public int compare(SiteRepository o1, SiteRepository o2) {
		if (StringUtils.equals(o1.getSiteName(), o2.getSiteName())) {
			return 0;
		} else if (o1.getSiteName() == null) {
			return -1;
		} else if (o2.getSiteName() == null) {
			return 1;
		} else {
			int retValue = collator.compare(o1.getSiteName(), o2.getSiteName());
			return retValue;
		}
	}
}
