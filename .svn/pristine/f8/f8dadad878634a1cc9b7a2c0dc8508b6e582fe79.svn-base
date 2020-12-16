package com.nec.asia.nic.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.framework.admin.code.domain.CodeValues;

public class CodeValueDescComparator implements Comparator<CodeValues> {
	private Collator collator;

	public CodeValueDescComparator() {
		collator = Collator.getInstance();
	}

	public CodeValueDescComparator(Locale locale) {
		collator = Collator.getInstance(locale);
	}

	@Override
	public int compare(CodeValues o1, CodeValues o2) {
		if(StringUtils.equals(o1.getCodeValueDesc(), o2.getCodeValueDesc())) {
			return 0;
		}
		else if(o1.getCodeValueDesc()==null) {
			return -1;
		}
		else if(o2.getCodeValueDesc()==null) {
			return 1;
		}
		else {
			int retValue = collator.compare(o1.getCodeValueDesc(), o2.getCodeValueDesc());
			return retValue;
		}	
	}
}
