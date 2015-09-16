package org.paces.Stata;

import com.stata.sfi.Data;
import com.stata.sfi.SFIToolkit;

import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class VarDebug {

	public static int vdb(String[] args) {

		Variables myvars = new Variables();

		List<Integer> varindex = myvars.getVariableIndex();

		for (Integer vdx : varindex) {

			SFIToolkit.displayln(Data.getVarName(vdx));
			SFIToolkit.displayln(Data.getVarLabel(vdx));
			SFIToolkit.displayln(Data.getVarFormat(vdx));

		}

		return 0;

	}

}
