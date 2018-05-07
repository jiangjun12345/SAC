package net.easipay.cbp.view.wss;

import java.util.List;

import net.easipay.cbp.view.AbstractAccountCentralServiceTest;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;

import org.junit.Test;

public class WssTests extends AbstractAccountCentralServiceTest
{
    @Test
    public void testWss() throws Exception
    {
	UnifiedConfig dicCodeConfig = UnifiedConfigSimple.getDicCodeConfig("52");
	System.out.println(dicCodeConfig.getDicValue());
	dicCodeConfig = UnifiedConfigSimple.getDicCodeConfig("52");
	System.out.println(dicCodeConfig.getDicValue());
	dicCodeConfig = UnifiedConfigSimple.getDicCodeConfig("161");
	System.out.println(dicCodeConfig.getDicValue());
	List<UnifiedConfig> _dicCodeConfigs = UnifiedConfigSimple.getDicTypeConfig("01");
	System.out.println(_dicCodeConfigs.get(0).getDicValue());
	_dicCodeConfigs = UnifiedConfigSimple.getDicTypeConfig("01");
	System.out.println(_dicCodeConfigs.get(0).getDicValue());
    }
    
}
