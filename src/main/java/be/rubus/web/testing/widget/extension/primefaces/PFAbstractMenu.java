package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.ArrayUtil;
import be.rubus.web.testing.widget.extension.primefaces.internal.SubMenu;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PFAbstractMenu extends AbstractPrimeFacesWidget {
    protected List<SubMenu> subMenus = new ArrayList<SubMenu>();

    public int getCount(Integer... idx) {
        int result;
        if (idx == null || idx.length == 0) {
            result = subMenus.size();
        } else {
            SubMenu subMenu = subMenus.get(idx[0]);
            result = subMenu.getCount(ArrayUtil.dropFirstItem(idx));
        }
        return result;
    }

    public String getLabel(Integer... idx) {
        if (idx == null || idx.length == 0) {
            throw new IllegalArgumentException("idx can't be null or empty");
        }

        SubMenu subMenu = subMenus.get(idx[0]);
        return subMenu.getLabel(ArrayUtil.dropFirstItem(idx));
    }

    public void click(Integer... idx) {
        if (idx == null || idx.length == 0) {
            throw new IllegalArgumentException("idx can't be null or empty");
        }

        SubMenu subMenu = subMenus.get(idx[0]);
        subMenu.click(ArrayUtil.dropFirstItem(idx));
    }
}
