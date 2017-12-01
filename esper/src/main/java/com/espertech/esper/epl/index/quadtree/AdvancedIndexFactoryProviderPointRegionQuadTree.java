/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.epl.index.quadtree;

import com.espertech.esper.epl.expression.core.ExprNodeUtilityCore;
import com.espertech.esper.epl.expression.core.ExprEvaluator;
import com.espertech.esper.epl.expression.core.ExprNode;
import com.espertech.esper.epl.expression.core.ExprValidationException;
import com.espertech.esper.epl.index.service.EventAdvancedIndexProvisionDesc;
import com.espertech.esper.epl.lookup.AdvancedIndexDesc;

import static com.espertech.esper.epl.index.service.AdvancedIndexValidationHelper.validateColumnCount;
import static com.espertech.esper.epl.index.service.AdvancedIndexValidationHelper.validateColumnReturnTypeNumber;

public class AdvancedIndexFactoryProviderPointRegionQuadTree extends AdvancedIndexFactoryProviderQuadTree {

    public EventAdvancedIndexProvisionDesc validateEventIndex(String indexName, String indexTypeName, ExprNode[] columns, ExprNode[] parameters) throws ExprValidationException {
        validateColumnCount(2, indexTypeName, columns.length);
        validateColumnReturnTypeNumber(indexTypeName, 0, columns[0], AdvancedIndexQuadTreeConstants.COL_X);
        validateColumnReturnTypeNumber(indexTypeName, 1, columns[1], AdvancedIndexQuadTreeConstants.COL_Y);

        validateParameters(indexTypeName, parameters);

        AdvancedIndexDesc indexDesc = new AdvancedIndexDesc(indexTypeName, columns);
        ExprEvaluator xEval = indexDesc.getIndexedExpressions()[0].getForge().getExprEvaluator();
        ExprEvaluator yEval = indexDesc.getIndexedExpressions()[1].getForge().getExprEvaluator();
        AdvancedIndexConfigStatementPointRegionQuadtree indexStatementConfigs = new AdvancedIndexConfigStatementPointRegionQuadtree(xEval, yEval);

        return new EventAdvancedIndexProvisionDesc(indexDesc, ExprNodeUtilityCore.getEvaluatorsNoCompile(parameters), EventAdvancedIndexFactoryQuadTreePointRegion.INSTANCE, indexStatementConfigs);
    }
}
