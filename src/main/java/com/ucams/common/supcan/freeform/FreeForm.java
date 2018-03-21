/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.common.supcan.freeform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.ucams.common.supcan.common.Common;
import com.ucams.common.supcan.common.properties.Properties;

/**
 * 硕正FreeForm
 * @author WangZhen
 * @version 2013-11-04
 */
@XStreamAlias("FreeForm")
public class FreeForm extends Common {

	public FreeForm() {
		super();
	}
	
	public FreeForm(Properties properties) {
		this();
		this.properties = properties;
	}
	
}
